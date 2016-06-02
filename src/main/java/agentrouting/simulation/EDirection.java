/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason Gridworld                                      #
 * # Copyright (c) 2015-16, Philipp Kraus (philipp.kraus@tu-clausthal.de)               #
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

package agentrouting.simulation;

import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tint.impl.DenseIntMatrix1D;


/**
 * direction enum
 */
public enum EDirection
{
    FORWARD,
    FORWARDRIGHT,
    RIGHT,
    BACKWARDRIGHT,
    BACKWARD,
    BACKWARDLEFT,
    LEFT,
    FORWARDLEFT;


    /**
     * calculates the new position
     *
     * @param p_position current position
     * @param p_viewpoint view point
     * @param p_length number of cells / step size
     * @return new position
     */
    @SuppressWarnings( "unchecked" )
    public IntMatrix1D position( final IntMatrix1D p_position, final IntMatrix1D p_viewpoint, final int p_length )
    {
        // https://de.wikipedia.org/wiki/Normalenform

        // normalvector to the viewpoint:  (p_viewpoint - p_position) -> normal (length 1)
        // normal vector -> rotation on direction (left = 0°, left fwd = 45°, fwd = 90° ...)
        // scale normalvector with length -> new position

        final IntMatrix1D l_return = new DenseIntMatrix1D( 2 );

        // calculate the direction and length
        l_return.setQuick( 0, p_viewpoint.getQuick( 0 ) - p_position.getQuick( 0 ) );
        l_return.setQuick( 1, p_viewpoint.getQuick( 1 ) - p_position.getQuick( 1 ) );

        //final int l_distance = (int) Math.round( Math.sqrt( Arrays.stream( p_position.toArray() ).asDoubleStream().map( i -> i * i ).sum() ) );

        switch ( this )
        {
            case FORWARD:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + p_length );
                break;

            case FORWARDRIGHT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + p_length );
                l_return.setQuick( 1, l_return.getQuick( 1 ) + p_length );
                break;

            case RIGHT:
                l_return.setQuick( 1, l_return.getQuick( 1 ) + p_length );
                break;

            case BACKWARDRIGHT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - p_length );
                l_return.setQuick( 1, l_return.getQuick( 1 ) + p_length );
                break;

            case BACKWARD:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - p_length );
                break;

            case BACKWARDLEFT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - p_length );
                l_return.setQuick( 1, l_return.getQuick( 1 ) - p_length );
                break;

            case LEFT:
                l_return.setQuick( 1, l_return.getQuick( 1 ) - p_length );
                break;

            case FORWARDLEFT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + p_length );
                l_return.setQuick( 1, l_return.getQuick( 1 ) - p_length );
                break;

            default:
        }

        return l_return;
    }
}
