package agentrouting.simulation.agent.pokemon;

import agentrouting.simulation.agent.EAccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * enum for defining the motivation value of an pokemon agent
 */
public enum EMotivation
{
    NEARBY( EAccess.WRITE ),
    HUNT( EAccess.WRITE ),
    FIGHT( EAccess.WRITE ),
    HELP( EAccess.WRITE ),
    IGNORANCE( EAccess.WRITE ),
    CAUTIOUSNESS( EAccess.WRITE ),
    ELOPEMENT( EAccess.WRITE ),
    CURE( EAccess.WRITE );

    /**
     * string name of enums for existance checking
     */
    private static final Set<String> NAMES;

    static
    {
        NAMES = Collections.unmodifiableSet( Arrays.stream( EMotivation.values() ).map( i -> i.toString().toLowerCase() ).collect( Collectors.toSet() ) );
    }

    /**
     * access to change by the agent
     */
    private final EAccess m_access;

    /**
     * ctor
     * @param p_access agent access
     */
    EMotivation( final EAccess p_access )
    {
        m_access = p_access;
    }

    /**
     * returns the access
     * @return access
     */
    public final EAccess access()
    {
        return m_access;
    }

    /**
     * checkes if a enum exists with a string name
     *
     * @param p_name name
     * @return existance
     */
    public static boolean exist( final String p_name )
    {
        return NAMES.contains( p_name.trim().toLowerCase() );
    }

}
