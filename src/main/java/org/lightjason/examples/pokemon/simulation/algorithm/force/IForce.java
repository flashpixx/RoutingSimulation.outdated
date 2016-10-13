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

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;


/**
 * interface for force calculation
 */
public interface IForce<T>
{
    /**
     * returns the potential metric function,
     * to calculate a value between objects
     *
     * @return metric function
     */
    Function<T, Double> metric();

    /**
     * returns a potential function
     * of the object
     *
     * @return potential function
     */
    UnaryOperator<Double> potential();


    /**
     * returns a potential scaling
     * function of the object
     *
     * @return scaling function (first parameter is metric value, second potential value)
     */
    BiFunction<Double, Double, Double> potentialscale();


    /**
     * returns a potential reduction function,
     * to reduce a set of potential values into
     * a single value
     *
     * @return reduction function to reduce a set
     * of (scaled) potential values into a single value
     */
    Collector<Double, ?, Double> potentialreduce();


    /**
     * returns a scaling function to
     * scale vale based on the physical
     * distance of objects
     *
     * @return scale function
     */
    BiFunction<T, T, Double> distancescale();


    /**
     * returns a reduction function
     * to reduce a set of (scaled) forces into
     * a single force value
     *
     * @return reduction function to reduce a set
     * of obj
     */
    Collector<Double, ?, Double> forceresult();

}
