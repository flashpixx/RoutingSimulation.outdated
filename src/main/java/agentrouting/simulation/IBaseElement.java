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

package agentrouting.simulation;

import agentrouting.simulation.agent.CMovingAgent;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.action.binding.IAgentActionAllow;
import org.lightjason.agentspeak.action.binding.IAgentActionBlacklist;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.CAgent;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;

import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;


/**
 * abstract class for all preferences access
 */
@IAgentActionBlacklist
public abstract class IBaseElement<T> extends CAgent<IElement<T>> implements IElement<T>
{
    /**
     * random generator
     */
    protected final Random m_random = new Random();
    /**
     * goal-position of the agent
     */
    protected final DoubleMatrix1D m_goal;
    /**
     * current position of the agent
     */
    protected final DoubleMatrix1D m_position;
    /**
     * reference to the environment
     */
    protected final IEnvironment m_environment;
    /**
     * force model
     */
    protected final IForce m_force;
    /**
     * sprite object for painting
     */
    private Sprite m_sprite;
    /**
     * current moving speed
     */
    private int m_speed = 1;

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_force force model
     * @param p_position initial position
     */
    protected IBaseElement( final IEnvironment p_environment, final IAgentConfiguration<IElement<T>> p_agentconfiguration,
                            final IForce p_force, final DoubleMatrix1D p_position
    ) throws Exception
    {
        super( p_agentconfiguration );
        m_force = p_force;
        m_position = p_position;
        m_environment = p_environment;

        // create a viewpoint based on the current position
        m_goal = new DenseDoubleMatrix1D( 2 );
        this.viewpointrandom( Math.min( m_environment.column(), m_environment.row() ) / 2 );

        // run first cycle
        super.call();
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public final T execute( final int p_step )
    {
        // run agent-cycle
        // cache current position to generate non-moving plan
        final DenseDoubleMatrix1D l_postion = new DenseDoubleMatrix1D( m_position.toArray() );
        try
        {
            super.call();
        }
        catch ( final Exception l_exception )
        {
            LOGGER.warning( l_exception.toString() );
        }

        // if position is not changed run not-moved plan
        if ( m_position.equals( l_postion ) )
            this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "movement/standstill" ) ) );

        // update sprite for painting (sprit position is x/y position, but position storing is row / column)
        if ( m_sprite != null )
            m_sprite.setPosition( (float) m_position.get( 1 ), (float) m_position.get( 0 ) );

        return (T) this;
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
        return m_goal;
    }

    @Override
    public final Stream<Map.Entry<String, Double>> preferences()
    {
        return this.getBeliefBase().stream( CPath.from( "preference" ) )
                   .map( i -> new AbstractMap.SimpleImmutableEntry<String, Double>(
                       i.getFQNFunctor().getSuffix(),
                       CCommon.<Double, ITerm>getRawValue(
                           i.orderedvalues()
                            .findFirst()
                            .get()
                       )
                   ) );

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
     * changes the current viewpoint
     *
     * @param p_row row position
     * @param p_column column position
     */
    @IAgentActionAllow
    @IAgentActionName( name = "goal/set" )
    protected final void viewpointset( final Number p_row, final Number p_column )
    {
        m_goal.set( 0, p_row.intValue() );
        m_goal.set( 1, p_column.intValue() );
        m_environment.clip( m_goal );
    }

    /**
     * creates a new viewpoint depend on the
     * distance around the current position
     *
     * @param p_radius distance (in cells)
     */
    @IAgentActionAllow
    @IAgentActionName( name = "goal/random" )
    protected final void viewpointrandom( final Number p_radius )
    {
        if ( p_radius.intValue() < 1 )
            throw new RuntimeException( "radius must be greater than zero" );

        m_goal.set( 0, m_position.getQuick( 0 ) + m_random.nextInt( p_radius.intValue() * 2 ) - p_radius.intValue() );
        m_goal.set( 1, m_position.getQuick( 1 ) + m_random.nextInt( p_radius.intValue() * 2 ) - p_radius.intValue() );

        m_environment.clip( m_goal );
    }

    /**
     * move forward into viewpoint direction
     */
    @IAgentActionName( name = "move/forward" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void moveforward()
    {
        this.move( EDirection.FORWARD );
    }

    /**
     * move left forward into viewpoint direction
     */
    @IAgentActionName( name = "move/forwardright" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void moveforwardright()
    {
        this.move( EDirection.FORWARDRIGHT );
    }

    /**
     * move right to the viewpoint direction
     */
    @IAgentActionName( name = "move/right" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void moveright()
    {
        this.move( EDirection.RIGHT );
    }

    /**
     * move backward right from viewpoint direction
     */
    @IAgentActionName( name = "move/backwardright" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void movebackwardright()
    {
        this.move( EDirection.BACKWARDRIGHT );
    }

    /**
     * move backward from viewpoint direction
     */
    @IAgentActionName( name = "move/backward" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void movebackward()
    {
        this.move( EDirection.BACKWARD );
    }

    /**
     * move backward right from viewpoint direction
     */
    @IAgentActionName( name = "move/backwardleft" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void movebackwardleft()
    {
        this.move( EDirection.BACKWARDLEFT );
    }

    /**
     * move left to the viewpoint
     */
    @IAgentActionName( name = "move/left" )
    @IAgentActionAllow( classes = CMovingAgent.class )
    protected final void moveleft()
    {
        this.move( EDirection.LEFT );
    }

    /**
     * move forward left into viewpoint direction
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
        if ( !this.equals( m_environment.position( this, p_direction.position( m_position, m_goal, m_speed ) ) ) )
            throw new RuntimeException( MessageFormat.format( "cannot move {0}", p_direction ) );
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
        m_sprite = this.visualization( p_rows, p_columns, p_cellsize );
        return m_sprite;
    }

    /**
     * returns the sprit object for painting
     *
     * @param p_rows row number
     * @param p_columns column number
     * @param p_cellsize cell size
     * @return sprite object
     */
    protected abstract Sprite visualization( final int p_rows, final int p_columns, final int p_cellsize );

}
