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

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * enum to define all sprites by name
 *
 * @see http://www.pokewiki.de/Pok%C3%A9mon-Liste
 */
public enum EPokemon
{
    // normal
    CHANSEY( 1 ),
    DITTO( 1 ),
    DODUO( 2 ),
    EEVEE( 4 ),
    FARFETCHD( 1 ),
    JIGGLYPUFF( 2 ),
    KANGASKHAN( 1 ),
    LICKITUNG( 1 ),
    MEOWTH( 2 ),
    PIDGEY( 3 ),
    PORYGON( 1 ),
    RATTATA( 2 ),
    SNORLAX( 1 ),
    SPEAROW( 2 ),
    TAUROS( 1 ),

    // fire
    CHARMANDER( 3 ),
    GROWLITHE( 2 ),
    MAGMAR( 1 ),
    MOLTRES( 1 ),
    PONYTA( 2 ),
    VULPIX( 2 ),

    // water
    GOLDEEN( 2 ),
    HORSEA( 2 ),
    KRABBY( 2 ),
    LAPRAS( 1 ),
    MAGIKARP( 2 ),
    POLIWAG( 3 ),
    PSYDUCK( 2 ),
    SEEL( 2 ),
    SHELLDER( 2 ),
    SLOWPOKE( 2 ),
    SQUIRTLE( 3 ),
    STARYU( 2 ),
    TENTACOOL( 2 ),

    // electric
    ELECTABUZZ( 1 ),
    MAGNEMITE( 2 ),
    PIKACHU( 2 ),
    VOLTORB( 2 ),
    ZAPDOS( 1 ),

    // grass
    BELLSPROUT( 3 ),
    BULBASAUR( 3 ),
    EXEGGCUTE( 2 ),
    ODDISH( 3 ),
    TANGELA( 2 ),

    // ice
    ARTICUNO( 1 ),
    JYNX( 1 ),

    // fighting
    HITMONCHAN( 1 ),
    HITMONLEE( 1 ),
    MACHOP( 3 ),
    MANKEY( 2 ),

    // poison
    EKANS( 2 ),
    GRIMER( 2 ),
    KOFFING( 2 ),
    NIDORAN_FEMALE( 3 ),
    NIDORAN_MALE( 3 ),
    ZUBAT( 2 ),

    // ground
    CUBONE( 2 ),
    DIGLETT( 2 ),
    RHYHORN( 2 ),
    SANDSHREW( 2 ),

    // psychic
    ABRA( 3 ),
    DROWZEE( 2 ),
    MEW( 1 ),
    MEWTWO( 1 ),
    MR_MIME( 1 ),

    // bug
    CATERPIE( 3 ),
    PARAS( 2 ),
    PINSIR( 1 ),
    SCYTHER( 1 ),
    VENONAT( 2 ),
    WEEDLE( 2 ),

    // rock
    AERODACTYL( 1 ),
    GEODUDE( 3 ),
    KABUTO( 2 ),
    OMANYTE( 2 ),
    ONIX( 1 ),

    // ghost
    GASTLY( 3 ),

    // dragon
    DRATINI( 3 ),

    // fairy
    CLEFAIRY( 2 );


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
     * ctor
     *
     * @param p_icons number of icons
     */
    EPokemon( final int p_icons )
    {
        m_icons = p_icons;
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
     * @return map
     */
    public Map<EPreferences, Double> generateCharacteristic()
    {
        return Collections.emptyMap();
    }

}
