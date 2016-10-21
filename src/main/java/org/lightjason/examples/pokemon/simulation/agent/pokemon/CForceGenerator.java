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

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.jet.math.Functions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.examples.pokemon.simulation.CMath;
import org.lightjason.examples.pokemon.simulation.IElement;
import org.lightjason.examples.pokemon.simulation.algorithm.force.EForceModelFactory;
import org.lightjason.examples.pokemon.simulation.environment.EDirection;

import java.util.stream.Stream;


/**
 * class to generate force literal
 */
final class CForceGenerator
{
    /**
     * functor name
     */
    private static final String FUNCTOR = "force";
    /**
     * source element
     */
    private final IElement m_source;
    /**
     * element map for direction
     */
    private final SetMultimap<EDirection, IElement> m_direction = HashMultimap.create();

    /**
     * ctor
     * @param p_source source element
     */
    private CForceGenerator( final IElement p_source )
    {
        m_source = p_source;
    }

    /**
     * pass a literal
     *
     * @param p_viewdirection view direction
     * @param p_element element object
     * @param p_elementposition position of the element within the viewrange
     * @return literal
     */
    final IElement push( final DoubleMatrix1D p_viewdirection, final IElement p_element, final DoubleMatrix1D p_elementposition )
    {
        if ( p_element != null )
            m_direction.put(
                EDirection.byAngle(
                    Math.toDegrees(
                        CMath.angle(
                            p_viewdirection,
                            new DenseDoubleMatrix1D( p_elementposition.toArray() )
                                .assign( m_source.position(), Functions.minus )
                        ).getKey() )
                ),
                p_element
            );

        return p_element;
    }

    /**
     * returns the build literal
     *
     * @return literal
     */
    final ILiteral get()
    {
        return CLiteral.from(
            FUNCTOR,
            m_direction.asMap().entrySet().stream()
                       .map( i -> CLiteral.from(
                                     i.getKey().name().toLowerCase(),
                                     Stream.of( CRawTerm.from( EForceModelFactory.DEFAULT.get().apply( m_source, i.getValue().stream() ) ) )
                                  )
                       )
        );
    }

    /**
     * factory
     *
     * @param p_source source
     * @return generator reference
     */
    static CForceGenerator generate( final IElement p_source )
    {
        return new CForceGenerator( p_source );
    }

}
