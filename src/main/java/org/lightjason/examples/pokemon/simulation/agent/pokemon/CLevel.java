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
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.lightjason.examples.pokemon.CCommon;
import org.lightjason.examples.pokemon.simulation.CMath;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * tupel to define all elements of a level
 */
public final class CLevel
{
    /**
     * filename of the icon
     */
    private static final String ICONFILENAME = CCommon.PACKAGEPATH + "data/icon/{0}_{1}.png";
    /**
     * ethnic map map with initialize values (initial value, min, max bounding)
     */
    private final Map<String, ImmutableTriple<AbstractRealDistribution, Number, Number>> m_ethnic;
    /**
     * attribute map with initialize values (initial value, min, max bounding)
     */
    private final Map<CAttribute, ImmutableTriple<AbstractRealDistribution, Number, Number>> m_attribute;
    /**
     * motivation map with initialize values (initial value, min, max bounding)
     */
    private final Map<String, ImmutableTriple<AbstractRealDistribution, Number, Number>> m_motivation;
    /**
     * attack set
     */
    private final Set<CAttack> m_attack;
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
     *
     * @param p_pokemon pokemon name
     * @param p_index index number of pokemon
     */
    public CLevel( final String p_pokemon, final int p_index )
    {
        this(
            p_pokemon, p_index,
            Stream.of(), Stream.of(),
            Stream.of(), Stream.of(),
            Stream.of(), Stream.of(),
            Stream.of()
        );
    }

    /**
     * ctor
     *
     * @param p_pokemon pokemon name
     * @param p_index index number of pokemon
     * @param p_ethnic stream with ethnic types
     * @param p_ethnicvalue stream with ethnic values
     */
    public CLevel( final String p_pokemon, final int p_index,
                    final Stream<String> p_ethnic, final Stream<ImmutableTriple<Number, Number, Number>> p_ethnicvalue )
    {
        this(
            p_pokemon, p_index,
            p_ethnic, p_ethnicvalue,
            Stream.of(), Stream.of(),
            Stream.of(), Stream.of(),
            Stream.of()
        );
    }

    /**
     * ctor
     *
     * @param p_pokemon pokemon name
     * @param p_index index number of pokemon
     * @param p_ethnic stream with ethnic types
     * @param p_ethnicvalue stream with ethnic values
     * @param p_motivation stream with motivation types
     * @param p_motivationvalue stream with motivation value
     */
    public CLevel( final String p_pokemon, final int p_index,
                    final Stream<String> p_ethnic, final Stream<ImmutableTriple<Number, Number, Number>> p_ethnicvalue,
                    final Stream<String> p_motivation, final Stream<ImmutableTriple<Number, Number, Number>> p_motivationvalue
    )
    {
        this(
            p_pokemon, p_index,
            p_ethnic, p_ethnicvalue,
            p_motivation, p_motivationvalue,
            Stream.of(), Stream.of(),
            Stream.of()
        );
    }

    /**
     * ctor
     *
     * @param p_pokemon pokemon name
     * @param p_index index number of pokemon
     * @param p_ethnic stream with ethnic types
     * @param p_ethnicvalue stream with ethnic values
     * @param p_motivation stream with motivation types
     * @param p_motivationvalue stream with motivation value
     * @param p_attribute stream with attributes types
     * @param p_attributesvalue stream with attributes values
     */
    public CLevel( final String p_pokemon, final int p_index,
                   final Stream<String> p_ethnic, final Stream<ImmutableTriple<Number, Number, Number>> p_ethnicvalue,
                   final Stream<String> p_motivation, final Stream<ImmutableTriple<Number, Number, Number>> p_motivationvalue,
                   final Stream<CAttribute> p_attribute, final Stream<ImmutableTriple<Number, Number, Number>> p_attributesvalue
    )
    {
        this(
            p_pokemon, p_index,
            p_ethnic, p_ethnicvalue,
            p_motivation, p_motivationvalue,
            p_attribute, p_attributesvalue,
            Stream.of()
        );
    }

    /**
     * ctor
     *
     * @param p_pokemon pokemon name
     * @param p_index index number of pokemon
     * @param p_ethnic stream with ethnic types
     * @param p_ethnicvalue stream with ethnic values
     * @param p_motivation stream with motivation types
     * @param p_motivationvalue stream with motivation value
     * @param p_attribute stream with attributes types
     * @param p_attributesvalue stream with attributes values
     * @param p_attack attack
     */
    public CLevel( final String p_pokemon, final int p_index,
                   final Stream<String> p_ethnic, final Stream<ImmutableTriple<Number, Number, Number>> p_ethnicvalue,
                   final Stream<String> p_motivation, final Stream<ImmutableTriple<Number, Number, Number>> p_motivationvalue,
                   final Stream<CAttribute> p_attribute, final Stream<ImmutableTriple<Number, Number, Number>> p_attributesvalue,
                   final Stream<CAttack> p_attack
    )
    {
        m_texturepath = MessageFormat.format(
            ICONFILENAME,
            p_pokemon.trim().toLowerCase().replaceAll( " ", "_" ),
            p_index
        );
        m_ethnic = CLevel.initialize( p_ethnic, p_ethnicvalue );
        m_attribute = CLevel.initialize( p_attribute, p_attributesvalue );
        m_motivation = CLevel.initialize( p_motivation, p_motivationvalue );
        m_attack = Collections.unmodifiableSet( p_attack.collect( Collectors.toSet() ) );
    }


    /**
     * generates the ethnic map of a pokemon
     *
     * @return map individual ethnic values
     */
    public Map<String, Number> ethnic()
    {
        return CLevel.generate( m_ethnic );
    }

    /**
     * generates the attribute map of a pokemon
     *
     * @return map individual attributes values
     */
    public Map<CAttribute, Number> attribute()
    {
        return CLevel.generate( m_attribute );
    }

    /**
     * generates the attribute map of a pokemon
     *
     * @return map individual attributes values
     */
    public Map<String, Number> motivation()
    {
        return CLevel.generate( m_motivation );
    }

    /**
     * returns the attacks
     *
     * @return attack set
     */
    public final Set<CAttack> attack()
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
     * generates the random distribution for the values
     *
     * @param p_keys keys
     * @param p_value tripel value set (initial value, min, max bounding)
     * @return map
     *
     * @tparam T key type
     */
    private static <T> Map<T, ImmutableTriple<AbstractRealDistribution, Number, Number>> initialize( final Stream<T> p_keys,
                                                                                            final Stream<ImmutableTriple<Number, Number, Number>> p_value
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
    private static <T> Map<T, Number> generate( final Map<T, ImmutableTriple<AbstractRealDistribution, Number, Number>> p_map )
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
