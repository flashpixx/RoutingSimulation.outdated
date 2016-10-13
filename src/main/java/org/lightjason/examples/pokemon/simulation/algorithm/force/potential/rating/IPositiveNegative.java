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


package org.lightjason.examples.pokemon.simulation.algorithm.force.potential.rating;


import java.util.function.BiFunction;


/**
 * scales a value within in the range [0,max] in
 * [0,0.5*max) to 1 (positiv potential) and
 * [0.5*max,max] to -1 (negative potential)
 * based on a sigmoid function
 *
 * @note the default 1 / ( 1 + exp(-x) ) must be modified for
 * a x-axis movment and y-axis scaling
 * @see https://en.wikipedia.org/wiki/Sigmoid_function
 */
public abstract class IPositiveNegative implements BiFunction<Double, Double, Double>
{

    /**
     * returns the gradient value
     * of the sigmoid function
     *
     * @return gradient value
     */
    protected abstract double gradient();


    /**
     * returns the inflection point of
     * the sigmoid function
     *
     * @return inflection point value
     */
    protected abstract double inflectionpoint();


    @Override
    public final Double apply( final Double p_metric, final Double p_potential )
    {
        return p_potential * 2 * ( 1 / ( 1 + Math.exp( -this.gradient() * ( p_metric - this.inflectionpoint() ) ) ) - 0.5 );
    }
}
