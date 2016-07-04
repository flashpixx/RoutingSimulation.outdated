package agentrouting.simulation.algorithm.routing;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tint.impl.DenseIntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import cern.colt.matrix.tobject.impl.SparseObjectMatrix2D;


/**
 * test for JPS+
 */
public final class TestCJPSPlus
{
    private ObjectMatrix2D m_grid;

    /**
     * initialize class with static data
     */
    @Before
    public void initialize()
    {
        m_grid = new SparseObjectMatrix2D( 10, 10 );
        /*IntStream.range(0,m_grid.rows())
            .forEach(i->
            {
                IntStream.range(0,m_grid.columns())
                    .forEach(j->m_grid.setQuick(i, j, new Object()));
            });*/
        m_grid.setQuick( 4, 2, new Object() );
        m_grid.setQuick( 4, 3, new Object() );
        m_grid.setQuick( 3, 2, new Object() );
    }


    /**
     * test of a correct working route without obstacles
     */
    @Test
    public void testrouting()
    {
        final List<IntMatrix1D> l_route = new CJPSPlus().route( m_grid, new DenseIntMatrix1D( new int[]{8, 0} ), new DenseIntMatrix1D( new int[]{2, 3} ) );
        //System.out.println( l_route );
        /*
        final List<IntMatrix1D> l_waypoint = Stream.of(
            new DenseIntMatrix1D( new int[]{2, 3} ),
            new DenseIntMatrix1D( new int[]{6, 7} ),
            new DenseIntMatrix1D( new int[]{6, 9} )
        ).collect( Collectors.toList() );
        */
        /*
        final List<IntMatrix1D> l_waypoint = Stream.of(
            new DenseIntMatrix1D( new int[]{8, 0} ),
            new DenseIntMatrix1D( new int[]{7, 1} ),
            new DenseIntMatrix1D( new int[]{4, 1} ),
            new DenseIntMatrix1D( new int[]{3, 2} ),
            new DenseIntMatrix1D( new int[]{2, 3} )
        ).collect( Collectors.toList() );
        */

        ///*
        final List<IntMatrix1D> l_waypoint = Stream.of(
            new DenseIntMatrix1D( new int[]{8, 0} ),
            new DenseIntMatrix1D( new int[]{7, 1} ),
            new DenseIntMatrix1D( new int[]{3, 1} ),
            new DenseIntMatrix1D( new int[]{2, 2} ),
            new DenseIntMatrix1D( new int[]{2, 3} )
        ).collect( Collectors.toList() );
        //*/

        assertEquals( l_route.size(), l_waypoint.size() );
        IntStream.range( 0, l_waypoint.size() ).boxed().forEach( i -> assertEquals( l_waypoint.get( i ), l_route.get( i ) ) );

        /* final List<IntMatrix1D> l_waypoint = Collections.<IntMatrix1D>emptyList();

        assertEquals( l_route.size(), l_waypoint.size() );*/

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
