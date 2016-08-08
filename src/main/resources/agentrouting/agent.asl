// --- individual behaviours -----------------------------------------------------------------------------------------------------------------------------------

// nearby belief to define the radius around the goal position to trigger the nearby plan
preferences/near-by(1).

// -------------------------------------------------------------------------------------------------------------------------------------------------------------



// initial-goal
!main.

// initial plan (triggered by the initial-goal) - calculates the route
+!main
    <-
    route/set( 140, 140 );
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

// walk left 90 degree to the goal position
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

// walk right 90 degree to the goal position
+!movement/walk/right
    <-
        generic/print( "walk right in cycle [", Cycle, "]" );
        move/right();
        !movement/walk/forward
.

// walk right fails than sleep and hope everything will be fine later,
// wakeup plan will be trigger after sleeping
-!movement/walk/right
    <-
        generic/print( "walk right fails in cycle [", Cycle, "]" );
        N = math/statistic/randomsimple();
        N = N*10 + 1;
        N = math/min(N);
        generic/sleep(N)
.




// if the agent is not walking because speed is low so
// the agent increment the current speed
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

// is called if the distance to the goal position less
// equal than the belief preference/near-by(V) than we
// will skip the current goal position and the agent walks
// to the next goal-position
+!goal/near-by(D)
    <-
        generic/print( "near-by - set speed to 1", D, " in cycle [", Cycle, "]" );
        speed/set(1);
        route/next
.


// is called if the agent achieves the goal position,
// than the agent will sleep 5 cycles
+!goal/achieve-position(P)
     <-
        generic/print( "position achieved [", P, "] in cycle [", Cycle, "] - sleep for 5 cycles" );
        route/next();
        generic/sleep(5)
.


// is called if the agent walks beyonds the goal-position, than
// the speed is set to 1 and we try go back
+!goal/beyond(P)
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
