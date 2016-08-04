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


package agentrouting.simulation.agent.pokemon;

import agentrouting.simulation.agent.IAgent;
import agentrouting.simulation.agent.IBaseAgent;
import agentrouting.simulation.environment.IEnvironment;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.util.Map;


/**
 * BDI agent for dynamic / moving elements
 * @todo add pokemon name
 */
public final class CPokemon extends IBaseAgent
{
    /**
     * pokemon type
     */
    private final EPokemon m_pokemon;
    /**
     * preference map
     */
    private final Map<EPreferences, Number> m_preferences;


    /**
     * ctor
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_position initialize position
     * @param p_force force model
     * @param p_pokemon pokemon name
     */
    public CPokemon( final IEnvironment p_environment, final IAgentConfiguration<IAgent> p_agentconfiguration,
                     final DoubleMatrix1D p_position, final IForce p_force, final String p_pokemon
    )
    {
        super( p_environment, p_agentconfiguration, p_force, p_position );

        if ( p_pokemon.isEmpty() )
            throw new RuntimeException( "pokemon name need not to be empty" );

        m_pokemon = EPokemon.valueOf( p_pokemon.trim().toUpperCase() );
        m_preferences = m_pokemon.generateCharacteristic();
    }

    @Override
    public final void spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit )
    {
        m_sprite = new Sprite( m_pokemon.initialize() );
        m_sprite.setSize( p_cellsize, p_cellsize );
        m_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );
        m_sprite.setScale( p_unit );
    }

}
