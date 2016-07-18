package agentrouting.simulation.algorithm.routing;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    private ObjectMatrix2D m_grid;
    private ObjectMatrix2D m_occupiedgrid;
    private ObjectMatrix2D m_emptygrid;

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
    }

    /**
     * initialize class with static data for empty grid
     */
    @Before
    public void initializeemptygrid()
    {
        m_emptygrid = new SparseObjectMatrix2D( 10, 10 );
    }

    /**
     * initialize class with static data for fully occupied grid
     */
    @Before
    public void initializeoccupiedgrid()
    {
        m_occupiedgrid = new SparseObjectMatrix2D( 10, 10 );
        IntStream.range( 0, m_occupiedgrid.rows() )
            .forEach( i->
            {
                IntStream.range( 0, m_occupiedgrid.columns() )
                    .forEach( j -> m_occupiedgrid.setQuick( i, j, new Object() ) );
            } );
    }


    /**
     * test of a correct working route with some obstacles
     */
    @Test
    public void testrouting()
    {
        final List<DoubleMatrix1D> l_route = new CJPSPlus().route( m_grid, new DenseDoubleMatrix1D( new double[]{8, 0} ), new DenseDoubleMatrix1D( new double[]{2, 3} ) );
        final List<DoubleMatrix1D> l_waypoint = Stream.of(
            new DenseDoubleMatrix1D( new double[]{7, 1} ),
            new DenseDoubleMatrix1D( new double[]{3, 1} ),
            new DenseDoubleMatrix1D( new double[]{2, 2} ),
            new DenseDoubleMatrix1D( new double[]{2, 3} )
        ).collect( Collectors.toList() );

        assertEquals( l_route.size(), l_waypoint.size() );
        IntStream.range( 0, l_waypoint.size() ).boxed().forEach( i -> assertEquals( l_waypoint.get( i ), l_route.get( i ) ) );

    }

    /**
     * test of a correct working route with full of obstacles
     */
    @Test
    public void testoccupiedgrid()
    {
        assertEquals(
            new CJPSPlus().route( m_occupiedgrid, new DenseDoubleMatrix1D( new double[]{8, 0} ), new DenseDoubleMatrix1D( new double[]{2, 3} ) ),
            Collections.<DoubleMatrix1D>emptyList()
        );
    }

    /**
     * test of a correct working route without obstacles
     */
    @Test
    public void testemptygrid()
    {
        final List<DoubleMatrix1D> l_emptyroute = new CJPSPlus().route(
            m_emptygrid, new DenseDoubleMatrix1D( new double[]{2, 3} ), new DenseDoubleMatrix1D( new double[]{6, 9} )
        );
        final List<DoubleMatrix1D> l_waypoint = Stream.of(
                new DenseDoubleMatrix1D( new double[]{6, 7} ),
                new DenseDoubleMatrix1D( new double[]{6, 9} )
            ).collect( Collectors.toList() );

        assertEquals( l_emptyroute.size(), l_waypoint.size() );
        IntStream.range( 0, l_waypoint.size() ).boxed().forEach( i -> assertEquals( l_waypoint.get( i ), l_emptyroute.get( i ) ) );
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
        new TestCJPSPlus().testemptygrid();
        new TestCJPSPlus().testoccupiedgrid();
        new TestCJPSPlus().testrouting();
    }


}
