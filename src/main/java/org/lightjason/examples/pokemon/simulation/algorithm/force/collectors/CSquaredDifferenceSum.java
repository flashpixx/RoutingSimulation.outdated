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

package org.lightjason.examples.pokemon.simulation.algorithm.force.collectors;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


/**
 * squared difference sum collector function with \f$ \sum_i (a_i - b_i )^2 \f$
 */
public final class CSquaredDifferenceSum implements Collector<Double, CMutableDouble, Double>
{

    /**
     * ctor
     */
    private CSquaredDifferenceSum()
    {}

    @Override
    public final Supplier<CMutableDouble> supplier()
    {
        return CMutableDouble::new;
    }

    @Override
    public final BiConsumer<CMutableDouble, Double> accumulator()
    {
        return (i, j) -> i.set( ( i.doubleValue() - j ) * ( i.doubleValue() - j ) );
    }

    @Override
    public final BinaryOperator<CMutableDouble> combiner()
    {
        return (i, j) -> i.set( ( i.doubleValue() + j.doubleValue() ) * ( i.doubleValue() + j.doubleValue() ) );
    }

    @Override
    public final Function<CMutableDouble, Double> finisher()
    {
        return CMutableDouble::doubleValue;
    }

    @Override
    public final Set<Characteristics> characteristics()
    {
        return Collections.emptySet();
    }

    /**
     * static factory
     * @return collector instance
     */
    public static Collector<Double, ?, Double> factory()
    {
        return new CSquaredDifferenceSum();
    }

}
