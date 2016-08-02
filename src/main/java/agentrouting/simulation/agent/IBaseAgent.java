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
import agentrouting.simulation.environment.EDirection;
import agentrouting.simulation.environment.EQuadrant;
import agentrouting.simulation.environment.IEnvironment;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.action.binding.IAgentActionAllow;
import org.lightjason.agentspeak.action.binding.IAgentActionBlacklist;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.beliefbase.IBeliefBaseOnDemand;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
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
abstract class IBaseAgent extends org.lightjason.agentspeak.agent.IBaseAgent<IAgent> implements IAgent
{
    /**
     * name of the beliefbase for individual preferences
     */
    public static final String PREFERENCE = "preferences";
    /**
     * name of the environment beliefbase with local view structure
     */
    private static final String ENVIRONMENT = "env";
    /**
     * random generator
     */
    private final Random m_random = new Random();
    /**
     * current position of the agent
     */
    private final DoubleMatrix1D m_position;
    /**
     * reference to the environment
     */
    private final IEnvironment m_environment;
    /**
     * color
     */
    private final Color m_color;
    /**
     * sprite object for painting
     */
    private Sprite m_sprite;
    /**
     * current moving speed
     */
    private int m_speed = 1;
    /**
     * route
     */
    private final Queue<DoubleMatrix1D> m_route = new ConcurrentLinkedQueue<>();




    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_force force model
     * @param p_position initialize position
     * @param p_color color string in RRGGBBAA
     */
    IBaseAgent( final IEnvironment p_environment, final IAgentConfiguration<IAgent> p_agentconfiguration,
                final IForce p_force, final DoubleMatrix1D p_position, final String p_color
    )
    {
        super( p_agentconfiguration );
        if ( p_color.isEmpty() )
            throw new RuntimeException( "color need not to be empty" );

        m_position = new DenseDoubleMatrix1D( 2 );
        m_environment = p_environment;
        m_color = Color.valueOf( p_color );


        // push the on-demand beliefbase to the agent
        m_beliefbase.add( new CEnvironmentBeliefbase().create( ENVIRONMENT ) );
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
    public final DoubleMatrix1D goal()
    {
        return m_route.isEmpty()
            ? m_position
            : m_route.peek();
    }

    @Override
    public final Stream<ILiteral> preferences()
    {
        return this.beliefbase().stream( CPath.from( PREFERENCE ) );
    }





    // --- agent actions ---------------------------------------------------------------------------------------------------------------------------------------
    // https://en.wikipedia.org/wiki/Fitness_proportionate_selection to calculate the direction

    @IAgentActionName( name = "speed/set" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void setspeed( final Number p_speed )
    {
        if ( p_speed.intValue() < 1 )
            throw new RuntimeException( "speed cannot be less than one" );
        m_speed = p_speed.intValue();
    }

    @IAgentActionName( name = "speed/increment" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void incrementspeed( final Number p_speed )
    {
        if ( p_speed.intValue() < 1 )
            throw new RuntimeException( "speed cannot be less than one" );
        m_speed += p_speed.intValue();
    }

    @IAgentActionName( name = "speed/decrement" )
    @IAgentActionAllow( classes = CMovingAgent.class )
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
        final List<DoubleMatrix1D> l_route = m_environment.route( this, new DenseDoubleMatrix1D( new double[]{p_row.doubleValue(), p_column.doubleValue()} ) );
        // @bug the first item must be removed, because it is the current position
        if ( l_route.size() > 1 )
            l_route.remove( 0 );

        m_route.addAll( l_route );
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
                this,
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
        m_route.poll();
    }

    /**
     * skips the current n-elements of the routing queue
     * @param p_value number of elements
     */
    @IAgentActionAllow
    @IAgentActionName( name = "route/skip" )
    protected final void routeskip( final Number p_value )
    {
        if ( p_value.intValue() < 1 )
            throw new RuntimeException( "value must be greater than zero" );

        IntStream.range( 0, p_value.intValue() ).forEach( i -> m_route.poll() );
    }

    /**
     * move forward into goal direction
     */
    @IAgentActionName( name = "move/forward" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void moveforward()
    {
        this.move( EDirection.FORWARD );
    }

    /**
     * move left forward into goal direction
     */
    @IAgentActionName( name = "move/forwardright" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void moveforwardright()
    {
        this.move( EDirection.FORWARDRIGHT );
    }

    /**
     * move right to the goal direction
     */
    @IAgentActionName( name = "move/right" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void moveright()
    {
        this.move( EDirection.RIGHT );
    }

    /**
     * move backward right from goal direction
     */
    @IAgentActionName( name = "move/backwardright" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void movebackwardright()
    {
        this.move( EDirection.BACKWARDRIGHT );
    }

    /**
     * move backward from goal direction
     */
    @IAgentActionName( name = "move/backward" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void movebackward()
    {
        this.move( EDirection.BACKWARD );
    }

    /**
     * move backward right from goal direction
     */
    @IAgentActionName( name = "move/backwardleft" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void movebackwardleft()
    {
        this.move( EDirection.BACKWARDLEFT );
    }

    /**
     * move left to the goal
     */
    @IAgentActionName( name = "move/left" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void moveleft()
    {
        this.move( EDirection.LEFT );
    }

    /**
     * move forward left into goal direction
     */
    @IAgentActionName( name = "move/forwardleft" )
    @IAgentActionAllow( classes = CMovingAgent.class )
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

    // --- visualization ---------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public final Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public final Sprite spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize )
    {
        // create a colored sequare for the agent
        final Pixmap l_pixmap = new Pixmap( p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
        l_pixmap.setColor( m_color );
        l_pixmap.fillRectangle( 0, 0, p_cellsize, p_cellsize );

        // add the square to a sprite (for visualization) and scale it to 90% of cell size
        m_sprite = new Sprite( new Texture( l_pixmap ), 0, 0, p_cellsize, p_cellsize );
        m_sprite.setSize( 0.9f * p_cellsize, 0.9f * p_cellsize );
        m_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );

        return m_sprite;
    }


    // --- on-demand beliefbase for local environment data of the agent ----------------------------------------------------------------------------------------

    /**
     * enum for create on-demand literals
     */
    private enum ELiteral
    {
        SIZE,
        SPEED,
        LANDMARKS;

        /**
         * creates the literal
         *
         * @param p_environment environment
         * @param p_speed current agent speed
         * @param p_landmarks number of current landmarks
         * @return literal
         */
        public final ILiteral create( final IEnvironment p_environment, final int p_speed, final int p_landmarks )
        {
            switch ( this )
            {
                case SIZE: return CLiteral.from( this.name().toLowerCase(),
                                                Stream.of(
                                                    CLiteral.from( "column", Stream.of( CRawTerm.from( p_environment .column() ) ) ),
                                                    CLiteral.from( "row", Stream.of(  CRawTerm.from( p_environment .row() ) ) )
                                                )
                );

                case SPEED: return CLiteral.from( this.name().toLowerCase(), Stream.of( CRawTerm.from( p_speed )) );

                case LANDMARKS: return CLiteral.from( this.name().toLowerCase(), Stream.of( CRawTerm.from( p_landmarks )) );

                default:
                    throw new RuntimeException( MessageFormat.format( "enum value [{0}] does not exist", this ) );
            }
        }


    }

    /**
     * class for local-environment data of the agent
     */
    private final class CEnvironmentBeliefbase extends IBeliefBaseOnDemand<IAgent>
    {

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return super.containsLiteral( p_key );
        }

        @Override
        public Collection<ILiteral> literal( final String p_key )
        {
            return super.literal( p_key );
        }

        @Override
        public Stream<ILiteral> streamLiteral()
        {
            return super.streamLiteral();
        }
    }
}
