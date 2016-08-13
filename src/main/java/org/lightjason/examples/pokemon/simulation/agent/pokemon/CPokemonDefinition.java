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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.lightjason.examples.pokemon.CCommon;
import org.lightjason.examples.pokemon.CConfiguration;
import org.lightjason.examples.pokemon.datasource.Structure;
import org.lightjason.examples.pokemon.simulation.CMath;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * pokemon definition class
 */
public final class CPokemonDefinition
{
    /**
     * singleton instance
     */
    public static final CPokemonDefinition INSTANCE = new CPokemonDefinition();
    /**
     * logger
     */
    private static final Logger LOGGER = Logger.getLogger( CConfiguration.class.getName() );
    /**
     * mapt with pokemon data
     */
    private Map<String, List<CLevelTupel>> m_pokemon;

    /**
     * ctor
     */
    private CPokemonDefinition()
    {
        try
        {
            m_pokemon = Collections.unmodifiableMap(
                ( (Structure) JAXBContext.newInstance( Structure.class )
                    .createUnmarshaller()
                    .unmarshal(
                        CCommon.getResourceURL( "org/lightjason/examples/pokemon/datasource/character.xml" )
                    ) )
                    .getCharacter()
                    .getPokemon()
                    .parallelStream()
                    .map( i -> new AbstractMap.SimpleImmutableEntry<>(
                                   i.getId().trim().toLowerCase(),
                                   Collections.<CLevelTupel>emptyList()
                    ) )
                    .collect(
                        Collectors.toMap(
                            AbstractMap.SimpleImmutableEntry::getKey,
                            AbstractMap.SimpleImmutableEntry::getValue
                        )
                    )
            );
        }
        catch ( final JAXBException | MalformedURLException | URISyntaxException l_exception )
        {
            LOGGER.warning( l_exception.toString() );
            return;
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
        return m_pokemon.getOrDefault( p_pokemon.trim().toLowerCase(), Collections.emptyList() ).size();
    }

    /**
     * returns the tupel
     *
     * @param p_pokemon pokemon name
     * @param p_index level
     * @®eturn tupel
     */
    public final CLevelTupel tupel( final String p_pokemon, final int p_index )
    {
        return m_pokemon.getOrDefault( p_pokemon.trim().toLowerCase(), Collections.emptyList() ).get( p_index );
    }


    /**
     * tupel to define all elements of a level
     */
    public static final class CLevelTupel
    {
        /**
         * ethnic map map with initialize values (initial value, min, max bounding)
         */
        private final Map<EEthnicity, Triple<AbstractRealDistribution, Number, Number>> m_ethnic;
        /**
         * attribute map with initialize values (initial value, min, max bounding)
         */
        private final Map<EAttribute, Triple<AbstractRealDistribution, Number, Number>> m_attributes;
        /**
         * motivation map with initialize values (initial value, min, max bounding)
         */
        private final Map<EMotivation, Triple<AbstractRealDistribution, Number, Number>> m_motivation;
        /**
         * attack set
         */
        private final Set<EAttack> m_attack;
        /**
         * texture file path
         */
        private String m_texturepath;
        /**
         * texture
         */
        private Texture m_texture;
        /**
         * sprite environment cellsize
         */
        private int m_spritecellsize;
        /**
         * sprite unit size
         */
        private float m_spriteunitsize;


        /**
         * ctor
         */
        private CLevelTupel()
        {
            this(
                Stream.of(), Stream.of(),
                Stream.of(), Stream.of(),
                Stream.of(), Stream.of(),
                Stream.of()
            );
        }

        /**
         * ctor
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         */
        private CLevelTupel( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue )
        {
            this(
                p_ethnic, p_ethnicvalue,
                Stream.of(), Stream.of(),
                Stream.of(), Stream.of(),
                Stream.of()
            );
        }

        /**
         * ctor
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         * @param p_attributes stream with attributes types
         * @param p_attributesvalue stream with attributes values
         */
        private CLevelTupel( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                             final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue
        )
        {
            this(
                p_ethnic, p_ethnicvalue,
                p_attributes, p_attributesvalue,
                Stream.of(), Stream.of(),
                Stream.of()
            );
        }

        /**
         * ctor
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         * @param p_attributes stream with attributes types
         * @param p_attributesvalue stream with attributes values
         * @param p_motivation stream with motivation types
         * @param p_motivationvalue stream with motivation value
         */
        private CLevelTupel( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                             final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue,
                             final Stream<EMotivation> p_motivation, final Stream<Triple<Number, Number, Number>> p_motivationvalue
        )
        {
            this(
                p_ethnic, p_ethnicvalue,
                p_attributes, p_attributesvalue,
                p_motivation, p_motivationvalue,
                Stream.of()
            );
        }

        /**
         * ctor
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         * @param p_attributes stream with attributes types
         * @param p_attributesvalue stream with attributes values
         * @param p_motivation stream with motivation types
         * @param p_motivationvalue stream with motivation value
         */
        private CLevelTupel( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                             final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue,
                             final Stream<EMotivation> p_motivation, final Stream<Triple<Number, Number, Number>> p_motivationvalue,
                             final Stream<EAttack> p_attack
        )
        {
            m_ethnic = EPokemon.CLevelTupel.initialize( p_ethnic, p_ethnicvalue );
            m_attributes = EPokemon.CLevelTupel.initialize( p_attributes, p_attributesvalue );
            m_motivation = EPokemon.CLevelTupel.initialize( p_motivation, p_motivationvalue );
            m_attack = Collections.unmodifiableSet( p_attack.collect( Collectors.toSet() ) );
        }


        /**
         * generates the ethnic map of a pokemon
         *
         * @return map individual ethnic values
         */
        public Map<EEthnicity, Number> ethnic()
        {
            return EPokemon.CLevelTupel.generate( m_ethnic );
        }

        /**
         * generates the attribute map of a pokemon
         *
         * @return map individual attributes values
         */
        public Map<EAttribute, Number> attributes()
        {
            return EPokemon.CLevelTupel.generate( m_attributes );
        }

        /**
         * generates the attribute map of a pokemon
         *
         * @return map individual attributes values
         */
        public Map<EMotivation, Number> motivation()
        {
            return EPokemon.CLevelTupel.generate( m_motivation );
        }

        /**
         * returns the attacks
         *
         * @return attack set
         */
        public final Set<EAttack> attack()
        {
            return m_attack;
        }


        /**
         * returns the texture of the sprite
         *
         * @param p_cellsize cell size
         * @param p_unit unit
         * @return new sprite instance with the pokemon texture
         */
        public final synchronized Sprite sprite( final int p_cellsize, final float p_unit )
        {
            if ( m_texture == null )
                m_texture = new Texture( Gdx.files.internal( m_texturepath ) );

            m_spritecellsize = p_cellsize;
            m_spriteunitsize = p_unit;

            final Sprite l_sprite = new Sprite( m_texture );
            l_sprite.setSize( m_spritecellsize, m_spritecellsize );
            l_sprite.setOrigin( 1.5f / m_spritecellsize, 1.5f / m_spritecellsize );
            l_sprite.setScale( m_spriteunitsize );

            return l_sprite;
        }

        /**
         * initialize the sprite
         *
         * @param p_pokemon pokemon enum
         * @param p_index index
         * @return self reference
         */
        public final synchronized EPokemon.CLevelTupel sprite( final EPokemon p_pokemon, final int p_index )
        {
            if ( m_texture != null )
                return this;

            m_texturepath = MessageFormat.format(
                ICONFILENAME,
                p_pokemon.name().toLowerCase().replaceAll( " ", "_" ),
                p_index
            );

            return this;
        }

        /**
         * returns the sprite unit size
         *
         * @return unit size
         */
        public final float spriteunit()
        {
            return m_spriteunitsize;
        }

        /**
         * returns the sprite cell size
         *
         * @return cell size
         */
        public final int spritecell()
        {
            return m_spritecellsize;
        }

        /**
         * factory of tupel
         *
         * @return tupel
         */
        public static EPokemon.CLevelTupel generate()
        {
            return new EPokemon.CLevelTupel();
        }

        /**
         * factory of tupel
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         * @return tupel
         */
        public static EPokemon.CLevelTupel generate( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue )
        {
            return new EPokemon.CLevelTupel(
                p_ethnic, p_ethnicvalue
            );
        }

        /**
         * factory of tupel
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         * @param p_attributes stream with attributes types
         * @param p_attributesvalue stream with attributes values
         * @return tupel
         */
        public static EPokemon.CLevelTupel generate( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                                                     final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue
        )
        {
            return new EPokemon.CLevelTupel(
                p_ethnic, p_ethnicvalue,
                p_attributes, p_attributesvalue
            );
        }

        /**
         * factory of tupel
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         * @param p_attributes stream with attributes types
         * @param p_attributesvalue stream with attributes values
         * @param p_motivation stream with motivation types
         * @param p_motivationvalue stream with motivation value
         * @return tupel
         */
        public static EPokemon.CLevelTupel generate( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                                                     final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue,
                                                     final Stream<EMotivation> p_motivation, final Stream<Triple<Number, Number, Number>> p_motivationvalue
        )
        {
            return new EPokemon.CLevelTupel(
                p_ethnic, p_ethnicvalue,
                p_attributes, p_attributesvalue,
                p_motivation, p_motivationvalue
            );
        }

        /**
         * factory of tupel
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         * @param p_attributes stream with attributes types
         * @param p_attributesvalue stream with attributes values
         * @param p_motivation stream with motivation types
         * @param p_motivationvalue stream with motivation value
         * @return tupel
         */
        public static EPokemon.CLevelTupel generate( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                                                     final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue,
                                                     final Stream<EMotivation> p_motivation, final Stream<Triple<Number, Number, Number>> p_motivationvalue,
                                                     final Stream<EAttack> p_attack
        )
        {
            return new EPokemon.CLevelTupel(
                p_ethnic, p_ethnicvalue,
                p_attributes, p_attributesvalue,
                p_motivation, p_motivationvalue,
                p_attack
            );
        }

        /**
         * generates the random distribution for the values
         *
         * @param p_keys keys
         * @param p_value tripel value set (initial value, min, max bounding)
         * @return map
         *
         * @tparam T key type
         */
        private static <T> Map<T, Triple<AbstractRealDistribution, Number, Number>> initialize( final Stream<T> p_keys,
                                                                                                final Stream<Triple<Number, Number, Number>> p_value
        )
        {
            return Collections.unmodifiableMap(
                StreamUtils.zip(
                    p_keys,
                    p_value,
                    ( n, v ) -> new ImmutablePair<>(
                        n,
                        new ImmutableTriple<AbstractRealDistribution, Number, Number>(
                            new NormalDistribution(
                                CMath.RANDOMGENERATOR,
                                v.getLeft().doubleValue(),
                                Math.sqrt( 0.5 * ( v.getRight().doubleValue() - v.getMiddle().doubleValue() ) )
                            ),
                            v.getMiddle(),
                            v.getRight()
                        )
                    )
                ).collect( Collectors.toMap( ImmutablePair::getLeft, ImmutablePair::getRight ) ) );
        }

        /**
         * generates of a distribution map an set of values
         *
         * @param p_map distribution map
         * @return value map
         *
         * @tparam T key type
         */
        private static <T> Map<T, Number> generate( final Map<T, Triple<AbstractRealDistribution, Number, Number>> p_map )
        {
            return p_map.entrySet().parallelStream().collect(
                Collectors.toConcurrentMap(
                    Map.Entry::getKey,
                    i -> Math.min(
                        Math.max(
                            i.getValue().getLeft().sample(),
                            i.getValue().getMiddle().doubleValue()
                        ),
                        i.getValue().getRight().doubleValue()
                    )
                )
            );
        }
    }

}
