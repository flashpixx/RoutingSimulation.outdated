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


/**
 * force model factory
 */
public enum EForceModelFactory
{
    DEFAULT( new CDefaultForceModell<IElement>() );

    /**
     * force model instance
     */
    private final IForceModel<IElement> m_modell;


    /**
     * ctor
     *
     * @param p_modell element force model
     */
    EForceModelFactory( final IForceModel<IElement> p_modell )
    {
        m_modell = p_modell;
    }

    /**
     * returns the force modell
     *
     * @return force modell
     */
    public IForceModel<IElement> get()
    {
        return m_modell;
    }

}
