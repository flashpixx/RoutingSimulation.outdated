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


import com.codepoetics.protonpack.StreamUtils;

import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * attacks sorted by type and in type section in learning order (from weaker to stronger)
 * @see http://pokewiki.de/Attacken-Liste
 *
 * @see http://gamedevelopment.tutsplus.com/tutorials/how-to-generate-shockingly-good-2d-lightning-effects--gamedev-2681
 * @see http://www.alcove-games.com/opengl-es-2-tutorials/lightmap-shader-fire-effect-glsl/s
 * @see http://www.gamedev.net/page/resources/_/creative/visual-arts/make-a-particle-explosion-effect-r2701
 * @see http://www.gamefromscratch.com/post/2014/11/03/LibGDX-Tutorial-Part-15-Particles-Part-One-2D-Particles.aspx
 */
public enum EAttack
{
    // --- normal ----------------------------------------------------------------------------------------------------------------------------------------------
    SCRATCH( 1, 0.05,
             Stream.of( EAttribute.HEALTH ),
             Stream.of( 0.3 )
    ),

    TACKLE( 1, 0.05,
            Stream.of( EAttribute.HEALTH ),
            Stream.of( 0.3 )
    ),

    SLASH( 1, 0.10,
           Stream.of( EAttribute.HEALTH ),
           Stream.of( 0.4 )
    ),

    SLAM( 0.9, 0.10,
          Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
          Stream.of( 0.4, 0.1 )
    ),

    HEADBUTT( 1, 0.10,
              Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
              Stream.of( 0.3, 0.2 )
    ),

    POUND( 1, 1,
           Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
           Stream.of( 0.4, 0.1 )
    ),

    COMETPUNCH( 0.8, 1,
                Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
                Stream.of( 0.4, 0.1 )
    ),

    DRILLPECK( 0.8, 0.05,
               Stream.of( EAttribute.HEALTH ),
               Stream.of( 0.8 )
    ),

    GUST( 1, 0.05,
          Stream.of( EAttribute.HEALTH ),
          Stream.of( 0.4 )
    ),

    WINGATTACK( 1, 0.10,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 0.6 )
    ),

    SKYATTACK( 0.9, 0.15,
               Stream.of( EAttribute.HEALTH ),
               Stream.of( 1 )
    ),


    // --- fire ------------------------------------------------------------------------------------------------------------------------------------------------
    EMBER( 1, 0.05,
           Stream.of( EAttribute.HEALTH ),
           Stream.of( 0.4 )
    ),

    FIRESPIN( 0.85, 0.05,
              Stream.of( EAttribute.HEALTH, EAttribute.SPEED ),
              Stream.of( 0.35, 0.1 )
    ),

    FLAMETHROWER( 1, 0.10,
                  Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
                  Stream.of( 0.8, 0.1 )
    ),

    FIREBLAST( 0.85, 0.15,
               Stream.of( EAttribute.HEALTH ),
               Stream.of( 1 )
    ),



    // --- water -----------------------------------------------------------------------------------------------------------------------------------------------
    BUBBLE( 1, 0.05,
            Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
            Stream.of( 0.3 )
    ),

    WATERGUN( 1, 0.10,
              Stream.of( EAttribute.HEALTH ),
              Stream.of( 0.4 )
    ),

    BUBBLEBEAM( 1, 0.15,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 0.65 )
    ),

    SURFER( 1, 0.15,
            Stream.of( EAttribute.HEALTH ),
            Stream.of( 0.9 )
    ),

    HYDROPUMP( 0.8, 0.05,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 1 )
    ),



    // --- electric --------------------------------------------------------------------------------------------------------------------------------------------
    THUNDERWAVE( 1, 0.05,
                 Stream.of( EAttribute.MIND ),
                 Stream.of( 0.1 )
    ),

    THUNDERSHOCK( 1, 0.05,
                  Stream.of( EAttribute.HEALTH ),
                  Stream.of( 0.4 )
    ),

    THUNDERBOLT( 1, 0.10,
                 Stream.of( EAttribute.HEALTH ),
                 Stream.of( 0.8 )
    ),

    THUNDER( 1, 0.15,
             Stream.of( EAttribute.HEALTH ),
             Stream.of( 1 )
    ),



    // --- grass -----------------------------------------------------------------------------------------------------------------------------------------------
    STUNSPORE( 0.95, 0.05,
               Stream.of( EAttribute.MIND ),
               Stream.of( 0.1 )
    ),

    VINEWHIP( 1, 0.05,
              Stream.of( EAttribute.HEALTH ),
              Stream.of( 0.25 )
    ),

    PETALDANCE( 1, 0.10,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 0.7 )
    ),

    RAZORLEAF( 0.95, 0.15,
               Stream.of( EAttribute.HEALTH ),
               Stream.of( 0.45 )
    ),

    SOLARBEAM( 1, 0.15,
               Stream.of( EAttribute.HEALTH ),
               Stream.of( 1 )
    ),



    // --- ice -------------------------------------------------------------------------------------------------------------------------------------------------
    HAZE( 1, 0.05,
          Stream.of( EAttribute.MIND ),
          Stream.of( 0.1 )
    ),

    AURORABEAM( 1, 0.10,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 0.65 )
    ),

    BLIZZARD( 0.7, 0.10,
              Stream.of( EAttribute.HEALTH, EAttribute.SPEED ),
              Stream.of( 1, 0.1 )
    ),

    ICEBEAM( 1, 0.15,
             Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
             Stream.of( 0.9, 0.3 )
    ),



    // --- fighting --------------------------------------------------------------------------------------------------------------------------------------------
    DOUBLEKICK( 1, 0.05,
                Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
                Stream.of( 0.3, 0.1 )
    ),

    KARATECHOP( 1, 0.05,
                Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
                Stream.of( 0.3, 0.1 )
    ),

    DOUBLESLAP( 1, 0.05,
                Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
                Stream.of( 0.4, 0.1 )
    ),

    JUMPKICK( 0.95, 0.1,
              Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
              Stream.of( 0.5, 0.1 )
    ),

    HIGHJUMPKICK( 0.95, 0.15,
                  Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
                  Stream.of( 0.7, 0.1 )
    ),

    SEISMICTOSS( 1, 0.15,
                 Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
                 Stream.of( 1, 0.1 )
    ),



    // --- poison ----------------------------------------------------------------------------------------------------------------------------------------------
    POISONPOWDER( 0.75, 0.05,
                  Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
                  Stream.of( 0.1, 0.2 )
    ),

    POISONSTING( 1, 0.05,
                 Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
                 Stream.of( 0.1, 0.15 )
    ),

    POISONGAS( 0.9, 0.05,
               Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
               Stream.of( 0.2, 0.1 )
    ),

    SMOG( 0.7, 0.10,
          Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
          Stream.of( 0.3, 0.1 )
    ),

    ACID( 1, 0.10,
          Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
          Stream.of( 0.4, 0.1 )
    ),

    TOXIN( 0.9, 0.15,
           Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
           Stream.of( 0.8, 0.2 )
    ),



    // --- ground ----------------------------------------------------------------------------------------------------------------------------------------------
    DIG( 1, 0.05,
         Stream.of( EAttribute.HEALTH ),
         Stream.of( 0.8 )
    ),

    EARTHQUAKE( 1, 0.10,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 1 )
    ),

    FISSURE( 0.3, 0.15,
             Stream.of( EAttribute.HEALTH, EAttribute.ENERGY ),
             Stream.of( 1, 0.5 )
    ),




    // --- psychic ---------------------------------------------------------------------------------------------------------------------------------------------
    CONFUSION( 0.95, 0.05,
               Stream.of( EAttribute.MIND ),
               Stream.of( 0.1 )
    ),

    KINESIS( 0.8, 0.05,
             Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
             Stream.of( 0.4, 0.1 )
    ),

    PSYBEAM( 1, 0.10,
             Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
             Stream.of( 0.65, 0.1 )
    ),

    PSYCHIC( 1, 0.15,
             Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
             Stream.of( 1, 0.1 )
    ),



    // --- bug -------------------------------------------------------------------------------------------------------------------------------------------------
    LEECHLIFE( 1, 0.05,
               Stream.of( EAttribute.HEALTH, EAttribute.ENERGY ),
               Stream.of( 0.2, 0.1 )
    ),

    STRINGSHOT( 0.95, 0.05,
                Stream.of( EAttribute.SPEED ),
                Stream.of( 0.2 )
    ),

    TWINNEEDLE( 1, 0.10,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 0.4 )
    ),

    PINMISSILE( 0.95, 0.15,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 0.5 )
    ),



    // --- rock ------------------------------------------------------------------------------------------------------------------------------------------------
    ROCKBLAST( 0.8, 0.05,
            Stream.of( EAttribute.HEALTH, EAttribute.ENERGY ),
            Stream.of( 0.25, 0.1 )
    ),

    ROLLOUT( 0.9, 0.10,
            Stream.of( EAttribute.HEALTH, EAttribute.ENERGY ),
            Stream.of( 0.3, 0.1 )
    ),

    ANCIENTPOWER( 1, 0.10,
            Stream.of( EAttribute.HEALTH, EAttribute.DEFENSE ),
            Stream.of( 0.6, 0.1 )
    ),

    ROCKSLIDE( 0.9, 0.15,
            Stream.of( EAttribute.HEALTH, EAttribute.ENERGY ),
            Stream.of( 0.9, 0.1 )
    ),



    // --- ghost -----------------------------------------------------------------------------------------------------------------------------------------------
    CONFUSERAY( 1, 0.05,
                Stream.of( EAttribute.MIND ),
                Stream.of( 0.1 )
    ),

    LICK( 1, 0.05,
          Stream.of( EAttribute.MIND ),
          Stream.of( 0.2 )
    ),

    NIGHTSHADE( 1, 0.10,
                Stream.of( EAttribute.HEALTH, EAttribute.MIND ),
                Stream.of( 0.8 )
    ),



    // --- dragon ----------------------------------------------------------------------------------------------------------------------------------------------
    DRAGONRAGE( 1, 0.05,
                Stream.of( EAttribute.HEALTH ),
                Stream.of( 0.2 )
    ),

    DRAGONBREATH( 1, 0.10,
            Stream.of( EAttribute.HEALTH ),
            Stream.of( 0.6 )
    );



    // --- fairy -----------------------------------------------------------------------------------------------------------------------------------------------
    // used normal & psychic



    /**
     * accuracy of the attack in (0,1]
     */
    private final double m_accuracy;
    /**
     * energy cost of the attac
     */
    private final double m_energy;
    /**
     * damage values in [0,1]
     */
    private final Map<EAttribute, Number> m_damage;

    /**
     * ctor
     * @param p_accuracy accuracy of the attack
     * @param p_energy energy of the attac
     * @param p_damagetype damage types
     * @param p_damagevalue decrese value
     */
    EAttack( final double p_accuracy, final double p_energy, final Stream<EAttribute> p_damagetype, final Stream<Number> p_damagevalue )
    {
        if ( ( p_accuracy <= 0 ) || ( p_accuracy > 1 ) )
            throw new RuntimeException( MessageFormat.format( "accuracy for [{0}] must be in (0,1]", this ) );
        if ( p_energy <= 0 )
            throw new RuntimeException( MessageFormat.format( "energy of the attack [{0}] must be greater than zero", this ) );

        m_energy = p_energy;
        m_accuracy = p_accuracy;
        m_damage = Collections.unmodifiableMap(
            StreamUtils.zip(
                p_damagetype,
                p_damagevalue,
                AbstractMap.SimpleImmutableEntry::new
            ).collect( Collectors.toMap( AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue ) )
        );
    }

}
