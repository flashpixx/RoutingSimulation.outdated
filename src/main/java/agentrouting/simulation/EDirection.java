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

package agentrouting.simulation;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.tint.IntMatrix1D;
import cern.jet.math.Functions;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;


/**
 * direction enum
 */
public enum EDirection
{
    FORWARD( 90 ),
    FORWARDRIGHT( 135 ),
    RIGHT( 180 ),
    BACKWARDRIGHT( 225 ),
    BACKWARD( 270 ),
    BACKWARDLEFT( 315 ),
    LEFT( 0 ),
    FORWARDLEFT( 45 );

    /**
     * algebra object
     */
    private static final Algebra ALGEBRA = new Algebra();
    /**
     * rotation-matrix to the normal-viewpoint-vector
     */
    private final DoubleMatrix2D m_rotation;

    /**
     * ctor
     *
     * @param p_alpha rotation of the normal-viewpoint-vector
     */
    EDirection( final double p_alpha )
    {
        m_rotation = EDirection.rotationmatrix( p_alpha );
    }


    /**
     * calculates the new position
     *
     * @param p_position current position
     * @param p_viewpoint view point
     * @param p_length number of cells / step size
     * @return new position
     */
    @SuppressWarnings( "unchecked" )
    public IntMatrix1D position( final IntMatrix1D p_position, final IntMatrix1D p_viewpoint, final int p_length )
    {
        // normalvector to the viewpoint:  (p_viewpoint - p_position) -> normal (length 1)
        // normal vector -> rotation on direction (left = 0°, left fwd = 45°, fwd = 90° ...)
        // scale normalvector with length -> new position
        return null;
    }

    /**
     * creates the normal vector between current position and viewpoint
     *
     * @param p_position current position
     * @param p_viewpoint viewpoint
     * @return normalized vector to the viewpoint
     */
    private DoubleMatrix1D normaldirectionvector( final IntMatrix1D p_position, final IntMatrix1D p_viewpoint )
    {
        final DoubleMatrix1D l_return = new DenseDoubleMatrix1D( Doubles.toArray( Ints.asList( p_viewpoint.toArray() ) ) );
        l_return.assign( new DenseDoubleMatrix1D( Doubles.toArray( Ints.asList( p_position.toArray() ) ) ), Functions.minus );
        l_return.assign( Functions.minus( ALGEBRA.norm2( l_return ) ) );
        return l_return;
    }

    /**
     * creates a rotation matrix
     *
     * @param p_alpha degree
     * @return matrix
     *
     * @see https://en.wikipedia.org/wiki/Rotation_matrix
     */
    private static DoubleMatrix2D rotationmatrix( final double p_alpha )
    {
        return new DenseDoubleMatrix2D( new double[][]{{Math.cos( p_alpha ), -Math.sin( p_alpha )}, {Math.sin( p_alpha ), Math.cos( p_alpha )}} );
    }
}
