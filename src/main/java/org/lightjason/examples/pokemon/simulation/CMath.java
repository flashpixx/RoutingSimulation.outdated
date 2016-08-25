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

package org.lightjason.examples.pokemon.simulation;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Formatter;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.math.Functions;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.SynchronizedRandomGenerator;

import java.util.function.Consumer;
import java.util.stream.Stream;


/**
 * class for global math algorithm
 */
public final class CMath
{
    /**
     * reference to global algebra instance
     */
    public static final Algebra ALGEBRA = Algebra.DEFAULT;
    /**
     * synchronized random generator
     */
    public static final RandomGenerator RANDOMGENERATOR = new SynchronizedRandomGenerator( new MersenneTwister() );
    /**
     * matrix formatter
     */
    public static final Formatter MATRIXFORMAT = new Formatter();

    static
    {
        MATRIXFORMAT.setRowSeparator( "; " );
        MATRIXFORMAT.setColumnSeparator( "," );
        MATRIXFORMAT.setPrintShape( false );
    }

    /**
     * pvate ctor
     */
    private CMath()
    {}

    /**
     * consums a stream of matrix objects
     *
     * @param p_stream stream
     * @param p_consumer consumer function
     * @return stream
     */
    public static Stream<DoubleMatrix1D> matrixconsumer( final Stream<DoubleMatrix1D> p_stream, final Consumer<String> p_consumer )
    {
        return p_stream.map( i -> {
            p_consumer.accept( MATRIXFORMAT.toString( i ) + " " );
            return i;
        } );
    }

    /**
     * creates a rotation matrix
     *
     * @param p_alpha degree in radians
     * @return matrix
     *
     * @see https://en.wikipedia.org/wiki/Rotation_matrix
     */
    public static DoubleMatrix2D rotationmatrix( final double p_alpha )
    {
        return new DenseDoubleMatrix2D( new double[][]{{Math.cos( p_alpha ), -Math.sin( p_alpha )}, {Math.sin( p_alpha ), Math.cos( p_alpha )}} );
    }

    /**
     * returns the angel
     *
     * @param p_first first vector
     * @param p_second second vector
     * @return angel in degree
     */
    public static double angel( final DoubleMatrix1D p_first, final DoubleMatrix1D p_second )
    {
        return p_first.zDotProduct( p_second ) / ( Math.sqrt( ALGEBRA.norm2( p_first ) ) * Math.sqrt( ALGEBRA.norm2( p_second ) ) );
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
