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
     * @param p_distance distance
     * @return new position
     */
    public IntMatrix1D position( final IntMatrix1D p_position, final int p_distance )
    {
        final IntMatrix1D l_return = new DenseIntMatrix1D( p_position.toArray() );

        switch ( this )
        {
            case FORWARD:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + p_distance );
                break;

            case FORWARDRIGHT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + p_distance );
                l_return.setQuick( 1, l_return.getQuick( 1 ) + p_distance );
                break;

            case RIGHT:
                l_return.setQuick( 1, l_return.getQuick( 1 ) + p_distance );
                break;

            case BACKWARDRIGHT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - p_distance );
                l_return.setQuick( 1, l_return.getQuick( 1 ) + p_distance );
                break;

            case BACKWARD:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - p_distance );
                break;

            case BACKWARDLEFT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) - p_distance );
                l_return.setQuick( 1, l_return.getQuick( 1 ) - p_distance );
                break;

            case LEFT:
                l_return.setQuick( 1, l_return.getQuick( 1 ) - p_distance );
                break;

            case FORWARDLEFT:
                l_return.setQuick( 0, l_return.getQuick( 0 ) + p_distance );
                l_return.setQuick( 1, l_return.getQuick( 1 ) - p_distance );
                break;

            default:
        }

        return l_return;
    }
}
