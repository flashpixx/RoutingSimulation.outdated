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
import org.lightjason.agentspeak.action.annotation.IActionAllow;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;


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


    /**
     * returns a void-method from a class
     *
     * @param p_class class
     * @param p_method methodname
     * @param p_parameter array with type-classes to define method parameter e.g. new Class[]{Integer.TYPE,
     * Integer.TYPE};
     * @return method
     *
    public static CMethod getClassMethod( final Class<?> p_class, final String p_method, final Class<?>[] p_parameter )
    throws IllegalArgumentException, IllegalAccessException
    {
        Method l_method = null;
        for ( Class<?> l_class = p_class; ( l_method == null ) && ( l_class != null ); l_class = l_class.getSuperclass() )
            try
            {
                l_method = l_class.getDeclaredMethod( p_method, p_parameter );
            }
            catch ( final Exception l_exception )
            {
            }

        if ( l_method == null )
            throw new IllegalArgumentException( CCommon.getResourceString( CReflection.class, "methodnotfound", p_method, p_class.getCanonicalName() ) );

        l_method.setAccessible( true );
        return new CMethod( l_method );
    }
*/

    /**
     * returns filtered methods of a class and the super classes
     *
     * @param p_class class
     * @param p_filter filtering object
     * @return map with method name
     */
    public static Stream<Method> getClassMethods( final Class<?> p_class, final boolean p_use )
    {
        /*
        Stream.concat(
            Stream.of( p_class ),
            Stream.of( p_class.getSuperclass() )
        )
        .flatMap( i -> Arraysi.getDeclaredMethods() )
        */

        return Arrays.stream( p_class.getDeclaredMethods() )
                     .map( i ->
                           {
                               i.setAccessible( true );
                               return i;
                           } )
                     .filter( i -> !Modifier.isAbstract( i.getModifiers() ) )
                     .filter( i -> !Modifier.isInterface( i.getModifiers() ) )
                     .filter( i -> !Modifier.isNative( i.getModifiers() ) )
                     .filter( i -> !Modifier.isStatic( i.getModifiers() ) )
                     .filter( i -> i.isAnnotationPresent( IActionAllow.class ) || p_use )
                     .map( i ->  )
    }


}
