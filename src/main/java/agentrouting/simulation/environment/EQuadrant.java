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

package agentrouting.simulation.environment;

import agentrouting.CCommon;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;


/**
 * quadrant
 * @see https://en.wikipedia.org/wiki/Quadrant_(plane_geometry)
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
     * @see http://gamedev.stackexchange.com/questions/96099/is-there-a-quick-way-to-determine-if-a-vector-is-in-a-quadrant
     */
    public static EQuadrant quadrant( final DoubleMatrix1D p_zero, final DoubleMatrix1D p_position )
    {
        final double l_sin = Math.toDegrees( Math.asin( ( p_position.getQuick( 0 ) - p_zero.getQuick( 0 ) ) / ( p_position.getQuick( 1 ) - p_zero.getQuick( 1 ) ) ) );
        final double l_cos = Math.toDegrees( Math.acos( ( p_position.getQuick( 1 ) - p_zero.getQuick( 1 ) ) / ( p_position.getQuick( 0 ) - p_zero.getQuick( 0 ) ) ) );

        System.out.println( CCommon.MATRIXFORMAT.toString( p_zero ) + "     " + CCommon.MATRIXFORMAT.toString( p_position ) + "     " + l_sin + "     " + l_cos );

        if ( ( l_sin >= 0 ) && ( l_cos >= 0 ) )
            return UPPERRIGHT;

        if ( ( l_sin >= 0 ) && ( l_cos < 0 ) )
            return UPPERLEFT;

        if ( ( l_sin < 0 ) && ( l_cos < 0 ) )
            return BOTTOMLEFT;

        return BOTTOMRIGHT;
    }

}
