package agentrouting.simulation.agent.pokemon;


import agentrouting.simulation.agent.EPreferenceAccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * preferences
 * @see http://bulbapedia.bulbagarden.net/wiki/Type
 */
public enum EPreferences
{
    // current changable preference
    NEARBY( EPreferenceAccess.WRITE ),
    HUNT( EPreferenceAccess.WRITE ),
    FIGHT( EPreferenceAccess.WRITE ),
    HELP( EPreferenceAccess.WRITE ),
    IGNORANCE( EPreferenceAccess.WRITE ),
    CAUTIOUSNESS( EPreferenceAccess.WRITE ),
    ELOPEMENT( EPreferenceAccess.WRITE ),

    // individual not-changable
    ENERGY( EPreferenceAccess.READ ),
    HEALTH( EPreferenceAccess.READ ),
    EXPERIENCE( EPreferenceAccess.READ ),
    ATTACK( EPreferenceAccess.READ ),
    DEFENSE( EPreferenceAccess.READ ),
    WEIGHT( EPreferenceAccess.READ ),
    HEIGHT( EPreferenceAccess.READ ),

    // pokemon tyoes
    NORMAL( EPreferenceAccess.READ ),
    FIGHTING( EPreferenceAccess.READ ),
    FLYING( EPreferenceAccess.READ ),
    POISON( EPreferenceAccess.READ ),
    GROUND( EPreferenceAccess.READ ),
    ROCK( EPreferenceAccess.READ ),
    BUG( EPreferenceAccess.READ ),
    GHOST( EPreferenceAccess.READ ),
    STEEL( EPreferenceAccess.READ ),
    FIRE( EPreferenceAccess.READ ),
    WATER( EPreferenceAccess.READ ),
    GRASS( EPreferenceAccess.READ ),
    ELECTRIC( EPreferenceAccess.READ ),
    PSYCHIC( EPreferenceAccess.READ ),
    ICE( EPreferenceAccess.READ ),
    DRAGON( EPreferenceAccess.READ ),
    DARK( EPreferenceAccess.READ ),
    FAIRY( EPreferenceAccess.READ );


    /**
     * string name of enums for existance cheinkg
     */
    private static final Set<String> NAMES;

    static
    {
        NAMES = Collections.unmodifiableSet( Arrays.stream( EPreferences.values() ).map( i -> i.toString().toLowerCase() ).collect( Collectors.toSet() ) );
    }

    /**
     * access to change by the agent
     */
    private final EPreferenceAccess m_access;

    /**
     * ctor
     * @param p_access agent access
     */
    EPreferences( final EPreferenceAccess p_access )
    {
        m_access = p_access;
    }

    /**
     * returns the access
     * @return access
     */
    public final EPreferenceAccess access()
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
