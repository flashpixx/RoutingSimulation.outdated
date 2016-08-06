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

import agentrouting.simulation.agent.IAgent;
import agentrouting.simulation.agent.IBaseAgent;
import agentrouting.simulation.environment.IEnvironment;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IPlanBundle;
import org.lightjason.agentspeak.agent.fuzzy.IFuzzy;
import org.lightjason.agentspeak.beliefbase.CBeliefbasePersistent;
import org.lightjason.agentspeak.beliefbase.storage.CMultiStorage;
import org.lightjason.agentspeak.beliefbase.storage.CSingleStorage;
import org.lightjason.agentspeak.beliefbase.storage.IBeliefPerceive;
import org.lightjason.agentspeak.beliefbase.view.IView;
import org.lightjason.agentspeak.beliefbase.view.IViewGenerator;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.CDefaultAgentConfiguration;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.execution.action.unify.IUnifier;
import org.lightjason.agentspeak.language.instantiable.plan.IPlan;
import org.lightjason.agentspeak.language.instantiable.rule.IRule;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Set;


/**
 * agent generator for dynamic / moving agents
 */
public final class CPokemonGenerator extends IBaseAgentGenerator<IAgent>
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
    public CPokemonGenerator( final IEnvironment p_environment, final InputStream p_stream,
                              final Set<IAction> p_actions, final IAggregation p_aggregation
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation, Collections.<IPlanBundle>emptySet(), Collections.<IBeliefPerceive<IAgent>>emptySet(), IVariableBuilder.EMPTY );
        m_environment = p_environment;
    }


    @Override
    protected final IAgentConfiguration<IAgent> configuration( final IFuzzy<Boolean, IAgent> p_fuzzy, final Collection<ILiteral> p_initalbeliefs,
                                                               final Set<IBeliefPerceive<IAgent>> p_beliefperceive, final Set<IPlan> p_plans, final Set<IRule> p_rules,
                                                               final ILiteral p_initialgoal, final IUnifier p_unifier, final IAggregation p_aggregation,
                                                               final IVariableBuilder p_variablebuilder
    )
    {
        return new CAgentConfiguration( p_fuzzy, p_initalbeliefs, p_beliefperceive, p_plans, p_rules, p_initialgoal, p_unifier, p_aggregation, p_variablebuilder );
    }

    @Override
    public IAgent generatesingle( final Object... p_data )
    {
        final DenseDoubleMatrix1D l_position = new DenseDoubleMatrix1D( new double[]{m_random.nextInt( m_environment.row() ), m_random.nextInt( m_environment.column() )} );
        while ( !m_environment.empty( l_position ) )
        {
            l_position.setQuick( 0, m_random.nextInt( m_environment.row() ) );
            l_position.setQuick( 1, m_random.nextInt( m_environment.column() ) );
        }

        return new CPokemon(
            m_environment,
            m_configuration,

            l_position,

            (IForce) p_data[0],

            (String) p_data[1]
        );
    }


    /**
     * agent configuration
     */
    private static final class CAgentConfiguration extends CDefaultAgentConfiguration<IAgent>
    {

        /**
         * ctor
         *
         * @param p_fuzzy fuzzy operator
         * @param p_initalbeliefs set with initial beliefs
         * @param p_beliefperceive belief perceiver object
         * @param p_plans plans
         * @param p_rules rules
         * @param p_initialgoal initial goal
         * @param p_aggregation aggregation function
         * @param p_unifier unifier component
         * @param p_variablebuilder variable builder
         */
        CAgentConfiguration( final IFuzzy<Boolean, IAgent> p_fuzzy, final Collection<ILiteral> p_initalbeliefs,
                                    final Set<IBeliefPerceive<IAgent>> p_beliefperceive, final Set<IPlan> p_plans,
                                    final Set<IRule> p_rules, final ILiteral p_initialgoal, final IUnifier p_unifier, final IAggregation p_aggregation,
                                    final IVariableBuilder p_variablebuilder
        )
        {
            super( p_fuzzy, p_initalbeliefs, p_beliefperceive, p_plans, p_rules, p_initialgoal, p_unifier, p_aggregation, p_variablebuilder );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public final IView<IAgent> beliefbase()
        {
            // generate root beliefbase - the beliefbase with the local environment-data is generated within the agent-ctor
            final IView<IAgent> l_beliefbase = new CBeliefbasePersistent<>( new CMultiStorage<ILiteral, IView<IAgent>, IAgent>() )
                                                .create( BELIEFBASEROOTNAME )

                                                // generates beliefbase with individual preferences
                                                .generate( new IViewGenerator<IAgent>()
                                                {
                                                    @Override
                                                    public final IView<IAgent> generate( final String p_name, final IView<IAgent> p_parent )
                                                    {
                                                        return new CBeliefbasePersistent<IAgent>( new CSingleStorage<>() ).create( p_name, p_parent );
                                                    }
                                                },
                                                CPath.from( IBaseAgent.PREFERENCE )
                                                );

            m_initialbeliefs.parallelStream().forEach( i -> l_beliefbase.add( i.shallowcopy() ) );
            return l_beliefbase;
        }

    }

}
