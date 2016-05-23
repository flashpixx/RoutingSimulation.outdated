package agentrouting.simulation;

import agentrouting.ui.ISprite;
import cern.colt.matrix.tint.IntMatrix1D;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;


/**
 * interface of a basic element that can be
 * executable within the simulation (so position
 * and preferences are required)
 */
public interface IElement<T> extends IExecutable<T>, ISprite
{

    /**
     * returns a stream of all preferences
     *
     * @return preference stream
     */
    Stream<Map.Entry<String, Double>> preferences();

    /**
     * returns the current position of the object
     *
     * @return tupel
     */
    IntMatrix1D position();

}
