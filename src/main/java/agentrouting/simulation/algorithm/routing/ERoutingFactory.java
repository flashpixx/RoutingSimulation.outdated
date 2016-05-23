package agentrouting.simulation.algorithm.routing;


/**
 * factory for creating routing objects
 */
public enum ERoutingFactory
{
    JPSPLUS;

    /**
     * creates the routing object
     *
     * @return routing object
     */
    public final IRouting build()
    {
        switch ( this )
        {
            case JPSPLUS:
                return new CJPSPlus();

            default:
                throw new IllegalArgumentException( "unknown routing algorithm" );
        }

    }
}
