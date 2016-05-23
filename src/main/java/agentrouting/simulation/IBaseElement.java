package agentrouting.simulation;

import cern.colt.matrix.tint.IntMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import lightjason.agentspeak.agent.CAgent;
import lightjason.agentspeak.common.CPath;
import lightjason.agentspeak.configuration.IAgentConfiguration;
import lightjason.agentspeak.language.CCommon;
import lightjason.agentspeak.language.ITerm;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;


/**
 * abstract class for all preferences access
 */
public abstract class IBaseElement<T> extends CAgent<IBaseElement<T>> implements IElement<T>
{
    /**
     * current position of the agent
     */
    protected final IntMatrix1D m_position;
    /**
     * reference to the environment
     */
    protected final IEnvironment m_environment;
    /**
     * sprite object for painting
     */
    private Sprite m_sprite;

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfig agent configuration
     * @param p_position initial position
     */
    protected IBaseElement( final IEnvironment p_environment, final IAgentConfiguration p_agentconfig, final IntMatrix1D p_position )
    {
        super( p_agentconfig );
        m_environment = p_environment;
        m_position = p_position;
    }

    @Override
    public final IntMatrix1D position()
    {
        return m_position;
    }

    @Override
    public final Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public final Sprite spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize )
    {
        m_sprite = this.visualization( p_rows, p_columns, p_cellsize );
        return m_sprite;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public final T execute( final int p_step )
    {
        // run agent-cycle
        try
        {
            super.call();
        }
        catch ( final Exception p_exception )
        {
            LOGGER.warning( p_exception.getMessage() );
        }

        // update sprite for painting (sprit position is x/y position, but position storing is row / column)
        if ( m_sprite != null )
            m_sprite.setPosition( m_position.get( 1 ), m_position.get( 0 ) );

        return (T) this;
    }

    @Override
    public final Stream<Map.Entry<String, Double>> preferences()
    {
        return this.getBeliefBase().stream( CPath.from( "preference" ) )
                   .map( i -> new AbstractMap.SimpleImmutableEntry<String, Double>(
                           i.getFQNFunctor().getSuffix(),
                           CCommon.<Double, ITerm>getRawValue( i.orderedvalues().findFirst().get() )
                   ) );

    }

    /**
     * updates the interal position of the agent, also within the environment
     * by the weighted fitness probability selection
     *
     * @param p_direction defied as an enum structure [ 7 0 1; 6 X 2; 5 4 3], so 0 -> straight forward (top) and than clockwise
     * @param p_distance distance to the target field (jumping to a position is possible)
     * @todo check cells if p_distance > 1
     * @see https://en.wikipedia.org/wiki/Fitness_proportionate_selection to calculate the direction
     */
    protected final IElement<?> updateposition( final Map<EDirection, Double> p_direction, final int p_distance )
    {
        /*
        return m_environment.position(
                this,
                EDirection.FORWARD.position( m_position, p_distance )
        );
        */
        return this;
    }


    /**
     * create the sprite for painting
     *
     * @param p_rows row number
     * @param p_columns column number
     * @param p_cellsize cell size
     * @return sprite object
     */
    protected abstract Sprite visualization( final int p_rows, final int p_columns, final int p_cellsize );

}
