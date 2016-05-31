!main.


+!main
    <-
        generic/print( "agent main" );

        foovoid();

        Z = fooreturn();
        Z = Z + 5;
        generic/print("return value", Z);

        fooparameter( Z )
.
