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

import org.lightjason.examples.pokemon.simulation.agent.pokemon.CPokemon;
import org.lightjason.examples.pokemon.simulation.agent.pokemon.CPokemonGenerator;
import org.lightjason.examples.pokemon.simulation.agent.IAgent;
import org.lightjason.examples.pokemon.simulation.algorithm.routing.ERoutingFactory;
import org.lightjason.examples.pokemon.simulation.environment.CEnvironment;
import org.lightjason.examples.pokemon.simulation.environment.IEnvironment;
import org.lightjason.examples.pokemon.simulation.agent.CEvaluation;
import org.lightjason.examples.pokemon.simulation.item.CStatic;
import org.lightjason.examples.pokemon.simulation.item.IItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.IBaseAction;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.execution.fuzzy.CFuzzyValue;
import org.lightjason.agentspeak.language.execution.fuzzy.IFuzzyValue;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * configuration and initialization of all
 * simulation objects
 */
public final class CConfiguration
{
    /**
     * singleton instance
     */
    public static final CConfiguration INSTANCE = new CConfiguration();
    /**
     * configuration path
     */
    private String m_configurationpath;
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
     * simulation dynamic elements
     */
    private List<IAgent> m_agents;
    /**
     * simulation static elements
     */
    private List<IItem> m_staticelements;
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
     * thread-sleep time in milliseconds
     */
    private int m_threadsleeptime;
    /**
     * boolean flag to show stack trace
     */
    private boolean m_stacktrace;
    /**
     * evaluation object
     */
    private CEvaluation m_evaluation;



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
     *
     * @throws IOException on io errors
     * @throws URISyntaxException on URI syntax error
     */
    @SuppressWarnings( "unchecked" )
    public final CConfiguration load( final String p_input ) throws IOException, URISyntaxException
    {
        final URL l_path = CCommon.getResourceURL( p_input );

        // read configuration
        final Map<String, Object> l_data = (Map<String, Object>) new Yaml().load( l_path.openStream() );
        m_configurationpath = FilenameUtils.getPath( l_path.toString() );

        // get initial values
        m_stacktrace = (boolean) l_data.getOrDefault( "stacktrace", false );
        m_threadsleeptime = (int) l_data.getOrDefault( "threadsleeptime", 0 );
        m_simulationstep = (int) l_data.getOrDefault( "steps", Integer.MAX_VALUE );
        if ( !(boolean) l_data.getOrDefault( "logging", false ) )
            LogManager.getLogManager().reset();


        m_windowweight = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "weight", 800 );
        m_windowheight = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "height", 600 );
        m_zoomspeed = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "zoomspeed", 2 ) / 100f;
        m_zoomspeed = m_zoomspeed <= 0 ? 0.02f : m_zoomspeed;
        m_dragspeed = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "dragspeed", 100 ) / 1000f;
        m_dragspeed = m_dragspeed <= 0 ? 1f : m_dragspeed;

        m_screenshot = new ImmutableTriple<>(
            (String) ( (Map<String, Object>) l_data.getOrDefault( "screenshot", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "file", "" ),
            (String) ( (Map<String, Object>) l_data.getOrDefault( "screenshot", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "format", "" ),
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "screenshot", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "step", -1 )
        );

        // create static objects - static object are needed by the environment
        final List<IItem> l_static = new LinkedList<>();
        this.createStatic( (List<Map<String, Object>>) l_data.getOrDefault( "element", Collections.<Map<String, Object>>emptyList() ), l_static );
        m_staticelements = Collections.unmodifiableList( l_static );

        // create environment - static items must be exists
        m_environment = new CEnvironment(
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "rows", -1 ),
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "columns", -1 ),
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) )
                .getOrDefault( "cellsize", -1 ),
            ERoutingFactory.valueOf( ( (String) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) )
                .getOrDefault( "routing", "" ) ).trim().toUpperCase() ).get(),
            m_staticelements
        );

        // create executable object list and check number of elements - environment must be exists
        final List<IAgent> l_agents = new LinkedList<>();
        this.createAgent(
            (Map<String, Object>) l_data.getOrDefault( "agent", Collections.<String, Object>emptyMap() ),
            l_agents,
            (boolean) l_data.getOrDefault( "agentprint", true )
        );
        m_agents = Collections.unmodifiableList( l_agents );

        if ( m_agents.size() + m_staticelements.size() > m_environment.column() * m_environment.row() / 2 )
            throw new IllegalArgumentException(
                MessageFormat.format(
                    "number of simulation elements are very large [{0}], so the environment size is too small, the environment "
                    + "[{1}x{2}] must define a number of cells which is greater than the two-time number of elements",
                    m_agents.size(),
                    m_environment.row(),
                    m_environment.column()
                )
            );

        // create evaluation
        m_evaluation = new CEvaluation( m_agents.parallelStream() );


        // run initialization processes
        m_environment.initialize();

        return this;
    }


    /**
     * return window height
     *
     * @return height
     */
    final int windowheight()
    {
        return m_windowheight;
    }

    /**
     * return window weight
     *
     * @return weight
     */
    final int windowweight()
    {
        return m_windowweight;
    }

    /**
     * number of simulation steps
     *
     * @return steps
     */
    final int simulationsteps()
    {
        return m_simulationstep;
    }

    /**
     * returns the evaluation object
     *
     * @return evaluation
     */
    final CEvaluation evaluation()
    {
        return m_evaluation;
    }

    /**
     * return all static elements
     *
     * @return object list
     */
    final List<IItem> staticelements()
    {
        return m_staticelements;
    }

    /**
     * return all executable objects
     *
     * @return object list
     */
    final List<IAgent> agents()
    {
        return m_agents;
    }

    /**
     * returns the environment
     *
     * @return environment
     */
    public final IEnvironment environment()
    {
        return m_environment;
    }

    /**
     * get screenshot information
     *
     * @return triple of screenshot information
     */
    final Triple<String, String, Integer> screenshot()
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
     * returns the thread-sleep time
     *
     * @return sleep time
     */
    final int threadsleeptime()
    {
        return m_threadsleeptime;
    }

    /**
     * returns the stacktrace visiblity
     *
     * @return stacktrace visibility
     */
    final boolean stackstrace()
    {
        return m_stacktrace;
    }



    /**
     * creates the moving agent based on the configuration
     *
     * @param p_agentconfiguration subsection for agent configuration
     * @param p_elements element list
     * @param p_agentprint disables / enables agent printing
     * @throws IOException thrown on ASL reading error
     */
    @SuppressWarnings( "unchecked" )
    private void createAgent( final Map<String, Object> p_agentconfiguration, final List<IAgent> p_elements, final boolean p_agentprint ) throws IOException
    {
        final Map<String, IAgentGenerator<IAgent>> l_agentgenerator = new HashMap<>();
        final Set<IAction> l_action = Collections.unmodifiableSet(
            Stream.concat(
                p_agentprint ? Stream.of() : Stream.of( new CEmptyPrint() ),
                Stream.concat(
                    org.lightjason.agentspeak.common.CCommon.actionsFromPackage(),
                    org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CPokemon.class )
                )
            ).collect( Collectors.toSet() ) );

        p_agentconfiguration
            .entrySet()
            .forEach( i ->
            {
                final Map<String, Object> l_parameter = (Map<String, Object>) i.getValue();

                // read ASL item from configuration and get the path relative to configuration
                final String l_asl = m_configurationpath + ( (String) l_parameter.getOrDefault( "asl", "" ) ).trim();

                try (
                    // open filestream of ASL content
                    final InputStream l_stream = new URL( l_asl ).openStream();
                )
                {
                    // get existing agent generator or create a new one based on the ASL
                    // and push it back if generator does not exists
                    final IAgentGenerator<IAgent> l_generator = l_agentgenerator.getOrDefault(
                        l_asl,
                        new CPokemonGenerator(
                            m_environment,
                            l_stream,
                            l_action,
                            IAggregation.EMPTY
                        )
                    );
                    l_agentgenerator.putIfAbsent( l_asl, l_generator );

                    // generate agents and put it to the list
                    l_generator.generatemultiple(
                        (int) l_parameter.getOrDefault( "number", 0 ),

                        //EForceFactory.valueOf( ( (String) l_parameter.getOrDefault( "force", "" ) ).trim().toUpperCase() ).get(),

                        (String) l_parameter.getOrDefault( "pokemon", "" )

                    ).sequential().forEach( p_elements::add );
                }
                catch ( final Exception l_exception )
                {
                    System.err.println( MessageFormat.format( "error on agent generation: {0}", l_exception ) );
                }

            } );
    }

    /**
     * creates the static elements
     *
     * @param p_elementconfiguration subsection of static elements
     * @param p_elements element list
     */
    @SuppressWarnings( "unchecked" )
    private void createStatic( final List<Map<String, Object>> p_elementconfiguration, final List<IItem> p_elements )
    {
        p_elementconfiguration
            .stream()
            .map( i -> (Map<String, Object>) i.get( "static" ) )
            .filter( Objects::nonNull )
            .map( i -> new CStatic(
                (List<Integer>) i.get( "left" ),
                (List<Integer>) i.get( "right" ),
                (String) i.getOrDefault( "color", "" ), (Map<String, ?>) i.getOrDefault( "preferences", Collections.emptyMap() )
            ) )
            .forEach( p_elements::add );

    }

    /**
     * creates an empty print action to supress output
     */
    private static final class CEmptyPrint extends IBaseAction
    {
        @Override
        public final IPath name()
        {
            return CPath.from( "generic/print" );
        }

        @Override
        public final int minimalArgumentNumber()
        {
            return 0;
        }

        @Override
        public IFuzzyValue<Boolean> execute( final IContext p_context, final boolean p_parallel, final List<ITerm> p_argument, final List<ITerm> p_return,
                                             final List<ITerm> p_annotation
        )
        {
            return CFuzzyValue.from( true );
        }
    }

}
