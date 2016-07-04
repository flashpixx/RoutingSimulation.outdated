package agentrouting.simulation.algorithm.routing;

import cern.colt.matrix.tint.IntMatrix1D;
import org.junit.Before;
import org.junit.Test;

import cern.colt.matrix.tint.impl.DenseIntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import cern.colt.matrix.tobject.impl.SparseObjectMatrix2D;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


/**
 * test for JPS+
 */
public final class TestCJPSPlus
{
    private ObjectMatrix2D m_grid;


    @Before
    public void initialize()
    {
        m_grid = new SparseObjectMatrix2D( 10, 10 );
    }


    /**
     * test of a correct working route without obstacles
     */
    @Test
    public void testrouting()
    {
        final List<IntMatrix1D> l_route = new CJPSPlus().route( m_grid, new DenseIntMatrix1D( new int[]{2, 3} ), new DenseIntMatrix1D( new int[]{6, 9} ) );
        final List<IntMatrix1D> l_waypoint = Stream.of(
            new DenseIntMatrix1D( new int[]{2, 3} ),
            new DenseIntMatrix1D( new int[]{6, 7} ),
            new DenseIntMatrix1D( new int[]{6, 9} )
        ).collect( Collectors.toList() );

        assertEquals( l_route.size(), l_waypoint.size() );
        IntStream.range( 0, l_waypoint.size() ).boxed().forEach( i -> assertEquals( l_waypoint.get( i ), l_route.get( i ) ) );
    }


    public void testjump()
    {
        //System.out.println( new CJPSPlus().jump(new ImmutablePair<>(2,3),new ImmutablePair<>(5,4) , 0, 1, m_grid));
    }

    public void isOccupied()
    {
        //System.out.println(new CJPSPlus().isOccupied(m_grid, 2, 3 + 1 ));
    }


    public void isnotNeighbour()
    {
       // System.out.println(new CJPSPlus().horizontal(2, 3, -1, m_grid, 1));
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
        //new TestCJPSPlus().testjump();
        //new TestCJPSPlus().isOccupied();
       // new TestCJPSPlus().isnotNeighbour();;
    }


}
