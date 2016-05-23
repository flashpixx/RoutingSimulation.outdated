package agentrouting.simulation;

import agentrouting.ui.ISprite;
import cern.colt.matrix.tint.IntMatrix1D;

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
     * returns the individual preference value in [-1,1]
     *
     * @param p_id identifier name
     * @return double value in [-1,1]
     */
    double getPreference( final String p_id );

    /**
     * set of preference IDs
     *
     * @return set with preferences
     */
    Set<String> preference();

    /**
     * sets the individual preference value
     *
     * @param p_id identifier name
     * @param p_value value in [-1,1]
     * @returns agent object reference
     */
    void setPreference( final String p_id, final double p_value );

    /**
     * returns the current position of the object
     *
     * @return tupel
     */
    IntMatrix1D position();

}
