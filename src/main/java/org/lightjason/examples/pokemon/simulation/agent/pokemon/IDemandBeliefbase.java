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

import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.examples.pokemon.simulation.agent.IAgent;

import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * abstract class for demand-beliefbase
 */
abstract class IDemandBeliefbase extends IBeliefbaseOnDemand<IAgent>
{
    @Override
    public ILiteral add( final ILiteral p_literal )
    {
        return p_literal;
    }

    @Override
    public final ILiteral remove( final ILiteral p_literal )
    {
        return p_literal;
    }

    /**
     * creates a literal
     *
     * @param p_key string name
     * @param p_value value
     * @return literal
     */
    final ILiteral numberliteral( final String p_key, final Number p_value )
    {
        return CLiteral.from( p_key, Stream.of( CRawTerm.from( p_value.doubleValue() ) ) );
    }

    @Override
    public final String toString()
    {
        return this.streamLiteral().collect( Collectors.toSet() ).toString();
    }
}
