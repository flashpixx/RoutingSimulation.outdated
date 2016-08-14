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

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.examples.pokemon.simulation.agent.EAccess;
import org.lightjason.examples.pokemon.simulation.agent.IAgent;
import org.lightjason.examples.pokemon.simulation.agent.IBaseAgent;
import org.lightjason.examples.pokemon.simulation.agent.pokemon.datasource.CAttack;
import org.lightjason.examples.pokemon.simulation.agent.pokemon.datasource.CAttribute;
import org.lightjason.examples.pokemon.simulation.agent.pokemon.datasource.CDefinition;
import org.lightjason.examples.pokemon.simulation.agent.pokemon.datasource.CLevel;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * BDI agent for dynamic / moving elements
 * @todo add pokemon name
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
    private final Map<String, Pair<EAccess, Number>> m_attribute;
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
        m_attack = l_level.attack().stream().collect( Collectors.toMap( CAttack::name, i -> i ) );
        m_attribute = l_level.attribute().entrySet().stream().collect(
                          Collectors.toMap( i -> i.getKey().name(), i -> new ImmutablePair<>( i.getKey().access(), i.getValue() ) )
                      );

        m_beliefbase
            .add( new CEthnicBeliefbase().create( "ethnic", m_beliefbase ) )
            .add( new CAttributeBeliefbase().create( "attribute", m_beliefbase ) )
            .add( new CMotivationBeliefbase().create( "motivation", m_beliefbase ) );
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
        final EPokemon.CLevelTupel l_old = m_pokemon.tupel( m_level );
        m_level++;
        final CLevel l_new = CDefinition.INSTANCE.tupel( m_pokemon, m_level );

        // set data for visualization and internal attributes
        m_sprite = l_new.sprite( l_old.spritecell(), l_old.spriteunit() );
        m_attack.addAll( l_new.attack() );
        m_attribute.putAll( l_new.attributes() );
        m_motivation.putAll( l_new.motivation() );
        m_ethnic.putAll( l_new.ethnic() );

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
         * @param p_key enum term
         * @param p_value value
         * @return literal
         */
        final <T extends Enum<?>> ILiteral literal( final T p_key, final Number p_value )
        {
            return CLiteral.from( p_key.name().toLowerCase(), Stream.of( CRawTerm.from( p_value.doubleValue() ) ) );
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
        public final Stream<ILiteral> streamLiteral()
        {
            return m_motivation.entrySet().parallelStream()
                               .map( i -> this.literal( i.getKey(), i.getValue() ) );
        }

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return EMotivation.exist( p_key ) && m_motivation.containsKey( EMotivation.valueOf( p_key.toUpperCase() ) );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            if ( !this.containsLiteral( p_key ) )
                return Collections.<ILiteral>emptySet();

            final EMotivation l_key =  EMotivation.valueOf( p_key.toUpperCase() );
            return Stream.of( this.literal( l_key, m_motivation.get( l_key ) ) ).collect( Collectors.toSet() );
        }

        /**
         * creates a literal
         *
         * @param p_key enum term
         * @param p_value value
         * @return literal
         */
        private ILiteral literal( final EMotivation p_key, final Number p_value )
        {
            return CLiteral.from( p_key.name().toLowerCase(), Stream.of( CRawTerm.from( p_value.doubleValue() ) ) );
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
        public final Stream<ILiteral> streamLiteral()
        {
            return m_ethnic.entrySet().parallelStream()
                           .map( i -> this.literal( i.getKey(), i.getValue() ) );
        }

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return EEthnicity.exist( p_key ) && m_ethnic.containsKey( EEthnicity.valueOf( p_key.toUpperCase() ) );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            if ( !this.containsLiteral( p_key ) )
                return Collections.<ILiteral>emptySet();

            final EEthnicity l_key =  EEthnicity.valueOf( p_key.toUpperCase() );
            return Stream.of( this.literal( l_key, m_ethnic.get( l_key ) ) ).collect( Collectors.toSet() );
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
        public final ILiteral add( final ILiteral p_literal )
        {
            // only existing attributes will be accepted
            if ( !EAttribute.exist( p_literal.functor() ) )
                return p_literal;

            // check if the attribute can be written
            final EAttribute l_attribute = EAttribute.valueOf( p_literal.functor().toUpperCase() );
            if ( ( EAccess.READ.equals( l_attribute.access() ) ) || ( !m_attribute.containsKey( l_attribute ) ) )
                return p_literal;

            // check if the number of the attribute is changed
            final Number l_newvalue = CCommon.<Number, ITerm>raw( p_literal.orderedvalues().findFirst().orElse( CRawTerm.<Double>from( 0.0 ) ) );
            final Number l_oldvalue = m_attribute.get( l_attribute );
            if ( l_oldvalue.doubleValue() == l_newvalue.doubleValue() )
                return p_literal;

            // set value within the map and generate event
            m_attribute.put( l_attribute, l_newvalue );
            this.event( ITrigger.EType.DELETEBELIEF, CLiteral.from( l_attribute.name().toLowerCase(), Stream.of( CRawTerm.from( l_oldvalue ) ) ) );
            this.event( ITrigger.EType.ADDBELIEF, CLiteral.from( l_attribute.name().toLowerCase(), Stream.of( CRawTerm.from( l_newvalue ) ) ) );

            return p_literal;
        }

        @Override
        public final boolean empty()
        {
            return m_attribute.isEmpty();
        }

        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_attribute.entrySet().parallelStream()
                              .map( i -> this.literal( i.getKey(), i.getValue() ) );
        }

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return EAttribute.exist( p_key ) && m_attribute.containsKey( EAttribute.valueOf( p_key.toUpperCase() ) );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            if ( !this.containsLiteral( p_key ) )
                return Collections.<ILiteral>emptySet();

            final EAttribute l_key =  EAttribute.valueOf( p_key.toUpperCase() );
            return Stream.of( this.literal( l_key, m_attribute.get( l_key ) ) ).collect( Collectors.toSet() );
        }

    }

}
