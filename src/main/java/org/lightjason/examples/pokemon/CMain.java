/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason AgentSpeak(L)                                  #
 * # Copyright (c) 2015-16, Philipp Kraus (philipp@lightjason.org)                      #
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

package org.lightjason.examples.pokemon;


import org.lightjason.examples.pokemon.ui.CScreen;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * main desktop application
 *
 * @note add JVM parameters: -Djava.util.concurrent.ForkJoinPool.common.parallelism=8 -XX:+UseParallelGC -Xmx8g
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
                            CCommon.getResourceURL( CCommon.PACKAGEPATH + i ),
                            Paths.get( l_cli.getOptionValue( "generate", "." ), i ).normalize().toFile()
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
            : CCommon.PACKAGEPATH + "configuration.yaml"
        );


        // force-exit must be disabled for avoid error exiting
        final LwjglApplicationConfiguration l_config = new LwjglApplicationConfiguration();

        l_config.forceExit = false;
        l_config.width = CConfiguration.INSTANCE.windowweight();
        l_config.height = CConfiguration.INSTANCE.windowheight();

        // open window
        LOGGER.info( MessageFormat.format( "open window with size [{0}x{1}]", l_config.width, l_config.height ) );
        final CScreen l_screen = new CScreen(
            Stream.concat(
                CConfiguration.INSTANCE.staticelements().parallelStream(),
                CConfiguration.INSTANCE.agents().parallelStream()
            ).collect( Collectors.toList() ),
            CConfiguration.INSTANCE.environment(),
            CConfiguration.INSTANCE.screenshot()
        );
        new LwjglApplication( l_screen, l_config );
        CMain.execute( l_screen );
    }

    /**
     * execute simulation
     *
     * @param p_screen screen reference
     */
    private static void execute( final CScreen p_screen )
    {
        IntStream
            .range( 0, CConfiguration.INSTANCE.simulationsteps() )
            .mapToObj( i ->
            {
                // update screen take screenshot and run object execution
                p_screen.iteration( i );
                Stream.concat(
                    Stream.of(
                        //CConfiguration.INSTANCE.evaluation(),
                        CConfiguration.INSTANCE.environment()
                    ),
                    Stream.concat(
                        CConfiguration.INSTANCE.staticelements().parallelStream(),
                        CConfiguration.INSTANCE.agents().parallelStream()
                    )
                )
                    .parallel()
                    .forEach( j ->
                    {
                        try
                        {
                            j.call();
                        }
                        catch ( final Exception l_exception )
                        {
                            LOGGER.warning( l_exception.toString() );
                            if ( CConfiguration.INSTANCE.stackstrace() )
                                l_exception.printStackTrace( System.err );
                        }
                    } );

                // thread sleep for slowing down
                if ( CConfiguration.INSTANCE.threadsleeptime() > 0 )
                    try
                    {
                        Thread.sleep( CConfiguration.INSTANCE.threadsleeptime() );
                    }
                    catch ( final InterruptedException l_exception )
                    {
                        LOGGER.warning( l_exception.toString() );
                    }

                // checks that the simulation is closed
                return p_screen.isDisposed();
            } )
            .filter( i -> i )
            .findFirst();
    }

}
