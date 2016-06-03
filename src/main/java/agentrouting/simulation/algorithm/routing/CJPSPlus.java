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

package agentrouting.simulation.algorithm.routing;

import agentrouting.simulation.IElement;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

import java.util.Collections;
import java.util.List;


/**
 * JPS+ algorithm
 */
public final class CJPSPlus implements IRouting
{
    @Override
    public final IRouting initialize( final ObjectMatrix2D p_objects )
    {
        return this;
    }

    @Override
    public final List<DoubleMatrix1D> route( final ObjectMatrix2D p_objects, final IElement<?> p_element, final DoubleMatrix1D p_target
    )
    {
        return Collections.<DoubleMatrix1D>emptyList();
    }

    @Override
    public final double estimatedtime( final ObjectMatrix2D p_objects, final List<DoubleMatrix1D> p_route, final double p_speed )
    {
        return 0;
    }
}
