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

import agentrouting.simulation.agent.IAgent;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.logging.Logger;


/**
 * common class with main methods
 */
public final class CCommon
{
    /**
     * logger
     */
    protected final static Logger LOGGER = Logger.getLogger( IAgent.class.getName() );


    /**
     * ctor
     */
    private CCommon()
    {
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file
     * @return URL of file or null
     */
    public static URL getResourceURL( final String p_file ) throws MalformedURLException, URISyntaxException
    {
        return getResourceURL( new File( p_file ) );
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file relative to the CMain
     * @return URL of file or null
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
