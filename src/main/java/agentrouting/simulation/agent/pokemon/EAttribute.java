/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason Gridworld                                      #
 * # Copyright (c) 2015-16, Philipp Kraus (philipp.kraus@tu-clausthal.de)               #
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

package agentrouting.simulation.agent.pokemon;

import agentrouting.simulation.agent.EAccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * individual attributes of an pokemon agent
 */
public enum EAttribute
{
    ENERGY( EAccess.READ ),
    VITALITY( EAccess.READ ),
    // set daze also to mind
    MIND( EAccess.READ ),
    HEALTH( EAccess.READ ),
    EXPERIENCE( EAccess.READ ),
    ATTACK( EAccess.READ ),
    DEFENSE( EAccess.READ ),
    WEIGHT( EAccess.READ ),
    HEIGHT( EAccess.READ ),
    MAXIMUMSPEED( EAccess.READ ),
    SPEED( EAccess.WRITE );

    /**
     * string name of enums for existance checking
     */
    private static final Set<String> NAMES;

    static
    {
        NAMES = Collections.unmodifiableSet( Arrays.stream( EAttribute.values() ).map( i -> i.toString().toLowerCase() ).collect( Collectors.toSet() ) );
    }

    /**
     * access to change by the agent
     */
    private final EAccess m_access;

    /**
     * ctor
     * @param p_access agent access
     */
    EAttribute( final EAccess p_access )
    {
        m_access = p_access;
    }

    /**
     * returns the access
     * @return access
     */
    public final EAccess access()
    {
        return m_access;
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
