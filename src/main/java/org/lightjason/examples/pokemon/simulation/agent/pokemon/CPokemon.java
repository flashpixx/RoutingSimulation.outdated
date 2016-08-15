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

import org.apache.commons.lang3.tuple.MutablePair;
import org.lightjason.examples.pokemon.simulation.agent.EAccess;
import org.lightjason.examples.pokemon.simulation.agent.IAgent;
import org.lightjason.examples.pokemon.simulation.agent.IBaseAgent;
import org.lightjason.examples.pokemon.simulation.environment.IEnvironment;
import org.lightjason.examples.pokemon.simulation.algorithm.force.IForce;
import cern.colt.matrix.DoubleMatrix1D;
import org.lightjason.agentspeak.action.binding.IAgentActionAllow;
import org.lightjason.agentspeak.action.binding.IAgentActionBlacklist;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * BDI agent for dynamic / moving elements
 */
@IAgentActionBlacklist
public final class CPokemon extends IBaseAgent
{
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
     * maximum level
     */
    private final int m_maximumlevel;
    /**
     * current level
     */
    private int m_level;


    /**
     * ctor
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_position initialize position
     * @param p_force force model
     * @param p_pokemon pokemon name
     */
    @SuppressWarnings( "unchecked" )
    public CPokemon( final IEnvironment p_environment, final IAgentConfiguration<IAgent> p_agentconfiguration,
                     final DoubleMatrix1D p_position, final IForce p_force, final String p_pokemon
    )
    {
        super( p_environment, p_agentconfiguration, p_force, p_position );

        if ( p_pokemon.isEmpty() )
            throw new RuntimeException( "pokemon name need not to be empty" );

        m_pokemon = p_pokemon;
        m_maximumlevel = CDefinition.INSTANCE.level( m_pokemon );
        final CLevel l_level = CDefinition.INSTANCE.tupel( m_pokemon, m_level );

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
            .add( new CAttackBeliefbase().create( "attack", m_beliefbase ) );
    }

    @Override
    public final String toString()
    {
        return MessageFormat.format( "{0} - pokemon [{1}] - ethnic {2} - attributes {3} - motivation {4}", super.toString(), m_pokemon, m_ethnic, m_attribute, m_motivation );
    }

    @Override
    public final void spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit )
    {
        m_sprite = CDefinition.INSTANCE.tupel( m_pokemon, 0 ).sprite( p_cellsize, p_unit );
    }


    // --- pokemon internals -----------------------------------------------------------------------------------------------------------------------------------

    /**
     * runs the level-up
     * @todo check for experience
     */
    private void levelup()
    {
        if ( m_level >= m_maximumlevel - 1 )
            return;

        // increment level and get old and new level structure of the pokemon
        final CLevel l_old = CDefinition.INSTANCE.tupel( m_pokemon, m_level );
        m_level++;
        final CLevel l_new = CDefinition.INSTANCE.tupel( m_pokemon, m_level );

        // set data for visualization and internal attributes
        m_sprite = l_new.sprite( l_old.spritecell(), l_old.spriteunit() );
        l_new.attack().forEach( i -> m_attack.put( i.name(), i ) );
        l_new.attribute().entrySet().forEach( i -> m_attribute.put( i.getKey().name(), new MutablePair<>( i.getKey().access(), i.getValue() ) ) );
        l_new.motivation().entrySet().forEach( i -> m_motivation.put( i.getKey(), i.getValue() ) );
        l_new.ethnic().entrySet().forEach( i -> m_ethnic.put( i.getKey(), i.getValue() ) );

        this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "level-up" ) ) );
    }


    // --- agent actions ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * point attack action
     *
     * @param p_attack attack name (must match enum)
     * @param p_power power of the attack (0,1]
     * @param p_row target row position
     * @param p_column target column position
     */
    @IAgentActionName( name = "act/attack/point" )
    @IAgentActionAllow( classes = CPokemon.class )
    private void pointattack( final String p_attack, final double p_power, final Number p_row, final Number p_column )
    {

    }

    /**
     * point attack action
     *
     * @param p_attack attack name (must match enum)
     * @param p_power power of the attack (0,1]
     * @param p_direction direction of the attack
     */
    @IAgentActionName( name = "act/attack/area" )
    @IAgentActionAllow( classes = CPokemon.class )
    private void areaattack( final String p_attack, final double p_power, final String p_direction )
    {

    }

    /**
     * curing a pokemon on another position
     *
     * @param p_power power of curing
     * @param p_row target row position
     * @param p_column target column position
     */
    @IAgentActionName( name = "act/cure" )
    @IAgentActionAllow( classes = CPokemon.class )
    private void cure( final double p_power, final Number p_row, final Number p_column )
    {

    }



    // --- on-demand beliefbases -------------------------------------------------------------------------------------------------------------------------------

    /**
     * abstract class for demand-beliefbase
     */
    private abstract static class IDemandBeliefbase extends IBeliefbaseOnDemand<IAgent>
    {
        @Override
        public ILiteral add( final ILiteral p_literal )
        {
            return p_literal;
        }

        @Override
        public final ILiteral remove( final ILiteral p_literal )
        {
            return p_literal;
        }

        /**
         * creates a literal
         *
         * @param p_key string name
         * @param p_value value
         * @return literal
         */
        final ILiteral numberliteral( final String p_key, final Number p_value )
        {
            return CLiteral.from( p_key, Stream.of( CRawTerm.from( p_value.doubleValue() ) ) );
        }

        @Override
        public final String toString()
        {
            return this.streamLiteral().collect( Collectors.toSet() ).toString();
        }
    }

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
            final Number l_newvalue = CCommon.<Number, ITerm>raw( p_literal.orderedvalues().findFirst().orElse( CRawTerm.from( 0.0 ) ) );
            if ( l_value.getRight().doubleValue() == l_newvalue.doubleValue() )
                return p_literal;

            // creates the trigger and sets the new value
            this.event( ITrigger.EType.DELETEBELIEF, CLiteral.from( p_literal.functor(), Stream.of( CRawTerm.from( l_value.getRight().doubleValue() ) ) ) );
            l_value.setRight( l_newvalue );
            this.event( ITrigger.EType.ADDBELIEF, CLiteral.from( p_literal.functor(), Stream.of( CRawTerm.from( l_value.getRight().doubleValue() ) ) ) );

            return p_literal;
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
            .filter( i -> i != null )
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
                                       this.numberliteral( "cost", p_attack.cost() ),
                                       CLiteral.from( "damage", p_attack.damage().entrySet().stream()
                                                                   .map( i -> this.numberliteral( i.getKey().name(), i.getValue() ) )
                                       )
                                   )

                   );
        }
    }

}
