package agentrouting.simulation;


import java.util.List;

import agentrouting.simulation.algorithm.force.IForce;
import agentrouting.ui.ITileMap;
import cern.colt.matrix.tint.IntMatrix1D;


/**
 * environment interface
 */
public interface IEnvironment extends IExecutable<IEnvironment>, ITileMap
{

    /**
     * calculate route
     *
     * @param p_object element
     * @param p_target target point
     * @return list of tuples of the cellindex
     */
    List<IntMatrix1D> route( final IElement<?> p_object, final IntMatrix1D p_target );

    /**
     * sets an object to the position and changes the object position
     *
     * @param p_object object, which should be moved (must store the current position)
     * @param p_position new position
     * @return updated object or object which uses the cell
     */
    IElement<?> position( final IElement<?> p_object, final IntMatrix1D p_position );

    /**
     * perceive all elements in the direction and returns a list of all items
     *
     * @param p_position current position
     * @param p_distance distance
     * @param p_direction directions
     * @return element list
     */
    List<IElement<?>> perceive( final IntMatrix1D p_position, final int p_distance, final EDirection... p_direction );

    /**
     * returns the force object
     *
     * @return force object
     */
    IForce force();

    /**
     * returns the number of rows
     *
     * @return rows
     */
    @Override
    int row();

    /**
     * returns the number of columns
     *
     * @return columns
     */
    @Override
    int column();

    /**
     * returns the cell size
     *
     * @return cell size
     */
    @Override
    int cellsize();

    /**
     * run initialization of the environment
     *
     * @return self reference
     */
    IEnvironment initialize();

}
