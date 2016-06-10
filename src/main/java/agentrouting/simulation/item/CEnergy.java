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

package agentrouting.simulation.item;

import agentrouting.simulation.IElement;
import agentrouting.simulation.IEnvironment;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;


/**
 * energy items
 */
public final class CEnergy
{
    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_force force model
     * @param p_position initial position
     */
    protected CEnergy( final IEnvironment p_environment,
                       final IAgentConfiguration<IElement<IItem>> p_agentconfiguration,
                       final IForce p_force,
                       final DoubleMatrix1D p_position
    )
    {
        //super( p_environment, p_agentconfiguration, p_force, p_position );
    }

    //@Override
    protected final Sprite visualization( final int p_rows, final int p_columns, final int p_cellsize )
    {
        return null;
    }

}
