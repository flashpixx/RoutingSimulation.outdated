package agentrouting.simulation.agent;

import agentrouting.simulation.environment.CEnvironment;
import agentrouting.simulation.environment.IEnvironment;
import agentrouting.simulation.algorithm.force.EForceFactory;
import agentrouting.simulation.algorithm.routing.ERoutingFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.util.Collections;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * agent test
 */
public final class TestCAgent
{
    /**
     * environment reference
     */
    private IEnvironment m_environment;
    /**
     * action references
     */
    private Set<IAction> m_actions;


    /**
     * data initialization
     */
    @Before
    public void initialize()
    {
        // disable logging
        LogManager.getLogManager().reset();

        m_environment = new CEnvironment( 100, 100, 25, ERoutingFactory.JPSPLUS.get() );

        m_actions = Collections.unmodifiableSet( Stream.concat(
            org.lightjason.agentspeak.common.CCommon.actionsFromPackage(),
            org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CMovingAgent.class )
        ).collect( Collectors.toSet() ) );
    }


    /**
     * test moving agent generator
     * @throws Exception throws any exceptions
     */
    @Test
    public void testMovingAgentGenerator() throws Exception
    {
        Assume.assumeNotNull( m_environment );
        Assume.assumeNotNull( m_actions );

        new CMovingAgentGenerator(
            m_environment,
            TestCAgent.class.getResourceAsStream( "/agentrouting/agent.asl" ),
            m_actions,
            IAggregation.EMPTY
        ).generatesingle( EForceFactory.SUM.get(), "000000" );
    }


}
