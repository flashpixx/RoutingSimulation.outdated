package agentrouting.simulation.algorithm.force;

import agentrouting.simulation.IElement;

import java.util.stream.Stream;


/**
 * sum force value
 */
public final class CSum implements IForce
{
    @Override
    public final double calculate( final IElement<?> p_self, final Stream<IElement<?>> p_other )
    {
        return p_other.parallel()
                      .mapToDouble( i -> elementwise( p_self, i ) )
                      .sum();
    }

    /**
     * calculate the force between two elements
     *
     * @param p_self first element
     * @param p_other other element
     * @return force
     */
    private static double elementwise( final IElement<?> p_self, final IElement<?> p_other )
    {
        return Stream.concat(
                p_self.preference().stream(),
                p_other.preference().stream()
        )
                     // create a sorted distinct keyset and run in parallel
                     .sorted().distinct()
                     .parallel()
                     // sum of both preferences
                     .mapToDouble( i -> p_self.getPreference( i ) + p_other.getPreference( i ) )
                     .boxed()
                     // maximum is -2 or 2, so normalize and scale it in [-1,1]
                     .mapToDouble( i -> i / 2.0 )
                     // and return the average
                     .average().getAsDouble();

    }
}
