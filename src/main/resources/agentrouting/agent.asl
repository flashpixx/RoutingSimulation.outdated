// the agent starts walking
// start-position and goal-position
// are initialize on the underlying structures
!movement/walk/forward.


// --- movement plans ------------------------------------------------------------------------------------------------------------------------------------------

// walk straight forward into the direction
// of the goal-position
+!movement/walk/forward
    <-
        generic/print( "walk forward" );
        move/forward();
        !movement/walk/forward
.


// walk straight forwad fails e.g. the is an obstacle,
// than calculate a new goal position within the next 10
// cells around the current position
-!movement/walk/forward
    <-
        generic/print( "walk forward fails" );
        goal/random( 10 );
        !movement/walk/forward
.


// if the agent is not walking e.g. speed is low
// so the agent increment the current speed
+!movement/standstill
    <-
        generic/print( "standstill" );
        speed/increment(5);
        !!movement/walk/forward
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------



// --- other calls ---------------------------------------------------------------------------------------------------------------------------------------------

+!viewpoint/reach(P)
     <-
        generic/print( "position reached", P );
        generic/sleep(5)
.


+!wakeup
    <-
        generic/print("wakeup");
        goal/random( 10 );
        speed/set(1);
        !!movement/walk/forward
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------
