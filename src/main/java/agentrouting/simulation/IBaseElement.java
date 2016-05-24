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

import agentrouting.simulation.agent.IAgent;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.tint.IntMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import lightjason.agentspeak.agent.CAgent;
import lightjason.agentspeak.common.CPath;
import lightjason.agentspeak.configuration.IAgentConfiguration;
import lightjason.agentspeak.language.CCommon;
import lightjason.agentspeak.language.ITerm;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;


/**
 * abstract class for all preferences access
 */
public abstract class IBaseElement<T> extends CAgent<IElement<T>> implements IElement<T>
{
    /**
     * current position of the agent
     */
    protected final IntMatrix1D m_position;
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
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_force force model
     * @param p_position initial position
     */
    protected IBaseElement( final IEnvironment p_environment, final IAgentConfiguration<IElement<T>> p_agentconfiguration,
                            final IForce p_force, final IntMatrix1D p_position
    )
    {
        super( p_agentconfiguration );
        m_environment = p_environment;
        m_position = p_position;
        m_force = p_force;
    }

    @Override
    public final IntMatrix1D position()
    {
        return m_position;
    }

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

    @Override
    @SuppressWarnings( "unchecked" )
    public final T execute( final int p_step )
    {
        // run agent-cycle
        try
        {
            super.call();
        }
        catch ( final Exception l_exception )
        {
            LOGGER.warning( l_exception.getMessage() );
        }

        // update sprite for painting (sprit position is x/y position, but position storing is row / column)
        if ( m_sprite != null )
            m_sprite.setPosition( m_position.get( 1 ), m_position.get( 0 ) );

        return (T) this;
    }

    @Override
    public final Stream<Map.Entry<String, Double>> preferences()
    {
        return this.getBeliefBase().stream( CPath.from( "preference" ) )
                   .map( i -> new AbstractMap.SimpleImmutableEntry<String, Double>(
                           i.getFQNFunctor().getSuffix(),
                           CCommon.<Double, ITerm>getRawValue( i.orderedvalues().findFirst().get() )
                   ) );

    }

    /**
     * updates the interal position of the agent, also within the environment
     * by the weighted fitness probability selection
     *
     * @param p_direction defied as an enum structure [ 7 0 1; 6 X 2; 5 4 3], so 0 -> straight forward (top) and than clockwise
     * @param p_distance distance to the target field (jumping to a position is possible)
     * @todo check cells if p_distance > 1
     * @see https://en.wikipedia.org/wiki/Fitness_proportionate_selection to calculate the direction
     */
    protected final IElement<?> updateposition( final Map<EDirection, Double> p_direction, final int p_distance )
    {
        /*
        return m_environment.position(
                this,
                EDirection.FORWARD.position( m_position, p_distance )
        );
        */
        return this;
    }


    /**
     * create the sprite for painting
     *
     * @param p_rows row number
     * @param p_columns column number
     * @param p_cellsize cell size
     * @return sprite object
     */
    protected abstract Sprite visualization( final int p_rows, final int p_columns, final int p_cellsize );

}
