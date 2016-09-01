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
import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseObjectMatrix2D;


/**
 * test for JPS+
 */
public final class TestCJPSPlus
{

    private final List<DoubleMatrix1D> m_static = new ArrayList<>();
    private ObjectMatrix2D m_grid;

    /**
     * initialize class with static data for routing algorithm test
     */
    @Before
    public void initialize()
    {
        m_grid = new SparseObjectMatrix2D( 10, 10 );

        m_grid.setQuick( 4, 2, new Object() );
        m_grid.setQuick( 4, 3, new Object() );
        m_grid.setQuick( 3, 2, new Object() );

        m_static.add( new DenseDoubleMatrix1D( new double[]{2, 2} ) );
        m_static.add( new DenseDoubleMatrix1D( new double[]{3, 1} ) );
        m_static.add( new DenseDoubleMatrix1D( new double[]{3, 3} ) );
        m_static.add( new DenseDoubleMatrix1D( new double[]{4, 1} ) );
        m_static.add( new DenseDoubleMatrix1D( new double[]{4, 4} ) );
        m_static.add( new DenseDoubleMatrix1D( new double[]{5, 2} ) );
        m_static.add( new DenseDoubleMatrix1D( new double[]{5, 3} ) );

    }


    /**
     * test of a correct working route with some obstacles
     */
    @Test
    public void testrouting()
    {
        final List<DoubleMatrix1D> l_route = new CJPSPlus().route( m_grid, new DenseDoubleMatrix1D( new double[]{8, 0} ),
                new DenseDoubleMatrix1D( new double[]{2, 3} ), m_static );
        System.out.println( l_route );
    }


    /**
     * it is recommand, that each test-class uses also
     * a main-method, which calls the test-methods manually,
     * because the Maven-test calls does not allow any debugging
     * with the IDE, so this main-method allows to start the
     * test through the IDE and run the IDE debugger
     * @param p_args input arguments
     **/
    public static void main( final String[] p_args )
    {
        new TestCJPSPlus().testrouting();
    }


}
