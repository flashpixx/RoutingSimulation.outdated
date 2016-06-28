package agentrouting.simulation.algorithm.routing;

import agentrouting.simulation.IElement;
import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tint.impl.DenseIntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import cern.jet.math.tint.IntFunctions;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * JPS+ algorithm
 */
public final class CJPSPlus implements IRouting
{
        
    @Override
    public final IRouting initialize( final ObjectMatrix2D p_objects )
    {
        return this;
    }
       
    @Override
    //can just change the return type to Immutable pair (one possibility)
    public final List<IntMatrix1D> route( final ObjectMatrix2D p_objects, final IElement<?> p_element, final IntMatrix1D p_target )
    {
    	final Set<CJumpPoint> l_openlist = Collections.synchronizedSet(new HashSet<CJumpPoint>());
    	
    	final ArrayList<ImmutablePair<Integer, Integer>> l_closedlist=new ArrayList<>();
    	
		final List<IntMatrix1D> l_finalpath = new ArrayList<>();
		
    	l_openlist.add(new CJumpPoint(new ImmutablePair<>(p_element.position().getQuick(0),p_element.position().getQuick(1)),null));
    	
    	while(!l_openlist.isEmpty())
    	{   
    		CJumpPoint l_currentnode= l_openlist.iterator().next();//take the first element from set
    		l_openlist.remove(l_currentnode);
    		
    		ImmutablePair<Integer, Integer> l_target=new ImmutablePair<>(p_target.getQuick(0),p_target.getQuick(1));
    		//if the current node is the end node
    		if(l_currentnode.coordinate().equals(l_target))
    		{   
    			l_finalpath.add(p_target);
    	        CJumpPoint l_parent = l_currentnode.parent();
    	        while(l_parent!= null)
    	        {
    	        	//l_finalpath.add(l_parent.coordinate());(for immutable pair)
    	        	//for return type IntMatrix1D (another possibility)
    	        	l_finalpath.add((IntMatrix1D) p_objects.getQuick( l_parent.coordinate().getLeft(),l_parent.coordinate().getRight()));
    	            l_parent = (CJumpPoint)l_parent.parent();
    	        }
    	        Collections.reverse(l_finalpath); 
    	        return l_finalpath;
    		}
    		 
    		identifySuccessors(p_objects,l_currentnode,l_target,l_closedlist,l_openlist);  //Find the successors to current node (add them to the open list)
             
            l_closedlist.add(l_currentnode.coordinate());    //Add the l_currentnode to the closed list (as to not open it again)
                 
    	}	
    	return Collections.<IntMatrix1D>emptyList();
    }
        
    @Override
    public final double estimatedtime( final ObjectMatrix2D p_objects, final List<IntMatrix1D> p_route, final double p_speed )
    {
        return 0;
    }
        
    /**
     * individual jump point successors are identified and add to the open list
     * @param p_objects Snapshot of the environment 
     * @param p_currentnode the current node to search for
     * @param p_target the goal node
     * @param p_closedlist the list of coordinate that already explored 
     * @param p_openlist the set of CJumpPoint that will be explored
     */
    public final void identifySuccessors(final ObjectMatrix2D p_objects,CJumpPoint p_currentnode,ImmutablePair<Integer, Integer> p_target,ArrayList<ImmutablePair<Integer, Integer>> p_closedlist,Set<CJumpPoint> p_openlist)
    {
   
    	IntStream.range( -1,2 )//work like for loop
    	.parallel()
        .forEach( i ->
        {
        	IntStream.range( -1,2 )
        	//.parallel()
        	.forEach(j->
        	{ 
        		if((i != 0 && j != 0 )&&!isNotValidNeighbour(p_objects,p_currentnode.coordinate().getLeft()+i, p_currentnode.coordinate().getRight()+j,p_closedlist)&&!isOccupied(p_objects,p_currentnode.coordinate().getLeft()+i,p_currentnode.coordinate().getRight()+j))	
        		{
        			   ImmutablePair<Integer, Integer> l_nextjumpnode = jumppointSearch(p_currentnode.coordinate(),p_target, i, j,p_objects);
                       if(l_nextjumpnode != null && (!p_closedlist.contains(l_nextjumpnode)))
                       {
                          
                        	   CJumpPoint l_jumpnode=new CJumpPoint(l_nextjumpnode,p_currentnode);
                               calculateScore(l_jumpnode,p_target);//Calculate its score to sort it in the open list later
                        	   
                               //checking that the jump point is already exists in open list or not, if yes then check their fscore to make decision 
                        	   boolean l_checkscore= p_openlist.parallelStream()
                     				  .filter(s->s.coordinate().equals(l_nextjumpnode))
                     				  .anyMatch(s->s.fscore()<l_jumpnode.fscore());
                        	   
                        	   if(!l_checkscore)                      	   
                                  p_openlist.add(l_jumpnode); // Add it to the open list for continuing the path                                  
                       }    		  	
        	     } 
            });//end of inner foreach
         });//end of outer foreach

    }//end of function
    
    /**
     * In order to identify individual jump point successors heading in a given direction (run recursively until find a jump point or failure)
     * @param p_currentnode the current node to search for
     * @param p_target the goal node
     * @param p_row horizontal direction 
     * @param p_column vertical direction
     * @param p_objects Snapshot of the environment
     * @return l_nextnode next jump point
     */
    public final ImmutablePair<Integer, Integer> jumppointSearch(ImmutablePair<Integer, Integer> p_currentnode,ImmutablePair<Integer, Integer> p_target,final int p_row,final int p_column,ObjectMatrix2D p_objects)
    {   
    	//The next nodes details
        int l_nextrow = p_currentnode.getLeft() + p_row;
        int l_nextcolumn = p_currentnode.getRight() + p_column;
        ImmutablePair<Integer, Integer> l_nextnode = new ImmutablePair<>(l_nextrow,l_nextcolumn);
        
        // If the l_nextnode is outside the grid or occupied then return null
        if(isOccupied(p_objects,l_nextrow,l_nextcolumn)|| isNotValidCoordinate(p_objects,l_nextrow,l_nextcolumn)) return null;
        
        //If the l_nextnode is the target node then return it
        if(p_target.equals(l_nextnode)) return l_nextnode;
        
        //If we are going in a diagonal direction check for forced neighbors
        if(p_row != 0 && p_column != 0)
        {
            //If neighbors do exist and are forced (top or bottom grid coordinate of l_nextnode is occupied)
            if(!isNotValidCoordinate(p_objects,l_nextrow-p_row,l_nextcolumn) && !isNotValidCoordinate(p_objects,l_nextrow-p_row,l_nextcolumn+p_column) && isOccupied(p_objects,l_nextrow-p_row,l_nextcolumn) && !isOccupied(p_objects,l_nextrow-p_row,l_nextcolumn+p_column))
                 return l_nextnode;
            
            //If neighbors do exist and are forced (left or right grid coordinate of l_nextnode is occupied)
            if(!isNotValidCoordinate(p_objects,l_nextrow,l_nextcolumn-p_column)&& !isNotValidCoordinate(p_objects,l_nextrow+p_row,l_nextcolumn-p_column) && isOccupied(p_objects,l_nextrow,l_nextcolumn-p_column)&& !isOccupied(p_objects,l_nextrow,l_nextcolumn-p_column))
                 return l_nextnode;
            
            //before each diagonal step the algorithm must first fail to detect any straight jump points 
            if(jumppointSearch(l_nextnode,p_target,p_row, 0,p_objects) != null || jumppointSearch(l_nextnode,p_target, 0,p_column,p_objects) != null)
            	 return l_nextnode; 
        }
        else
        {	
            //Horizontal Case  
        	if(p_row != 0 && !isNotValidCoordinate(p_objects,l_nextrow+p_row,l_nextcolumn)&& !isOccupied(p_objects,l_nextrow+p_row,l_nextcolumn))
        	{	
        		//If neighbors do exist and are forced (right side grid coordinate of l_nextnode is occupied) 
        	    if(!isNotValidCoordinate(p_objects,l_nextrow,l_nextcolumn+1) && isOccupied(p_objects,l_nextrow,l_nextcolumn+1) && !isOccupied(p_objects,l_nextrow+p_row,l_nextcolumn+1))                    
            	    return l_nextnode;       		  
        		  
        		//If neighbors do exist and are forced (left side grid coordinate of l_nextnode is occupied)
        		if(!isNotValidCoordinate(p_objects,l_nextrow,l_nextcolumn-1) && isOccupied(p_objects,l_nextrow,l_nextcolumn-1) && !isOccupied(p_objects,l_nextrow+p_row,l_nextcolumn-1))
                    return l_nextnode;
        	   
            }//end of Horizontal Case
            
            //Vertical Case 
        	if(p_column !=0&&!isNotValidCoordinate(p_objects,l_nextrow,l_nextcolumn+p_column) && !isOccupied(p_objects,l_nextrow,l_nextcolumn+p_column))
        	{	
        		//If neighbors do exist and are forced (top grid coordinate of l_nextnode is occupied)
        		if(!isNotValidCoordinate(p_objects,l_nextrow+1,l_nextcolumn) && isOccupied(p_objects,l_nextrow+1,l_nextcolumn) && !isOccupied(p_objects,l_nextrow+1,l_nextcolumn+p_column))
                    return l_nextnode;
        		   
        		//If neighbors do exist and are forced (bottom grid coordinate of l_nextnode is occupied)
        		if(!isNotValidCoordinate(p_objects,l_nextrow-1,l_nextcolumn) && isOccupied(p_objects,l_nextrow-1,l_nextcolumn)&& !isOccupied(p_objects,l_nextrow-1,l_nextcolumn+p_column))
                    return l_nextnode;
        	   
            }//end of vertical case
        }//end of else
        
        return jumppointSearch(l_nextnode,p_target, p_row, p_column,p_objects);
    	
    }
    
    /**
     * To check any point in the grid environment is free or occupied
     * @param p_objects Snapshot of the environment 
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     */
    public final boolean isOccupied(ObjectMatrix2D p_objects,final int p_row,final int p_column)       
    {   
    	final IElement<?> l_object = (IElement<?>) p_objects.getQuick( p_row, p_column );
    	return (l_object != null);       
    }
    
    /**
     * To check any point is in the grid environment or out of environment
     * @param p_objects Snapshot of the environment 
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     */
    public final boolean isNotValidCoordinate(ObjectMatrix2D p_objects,final int p_row,final int p_column)       
    {   
    	return (p_column<0 || p_column>=p_objects.columns() || p_row<0 || p_row>=p_objects.rows());       
    }
    
    /**
     * Finds the non valid successors of any grid
     * @param p_objects Snapshot of the environment 
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     * @param p_closedlist the list of coordinate that already explored 
     */
    public final boolean isNotValidNeighbour(ObjectMatrix2D p_objects,final int p_row,final int p_column,ArrayList<ImmutablePair<Integer, Integer>> p_closedlist)       
    {   
    	return (p_closedlist.contains(new ImmutablePair<>( p_row, p_column ))||p_column<0 || p_column>=p_objects.columns() || p_row<0 || p_row>=p_objects.rows());       
    }
    
    /**
     * Calculates the given node's score using the Manhattan distance formula
     * @param p_jumpnode The node to calculate
     * @param p_target the target node
     * @param p_row horizontal direction
     * @param p_column vertical direction
     */
    private void calculateScore(CJumpPoint p_jumpnode,ImmutablePair<Integer, Integer> p_target)
    {
        int l_hscore = Math.abs(p_target.getLeft() - p_jumpnode.coordinate().getLeft()) * 10 + Math.abs(p_target.getRight() - p_jumpnode.coordinate().getRight()) * 10;
        
        p_jumpnode.sethscore(l_hscore);
        
        int l_row = Math.abs(p_jumpnode.parent().coordinate().getLeft() - p_jumpnode.coordinate().getLeft());
        int l_column =Math.abs(p_jumpnode.parent().coordinate().getRight() - p_jumpnode.coordinate().getRight());
        		
        int l_gscore=(l_row == 0 && l_column == 0)? Math.abs(14 * l_row):Math.abs(10 * Math.max(l_row, l_column));
                    
        p_jumpnode.setgscore(p_jumpnode.parent().gscore() +l_gscore );   
    }
    
    
    /**
     * jump-point with a static class
     * "static" means that the class can exists without the CJPSPlus object
     * "final" no inheritance can be create
     */
    private static final class CJumpPoint
    {
        /**
         * for avoid zero-jump-points we create exactly one
         */
        public static final CJumpPoint ZERO = new CJumpPoint();

        /**
         * jump-point g-score value
         * @todo some description, what a g-score value is
         */
        private int m_gscore;

        /**
         * jump-point h-score value
         * @todo some description, what a h-score value is
         */
        private int m_hscore;
        
        /**
         * parent node of current JumpPoint
         */
        private CJumpPoint m_parent;
        
        private final ImmutablePair<Integer, Integer> m_coordinate;                

        /**
         * ctor - with default values
         */
        private CJumpPoint()
        {
            this( null,null);
        }

        /**
         * ctor
         *
         * @param p_coodinate postion value
         * @param p_gscore gscore value
         * @param p_hscore hscore value 
         * @param p_parent parent of the current jump point 
         */
        public CJumpPoint( final ImmutablePair<Integer, Integer> p_coordinate,final CJumpPoint p_parent)
        {
        	m_coordinate=p_coordinate;
            //m_gscore = p_gscore;
            //m_hscore = p_hscore;
            m_parent= p_parent;
        }
        
        /**
         * getter for coordinate
         *
         * @return coordinate
         */
        public final ImmutablePair<Integer, Integer> coordinate()
        {
            return m_coordinate;
        }

        /**
         * getter for g-score
         *
         * @return g-score
         */
        public final int gscore()
        {
            return m_gscore;
        }
        
        /**
         * getter for f_score
         *
         * @return f-score
         */
        public final int fscore()
        {
            return m_gscore+m_hscore;
        }
        
        /**
         * getter for parent
         *
         * @return parent
         */
        public final CJumpPoint parent()
        {
            return m_parent;
        }
        
        /**
         * setter for g_score
         *
         * @return CJumpPoint
         */
        public final CJumpPoint setgscore( final int p_gscore )
        {
            m_gscore = p_gscore;
            return this;
        }
        
        /**
         * setter for h_score
         *
         * @return CJumpPoint
         */
        public final CJumpPoint sethscore( final int p_hscore )
        {
        	m_hscore = p_hscore;
            return this;
        }
        
        
        @Override
        public int hashCode()
        {
        	return m_hscore+m_gscore;
        }               
    
    }//end of CJumpPoint    
    
}
