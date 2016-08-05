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

import agentrouting.simulation.CMath;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    // --- normal ----------------------------------------------------------------------------------------------------------------------------------------------
    CHANSEY( 1,
             Stream.of(
                 EPreferences.NORMAL,
                 EPreferences.HEIGHT,
                 EPreferences.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 1.1, 0.8, 1.3 ),
                 new ImmutableTriple<>( 34.6, 30, 40 )
             )
    ),

    DITTO( 1,
           Stream.of(
               EPreferences.NORMAL,
               EPreferences.HEIGHT,
               EPreferences.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 0.3, 0.1, 0.5 ),
               new ImmutableTriple<>( 4, 3.5, 4.5 )
           )
    ),

    DODUO( 2,
           Stream.of(
               EPreferences.NORMAL,
               EPreferences.FLYING,
               EPreferences.HEIGHT,
               EPreferences.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 0.4, 0.3, 0.5 ),
               new ImmutableTriple<>( 1.4, 1, 0.7 ),
               new ImmutableTriple<>( 39.2, 36, 42 )
           )
    ),

    EEVEE( 4,
           Stream.of(
               EPreferences.NORMAL,
               EPreferences.HEIGHT,
               EPreferences.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 0.3, 0.1, 5 ),
               new ImmutableTriple<>( 6.5, 5.5, 7.5 )
                   )
    ),

    FARFETCHD( 1,
               Stream.of(
                   EPreferences.NORMAL,
                   EPreferences.FLYING,
                   EPreferences.HEIGHT,
                   EPreferences.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 ),
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 15, 12.5, 17.5 )
               )
    ),

    JIGGLYPUFF( 2,
                Stream.of(
                    EPreferences.NORMAL,
                    EPreferences.FAIRY,
                    EPreferences.HEIGHT,
                    EPreferences.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 ),
                    new ImmutableTriple<>( 0.4, 0.3, 0.5 ),
                    new ImmutableTriple<>( 0.5, 0.3, 0.7 ),
                    new ImmutableTriple<>( 5.5, 4.2, 6.5 )
                )
    ),

    KANGASKHAN( 1,
                Stream.of(
                    EPreferences.NORMAL,
                    EPreferences.HEIGHT,
                    EPreferences.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 ),
                    new ImmutableTriple<>( 2.2, 1.9, 2.7 ),
                    new ImmutableTriple<>( 80, 60, 100 )
                )
    ),

    LICKITUNG( 1,
               Stream.of(
                   EPreferences.NORMAL,
                   EPreferences.HEIGHT,
                   EPreferences.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 1.2, 1, 1.4 ),
                   new ImmutableTriple<>( 65.5, 60, 72 )
               )
    ),

    MEOWTH( 2,
            Stream.of(
                EPreferences.NORMAL,
                EPreferences.HEIGHT,
                EPreferences.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                new ImmutableTriple<>( 4.2, 3.8, 4.7 )
            )
    ),

    PIDGEY( 3,
            Stream.of(
                EPreferences.NORMAL,
                EPreferences.FLYING,
                EPreferences.HEIGHT,
                EPreferences.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 ),
                new ImmutableTriple<>( 0.3, 0.2, 0.5 ),
                new ImmutableTriple<>( 1.8, 1.5, 2.1 )
            )
    ),

    PORYGON( 1,
             Stream.of(
                 EPreferences.NORMAL,
                 EPreferences.HEIGHT,
                 EPreferences.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.8, 0.5, 1.1 ),
                 new ImmutableTriple<>( 36.5, 32, 39 )
             )
    ),

    RATTATA( 2,
             Stream.of(
                 EPreferences.NORMAL,
                 EPreferences.HEIGHT,
                 EPreferences.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.3, 0.1, 0.5 ),
                 new ImmutableTriple<>( 3.5, 2.8, 3.8 )
             )
    ),

    SNORLAX( 1,
             Stream.of(
                 EPreferences.NORMAL,
                 EPreferences.HEIGHT,
                 EPreferences.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 2.1, 1.8, 2.4 ),
                 new ImmutableTriple<>( 460, 400, 490 )
             )
    ),

    SPEAROW( 2,
             Stream.of(
                 EPreferences.NORMAL,
                 EPreferences.FLYING,
                 EPreferences.HEIGHT,
                 EPreferences.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 ),
                 new ImmutableTriple<>( 0.3, 0.1, 0.6 ),
                 new ImmutableTriple<>( 2, 1.2, 2.9 )
             )
    ),

    TAUROS( 1,
            Stream.of(
                EPreferences.NORMAL,
                EPreferences.HEIGHT,
                EPreferences.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 1.4, 1, 1.9 ),
                new ImmutableTriple<>( 88.4, 85, 91 )
            )
    ),



    // --- fire ------------------------------------------------------------------------------------------------------------------------------------------------
    CHARMANDER( 3,
                Stream.of( EPreferences.FIRE ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    GROWLITHE( 2,
               Stream.of( EPreferences.FIRE ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    MAGMAR( 1,
            Stream.of( EPreferences.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    MOLTRES( 1,
             Stream.of(
                 EPreferences.FIRE,
                 EPreferences.FLYING
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             )
    ),

    PONYTA( 2,
            Stream.of( EPreferences.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    VULPIX( 2,
            Stream.of( EPreferences.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),



    // --- water -----------------------------------------------------------------------------------------------------------------------------------------------
    GOLDEEN( 2,
             Stream.of( EPreferences.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    HORSEA( 2,
            Stream.of( EPreferences.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    KRABBY( 2,
            Stream.of( EPreferences.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    LAPRAS( 1,
            Stream.of(
                EPreferences.WATER,
                EPreferences.ICE
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            )
    ),

    MAGIKARP( 2,
              Stream.of( EPreferences.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    POLIWAG( 3,
             Stream.of( EPreferences.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    PSYDUCK( 2,
             Stream.of( EPreferences.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    SEEL( 2,
          Stream.of( EPreferences.WATER ),
          Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    SHELLDER( 2,
              Stream.of( EPreferences.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    SLOWPOKE( 2,
              Stream.of(
                  EPreferences.WATER,
                  EPreferences.PSYCHIC
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.8, 0.6, 1 ),
                  new ImmutableTriple<>( 0.4, 0.3, 0.5 )
              )
    ),

    SQUIRTLE( 3,
              Stream.of( EPreferences.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    STARYU( 2,
            Stream.of( EPreferences.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    TENTACOOL( 2,
               Stream.of(
                   EPreferences.WATER,
                   EPreferences.POISON
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               )
    ),



    // --- electric --------------------------------------------------------------------------------------------------------------------------------------------
    ELECTABUZZ( 1,
                Stream.of( EPreferences.ELECTRIC ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    MAGNEMITE( 2,
               Stream.of(
                   EPreferences.ELECTRIC,
                   EPreferences.STEEL
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               )
    ),

    PIKACHU( 2,
             Stream.of( EPreferences.ELECTRIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    VOLTORB( 2,
             Stream.of( EPreferences.ELECTRIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    ZAPDOS( 1,
            Stream.of(
                EPreferences.ELECTRIC,
                EPreferences.FLYING
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            )
    ),



    // --- grass -----------------------------------------------------------------------------------------------------------------------------------------------
    BELLSPROUT( 3,
                Stream.of(
                    EPreferences.GRASS,
                    EPreferences.POISON
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 ),
                    new ImmutableTriple<>( 0.4, 0.3, 0.5 )
                )
    ),

    BULBASAUR( 3,
               Stream.of(
                   EPreferences.GRASS,
                   EPreferences.POISON
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               )
    ),

    EXEGGCUTE( 2,
               Stream.of(
                   EPreferences.GRASS,
                   EPreferences.PSYCHIC
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               )
    ),

    ODDISH( 3,
            Stream.of(
                EPreferences.GRASS,
                EPreferences.POISON
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            )
    ),

    TANGELA( 2,
             Stream.of( EPreferences.GRASS ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),



    // --- ice -------------------------------------------------------------------------------------------------------------------------------------------------
    ARTICUNO( 1,
              Stream.of(
                  EPreferences.ICE,
                  EPreferences.FLYING
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.8, 0.6, 1 ),
                  new ImmutableTriple<>( 0.4, 0.3, 0.5 )
              )
    ),

    JYNX( 1,
          Stream.of(
              EPreferences.ICE,
              EPreferences.PSYCHIC
          ),
          Stream.of(
              new ImmutableTriple<>( 0.8, 0.6, 1 ),
              new ImmutableTriple<>( 0.4, 0.3, 0.5 )
          )
    ),



    // --- fighting --------------------------------------------------------------------------------------------------------------------------------------------
    HITMONCHAN( 1,
                Stream.of( EPreferences.FIGHTING ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    HITMONLEE( 1,
               Stream.of( EPreferences.FIGHTING ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    MACHOP( 3,
            Stream.of( EPreferences.FIGHTING ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    MANKEY( 2,
            Stream.of( EPreferences.FIGHTING ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),



    // --- poison ----------------------------------------------------------------------------------------------------------------------------------------------
    EKANS( 2,
           Stream.of( EPreferences.POISON ),
           Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    GRIMER( 2,
            Stream.of( EPreferences.POISON ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    KOFFING( 2,
             Stream.of( EPreferences.POISON ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    NIDORAN_FEMALE( 3,
                    Stream.of( EPreferences.POISON ),
                    Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    NIDORAN_MALE( 3,
                  Stream.of( EPreferences.POISON ),
                  Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    ZUBAT( 2,
           Stream.of(
               EPreferences.POISON,
               EPreferences.FLYING
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 0.4, 0.3, 0.5 )
           )
    ),



    // --- ground ----------------------------------------------------------------------------------------------------------------------------------------------
    CUBONE( 2,
            Stream.of( EPreferences.GROUND ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    DIGLETT( 2,
             Stream.of( EPreferences.GROUND ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    RHYHORN( 2,
             Stream.of(
                 EPreferences.GROUND,
                 EPreferences.ROCK
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             )
    ),

    SANDSHREW( 2,
               Stream.of( EPreferences.GROUND ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),



    // --- psychic ---------------------------------------------------------------------------------------------------------------------------------------------
    ABRA( 3,
          Stream.of( EPreferences.PSYCHIC ),
          Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    DROWZEE( 2,
             Stream.of( EPreferences.PSYCHIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    MEW( 1,
         Stream.of( EPreferences.PSYCHIC ),
         Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    MEWTWO( 1,
            Stream.of( EPreferences.PSYCHIC ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    MR_MIME( 1,
             Stream.of(
                 EPreferences.PSYCHIC,
                 EPreferences.FAIRY
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             )
    ),



    // --- bug -------------------------------------------------------------------------------------------------------------------------------------------------
    CATERPIE( 3,
              Stream.of( EPreferences.BUG ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    PARAS( 2,
           Stream.of(
               EPreferences.BUG,
               EPreferences.GRASS
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 0.4, 0.3, 0.5 )
           )
    ),

    PINSIR( 1,
            Stream.of( EPreferences.BUG ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),

    SCYTHER( 1,
             Stream.of(
                 EPreferences.BUG,
                 EPreferences.FLYING
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             )
    ),

    VENONAT( 2,
             Stream.of(
                 EPreferences.BUG,
                 EPreferences.POISON
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             )
    ),

    WEEDLE( 2,
            Stream.of(
                EPreferences.BUG,
                EPreferences.POISON
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            )
    ),



    // --- rock ------------------------------------------------------------------------------------------------------------------------------------------------
    AERODACTYL( 1,
                Stream.of(
                    EPreferences.ROCK,
                    EPreferences.FLYING
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 ),
                    new ImmutableTriple<>( 0.4, 0.3, 0.5 )
                )
    ),

    GEODUDE( 3,
             Stream.of(
                 EPreferences.ROCK,
                 EPreferences.GROUND
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             )
    ),

    KABUTO( 2,
            Stream.of(
                EPreferences.ROCK,
                EPreferences.WATER
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            )
    ),

    OMANYTE( 2,
             Stream.of(
                 EPreferences.ROCK,
                 EPreferences.WATER
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             )
    ),

    ONIX( 1,
          Stream.of(
              EPreferences.ROCK,
              EPreferences.GROUND
          ),
          Stream.of(
              new ImmutableTriple<>( 0.8, 0.6, 1 ),
              new ImmutableTriple<>( 0.4, 0.3, 0.5 )
          )
    ),



    // --- ghost -----------------------------------------------------------------------------------------------------------------------------------------------
    GASTLY( 3,
            Stream.of(
                EPreferences.GHOST,
                EPreferences.POISON
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            )
    ),



    // --- dragon ----------------------------------------------------------------------------------------------------------------------------------------------
    DRATINI( 3,
             Stream.of( EPreferences.DRAGON ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    ),



    // --- fairy -----------------------------------------------------------------------------------------------------------------------------------------------
    CLEFAIRY( 2,
              Stream.of( EPreferences.FAIRY ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) )
    );



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
    private final Map<EPreferences, Triple<AbstractRealDistribution, Number, Number>> m_preference;



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
                               ( n, v ) -> new ImmutablePair<>(
                                               n,
                                               new ImmutableTriple<AbstractRealDistribution, Number, Number>(
                                                   new NormalDistribution( CMath.RANDOMGENERATOR, v.getLeft().doubleValue(), 0.1 ),
                                                   v.getMiddle(),
                                                   v.getRight()
                                               )
                               )
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
        return m_preference.entrySet().stream().collect(
            Collectors.toMap(
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
