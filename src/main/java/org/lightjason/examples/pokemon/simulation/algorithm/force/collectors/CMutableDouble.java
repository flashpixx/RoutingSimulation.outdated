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

/**
 * mutable double structure
 */
final class CMutableDouble extends Number
{
    /**
     * value
     */
    private Double m_value = 0.0;

    /**
     * ctor
     */
    public CMutableDouble()
    {}

    /**
     * ctor
     * @param p_value initialize value
     */
    public CMutableDouble( final double p_value )
    {
        m_value = p_value;
    }

    /**
     * set value
     * @param p_value double value
     * @return mutable value
     */
    public final CMutableDouble set( final Number p_value )
    {
        m_value = p_value.doubleValue();
        return this;
    }

    @Override
    public final int intValue()
    {
        return m_value.intValue();
    }

    @Override
    public final long longValue()
    {
        return m_value.longValue();
    }

    @Override
    public final float floatValue()
    {
        return m_value.floatValue();
    }

    @Override
    public final double doubleValue()
    {
        return m_value;
    }
}
