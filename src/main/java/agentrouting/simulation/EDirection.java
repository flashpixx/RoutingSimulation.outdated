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
import cern.jet.math.Functions;


/**
 * direction enum
 */
public enum EDirection
{
    FORWARD,
    FORWARDRIGHT,
    RIGHT,
    BACKWARDRIGHT(),
    BACKWARD,
    BACKWARDLEFT,
    LEFT,
    FORWARDLEFT;

    /**
     * algebra object
     */
    private static final Algebra ALGEBRA = new Algebra();


    /**
     * calculates the new position
     *
     * @param p_position current position
     * @param p_viewpoint view point
     * @param p_length number of cells / step size
     * @return new position
     *
     * @todo https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm can be an optimizing
     * @see https://www.opengl.org/sdk/docs/man2/xhtml/gluLookAt.xml
     * @see http://stackoverflow.com/questions/21830340/understanding-glmlookat
     * @todo tansform
     */
    @SuppressWarnings( "unchecked" )
    public DoubleMatrix1D position( final DoubleMatrix1D p_position, final DoubleMatrix1D p_viewpoint, final int p_length )
    {
        // normalvector to the viewpoint:  (p_viewpoint - p_position) -> normal (length 1)
        // normal vector -> rotation on direction (left = 0°, left fwd = 45°, fwd = 90° ...)
        // scale normalvector with length -> new position

        // https://solarianprogrammer.com/2013/05/22/opengl-101-matrices-projection-view-model/
        final DoubleMatrix1D l_direction = new DenseDoubleMatrix1D( new double[]{p_viewpoint.getQuick( 0 ), p_viewpoint.getQuick( 1 ), 0} );
        l_direction
            .assign( p_position, Functions.minus )
            .assign( Functions.div( ALGEBRA.norm2( l_direction ) ) );

        final DoubleMatrix1D l_up = new DenseDoubleMatrix1D( l_direction.toArray() )
            .assign( Functions.mult( p_length ) )
            .assign( Functions.div( ALGEBRA.norm2( l_direction ) ) );


        return l_direction;
    }

    /**
     * creates the normal vector between current position and viewpoint
     *
     * @param p_position current position
     * @param p_viewpoint viewpoint
     * @return normalized vector to the viewpoint
     */
    private static DoubleMatrix1D normaldirectionvector( final DoubleMatrix1D p_position, final DoubleMatrix1D p_viewpoint )
    {
        final DoubleMatrix1D l_return = new DenseDoubleMatrix1D( p_viewpoint.toArray() );
        l_return.assign( p_position, Functions.minus );
        l_return.assign( Functions.div( ALGEBRA.norm2( l_return ) ) );

        return new DenseDoubleMatrix1D( new double[]{-l_return.getQuick( 1 ), l_return.getQuick( 0 )} );
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
