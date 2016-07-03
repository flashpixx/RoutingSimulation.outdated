package agentrouting.simulation.algorithm.routing;

import org.junit.Before;
import org.junit.Test;

import cern.colt.matrix.tint.impl.DenseIntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import cern.colt.matrix.tobject.impl.SparseObjectMatrix2D;


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


    @Test
    public void testrouting()
    {
        System.out.println( new CJPSPlus().route( m_grid, new DenseIntMatrix1D( new int[]{2, 3} ) , new DenseIntMatrix1D( new int[]{8, 6} ) ) );
    }

    @Test
    public void testjump()
    {
        //System.out.println( new CJPSPlus().jump(new ImmutablePair<>(2,3),new ImmutablePair<>(5,4) , 0, 1, m_grid));
    }

    @Test
    public void isOccupied()
    {
        //System.out.println(new CJPSPlus().isOccupied(m_grid, 2, 3 + 1 ));
    }

    @Test
    public void isnotNeighbour()
    {
       // System.out.println(new CJPSPlus().horizontal(2, 3, -1, m_grid, 1));
    }




    public static void main( final String[] p_args )
    {
        new TestCJPSPlus().testrouting();
        //new TestCJPSPlus().testjump();
        //new TestCJPSPlus().isOccupied();
       // new TestCJPSPlus().isnotNeighbour();;
    }


}
