!main.


+!walk
    <-
        move/forward();
        !walk
.

-!walk
    <-
        viewpoint/random( 150 );
        !walk
.


+!main
    <-
        viewpoint/random( 15 );
        !!walk
.
