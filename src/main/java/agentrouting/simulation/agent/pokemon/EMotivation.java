package agentrouting.simulation.agent.pokemon;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * enum for defining the motivation value of an pokemon agent
 */
public enum EMotivation
{
    NEARBY,
    HUNT,
    FIGHT,
    HELP,
    IGNORANCE,
    CAUTIOUSNESS,
    ELOPEMENT,
    CURE;

    /**
     * string name of enums for existance checking
     */
    private static final Set<String> NAMES;

    static
    {
        NAMES = Collections.unmodifiableSet( Arrays.stream( EMotivation.values() ).map( i -> i.toString().toLowerCase() ).collect( Collectors.toSet() ) );
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
