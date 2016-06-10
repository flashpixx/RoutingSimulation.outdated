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

import agentrouting.ui.ISprite;
import cern.colt.matrix.DoubleMatrix1D;

import java.util.Map;
import java.util.stream.Stream;


/**
 * interface of a basic element that can be
 * executable within the simulation (so position
 * and preferences are required)
 */
public interface IElement<T> extends ISprite
{

    /**
     * returns a stream of all preferences
     *
     * @return preference stream
     */
    Stream<Map.Entry<String, Double>> preferences();

    /**
     * returns the current position of the object
     *
     * @return position tupel
     */
    DoubleMatrix1D position();

}
