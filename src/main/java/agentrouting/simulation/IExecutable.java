package agentrouting.simulation;

/**
 * executable interface
 */
public interface IExecutable<T>
{

    /**
     * executes the object
     *
     * @param p_step current iteration
     * @return self reference
     */
    T execute( final int p_step );

}
