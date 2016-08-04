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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * enum to define all sprites by name
 *
 * @see http://www.pokewiki.de/Pok%C3%A9mon-Liste
 */
public enum EPokemon
{
    // normal
    CHANSEY( 1, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    DITTO( 1, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    DODUO( 2, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    EEVEE( 4, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    FARFETCHD( 1, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    JIGGLYPUFF( 2, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    KANGASKHAN( 1, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    LICKITUNG( 1, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MEOWTH( 2, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    PIDGEY( 3, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    PORYGON( 1, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    RATTATA( 2, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    SNORLAX( 1, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    SPEAROW( 2, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    TAUROS( 1, Stream.of( EPreferences.NORMAL ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // fire
    CHARMANDER( 3, Stream.of( EPreferences.FIRE ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    GROWLITHE( 2, Stream.of( EPreferences.FIRE ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MAGMAR( 1, Stream.of( EPreferences.FIRE ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MOLTRES( 1, Stream.of( EPreferences.FIRE ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    PONYTA( 2, Stream.of( EPreferences.FIRE ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    VULPIX( 2, Stream.of( EPreferences.FIRE ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // water
    GOLDEEN( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    HORSEA( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    KRABBY( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    LAPRAS( 1, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MAGIKARP( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    POLIWAG( 3, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    PSYDUCK( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    SEEL( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    SHELLDER( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    SLOWPOKE( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    SQUIRTLE( 3, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    STARYU( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    TENTACOOL( 2, Stream.of( EPreferences.WATER ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // electric
    ELECTABUZZ( 1, Stream.of( EPreferences.ELECTRIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MAGNEMITE( 2, Stream.of( EPreferences.ELECTRIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    PIKACHU( 2, Stream.of( EPreferences.ELECTRIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    VOLTORB( 2, Stream.of( EPreferences.ELECTRIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    ZAPDOS( 1, Stream.of( EPreferences.ELECTRIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // grass
    BELLSPROUT( 3, Stream.of( EPreferences.GRASS ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    BULBASAUR( 3, Stream.of( EPreferences.GRASS ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    EXEGGCUTE( 2, Stream.of( EPreferences.GRASS ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    ODDISH( 3, Stream.of( EPreferences.GRASS ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    TANGELA( 2, Stream.of( EPreferences.GRASS ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // ice
    ARTICUNO( 1, Stream.of( EPreferences.ICE ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    JYNX( 1, Stream.of( EPreferences.ICE ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // fighting
    HITMONCHAN( 1, Stream.of( EPreferences.FIGHTING ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    HITMONLEE( 1, Stream.of( EPreferences.FIGHTING ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MACHOP( 3, Stream.of( EPreferences.FIGHTING ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MANKEY( 2, Stream.of( EPreferences.FIGHTING ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // poison
    EKANS( 2, Stream.of( EPreferences.POISON ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    GRIMER( 2, Stream.of( EPreferences.POISON ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    KOFFING( 2, Stream.of( EPreferences.POISON ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    NIDORAN_FEMALE( 3, Stream.of( EPreferences.POISON ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    NIDORAN_MALE( 3, Stream.of( EPreferences.POISON ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    ZUBAT( 2, Stream.of( EPreferences.POISON ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // ground
    CUBONE( 2, Stream.of( EPreferences.GROUND ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    DIGLETT( 2, Stream.of( EPreferences.GROUND ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    RHYHORN( 2, Stream.of( EPreferences.GROUND ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    SANDSHREW( 2, Stream.of( EPreferences.GROUND ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // psychic
    ABRA( 3, Stream.of( EPreferences.PSYCHIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    DROWZEE( 2, Stream.of( EPreferences.PSYCHIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MEW( 1, Stream.of( EPreferences.PSYCHIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MEWTWO( 1, Stream.of( EPreferences.PSYCHIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    MR_MIME( 1, Stream.of( EPreferences.PSYCHIC ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // bug
    CATERPIE( 3, Stream.of( EPreferences.BUG ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    PARAS( 2, Stream.of( EPreferences.BUG ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    PINSIR( 1, Stream.of( EPreferences.BUG ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    SCYTHER( 1, Stream.of( EPreferences.BUG ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    VENONAT( 2, Stream.of( EPreferences.BUG ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    WEEDLE( 2, Stream.of( EPreferences.BUG ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // rock
    AERODACTYL( 1, Stream.of( EPreferences.ROCK ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    GEODUDE( 3, Stream.of( EPreferences.ROCK ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    KABUTO( 2, Stream.of( EPreferences.ROCK ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    OMANYTE( 2, Stream.of( EPreferences.ROCK ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),
    ONIX( 1, Stream.of( EPreferences.ROCK ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // ghost
    GASTLY( 3, Stream.of( EPreferences.GHOST ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // dragon
    DRATINI( 3, Stream.of( EPreferences.DRAGON ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) ),

    // fairy
    CLEFAIRY( 2, Stream.of( EPreferences.FAIRY ), Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ) );


    /**
     * logger
     */
    private static final Logger LOGGER = Logger.getLogger( EPokemon.class.getName() );
    /**
     * number of icons
     */
    private final int m_icons;

    /**
     * sprite list
     */
    private List<Texture> m_sprites;
    /**
     * preference map with initialize values (initial value, min, max bounding)
     */
    private final Map<EPreferences, Triple<Number, Number, Number>> m_preference;



    /**
     * ctor
     *
     * @param p_icons number of icons
     */
    EPokemon( final int p_icons, final Stream<EPreferences> p_preferencename, final Stream<Triple<Number, Number, Number>> p_preferencevalue )
    {
        m_icons = p_icons;
        m_preference = Collections.unmodifiableMap(
                           StreamUtils.zip(
                               p_preferencename,
                               p_preferencevalue,
                               ImmutablePair::new
                           ).collect( Collectors.toMap( ImmutablePair::getLeft, ImmutablePair::getRight ) )
        );
    }

    /**
     * returns the number of sprites
     *
     * @return sprite number
     */
    public final int size()
    {
        return m_icons;
    }

    /**
     * initialize sprites
     *
     * @return texture object
     */
    public final synchronized Texture initialize()
    {
        if ( m_sprites != null )
            return m_sprites.get( 0 );

        m_sprites = Collections.unmodifiableList(
            IntStream.range( 0, m_icons )
                     .mapToObj( i -> new Texture(
                                        Gdx.files.internal(
                                            MessageFormat.format( "agentrouting/sprites/{0}_{1}.png", this.name().toLowerCase().replaceAll( " ", "_" ), i )
                                        )
                                    )
                     )
                     .filter( i -> i != null )
                     .collect( Collectors.toList() )
        );

        if ( ( m_sprites.isEmpty() ) || ( m_icons != m_sprites.size() ) )
            throw new RuntimeException( MessageFormat.format( "texture [{0}] cannot initialize", this ) );

        return m_sprites.get( 0 );
    }

    /**
     * returns pokemon sprite
     *
     * @param p_index index number
     * @return sprite
     */
    public final synchronized Texture sprite( final int p_index )
    {
        return m_sprites.get( p_index );
    }

    /**
     * generates the preference map of a pokemon
     * @return map individual preferences
     */
    public Map<EPreferences, Number> generateCharacteristic()
    {
        return m_preference.entrySet().stream().collect( Collectors.toMap( Map.Entry::getKey, i -> i.getValue().getLeft() ) );
    }

}
