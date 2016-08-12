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

package agentrouting.simulation.agent.pokemon;

import agentrouting.simulation.CMath;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import java.util.Set;
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
    CHANSEY(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 )
            ),
            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT,
                EAttribute.VIEWANGLE,
                EAttribute.VIEWRANGE,
                EAttribute.NEARBY
            ),
            Stream.of(
                new ImmutableTriple<>( 1.1, 0.8, 1.3 ),
                new ImmutableTriple<>( 34.6, 30, 40 ),
                new ImmutableTriple<>( 110, 105, 120 ),
                new ImmutableTriple<>( 2, 1, 3 ),
                new ImmutableTriple<>( 2, 1, 3 )
            ),
            Stream.of(
                EMotivation.HELP,
                EMotivation.CURE,
                EMotivation.CAUTIOUSNESS
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),
            Stream.of(
                EAttack.POUND,
                EAttack.COMETPUNCH,
                EAttack.DOUBLESLAP
            )
        )
    ),

    DITTO(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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
        )
    ),

    DODUO(
        2,

        // doduo
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL,
                EEthnicity.FLYING
            ),
            Stream.of(
                new ImmutableTriple<>( 0.8, 0.6, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT,
                EAttribute.NEARBY,
                EAttribute.VIEWRANGE,
                EAttribute.VIEWANGLE
            ),

            Stream.of(
                new ImmutableTriple<>( 1.4, 1, 1.7 ),
                new ImmutableTriple<>( 39.2, 36, 42 ),
                new ImmutableTriple<>( 3, 1, 4 ),
                new ImmutableTriple<>( 3, 1, 4 ),
                new ImmutableTriple<>( 170, 160, 180 )
            ),

            Stream.of(
                EMotivation.HUNT,
                EMotivation.IGNORANCE
            ),

            Stream.of(
                new ImmutableTriple<>( 0.85, 0.7, 1 ),
                new ImmutableTriple<>( 0.4, 0.3, 0.5 )
            ),

            Stream.of(
                EAttack.DRILLPECK,
                EAttack.SLAM
            )
        ),

        // dodo
        CLevelTupel.generate(
            Stream.of(), Stream.of(),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.8, 1.6, 2 ),
                new ImmutableTriple<>( 85.2, 80, 90 )
            )
        )
    ),

    EEVEE(
        4,

        // eevee
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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

        // vaporeon
        CLevelTupel.generate(),

        // jolteon
        CLevelTupel.generate(),

        // flareon
        CLevelTupel.generate()
    ),

    FARFETCHD(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL,
                EEthnicity.FLYING
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
        )
    ),

    JIGGLYPUFF(
        2,

        // jigglypuff
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL,
                EEthnicity.FAIRY
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

        // wigglytuff
        CLevelTupel.generate()
    ),

    KANGASKHAN(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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
        )
    ),

    LICKITUNG(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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
        )
    ),

    MEOWTH(
        2,

        // meowth
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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

        // persian
        CLevelTupel.generate(
            Stream.of(), Stream.of(),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1, 0.85, 1.3 ),
                new ImmutableTriple<>( 32, 29, 35 )
            )
        )
    ),

    PIDGEY(
        3,

        // pidgey
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL,
                EEthnicity.FLYING
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

        // pidgeotto
        CLevelTupel.generate(),

        // pidgeot
        CLevelTupel.generate()
    ),

    PORYGON(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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
        )
    ),

    RATTATA(
        2,

        // rattata
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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

        // raticate
        CLevelTupel.generate()
    ),

    SNORLAX(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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
        )
    ),

    SPEAROW(
        2,

        // spearow
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL,
                EEthnicity.FLYING
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

        // fearow
        CLevelTupel.generate(
            Stream.of(), Stream.of(),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.2, 1, 0.4 ),
                new ImmutableTriple<>( 38, 35, 40 )
            )
        )
    ),

    TAUROS(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.NORMAL
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
        )
    ),



    // --- fire ------------------------------------------------------------------------------------------------------------------------------------------------
    CHARMANDER(
        3,

        // charmander
        CLevelTupel.generate(
            Stream.of( EEthnicity.FIRE ),
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

        // charmeleon
        CLevelTupel.generate(),

        // charizard
        CLevelTupel.generate()
    ),

    GROWLITHE(
        2,

        // growlithe
        CLevelTupel.generate(
            Stream.of( EEthnicity.FIRE ),
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

        // arcanine
        CLevelTupel.generate()
    ),

    MAGMAR(
        1,

        CLevelTupel.generate(
            Stream.of( EEthnicity.FIRE ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.3, 1, 1.6 ),
                new ImmutableTriple<>( 44.5, 42.5, 46.5 )
            )
        )
    ),

    MOLTRES(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.FIRE,
                EEthnicity.FLYING
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
        )
    ),

    PONYTA(
        2,

        // ponyta
        CLevelTupel.generate(
            Stream.of( EEthnicity.FIRE ),
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

        // rapidash
        CLevelTupel.generate()
    ),

    VULPIX(
        2,

        // vulpix
        CLevelTupel.generate(
            Stream.of( EEthnicity.FIRE ),
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

        // ninetales
        CLevelTupel.generate()
    ),



    // --- water -----------------------------------------------------------------------------------------------------------------------------------------------
    GOLDEEN(
        2,

        // goldeen
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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

        // seaking
        CLevelTupel.generate()
    ),

    HORSEA(
        2,

        // horsea
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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

        // kingdra
        CLevelTupel.generate(
            Stream.of(), Stream.of(),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.8, 1.6, 2.1 ),
                new ImmutableTriple<>( 152, 140, 165 )
            )
        )
    ),

    KRABBY(
        2,

        // krabby
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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

        // kingler
        CLevelTupel.generate()
    ),

    LAPRAS(
        1,

        CLevelTupel.generate(
            Stream.of(
                EEthnicity.WATER,
                EEthnicity.ICE
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
        )
    ),

    MAGIKARP(
        2,

        // magikarp
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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

        // skullkraken
        CLevelTupel.generate()
    ),

    POLIWAG(
        3,

        // poliwag
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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

        // poliwhirl
        CLevelTupel.generate(),

        // poliwrath
        CLevelTupel.generate()
    ),

    PSYDUCK(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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
        CLevelTupel.generate()
    ),

    SEEL(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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
        CLevelTupel.generate()
    ),

    SHELLDER(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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
        CLevelTupel.generate()
    ),

    SLOWPOKE(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.WATER,
                EEthnicity.PSYCHIC
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
        CLevelTupel.generate()
    ),

    SQUIRTLE(
        3,
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    STARYU(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.WATER ),
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
        CLevelTupel.generate()
    ),

    TENTACOOL(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.WATER,
                EEthnicity.POISON
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
        CLevelTupel.generate()
    ),



    // --- electric --------------------------------------------------------------------------------------------------------------------------------------------
    ELECTABUZZ(
        1,
        CLevelTupel.generate(
            Stream.of( EEthnicity.ELECTRIC ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.1, 0.9, 1.3 ),
                new ImmutableTriple<>( 30, 25, 35 )
            )
        )
    ),

    MAGNEMITE(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ELECTRIC,
                EEthnicity.STEEL
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
        CLevelTupel.generate()
    ),

    PIKACHU(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.ELECTRIC ),
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
        CLevelTupel.generate()
    ),

    VOLTORB(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.ELECTRIC ),
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
        CLevelTupel.generate()
    ),

    ZAPDOS(
        1,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ELECTRIC,
                EEthnicity.FLYING
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
        )
    ),



    // --- grass -----------------------------------------------------------------------------------------------------------------------------------------------
    BELLSPROUT(
        3,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.GRASS,
                EEthnicity.POISON
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    BULBASAUR(
        3,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.GRASS,
                EEthnicity.POISON
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    EXEGGCUTE(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.GRASS,
                EEthnicity.PSYCHIC
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
        CLevelTupel.generate()
    ),

    ODDISH(
        3,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.GRASS,
                EEthnicity.POISON
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    TANGELA(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.GRASS ),
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
        CLevelTupel.generate()
    ),



    // --- ice -------------------------------------------------------------------------------------------------------------------------------------------------
    ARTICUNO(
        1,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ICE,
                EEthnicity.FLYING
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
        )
    ),

    JYNX(
        1,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ICE,
                EEthnicity.PSYCHIC
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
        )
    ),



    // --- fighting --------------------------------------------------------------------------------------------------------------------------------------------
    HITMONCHAN(
        1,
        CLevelTupel.generate(
            Stream.of( EEthnicity.FIGHTING ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.4, 1, 1.8 ),
                new ImmutableTriple<>( 50.2, 45, 55 )
            )
        )
    ),

    HITMONLEE(
        1,
        CLevelTupel.generate(
            Stream.of( EEthnicity.FIGHTING ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.5, 1, 2 ),
                new ImmutableTriple<>( 49.8, 40, 58 )
            )
        )
    ),

    MACHOP(
        3,
        CLevelTupel.generate(
            Stream.of( EEthnicity.FIGHTING ),
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    MANKEY(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.FIGHTING ),
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
        CLevelTupel.generate()
    ),



    // --- poison ----------------------------------------------------------------------------------------------------------------------------------------------
    EKANS(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.POISON ),
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
        CLevelTupel.generate()
    ),

    GRIMER(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.POISON ),
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
        CLevelTupel.generate()
    ),

    KOFFING(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.POISON ),
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
        CLevelTupel.generate()
    ),

    NIDORAN_FEMALE(
        3,
        CLevelTupel.generate(
            Stream.of( EEthnicity.POISON ),
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    NIDORAN_MALE(
        3,
        CLevelTupel.generate(
            Stream.of( EEthnicity.POISON ),
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    ZUBAT(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.POISON,
                EEthnicity.FLYING
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
        CLevelTupel.generate()
    ),



    // --- ground ----------------------------------------------------------------------------------------------------------------------------------------------
    CUBONE(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.GROUND ),
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
        CLevelTupel.generate()
    ),

    DIGLETT(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.GROUND ),
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
        CLevelTupel.generate()
    ),

    RHYHORN(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.GROUND,
                EEthnicity.ROCK
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
        CLevelTupel.generate()
    ),

    SANDSHREW(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.GROUND ),
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
        CLevelTupel.generate()
    ),



    // --- psychic ---------------------------------------------------------------------------------------------------------------------------------------------
    ABRA(
        3,
        CLevelTupel.generate(
            Stream.of( EEthnicity.PSYCHIC ),
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    DROWZEE(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.PSYCHIC ),
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
        CLevelTupel.generate()
    ),

    MEW(
        1,
        CLevelTupel.generate(
            Stream.of( EEthnicity.PSYCHIC ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.4, 0.2, 0.6 ),
                new ImmutableTriple<>( 4, 2.5, 5.5 )
            )
        )
    ),

    MEWTWO(
        1,
        CLevelTupel.generate(
            Stream.of( EEthnicity.PSYCHIC ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 2, 1, 3 ),
                new ImmutableTriple<>( 122, 116, 128 )
            )
        )
    ),

    MR_MIME(
        1,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.PSYCHIC,
                EEthnicity.FAIRY
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
        )
    ),



    // --- bug -------------------------------------------------------------------------------------------------------------------------------------------------
    CATERPIE(
        3,
        CLevelTupel.generate(
            Stream.of( EEthnicity.BUG ),
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    PARAS(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.BUG,
                EEthnicity.GRASS
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
        CLevelTupel.generate()
    ),

    PINSIR(
        1,
        CLevelTupel.generate(
            Stream.of( EEthnicity.BUG ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.5, 1.2, 1.8 ),
                new ImmutableTriple<>( 55, 50, 60 )
            )
        )
    ),

    SCYTHER(
        1,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.BUG,
                EEthnicity.FLYING
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
        )
    ),

    VENONAT(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.BUG,
                EEthnicity.POISON
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
        CLevelTupel.generate()
    ),

    WEEDLE(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.BUG,
                EEthnicity.POISON
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
        CLevelTupel.generate()
    ),



    // --- rock ------------------------------------------------------------------------------------------------------------------------------------------------
    AERODACTYL(
        1,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ROCK,
                EEthnicity.FLYING
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
        )
    ),

    GEODUDE(
        3,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ROCK,
                EEthnicity.GROUND
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),

    KABUTO(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ROCK,
                EEthnicity.WATER
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
        CLevelTupel.generate()
    ),

    OMANYTE(
        2,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ROCK,
                EEthnicity.WATER
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
        CLevelTupel.generate()
    ),

    ONIX(
        1,
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.ROCK,
                EEthnicity.GROUND
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
        )
    ),



    // --- ghost -----------------------------------------------------------------------------------------------------------------------------------------------
    GASTLY(
        3,

        // gastly
        CLevelTupel.generate(
            Stream.of(
                EEthnicity.GHOST,
                EEthnicity.POISON
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

        // haunter
        CLevelTupel.generate(
            Stream.of(), Stream.of(),

            Stream.of(
                EAttribute.HEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 1.6, 1.3, 1.9 )
            )
        ),

        // phantom
        CLevelTupel.generate()
    ),



    // --- dragon ----------------------------------------------------------------------------------------------------------------------------------------------
    DRATINI(
        3,
        CLevelTupel.generate(
            Stream.of( EEthnicity.DRAGON ),
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
        CLevelTupel.generate(),
        CLevelTupel.generate()
    ),



    // --- fairy -----------------------------------------------------------------------------------------------------------------------------------------------
    CLEFAIRY(
        2,
        CLevelTupel.generate(
            Stream.of( EEthnicity.FAIRY ),
            Stream.of( new ImmutableTriple<>( 0.8, 0.6, 1 ) ),

            Stream.of(
                EAttribute.HEIGHT,
                EAttribute.WEIGHT
            ),
            Stream.of(
                new ImmutableTriple<>( 0.6, 0.4, 0.8 ),
                new ImmutableTriple<>( 7.5, 6, 9 )
            )
        ),
        CLevelTupel.generate()
    );


    /**
     * filename of the icon
     */
    private static final String ICONFILENAME = "agentrouting/pokemon/icon/{0}_{1}.png";
    /**
     * sprite list
     */
    private List<CLevelTupel> m_level;



    /**
     * ctor
     *
     * @param p_level number of level
     */
    EPokemon( final int p_level, final CLevelTupel... p_tupel )
    {
        if ( ( p_level < 1 ) || ( p_tupel == null ) || ( p_tupel.length == 0 ) || ( p_level != p_tupel.length ) )
            throw new RuntimeException( MessageFormat.format( "pokemon [{0}] level and tupel must be equal and not null", this ) );

        m_level = Collections.unmodifiableList(
            IntStream.range( 0, p_level )
                     .mapToObj( i -> p_tupel[i].sprite( this, i ) )
                     .collect( Collectors.toList() )
        );
    }

    /**
     * returns the maxium level
     *
     * @return maximum level number
     */
    public final int level()
    {
        return m_level.size();
    }

    /**
     * returns the tupel
     *
     * @param p_index level
     * @®eturn tupel
     */
    public final CLevelTupel tupel( final int p_index )
    {
        return m_level.get( p_index );
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
            m_ethnic = CLevelTupel.initialize( p_ethnic, p_ethnicvalue );
            m_attributes = CLevelTupel.initialize( p_attributes, p_attributesvalue );
            m_motivation = CLevelTupel.initialize( p_motivation, p_motivationvalue );
            m_attack = Collections.unmodifiableSet( p_attack.collect( Collectors.toSet() ) );
        }


        /**
         * generates the ethnic map of a pokemon
         *
         * @return map individual ethnic values
         */
        public Map<EEthnicity, Number> ethnic()
        {
            return CLevelTupel.generate( m_ethnic );
        }

        /**
         * generates the attribute map of a pokemon
         *
         * @return map individual attributes values
         */
        public Map<EAttribute, Number> attributes()
        {
            return CLevelTupel.generate( m_attributes );
        }

        /**
         * generates the attribute map of a pokemon
         *
         * @return map individual attributes values
         */
        public Map<EMotivation, Number> motivation()
        {
            return CLevelTupel.generate( m_motivation );
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
        public final synchronized CLevelTupel sprite( final EPokemon p_pokemon, final int p_index )
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

        /**
         * factory of tupel
         *
         * @return tupel
         */
        public static CLevelTupel generate()
        {
            return new CLevelTupel();
        }

        /**
         * factory of tupel
         *
         * @param p_ethnic stream with ethnic types
         * @param p_ethnicvalue stream with ethnic values
         * @return tupel
         */
        public static CLevelTupel generate( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue )
        {
            return new CLevelTupel(
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
        public static CLevelTupel generate( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                                            final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue
        )
        {
            return new CLevelTupel(
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
        public static CLevelTupel generate( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                                            final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue,
                                            final Stream<EMotivation> p_motivation, final Stream<Triple<Number, Number, Number>> p_motivationvalue
        )
        {
            return new CLevelTupel(
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
        public static CLevelTupel generate( final Stream<EEthnicity> p_ethnic, final Stream<Triple<Number, Number, Number>> p_ethnicvalue,
                                            final Stream<EAttribute> p_attributes, final Stream<Triple<Number, Number, Number>> p_attributesvalue,
                                            final Stream<EMotivation> p_motivation, final Stream<Triple<Number, Number, Number>> p_motivationvalue,
                                            final Stream<EAttack> p_attack
        )
        {
            return new CLevelTupel(
                p_ethnic, p_ethnicvalue,
                p_attributes, p_attributesvalue,
                p_motivation, p_motivationvalue,
                p_attack
            );
        }
    }
}
