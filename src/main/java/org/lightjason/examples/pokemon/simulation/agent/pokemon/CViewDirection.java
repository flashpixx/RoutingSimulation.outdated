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
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.examples.pokemon.simulation.CMath;


/**
 * class to define the view direction structure
 */
final class CViewDirection
{
    /**
     * error on structure calculaton
     */
    private final boolean m_error;
    /**
     * view range (radius)
     */
    private final int m_radius;
    /**
     * view angle
     */
    private final double m_angle;
    /**
     * vector of view direction
     */
    private final DoubleMatrix1D m_direction;

    /**
     * ctor
     *
     * @param p_error error flag
     * @param p_radius view radius
     * @param p_angle view angle
     * @param p_direction direction vector
     */
    private CViewDirection( final boolean p_error, final int p_radius, final double p_angle, final DoubleMatrix1D p_direction )
    {
        m_error = p_error;
        m_radius = p_radius;
        m_angle = p_angle;
        m_direction = p_direction;
    }

    /**
     * returns calculation error
     *
     * @return error flag
     */
    boolean error()
    {
        return m_error;
    }

    /**
     * returns view radius
     *
     * @return radius
     */
    int radius()
    {
        return m_radius;
    }

    /**
     * returns view angle
     *
     * @return angle
     */
    double angle()
    {
        return m_angle;
    }

    /**
     * view direction vector
     *
     * @return vector
     */
    DoubleMatrix1D direction()
    {
        return m_direction;
    }


    /**
     * factory to generate view point
     *
     * @param p_angle view angle
     * @param p_radius view radius
     * @param p_position current object position
     * @param p_goal current goal position
     * @return view direction object
     */
    static CViewDirection generate( final double p_angle, final int p_radius, final DoubleMatrix1D p_position, final DoubleMatrix1D p_goal )
    {
        // rotate view-range into the direction of the next goal-position
        // calculate angle from current position to goal-position
        final Pair<Double, Boolean> l_direction = CMath.angle(
            new DenseDoubleMatrix1D( p_goal.toArray() )
                .assign( p_position, Functions.minus ),
            new DenseDoubleMatrix1D( new double[]{0, p_radius} )
        );

        // rotate current position of the agent into view direction
        return new CViewDirection( l_direction.getRight(), p_radius, p_angle, CMath.ALGEBRA.mult( CMath.rotationmatrix( l_direction.getLeft() ), p_position ) );
    }
}
