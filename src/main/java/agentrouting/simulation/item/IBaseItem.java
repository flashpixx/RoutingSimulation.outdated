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
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.ITerm;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * static wall item
 */
public abstract class IBaseItem implements IItem
{
    /**
     * defines the left upper position (row / column / height in cells / width in cells )
     */
    private final DoubleMatrix1D m_position;
    /**
     * set with preferences
     */
    private final Map<String, ILiteral> m_preferences;
    /**
     * color
     */
    private final Color m_color;
    /**
     * sprite object for painting
     */
    private Sprite m_sprite;

    /**
     *
     * @param p_leftupper left-upper position
     * @param p_rightbottom right-bottom position
     * @param p_color color
     * @param p_preference preference set
     */
    protected IBaseItem( final DoubleMatrix1D p_leftupper, final DoubleMatrix1D p_rightbottom, final Color p_color, final Set<ILiteral> p_preference )
    {
        m_color = p_color;
        m_position = new DenseDoubleMatrix1D( new double[]{
            Math.min( p_leftupper.getQuick( 0 ), p_rightbottom.getQuick( 0 ) ),
            Math.min( p_leftupper.getQuick( 1 ), p_rightbottom.getQuick( 1 ) ),
            Math.abs( p_rightbottom.getQuick( 0 ) - p_leftupper.getQuick( 0 ) ),
            Math.abs( p_rightbottom.getQuick( 1 ) - p_leftupper.getQuick( 1 ) )
        } );
        m_preferences = Collections.unmodifiableMap( p_preference.parallelStream().collect( Collectors.toMap( ITerm::functor, i -> i ) ) );
    }

    @Override
    public final Stream<ILiteral> preferences()
    {
        return m_preferences.values().stream();
    }

    @Override
    public final <N> N preference( final String p_name, final N p_default )
    {
        return CCommon.raw( m_preferences.getOrDefault( p_name, CLiteral.from( p_name, Stream.of( CRawTerm.from( p_default ) ) ) ) );
    }

    @Override
    public final Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public final Sprite spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize )
    {
        // create a colored block of the item
        final Pixmap l_pixmap = new Pixmap( p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
        l_pixmap.setColor( m_color );
        l_pixmap.fillRectangle( 0, 0, p_cellsize * (int) m_position.getQuick( 3 ), p_cellsize * (int) m_position.getQuick( 4 ) );

        // add the square to a sprite (for visualization) and use 100% of cell size
        m_sprite = new Sprite( new Texture( l_pixmap ), 0, 0, p_cellsize, p_cellsize );
        m_sprite.setSize( p_cellsize, p_cellsize );
        m_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );

        return m_sprite;
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }
}
