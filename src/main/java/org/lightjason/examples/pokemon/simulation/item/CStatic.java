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

package org.lightjason.examples.pokemon.simulation.item;

import java.util.List;
import java.util.Map;


/**
 * static elements
 */
public final class CStatic extends IBaseItem
{

    /**
     * ctor
     *
     * @param p_leftupper left-upper position
     * @param p_rightbottom right-bottom position
     * @param p_color color
     */
    public CStatic( final List<Integer> p_leftupper, final List<Integer> p_rightbottom,
                    final String p_color
    )
    {
        super( p_leftupper, p_rightbottom, p_color );
    }


    /**
     * ctor
     *
     * @param p_leftupper left-upper position
     * @param p_rightbottom right-bottom position
     * @param p_color color
     * @param p_attribute map with attributes
     */
    public CStatic( final List<Integer> p_leftupper, final List<Integer> p_rightbottom,
                    final String p_color, final Map<String, ?> p_attribute
    )
    {
        super( p_leftupper, p_rightbottom, p_color, p_attribute );
    }

    @Override
    public final IItem call() throws Exception
    {
        return this;
    }
}
