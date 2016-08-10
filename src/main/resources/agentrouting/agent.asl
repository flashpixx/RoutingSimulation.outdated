// --- individual behaviours -----------------------------------------------------------------------------------------------------------------------------------

// nearby belief to define the radius around the goal position to trigger the near-by plan
preferences/near-by(10).

// -------------------------------------------------------------------------------------------------------------------------------------------------------------



// initial-goal
!main.

// initial plan (triggered by the initial-goal) - calculates the initial route route
+!main
    <-
    route/set/start( 140, 140 );
    T = route/estimatedtime();
    generic/print("estimated time of the current route [", T , "]");
    !movement/walk/forward
.



// --- movement plans ------------------------------------------------------------------------------------------------------------------------------------------

// walk straight forward into the direction of the goal-position
+!movement/walk/forward
    <-
        generic/print( "walk forward in cycle [", Cycle, "]" );
        move/forward();
        !movement/walk/forward
.


// walk straight forward fails than go left
-!movement/walk/forward
    <-
        generic/print( "walk forward fails in cycle [", Cycle, "]" );
        !movement/walk/left
.

// walk left - direction 90 degree to the goal position
+!movement/walk/left
    <-
        generic/print( "walk left in cycle [", Cycle, "]" );
        move/left();
        !movement/walk/forward
.

// walk left fails than go right
-!movement/walk/left
    <-
        generic/print( "walk left fails in cycle [", Cycle, "]" );
        !movement/walk/right
.

// walk right - direction 90 degree to the goal position
+!movement/walk/right
    <-
        generic/print( "walk right in cycle [", Cycle, "]" );
        move/right();
        !movement/walk/forward
.

// walk right fails than sleep and hope everything will be
// fine later, wakeup plan will be trigger after sleeping
-!movement/walk/right
    <-
        T = math/statistic/randomsimple();
        T = T*10 + 1;
        T = math/min(T,5);
        generic/print( "walk right fails in cycle [", Cycle, "] wait [", T,"] cycles" );
        generic/sleep(N)
.

// if the agent is not walking because speed is
// low the agent increment the current speed
+!movement/standstill
    <-
        generic/print( "standstill - increment speed with 1 in cycle [", Cycle, "]" );
        speed/increment( 1 );
        !movement/walk/forward
.

// near-by belief change
+preferences/near-by(X)
    <-
        generic/print( "near-by preference belief modified to [", X ,"] in cycle [", Cycle, "]" )
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------



// --- other calls ---------------------------------------------------------------------------------------------------------------------------------------------

// is called if the pokemon agent increment the level
+!level-up
    <-
        generic/print( "level-up in cycle [", Cycle, "]" )
.

// is called iif || current position - goal-position || <= near-by
// the exact position of the goal will be skipped, so the agent
// is walking to the next position
+!position/near-by(D)
    <-
        generic/print( "near-by - set speed to 1", D, " in cycle [", Cycle, "]" );
        speed/set(1);
        route/next
.


// is called if the agent achieves the exact goal-position,
// than the agent will sleep 5 cycles
+!position/achieve-exact(P)
     <-
        generic/print( "position achieved [", P, "] in cycle [", Cycle, "] - sleep for 5 cycles" );
        route/next();
        generic/sleep(5)
.


// is called if the agent walks beyonds the goal-position, than
// the speed is set to 1 and we try go back
+!position/beyond(P)
    <-
        generic/print( "position beyond [", P, "] - set speed to 1 in cycle [", Cycle, "]" );
        speed/set(1)
.


// if the agent is wake-uped the speed is set to 1 and the agent starts walking
// to the next goal-position
+!wakeup
    <-
        generic/print("wakeup - set speed to 1 in cycle [", Cycle, "]");
        speed/set(1);
        !movement/walk/forward
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------
