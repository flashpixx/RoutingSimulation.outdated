package agentrouting.simulation.agent.pokemon;


import agentrouting.simulation.agent.EAccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * ethncity of a pokemon agent
 * @see http://bulbapedia.bulbagarden.net/wiki/Type
 * @see http://jkunst.com/r/pokemon-visualize-em-all/
 */
public enum EEthncity
{
    NORMAL( EAccess.READ ),
    FIGHTING( EAccess.READ ),
    FLYING( EAccess.READ ),
    POISON( EAccess.READ ),
    GROUND( EAccess.READ ),
    ROCK( EAccess.READ ),
    BUG( EAccess.READ ),
    GHOST( EAccess.READ ),
    STEEL( EAccess.READ ),
    FIRE( EAccess.READ ),
    WATER( EAccess.READ ),
    GRASS( EAccess.READ ),
    ELECTRIC( EAccess.READ ),
    PSYCHIC( EAccess.READ ),
    ICE( EAccess.READ ),
    DRAGON( EAccess.READ ),
    DARK( EAccess.READ ),
    FAIRY( EAccess.READ );


    /**
     * string name of enums for existance cheinkg
     */
    private static final Set<String> NAMES;

    static
    {
        NAMES = Collections.unmodifiableSet( Arrays.stream( EEthncity.values() ).map( i -> i.toString().toLowerCase() ).collect( Collectors.toSet() ) );
    }

    /**
     * access to change by the agent
     */
    private final EAccess m_access;

    /**
     * ctor
     * @param p_access agent access
     */
    EEthncity( final EAccess p_access )
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
