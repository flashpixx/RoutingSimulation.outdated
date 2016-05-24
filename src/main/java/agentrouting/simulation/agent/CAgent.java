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

import agentrouting.simulation.IEnvironment;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.tint.IntMatrix1D;
import lightjason.agentspeak.configuration.IAgentConfiguration;


/**
 * BDI agent
 */
public class CAgent extends IBaseAgent
{

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_position initialize position
     * @param p_force force model
     * @param p_name agent name
     * @param p_color color string in RRGGBBAA
     */
    public CAgent( final IEnvironment p_environment, final IAgentConfiguration p_agentconfiguration,
                   final IntMatrix1D p_position, final IForce p_force, final String p_name, final String p_color
    )
    {
        super( p_environment, p_agentconfiguration, p_force, p_position, p_name, p_color );
        //Arrays.stream( EDirection.values() ).forEach( i -> m_directionprobability.put( i, 1.0 / EDirection.values().length ) );
    }

}
