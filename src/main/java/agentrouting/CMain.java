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

import java.io.IOException;
import java.net.URISyntaxException;
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
    private final static Logger LOGGER = Logger.getLogger( CMain.class.getName() );

    /**
     * main
     *
     * @param p_args CLI arguments
     */
    public static void main( final String[] p_args ) throws IOException, URISyntaxException
    {
        // read configuration on default from the resources
        CConfiguration.INSTANCE.load(
            p_args.length == 0
            ? "agentrouting/configuration.yaml"
            : p_args[0]
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
            CConfiguration.INSTANCE.getScreenshot(),
            CConfiguration.INSTANCE.getStatusVisible()
        );
        new LwjglApplication( l_screen, l_config );


        // run simulation
        LOGGER.info( MessageFormat.format( "start simulation with [{0}] steps", CConfiguration.INSTANCE.getSimulationSteps() ) );
        IntStream.range( 0, CConfiguration.INSTANCE.getSimulationSteps() )
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

                                // Thread sleep for slowing down
                                if ( CConfiguration.INSTANCE.getThreadSleepTime() < 1 )
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
                            } ).filter( i -> i ).findFirst();
    }

}
