package agentrouting.simulation.algorithm.routing;

import agentrouting.simulation.IElement;
import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;

import java.util.List;


/**
 * routing interface
 */
public interface IRouting
{

    /**
     * runs the initialization process from the environment
     *
     * @param p_objects full initialized environment grid (static elements)
     * @return self reference
     */
    IRouting initialize( final ObjectMatrix2D p_objects );

    /**
     * routing algorithm
     *
     * @param p_objects object matrix
     * @param p_element element
     * @param p_target target position
     * @return list of tuples of the cellindex
     */
    List<IntMatrix1D> route( final ObjectMatrix2D p_objects, final IElement<?> p_element, final IntMatrix1D p_target );

    /**
     * calculated the estimated time to move the path
     *
     * @param p_objects object matrix
     * @param p_route route list
     * @param p_speed estimated speed
     * @return speed of the full path
     */
    double estimatedtime( final ObjectMatrix2D p_objects, final List<IntMatrix1D> p_route, final double p_speed );

}
