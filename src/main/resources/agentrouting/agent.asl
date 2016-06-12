// the agent starts walking start-position and goal-position
// are initialize on the underlying structures (random on default)
!movement/walk/forward.


// --- movement plans ------------------------------------------------------------------------------------------------------------------------------------------

// walk straight forward into the direction of the goal-position
+!movement/walk/forward
    <-
        generic/print( "walk forward" );
        move/forward();
        !movement/walk/forward
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
// belief preference/nearby(V)
+!goal/nearby(D)
    <-
        generic/print( "nearby", D );
        speed/set(1)
.


// is called if the agent achieves the goal position, than the agent
// will sleep 5 cycles
+!goal/achieve(P)
     <-
        generic/print( "position achieved", P );
        generic/sleep(5)
.


// if the agent is wake-uped a new goal position is taken by random or fixed
// around the current position and than starts walking with the initial speed
+!wakeup
    <-
        generic/print("wakeup");
        //goal/random( 50 );
        goal/set(10,10);
        speed/set(1);
        !!movement/walk/forward
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------
