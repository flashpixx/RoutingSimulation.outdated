package agentrouting.simulation.algorithm.routing;

import agentrouting.simulation.IElement;
import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;

import java.util.Collections;
import java.util.List;


/**
 * JPS+ algorithm
 */
public final class CJPSPlus implements IRouting
{
    @Override
    public final IRouting initialize( final ObjectMatrix2D p_objects )
    {
        return this;
    }

    @Override
    public final List<IntMatrix1D> route( final ObjectMatrix2D p_objects, final IElement<?> p_element, final IntMatrix1D p_target
    )
    {
        return Collections.<IntMatrix1D>emptyList();
    }

    @Override
    public final double estimatedtime( final ObjectMatrix2D p_objects, final List<IntMatrix1D> p_route, final double p_speed )
    {
        return 0;
    }
}
