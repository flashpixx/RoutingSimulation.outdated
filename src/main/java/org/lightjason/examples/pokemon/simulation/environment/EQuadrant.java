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

package org.lightjason.examples.pokemon.simulation.environment;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;
import cern.jet.math.Functions;


/**
 * quadrant
 * @see https://en.wikipedia.org/wiki/Quadrant_(plane_geometry)
 * @see http://gamedev.stackexchange.com/questions/96099/is-there-a-quick-way-to-determine-if-a-vector-is-in-a-quadrant
 */
public enum EQuadrant
{
    UPPERRIGHT,
    UPPERLEFT,
    BOTTOMLEFT,
    BOTTOMRIGHT;

    /**
     * returns a quadrant based on the vector
     *
     * @param p_position position
     * @return quadrant relativ to zero position
     */
    public static EQuadrant quadrant( final DoubleMatrix1D p_position )
    {
        return EQuadrant.quadrant( new SparseDoubleMatrix1D( p_position.size() ), p_position );
    }

    /**
     * returns a quadrant based on the vector
     *
     * @param p_zero zero position
     * @param p_position position
     * @return quadrant relative to zero position

     */
    public static EQuadrant quadrant( final DoubleMatrix1D p_zero, final DoubleMatrix1D p_position )
    {
        final DoubleMatrix1D l_difference = new DenseDoubleMatrix1D( p_position.toArray() ).assign( p_zero, Functions.minus );
        return l_difference.getQuick( 1 ) < 0
               ? l_difference.getQuick( 0 ) < 0
                 ? BOTTOMLEFT
                 : UPPERLEFT
               : l_difference.getQuick( 0 ) < 0
                 ? BOTTOMRIGHT
                 : UPPERRIGHT;
    }

}
