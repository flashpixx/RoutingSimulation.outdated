// --- individual behaviours -----------------------------------------------------------------------------------------------------------------------------------

// nearby belief to define the radius around the goal position to trigger the nearby plan
preferences/near-by(5).

// belief for definining the view radius into forward-backward direction
preferences/viewforwardbackwardradius(3).

// belief for defining the view radius into left-right direction
preferences/viewleftrightradius(1).

// -------------------------------------------------------------------------------------------------------------------------------------------------------------



// the agent starts walking start-position and goal-position
// are initialize on the underlying structures (random on default)
!movement/walk/forward.



// --- movement plans ------------------------------------------------------------------------------------------------------------------------------------------

// walk straight forward into the direction of the goal-position
+!movement/walk/forward
    <-
        route/calculate(8,9);
        generic/print( "walk forward" );
        move/forward();
        !movement/walk/forward
.

// near-by belief change
+preferences/near-by(X)
    <-
        generic/print( "near-by preference belief modified to [", X ,"]" )
.

// walk straight forwad fails e.g. the is an obstacle, than calculate
// a new goal position within the next 10 cells around the current position
-!movement/walk/forward
    <-
        generic/print( "walk forward fails" );
        goal/random( 10 );
        !movement/walk/forward
.


// if the agent is not walking e.g. speed is low so the agent increment
// the current speed
+!movement/standstill
    <-
        generic/print( "standstill" );
        speed/increment(5);
        !!movement/walk/forward
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------



// --- other calls ---------------------------------------------------------------------------------------------------------------------------------------------

// is called if the distance to the goal position less equal than the
// belief preference/near-by(V)
+!goal/near-by(D)
    <-
        generic/print( "near-by", D );
        speed/set(1)
.


// is called if the agent achieves the goal position, than the agent
// will sleep 5 cycles
+!goal/achieve-position(P)
     <-
        generic/print( "position achieved", P );
        generic/sleep(5)
.


// if the agent is wake-uped a new goal position is taken by random or fixed
// around the current position and than starts walking with the initial speed
// and a random near-by definition
+!wakeup
    <-
        generic/print("wakeup");

        goal/random( 25 );
        //goal/set(10,10);

        N = math/statistic/randomsimple();
        N = N*10;
        +preferences/near-by(N);

        speed/set(1);

        !!movement/walk/forward
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------
