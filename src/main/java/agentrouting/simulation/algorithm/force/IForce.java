package agentrouting.simulation.algorithm.force;

import agentrouting.simulation.IElement;

import java.util.stream.Stream;


/**
 * interface for force-model
 */
public interface IForce
{

    /**
     * calculates the metric-force value between two elements
     *
     * @param p_self element which uses the force value
     * @param p_other other elements
     * @return value in [-1, 1]
     */
    double calculate( final IElement<?> p_self, final Stream<IElement<?>> p_other );

}
