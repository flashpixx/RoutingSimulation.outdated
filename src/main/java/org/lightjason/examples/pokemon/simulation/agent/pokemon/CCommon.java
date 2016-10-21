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


import cern.colt.matrix.DoubleMatrix1D;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.examples.pokemon.simulation.IElement;
import org.lightjason.examples.pokemon.simulation.item.CStatic;

import java.util.stream.Stream;


/**
 * common algorithms
 */
final class CCommon
{
    /**
     * ctor
     */
    private CCommon()
    {}


    /**
     * generates the literal of an object
     *
     * @param p_element element or null
     * @return null or literal
     */
    @SuppressWarnings( "unchecked" )
    static ILiteral elementliteral( final IElement p_element )
    {
        if ( p_element instanceof CPokemon )
            return CLiteral.from(
                "pokemon",
                Stream.of(
                    CCommon.literalposition( "position", p_element.position() ),
                    CLiteral.from( "attribute", p_element.attribute() )
                )
            );

        if ( p_element instanceof CStatic )
            return CLiteral.from(
                "obstacle",
                Stream.of(
                    CCommon.literalposition( "position", p_element.position() ),
                    CLiteral.from( "attribute", p_element.attribute() )
                )
            );

        return null;
    }

    /**
     * creates inner pokemon literals
     *
     * @param p_position current position
     * @param p_goal current goal position
     * @param p_pokemon pokemon type
     * @return literal string
     */
    static Stream<ILiteral> literalpokemon( final DoubleMatrix1D p_position, final DoubleMatrix1D p_goal, final String p_pokemon )
    {
        return Stream.of(

            // current position within the environment
            CCommon.literalposition( "myposition", p_position ),

            // next goal-position
            CCommon.literalposition( "mygoal", p_goal ),

            // pokemon type
            CLiteral.from( "ima", Stream.of( CLiteral.from( p_pokemon.toLowerCase() ) ) )
        );
    }

    /**
     * generate position literal
     *
     * @param p_functor functor name
     * @param p_position position vector
     * @return position literal
     */
    static ILiteral literalposition( final String p_functor, final DoubleMatrix1D p_position )
    {
        return CLiteral.from(
            p_functor,
            Stream.of(
                CLiteral.from( "y", Stream.of( CRawTerm.from( (int) p_position.getQuick( 0 ) ) ) ),
                CLiteral.from( "x", Stream.of( CRawTerm.from( (int) p_position.getQuick( 1  ) ) ) )
            )
        );
    }

    /**
     * checks if a point is within the angel of the visual range
     *
     * @param p_ypos y-position of the point (relative position [-radius, +radius])
     * @param p_xpos x-position of the point (relative position [-radius, +radius])
     * @param p_angle angle of the view-range in degree
     * @return boolean if the position is inside
     */
    static boolean positioninsideangle( final double p_ypos, final double p_xpos, final double p_angle )
    {
        final double l_segment = 0.5 * p_angle;
        final double l_angle = Math.toDegrees( Math.atan( Math.abs( p_ypos ) / Math.abs( p_xpos ) ) );
        return !Double.isNaN( l_angle ) && ( l_angle >= 360 - l_segment ) || ( l_angle <= l_segment );
    }

}
