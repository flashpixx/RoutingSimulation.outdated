!main.


+!walk
    <-
        move/forward();
        !walk
.

-!walk
    <-
        viewpoint/random( 10 );
        !walk
.


+!main
    <-
        viewpoint/random( 20 );
        !!walk
.
