package agentrouting.simulation;

import cern.colt.matrix.tint.IntMatrix1D;
import cern.colt.matrix.tint.impl.DenseIntMatrix1D;

import java.util.Arrays;


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
     * @param p_viewpoint view point
     * @param p_position current position
     * @return new position
     */
    @SuppressWarnings( "unchecked" )
    public IntMatrix1D position( final IntMatrix1D p_viewpoint, final IntMatrix1D p_position )
    {
        final IntMatrix1D l_return = new DenseIntMatrix1D( 2 );

        // calculate the direction and length
        l_return.setQuick( 0, p_viewpoint.getQuick( 0 ) - p_position.getQuick( 0 ) );
        l_return.setQuick( 1, p_viewpoint.getQuick( 1 ) - p_position.getQuick( 1 ) );

        final int l_distance = (int) Math.round( Math.sqrt( Arrays.stream( p_position.toArray() ).asDoubleStream().map( i -> i * i ).sum() ) );

        switch ( this )
        {
            case FORWARD:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + l_distance );
                break;

            case FORWARDRIGHT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + l_distance );
                l_return.setQuick( 1, l_return.getQuick( 1 ) + l_distance );
                break;

            case RIGHT:
                l_return.setQuick( 1, l_return.getQuick( 1 ) + l_distance );
                break;

            case BACKWARDRIGHT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - l_distance );
                l_return.setQuick( 1, l_return.getQuick( 1 ) + l_distance );
                break;

            case BACKWARD:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - l_distance );
                break;

            case BACKWARDLEFT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - l_distance );
                l_return.setQuick( 1, l_return.getQuick( 1 ) - l_distance );
                break;

            case LEFT:
                l_return.setQuick( 1, l_return.getQuick( 1 ) - l_distance );
                break;

            case FORWARDLEFT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + l_distance );
                l_return.setQuick( 1, l_return.getQuick( 1 ) - l_distance );
                break;

            default:
        }

        return l_return;
    }
}
