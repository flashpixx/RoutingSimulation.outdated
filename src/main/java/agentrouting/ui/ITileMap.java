package agentrouting.ui;

import com.badlogic.gdx.maps.tiled.TiledMap;


/**
 * interface for creating a tilemap (environment)
 */
public interface ITileMap extends IVisible
{

    /**
     * returns the tilemap
     *
     * @return map
     */
    TiledMap map();

    /**
     * returns the number of rows
     *
     * @return rows
     */
    int row();

    /**
     * returns the number of columns
     *
     * @return columns
     */
    int column();

    /**
     * returns the cell size
     *
     * @return cell size
     */
    int cellsize();

}
