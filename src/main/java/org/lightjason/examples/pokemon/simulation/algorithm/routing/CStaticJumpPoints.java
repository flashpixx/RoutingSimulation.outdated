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

package org.lightjason.examples.pokemon.simulation.algorithm.routing;

import java.util.List;
import java.util.stream.Stream;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;

/**
 * class for Static jump points
 */
public final class CStaticJumpPoints
{

    private CStaticJumpPoints()
    {
    }

    /**
     * to create static jump points
     * @param p_staticjumppoints list of the static jump points
     * @param p_row row number of the grid cell
     * @param p_column column number of the grid cell
     * @param p_objects Snapshot of the environment
     */
    public static void createstaticjump( final List<DoubleMatrix1D> p_staticjumppoints, final double p_row, final double p_column, final ObjectMatrix2D p_objects )
    {

        Stream.of( new DenseDoubleMatrix1D( new double[]{p_row + 1, p_column} ), new DenseDoubleMatrix1D( new double[]{p_row - 1, p_column} ),
                new DenseDoubleMatrix1D( new double[]{p_row, p_column + 1} ), new DenseDoubleMatrix1D( new double[]{p_row, p_column - 1} ) )

            .filter( s-> !isNotCoordinate( p_objects, s.getQuick( 0 ), s.getQuick( 1 ) ) && !isOccupied( p_objects, s.getQuick( 0 ), s.getQuick( 1 ) )
                     && !p_staticjumppoints.contains( s ) )
            .forEach( s-> p_staticjumppoints.add( s ) );
    }

    /**
     * To check any point in the grid environment is free or occupied
     * @param p_objects Snapshot of the environment
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     */
    public static boolean isOccupied( final ObjectMatrix2D p_objects, final double p_row, final double p_column )
    {
        return p_objects.getQuick( (int) p_row, (int) p_column ) != null;
    }

    /**
     * To check any point is in the grid environment or out of environment
     * @param p_objects Snapshot of the environment
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     * @return return true or false
     */

    public static boolean isNotCoordinate( final ObjectMatrix2D p_objects, final double p_row, final double p_column )
    {
        return p_column < 0 || p_column >= p_objects.columns() || p_row < 0 || p_row >= p_objects.rows();
    }

}