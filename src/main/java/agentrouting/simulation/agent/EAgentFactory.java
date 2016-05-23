package agentrouting.simulation.agent;

import agentrouting.simulation.IEnvironment;
import cern.colt.matrix.tint.IntMatrix1D;

import java.util.Map;


/**
 * factory for creating agent object
 */
public enum EAgentFactory
{
    RANDOMWALK;

    /**
     * creates the agent, parameters equal to agent ctor parameters
     *
     * @param p_environment environment
     * @param p_position initialize position
     * @param p_preference preference value
     * @param p_name agent name
     * @param p_color color string in RRGGBBAA
     * @return agent
     */
    public final IAgent build( final IEnvironment p_environment, final IntMatrix1D p_position,
                               final Map<String, Double> p_preference,
                               final String p_name,
                               final String p_color
    )
    {
        switch ( this )
        {
            case RANDOMWALK:
                return new CRandomWalk( p_environment, p_position, p_preference, p_name, p_color );

            default:
                throw new IllegalArgumentException( "agent type unknown" );
        }
    }

}
