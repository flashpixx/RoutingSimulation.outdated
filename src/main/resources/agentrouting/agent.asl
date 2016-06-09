!start.


+!start
    <-
        viewpoint/random( 100 );
        !!movement/walk
.


+!movement/walk
    <-
        generic/print( "walk forward" );
        move/forward();
        !movement/walk
.


-!movement/walk
    <-
        generic/print( "walk fails" );
        viewpoint/random( 10 );
        !movement/walk
.

+!movement/standstill
    <-
        generic/print( "standstill" );
        speed/increment(5);
        !!movement/walk
.



+!viewpoint/reach(P)
     <-
        generic/print( "position reached", P );
        generic/sleep(5)
.


+!wakeup
    <-
        generic/print("wakeup");
        viewpoint/random( 10 );
        speed/set(1);
        !!movement/walk
.
