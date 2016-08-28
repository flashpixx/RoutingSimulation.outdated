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

import com.codepoetics.protonpack.StreamUtils;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.examples.pokemon.CCommon;
import org.lightjason.examples.pokemon.simulation.CMath;
import org.lightjason.examples.pokemon.simulation.IElement;
import org.lightjason.examples.pokemon.simulation.agent.IAgent;
import org.lightjason.examples.pokemon.simulation.agent.pokemon.CPokemon;
import org.lightjason.examples.pokemon.simulation.agent.pokemon.CPokemonGenerator;
import org.lightjason.examples.pokemon.simulation.algorithm.routing.ERoutingFactory;
import org.lightjason.examples.pokemon.simulation.environment.CEnvironment;
import org.lightjason.examples.pokemon.simulation.environment.IEnvironment;
import org.lightjason.examples.pokemon.simulation.item.CStatic;
import org.lightjason.examples.pokemon.simulation.item.IItem;

import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * test of force algorithm
 */
public final class TestCForce
{
    /**
     * number of agents for generating
     */
    private static final int AGENTNUMBER = 2;
    /**
     * number of static objects
     */
    private static final int STATICNUMBER = 2;
    /**
     * list with agents
     */
    private List<IAgent> m_agent;
    /**
     * environment instance
     */
    private IEnvironment m_environment;
    /**
     * action references
     */
    private Set<IAction> m_actions;
    /**
     * static attributes
     */
    private List<IElement> m_element;


    /**
     * unit-test initialize
     *
     * @throws Exception is thrown on any error
     */
    @Before
    public void initialize() throws Exception
    {
        // disable logging
        LogManager.getLogManager().reset();

        m_environment = new CEnvironment( 100, 100, 25, ERoutingFactory.JPSPLUS.get(), Collections.<IItem>emptyList() );

        m_actions = Collections.unmodifiableSet( Stream.concat(
            org.lightjason.agentspeak.common.CCommon.actionsFromPackage(),
            org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CPokemon.class )
        ).collect( Collectors.toSet() ) );

        // generate agents
        m_agent = Collections.unmodifiableList(
            new CPokemonGenerator(
                m_environment,
                TestCForce.class.getResourceAsStream( MessageFormat.format( "/{0}/agent.asl", CCommon.PACKAGEPATH ) ),
                m_actions,
                IAggregation.EMPTY
            )
                .generatemultiple( AGENTNUMBER, EForceFactory.SUM.get(), "gastly" )
                .collect( Collectors.toList() )
        );

        // generate static elements
        m_element = Collections.unmodifiableList(
            IntStream.range( 0, STATICNUMBER )
                     .mapToObj( j -> new CStatic(

                                    IntStream.range( 0, 2 ).boxed().map( i -> CMath.RANDOMGENERATOR.nextInt() ).collect( Collectors.toList() ),
                                    IntStream.range( 0, 2 ).boxed().map( i -> CMath.RANDOMGENERATOR.nextInt() ).collect( Collectors.toList() ),
                                    "000000",

                                    StreamUtils.zip(
                                        Stream.of( "foo", "bar" ),
                                        Stream.of( "hallo", 3 ),
                                        AbstractMap.SimpleImmutableEntry::new
                                    ).collect(
                                        Collectors.toMap(
                                            AbstractMap.SimpleImmutableEntry::getKey,
                                            AbstractMap.SimpleImmutableEntry::getValue
                                        )
                                    )

                                )
                     ).collect( Collectors.toList() )
        );
    }

    /**
     * force test
     */
    @Test
    public void testForce()
    {
        Assume.assumeNotNull( m_environment );
        Assume.assumeNotNull( m_actions );
        Assume.assumeNotNull( m_agent );
        Assume.assumeTrue( m_agent.isEmpty() );
        Assume.assumeNotNull( m_element );
        Assume.assumeTrue( m_element.isEmpty() );

        System.out.println( m_element.get( 0 ).attribute().collect( Collectors.toSet() ) );
        System.out.println( m_agent.get( 0 ).attribute().collect( Collectors.toSet() ) );
        System.out.println( m_agent.get( 0 ).beliefbase().stream().collect( Collectors.toSet() ) );
    }



    /**
     * main method to test force algorithms
     *
     * @param p_args CLI command
     */
    public static void main( final String[] p_args )
    {

    }

}
