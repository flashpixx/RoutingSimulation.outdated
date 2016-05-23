package agentrouting.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * sprite (element) visualisation interface
 */
public interface ISprite extends IVisible
{

    /**
     * returns the sprite
     *
     * @return sprite object
     */
    Sprite sprite();

    /**
     * sprite initialize for correct painting initialization
     *
     * @param p_rows number of rows
     * @param p_columns number of columns
     * @param p_cellsize cellsize
     * @return sprite object
     */
    Sprite spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize );

}
