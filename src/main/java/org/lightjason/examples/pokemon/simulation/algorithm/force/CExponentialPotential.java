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
 * exponential potential function
 */
public final class CExponentialPotential implements IPotential<Double>
{
    /**
     * maximum value of the metric range
     */
    private final double m_maximum;
    /**
     * scaling factor
     */
    private final double m_scale;


    /**
     * ctor
     *
     * @param p_maximum maximum value
     */
    public CExponentialPotential( final double p_maximum )
    {
        this( p_maximum, 1 );
    }

    /**
     * ctor
     *
     * @param p_maximum maximum value
     * @param p_scale scaling value
     */
    public CExponentialPotential( final double p_maximum, final double p_scale )
    {
        m_maximum = p_maximum;
        m_scale = p_scale;
    }


    @Override
    public final Double apply( final Double p_double )
    {
        return Math.exp( m_maximum - m_scale * p_double );
    }

}
