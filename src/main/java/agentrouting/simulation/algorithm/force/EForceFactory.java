package agentrouting.simulation.algorithm.force;


/**
 * factory for creating a force object
 */
public enum EForceFactory
{
    SUM;

    /**
     * creates the force object
     *
     * @return force object
     */
    public final IForce build()
    {
        switch ( this )
        {
            case SUM:
                return new CSum();

            default:
                throw new IllegalArgumentException( "unknown force algorithm" );
        }
    }

}
