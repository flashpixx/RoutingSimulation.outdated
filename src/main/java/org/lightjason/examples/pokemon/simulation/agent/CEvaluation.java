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

package org.lightjason.examples.pokemon.simulation.agent;

import org.lightjason.agentspeak.consistency.CConsistency;
import org.lightjason.agentspeak.consistency.IConsistency;
import org.lightjason.agentspeak.consistency.filter.CPlan;
import org.lightjason.agentspeak.consistency.metric.CWeightedDifference;

import java.util.concurrent.Callable;
import java.util.stream.Stream;


/**
 * structure to calculate the consistency values
 */
public final class CEvaluation implements Callable<CEvaluation>
{
    /**
     * concsitency value of executable plans
     */
    private final IConsistency m_plans = CConsistency.heuristic( new CPlan(), new CWeightedDifference() );

    /**
     * ctor
     *
     * @param p_agents agent stream
     */
    public CEvaluation( final Stream<IAgent> p_agents )
    {
        p_agents.forEach( m_plans::add );
    }


    @Override
    public final CEvaluation call() throws Exception
    {
        m_plans.call();
        System.out.println( m_plans.statistic().getStandardDeviation() + "    " + m_plans.statistic().getVariance() );

        return this;
    }
}
