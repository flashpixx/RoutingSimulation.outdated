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

package agentrouting.simulation.item;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.Color;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Set;


/**
 * static elements
 */
public final class CStatic extends IBaseItem
{
    /**
     * ctor
     *
     * @param p_leftupper left-upper position
     * @param p_rightbottom right-bottom position
     * @param p_color color
     * @param p_preference preference set
     */
    public CStatic( final DoubleMatrix1D p_leftupper, final DoubleMatrix1D p_rightbottom,
                       final Color p_color,
                       final Set<ILiteral> p_preference
    )
    {
        super( p_leftupper, p_rightbottom, p_color, p_preference );
    }

    @Override
    public final boolean whipeable()
    {
        return false;
    }
}
