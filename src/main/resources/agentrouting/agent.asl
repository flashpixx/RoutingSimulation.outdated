!main.


+!walk
    <-
        generic/print( "walk forward" );
        move/forward();
        !walk
.


-!walk
    <-
        generic/print( "walk fails" );
        viewpoint/random( 1 );
        !walk
.


+!viewpointreach(P)
     <-
        generic/print( "position reached", P );
        generic/sleep(25)
.


+!wakeup
    <-
        generic/print("wakeup");
        viewpoint/random( 10 );
        !walk
.


+!main
    <-
        viewpoint/random( 10 );
        !!walk
.
