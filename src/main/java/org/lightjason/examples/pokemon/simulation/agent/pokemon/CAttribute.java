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

import org.lightjason.examples.pokemon.simulation.agent.EAccess;

import java.text.MessageFormat;


/**
 * attribute class
 */
public final class CAttribute
{
    /**
     * name of the attribute
     */
    private final String m_name;
    /**
     * access
     */
    private final EAccess m_access;

    /**
     * ctor
     *
     * @param p_name name
     * @param p_access access
     */
    public CAttribute( final String p_name, final EAccess p_access )
    {
        m_name = p_name.trim().toLowerCase();
        m_access = p_access;
    }

    /**
     * returns the name of the attribute
     *
     * @return attribute name
     */
    public final String name()
    {
        return m_name;
    }

    /**
     * returns attribute access
     *
     * @return access
     */
    public final EAccess access()
    {
        return m_access;
    }

    @Override
    public final int hashCode()
    {
        return m_name.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof CAttribute ) && ( this.hashCode() == p_object.hashCode() );
    }

    @Override
    public final String toString()
    {
        return MessageFormat.format( "attribute( {0} - {1} )", m_name, m_access );
    }
}
