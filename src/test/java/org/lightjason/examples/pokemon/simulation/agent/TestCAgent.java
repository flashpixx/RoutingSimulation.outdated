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

import org.lightjason.examples.pokemon.simulation.agent.pokemon.CPokemon;
import org.lightjason.examples.pokemon.simulation.agent.pokemon.CPokemonGenerator;
import org.lightjason.examples.pokemon.simulation.environment.CEnvironment;
import org.lightjason.examples.pokemon.simulation.environment.IEnvironment;
import org.lightjason.examples.pokemon.simulation.algorithm.force.EForceFactory;
import org.lightjason.examples.pokemon.simulation.algorithm.routing.ERoutingFactory;
import org.lightjason.examples.pokemon.simulation.item.IItem;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.util.Collections;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * agent test
 */
public final class TestCAgent
{
    /**
     * environment reference
     */
    private IEnvironment m_environment;
    /**
     * action references
     */
    private Set<IAction> m_actions;


    /**
     * data initialization
     */
    @Before
    public void initialize()
    {
        // disable logging
        LogManager.getLogManager().reset();

        m_environment = new CEnvironment( 100, 100, 25, ERoutingFactory.JPSPLUS.get(), Collections.<IItem>emptyList() );

        m_actions = Collections.unmodifiableSet( Stream.concat(
            org.lightjason.agentspeak.common.CCommon.actionsFromPackage(),
            org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CPokemon.class )
        ).collect( Collectors.toSet() ) );
    }


    /**
     * test moving agent generator
     * @throws Exception throws any exceptions
     */
    @Test
    public void testMovingAgentGenerator() throws Exception
    {
        Assume.assumeNotNull( m_environment );
        Assume.assumeNotNull( m_actions );
        new CPokemonGenerator(
            m_environment,
            TestCAgent.class.getResourceAsStream( "/org/lightjason/examples/pokemon/agent.asl" ),
            m_actions,
            IAggregation.EMPTY
        ).generatesingle( EForceFactory.SUM.get(), "eevee" );
    }


}
