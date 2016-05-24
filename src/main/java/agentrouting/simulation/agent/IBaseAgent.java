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


package agentrouting.simulation.agent;

import agentrouting.simulation.IBaseElement;
import agentrouting.simulation.IEnvironment;
import agentrouting.simulation.algorithm.force.IForce;
import cern.colt.matrix.tint.IntMatrix1D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import lightjason.agentspeak.configuration.IAgentConfiguration;

import java.text.MessageFormat;
import java.util.logging.Logger;


/**
 * agent class for modelling individual behaviours
 */
public abstract class IBaseAgent extends IBaseElement<IAgent> implements IAgent
{
    /**
     * color
     */
    private final Color m_color;



    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_force force model
     * @param p_position initialize position
     * @param p_color color string in RRGGBBAA
     */
    protected IBaseAgent( final IEnvironment p_environment, final IAgentConfiguration p_agentconfiguration,
                          final IForce p_force, final IntMatrix1D p_position, final String p_color
    )
    {
        super( p_environment, p_agentconfiguration, p_force, p_position );
        m_color = Color.valueOf( p_color );
    }

    @Override
    protected final Sprite visualization( final int p_rows, final int p_columns, final int p_cellsize )
    {
        // create a colored sequare for the agent
        final Pixmap l_pixmap = new Pixmap( p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
        l_pixmap.setColor( m_color );
        l_pixmap.fillRectangle( 0, 0, p_cellsize, p_cellsize );

        // add the square to a sprite (for visualization) and scale it to 80% of cell size
        final Sprite l_sprite = new Sprite( new Texture( l_pixmap ), 0, 0, p_cellsize, p_cellsize );
        l_sprite.setSize( 0.9f * p_cellsize, 0.9f * p_cellsize );
        l_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );

        return l_sprite;
    }

}