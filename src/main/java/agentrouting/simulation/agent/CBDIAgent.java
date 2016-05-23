package agentrouting.simulation.agent;

import agentrouting.simulation.EDirection;
import agentrouting.simulation.IEnvironment;
import cern.colt.matrix.tint.IntMatrix1D;
import lightjason.agentspeak.configuration.IAgentConfiguration;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;


/**
 * BDI agent
 */
public class CBDIAgent extends IBaseAgent
{

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_position initialize position
     * @param p_agentconfiguration agent configuration
     * @param p_name agent name
     * @param p_color color string in RRGGBBAA
     */
    public CBDIAgent( final IEnvironment p_environment, final IAgentConfiguration p_agentconfiguration,
                          final IntMatrix1D p_position, final String p_name, final String p_color
    )
    {
        super( p_environment, p_agentconfiguration, p_position, p_name, p_color );
        Arrays.stream( EDirection.values() ).forEach( i -> m_directionprobability.put( i, 1.0 / EDirection.values().length ) );
    }

}
