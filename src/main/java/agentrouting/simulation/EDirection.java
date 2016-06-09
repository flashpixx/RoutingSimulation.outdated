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

import cern.colt.function.DoubleFunction;
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
     * round function
     */
    private static final DoubleFunction ROUND = new DoubleFunction()
    {
        @Override
        public final double apply( final double p_value )
        {
            return Math.round( p_value );
        }
    };
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
        m_rotation = EDirection.rotationmatrix( Math.toRadians( p_alpha ) );
    }

    /**
     * calculates the new position
     *
     * @param p_position current position
     * @param p_viewpoint view point
     * @param p_speed number of cells / step size
     * @return new position
     */
    @SuppressWarnings( "unchecked" )
    public DoubleMatrix1D position( final DoubleMatrix1D p_position, final DoubleMatrix1D p_viewpoint, final int p_speed )
    {
        // calculate the stright line by: p_position + l * (viewpoint - p_position)
        // normalize direction and rotate the normalized vector based on the direction
        // calculate the target position based by: p_position + speed * rotate( normalize( viewpoint - p_position ) )
        final DoubleMatrix1D l_view = new DenseDoubleMatrix1D( p_viewpoint.toArray() );
        return ALGEBRA.mult(
                    m_rotation,
                    l_view
                        .assign( p_position, Functions.minus )
                        .assign( Functions.div( ALGEBRA.norm2( l_view ) ) )
        )
        .assign( Functions.mult( p_speed ) )
        .assign( p_position, Functions.plus )
        .assign( ROUND );
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
