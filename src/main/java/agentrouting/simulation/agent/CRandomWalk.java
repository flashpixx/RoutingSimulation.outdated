package agentrouting.simulation.agent;

import agentrouting.simulation.EDirection;
import agentrouting.simulation.IEnvironment;
import cern.colt.matrix.tint.IntMatrix1D;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * random-walk agent
 */
@Deprecated
public final class CRandomWalk
{
    /**
     * direction probabilities
     */
    private final Map<EDirection, Double> m_directionprobability = new HashMap<>();

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_position initialize position
     * @param p_preference preference value
     * @param p_name agent name
     * @param p_color color string in RRGGBBAA
     */
    public CRandomWalk( final IEnvironment p_environment, final IntMatrix1D p_position,
                        final Map<String, Double> p_preference,
                        final String p_name,
                        final String p_color
    )
    {
        //super( p_environment, p_position, p_preference, p_name, p_color );
        Arrays.stream( EDirection.values() ).forEach( i -> m_directionprobability.put( i, 1.0 / EDirection.values().length ) );
    }

/*
    @Override
    public IAgent execute( final int p_step )
    {
        LOGGER.info( MessageFormat.format( "execute agent [{0}] in step [{1}]", this, p_step ) );

        // calculate the force between agent and all agents around (5 cells in each direction)
        final double l_force = m_environment.force().calculate(
                this,
                m_environment.perceive( m_position, 5, EDirection.FORWARD, EDirection.FORWARDLEFT, EDirection.FORWARDRIGHT ).stream()
        ) / 3;

        // update the probility of the directions, positive value are attraction (increment probability), negative value are sheding (decrement probability)

        // @Fatema: Modify within the configuration.yml the preference values and take a look on the result, I have used onle forward & backward direction
        // but here you can set the force for each direction in detail, also the below the distance on updateposition, so the force can be affected
        // to the distance
        if ( l_force > 0 )
        {
            m_directionprobability.put( EDirection.FORWARD, l_force );
            m_directionprobability.put( EDirection.FORWARDLEFT, l_force );
            m_directionprobability.put( EDirection.FORWARDRIGHT, l_force );
        }
        else
        {
            m_directionprobability.put( EDirection.BACKWARD, Math.abs( l_force ) );
            m_directionprobability.put( EDirection.BACKWARDLEFT, Math.abs( l_force ) );
            m_directionprobability.put( EDirection.BACKWARDRIGHT, Math.abs( l_force ) );
        }

        // update agent position
        this.updateposition( m_directionprobability, 1 );
        return super.execute( p_step );
    }
*/

}
