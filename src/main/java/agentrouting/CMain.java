package agentrouting;


import agentrouting.ui.CScreen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * main desktop application
 */
public final class CMain
{
    /**
     * logger
     */
    private static final Logger LOGGER = Logger.getLogger( CMain.class.getName() );

    /**
     * ctor
     */
    private CMain()
    {}

    /**
     * initialization
     *
     * @param p_args CLI arguments
     * @throws FileNotFoundException on configuration file reading
     */
    public static void main( final String[] p_args ) throws FileNotFoundException
    {
        // read configuration on default from the resources
        CConfiguration.INSTANCE.load(
                p_args.length == 0
                ? CMain.class.getResourceAsStream( "/configuration.yaml" )
                : new FileInputStream( new File( p_args[0] ) )
        );
        LOGGER.info( MessageFormat.format( "read configuration file from [{0}]", p_args.length == 0 ? "-resources-" : p_args[0] ) );


        // force-exit must be disabled for avoid error exiting
        final LwjglApplicationConfiguration l_config = new LwjglApplicationConfiguration();

        l_config.forceExit = false;
        l_config.width = CConfiguration.INSTANCE.getWindowWeight();
        l_config.height = CConfiguration.INSTANCE.getWindowHeight();


        // open window
        LOGGER.info( MessageFormat.format( "open window with size [{0}x{1}]", l_config.width, l_config.height ) );
        final CScreen l_screen = new CScreen(
                CConfiguration.INSTANCE.getObjects(),
                CConfiguration.INSTANCE.getEnvironment(),
                CConfiguration.INSTANCE.getScreenshot()
        );
        new LwjglApplication( l_screen, l_config );


        // run simulation
        LOGGER.info( MessageFormat.format( "start simulation with [{0}] steps", CConfiguration.INSTANCE.getSimulationSteps() ) );
        IntStream.range( 0, CConfiguration.INSTANCE.getSimulationSteps() )
                 .mapToObj( i -> {
                     LOGGER.info( MessageFormat.format( "run simulation step [{0}]", i ) );

                     // update screen take screenshot and run object execution
                     l_screen.iteration( i );
                     Stream.concat(
                             Stream.of( CConfiguration.INSTANCE.getEnvironment() ),
                             CConfiguration.INSTANCE.getObjects().parallelStream()
                     )
                           .parallel()
                           .forEach( j -> j.execute( i ) );

                     // checks that the simulation is closed
                     return l_screen.isDisposed();
                 } ).filter( i -> i ).findFirst();
    }

}
