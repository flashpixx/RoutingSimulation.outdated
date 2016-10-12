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


package org.lightjason.examples.pokemon.simulation.algorithm.force.potential.scale;


/**
 * scales a value within in the range [0,max] in
 * [0,0.5*max) to 1 (positiv potential) and
 * [0.5*max,max] to -1 (negative potential)
 * based on a sigmoid function
 *
 * @see https://en.wikipedia.org/wiki/Sigmoid_function
 */
public final class CPositiveNegative implements IScale
{
    /**
     * inflection point of the sigmoid function
     */
    private final double m_inflectionpoint;
    /**
     * sigmoid value of the maximum
     * for scaling
     */
    private final double m_resultmaximum;
    /**
     * gradient of the sigmoid function
     */
    private final double m_gradient;


    /**
     * ctor
     *
     * @param p_maximum maximum value
     */
    public CPositiveNegative( final double p_maximum )
    {
        this( p_maximum, 1 );
    }

    /**
     * ctor
     *
     * @param p_maximum maximum value
     * @param p_gradient gradient of the sigmoid
     */
    public CPositiveNegative( final double p_maximum, final double p_gradient )
    {
        this( p_maximum, p_gradient, p_maximum / 2 );
    }


    /**
     * ctor
     *
     * @param p_maximum maximum value
     * @param p_gradient gradient of the sigmoid function
     * @param p_inflectionpoint inflection point of the sigmoide function
     */
    public CPositiveNegative( final double p_maximum, final double p_gradient, final double p_inflectionpoint )
    {
        m_gradient = p_gradient;
        m_inflectionpoint = p_inflectionpoint;
        m_resultmaximum = this.apply( p_maximum, 1.0 );
    }

    @Override
    public final Double apply( final Double p_metric, final Double p_potential )
    {
        return p_potential * ( ( -1 / ( 1 + Math.exp( -m_gradient * ( p_metric - m_inflectionpoint ) ) ) ) / m_resultmaximum );
    }
}
