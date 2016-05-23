package agentrouting.simulation;

import cern.colt.matrix.tint.IntMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.util.Precision;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * abstract class for all preferences access
 */
public abstract class IBaseElement<T> implements IElement<T>
{
    /**
     * thread-safe map with individual preference value,
     * key-value is always lower-case
     **/
    protected final Map<String, Double> m_preference = new ConcurrentHashMap<>();
    /**
     * current position of the agent
     */
    protected final IntMatrix1D m_position;
    /**
     * reference to the environment
     */
    protected final IEnvironment m_environment;
    /**
     * random generator
     */
    private final RandomGenerator m_random = new MersenneTwister();
    /**
     * sprite object for painting
     */
    private Sprite m_sprite;

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_position position
     */
    protected IBaseElement( final IEnvironment p_environment, final IntMatrix1D p_position )
    {
        m_environment = p_environment;
        m_position = p_position;
    }


    @Override
    public final double getPreference( final String p_id )
    {
        // if not found we return an epsilon value for avoid "division by zero"
        return m_preference.getOrDefault( p_id.trim().toLowerCase(), Precision.EPSILON );
    }

    @Override
    public final void setPreference( final String p_id, final double p_value )
    {
        if ( ( p_value < -1 ) || ( p_value > 1 ) )
            throw new IllegalArgumentException( "preference value must be in [-1,1]" );

        // if value is zero, we set an epsilon value for avoid "division by zero"
        m_preference.put( p_id.trim().toLowerCase(), p_value == 0 ? Precision.EPSILON : p_value );
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
    public T execute( final int p_step )
    {
        // update sprite for painting (sprit position is x/y position, but position storing is row / column)
        if ( m_sprite != null )
            m_sprite.setPosition( m_position.get( 1 ), m_position.get( 0 ) );

        return (T) this;
    }

    @Override
    public final Set<String> preference()
    {
        return m_preference.keySet();
    }

    @Override
    public final Stream<Map.Entry<String, Double>> preferences()
    {
        return m_preference.entrySet().stream();
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
        return m_environment.position(
                this,

                new EnumeratedDistribution<>(
                        m_random,
                        p_direction.entrySet().stream()
                                   .map( i -> new Pair<>( i.getKey(), i.getValue() ) )
                                   .collect( Collectors.toList() )
                )
                        .sample()
                        .position( m_position, p_distance )
        );
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
