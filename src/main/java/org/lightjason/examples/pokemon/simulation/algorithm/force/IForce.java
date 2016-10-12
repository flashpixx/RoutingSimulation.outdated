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

package org.lightjason.examples.pokemon.simulation.algorithm.force;

/**
 * interface for force calculation
 */
public interface IForce<T>
{
    /**
     * returns the potential metric function
     *
     * @return metric function
     */
    IPotentialMetric<T> metric();

    /**
     * returns a potential function
     * of the object
     *
     * @return potential function
     */
    IPotential potential();


    /**
     * returns a potential scaling
     * function of the object
     *
     * @return scaling function
     */
    IPotentialScale potentialscale();


    /**
     * returns a potential reduction function,
     * to reduce a set of potential values into
     * a single value
     *
     * @return reduction function
     */
    IReduce potentialreduce();


    /**
     * returns a object scaling function
     *
     * @return object scale function
     */
    IObjectScale<T> objectscale();

    /**
     * returns a object reduction function
     * to reduce pairs of objects and potential
     * values to a single vlaue
     *
     * @return reduction function
     */
    IReduce objectreduce();

}
