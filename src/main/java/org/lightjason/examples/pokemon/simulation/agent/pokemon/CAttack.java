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

import org.lightjason.examples.pokemon.data.Iattack;
import org.lightjason.examples.pokemon.data.Iconfiguration;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * attack
 */
public final class CAttack
{
    /**
     * name of the attack
     */
    private final String m_name;
    /**
     * attack distance
     */
    private final double m_distance;
    /**
     * attack accuracy
     */
    private final double m_accuracy;
    /**
     * energy cost / experience increment in [0,1]
     */
    private final double m_power;
    /**
     * damages
     */
    private final Map<CAttribute, Double> m_damage;

    /**
     * ctor
     *
     * @param p_attack data source attack object
     * @param p_attribute attribute map
     */
    public CAttack( final Iconfiguration.Attack.Item p_attack, final Map<String, CAttribute> p_attribute )
    {
        m_name = p_attack.getId().trim().toLowerCase();
        m_distance = p_attack.getDistance();
        m_accuracy = p_attack.getAccuracy();
        m_power = p_attack.getPower();
        m_damage = Collections.unmodifiableMap(
                       p_attack.getDamage().stream()
                               .collect( Collectors.toMap( i -> p_attribute.get( i.getId().trim().toLowerCase() ), Iattack.Damage::getValue ) )
        );
    }



    @Override
    public final int hashCode()
    {
        return m_name.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof CAttack ) && ( this.hashCode() == p_object.hashCode() );
    }

    @Override
    public final String toString()
    {
        return MessageFormat.format(
            "attack( {0} - distance {1} - accuracy {2} - power {3} - damage {4})",
            m_name, m_distance, m_accuracy, m_power, m_damage
        );
    }

    /**
     * return the name of attack
     *
     * @return attack name
     */
    public final String name()
    {
        return m_name;
    }

    /**
     * maximum attack distance
     *
     * @return distance
     */
    public final double distance()
    {
        return m_distance;
    }

    /**
     * return attack accuracy
     *
     * @return accuracy
     */
    public final double accuracy()
    {
        return m_accuracy;
    }

    /**
     * attack cost
     *
     * @return cost
     */
    public final double power()
    {
        return m_power;
    }

    /**
     * damage of the attack
     *
     * @return attack map
     */
    public final Map<CAttribute, Double> damage()
    {
        return m_damage;
    }
}
