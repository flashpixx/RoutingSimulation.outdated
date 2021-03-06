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

package org.lightjason.examples.pokemon.simulation.algorithm.routing;


/**
 * factory for creating routing objects
 */
public enum ERoutingFactory
{
    JPSPLUS( new CJPSPlus() );

    /**
     * routing object
     */
    private final IRouting m_routing;

    /**
     * ctor
     *
     * @param p_routing routing object
     */
    ERoutingFactory( final IRouting p_routing )
    {
        m_routing = p_routing;
    }

    /**
     * returns routing object
     *
     * @return routing object
     */
    public final IRouting get()
    {
        return m_routing;
    }
}
