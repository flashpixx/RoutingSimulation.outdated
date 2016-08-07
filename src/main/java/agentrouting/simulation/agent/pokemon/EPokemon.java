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
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1.1, 0.8, 1.3 ),
                 new ImmutableTriple<>( 34.6, 30, 40 )
             )
    ),

    DITTO( 1,
           Stream.of(
               EEthncity.NORMAL
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 )
           ),

           Stream.of(
               EAttribute.HEIGHT,
               EAttribute.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.3, 0.1, 0.5 ),
               new ImmutableTriple<>( 4, 3.5, 4.5 )
           )
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
               EAttribute.HEIGHT,
               EAttribute.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 1.4, 1, 0.7 ),
               new ImmutableTriple<>( 39.2, 36, 42 )
           )
    ),

    EEVEE( 4,
           Stream.of(
               EEthncity.NORMAL
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 )
           ),

           Stream.of(
               EAttribute.HEIGHT,
               EAttribute.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.3, 0.1, 5 ),
               new ImmutableTriple<>( 6.5, 5.5, 7.5 )
           )
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
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 ),
                   new ImmutableTriple<>( 15, 12.5, 17.5 )
               )
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
                    EAttribute.HEIGHT,
                    EAttribute.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.5, 0.3, 0.7 ),
                    new ImmutableTriple<>( 5.5, 4.2, 6.5 )
                )
    ),

    KANGASKHAN( 1,
                Stream.of(
                    EEthncity.NORMAL
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.8, 0.6, 1 )
                ),

                Stream.of(
                    EAttribute.HEIGHT,
                    EAttribute.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 2.2, 1.9, 2.7 ),
                    new ImmutableTriple<>( 80, 60, 100 )
                )
    ),

    LICKITUNG( 1,
               Stream.of(
                   EEthncity.NORMAL
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.8, 0.6, 1 )
               ),

               Stream.of(
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 1.2, 1, 1.4 ),
                   new ImmutableTriple<>( 65.5, 60, 72 )
               )
    ),

    MEOWTH( 2,
            Stream.of(
                EEthncity.NORMAL
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 )
            ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                new ImmutableTriple<>( 4.2, 3.8, 4.7 )
            )
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
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.3, 0.2, 0.5 ),
                new ImmutableTriple<>( 1.8, 1.5, 2.1 )
            )
    ),

    PORYGON( 1,
             Stream.of(
                 EEthncity.NORMAL
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 )
             ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.5, 1.1 ),
                 new ImmutableTriple<>( 36.5, 32, 39 )
             )
    ),

    RATTATA( 2,
             Stream.of(
                 EEthncity.NORMAL
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 )
             ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.3, 0.1, 0.5 ),
                 new ImmutableTriple<>( 3.5, 2.8, 3.8 )
             )
    ),

    SNORLAX( 1,
             Stream.of(
                 EEthncity.NORMAL
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 )
             ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 2.1, 1.8, 2.4 ),
                 new ImmutableTriple<>( 460, 400, 490 )
             )
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
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.3, 0.1, 0.6 ),
                 new ImmutableTriple<>( 2, 1.2, 2.9 )
             )
    ),

    TAUROS( 1,
            Stream.of(
                EEthncity.NORMAL
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 )
            ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.4, 1, 1.9 ),
                new ImmutableTriple<>( 88.4, 85, 91 )
            )
    ),



    // --- fire ------------------------------------------------------------------------------------------------------------------------------------------------
    CHARMANDER( 3,
                Stream.of( EEthncity.FIRE ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

                Stream.of(
                    EAttribute.HEIGHT,
                    EAttribute.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.6, 0.3, 0.9 ),
                    new ImmutableTriple<>( 8.5, 7.5, 9.5 )
                )
    ),

    GROWLITHE( 2,
               Stream.of( EEthncity.FIRE ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

               Stream.of(
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.7, 0.4, 1 ),
                   new ImmutableTriple<>( 19, 18, 20 )
               )
    ),

    MAGMAR( 1,
            Stream.of( EEthncity.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.3, 1, 1.6 ),
                new ImmutableTriple<>( 44.5, 42.5, 46.5 )
            )
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

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 2, 1.8, 2.2 ),
                 new ImmutableTriple<>( 60, 56, 64 )
             )
    ),

    PONYTA( 2,
            Stream.of( EEthncity.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1, 0.7, 1.7 ),
                new ImmutableTriple<>( 30, 25, 35 )
            )
    ),

    VULPIX( 2,
            Stream.of( EEthncity.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.6, 0.4, 1.8 ),
                new ImmutableTriple<>( 9.9, 0.5, 10.3 )
            )
    ),



    // --- water -----------------------------------------------------------------------------------------------------------------------------------------------
    GOLDEEN( 2,
             Stream.of( EEthncity.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.6, 0.4, 1.8 ),
                 new ImmutableTriple<>( 15, 13, 17 )
             )
    ),

    HORSEA( 2,
            Stream.of( EEthncity.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                new ImmutableTriple<>( 8, 6, 10 )
            )
    ),

    KRABBY( 2,
            Stream.of( EEthncity.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                new ImmutableTriple<>( 6.5, 6, 7 )
            )
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

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 2.5, 2, 3 ),
                new ImmutableTriple<>( 220, 200, 240 )
            )
    ),

    MAGIKARP( 2,
              Stream.of( EEthncity.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

              Stream.of(
                  EAttribute.HEIGHT,
                  EAttribute.WEIGHT
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.9, 0.7, 1.1 ),
                  new ImmutableTriple<>( 10, 8, 12 )
              )
    ),

    POLIWAG( 3,
             Stream.of( EEthncity.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.6, 0.4, 0.8 ),
                 new ImmutableTriple<>( 12.4, 10, 14 )
             )
    ),

    PSYDUCK( 2,
             Stream.of( EEthncity.WATER ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.8, 0.6, 1 ),
                 new ImmutableTriple<>( 19.6, 17, 21 )
             )
    ),

    SEEL( 2,
          Stream.of( EEthncity.WATER ),
          Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

          Stream.of(
              EAttribute.HEIGHT,
              EAttribute.WEIGHT
          ),
          Stream.of(
              new ImmutableTriple<>( 1.1, 0.9, 1.3 ),
              new ImmutableTriple<>( 90, 80, 100 )
          )
    ),

    SHELLDER( 2,
              Stream.of( EEthncity.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

              Stream.of(
                  EAttribute.HEIGHT,
                  EAttribute.WEIGHT
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.3, 0.2, 0.4 ),
                  new ImmutableTriple<>( 4, 3, 5 )
              )
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

              Stream.of(
                  EAttribute.HEIGHT,
                  EAttribute.WEIGHT
              ),
              Stream.of(
                  new ImmutableTriple<>( 1.2, 1, 1.4 ),
                  new ImmutableTriple<>( 36, 32, 40 )
              )
    ),

    SQUIRTLE( 3,
              Stream.of( EEthncity.WATER ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

              Stream.of(
                  EAttribute.HEIGHT,
                  EAttribute.WEIGHT
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.5, 0.4, 0.6 ),
                  new ImmutableTriple<>( 9, 8, 7 )
              )
    ),

    STARYU( 2,
            Stream.of( EEthncity.WATER ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 34.5, 30, 39 )
            )
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

               Stream.of(
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.9, 0.6, 1.2 ),
                   new ImmutableTriple<>( 45.5, 40, 50 )
               )
    ),



    // --- electric --------------------------------------------------------------------------------------------------------------------------------------------
    ELECTABUZZ( 1,
                Stream.of( EEthncity.ELECTRIC ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

                Stream.of(
                    EAttribute.HEIGHT,
                    EAttribute.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 1.1, 0.9, 1.3 ),
                    new ImmutableTriple<>( 30, 25, 35 )
                )
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

               Stream.of(
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.3, 0.1, 0.5 ),
                   new ImmutableTriple<>( 6, 4, 8 )
               )
    ),

    PIKACHU( 2,
             Stream.of( EEthncity.ELECTRIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 ),
                 new ImmutableTriple<>( 6, 5, 7 )
             )
    ),

    VOLTORB( 2,
             Stream.of( EEthncity.ELECTRIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.5, 0.4, 0.6 ),
                 new ImmutableTriple<>( 10.4, 9, 12 )
             )
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

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.6, 1.3, 1.9 ),
                new ImmutableTriple<>( 52.6, 49, 55 )
            )
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

                Stream.of(
                    EAttribute.HEIGHT,
                    EAttribute.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 0.7, 0.5, 0.9 ),
                    new ImmutableTriple<>( 4, 3, 5 )
                )
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

               Stream.of(
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.7, 0.5, 0.9 ),
                   new ImmutableTriple<>( 6.9, 6, 8 )
               )
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

               Stream.of(
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                   new ImmutableTriple<>( 2.5, 1.5, 3.5 )
               )
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

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.5, 0.2, 0.8 ),
                new ImmutableTriple<>( 5.4, 4.5, 6.5 )
            )
    ),

    TANGELA( 2,
             Stream.of( EEthncity.GRASS ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1, 0.7, 1.3 ),
                 new ImmutableTriple<>( 35, 30, 40 )
             )
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

              Stream.of(
                  EAttribute.HEIGHT,
                  EAttribute.WEIGHT
              ),
              Stream.of(
                  new ImmutableTriple<>( 1.7, 1.2, 2.2 ),
                  new ImmutableTriple<>( 55.4, 50, 60 )
              )
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

          Stream.of(
              EAttribute.HEIGHT,
              EAttribute.WEIGHT
          ),
          Stream.of(
              new ImmutableTriple<>( 1.4, 1, 1.8 ),
              new ImmutableTriple<>( 40.6, 35, 45 )
          )
    ),



    // --- fighting --------------------------------------------------------------------------------------------------------------------------------------------
    HITMONCHAN( 1,
                Stream.of( EEthncity.FIGHTING ),
                Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

                Stream.of(
                    EAttribute.HEIGHT,
                    EAttribute.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 1.4, 1, 1.8 ),
                    new ImmutableTriple<>( 50.2, 45, 55 )
                )
    ),

    HITMONLEE( 1,
               Stream.of( EEthncity.FIGHTING ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

               Stream.of(
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 1.5, 1, 2 ),
                   new ImmutableTriple<>( 49.8, 40, 58 )
               )
    ),

    MACHOP( 3,
            Stream.of( EEthncity.FIGHTING ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 19.5, 18, 21 )
            )
    ),

    MANKEY( 2,
            Stream.of( EEthncity.FIGHTING ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.5, 0.3, 0.7 ),
                new ImmutableTriple<>( 28, 25, 31 )
            )
    ),



    // --- poison ----------------------------------------------------------------------------------------------------------------------------------------------
    EKANS( 2,
           Stream.of( EEthncity.POISON ),
           Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

           Stream.of(
               EAttribute.HEIGHT,
               EAttribute.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 2, 1.8, 2.4 ),
               new ImmutableTriple<>( 6.9, 6, 7.8 )
           )
    ),

    GRIMER( 2,
            Stream.of( EEthncity.POISON ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.9, 0.7, 1.1 ),
                new ImmutableTriple<>( 30, 27, 33 )
            )
    ),

    KOFFING( 2,
             Stream.of( EEthncity.POISON ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.6, 0.4, 0.8 ),
                 new ImmutableTriple<>( 1, 0.8, 1.2 )
             )
    ),

    NIDORAN_FEMALE( 3,
                    Stream.of( EEthncity.POISON ),
                    Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

                    Stream.of(
                        EAttribute.HEIGHT,
                        EAttribute.WEIGHT
                    ),
                    Stream.of(
                        new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                        new ImmutableTriple<>( 7, 5, 9 )
                    )
    ),

    NIDORAN_MALE( 3,
                  Stream.of( EEthncity.POISON ),
                  Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

                  Stream.of(
                      EAttribute.HEIGHT,
                      EAttribute.WEIGHT
                  ),
                  Stream.of(
                      new ImmutableTriple<>( 0.5, 0.3, 0.7 ),
                      new ImmutableTriple<>( 9, 7, 11 )
                  )
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

           Stream.of(
               EAttribute.HEIGHT,
               EAttribute.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.8, 0.6, 1 ),
               new ImmutableTriple<>( 7.5, 6.5, 8.5 )
           )
    ),



    // --- ground ----------------------------------------------------------------------------------------------------------------------------------------------
    CUBONE( 2,
            Stream.of( EEthncity.GROUND ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                new ImmutableTriple<>( 6.5, 5, 8 )
            )
    ),

    DIGLETT( 2,
             Stream.of( EEthncity.GROUND ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.2, 0.1, 0.3 ),
                 new ImmutableTriple<>( 0.8, 0.6, 1 )
             )
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

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1, 0.8, 1.2 ),
                 new ImmutableTriple<>( 115, 105, 125 )
             )
    ),

    SANDSHREW( 2,
               Stream.of( EEthncity.GROUND ),
               Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

               Stream.of(
                   EAttribute.HEIGHT,
                   EAttribute.WEIGHT
               ),
               Stream.of(
                   new ImmutableTriple<>( 0.6, 0.4, 0.8 ),
                   new ImmutableTriple<>( 12, 8, 16 )
               )
    ),



    // --- psychic ---------------------------------------------------------------------------------------------------------------------------------------------
    ABRA( 3,
          Stream.of( EEthncity.PSYCHIC ),
          Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

          Stream.of(
              EAttribute.HEIGHT,
              EAttribute.WEIGHT
          ),
          Stream.of(
              new ImmutableTriple<>( 0.9, 0.5, 1.3 ),
              new ImmutableTriple<>( 19.5, 17, 22 )
          )
    ),

    DROWZEE( 2,
             Stream.of( EEthncity.PSYCHIC ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1, 0.6, 1.4 ),
                 new ImmutableTriple<>( 32.4, 28, 36.4 )
             )
    ),

    MEW( 1,
         Stream.of( EEthncity.PSYCHIC ),
         Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

         Stream.of(
             EAttribute.HEIGHT,
             EAttribute.WEIGHT
         ),
         Stream.of(
             new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
             new ImmutableTriple<>( 4, 2.5, 5.5 )
         )
    ),

    MEWTWO( 1,
            Stream.of( EEthncity.PSYCHIC ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 2, 1, 3 ),
                new ImmutableTriple<>( 122, 116, 128 )
            )
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

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1.3, 1, 1.6 ),
                 new ImmutableTriple<>( 54.5, 50, 59 )
             )
    ),



    // --- bug -------------------------------------------------------------------------------------------------------------------------------------------------
    CATERPIE( 3,
              Stream.of( EEthncity.BUG ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

              Stream.of(
                  EAttribute.HEIGHT,
                  EAttribute.WEIGHT
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.3, 0.2, 0.4 ),
                  new ImmutableTriple<>( 2.9, 2.5, 3.3 )
              )
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

           Stream.of(
               EAttribute.HEIGHT,
               EAttribute.WEIGHT
           ),
           Stream.of(
               new ImmutableTriple<>( 0.3, 0.2, 0.4 ),
               new ImmutableTriple<>( 5.4, 5, 5.8 )
           )
    ),

    PINSIR( 1,
            Stream.of( EEthncity.BUG ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.5, 1.2, 1.8 ),
                new ImmutableTriple<>( 55, 50, 60 )
            )
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

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1.5, 1.2, 1.8 ),
                 new ImmutableTriple<>( 56, 50, 62 )
             )
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

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1, 0.5, 1.5 ),
                 new ImmutableTriple<>( 30, 25, 35 )
             )
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

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.3, 0.1, 0.5 ),
                new ImmutableTriple<>( 3.2, 2.6, 3.8 )
            )
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

                Stream.of(
                    EAttribute.HEIGHT,
                    EAttribute.WEIGHT
                ),
                Stream.of(
                    new ImmutableTriple<>( 1.8, 1.5, 2.1 ),
                    new ImmutableTriple<>( 59, 50, 68 )
                )
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

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                 new ImmutableTriple<>( 20, 16, 24 )
             )
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

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.5, 0.3, 0.7 ),
                new ImmutableTriple<>( 11.5, 9, 13 )
            )
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

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 0.4, 0.3, 0.5 ),
                 new ImmutableTriple<>( 7.5, 6.5, 8.5 )
             )
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

          Stream.of(
              EAttribute.HEIGHT,
              EAttribute.WEIGHT
          ),
          Stream.of(
              new ImmutableTriple<>( 8.8, 7, 10.6 ),
              new ImmutableTriple<>( 210, 180, 240 )
          )
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

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.3, 1, 1.6 ),
                new ImmutableTriple<>( 0.1, 0.05, 0.2 )
            )
    ),



    // --- dragon ----------------------------------------------------------------------------------------------------------------------------------------------
    DRATINI( 3,
             Stream.of( EEthncity.DRAGON ),
             Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

             Stream.of(
                 EAttribute.HEIGHT,
                 EAttribute.WEIGHT
             ),
             Stream.of(
                 new ImmutableTriple<>( 1.8, 1.6, 2 ),
                 new ImmutableTriple<>( 3.3, 3, 3.6 )
             )
    ),



    // --- fairy -----------------------------------------------------------------------------------------------------------------------------------------------
    CLEFAIRY( 2,
              Stream.of( EEthncity.FAIRY ),
              Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

              Stream.of(
                  EAttribute.HEIGHT,
                  EAttribute.WEIGHT
              ),
              Stream.of(
                  new ImmutableTriple<>( 0.6, 0.4, 0.8 ),
                  new ImmutableTriple<>( 7.5, 6, 9 )
              )
    );



    /**
     * number of level
     */
    private final int m_level;
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
    private final Map<EAttribute, Triple<AbstractRealDistribution, Number, Number>> m_attributes;
    /**
     * motivation map with initialize values (initial value, min, max bounding)
     */
    private final Map<EMotivation, Triple<AbstractRealDistribution, Number, Number>> m_motivation;


    /**
     * ctor
     *
     * @param p_level number of level
     */
    EPokemon( final int p_level )
    {
        this( p_level,
              Stream.of(), Stream.of(),
              Stream.of(), Stream.of(),
              Stream.of(), Stream.of()
        );
    }

    /**
     * ctor
     *
     * @param p_level number of level
     * @param p_ethnic stream with ethnic types
     * @param p_ethnicvalue stream with ethnic values
     */
    EPokemon( final int p_level,
              final Stream<EEthncity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue
    )
    {
        this( p_level,
              p_ethnic, p_ethnicvalue,
              Stream.of(), Stream.of(),
              Stream.of(), Stream.of()
        );
    }

    /**
     * ctor
     *
     * @param p_level number of level
     * @param p_ethnic stream with ethnic types
     * @param p_ethnicvalue stream with ethnic values
     * @param p_attributes stream with attributes types
     * @param p_attributesvalue stream with attributes values
     */
    EPokemon( final int p_level,
              final Stream<EEthncity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
              final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue
    )
    {
        this( p_level,
              p_ethnic, p_ethnicvalue,
              p_attributes, p_attributesvalue,
              Stream.of(), Stream.of()
        );
    }

    /**
     * ctor
     *
     * @param p_level number of level
     * @param p_ethnic stream with ethnic types
     * @param p_ethnicvalue stream with ethnic values
     * @param p_attributes stream with attributes types
     * @param p_attributesvalue stream with attributes values
     * @param p_motivation stream with motivation types
     * @param p_motivationvalue stream with motivation value
     */
    EPokemon( final int p_level,
              final Stream<EEthncity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
              final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue,
              final Stream<EMotivation> p_motivation, final Stream<Triple<Number, Number, Number>> p_motivationvalue
    )
    {
        m_level = p_level;
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
     * @tparam T key type
     * @return value map
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

    /**
     * returns the maxium level
     *
     * @return maximum level number
     */
    public final int level()
    {
        return m_level;
    }

    /**
     * initialize sprites
     *
     * @return first texture object
     */
    public final synchronized Texture texture()
    {
        if ( m_sprites != null )
            return m_sprites.get( 0 );

        m_sprites = Collections.unmodifiableList(
            IntStream.range( 0, m_level )
                     .mapToObj( i -> new Texture(
                                        Gdx.files.internal(
                                            MessageFormat.format( "agentrouting/pokemon/{0}_{1}.png", this.name().toLowerCase().replaceAll( " ", "_" ), i )
                                        )
                                    )
                     )
                     .filter( i -> i != null )
                     .collect( Collectors.toList() )
        );

        if ( ( m_sprites.isEmpty() ) || ( m_level != m_sprites.size() ) )
            throw new RuntimeException( MessageFormat.format( "texture [{0}] cannot initialize", this ) );

        return m_sprites.get( 0 );
    }

    /**
     * returns a version of the sprite
     *
     * @param p_index level
     * @return sprite texture
     */
    public final Texture texture( final int p_index )
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
    public Map<EAttribute, Number> attributes()
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
