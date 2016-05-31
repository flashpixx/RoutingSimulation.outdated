!main.


+!walk
    <-
        move/forward();
        !walk
.


+!main
    <-
        viewpoint/random( 10 );
        !!walk
.
