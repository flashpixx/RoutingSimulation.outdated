/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason AgentSpeak(L)                                  #
 * # Copyright (c) 2015-16, Philipp Kraus (philipp@lightjason.org)                      #
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

package org.lightjason.examples.pokemon.simulation.agent;

import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.examples.pokemon.simulation.CMath;
import org.lightjason.examples.pokemon.simulation.algorithm.force.IForce;
import org.lightjason.examples.pokemon.simulation.environment.EDirection;
import org.lightjason.examples.pokemon.simulation.environment.EQuadrant;
import org.lightjason.examples.pokemon.simulation.environment.IEnvironment;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * agent class for modelling individual behaviours
 */
@IAgentAction
@SuppressFBWarnings( "NM_SAME_SIMPLE_NAME_AS_SUPERCLASS" )
public abstract class IBaseAgent extends org.lightjason.agentspeak.agent.IBaseAgent<IAgent> implements IAgent
{
    /**
     * sprite
     */
    protected Sprite m_sprite;
    /**
     * reference to the environment
     */
    protected final IEnvironment m_environment;
    /**
     * current position of the agent
     */
    private final DoubleMatrix1D m_position;
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
    public String toString()
    {
        return MessageFormat.format(
            "{0} - route [{1}]",
            super.toString(),
            m_route.stream().map( CMath.MATRIXFORMAT::toString ).collect( Collectors.joining( "; " ) )
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

        // get the next landmark
        final DoubleMatrix1D l_goalposition = this.goal();

        // check "near-by(D)" preference for the current position and the goal
        // position, D is the radius (in cells) so we trigger the goal "near-by(Y)" and
        // Y is a literal with distance,
        // default argument must match literal-value type (and on integral types long is used)
        final double l_distance = CMath.distance( m_position, l_goalposition );
        if ( l_distance <= this.nearby() )
            this.trigger(
                CTrigger.from(
                    ITrigger.EType.ADDGOAL,
                    CLiteral.from( "position/achieve", Stream.of( CRawTerm.from( l_goalposition ), CRawTerm.from( l_distance ) ) )
                )
            );

        // check if the quadrant between cached position and current position relative to goal-position, if it is changed, than we have missed the goal-position
        if ( !l_quadrant.equals( EQuadrant.quadrant( l_goalposition, m_position ) ) )
            this.trigger(
                CTrigger.from(
                    ITrigger.EType.ADDGOAL,
                    CLiteral.from( "position/beyond", Stream.of( CRawTerm.from( l_goalposition ) ) )
                )
            );

        // System.out.println( this );

        return this;
    }


    // --- object getter ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * returns the speed
     *
     * @return speed
     */
    protected abstract int speed();

    /**
     * returns the near-by attribute
     *
     * @return near-by value
     */
    protected abstract double nearby();

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
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
     * route calculation and add landmarks at the beginning
     *
     * @param p_row row position
     * @param p_column column position
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/set/start" )
    protected final void routeatstart( final Number p_row, final Number p_column )
    {
        m_route.addAll( 0, this.route( p_row, p_column ) );
    }

    /**
     * creates a new route depend on the
     * distance around the current position
     * and add landmarks at the beginning
     *
     * @param p_radius distance (in cells)
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/random/start" )
    protected final void routerandomatstart( final Number p_radius )
    {
        if ( p_radius.intValue() < 1 )
            throw new RuntimeException( "radius must be greater than zero" );

        m_route.addAll(
            0,
            this.route(
                m_position.getQuick( 0 ) + CMath.RANDOMGENERATOR.nextInt( p_radius.intValue() * 2 ) - p_radius.intValue(),
                m_position.getQuick( 1 ) + CMath.RANDOMGENERATOR.nextInt( p_radius.intValue() * 2 ) - p_radius.intValue()
            )
        );
    }

    /**
     * route calculation and add landmarks at the end
     *
     * @param p_row row position
     * @param p_column column position
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/set/end" )
    protected final void routeatend( final Number p_row, final Number p_column )
    {
        m_route.addAll( this.route( p_row, p_column ) );
    }

    /**
     * creates a new route depend on the
     * distance around the current position and
     * add landmarks at the end
     *
     * @param p_radius distance (in cells)
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/random/end" )
    protected final void routerandomatend( final Number p_radius )
    {
        if ( p_radius.intValue() < 1 )
            throw new RuntimeException( "radius must be greater than zero" );

        m_route.addAll(
            this.route(
                m_position.getQuick( 0 ) + CMath.RANDOMGENERATOR.nextInt( p_radius.intValue() * 2 ) - p_radius.intValue(),
                m_position.getQuick( 1 ) + CMath.RANDOMGENERATOR.nextInt( p_radius.intValue() * 2 ) - p_radius.intValue()
            )
        );
    }



    /**
     * calculates a new route
     * @param p_row target row position
     * @param p_column target column position
     * @return route list
     */
    private List<DoubleMatrix1D> route( final Number p_row, final Number p_column )
    {
        return m_environment.route( m_position, new DenseDoubleMatrix1D( new double[]{p_row.doubleValue(), p_column.doubleValue()} ) );
    }


    /**
     * skips the current goal-position of the routing queue
     */
    @IAgentActionFilter
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
    @IAgentActionFilter
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
     *
     * @return time
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/estimatedtime" )
    protected final double routeestimatedtime()
    {
        return m_route.size() < 1
               ? 0
               : m_environment.routestimatedtime( Stream.concat( Stream.of( m_position ), m_route.stream() ), this.speed() );
    }

    /**
     * move forward into goal direction
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/forward" )
    protected final void moveforward()
    {
        this.move( EDirection.FORWARD );
    }

    /**
     * move left forward into goal direction
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/forwardright" )
    protected final void moveforwardright()
    {
        this.move( EDirection.FORWARDRIGHT );
    }

    /**
     * move right to the goal direction
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/right" )
    protected final void moveright()
    {
        this.move( EDirection.RIGHT );
    }

    /**
     * move backward right from goal direction
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/backwardright" )
    protected final void movebackwardright()
    {
        this.move( EDirection.BACKWARDRIGHT );
    }

    /**
     * move backward from goal direction
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/backward" )
    protected final void movebackward()
    {
        this.move( EDirection.BACKWARD );
    }

    /**
     * move backward right from goal direction
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/backwardleft" )
    protected final void movebackwardleft()
    {
        this.move( EDirection.BACKWARDLEFT );
    }

    /**
     * move left to the goal
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/left" )
    protected final void moveleft()
    {
        this.move( EDirection.LEFT );
    }

    /**
     * move forward left into goal direction
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/forwardleft" )
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

        if ( !this.equals( m_environment.move( this, p_direction.position( m_position, l_goalposition, this.speed() ) ) ) )
            throw new RuntimeException( MessageFormat.format( "cannot move {0}", p_direction ) );
    }

}
