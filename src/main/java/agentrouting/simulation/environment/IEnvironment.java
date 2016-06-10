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

package agentrouting.simulation.environment;


import agentrouting.simulation.IElement;
import agentrouting.simulation.agent.IAgent;
import agentrouting.ui.ITileMap;
import cern.colt.matrix.DoubleMatrix1D;
import org.lightjason.agentspeak.beliefbase.IBeliefPerceive;

import java.util.List;
import java.util.concurrent.Callable;


/**
 * environment interface
 */
public interface IEnvironment extends IBeliefPerceive<IAgent>, Callable<IEnvironment>, ITileMap
{

    /**
     * calculate route
     *
     * @param p_object element
     * @param p_target target point
     * @return list of tuples of the cellindex
     */
    List<DoubleMatrix1D> route( final IElement<?> p_object, final DoubleMatrix1D p_target );

    /**
     * sets an object to the position and changes the object position
     *
     * @param p_object object, which should be moved (must store the current position)
     * @param p_position new position
     * @return updated object or object which uses the cell
     */
    IElement<?> position( final IElement<?> p_object, final DoubleMatrix1D p_position );

    /**
     * clip position data
     *
     * @param p_position position vector
     * @return modified clipped vector
     */
    DoubleMatrix1D clip( final DoubleMatrix1D p_position );

    /**
     * returns the number of rows
     *
     * @return rows
     */
    int row();

    /**
     * returns the number of columns
     *
     * @return columns
     */
    int column();

    /**
     * returns the cell size
     *
     * @return cell size
     */
    int cellsize();

    /**
     * run initialization of the environment
     *
     * @return self reference
     */
    IEnvironment initialize();

}
