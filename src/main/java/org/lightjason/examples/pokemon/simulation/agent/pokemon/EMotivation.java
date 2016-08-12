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

package org.lightjason.examples.pokemon.simulation.agent.pokemon;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * enum for defining the motivation value of an pokemon agent
 */
public enum EMotivation
{
    HUNT,
    FIGHT,
    HELP,
    IGNORANCE,
    CAUTIOUSNESS,
    ELOPEMENT,
    CURE;

    /**
     * string name of enums for existance checking
     */
    private static final Set<String> NAMES;

    static
    {
        NAMES = Collections.unmodifiableSet( Arrays.stream( EMotivation.values() ).map( i -> i.toString().toLowerCase() ).collect( Collectors.toSet() ) );
    }


    /**
     * checkes if a enum exists with a string name
     *
     * @param p_name name
     * @return existance
     */
    public static boolean exist( final String p_name )
    {
        return NAMES.contains( p_name.trim().toLowerCase() );
    }

}
