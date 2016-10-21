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


package org.lightjason.examples.pokemon.simulation.agent.pokemon;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.jet.math.Functions;
import com.google.common.util.concurrent.AtomicDouble;
import org.apache.commons.lang3.tuple.MutablePair;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.consistency.metric.CNCD;
import org.lightjason.agentspeak.consistency.metric.IMetric;
import org.lightjason.examples.pokemon.simulation.CMath;
import org.lightjason.examples.pokemon.simulation.IElement;
import org.lightjason.examples.pokemon.simulation.agent.EAccess;
import org.lightjason.examples.pokemon.simulation.agent.IAgent;
import org.lightjason.examples.pokemon.simulation.agent.IBaseAgent;
import org.lightjason.examples.pokemon.simulation.algorithm.force.collectors.CSum;
import org.lightjason.examples.pokemon.simulation.algorithm.force.potential.IExponential;
import org.lightjason.examples.pokemon.simulation.algorithm.force.potential.rating.IPositiveNegative;
import org.lightjason.examples.pokemon.simulation.environment.IEnvironment;
import cern.colt.matrix.DoubleMatrix1D;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;
import org.lightjason.examples.pokemon.ui.CParticleSystem;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * BDI agent for dynamic / moving elements
 *
 * @see http://pokewiki.de/
 * @see http://bulbapedia.bulbagarden.net/wiki/Main_Page
 */
@IAgentAction
public final class CPokemon extends IBaseAgent
{
    /**
     * attribute of speed
     */
    private static final String ATTRIBUTE_SPEED = "speed";
    /**
     * near-by attribute
     */
    private static final String ATTRIBUTE_NEARBY = "nearby";
    /**
     * interest attribute
     */
    private static final String ATTRIBUTE_INTEREST = "interest";
    /**
     * ratient gradient attribute
     */
    private static final String ATTRIBUTE_RATINGGRADIENT = "ratinggradient";
    /**
     * rating inflection point attribute
     */
    private static final String ATTRIBUTE_RATINGINFLECTIONPOINT = "ratinginflectionpoint";
    /**
     * visual range attribute
     */
    private static final String ATTRIBUTE_VISUALRANGE = "viewrange";
    /**
     * social force metric
     */
    private static final IMetric SOCIALFORCEMETRIC = new CNCD();
    /**
     * pokemon type
     */
    private final String m_pokemon;
    /**
     * ethnic map
     */
    private final Map<String, Number> m_ethnic;
    /**
     * motivation map
     */
    private final Map<String, Number> m_motivation;
    /**
     * attack map
     */
    private final Map<String, CAttack> m_attack;
    /**
     * attribute map
     */
    private final Map<String, MutablePair<EAccess, Number>> m_attribute;
    /**
     * current objects within the view-range
     */
    private final Map<String, ILiteral> m_environmentliteral = new ConcurrentHashMap<>();
    /**
     * social-force potential function
     */
    private final CPotential m_socialforcepotential;
    /**
     * social-force potential rating function
     */
    private final CRating m_socialforcepotentialrating;
    /**
     * social-force visual-range distance
     */
    private final AtomicDouble m_socialforcedistance = new AtomicDouble();
    /**
     * social-force metric
     */
    private final Function<IElement, Double> m_socialforcemetric;
    /**
     * experience maximum
     */
    private final BigInteger m_experiencemaximum;
    /**
     * experience of level update
     */
    private final BigInteger m_levelexperience;
    /**
     * current experience
     */
    private AtomicReference<BigInteger> m_experience = new AtomicReference<>( BigInteger.ZERO );
    /**
     * current level
     */
    private AtomicInteger m_level = new AtomicInteger();


    /**
     * ctor
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_position initialize position
     * @param p_pokemon pokemon name
     */
    @SuppressWarnings( "unchecked" )
    public CPokemon( final IEnvironment p_environment, final IAgentConfiguration<IAgent> p_agentconfiguration,
                     final DoubleMatrix1D p_position, final String p_pokemon
    )
    {
        super( p_environment, p_agentconfiguration, p_position );

        if ( p_pokemon.isEmpty() )
            throw new RuntimeException( "pokemon name need not to be empty" );

        m_pokemon = p_pokemon;
        final CLevel l_level = CDefinition.INSTANCE.tupel( m_pokemon, m_level.get() );

        m_experiencemaximum = CDefinition.INSTANCE.experience( m_pokemon );
        m_levelexperience = m_experiencemaximum.divide( BigInteger.valueOf( CDefinition.INSTANCE.level( p_pokemon ) ) );
        m_ethnic = l_level.ethnic();
        m_motivation = l_level.motivation();
        m_attack = l_level.attack().stream().collect( Collectors.toConcurrentMap( CAttack::name, i -> i ) );
        m_attribute = l_level.attribute().entrySet().stream().collect(
                          Collectors.toConcurrentMap( i -> i.getKey().name(), i -> new MutablePair<>( i.getKey().access(), i.getValue() ) )
                      );

        m_beliefbase
            .add( new CEthnicBeliefbase().create( "ethnic", m_beliefbase ) )
            .add( new CAttributeBeliefbase().create( "attribute", m_beliefbase ) )
            .add( new CMotivationBeliefbase().create( "motivation", m_beliefbase ) )
            .add( new CAttackBeliefbase().create( "attack", m_beliefbase ) )
            .add( new CEnvironmentBeliefbase().create( "env", m_beliefbase  ) );

        m_socialforcepotential = new CPotential();
        m_socialforcepotentialrating = new CRating();
        m_socialforcemetric = ( i ) -> SOCIALFORCEMETRIC.apply( this.attribute(), i.attribute() );
    }

    @Override
    public final void spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit )
    {
        m_sprite = CDefinition.INSTANCE.tupel( m_pokemon, 0 ).sprite( p_cellsize, p_unit );
        m_attack.values().forEach( i -> CParticleSystem.INSTANCE.initialize( i.particlesystem(), p_unit ) );

        // initialize all other sprites and particle systems
        IntStream.range( 1, CDefinition.INSTANCE.level( m_pokemon ) )
                 .parallel()
                 .forEach( i -> CDefinition.INSTANCE.tupel( m_pokemon, i ).sprite( p_cellsize, p_unit ) );
        IntStream.range( 1, CDefinition.INSTANCE.level( m_pokemon ) )
                 .boxed()
                 .flatMap( i -> CDefinition.INSTANCE.tupel( m_pokemon, i ).attack().stream() )
                 .forEach( i -> CParticleSystem.INSTANCE.initialize( i.particlesystem(), p_unit ) );
    }


    // --- pokemon internals -----------------------------------------------------------------------------------------------------------------------------------

    @Override
    public final IAgent call() throws Exception
    {
        // level-up if needed
        this.levelup();

        // calculating force and build trigger (determine view vector and object position the angle)
        final CViewDirection l_viewdirection = CViewDirection.generate(
            m_attribute.getOrDefault( "viewangle", new MutablePair<>( EAccess.READ, 0 ) ).getRight().doubleValue(),
            m_attribute.getOrDefault( "viewrange", new MutablePair<>( EAccess.READ, 1 ) ).getRight().intValue(),
            m_position,
            this.goal()
        );

        final CForceGenerator l_forceliteralgenerator = CForceGenerator.generate( this );
        m_environmentliteral.clear();

        Stream.concat(
            CCommon.literalpokemon( m_position, this.goal(), m_pokemon ),
            this.perceive( l_viewdirection )
                //.map( l_forceliteralgenerator::push )
                .map( CCommon::elementliteral )
                .filter( Objects::nonNull )
        )
            .forEach( i -> m_environmentliteral.putIfAbsent( i.functor(), i ) );

        // run cycle
        super.call();

        // update social-force elements
        m_socialforcepotential.call();
        m_socialforcepotentialrating.call();
        m_socialforcedistance.set( m_attribute.getOrDefault( ATTRIBUTE_VISUALRANGE, new MutablePair<>( EAccess.READ, 0 )  ).getValue().doubleValue() );

        return this;
    }

    /**
     * runs the level-up
     * @see http://www.pokewiki.de/Erfahrung
     */
    private void levelup()
    {
        // check current experience depend on level and sprite
        final int l_level = m_experience.get().divide( m_levelexperience ).intValue();
        if ( ( m_sprite == null ) || ( l_level == m_level.get() ) || ( m_experience.get().compareTo( m_experiencemaximum ) != -1 ) )
            return;

        // increment level and get old and new level structure of the pokemon
        final CLevel l_old = CDefinition.INSTANCE.tupel( m_pokemon, m_level.get() );
        final CLevel l_new = CDefinition.INSTANCE.tupel( m_pokemon, l_level );

        // set data for visualization and internal attributes
        m_sprite = l_new.sprite( l_old.spritecell(), l_old.spriteunit() );
        l_new.attack().forEach( i -> m_attack.put( i.name(), i ) );
        l_new.attribute().entrySet().forEach( i -> m_attribute.put( i.getKey().name(), new MutablePair<>( i.getKey().access(), i.getValue() ) ) );
        l_new.motivation().entrySet().forEach( i -> m_motivation.put( i.getKey(), i.getValue() ) );
        l_new.ethnic().entrySet().forEach( i -> m_ethnic.put( i.getKey(), i.getValue() ) );

        // set new level
        m_level.set( l_level );

        // create goal-trigger
        this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "level-up" ) ) );
    }

    @Override
    public final Stream<ITerm> attribute()
    {
        return m_ethnic.entrySet().parallelStream().map( i -> CLiteral.from( i.getKey(), Stream.of( CRawTerm.from( i.getValue() ) ) ) );
    }

    @Override
    protected final int speed()
    {
        // null check because of initialization of base class
        return m_attribute == null
               ? 0
               : m_attribute.getOrDefault( ATTRIBUTE_SPEED, new MutablePair<>( EAccess.READ, 0 ) ).getRight().intValue();
    }

    @Override
    protected final double nearby()
    {
        // null check because of initialization of base class
        return m_attribute == null
               ? 0
               : m_attribute.getOrDefault( ATTRIBUTE_NEARBY, new MutablePair<>( EAccess.READ, 0 ) ).getRight().doubleValue();
    }

    /**
     * environment perceiving
     *
     * @param p_direction structure
     * @return stream with symbolic environment literals
     */
    private Stream<IElement> perceive( final CViewDirection p_direction )
    {
        if ( !p_direction.error() )
            return Stream.of();

        // iterate over the possible cells of the grid to read other elements,
        // center is the rotated view point and iterate the sequare with radius size
        return IntStream.rangeClosed( -p_direction.radius(), p_direction.radius() )
                        .parallel()
                        .boxed()
                        .flatMap( y -> IntStream.rangeClosed( -p_direction.radius(), p_direction.radius() )
                                                .boxed()
                                                .filter( x -> CCommon.positioninsideangle( y, x, p_direction.angle() ) )
                                                .map( x -> new DenseDoubleMatrix1D(
                                                               new double[]{
                                                                   y + p_direction.direction().getQuick( 0 ),
                                                                   x + p_direction.direction().getQuick( 1 )}
                                                               )
                                                )
                                                .filter( m_environment::isinside )
                                                .map( m_environment::get )
                                                .filter( Objects::nonNull )
                        );
    }



    // --- agent actions ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * point attack action
     *
     * @param p_attack attack name (must match enum)
     * @param p_power power of the attack (0,1]
     * @param p_row target row position
     * @param p_column target column position
     * @todo check power of attack
     */
    @IAgentActionFilter
    @IAgentActionName( name = "attack/point" )
    private void pointattack( final String p_attack, final double p_power, final Number p_row, final Number p_column )
    {
        final CAttack l_attack = this.attack( p_attack );
        final DoubleMatrix1D l_target = new DenseDoubleMatrix1D( new double[]{p_row.doubleValue(), p_column.doubleValue()} );
        final IElement l_element = m_environment.get( l_target );

        // check attack distance
        if ( CMath.ALGEBRA.norm2(
                new DenseDoubleMatrix1D( new double[]{p_row.doubleValue(), p_column.doubleValue()} )
                    .assign( m_position, Functions.minus )
             )
             > l_attack.distance()
        )
            throw new RuntimeException(
                MessageFormat.format( "position between {0} and {1} greater than attack distance {2}",
                                      CMath.MATRIXFORMAT.toString( m_position ),
                                      CMath.MATRIXFORMAT.toString( l_target ),
                                      l_attack.distance() )
            );

        // show particle system
        CParticleSystem.INSTANCE.execute( l_attack.particlesystem(), l_target );

        // check target position
        if (  ( l_element == null ) || ( !( l_element instanceof IAgent ) )  )
            throw new RuntimeException( MessageFormat.format( "target postion {0} is empty or is not an agent", CMath.MATRIXFORMAT.toString( l_target ) ) );

        // increment experience
        m_experience.set(
            m_experience.get().add( BigInteger.valueOf( (long) ( l_attack.power() * m_levelexperience.doubleValue() * ( m_level.get() + 1 ) ) ) )
        );

    }

    /**
     * point attack action
     *
     * @param p_attack attack name (must match enum)
     * @param p_power power of the attack (0,1]
     * @param p_direction direction of the attack
     */
    @IAgentActionFilter
    @IAgentActionName( name = "attack/area" )
    private void areaattack( final String p_attack, final double p_power, final String p_direction )
    {
        final CAttack l_attack = this.attack( p_attack );
    }

    /**
     * get an attack
     *
     * @param p_attack attack name
     * @return attack or throws an exception
     */
    private CAttack attack( final String p_attack )
    {
        final CAttack l_attack = m_attack.get( p_attack.trim().toLowerCase() );

        // if attack cannot be found
        if ( l_attack == null )
            throw new RuntimeException( MessageFormat.format( "attack [{0}] not exists", p_attack ) );

        // check accuracy that the attack will successful executed
        if ( CMath.RANDOMGENERATOR.nextDouble() > l_attack.accuracy() )
            throw new RuntimeException( MessageFormat.format( "attack fails [{0}]", p_attack ) );

        return l_attack;
    }


    /**
     * curing a pokemon on another position
     *
     * @param p_power power of curing
     * @param p_row target row position
     * @param p_column target column position
     */
    @IAgentActionFilter
    @IAgentActionName( name = "act/cure" )
    private void cure( final double p_power, final Number p_row, final Number p_column )
    {

    }

    // --- social-force model structure ------------------------------------------------------------------------------------------------------------------------

    @Override
    public final Function<IElement, Double> metric()
    {
        return m_socialforcemetric;
    }

    @Override
    public final UnaryOperator<Double> potential()
    {
        return m_socialforcepotential;
    }

    @Override
    public final BiFunction<Double, Double, Double> potentialrating()
    {
        return m_socialforcepotentialrating;
    }

    @Override
    public final Collector<Double, ?, Double> potentialreduce()
    {
        return CSum.factory();
    }

    @Override
    public final BiFunction<IElement, IElement, Double> distancescale()
    {
        return (i, j) -> 1 - CMath.ALGEBRA.norm2( new DenseDoubleMatrix1D( i.position().toArray() ).assign( j.position(), Functions.minus ) ) / m_socialforcedistance.get();
    }

    @Override
    public final Collector<Double, ?, Double> forceresult()
    {
        return CSum.factory();
    }

    /**
     * potential function
     */
    private final class CPotential extends IExponential implements Callable<UnaryOperator<Double>>
    {
        /**
         * maximum potential value
         */
        private final AtomicDouble m_maximum = new AtomicDouble();

        @Override
        public UnaryOperator<Double> call() throws Exception
        {
            // read the attribute
            m_maximum.set( m_attribute.getOrDefault( ATTRIBUTE_INTEREST, new MutablePair<>( EAccess.READ, 0 ) ).getRight().doubleValue() );
            return this;
        }

        @Override
        protected final double maximum()
        {
            return m_maximum.get();
        }

        @Override
        protected final double scale()
        {
            return 1;
        }

    }

    /**
     * potential rating function (like / dislike)
     */
    private final class CRating extends IPositiveNegative implements Callable<BiFunction<Double, Double, Double>>
    {
        /**
         * gradient rating value
         */
        private final AtomicDouble m_gradient = new AtomicDouble();
        /**
         * inflection point of the function
         */
        private final AtomicDouble m_inflectionpoint = new AtomicDouble();


        @Override
        public final BiFunction<Double, Double, Double> call() throws Exception
        {
            // read attributes
            m_gradient.set( m_attribute.getOrDefault( ATTRIBUTE_RATINGGRADIENT, new MutablePair<>( EAccess.READ, 0 ) ).getRight().doubleValue() );
            m_inflectionpoint.set( m_attribute.getOrDefault( ATTRIBUTE_RATINGINFLECTIONPOINT, new MutablePair<>( EAccess.READ, 0 ) ).getRight().doubleValue() );
            return this;
        }


        @Override
        protected final double gradient()
        {
            return m_gradient.get();
        }

        @Override
        protected final double inflectionpoint()
        {
            return m_inflectionpoint.get();
        }

    }

    // --- on-demand beliefbases -------------------------------------------------------------------------------------------------------------------------------

    /**
     * beliefbase of the motivation elements
     */
    private final class CMotivationBeliefbase extends IDemandBeliefbase
    {
        @Override
        public final int size()
        {
            return m_motivation.size();
        }

        @Override
        public final boolean empty()
        {
            return m_motivation.isEmpty();
        }

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return m_motivation.containsKey( p_key.toLowerCase() );
        }

        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_motivation.entrySet().parallelStream().map( i -> this.numberliteral( i.getKey(), i.getValue() ) );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            return this.containsLiteral( p_key )
                   ? Stream.of( this.numberliteral( p_key, m_motivation.get( p_key ) ) ).collect( Collectors.toSet() )
                   : Collections.emptySet();
        }
    }


    /**
     * beliefbase of the motivation elements
     */
    private final class CEthnicBeliefbase extends IDemandBeliefbase
    {
        @Override
        public final int size()
        {
            return m_ethnic.size();
        }

        @Override
        public final boolean empty()
        {
            return m_ethnic.isEmpty();
        }

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return m_ethnic.containsKey( p_key );
        }

        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_ethnic.entrySet().parallelStream().map( i -> this.numberliteral( i.getKey(), i.getValue() ) );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            return this.containsLiteral( p_key )
                   ? Stream.of( this.numberliteral( p_key, m_ethnic.get( p_key ) ) ).collect( Collectors.toSet() )
                   : Collections.emptySet();
        }

    }


    /**
     * beliefbase of the attribute elements
     */
    private final class CAttributeBeliefbase extends IDemandBeliefbase
    {
        @Override
        public final int size()
        {
            return m_attribute.size();
        }

        @Override
        public final boolean empty()
        {
            return m_attribute.isEmpty();
        }

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return m_attribute.containsKey( p_key );
        }

        @Override
        public final ILiteral add( final ILiteral p_literal )
        {
            final MutablePair<EAccess, Number> l_value = m_attribute.get( p_literal.functor() );
            if ( (  l_value == null ) || ( EAccess.READ.equals( l_value.getLeft() ) ) )
                return p_literal;

            // check if the number of the attribute is changed
            final Number l_newvalue = p_literal.orderedvalues().findFirst().orElse( CRawTerm.from( 0 ) ).raw();
            if ( l_value.getRight().intValue() == l_newvalue.intValue() )
                return p_literal;

            // creates the trigger and sets the new value
            this.event( ITrigger.EType.DELETEBELIEF, CLiteral.from( p_literal.functor(), Stream.of( CRawTerm.from( l_value.getRight().intValue() ) ) ) );
            l_value.setRight( l_newvalue );

            return this.event( ITrigger.EType.ADDBELIEF, p_literal );
        }

        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_attribute.entrySet().parallelStream()
                              .map( i -> this.numberliteral( i.getKey(), i.getValue().getRight() ) );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            return this.containsLiteral( p_key )
                   ? Stream.of( this.numberliteral( p_key, m_attribute.getOrDefault( p_key, new MutablePair<>( EAccess.READ, 0 ) ).getRight() ) ).collect( Collectors.toSet() )
                   : Collections.emptySet();
        }

    }


    /**
     * beliefbase of the attack elements
     */
    private final class CAttackBeliefbase extends IDemandBeliefbase
    {
        @Override
        public final int size()
        {
            return m_attack.size();
        }

        @Override
        public final boolean empty()
        {
            return m_attack.isEmpty();
        }

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return m_attack.containsKey( p_key );
        }

        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return Stream.concat(
                       m_attack.values().parallelStream().map( this::attackliteral ),
                       Stream.of( this.attacklist() )
            );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            return Stream.of(
                              "list".equals( p_key )
                              ? this.attacklist()
                              : this.attackliteral( m_attack.get( p_key ) )
            )
            .filter( Objects::nonNull )
            .collect( Collectors.toSet() );
        }

        /**
         * creates a literal with a list of attacks
         *
         * @return literal
         */
        private ILiteral attacklist()
        {
            return CLiteral.from( "list", m_attack.keySet().stream().map( CLiteral::from ) );
        }

        /**
         * create a literal for an attack
         *
         * @param p_attack attack
         * @return literal
         */
        private ILiteral attackliteral( final CAttack p_attack )
        {
            return p_attack == null
                   ? null
                   : CLiteral.from(
                                   p_attack.name(),
                                   Stream.of(
                                       this.numberliteral( "accuracy", p_attack.accuracy() ),
                                       this.numberliteral( "distance", p_attack.distance() ),
                                       this.numberliteral( "cost", p_attack.power() ),
                                       CLiteral.from( "damage", p_attack.damage().entrySet().stream()
                                                                   .map( i -> this.numberliteral( i.getKey().name(), i.getValue() ) )
                                       )
                                   )

                   );
        }
    }

    /**
     * environment beliefbase with angle- and radius-based view-range
     */
    private final class CEnvironmentBeliefbase extends IDemandBeliefbase
    {
        @Override
        public Stream<ILiteral> streamLiteral()
        {
            return m_environmentliteral.values().stream();
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            final ILiteral l_literal = m_environmentliteral.get( p_key );
            return l_literal == null
                   ? Collections.emptySet()
                   : Stream.of( l_literal ).collect( Collectors.toSet() );
        }

    }

}
