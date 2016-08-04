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

package agentrouting.simulation.algorithm.routing;



import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

import java.util.List;


/**
 * routing interface
 */
public interface IRouting
{

    /**
     * runs the initialization process from the environment
     *
     * @param p_objects full initialized environment grid (static elements)
     * @return self reference
     */
    IRouting initialize( final ObjectMatrix2D p_objects );

    /**
     * routing algorithm
     *
     * @param p_objects object matrix
     * @param p_source current position
     * @param p_target target position
     * @return list of tuples of the cellindex
     * @todo change list to a queue
     */
    List<DoubleMatrix1D> route( final ObjectMatrix2D p_objects, final DoubleMatrix1D p_source, final DoubleMatrix1D p_target );


    /**
     * calculated the estimated time to move the path
     *
     * @param p_route route list
     * @param p_speed estimated speed
     * @return speed of the full path
     */
    double estimatedtime( final List<DoubleMatrix1D> p_route, final double p_speed );


}
