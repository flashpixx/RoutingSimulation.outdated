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

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.math.Functions;


/**
 * direction enum
 */
public enum EDirection
{
    FORWARD( 0 ),
    FORWARDLEFT( 45 ),
    LEFT( 90 ),
    BACKWARDLEFT( 135 ),
    BACKWARD( 180 ),
    BACKWARDRIGHT( 225 ),
    RIGHT( 270 ),
    FORWARDRIGHT( 315 );


    /**
     * algebra object
     */
    private static final Algebra ALGEBRA = new Algebra();
    /**
     * rotation-matrix for the direction vector
     */
    private final DoubleMatrix2D m_rotation;

    /**
     * ctor
     *
     * @param p_alpha rotation of the normal-viewpoint-vector
     */
    EDirection( final double p_alpha )
    {
        m_rotation = EDirection.rotationmatrix( Math.toRadians( p_alpha ) );
    }

    /**
     * calculates the new position
     *
     * @param p_position current position
     * @param p_goalposition goal position
     * @param p_speed number of cells / step size
     * @return new position
     */
    @SuppressWarnings( "unchecked" )
    public DoubleMatrix1D position( final DoubleMatrix1D p_position, final DoubleMatrix1D p_goalposition, final int p_speed )
    {
        // calculate the stright line by: current position + l * (goal position - current position)
        // normalize direction and rotate the normalized vector based on the direction
        // calculate the target position based by: current position + speed * rotate( normalize( goal position - current position ) )
        final DoubleMatrix1D l_view = new DenseDoubleMatrix1D( p_goalposition.toArray() );
        return ALGEBRA.mult(
                    m_rotation,
                    l_view
                        .assign( p_position, Functions.minus )
                        .assign( Functions.div( ALGEBRA.norm2( l_view ) ) )
        )
        .assign( Functions.mult( p_speed ) )
        .assign( p_position, Functions.plus )
        .assign( Math::round );
    }

    /**
     * creates a rotation matrix
     *
     * @param p_alpha degree in radians
     * @return matrix
     *
     * @see https://en.wikipedia.org/wiki/Rotation_matrix
     */
    private static DoubleMatrix2D rotationmatrix( final double p_alpha )
    {
        return new DenseDoubleMatrix2D( new double[][]{{Math.cos( p_alpha ), -Math.sin( p_alpha )}, {Math.sin( p_alpha ), Math.cos( p_alpha )}} );
    }

    /**
     * returns the distance between to points
     *
     * @param p_first vector
     * @param p_second vector
     * @return distance
     */
    public static double distance( final DoubleMatrix1D p_first, final DoubleMatrix1D p_second )
    {
        return ALGEBRA.norm2(
            new DenseDoubleMatrix1D( p_second.toArray() )
            .assign( p_first, Functions.minus )
        );
    }

}