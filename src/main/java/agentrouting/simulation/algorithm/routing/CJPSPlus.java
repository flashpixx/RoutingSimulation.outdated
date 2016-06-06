package agentrouting.simulation.algorithm.routing;

import agentrouting.simulation.IBaseElement;
import agentrouting.simulation.IElement;
import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.badlogic.gdx.maps.Map;

import agentrouting.simulation.algorithm.routing.IPriorityQueue;
import agentrouting.simulation.algorithm.routing.BinaryHeap;

/**
 * JPS+ algorithm
 */
public final class CJPSPlus implements IRouting
{   

    private GridNode[][] myNodes;
	private GridNode startNode, endNode;
    private IPriorityQueue openList;
    private ArrayList<GridNode> closedList;    //private LinkedHashMap<IntMatrix1D,Long> openList=new LinkedHashMap<IntMatrix1D,Long>();
	private IntMatrix1D grid;
    //private ArrayList<IntMatrix1D> closedList;
    //private int gScore, hScore;
    @Override
    public final IRouting initialize( final ObjectMatrix2D p_objects )
    {
        return this;
    }
    
    /**
     * Finds the shortest path between two nodes using the Jump Point Search Algorithm
     * @return A path of nodes
     */
    @Override
    public final List<IntMatrix1D> route( final ObjectMatrix2D p_objects, final IElement<?> p_element, final IntMatrix1D p_target
    )
    {   
    	startNode=new GridNode(p_element.position().getQuick(0),p_element.position().getQuick(1),true);
    	endNode=new GridNode(p_target.getQuick(0),p_target.getQuick(1),true);
    	
        return jumpPointSearch(startNode,endNode);
    }
    
    /**
     * Finds the shortest path between two nodes using the Jump Point Search Algorithm
     * @return A path of nodes
     */
    public List<IntMatrix1D> jumpPointSearch(GridNode startNode,GridNode endNode)
    {
        closedList = new ArrayList<>();
        openList = new BinaryHeap(startNode);
        
        while(!openList.isEmpty())
        {
            GridNode curNode = (GridNode)openList.pop(); //Get the next node with the lowest fScore
            
            if(curNode.equals(endNode)) //If this node is the end node we are done
                return backTrace(curNode); // Return the current node to back trace through to find the path
            
            identifySuccessors(curNode);    //Find the successors to this node (add them to the openList)
            
            closedList.add(curNode);    //Add the curnode to the closed list (as to not open it again)
        }

        return null;
    }//calculate jumpPointSearch
    
    /**
     * Calculates the given node's score using the Manhattan distance formula
     * @param node The node to calculate
     */
    private void calculateNodeScore(GridNode node)
    {
        int manhattan = Math.abs(endNode.getX() - node.getX()) * 10 + Math.abs(endNode.getY() - node.getY()) * 10;
        
        node.setHScore(manhattan);
        
        GridNode parent = (GridNode)node.getParent();
        
        node.setGScore(parent.getGScore() + calcuateGScore(node, parent));   
    }//end calculateNodeScore
    
    /**
     * Will calculate the gScore between two nodes
     * @param newNode
     * @param oldNode
     * @return The gScore between the nodes
     */
    private int calcuateGScore(GridNode newNode, GridNode oldNode)
    {
        int dx = newNode.getX() - oldNode.getX();
        int dy = newNode.getY() - oldNode.getY();
        
            if(dx == 0 || dy == 0) //Horizontal or vertical
                return Math.abs(10 * Math.max(dx, dy));
            else                   // Diaganol
                return Math.abs(14 * Math.max(dx, dy));
    }//end calculateGScore
    
    
    /**
     * Finds the successors the the curNode and adds them to the openlist
     * @param curNode The current node to search for
     */
    private void identifySuccessors(GridNode curNode)
    {
        //Use two for loops to cycle through all 8 directions
        for(int dx = -1; dx <= 1; dx++) 
        {
            for(int dy = -1; dy <= 1; dy++)
            {
                if(dx == 0 && dy == 0) //Skip the curNode 
                    continue;
                
                //If the neighbor exists at this direction and is valid contiue
                if(isValidNeighbor(curNode,getNode(curNode.getX() + dx, curNode.getY() + dy)))
                {
                    //Try to find a jump node (One that is further down the path and has a forced neighbor)
                    GridNode jumpNode = jump(curNode, dx, dy);
                    if(jumpNode != null)
                    {
                        //If we found one add it to the open list if its not already on it
                        if(!openList.contains(jumpNode) && !closedList.contains(jumpNode))
                        {
                            jumpNode.setParent(curNode); //Set its parent so we can find our way back later
                            calculateNodeScore(jumpNode);   //Calculate its score to pull it from the open list later
                            openList.add(jumpNode);     // Add it to the open list for continuing the path
                        }
                    }
                }
                
            }
        }
    }//end identifySuccessors
    
    /**
     * Finds the next node heading in a given direction that has a forced neighbor or is significant
     * @param curNode The node we are starting at
     * @param dx The x direction we are heading
     * @param dy The y direction we are heading
     * @return The node that has a forced neighbor or is significant
     */
    private GridNode jump(GridNode curNode, int dx, int dy)
    {
        //The next nodes details
        int nextX = curNode.getX() + dx;
        int nextY = curNode.getY() + dy;
        GridNode nextNode = getNode(nextX, nextY);
        
        // If the nextNode is null or impassable we are done here
        if(nextNode == null || !nextNode.isPassable()) return null;
        
        //If the nextNode is the endNode we have found our target so return it
        if(nextNode.equals(endNode)) return nextNode;
        
        //If we are going in a diaganol direction check for forced neighbors
        if(dx != 0 && dy != 0)
        {
            //If neighbors do exist and are forced (left and right are impassable)
            if(getNode(nextX - dx, nextY) != null && getNode(nextX - dx, nextY + dy) != null)
                if(!getNode(nextX - dx, nextY).isPassable() && getNode(nextX - dx, nextY + dy).isPassable())
                    return nextNode;
            
            //If neighbors do exist and are forced (top and bottom impassable)
            if(getNode(nextX, nextY - dy) != null && getNode(nextX + dx, nextY - dy) != null)
                if(!getNode(nextX, nextY - dy).isPassable() && getNode(nextX + dx, nextY - dy).isPassable())
                    return nextNode;
            
            if(jump(nextNode, dx, 0) != null || jump(nextNode, 0, dy) != null)//Special Diagonal Case
                return nextNode; 
        }
        else //We are going horizontal or vertical
        {
            if(dx != 0) //Horizontal Case
            {
                if(isPassable(nextX + dx, nextY) && !isPassable(nextX, nextY + 1))
                    if(isPassable(nextX + dx, nextY + 1))
                        return nextNode;
                
                if(isPassable(nextX + dx, nextY) && !isPassable(nextX, nextY - 1))
                    if(isPassable(nextX + dx, nextY - 1))
                        return nextNode;
            }
            else        //Vertical Case
            {
                if(isPassable(nextX, nextY + dy) && !isPassable(nextX + 1, nextY))
                    if(isPassable(nextX + 1, nextY + dy))
                        return nextNode;
                
                if(isPassable(nextX, nextY + dy) && !isPassable(nextX - 1, nextY))
                    if(isPassable(nextX - 1, nextY + dy))
                        return nextNode;
            }
        }
        return jump(nextNode, dx, dy); //No forced neighbors so we are continuing down the path
    }//end jump
    
    /**
     * Goes through each parent of each subsequent node and adds them to a list 
     * starting with the provided node
     * @param theNode
     * @return The list of nodes that make up the path
     */
    private List<IntMatrix1D> backTrace(GridNode p_theNode)
    {
        List<IntMatrix1D> thePath = new ArrayList<IntMatrix1D>();
        GridNode parent = p_theNode;

        while(parent != null)
        {   
        	grid=null;
        	int row= parent.getX();
            int column= parent.getY();
            grid.setQuick(row,column);
            thePath.add(grid);
            parent = (GridNode)parent.getParent();
            
        }
        return thePath;
    }//end backTrace
    
    /**
     * Check if the neighbor of the given node is valid
     * @param node The node to check
     * @param neighbor The neighbor of the node to check
     * @return True if neighbor is valid, false otherwise
     */
    private boolean isValidNeighbor(GridNode node, GridNode neighbor)
    {
        return neighbor != null && neighbor.isPassable() && !closedList.contains(neighbor) && !neighbor.equals(node);
    }//end isValidNeighbor  
    
    /**
     * Gets a node at the specified location in the grid
     * @param x
     * @param y
     * @return The node at the x,y coordinates
     */
    public GridNode getNode(int x, int y)
    {
        if(x >= this.myNodes.length || x < 0 || y >= this.myNodes[0].length || y < 0)
            return null;
        return this.myNodes[x][y];
    }//end getNode
    
    /**
     * Checks if a node is passable at the specified location in the grid
     * @param x
     * @param y
     * @return True if passable, false otherwise
     */
    public boolean isPassable(int x, int y)
    {
        if(x >= this.myNodes.length || x < 0 || y >= this.myNodes[0].length || y < 0)
            return false;
        return this.myNodes[x][y].isPassable();
    }//end isPassable


    @Override
    public final double estimatedtime( final ObjectMatrix2D p_objects, final List<IntMatrix1D> p_route, final double p_speed )
    {
        return 0;
    }

}
