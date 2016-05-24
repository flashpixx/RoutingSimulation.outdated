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

import lightjason.agentspeak.action.IAction;
import lightjason.agentspeak.agent.CAgent;
import lightjason.agentspeak.agent.IPlanBundle;
import lightjason.agentspeak.beliefbase.IBeliefBaseUpdate;
import lightjason.agentspeak.generator.CDefaultAgentGenerator;
import lightjason.agentspeak.language.execution.IVariableBuilder;
import lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;


/**
 * agent generator
 */
public final class CAgentGenerator extends CDefaultAgentGenerator<IAgent>
{

    /**
     * @param p_stream input asl stream
     * @param p_actions action set
     * @param p_aggregation aggregation set
     * @param p_planbundle plan bundle set
     * @param p_beliefbaseupdate beliefbase updater
     * @param p_variablebuilder variable builder
     * @throws Exception on any error
     */
    public CAgentGenerator( final InputStream p_stream, final Set<IAction> p_actions, final IAggregation p_aggregation,
                            final Set<IPlanBundle> p_planbundle,
                            final IBeliefBaseUpdate<IAgent> p_beliefbaseupdate,
                            final IVariableBuilder p_variablebuilder
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation, p_planbundle, p_beliefbaseupdate, p_variablebuilder );
    }

    @Override
    public lightjason.agentspeak.agent.IAgent<IAgent> generate( final Object... p_data ) throws Exception
    {
        return new CAgent<>( m_configuration );
    }
}
