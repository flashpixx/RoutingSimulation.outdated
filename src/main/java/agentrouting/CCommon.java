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

import agentrouting.simulation.IElement;
import agentrouting.simulation.agent.IAgent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * common class with main methods
 */
public final class CCommon
{
    /**
     * logger
     */
    private static final Logger LOGGER = Logger.getLogger( IAgent.class.getName() );

    /**
     * ctor
     */
    private CCommon()
    {
    }


    /**
     * creates an int-pair-stream of an element
     *
     * @param p_element element but position must be return a vector with 4 elements
     * @return int-pair-stream
     */
    public static Stream<Pair<Integer, Integer>> inttupelstream( final IElement p_element )
    {
        return CCommon.inttupelstream(
            (int) p_element.position().get( 0 ), (int) ( p_element.position().get( 0 ) + p_element.position().get( 2 ) ),
            (int) p_element.position().get( 1 ), (int) ( p_element.position().get( 1 ) + p_element.position().get( 3 ) )
        );
    }


    /**
     * creates an int-pair-stream
     *
     * @param p_firststart start value of first component
     * @param p_firstend end (inclusive) of first component
     * @param p_secondstart start value of second component
     * @param p_secondend end (inclusive) of second component
     * @return int-pair-sream
     */
    public static Stream<Pair<Integer, Integer>> inttupelstream( final int p_firststart, final int p_firstend, final int p_secondstart, final int p_secondend )
    {
        final Supplier<IntStream> l_inner = () -> IntStream.rangeClosed( p_secondstart, p_secondend );
        return IntStream.rangeClosed( p_firststart, p_firstend ).boxed().flatMap( i -> l_inner.get().mapToObj( j -> new ImmutablePair<>( i, j ) ) );
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file
     * @return URL of file or null
     * @throws MalformedURLException on incorrect URL
     * @throws URISyntaxException on incorrect URI syntax
     */
    public static URL getResourceURL( final String p_file ) throws MalformedURLException, URISyntaxException
    {
        return CCommon.getResourceURL( new File( p_file ) );
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file relative to the CMain
     * @return URL of file or null
     * @throws MalformedURLException on incorrect URL
     * @throws URISyntaxException on incorrect URI syntax
     */
    public static URL getResourceURL( final File p_file ) throws MalformedURLException, URISyntaxException
    {
        try
        {
            if ( p_file.exists() )
                return p_file.toURI().normalize().toURL();
            return CCommon.class.getClassLoader().getResource( p_file.toString().replace( File.separator, "/" ) ).toURI().normalize().toURL();
        }
        catch ( final Exception l_exception )
        {
            LOGGER.warning( MessageFormat.format( "source [{0}] not found", p_file ) );
            throw l_exception;
        }
    }

}
