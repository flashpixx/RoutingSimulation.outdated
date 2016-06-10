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

import agentrouting.simulation.environment.IEnvironment;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IPlanBundle;
import org.lightjason.agentspeak.generator.CDefaultAgentGenerator;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Collections;
import java.util.Random;
import java.util.Set;


/**
 * agent generator for dynamic / moving agents
 */
public final class CMovingAgentGenerator extends CDefaultAgentGenerator<IAgent>
{
    /**
     * environment reference
     */
    private final IEnvironment m_environment;
    /**
     * random generator
     */
    private final Random m_random = new Random();

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_stream input asl stream
     * @param p_actions action set
     * @param p_aggregation aggregation set
     * @throws Exception on any error
     */
    public CMovingAgentGenerator( final IEnvironment p_environment, final InputStream p_stream,
                                  final Set<IAction> p_actions, final IAggregation p_aggregation
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation, Collections.<IPlanBundle>emptySet(), p_environment, IVariableBuilder.EMPTY );
        m_environment = p_environment;
    }

    @Override
    public IAgent generatesingle( final Object... p_data )
    {
        return new CMovingAgent(
            m_environment,
            m_configuration,

            new DenseDoubleMatrix1D( new double[]{m_random.nextInt( m_environment.row() ), m_random.nextInt( m_environment.column() )} ),

            (IForce) p_data[0],

            (String) p_data[1]
        );
    }
}