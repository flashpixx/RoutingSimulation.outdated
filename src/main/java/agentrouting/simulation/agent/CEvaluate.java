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

package agentrouting.simulation.agent;

import org.lightjason.agentspeak.consistency.CConsistency;
import org.lightjason.agentspeak.consistency.filter.CBelief;
import org.lightjason.agentspeak.consistency.filter.CPlan;
import org.lightjason.agentspeak.consistency.metric.CWeightedDifference;

import java.util.concurrent.Callable;


/**
 * calculates the consistency values
 */
public class CEvaluate implements Callable<CEvaluate>
{
    /**
     * consistency of the beliefs
     */
    private final CConsistency m_consistencybelief = new CConsistency( new CBelief(), new CWeightedDifference(), 5, 0.001 );
    /**
     * consistency of the plans
     */
    private final CConsistency m_consistencyplan = new CConsistency( new CPlan(), new CWeightedDifference(), 5, 0.001 );

    @Override
    public final CEvaluate call() throws Exception
    {
        m_consistencyplan.call();
        m_consistencybelief.call();

        return this;
    }
}
