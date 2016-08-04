/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason Gridworld                                      #
 * # Copyright (c) 2015-16, Philipp Kraus (philipp.kraus@tu-clausthal.de)               #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */


package agentrouting.simulation.agent;

import agentrouting.simulation.CMath;
import agentrouting.simulation.agent.pokemon.CPokemon;
import agentrouting.simulation.algorithm.force.IForce;
import agentrouting.simulation.environment.EDirection;
import agentrouting.simulation.environment.EQuadrant;
import agentrouting.simulation.environment.IEnvironment;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.action.binding.IAgentActionAllow;
import org.lightjason.agentspeak.action.binding.IAgentActionBlacklist;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * agent class for modelling individual behaviours
 */
@IAgentActionBlacklist
public abstract class IBaseAgent extends org.lightjason.agentspeak.agent.IBaseAgent<IAgent> implements IAgent
{
    /**
     * name of the beliefbase for individual preferences
     */
    public static final String PREFERENCE = "preferences";
    /**
     * sprite
     */
    protected Sprite m_sprite;
    /**
     * random generator
     */
    protected final Random m_random = new Random();
    /**
     * current position of the agent
     */
    protected final DoubleMatrix1D m_position;
    /**
     * reference to the environment
     */
    protected final IEnvironment m_environment;
    /**
     * current moving speed
     *
     * @deprecated move to preference
     */
    @Deprecated
    private int m_speed = 1;
    /**
     * route
     */
    private final List<DoubleMatrix1D> m_route = Collections.synchronizedList( new LinkedList<>() );



    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_force force model
     * @param p_position initialize position
     */
    protected IBaseAgent( final IEnvironment p_environment, final IAgentConfiguration<IAgent> p_agentconfiguration,
                final IForce p_force, final DoubleMatrix1D p_position
    )
    {
        super( p_agentconfiguration );

        m_position = p_position;
        m_environment = p_environment;
    }

    @Override
    public final String toString()
    {
        return MessageFormat.format(
            "{0} - current position (speed) [{1} ({2})] - route [{3}]",
            super.toString(),
            m_position == null ? "" : CMath.MATRIXFORMAT.toString( m_position ),
            m_speed,
            m_route == null ? "" : m_route.stream().map( CMath.MATRIXFORMAT::toString ).collect( Collectors.joining( ", " ) )
        );
    }

    @Override
    public final Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public IAgent call() throws Exception
    {
        // cache current position to generate non-moving and targte-beyond trigger
        final DenseDoubleMatrix1D l_position = new DenseDoubleMatrix1D( m_position.toArray() );
        final EQuadrant l_quadrant = EQuadrant.quadrant( this.goal(), l_position );

        // --- visualization -----------------------------------------------------------------------

        // update sprite for painting (sprit position is x/y position, but position storing is row / column)
        if ( m_sprite != null )
            m_sprite.setPosition( (float) l_position.get( 1 ), (float) l_position.get( 0 ) );


        // --- agent-cycle to create goal-trigger --------------------------------------------------

        // call cycle
        super.call();

        // if position is not changed run not-moved plan
        if ( m_position.equals( l_position ) )
            this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "movement/standstill" ) ) );

        // check if the agent reaches the goal-position, if it reachs, remove it from the route queue
        final DoubleMatrix1D l_goalposition = this.goal();
        if ( m_position.equals( l_goalposition ) )
            this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "goal/achieve-position", Stream.of( CRawTerm.from( m_position ) ) ) ) );
        else
        {
            // check if the quadrant between cached position and current position relative to goal-position, if it is changed, than we have missed the goal-position
            if ( !l_quadrant.equals( EQuadrant.quadrant( l_goalposition, m_position ) ) )
                this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "goal/beyond", Stream.of( CRawTerm.from( l_goalposition ) ) ) ) );

            // otherwise check "near-by(D)" preference for the current position and the goal
            // position, D is the radius (in cells) so we trigger the goal "near-by(Y)" and
            // Y is a literal with distance
            final double l_distance = CMath.distance( m_position, l_goalposition );

            // default argument must match literal-value type (and on integral types long is used)
            if ( l_distance <= this.preference( "near-by", Long.valueOf( 0 ) ).doubleValue() )
                this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "goal/near-by", Stream.of( CRawTerm.from( l_distance ) ) ) ) );
        }

        return this;
    }


    // --- object getter ---------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }

    @Override
    public final Stream<ILiteral> preferences()
    {
        return this.beliefbase().stream( CPath.from( PREFERENCE ) );
    }

    @Override
    public final <N> N preference( final String p_name, final N p_default )
    {
        return CCommon.raw(
            this.beliefbase().stream( CPath.from( MessageFormat.format( "{0}/{1}", PREFERENCE, p_name ) ) )
                .findFirst()
                .orElseGet( () -> CLiteral.from( MessageFormat.format( "{0}/{1}", PREFERENCE, p_name ), Stream.of( CRawTerm.from( p_default ) ) ) )
                .values()
                .findFirst()
                .orElse( CRawTerm.from( p_default ) )
        );
    }

    /**
     * returns the goal-position
     * @return position
     */
    protected final DoubleMatrix1D goal()
    {
        return m_route.isEmpty()
               ? m_position
               : m_route.get( 0 );
    }

    // --- agent actions ---------------------------------------------------------------------------------------------------------------------------------------
    // https://en.wikipedia.org/wiki/Fitness_proportionate_selection to calculate the direction

    /**
     * agent action to set speed to a fixed value
     *
     * @param p_speed spped value
     */
    @IAgentActionName( name = "speed/set" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void setspeed( final Number p_speed )
    {
        if ( p_speed.intValue() < 1 )
            throw new RuntimeException( "speed cannot be less than one" );
        m_speed = p_speed.intValue();
    }

    /**
     * agent action to increment speed
     *
     * @param p_speed increment value
     */
    @IAgentActionName( name = "speed/increment" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void incrementspeed( final Number p_speed )
    {
        if ( p_speed.intValue() < 1 )
            throw new RuntimeException( "speed cannot be less than one" );
        m_speed += p_speed.intValue();
    }

    /**
     * agent action to decrement speed
     *
     * @param p_speed decrement value
     */
    @IAgentActionName( name = "speed/decrement" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void decrementspeed( final Number p_speed )
    {
        if ( ( p_speed.intValue() < 1 ) || ( m_speed - p_speed.intValue() < 1 ) )
            throw new RuntimeException( "speed cannot be less than one or cannot be smaler than one" );
        m_speed -= p_speed.intValue();
    }

    /**
     * route calculation
     *
     * @param p_row row position
     * @param p_column column position
     */
    @IAgentActionAllow
    @IAgentActionName( name = "route/set" )
    protected final void route( final Number p_row, final Number p_column )
    {
        m_route.addAll( m_environment.route( m_position, new DenseDoubleMatrix1D( new double[]{p_row.doubleValue(), p_column.doubleValue()} ) ) );
    }

    /**
     * creates a new route depend on the
     * distance around the current position
     *
     * @param p_radius distance (in cells)
     */
    @IAgentActionAllow
    @IAgentActionName( name = "route/random" )
    protected final void routerandom( final Number p_radius )
    {
        if ( p_radius.intValue() < 1 )
            throw new RuntimeException( "radius must be greater than zero" );

        m_route.addAll(
            m_environment.route(
                m_position,
                new DenseDoubleMatrix1D(
                    new double[]{
                        m_position.getQuick( 0 ) + m_random.nextInt( p_radius.intValue() * 2 ) - p_radius.intValue(),
                        m_position.getQuick( 1 ) + m_random.nextInt( p_radius.intValue() * 2 ) - p_radius.intValue()
                    }
                )
            )
        );
    }

    /**
     * skips the current goal-position of the routing queue
     */
    @IAgentActionAllow
    @IAgentActionName( name = "route/next" )
    protected final void routenext()
    {
        if ( !m_route.isEmpty() )
            m_route.remove( 0 );
    }

    /**
     * skips the current n-elements of the routing queue
     *
     * @param p_value number of elements
     */
    @IAgentActionAllow
    @IAgentActionName( name = "route/skip" )
    protected final void routeskip( final Number p_value )
    {
        if ( p_value.intValue() < 1 )
            throw new RuntimeException( "value must be greater than zero" );

        IntStream.range( 0, p_value.intValue() ).filter( i -> !m_route.isEmpty() ).forEach( i -> m_route.remove( 0 ) );
    }

    /**
     * calculates the estimated time by the
     * current speed of the current route
     */
    @IAgentActionAllow
    @IAgentActionName( name = "route/estimatedtime" )
    protected final double routeestimatedtime()
    {
        return m_environment.routestimatedtime( m_route, m_speed );
    }

    /**
     * move forward into goal direction
     */
    @IAgentActionName( name = "move/forward" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void moveforward()
    {
        this.move( EDirection.FORWARD );
    }

    /**
     * move left forward into goal direction
     */
    @IAgentActionName( name = "move/forwardright" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void moveforwardright()
    {
        this.move( EDirection.FORWARDRIGHT );
    }

    /**
     * move right to the goal direction
     */
    @IAgentActionName( name = "move/right" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void moveright()
    {
        this.move( EDirection.RIGHT );
    }

    /**
     * move backward right from goal direction
     */
    @IAgentActionName( name = "move/backwardright" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void movebackwardright()
    {
        this.move( EDirection.BACKWARDRIGHT );
    }

    /**
     * move backward from goal direction
     */
    @IAgentActionName( name = "move/backward" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void movebackward()
    {
        this.move( EDirection.BACKWARD );
    }

    /**
     * move backward right from goal direction
     */
    @IAgentActionName( name = "move/backwardleft" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void movebackwardleft()
    {
        this.move( EDirection.BACKWARDLEFT );
    }

    /**
     * move left to the goal
     */
    @IAgentActionName( name = "move/left" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void moveleft()
    {
        this.move( EDirection.LEFT );
    }

    /**
     * move forward left into goal direction
     */
    @IAgentActionName( name = "move/forwardleft" )
    @IAgentActionAllow( classes = CPokemon.class )
    protected final void moveforwardleft()
    {
        this.move( EDirection.FORWARDLEFT );
    }

    /**
     * helper method for moving
     *
     * @param p_direction direction
     */
    private void move( final EDirection p_direction )
    {
        final DoubleMatrix1D l_goalposition = this.goal();
        if ( l_goalposition.equals( m_position ) )
            return;

        if ( !this.equals( m_environment.position( this, p_direction.position( m_position, l_goalposition, m_speed ) ) ) )
            throw new RuntimeException( MessageFormat.format( "cannot move {0}", p_direction ) );
    }

}
