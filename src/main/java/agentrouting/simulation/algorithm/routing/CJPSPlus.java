package agentrouting.simulation.algorithm.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;

import agentrouting.simulation.IElement;
import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;


/**
 * JPS+ algorithm
 * @todo think about visibility public and static methods
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
    public final List<IntMatrix1D> route( final ObjectMatrix2D p_objects, final IntMatrix1D p_currentposition, final IntMatrix1D p_targetposition )
    {
        final Set<CJumpPoint> l_openlist = Collections.synchronizedSet( new HashSet<CJumpPoint>() );

        final ArrayList<ImmutablePair<Integer, Integer>> l_closedlist = new ArrayList<>();

        final List<IntMatrix1D> l_finalpath = new ArrayList<>();

        l_openlist.add( new CJumpPoint( new ImmutablePair<>( p_currentposition.getQuick( 0 ), p_currentposition.getQuick( 1 ) ), null ) );

        while ( !l_openlist.isEmpty() )
        {
            //take the first element from set
            final CJumpPoint l_currentnode = l_openlist.iterator().next();
            l_openlist.remove( l_currentnode );

            final ImmutablePair<Integer, Integer> l_target = new ImmutablePair<>( p_targetposition.getQuick( 0 ), p_targetposition.getQuick( 1 ) );
            //if the current node is the end node
            if ( l_currentnode.coordinate().equals( l_target ) )
            {
                l_finalpath.add( p_targetposition );
                CJumpPoint l_parent = l_currentnode.parent();
                while ( l_parent != null )
                {
                    //l_finalpath.add(l_parent.coordinate());(for immutable pair)
                    //for return type IntMatrix1D (another possibility)
                    l_finalpath.add( (IntMatrix1D)p_objects.getQuick( l_parent.coordinate().getLeft(), l_parent.coordinate().getRight() ) );
                    l_parent = l_parent.parent();
                }
                Collections.reverse( l_finalpath );
                return l_finalpath;
            }

            //Find the successors to current node (add them to the open list)
            this.successors( p_objects, l_currentnode, l_target, l_closedlist, l_openlist );

            //Add the l_currentnode to the closed list (as to not open it again)
            l_closedlist.add( l_currentnode.coordinate() );

        }
        return Collections.<IntMatrix1D>emptyList();
    }

    @Override
    public final double estimatedtime( final ObjectMatrix2D p_objects, final List<IntMatrix1D> p_route, final double p_speed )
    {
        return 0;
    }

    /**
     * individual jump point successors are identified
     * @param p_objects Snapshot of the environment
     * @param p_curnode the current node to search for successors
     * @param p_target the goal node
     * @param p_closedlist the list of coordinate that already explored
     * @param p_openlist the set of CJumpPoint that will be explored
     * @todo remove if-condition -> with filter
     */
    public final void successors( final ObjectMatrix2D p_objects, final CJumpPoint p_curnode, final ImmutablePair<Integer, Integer> p_target,
                                  final ArrayList<ImmutablePair<Integer, Integer>> p_closedlist, final Set<CJumpPoint> p_openlist )
    {

        IntStream.range( -1, 2 )
            .parallel()
            .forEach( i ->
            {
                IntStream.range( -1, 2 )
                    .forEach( j->
                    {
                        if ( ( i != 0 && j != 0 ) && !this.isNotNeighbour( p_objects, p_curnode.coordinate().getLeft() + i, p_curnode.coordinate().getRight() + j,
                             p_closedlist ) && !this.isOccupied( p_objects, p_curnode.coordinate().getLeft() + i, p_curnode.coordinate().getRight() + j ) )
                        {
                            final ImmutablePair<Integer, Integer> l_nextjumpnode = this.jump( p_curnode.coordinate(), p_target, i, j, p_objects );
                            this.addsuccessors( l_nextjumpnode, p_closedlist, p_openlist, p_curnode, p_target );
                        }
                    } );
            } );

    }

    /**
     * Validated successors are added to open list
     * @param p_nextjumpnode successors that need to be validated
     * @param p_closedlist the list of coordinate that already explored
     * @param p_openlist the set of CJumpPoint that will be explored
     * @param p_curnode the current node to search for successors
     * @param p_target the goal node
     */
    public final void addsuccessors( final ImmutablePair<Integer, Integer> p_nextjumpnode, final ArrayList<ImmutablePair<Integer, Integer>> p_closedlist,
                                     final Set<CJumpPoint> p_openlist, final CJumpPoint p_curnode, final ImmutablePair<Integer, Integer> p_target )
    {
        if ( p_nextjumpnode != null && ( !p_closedlist.contains( p_nextjumpnode ) ) )
        {
            final CJumpPoint l_jumpnode = new CJumpPoint( p_nextjumpnode, p_curnode );
            this.calculateScore( l_jumpnode, p_target );

            //checking that the jump point is already exists in open list or not, if yes then check their fscore to make decision
            final boolean l_checkscore = p_openlist.parallelStream()
                                                   .filter( s -> s.coordinate().equals( p_nextjumpnode ) )
                                                   .anyMatch( s -> s.fscore() < l_jumpnode.fscore() );

            if ( !l_checkscore )
                p_openlist.add( l_jumpnode );
        }
    }

    /**
     * In order to identify individual jump point successors heading in a given direction (run recursively until find a jump point or failure)
     * @param p_curnode the current node to search for
     * @param p_target the goal node
     * @param p_row horizontal direction
     * @param p_col vertical direction
     * @param p_objects Snapshot of the environment
     * @return l_nextnode next jump point
     */
    public final ImmutablePair<Integer, Integer> jump( final ImmutablePair<Integer, Integer> p_curnode, final ImmutablePair<Integer, Integer> p_target,
                                                       final int p_row, final int p_col, final ObjectMatrix2D p_objects )
    {
        //The next nodes details
        final int l_nextrow = p_curnode.getLeft() + p_row;
        final int l_nextcol = p_curnode.getRight() + p_col;
        final ImmutablePair<Integer, Integer> l_nextnode = new ImmutablePair<>( l_nextrow, l_nextcol );

        // If the l_nextnode is outside the grid or occupied then return null
        if ( this.isOccupied( p_objects, l_nextrow, l_nextcol ) || this.isNotCoordinate( p_objects, l_nextrow, l_nextcol ) ) return null;

        //If the l_nextnode is the target node then return it
        if ( p_target.equals( l_nextnode ) ) return l_nextnode;

        //If we are going in a diagonal direction check for forced neighbors
        if ( p_row != 0 && p_col != 0 )
        {
            this.diagjump( l_nextrow, l_nextcol, l_nextnode, p_target, p_row, p_col, p_objects );
        }
        else
        {
            if ( this.horizontal( l_nextrow, l_nextcol, p_row, p_objects, 1 ) || this.horizontal( l_nextrow, l_nextcol, p_row, p_objects, -1 )
                || this.vertical( l_nextrow, l_nextcol, p_col, p_objects, 1 ) || this.vertical( l_nextrow, l_nextcol, p_col, p_objects, -1 ) )
                return l_nextnode;
        }

        return this.jump( l_nextnode, p_target, p_row, p_col, p_objects );
    }

    /**
     * In order to identify diagonal jump point
     * @param p_nextrow row of the next node to search for
     * @param p_nextcolumn column of the next node to search for
     * @param p_target the goal node
     * @param p_row horizontal direction
     * @param p_col vertical direction
     * @param p_objects Snapshot of the environment
     * @return p_nextnode diagonal jump point
     * @todo https://dst.lbl.gov/ACSSoftware/colt/api/cern/colt/matrix/DoubleMatrix2D.html#viewSelection(int[], int[])
     */
    public final ImmutablePair<Integer, Integer> diagjump( final int p_nextrow, final int p_nextcolumn, final ImmutablePair<Integer, Integer> p_nextnode,
                                       final ImmutablePair<Integer, Integer> p_target, final int p_row, final int p_col, final ObjectMatrix2D p_objects )
    {
        //If neighbors do exist and are forced (top or bottom grid coordinate of l_nextnode is occupied)
        if ( !this.isNotCoordinate( p_objects, p_nextrow - p_row, p_nextcolumn ) && !this.isNotCoordinate( p_objects, p_nextrow - p_row, p_nextcolumn + p_col )
            && this.isOccupied( p_objects, p_nextrow - p_row, p_nextcolumn ) && !this.isOccupied( p_objects, p_nextrow - p_row, p_nextcolumn + p_col ) )
            return p_nextnode;

        //If neighbors do exist and are forced (left or right grid coordinate of l_nextnode is occupied)
        if ( !this.isNotCoordinate( p_objects, p_nextrow, p_nextcolumn - p_col ) && !this.isNotCoordinate( p_objects, p_nextrow + p_row, p_nextcolumn - p_col )
             && this.isOccupied( p_objects, p_nextrow, p_nextcolumn - p_col ) && !this.isOccupied( p_objects, p_nextrow, p_nextcolumn - p_col ) )
            return p_nextnode;

        //before each diagonal step the algorithm must first fail to detect any straight jump points
        if ( this.jump( p_nextnode, p_target, p_row, 0, p_objects ) != null || this.jump( p_nextnode, p_target, 0, p_col, p_objects ) != null )
            return p_nextnode;

        return null;
    }

    /**
     * Helper function to identify horizontal jump point
     * @param p_nextrow row of the next node to search for
     * @param p_nextcol column of the next node to search for
     * @param p_row horizontal direction
     * @param p_objects Snapshot of the environment
     * @return p_value to check the top and down grids
     */
    public final boolean horizontal( final int p_nextrow, final int p_nextcol, final int p_row, final ObjectMatrix2D p_objects, final int p_value )
    {
        return p_row != 0 && !this.isNotCoordinate( p_objects, p_nextrow + p_row, p_nextcol ) && !this.isOccupied( p_objects, p_nextrow + p_row, p_nextcol )
                  && !this.isNotCoordinate( p_objects, p_nextrow, p_nextcol + p_value ) && this.isOccupied( p_objects, p_nextrow, p_nextcol + p_value )
                     && !this.isOccupied( p_objects, p_nextrow + p_row, p_nextcol + p_value );
    }

    /**
     * Helper function to identify vertical jump point
     * @param p_nextrow row of the next node to search for
     * @param p_nextcol column of the next node to search for
     * @param p_col vertical direction
     * @param p_objects Snapshot of the environment
     * @return p_value to check the left and right side grids
     */
    public final boolean vertical( final int p_nextrow, final int p_nextcol, final int p_col, final ObjectMatrix2D p_objects, final int p_value )
    {
        return p_col != 0 && !this.isNotCoordinate( p_objects, p_nextrow, p_nextcol + p_col ) && !this.isOccupied( p_objects, p_nextrow, p_nextcol + p_col )
                && !this.isNotCoordinate( p_objects, p_nextrow + p_value, p_nextcol ) && this.isOccupied( p_objects, p_nextrow + p_value, p_nextcol )
                   && !this.isOccupied( p_objects, p_nextrow + p_value, p_nextcol + p_col );
    }

    /**
     * To check any point in the grid environment is free or occupied
     * @param p_objects Snapshot of the environment
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     * @return return true or false
     */
    public final boolean isOccupied( final ObjectMatrix2D p_objects, final int p_row, final int p_column )
    {
        final IElement<?> l_object = (IElement<?>) p_objects.getQuick( p_row, p_column );
        return l_object != null;
    }

    /**
     * To check any point is in the grid environment or out of environment
     * @param p_objects Snapshot of the environment
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     * @return return true or false
     */
    public final boolean isNotCoordinate( final ObjectMatrix2D p_objects, final int p_row, final int p_column )
    {
        return p_column < 0 || p_column >= p_objects.columns() || p_row < 0 || p_row >= p_objects.rows();
    }

    /**
     * Finds the non valid successors of any grid
     * @param p_objects Snapshot of the environment
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     * @param p_closedlist the list of coordinate that already explored
     */
    public final boolean isNotNeighbour( final ObjectMatrix2D p_objects, final int p_row, final int p_column, final ArrayList<ImmutablePair<Integer, Integer>> p_closedlist )
    {
        return p_closedlist.contains( new ImmutablePair<>( p_row, p_column ) ) || p_column < 0 || p_column >= p_objects.columns() || p_row < 0 || p_row >= p_objects.rows();
    }

    /**
     * Calculates the given node's score using the Manhattan distance formula
     * @param p_jumpnode The node to calculate
     * @param p_target the target node
     */
    private void calculateScore( final CJumpPoint p_jumpnode, final ImmutablePair<Integer, Integer> p_target )
    {
        final int l_hscore = Math.abs( p_target.getLeft() - p_jumpnode.coordinate().getLeft() ) * 10
                             + Math.abs( p_target.getRight() - p_jumpnode.coordinate().getRight() ) * 10;

        p_jumpnode.sethscore( l_hscore );

        final int l_row = Math.abs( p_jumpnode.parent().coordinate().getLeft() - p_jumpnode.coordinate().getLeft() );
        final int l_column = Math.abs( p_jumpnode.parent().coordinate().getRight() - p_jumpnode.coordinate().getRight() );

        final int l_gscore = ( l_row == 0 && l_column == 0 ) ? Math.abs( 14 * l_row ) : Math.abs( 10 * Math.max( l_row, l_column ) );

        p_jumpnode.setgscore( p_jumpnode.parent().gscore() + l_gscore );
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
            this( null, null );
        }

        /**
         * ctor
         *
         * @param p_coodinate postion value
         * @param p_gscore gscore value
         * @param p_hscore hscore value
         * @param p_parent parent of the current jump point
         */
        public CJumpPoint( final ImmutablePair<Integer, Integer> p_coordinate, final CJumpPoint p_parent )
        {
            m_coordinate = p_coordinate;
            m_parent = p_parent;
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
            return m_gscore + m_hscore;
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
            return m_hscore + m_gscore;
        }
    }
}
