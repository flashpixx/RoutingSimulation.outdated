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

import org.lightjason.examples.pokemon.simulation.IElement;

import java.util.stream.Stream;


/**
 * sum force value
 */
final class CSum implements IForce
{
    @Override
    public final double calculate( final IElement p_self, final Stream<IElement> p_other )
    {
        return p_other.parallel()
                      .mapToDouble( i -> elementwise( p_self, i ) )
                      .sum();
    }

    /**
     * calculate the force between two elements
     *
     * @param p_self first element
     * @param p_other other element
     * @return force
     */
    private static double elementwise( final IElement p_self, final IElement p_other )
    {
        /*
        return Stream.concat(
                p_self.preference().stream(),
                p_other.preference().stream()
        )
                     // create a sorted distinct keyset and run in parallel
                     .sorted().distinct()
                     .parallel()
                     // sum of both preferences
                     .mapToDouble( i -> p_self.getPreference( i ) + p_other.getPreference( i ) )
                     .boxed()
                     // maximum is -2 or 2, so normalize and scale it in [-1,1]
                     .mapToDouble( i -> i / 2.0 )
                     // and return the average
                     .average().getAsDouble();
                     */
        return 0;

    }
}
