/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason Gridworld                                      #
 * # Copyright (c) 2015-16, Philipp Kraus (philipp.kraus@tu-clausthal.de)               #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package agentrouting;


import agentrouting.ui.CScreen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
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
     * @throws IOException on configuration file reading
     * @throws URISyntaxException on URI sytax definition
     */
    public static void main( final String[] p_args ) throws IOException, URISyntaxException
    {
        // --- define CLI options ------------------------------------------------------------------------------------------------------------------------------

        final Options l_clioptions = new Options();
        l_clioptions.addOption( "help", false, "shows this information" );
        l_clioptions.addOption( "generate", true, "generates an example configuration within the current directory" );
        l_clioptions.addOption( "configuration", true, "defines the simulation configuration" );

        final CommandLine l_cli;
        try
        {
            l_cli = new DefaultParser().parse( l_clioptions, p_args );
        }
        catch ( final Exception l_exception )
        {
            System.err.println( "command-line arguments parsing error" );
            System.exit( -1 );
            return;
        }



        // --- process CLI arguments and initialize configuration ----------------------------------------------------------------------------------------------

        if ( l_cli.hasOption( "help" ) )
        {
            new HelpFormatter().printHelp( new java.io.File( CMain.class.getProtectionDomain().getCodeSource().getLocation().getPath() ).getName(), l_clioptions );
            System.exit( 0 );
            return;
        }


        if ( l_cli.hasOption( "generate" ) )
        {
            System.exit(
                Stream.of(
                    "agent.asl",
                    "configuration.yaml"
                )
                .parallel()
                .map( i ->
                {
                    try
                    {
                        FileUtils.copyURLToFile(
                            CCommon.getResourceURL( "agentrouting/" + i ),
                            Paths.get( l_cli.getOptionValue( "generate" ), i ).normalize().toFile()
                        );
                        return true;
                    }
                    catch ( final IOException | URISyntaxException l_exception )
                    {
                        System.err.println( l_exception );
                        return false;
                    }
                } ).allMatch( i -> i )
                ? 0
                : -1
            );
            return;
        }



        // --- read configuration and initialize simulation ui -------------------------------------------------------------------------------------------------

        CConfiguration.INSTANCE.load(
            l_cli.hasOption( "configuration" )
            ? l_cli.getOptionValue( "configuration" )
            : "agentrouting/configuration.yaml"
        );


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
            CConfiguration.INSTANCE.getScreenshot(),
            CConfiguration.INSTANCE.getStatusVisible()
        );
        new LwjglApplication( l_screen, l_config );



        // --- run simulation ----------------------------------------------------------------------------------------------------------------------------------

        LOGGER.info( MessageFormat.format( "start simulation with [{0}] steps", CConfiguration.INSTANCE.getSimulationSteps() ) );
        IntStream
                .range( 0, CConfiguration.INSTANCE.getSimulationSteps() )
                .mapToObj( i ->
                {
                    // update screen take screenshot and run object execution
                    l_screen.iteration( i );
                    Stream.concat(
                        Stream.of( CConfiguration.INSTANCE.getEnvironment() ),
                        CConfiguration.INSTANCE.getObjects().parallelStream()
                    )
                        .parallel()
                        .forEach( j -> j.execute( i ) );

                    // thread sleep for slowing down
                    if ( CConfiguration.INSTANCE.getThreadSleepTime() > 0 )
                        try
                        {
                            Thread.sleep( CConfiguration.INSTANCE.getThreadSleepTime() );
                        }
                        catch ( final InterruptedException l_exception )
                        {
                            LOGGER.warning( l_exception.toString() );
                        }

                    // checks that the simulation is closed
                    return l_screen.isDisposed();
                } )
                .filter( i -> i )
                .findFirst();
    }

}
