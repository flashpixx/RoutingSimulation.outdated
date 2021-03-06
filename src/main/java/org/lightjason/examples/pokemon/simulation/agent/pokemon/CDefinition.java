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

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.examples.pokemon.CCommon;
import org.lightjason.examples.pokemon.CConfiguration;
import org.lightjason.examples.pokemon.data.Ilevelitem;
import org.lightjason.examples.pokemon.data.Structure;
import org.lightjason.examples.pokemon.simulation.agent.EAccess;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * pokemon definition class
 */
public final class CDefinition
{
    /**
     * singleton instance
     */
    public static final CDefinition INSTANCE = new CDefinition();
    /**
     * logger
     */
    private static final Logger LOGGER = Logger.getLogger( CConfiguration.class.getName() );
    /**
     * map with pokemon data
     */
    private final Map<String, Pair<BigInteger, List<CLevel>>> m_pokemon;

    /**
     * ctor
     */
    private CDefinition()
    {
        Structure l_structure = null;
        try
        {
            // read structure
            l_structure = (Structure) JAXBContext.newInstance( Structure.class )
                                                                 .createUnmarshaller()
                                                                 .unmarshal(
                                                                     CCommon.getResourceURL( CCommon.PACKAGEPATH + "data/character.xml" )
                                                                 );

        }
        catch ( final JAXBException | MalformedURLException | URISyntaxException l_exception )
        {
            System.out.println( l_exception );
            LOGGER.warning( l_exception.toString() );
        }

        if ( l_structure == null )
            m_pokemon = Collections.emptyMap();
        else
        {

            // read attributes
            final Map<String, CAttribute> l_attribute = Collections.unmodifiableMap(
                l_structure.getConfiguration()
                           .getAttribute()
                           .getItem()
                           .stream()
                           .map( i -> new CAttribute( i.getId(), EAccess.valueOf( i.getAgentaccess().trim().toUpperCase() ) ) )
                           .collect( Collectors.toMap( CAttribute::name, i -> i ) )
            );

            // read atacks
            final Map<String, CAttack> l_attack = Collections.unmodifiableMap(
                l_structure.getConfiguration()
                           .getAttack()
                           .getItem()
                           .stream()
                           .map( i -> new CAttack( i, l_attribute ) )
                           .collect( Collectors.toMap( CAttack::name, i -> i ) )
            );

            // read pokemon character
            m_pokemon = Collections.unmodifiableMap(
                l_structure.getCharacter()
                    .getPokemon()
                    .stream()
                    .collect(
                        Collectors.toMap(
                            i -> i.getId().trim().toLowerCase(),
                            i -> new ImmutablePair<>(

                                    i.getExperience(),

                                    Collections.unmodifiableList(
                                        IntStream.range( 0, i.getLevel().size() )
                                            .mapToObj( j -> new CLevel(
                                                                i.getId().trim().toLowerCase(),
                                                                j,

                                                                i.getLevel().get( j ).getEthnicity().stream()
                                                                    .map( Ilevelitem::getId ),
                                                                i.getLevel().get( j ).getEthnicity().stream()
                                                                    .map( n -> new ImmutableTriple<Number, Number, Number>(
                                                                                   n.getExpected(),
                                                                                   n.getMinimum(),
                                                                                   n.getMaximum()
                                                                    ) ),

                                                                i.getLevel().get( j ).getMotivation().stream()
                                                                    .map( Ilevelitem::getId ),

                                                                i.getLevel().get( j ).getMotivation().stream()
                                                                    .map( n -> new ImmutableTriple<Number, Number, Number>(
                                                                                  n.getExpected(),
                                                                                  n.getMinimum(),
                                                                                  n.getMaximum()
                                                                    ) ),

                                                                i.getLevel().get( j ).getAttribute().stream()
                                                                    .map( n -> l_attribute.get( n.getId().trim().toLowerCase() ) ),

                                                                i.getLevel().get( j ).getAttribute().stream()
                                                                    .map( n -> new ImmutableTriple<Number, Number, Number>(
                                                                                   n.getExpected(),
                                                                                   n.getMinimum(),
                                                                                   n.getMaximum()
                                                                    ) ),

                                                                i.getLevel().get( j ).getAttack().stream()
                                                                    .map( n -> l_attack.get( n.getId().trim().toLowerCase() ) )

                                                            )
                                            )
                                            .collect( Collectors.toList() )
                                    )
                            )

                        )
                    )
            );

        }
    }

    /**
     * returns the maxium level
     *
     * @param p_pokemon pokemon name
     * @return maximum level number
     */
    public final int level( final String p_pokemon )
    {
        return this.getOrThrow( p_pokemon ).getRight().size();
    }

    /**
     * returns the tupel
     *
     * @param p_pokemon pokemon name
     * @param p_index level
     * @®eturn level tupel
     */
    public final CLevel tupel( final String p_pokemon, final int p_index )
    {
        return this.getOrThrow( p_pokemon ).getRight().get( p_index );
    }

    /**
     * maximum experience
     */
    public final BigInteger experience( final String p_pokemon )
    {
        return this.getOrThrow( p_pokemon ).getLeft();
    }

    /**
     * returns a pokemon or throws an exception
     * @param p_pokemon pokemon name
     * @return pair of experience and level list
     */
    private Pair<BigInteger, List<CLevel>> getOrThrow( final String p_pokemon )
    {
        final Pair<BigInteger, List<CLevel>> l_pokemon = m_pokemon.get( p_pokemon.trim().toLowerCase() );
        if ( l_pokemon == null )
            throw new RuntimeException( MessageFormat.format( "pokemon [{0}] not found", p_pokemon ) );
        return l_pokemon;
    }

}
