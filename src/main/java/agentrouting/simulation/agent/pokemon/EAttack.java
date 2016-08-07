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
 * attacks with different attributes
 * @see http://pokewiki.de/Attacken-Liste
 *
 * @see http://gamedevelopment.tutsplus.com/tutorials/how-to-generate-shockingly-good-2d-lightning-effects--gamedev-2681
 */
public enum EAttack
{
    POUND( 1, 1,
           Stream.of( EAttribute.ENERGY, EAttribute.VITALITY ),
           Stream.of( 1, 1 )
    );
    /*
    KARATECHOP(),
    DOUBLESLAP(),
    COMETPUNCH(),
    MEGAPUNCH(),
    FIREPUNCH(),
    ICEPUNCH(),
    THUNDERPUNCH(),
    SCRATCH(),
    VICEGRIP(),
    GUILLOTINE(),
    RAZORWIND(),
    SWORDSDANCE(),
    CUT(),
    GUST(),
    WINDATTACK(),
    WHIRLWIND(),
    FLY(),
    BIND(),
    SLAM(),
    VINEWHIP(),
    STOMP(),
    DOUBLEKICK(),
    MEGAKICK(),
    JUMPKICK()
    */




    /**
     * accuracy of the attack in (0,1]
     */
    private final double m_accuracy;
    /**
     * energy cost of the attac
     */
    private final double m_energy;
    /**
     * damage values
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
