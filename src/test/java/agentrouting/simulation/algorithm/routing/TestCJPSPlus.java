package agentrouting.simulation.algorithm.routing;

import cern.colt.matrix.tint.impl.DenseIntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import cern.colt.matrix.tobject.impl.SparseObjectMatrix2D;
import org.junit.Before;
import org.junit.Test;


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
        System.out.println( new CJPSPlus().route( m_grid, new DenseIntMatrix1D( 2 ), new DenseIntMatrix1D( new int[]{9, 9} ) ) );
    }



    public static void main( final String[] p_args )
    {
        new TestCJPSPlus().testrouting();
    }


}
