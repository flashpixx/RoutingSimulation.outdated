package agentrouting.simulation.algorithm.routing;

import agentrouting.simulation.IElement;
import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * JPS+ algorithm
 * @todo for all current Java componentes read https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html first
 */
public final class CJPSPlus implements IRouting
{
    /**
     * map with position (y/x) and the jump-point
     * @todo if you are putting data in parallel to the map, use instead of a HashMap a ConcurrentHashMap (https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html)
     */
    private final Map<Pair<Integer,Integer>, CJumpPoint> m_staticjumppoints = new HashMap<>();



    @Override
    public final IRouting initialize( final ObjectMatrix2D p_objects )
    {
        // initialize the jump-point map with jump-points for static elements e.g. walls,
        // this method is called once from the environment and the full grid with all static
        // elements is pushed in

        // for creating a jump-point (see for parallel @todo on top)
        // the pair represent the coordination of the jump-point, but it can be also represent as an IntMatrix1D
        // and the jump-point is put in, if there not exists a jump-point on the position,
        // within the Apache Commons library exists more other container elements see http://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/tuple/package-summary.html
        m_staticjumppoints.putIfAbsent( new ImmutablePair<>( 2, 5 ), new CJumpPoint() );

        return this;
    }

    @Override
    public final List<IntMatrix1D> route( final ObjectMatrix2D p_objects, final IElement<?> p_element, final IntMatrix1D p_target )
    {
        // just cloning the map with static jump-points
        // we are coping the referenced jump-points to a local map and work with the local map (removing elements is possible without any problems)
        final Map<Pair<Integer,Integer>, CJumpPoint> l_dynamicjumppoints = new HashMap<>( m_staticjumppoints );

        return Collections.<IntMatrix1D>emptyList();
    }

    @Override
    public final double estimatedtime( final ObjectMatrix2D p_objects, final List<IntMatrix1D> p_route, final double p_speed )
    {
        return 0;
    }


    /**
     * jump-point with a static class
     * "static" means that the class can exists without the CJPSPlus object
     * "final" no inheritance can be create
     * @todo think about this structure, because can an agent have individual g- / h-score values for a jump-point e.g. based on it own behaviour?
     * If yes, you need to create a jump-point interface and different types of jump-point classes
     */
    private static final class CJumpPoint
    {
        /**
         * jump-point g-score value
         * @todo some description, what a g-score value is
         */
        private final int m_gscore;

        /**
         * jump-point h-score value
         * @todo some description, what a h-score value is
         */
        private final int m_hscore;

        /**
         * ctor - with default values
         */
        public CJumpPoint()
        {
            this( 0, 0 );
        }

        /**
         * ctor
         *
         * @param p_gscore gscore value
         * @param p_hscore hscore value
         */
        public CJumpPoint( final int p_gscore, final int p_hscore )
        {
            m_gscore = p_gscore;
            m_hscore = p_hscore;
        }

        /**
         * getter for g-score
         *
         * @return g-score
         */
        public final int gscore()
        {
            return m_gscore;
        }

        /**
         * getter for h_score
         *
         * @return h-score
         */
        public final int hscore()
        {
            return m_hscore;
        }

    }

}
