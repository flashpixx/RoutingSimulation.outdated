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

import agentrouting.simulation.CEnvironment;
import agentrouting.simulation.IElement;
import agentrouting.simulation.IEnvironment;
import agentrouting.simulation.agent.CAgentGenerator;
import agentrouting.simulation.agent.IAgent;
import agentrouting.simulation.algorithm.force.EForceFactory;
import agentrouting.simulation.algorithm.routing.ERoutingFactory;
import cern.colt.matrix.tint.impl.DenseIntMatrix1D;
import lightjason.agentspeak.action.IAction;
import lightjason.agentspeak.generator.IAgentGenerator;
import lightjason.agentspeak.language.score.CZeroAggregation;
import lightjason.agentspeak.language.score.IAggregation;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;


/**
 * configuration and initialization of all
 * simulation objects
 */
public final class CConfiguration
{
    /**
     * singleton instance
     */
    public final static CConfiguration INSTANCE = new CConfiguration();
    /**
     * configuration path
     */
    public Path m_configurationpath;
    /**
     * logger
     */
    private final static Logger LOGGER = Logger.getLogger( CConfiguration.class.getName() );
    /**
     * window height
     */
    private int m_windowheight;
    /**
     * window weight
     */
    private int m_windowweight;
    /**
     * simulation steps
     */
    private int m_simulationstep;
    /**
     * simulating elements without environment
     */
    private List<IElement<?>> m_elements;
    /**
     * environment
     */
    private IEnvironment m_environment;
    /**
     * screenshot information
     * (file name, number format, iteration)
     */
    private Triple<String, String, Integer> m_screenshot;
    /**
     * zoom speed
     */
    private float m_zoomspeed = 0.02f;
    /**
     * dragging speed
     */
    private float m_dragspeed = 1f;



    /**
     * ctor
     */
    private CConfiguration()
    {
    }

    /**
     * loads the configuration from a file
     *
     * @param p_input YAML configuration file
     * @return instance
     */
    @SuppressWarnings( "unchecked" )
    public final CConfiguration load( final String p_input ) throws IOException, URISyntaxException
    {
        try (
            final InputStream l_stream = CCommon.getResourceURL( p_input ).openStream();
        )
        {

            // read configuration
            final Map<String, Object> l_data = (Map<String, Object>) new Yaml().load( l_stream );
            m_configurationpath = Paths.get( p_input ).normalize().getParent();

            // disable logging
            //LogManager.getLogManager().reset();


            // get initial values
            m_simulationstep = (int) l_data.getOrDefault( "steps", Integer.MAX_VALUE );

            m_windowweight = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "weight", 800 );
            m_windowheight = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "height", 600 );
            m_zoomspeed = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "zoomspeed", 2 ) /
                          100f;
            m_zoomspeed = m_zoomspeed <= 0 ? 0.02f : m_zoomspeed;
            m_dragspeed = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "dragspeed", 100 ) /
                          1000f;
            m_dragspeed = m_dragspeed <= 0 ? 1f : m_dragspeed;

            m_screenshot = new ImmutableTriple<>(
                (String) ( (Map<String, Object>) l_data.getOrDefault( "screenshot", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "file", "" ),
                (String) ( (Map<String, Object>) l_data.getOrDefault( "screenshot", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "format", "" ),
                (int) ( (Map<String, Object>) l_data.getOrDefault( "screenshot", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "step", -1 )
            );

            // create environment
            m_environment = new CEnvironment(
                (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "rows", -1 ),
                (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "columns", -1 ),
                (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) )
                    .getOrDefault( "cellsize", -1 ),
                ERoutingFactory.valueOf( ( (String) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) )
                    .getOrDefault( "routing", "" ) ).trim().toUpperCase() ).get()
            );

            // create executable object list
            final List<IElement<?>> l_elements = new LinkedList<>();
            this.createAgent( (Map<String, Object>) l_data.getOrDefault( "agent", Collections.<String, Object>emptyMap() ), l_elements );
            m_elements = Collections.unmodifiableList( l_elements );

            // run initialization processes
            m_environment.initialize();

            return this;
        }
        catch ( final IOException | URISyntaxException l_exception )
        {
            throw l_exception;
        }
    }

    /**
     * return window height
     *
     * @return height
     */
    public final int getWindowHeight()
    {
        return m_windowheight;
    }

    /**
     * return window weight
     *
     * @return weight
     */
    public final int getWindowWeight()
    {
        return m_windowweight;
    }

    /**
     * number of simulation steps
     *
     * @return steps
     */
    public final int getSimulationSteps()
    {
        return m_simulationstep;
    }

    /**
     * return all executable objects
     *
     * @return object list
     */
    public final List<IElement<?>> getObjects()
    {
        return m_elements;
    }

    /**
     * returns the environment
     *
     * @return environment
     */
    public final IEnvironment getEnvironment()
    {
        return m_environment;
    }

    /**
     * get screenshot information
     *
     * @return triple of screenshot information
     */
    public final Triple<String, String, Integer> getScreenshot()
    {
        return m_screenshot;
    }

    /**
     * returns the zoom speed
     *
     * @return zoom speed
     */
    public final float zoomspeed()
    {
        return m_zoomspeed;
    }

    /**
     * returns the dragging speed
     *
     * @return dragging speed
     */
    public final float dragspeed()
    {
        return m_dragspeed;
    }


    /**
     * creates the agent based on the configuration
     *
     * @param p_agentconfiguration subsection for agent configuration
     * @param p_elements element list
     */
    @SuppressWarnings( "unchecked" )
    private void createAgent( final Map<String, Object> p_agentconfiguration, final List<IElement<?>> p_elements ) throws IOException
    {
        final Random l_random = new Random();
        final IAggregation l_aggregation = new CZeroAggregation();
        final Map<String, IAgentGenerator<IElement<IAgent>>> m_agentgenerator = new HashMap<>();
        final Set<IAction> l_action = lightjason.agentspeak.common.CCommon.getActionsFromPackage();

        p_agentconfiguration
            .entrySet()
            .stream()
            .forEach( i ->
                      {
                          final Map<String, Object> l_parameter = ( (Map<String, Object>) i.getValue() );

                          // read ASL item from configuration and get the path relative to configuration
                          final String l_asl = m_configurationpath.resolve( ( (String) l_parameter.getOrDefault( "asl", "" ) ).trim() ).toString();

                          try (
                              // open filestream of ASL content
                              final InputStream l_stream = CCommon.getResourceURL( l_asl ).openStream();
                          )
                          {

                              // get existing agent generator or create a new one based on the ASL
                              // and push it back if generator does not exists
                              final IAgentGenerator<IElement<IAgent>> l_generator = m_agentgenerator.getOrDefault(
                                  l_asl,
                                  new CAgentGenerator(
                                      m_environment,
                                      l_stream,
                                      l_action,
                                      l_aggregation
                                  )
                              );
                              m_agentgenerator.putIfAbsent( l_asl, l_generator );

                              // generate agents and put it to the list
                              l_generator.generatemultiple(
                                  (int) l_parameter.getOrDefault( "number", 0 ),

                                  new DenseIntMatrix1D(
                                      new int[]{m_environment.row() / 2, m_environment.column() / 2}
                                      //new int[]{l_random.nextInt( m_environment.row() ), l_random.nextInt( m_environment.column() )}
                                  ),

                                  EForceFactory.valueOf( ( (String) l_parameter.getOrDefault( "force", "" ) ).trim().toUpperCase() ).get(),

                                  (String) l_parameter.getOrDefault( "color", "ffffff" )

                              ).forEach( l -> m_elements.add( l ) );
                          }
                          catch ( final Exception l_exception )
                          {
                              LOGGER.warning( MessageFormat.format( "error on agent generation: {0}", l_exception ) );
                          }

                      } );
    }

}
