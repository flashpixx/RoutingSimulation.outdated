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


package agentrouting.simulation.agent.pokemon;

import agentrouting.simulation.agent.IAgent;
import agentrouting.simulation.agent.IBaseAgent;
import agentrouting.simulation.environment.IEnvironment;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * BDI agent for dynamic / moving elements
 * @todo add pokemon name
 */
public final class CPokemon extends IBaseAgent
{
    /**
     * pokemon type
     */
    private final EPokemon m_pokemon;
    /**
     * ethnic map
     */
    private final Map<EEthncity, Number> m_ethnic;
    /**
     * attribute map
     */
    private final Map<EAttribute, Number> m_attribute;
    /**
     * motivation map
     */
    private final Map<EMotivation, Number> m_motivation;


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

        m_pokemon = EPokemon.valueOf( p_pokemon.trim().toUpperCase() );
        m_ethnic = m_pokemon.ethnic();
        m_attribute = m_pokemon.attributes();
        m_motivation = m_pokemon.motivation();

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
        m_sprite = new Sprite( m_pokemon.initialize() );
        m_sprite.setSize( p_cellsize, p_cellsize );
        m_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );
        m_sprite.setScale( p_unit );
    }

    /**
     * beliefbase of the motivation elements
     */
    private final class CMotivationBeliefbase extends IBeliefbaseOnDemand<IAgent>
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
        public final ILiteral add( final ILiteral p_literal )
        {
            return p_literal;
        }

        @Override
        public final ILiteral remove( final ILiteral p_literal )
        {
            return p_literal;
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
    private final class CEthnicBeliefbase extends IBeliefbaseOnDemand<IAgent>
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
        public final ILiteral add( final ILiteral p_literal )
        {
            return p_literal;
        }

        @Override
        public final ILiteral remove( final ILiteral p_literal )
        {
            return p_literal;
        }

        @Override
        public final boolean containsLiteral( final String p_key )
        {
            return EEthncity.exist( p_key ) && m_ethnic.containsKey( EEthncity.valueOf( p_key.toUpperCase() ) );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            if ( !this.containsLiteral( p_key ) )
                return Collections.<ILiteral>emptySet();

            final EEthncity l_key =  EEthncity.valueOf( p_key.toUpperCase() );
            return Stream.of( this.literal( l_key, m_ethnic.get( l_key ) ) ).collect( Collectors.toSet() );
        }

        /**
         * creates a literal
         *
         * @param p_key enum term
         * @param p_value value
         * @return literal
         */
        private ILiteral literal( final EEthncity p_key, final Number p_value )
        {
            return CLiteral.from( p_key.name().toLowerCase(), Stream.of( CRawTerm.from( p_value.doubleValue() ) ) );
        }
    }

    /**
     * beliefbase of the attribute elements
     */
    private final class CAttributeBeliefbase extends IBeliefbaseOnDemand<IAgent>
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
        public final Stream<ILiteral> streamLiteral()
        {
            return m_attribute.entrySet().parallelStream()
                              .map( i -> this.literal( i.getKey(), i.getValue() ) );
        }

        @Override
        public final ILiteral add( final ILiteral p_literal )
        {
            return p_literal;
        }

        @Override
        public final ILiteral remove( final ILiteral p_literal )
        {
            return p_literal;
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

        /**
         * creates a literal
         *
         * @param p_key enum term
         * @param p_value value
         * @return literal
         */
        private ILiteral literal( final EAttribute p_key, final Number p_value )
        {
            return CLiteral.from( p_key.name().toLowerCase(), Stream.of( CRawTerm.from( p_value.doubleValue() ) ) );
        }

    }

}
