/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason AgentSpeak(L)                                  #
 * # Copyright (c) 2015-16, Philipp Kraus (philipp@lightjason.org)                      #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.examples.pokemon.simulation.algorithm.routing;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.math.Functions;



/**
 * JPS+ algorithm
 */
final class CJPSPlus implements IRouting
{

    private List<DoubleMatrix1D> m_staticjumppoints;

    @Override
    public final IRouting initialize( final ObjectMatrix2D p_objects )
    {
        m_staticjumppoints = new ArrayList<>();

        IntStream.range( 0, p_objects.rows() )
            .forEach( i->
            {
                IntStream.range( 0, p_objects.columns() )
                    .filter( j -> this.isOccupied( p_objects, i, j ) )
                    .forEach( j ->
                    {
                        this.createstaticjump( m_staticjumppoints, i, j, p_objects );
                    } );
            } );
        return this;
    }

    @Override
    public final List<DoubleMatrix1D> route( final ObjectMatrix2D p_objects, final DoubleMatrix1D p_currentposition, final DoubleMatrix1D p_targetposition )
    {
        final TreeSet<CJumpPoint> l_openlist = new TreeSet<CJumpPoint>( new CCompareJumpPoint() );

        final ArrayList<DoubleMatrix1D> l_closedlist = new ArrayList<>();

        final List<DoubleMatrix1D> l_finalpath = new ArrayList<>();

        l_openlist.add( new CJumpPoint( p_currentposition, null ) );

        final ArrayList<DoubleMatrix1D> l_staticjumppoints = new ArrayList<>( m_staticjumppoints );

        final List<DoubleMatrix1D> l_requiredstaticjumppoints = l_staticjumppoints.parallelStream()
            .filter( i -> this.staticjumppointfilter( p_currentposition.getQuick( 0 ), p_targetposition.getQuick( 0 ), p_currentposition.getQuick( 1 ),
                      p_targetposition.getQuick( 1 ), i.getQuick( 0 ), i.getQuick( 1 ) ) )
            .collect( Collectors.toList() );

        while ( !l_openlist.isEmpty() )
        {
            final CJumpPoint l_currentnode = l_openlist.pollFirst();

            if ( l_currentnode.coordinate().equals( p_targetposition ) )
            {
                l_finalpath.add( p_targetposition );
                CJumpPoint l_parent = l_currentnode.parent();

                while ( !l_parent.coordinate().equals( p_currentposition ) )
                {
                    l_finalpath.add( l_parent.coordinate() );
                    l_parent = l_parent.parent();
                }
                Collections.reverse( l_finalpath );
                return l_finalpath;
            }
            this.successors( p_objects, l_currentnode, p_targetposition, l_closedlist, l_openlist, l_requiredstaticjumppoints );
            l_closedlist.add( l_currentnode.coordinate() );
        }
        return Collections.<DoubleMatrix1D>emptyList();
    }

    @Override
    public final double estimatedtime( final Stream<DoubleMatrix1D> p_route, final double p_speed )
    {
        return StreamUtils.windowed( p_route, 2, 1 )
                   .mapToDouble( i -> Math.sqrt(
                                          Algebra.DEFAULT.norm2(
                                              new DenseDoubleMatrix1D( i.get( 1 ).toArray() )
                                                  .assign( i.get( 0 ), Functions.minus )
                                              )
                                          ) / p_speed
                   )
                   .sum();
    }

      /**
     * to create static jump points
     * @param p_staticjumppoints list of the static jump points
     * @param p_row row number of the grid cell
     * @param p_column column number of the grid cell
     * @param p_objects Snapshot of the environment
     */
    private void createstaticjump( final List<DoubleMatrix1D> p_staticjumppoints, final int p_row, final int p_column, final ObjectMatrix2D p_objects )
    {

        Stream.of( new DenseDoubleMatrix1D( new double[]{p_row + 1, p_column} ), new DenseDoubleMatrix1D( new double[]{p_row - 1, p_column} ),
                new DenseDoubleMatrix1D( new double[]{p_row, p_column + 1} ), new DenseDoubleMatrix1D( new double[]{p_row, p_column - 1} ) )

            .filter( s-> !this.isNotCoordinate( p_objects, s.getQuick( 0 ), s.getQuick( 1 ) ) && !this.isOccupied( p_objects, s.getQuick( 0 ), s.getQuick( 1 ) )
                     && !p_staticjumppoints.contains( s ) )
            .forEach( s-> p_staticjumppoints.add( s ) );
    }

    /**
     * filter to collect the static jump points in current range
     * @param p_currentx row number of the current cell
     * @param p_targetx row number of the target cell
     * @param p_currenty column number of the current cell
     * @param p_targety column number of the target cell
     * @param p_staticjumpx row number of the static jump point
     * @param p_staticjumpy column number of the static jump point
     */
    private final boolean staticjumppointfilter( final double p_currentx, final double p_targetx, final double p_currenty,
                                                 final double p_targety, final double p_staticjumpx, final double p_staticjumpy )
    {
        return ( p_staticjumpx >= Math.min( p_currentx, p_targetx ) - 1 && p_staticjumpx <= Math.max( p_currentx, p_targetx ) + 1 )
                && ( p_staticjumpy >= Math.min( p_currenty, p_targety ) - 1 && p_staticjumpy <= Math.max( p_currenty, p_targety ) + 1 );
    }

    /**
     * individual jump point successors are identified
     * @param p_objects Snapshot of the environment
     * @param p_curnode the current node to search for successors
     * @param p_target the goal node
     * @param p_closedlist the list of coordinate that already explored
     * @param p_openlist the set of CJumpPoint that will be explored
     */
    private void successors( final ObjectMatrix2D p_objects, final CJumpPoint p_curnode, final DoubleMatrix1D p_target, final ArrayList<DoubleMatrix1D> p_closedlist,
            final TreeSet<CJumpPoint> p_openlist, final List<DoubleMatrix1D> p_requiredstaticjumppoints )
    {
        IntStream.rangeClosed( -1, 1 )
            .forEach( i ->
            {
                IntStream.rangeClosed( -1, 1 )
                    .filter( j -> ( i != 0 || j != 0 )
                                 && !this.isNotNeighbour( p_objects, p_curnode.coordinate().getQuick( 0 ) + i, p_curnode.coordinate().getQuick( 1 ) + j, p_closedlist )
                                 && !this.isOccupied( p_objects, p_curnode.coordinate().getQuick( 0 ) + i, p_curnode.coordinate().getQuick( 1 ) + j ) )
                    .forEach( j ->
                    {
                        final DoubleMatrix1D l_nextjumpnode = this.jump( p_curnode.coordinate(), p_target, i, j, p_objects, p_requiredstaticjumppoints, p_closedlist );
                        this.addsuccessors( l_nextjumpnode, p_closedlist, p_openlist, p_curnode, p_target );
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
     * @todo refactor all todos within this method
     */
    private void addsuccessors( final DoubleMatrix1D p_nextjumpnode, final ArrayList<DoubleMatrix1D> p_closedlist,
                                     final Set<CJumpPoint> p_openlist, final CJumpPoint p_curnode, final DoubleMatrix1D p_target )
    {
        if ( !( p_nextjumpnode != null && !p_curnode.coordinate().equals( p_nextjumpnode ) && ( !p_closedlist.contains( p_nextjumpnode ) ) ) )
            return;

        final CJumpPoint l_jumpnode = new CJumpPoint( p_nextjumpnode, p_curnode );
        this.calculateScore( l_jumpnode, p_target );

        p_openlist.removeIf( s-> s.coordinate().equals( p_nextjumpnode ) && s.fscore() > l_jumpnode.fscore() );

        //checking that the jump point is already exists in open list or not, if yes then check their fscore to make decision
        final boolean l_checkscore = p_openlist.parallelStream()
                                     .filter( s -> s.coordinate().equals( p_nextjumpnode ) )
                                     .anyMatch( s -> s.fscore() < l_jumpnode.fscore() );
        if ( !l_checkscore )
            p_openlist.add( l_jumpnode );

    }

    /**
     * In order to identify individual jump point successors heading in a given direction (run recursively until find a jump point or failure)
     * @param p_curnode the current node to search for
     * @param p_target the goal node
     * @param p_row to increase or decrease row by adding p_row
     * @param p_col to increase or decrease column by adding p_col
     * @param p_objects Snapshot of the environment
     * @return l_nextnode next jump point
     */
        private DoubleMatrix1D jump( final DoubleMatrix1D p_curnode, final DoubleMatrix1D p_target, final double p_row, final double p_col,
                             final ObjectMatrix2D p_objects, final List<DoubleMatrix1D> p_staticjp, final ArrayList<DoubleMatrix1D> p_closedlist )
    {
        //The next nodes details
        final double l_nextrow = p_curnode.getQuick( 0 ) + p_row;
        final double l_nextcol = p_curnode.getQuick( 1 ) + p_col;
        final DoubleMatrix1D l_nextnode =  new DenseDoubleMatrix1D( new double[]{l_nextrow, l_nextcol} );

        // If the l_nextnode is outside the grid or occupied then return null
        if ( this.isOccupied( p_objects, l_nextrow, l_nextcol ) || this.isNotCoordinate( p_objects, l_nextrow, l_nextcol ) ) return null;

        //If the l_nextnode is the target node then return it
        if ( p_target.equals( l_nextnode ) ) return l_nextnode;

        //If we are going in a diagonal direction check for forced neighbors
        if ( p_row != 0 && p_col != 0 )
        {
            final DoubleMatrix1D l_node = this.diagjump( l_nextrow, l_nextcol, l_nextnode, p_curnode, p_target, p_row, p_col, p_objects, p_staticjp, p_closedlist );
            if ( l_node != null ) return l_node;
        }
        else
        {
            if ( this.horizontal( l_nextrow, l_nextcol, p_row, p_objects, 1 ) || this.horizontal( l_nextrow, l_nextcol, p_row, p_objects, -1 )
                  || this.vertical( l_nextrow, l_nextcol, p_col, p_objects, 1 ) || this.vertical( l_nextrow, l_nextcol, p_col, p_objects, -1 ) )
                return l_nextnode;

            final DoubleMatrix1D l_jumpnode = this.nondiagonalstaticjump( p_curnode, l_nextnode, p_staticjp, p_objects, p_closedlist, p_row, p_col );
            if ( l_jumpnode != null ) return l_jumpnode;
        }

        return this.jump( l_nextnode, p_target, p_row, p_col, p_objects, p_staticjp, p_closedlist );
    }

    /**
     * In order to identify non diagonal static jump points
     * @param p_parent the parent node of the current node
     * @param p_curnode the current node to search for
     * @param p_staticjp list of static jump points
     * @param p_objects Snapshot of the environment
     * @param p_closedlist the list of coordinate that already explored
     * @param p_row to increase or decrease row by adding p_row
     * @param p_col to increase or decrease column by adding p_col
     * @return next jump point
     */
    private DoubleMatrix1D nondiagonalstaticjump( final DoubleMatrix1D p_parent, final DoubleMatrix1D p_curnode, final List<DoubleMatrix1D> p_staticjp,
                                      final ObjectMatrix2D p_objects, final ArrayList<DoubleMatrix1D> p_closedlist, final double p_row, final double p_col )
    {
        final List<DoubleMatrix1D> l_jumplist = p_staticjp.stream()
            .filter( i-> ( this.jumppointcheck( p_closedlist, p_curnode, p_parent, i ) ) && ( ( p_row != 0 && this.nondiagjumppoint( p_objects, p_row, p_curnode.getQuick( 0 ),
              p_curnode.getQuick( 1 ), i.getQuick( 0 ), i.getQuick( 1 ), 0 ) ) || ( p_col != 0 && this.nondiagjumppoint( p_objects, p_col, p_curnode.getQuick( 1 ),
                p_curnode.getQuick( 0 ), i.getQuick( 1 ), i.getQuick( 0 ), 1 ) ) ) ).collect( Collectors.toList() );

        if ( !l_jumplist.isEmpty() )
            return ( p_parent.getQuick( 0 ) < p_curnode.getQuick( 0 ) || p_parent.getQuick( 1 ) < p_curnode.getQuick( 1 ) )
                    ? l_jumplist.get( l_jumplist.size() - 1 ) : l_jumplist.get( 0 );

        return null;

    }

    /**
     * In order to identify diagonal jump point
     * @param p_nextrow row of the next node to search for
     * @param p_nextcolumn column of the next node to search for
     * @param p_target the goal node
     * @param p_row to increase or decrease row by adding p_row
     * @param p_col to increase or decrease column by adding p_col
     * @param p_objects Snapshot of the environment
     * @return p_nextnode diagonal jump point
     */
    private DoubleMatrix1D diagjump( final double p_nextrow, final double p_nextcol, final DoubleMatrix1D p_nextnode, final DoubleMatrix1D p_curnode,
               final DoubleMatrix1D p_target, final double p_row, final double p_col, final ObjectMatrix2D p_objects, final List<DoubleMatrix1D> p_staticjp,
                final ArrayList<DoubleMatrix1D> p_closedlist )
    {
        if ( this.diagonal( p_nextrow, p_nextcol, -p_row, p_col, p_row, 0, p_objects ) || this.diagonal( p_nextrow, p_nextcol, p_row, -p_col, 0, p_col, p_objects ) )
            return p_nextnode;

        //before each diagonal step the algorithm must first fail to detect any straight jump points
        if ( this.jump( p_nextnode, p_target, p_row, 0, p_objects, p_staticjp, p_closedlist ) != null || this.jump( p_nextnode, p_target, 0, p_col, p_objects,
                p_staticjp, p_closedlist ) != null )
            return p_nextnode;

        final List<DoubleMatrix1D> l_jumplist = p_staticjp.stream()
                .filter( i-> ( Math.abs( i.getQuick( 0 ) - p_nextnode.getQuick( 0 ) ) == Math.abs( i.getQuick( 1 ) - p_nextnode.getQuick( 1 ) ) )
                && ( ( p_row == -1 && i.getQuick( 0 ) < p_nextnode.getQuick( 0 ) ) || ( p_row == 1 && i.getQuick( 0 ) > p_nextnode.getQuick( 0 ) ) )
                && !this.choosediagstaticjump( p_objects, i.getQuick( 0 ), p_nextnode.getQuick( 0 ), p_nextnode.getQuick( 1 ), i.getQuick( 1 ),
                p_closedlist, p_nextnode, p_curnode, i ) ).collect( Collectors.toList() );

        if ( !l_jumplist.isEmpty() )
            return ( p_curnode.getQuick( 0 ) > p_nextnode.getQuick( 0 ) && p_curnode.getQuick( 1 ) < p_nextnode.getQuick( 1 ) )
                    || ( p_curnode.getQuick( 0 ) < p_nextnode.getQuick( 0 ) && p_curnode.getQuick( 1 ) < p_nextnode.getQuick( 1 ) )
                    ? l_jumplist.get( l_jumplist.size() - 1 ) : l_jumplist.get( 0 );

        return null;
    }

    /**
     * Helper function to identify diagonal jump point
     * @param p_nextrow row of the next node to search for
     * @param p_nextcolumn column of the next node to search for
     * @param p_row to increase or decrease row by adding p_row
     * @param p_col to increase or decrease column by adding p_col
     * @param p_hzon top or bottom direction
     * @param p_ver left or right direction
     * @param p_objects Snapshot of the environment
     */
    private boolean diagonal( final double p_nextrow, final double p_nextcolumn, final double p_row, final double p_col, final double p_hzon, final double p_ver,
                                       final ObjectMatrix2D p_objects )
    {
        return !this.isNotCoordinate( p_objects, p_nextrow - p_hzon, p_nextcolumn - p_ver ) && !this.isNotCoordinate( p_objects, p_nextrow + p_row, p_nextcolumn + p_col )
            && this.isOccupied( p_objects, p_nextrow - p_hzon, p_nextcolumn - p_ver ) && !this.isOccupied( p_objects, p_nextrow + p_row, p_nextcolumn + p_col );
    }

    /**
     * Helper function to identify horizontal jump point
     * @param p_nextrow row of the next node to search for
     * @param p_nextcol column of the next node to search for
     * @param p_row to increase or decrease row by adding p_row
     * @param p_objects Snapshot of the environment
     * @param p_value direction
     */
    private boolean horizontal( final double p_nextrow, final double p_nextcol, final double p_row, final ObjectMatrix2D p_objects, final double p_value )
    {
        return p_row != 0 && !this.isNotCoordinate( p_objects, p_nextrow + p_row, p_nextcol ) && !this.isOccupied( p_objects, p_nextrow + p_row, p_nextcol )
                  && !this.isNotCoordinate( p_objects, p_nextrow, p_nextcol + p_value ) && this.isOccupied( p_objects, p_nextrow, p_nextcol + p_value )
                     && !this.isOccupied( p_objects, p_nextrow + p_row, p_nextcol + p_value );
    }

    /**
     * Helper function to identify vertical jump point
     * @param p_nextrow row of the next node to search for
     * @param p_nextcol column of the next node to search for
     * @param p_col to increase or decrease column by adding p_col
     * @param p_objects Snapshot of the environment
     * @param p_value direction
     */
    private boolean vertical( final double p_nextrow, final double p_nextcol, final double p_col, final ObjectMatrix2D p_objects, final double p_value )
    {
        return p_col != 0 && !this.isNotCoordinate( p_objects, p_nextrow, p_nextcol + p_col ) && !this.isOccupied( p_objects, p_nextrow, p_nextcol + p_col )
                && !this.isNotCoordinate( p_objects, p_nextrow + p_value, p_nextcol ) && this.isOccupied( p_objects, p_nextrow + p_value, p_nextcol )
                   && !this.isOccupied( p_objects, p_nextrow + p_value, p_nextcol + p_col );
    }

    /**
     * Helper function to identify static jump point
     * @param p_closedlist the list of coordinate that already explored
     * @param p_curnode the current node to search for
     * @param p_parent the parent node of the current node
     * @param p_staticjumppoint current static jump point
     */
    private boolean jumppointcheck( final ArrayList<DoubleMatrix1D> p_closedlist, final DoubleMatrix1D p_curnode, final DoubleMatrix1D p_parent,
                                     final DoubleMatrix1D p_staticjumppoint )
    {
        return !p_closedlist.contains( p_staticjumppoint ) && !p_parent.equals( p_staticjumppoint ) && !p_curnode.equals( p_staticjumppoint );
    }

    /**
     * Helper function to identify non diagonal static jump point
     * @param p_objects Snapshot of the environment
     * @param p_direction direction
     * @param p_current1 row of the current node
     * @param p_current2 column of the current node
     * @param p_jumppoint1 row of the current static jump point
     * @param p_jumppoint2 column of the current static jump point
     * @param p_differ to distinguish between directions ( horizontal or vertical )
     */
    private boolean nondiagjumppoint( final ObjectMatrix2D p_objects, final double p_direction, final double p_current1, final double p_current2,
                                                final double p_jumppoint1, final double p_jumppoint2, final int p_differ )
    {
        return ( p_jumppoint2 == p_current2 ) && ( ( p_direction == -1 && p_jumppoint1 < p_current1 ) || ( p_direction == 1
                && p_jumppoint1 > p_current1 ) ) && this.helpnondiagjumppoint( p_objects, p_current1, p_jumppoint1, p_jumppoint2, p_differ );
    }

    /**
     * Helper function for nondiagjumppoint() function
     * @param p_objects Snapshot of the environment
     * @param p_current1 row or column of the current node
     * @param p_jumppoint1 row or column of the current static jump point
     * @param p_jumppoint2 row or column of the current static jump point
     * @param p_differ to distinguish between directions
     */
    private boolean helpnondiagjumppoint( final ObjectMatrix2D p_objects, final double p_current1, final double p_jumppoint1, final double p_jumppoint2,
                                        final int p_differ )
    {
        return ( p_differ == 0 && !this.choosestaticjump( p_objects, -1, p_jumppoint2, p_current1, p_jumppoint1 ) )
                 || ( p_differ == 1 && !this.choosestaticjump( p_objects, p_jumppoint2, -1, p_current1, p_jumppoint1 ) );
    }


    /**
     * To check any point in the grid environment is free or occupied
     * @param p_objects Snapshot of the environment
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     */
    private boolean isOccupied( final ObjectMatrix2D p_objects, final double p_row, final double p_column )
    {
        return p_objects.getQuick( (int) p_row, (int) p_column ) != null;
    }

    /**
     * choose the required non diagonal static jump points
     * @param p_objects Snapshot of the environment
     * @param p_row to distinguish between horizontal and vertical static jump points
     * @param p_col to distinguish between horizontal and vertical static jump points
     * @param p_srtrange row or column of the current node
     * @param p_endrange row or column of the current static jump point
     */
    private boolean choosestaticjump( final ObjectMatrix2D p_objects, final double p_row, final double p_col, final double p_srtrange, final double p_endrange )
    {
        if ( p_row != -1 )
            return IntStream.range( Math.min( (int) p_srtrange, (int) p_endrange ) + 1, Math.max( (int) p_srtrange, (int) p_endrange ) )
                .anyMatch( i-> this.isOccupied( p_objects, p_row, i ) );
        if ( p_col != -1 )
            return IntStream.range( Math.min( (int) p_srtrange, (int) p_endrange ) + 1, Math.max( (int) p_srtrange, (int) p_endrange ) )
                .anyMatch( i-> this.isOccupied( p_objects, i, p_col ) );

        return false;
    }

    /**
     * choose the required diagonal static jump points
     * @param p_objects Snapshot of the environment
     * @param p_srtrowrange row of the current node
     * @param p_endrowrange column of the current node
     * @param p_srtcolrange row of the current static jump point
     * @param p_endcolrange column of the current static jump point
     * @param p_closedlist the list of coordinate that already explored
     * @param p_curnode the current node to search for
     * @param p_parent the parent node of the current node
     * @param p_staticjumppoint current static jump point
     */
    private boolean choosediagstaticjump( final ObjectMatrix2D p_objects, final double p_srtrowrange, final double p_endrowrange, final double p_strcolrange,
                                           final double p_endcolrange, final ArrayList<DoubleMatrix1D> p_closedlist, final DoubleMatrix1D p_curnode,
                                            final DoubleMatrix1D p_parent, final DoubleMatrix1D p_staticjumppoint )
    {
        if ( this.jumppointcheck( p_closedlist, p_curnode, p_parent, p_staticjumppoint ) )
        {
            double l_column = Math.max( (int) p_strcolrange, (int) p_endcolrange ) - 1;
            for ( int l_row = Math.min( (int) p_srtrowrange, (int) p_endrowrange ) + 1; l_row < Math.max( (int) p_srtrowrange, (int) p_endrowrange ); l_row++ )
            {
                if ( this.isOccupied( p_objects, l_row, l_column ) ) return true;
                l_column--;
            }
        }
        return false;
    }

    /**
     * To check any point is in the grid environment or out of environment
     * @param p_objects Snapshot of the environment
     * @param p_row the row number of the coordinate to check
     * @param p_column the column number of the coordinate to check
     * @return return true or false
     */
    private boolean isNotCoordinate( final ObjectMatrix2D p_objects, final double p_row, final double p_column )
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
    private boolean isNotNeighbour( final ObjectMatrix2D p_objects, final double p_row, final double p_column, final ArrayList<DoubleMatrix1D> p_closedlist )
    {
        return p_closedlist.contains(  new DenseDoubleMatrix1D( new double[]{p_row, p_column} ) ) || this.isNotCoordinate( p_objects, p_row, p_column );
    }

    /**
     * Calculates the given node's score using the Manhattan distance formula
     * @param p_jumpnode The node to calculate
     * @param p_target the target node
     */
    private void calculateScore( final CJumpPoint p_jumpnode, final DoubleMatrix1D p_target )
    {
        final double l_hscore = Math.abs( p_target.getQuick( 0 ) - p_jumpnode.coordinate().getQuick( 0 ) ) * 10
                             + Math.abs( p_target.getQuick( 1 ) - p_jumpnode.coordinate().getQuick( 1 ) ) * 10;

        p_jumpnode.hscore( l_hscore );

        final double l_row = Math.abs( p_jumpnode.parent().coordinate().getQuick( 0 ) - p_jumpnode.coordinate().getQuick( 0 ) );
        final double l_column = Math.abs( p_jumpnode.parent().coordinate().getQuick( 1 ) - p_jumpnode.coordinate().getQuick( 1 ) );

        final double l_gscore = ( l_row != 0 && l_column != 0 ) ? Math.abs( 14 * l_row ) : Math.abs( 10 * Math.max( l_row, l_column ) );

        p_jumpnode.gscore( p_jumpnode.parent().gscore() + l_gscore );
    }

    /**
     * class to compare two jump-points
     */
    private static final class CCompareJumpPoint implements Comparator<CJumpPoint>
    {
        @Override
        public final int compare( final CJumpPoint p_jumppoint1, final CJumpPoint p_jumppoint2 )
        {
            return ( p_jumppoint1.fscore() > p_jumppoint2.fscore() ) ? 1 : -1;
        }
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
         */
        private double m_gscore;

        /**
         * jump-point h-score value
         */
        private double m_hscore;

        /**
         * parent node of current JumpPoint
         */
        private CJumpPoint m_parent;
        /**
         * position
         */
        private final DoubleMatrix1D m_coordinate;

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
         * @param p_coordinate postion value
         * @param p_parent parent of the current jump point
         */
        public CJumpPoint( final DoubleMatrix1D p_coordinate, final CJumpPoint p_parent )
        {
            m_coordinate = p_coordinate;
            m_parent = p_parent;
        }

        /**
         * getter for coordinate
         *
         * @return coordinate
         */
        public final DoubleMatrix1D coordinate()
        {
            return m_coordinate;
        }

        /**
         * getter for g-score
         *
         * @return g-score
         */
        public final double gscore()
        {
            return m_gscore;
        }

        /**
         * getter for f_score
         *
         * @return f-score
         */
        public final double fscore()
        {
            return m_hscore + m_gscore;
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
        public final CJumpPoint gscore( final double p_gscore )
        {
            m_gscore = p_gscore;
            return this;
        }

        /**
         * setter for h_score
         *
         * @return CJumpPoint
         */
        public final CJumpPoint hscore( final double p_hscore )
        {
            m_hscore = p_hscore;
            return this;
        }

    }
}
