package agentrouting.simulation.element;

import agentrouting.simulation.IElement;


/**
 * factory for creating any static element
 */
public enum EElementFactory
{
    NOONE;

    /**
     * creates a static element
     *
     * @return element
     */
    public final IElement<?> build()
    {
        return null;
    }

}
