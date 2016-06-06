package agentrouting.simulation.agent;

import agentrouting.simulation.IBaseElement;
import agentrouting.simulation.IEnvironment;
import cern.colt.matrix.tint.IntMatrix1D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Logger;


/**
 * agent class for modelling individual behaviours
 */
public abstract class IBaseAgent extends IBaseElement<IAgent> implements IAgent
{
    /**
     * logger
     */
    protected static final Logger LOGGER = Logger.getLogger( IAgent.class.getName() );
    /**
     * agent name
     */
    private final String m_name;
    /**
     * color
     */
    private final Color m_color;



    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_position initialize position
     * @param p_preference preference value
     * @param p_name agent name
     * @param p_color color string in RRGGBBAA
     */
    protected IBaseAgent( final IEnvironment p_environment, final IntMatrix1D p_position, final Map<String, Double> p_preference, final String p_name,
                          final String p_color
    )
    {
        super( p_environment, p_position );
        LOGGER.info( MessageFormat.format( "create agent [{0}] with position / name / color [{1} / {2} / {3}]", this, p_position, p_name, p_color ) );

        m_name = p_name;
        m_color = Color.valueOf( p_color );
        p_preference.entrySet().parallelStream().forEach( i -> this.setPreference( i.getKey(), i.getValue() ) );
    }

    @Override
    protected final Sprite visualization( final int p_rows, final int p_columns, final int p_cellsize )
    {
        // create a colored sequare for the agent
        final Pixmap l_pixmap = new Pixmap( p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
        l_pixmap.setColor( m_color );
        l_pixmap.fillRectangle( 0, 0, p_cellsize, p_cellsize );

        // add the square to a sprite (for visualization) and scale it to 80% of cell size
        final Sprite l_sprite = new Sprite( new Texture( l_pixmap ), 0, 0, p_cellsize, p_cellsize );
        l_sprite.setSize( 0.9f * p_cellsize, 0.9f * p_cellsize );
        l_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );

        return l_sprite;
    }

}
