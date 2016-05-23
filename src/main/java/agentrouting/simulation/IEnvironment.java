package agentrouting.simulation;


import agentrouting.simulation.agent.IAgent;
import agentrouting.simulation.algorithm.force.IForce;
import agentrouting.ui.ITileMap;
import cern.colt.matrix.tint.IntMatrix1D;
import lightjason.agentspeak.beliefbase.IBeliefBaseUpdate;

import java.util.List;


/**
 * environment interface
 */
public interface IEnvironment extends IBeliefBaseUpdate<IAgent>, IExecutable<IEnvironment>, ITileMap
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

    /**
     * run initialization of the environment
     *
     * @return self reference
     */
    IEnvironment initialize();

}
