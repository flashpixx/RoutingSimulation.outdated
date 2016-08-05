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
                 EEthncity.NORMAL
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 )
             ),

             Stream.of(
                 EAttributes.HEIGHT,
                 EAttributes.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1.1, 0.8, 1.3 ),
                 new ImmutableTriple<>( 34.6, 30, 40 )
             ),

             Stream.of(),
             Stream.of()
    ),

    DITTO( 1,
           Stream.of(
               EEthncity.NORMAL
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 )
           ),

           Stream.of(
               EAttributes.HEIGHT,
               EAttributes.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.3, 0.1, 0.5 ),
               new ImmutableTriple<>( 4, 3.5, 4.5 )
           ),

           Stream.of(),
           Stream.of()
    ),

    DODUO( 2,
           Stream.of(
               EEthncity.NORMAL,
               EEthncity.FLYING
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 0.4, 0.3, 0.5 )
           ),

           Stream.of(
               EAttributes.HEIGHT,
               EAttributes.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 1.4, 1, 0.7 ),
               new ImmutableTriple<>( 39.2, 36, 42 )
           ),

           Stream.of(),
           Stream.of()
    ),

    EEVEE( 4,
           Stream.of(
               EEthncity.NORMAL
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 )
           ),

           Stream.of(
               EAttributes.HEIGHT,
               EAttributes.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.3, 0.1, 5 ),
               new ImmutableTriple<>( 6.5, 5.5, 7.5 )
           ),

           Stream.of(),
           Stream.of()
    ),

    FARFETCHD( 1,
               Stream.of(
                   EEthncity.NORMAL,
                   EEthncity.FLYING
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               ),

               Stream.of(
                   EAttributes.HEIGHT,
                   EAttributes.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 15, 12.5, 17.5 )
               ),

               Stream.of(),
               Stream.of()
    ),

    JIGGLYPUFF( 2,
                Stream.of(
                    EEthncity.NORMAL,
                    EEthncity.FAIRY
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 ),
                    new ImmutableTriple<>( 0.4, 0.3, 0.5 )
                ),

                Stream.of(
                    EAttributes.HEIGHT,
                    EAttributes.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.5, 0.3, 0.7 ),
                    new ImmutableTriple<>( 5.5, 4.2, 6.5 )
                ),

                Stream.of(),
                Stream.of()
    ),

    KANGASKHAN( 1,
                Stream.of(
                    EEthncity.NORMAL
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 )
                ),

                Stream.of(
                    EAttributes.HEIGHT,
                    EAttributes.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 2.2, 1.9, 2.7 ),
                    new ImmutableTriple<>( 80, 60, 100 )
                ),

                Stream.of(),
                Stream.of()
    ),

    LICKITUNG( 1,
               Stream.of(
                   EEthncity.NORMAL
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 )
               ),

               Stream.of(
                   EAttributes.HEIGHT,
                   EAttributes.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 1.2, 1, 1.4 ),
                   new ImmutableTriple<>( 65.5, 60, 72 )
               ),

               Stream.of(),
               Stream.of()
    ),

    MEOWTH( 2,
            Stream.of(
                EEthncity.NORMAL
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 )
            ),

            Stream.of(
                EAttributes.HEIGHT,
                EAttributes.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                new ImmutableTriple<>( 4.2, 3.8, 4.7 )
            ),

            Stream.of(),
            Stream.of()
    ),

    PIDGEY( 3,
            Stream.of(
                EEthncity.NORMAL,
                EEthncity.FLYING
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),

            Stream.of(
                EAttributes.HEIGHT,
                EAttributes.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.3, 0.2, 0.5 ),
                new ImmutableTriple<>( 1.8, 1.5, 2.1 )
            ),

            Stream.of(),
            Stream.of()
    ),

    PORYGON( 1,
             Stream.of(
                 EEthncity.NORMAL
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 )
             ),

             Stream.of(
                 EAttributes.HEIGHT,
                 EAttributes.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.5, 1.1 ),
                 new ImmutableTriple<>( 36.5, 32, 39 )
             ),

             Stream.of(),
             Stream.of()
    ),

    RATTATA( 2,
             Stream.of(
                 EEthncity.NORMAL
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 )
             ),

             Stream.of(
                 EAttributes.HEIGHT,
                 EAttributes.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.3, 0.1, 0.5 ),
                 new ImmutableTriple<>( 3.5, 2.8, 3.8 )
             ),

             Stream.of(),
             Stream.of()
    ),

    SNORLAX( 1,
             Stream.of(
                 EEthncity.NORMAL
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 )
             ),

             Stream.of(
                 EAttributes.HEIGHT,
                 EAttributes.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 2.1, 1.8, 2.4 ),
                 new ImmutableTriple<>( 460, 400, 490 )
             ),

             Stream.of(),
             Stream.of()
    ),

    SPEAROW( 2,
             Stream.of(
                 EEthncity.NORMAL,
                 EEthncity.FLYING
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             ),

             Stream.of(
                 EAttributes.HEIGHT,
                 EAttributes.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.3, 0.1, 0.6 ),
                 new ImmutableTriple<>( 2, 1.2, 2.9 )
             ),

             Stream.of(),
             Stream.of()
    ),

    TAUROS( 1,
            Stream.of(
                EEthncity.NORMAL
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 )
            ),

            Stream.of(
                EAttributes.HEIGHT,
                EAttributes.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.4, 1, 1.9 ),
                new ImmutableTriple<>( 88.4, 85, 91 )
            ),

            Stream.of(),
            Stream.of()
    ),



    // --- fire ------------------------------------------------------------------------------------------------------------------------------------------------
    CHARMANDER( 3,
                Stream.of( EEthncity.FIRE ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
                Stream.of(),
                Stream.of(),
                Stream.of(),
                Stream.of()
    ),

    GROWLITHE( 2,
               Stream.of( EEthncity.FIRE ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
               Stream.of(),
               Stream.of(),
               Stream.of(),
               Stream.of()
    ),

    MAGMAR( 1,
            Stream.of( EEthncity.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    MOLTRES( 1,
             Stream.of(
                 EEthncity.FIRE,
                 EEthncity.FLYING
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    PONYTA( 2,
            Stream.of( EEthncity.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    VULPIX( 2,
            Stream.of( EEthncity.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),



    // --- water -----------------------------------------------------------------------------------------------------------------------------------------------
    GOLDEEN( 2,
             Stream.of( EEthncity.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    HORSEA( 2,
            Stream.of( EEthncity.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    KRABBY( 2,
            Stream.of( EEthncity.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    LAPRAS( 1,
            Stream.of(
                EEthncity.WATER,
                EEthncity.ICE
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    MAGIKARP( 2,
              Stream.of( EEthncity.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
              Stream.of(),
              Stream.of(),
              Stream.of(),
              Stream.of()
    ),

    POLIWAG( 3,
             Stream.of( EEthncity.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    PSYDUCK( 2,
             Stream.of( EEthncity.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    SEEL( 2,
          Stream.of( EEthncity.WATER ),
          Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
          Stream.of(),
          Stream.of(),
          Stream.of(),
          Stream.of()
    ),

    SHELLDER( 2,
              Stream.of( EEthncity.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
              Stream.of(),
              Stream.of(),
              Stream.of(),
              Stream.of()
    ),

    SLOWPOKE( 2,
              Stream.of(
                  EEthncity.WATER,
                  EEthncity.PSYCHIC
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.8, 0.6, 1 ),
                  new ImmutableTriple<>( 0.4, 0.3, 0.5 )
              ),
              Stream.of(),
              Stream.of(),
              Stream.of(),
              Stream.of()
    ),

    SQUIRTLE( 3,
              Stream.of( EEthncity.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
              Stream.of(),
              Stream.of(),
              Stream.of(),
              Stream.of()
    ),

    STARYU( 2,
            Stream.of( EEthncity.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    TENTACOOL( 2,
               Stream.of(
                   EEthncity.WATER,
                   EEthncity.POISON
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               ),
               Stream.of(),
               Stream.of(),
               Stream.of(),
               Stream.of()
    ),



    // --- electric --------------------------------------------------------------------------------------------------------------------------------------------
    ELECTABUZZ( 1,
                Stream.of( EEthncity.ELECTRIC ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
                Stream.of(),
                Stream.of(),
                Stream.of(),
                Stream.of()
    ),

    MAGNEMITE( 2,
               Stream.of(
                   EEthncity.ELECTRIC,
                   EEthncity.STEEL
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               ),
               Stream.of(),
               Stream.of(),
               Stream.of(),
               Stream.of()
    ),

    PIKACHU( 2,
             Stream.of( EEthncity.ELECTRIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    VOLTORB( 2,
             Stream.of( EEthncity.ELECTRIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    ZAPDOS( 1,
            Stream.of(
                EEthncity.ELECTRIC,
                EEthncity.FLYING
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),



    // --- grass -----------------------------------------------------------------------------------------------------------------------------------------------
    BELLSPROUT( 3,
                Stream.of(
                    EEthncity.GRASS,
                    EEthncity.POISON
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 ),
                    new ImmutableTriple<>( 0.4, 0.3, 0.5 )
                ),
                Stream.of(),
                Stream.of(),
                Stream.of(),
                Stream.of()
    ),

    BULBASAUR( 3,
               Stream.of(
                   EEthncity.GRASS,
                   EEthncity.POISON
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               ),
               Stream.of(),
               Stream.of(),
               Stream.of(),
               Stream.of()
    ),

    EXEGGCUTE( 2,
               Stream.of(
                   EEthncity.GRASS,
                   EEthncity.PSYCHIC
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 0.4, 0.3, 0.5 )
               ),
               Stream.of(),
               Stream.of(),
               Stream.of(),
               Stream.of()
    ),

    ODDISH( 3,
            Stream.of(
                EEthncity.GRASS,
                EEthncity.POISON
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    TANGELA( 2,
             Stream.of( EEthncity.GRASS ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),



    // --- ice -------------------------------------------------------------------------------------------------------------------------------------------------
    ARTICUNO( 1,
              Stream.of(
                  EEthncity.ICE,
                  EEthncity.FLYING
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.8, 0.6, 1 ),
                  new ImmutableTriple<>( 0.4, 0.3, 0.5 )
              ),
              Stream.of(),
              Stream.of(),
              Stream.of(),
              Stream.of()
    ),

    JYNX( 1,
          Stream.of(
              EEthncity.ICE,
              EEthncity.PSYCHIC
          ),
          Stream.of(
              new ImmutableTriple<>( 0.8, 0.6, 1 ),
              new ImmutableTriple<>( 0.4, 0.3, 0.5 )
          ),
          Stream.of(),
          Stream.of(),
          Stream.of(),
          Stream.of()
    ),



    // --- fighting --------------------------------------------------------------------------------------------------------------------------------------------
    HITMONCHAN( 1,
                Stream.of( EEthncity.FIGHTING ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
                Stream.of(),
                Stream.of(),
                Stream.of(),
                Stream.of()
    ),

    HITMONLEE( 1,
               Stream.of( EEthncity.FIGHTING ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
               Stream.of(),
               Stream.of(),
               Stream.of(),
               Stream.of()
    ),

    MACHOP( 3,
            Stream.of( EEthncity.FIGHTING ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    MANKEY( 2,
            Stream.of( EEthncity.FIGHTING ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),



    // --- poison ----------------------------------------------------------------------------------------------------------------------------------------------
    EKANS( 2,
           Stream.of( EEthncity.POISON ),
           Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
           Stream.of(),
           Stream.of(),
           Stream.of(),
           Stream.of()
    ),

    GRIMER( 2,
            Stream.of( EEthncity.POISON ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    KOFFING( 2,
             Stream.of( EEthncity.POISON ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    NIDORAN_FEMALE( 3,
                    Stream.of( EEthncity.POISON ),
                    Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
                    Stream.of(),
                    Stream.of(),
                    Stream.of(),
                    Stream.of()
    ),

    NIDORAN_MALE( 3,
                  Stream.of( EEthncity.POISON ),
                  Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
                  Stream.of(),
                  Stream.of(),
                  Stream.of(),
                  Stream.of()
    ),

    ZUBAT( 2,
           Stream.of(
               EEthncity.POISON,
               EEthncity.FLYING
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 0.4, 0.3, 0.5 )
           ),
           Stream.of(),
           Stream.of(),
           Stream.of(),
           Stream.of()
    ),



    // --- ground ----------------------------------------------------------------------------------------------------------------------------------------------
    CUBONE( 2,
            Stream.of( EEthncity.GROUND ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    DIGLETT( 2,
             Stream.of( EEthncity.GROUND ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    RHYHORN( 2,
             Stream.of(
                 EEthncity.GROUND,
                 EEthncity.ROCK
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    SANDSHREW( 2,
               Stream.of( EEthncity.GROUND ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
               Stream.of(),
               Stream.of(),
               Stream.of(),
               Stream.of()
    ),



    // --- psychic ---------------------------------------------------------------------------------------------------------------------------------------------
    ABRA( 3,
          Stream.of( EEthncity.PSYCHIC ),
          Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
          Stream.of(),
          Stream.of(),
          Stream.of(),
          Stream.of()
    ),

    DROWZEE( 2,
             Stream.of( EEthncity.PSYCHIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    MEW( 1,
         Stream.of( EEthncity.PSYCHIC ),
         Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
         Stream.of(),
         Stream.of(),
         Stream.of(),
         Stream.of()
    ),

    MEWTWO( 1,
            Stream.of( EEthncity.PSYCHIC ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    MR_MIME( 1,
             Stream.of(
                 EEthncity.PSYCHIC,
                 EEthncity.FAIRY
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),



    // --- bug -------------------------------------------------------------------------------------------------------------------------------------------------
    CATERPIE( 3,
              Stream.of( EEthncity.BUG ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
              Stream.of(),
              Stream.of(),
              Stream.of(),
              Stream.of()
    ),

    PARAS( 2,
           Stream.of(
               EEthncity.BUG,
               EEthncity.GRASS
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 0.4, 0.3, 0.5 )
           ),
           Stream.of(),
           Stream.of(),
           Stream.of(),
           Stream.of()
    ),

    PINSIR( 1,
            Stream.of( EEthncity.BUG ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    SCYTHER( 1,
             Stream.of(
                 EEthncity.BUG,
                 EEthncity.FLYING
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    VENONAT( 2,
             Stream.of(
                 EEthncity.BUG,
                 EEthncity.POISON
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    WEEDLE( 2,
            Stream.of(
                EEthncity.BUG,
                EEthncity.POISON
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),



    // --- rock ------------------------------------------------------------------------------------------------------------------------------------------------
    AERODACTYL( 1,
                Stream.of(
                    EEthncity.ROCK,
                    EEthncity.FLYING
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 ),
                    new ImmutableTriple<>( 0.4, 0.3, 0.5 )
                ),
                Stream.of(),
                Stream.of(),
                Stream.of(),
                Stream.of()
    ),

    GEODUDE( 3,
             Stream.of(
                 EEthncity.ROCK,
                 EEthncity.GROUND
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    KABUTO( 2,
            Stream.of(
                EEthncity.ROCK,
                EEthncity.WATER
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),

    OMANYTE( 2,
             Stream.of(
                 EEthncity.ROCK,
                 EEthncity.WATER
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 )
             ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),

    ONIX( 1,
          Stream.of(
              EEthncity.ROCK,
              EEthncity.GROUND
          ),
          Stream.of(
              new ImmutableTriple<>( 0.8, 0.6, 1 ),
              new ImmutableTriple<>( 0.4, 0.3, 0.5 )
          ),
          Stream.of(),
          Stream.of(),
          Stream.of(),
          Stream.of()
    ),



    // --- ghost -----------------------------------------------------------------------------------------------------------------------------------------------
    GASTLY( 3,
            Stream.of(
                EEthncity.GHOST,
                EEthncity.POISON
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),
            Stream.of(),
            Stream.of(),
            Stream.of(),
            Stream.of()
    ),



    // --- dragon ----------------------------------------------------------------------------------------------------------------------------------------------
    DRATINI( 3,
             Stream.of( EEthncity.DRAGON ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
             Stream.of(),
             Stream.of(),
             Stream.of(),
             Stream.of()
    ),



    // --- fairy -----------------------------------------------------------------------------------------------------------------------------------------------
    CLEFAIRY( 2,
              Stream.of( EEthncity.FAIRY ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),
              Stream.of(),
              Stream.of(),
              Stream.of(),
              Stream.of()
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
     * ethnic map map with initialize values (initial value, min, max bounding)
     */
    private final Map<EEthncity, Triple<AbstractRealDistribution, Number, Number>> m_ethnic;
    /**
     * attribute map with initialize values (initial value, min, max bounding)
     */
    private final Map<EAttributes, Triple<AbstractRealDistribution, Number, Number>> m_attributes;
    /**
     * motivation map with initialize values (initial value, min, max bounding)
     */
    private final Map<EMotivation, Triple<AbstractRealDistribution, Number, Number>> m_motivation;


    /**
     * ctor
     *
     * @param p_icons number of icons
     * @param p_ethnic stream with ethnik types
     * @param p_ethnic stream with ethnic values
     * @param p_attributes stream with attributes types
     * @param p_attributesvalue stream with attributes values
     * @param p_motivation stream with motivation types
     * @param p_motivationvalue stream with motivation value
     */
    EPokemon( final int p_icons,
              final Stream<EEthncity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
              final Stream<EAttributes> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue,
              final Stream<EMotivation> p_motivation, final Stream<Triple<Number, Number, Number>> p_motivationvalue
    )
    {
        m_icons = p_icons;
        m_ethnic = EPokemon.initialize( p_ethnic, p_ethnicvalue );
        m_attributes = EPokemon.initialize( p_attributes, p_attributesvalue );
        m_motivation = EPokemon.initialize( p_motivation, p_motivationvalue );
    }

    /**
     * generates the random distribution for the values
     *
     * @param p_keys keys
     * @param p_value tripel value set (initial value, min, max bounding)
     * @tparam T key type
     * @return map
     */
    private static <T>  Map<T, Triple<AbstractRealDistribution, Number, Number>> initialize( final Stream<T> p_keys, final Stream<Triple<Number, Number, Number>> p_value )
    {
        return Collections.unmodifiableMap(
            StreamUtils.zip(
                p_keys,
                p_value,
                ( n, v ) -> new ImmutablePair<>(
                    n,
                    new ImmutableTriple<AbstractRealDistribution, Number, Number>(
                        new NormalDistribution( CMath.RANDOMGENERATOR, v.getLeft().doubleValue(), 0.1 ),
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
     * @tparam T key type
     * @return value map
     */
    private static <T> Map<T, Number> generate( final Map<T, Triple<AbstractRealDistribution, Number, Number>> p_map )
    {
        return p_map.entrySet().parallelStream().collect(
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
     * generates the ethnic map of a pokemon
     * @return map individual ethnic values
     */
    public Map<EEthncity, Number> ethnic()
    {
        return EPokemon.generate( m_ethnic );
    }

    /**
     * generates the attribute map of a pokemon
     * @return map individual attributes values
     */
    public Map<EAttributes, Number> attributes()
    {
        return EPokemon.generate( m_attributes );
    }

    /**
     * generates the attribute map of a pokemon
     * @return map individual attributes values
     */
    public Map<EMotivation, Number> motivation()
    {
        return EPokemon.generate( m_motivation );
    }
}
