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

package org.lightjason.examples.pokemon.simulation.item;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.ITerm;

import java.util.Collections;
import java.util.List;
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
    private final Set<ILiteral> m_attribute;
    /**
     * color
     */
    private final Color m_color;
    /**
     * sprite object for painting
     */
    private Sprite m_sprite;


    /**
     * @param p_leftupper left-upper position
     * @param p_rightbottom right-bottom position
     * @param p_color color
     */
    protected IBaseItem( final List<Integer> p_leftupper, final List<Integer> p_rightbottom, final String p_color )
    {
        this( p_leftupper, p_rightbottom, p_color, Collections.emptyMap() );
    }


    /**
     * @param p_leftupper left-upper position
     * @param p_rightbottom right-bottom position
     * @param p_color color
     * @param p_attribute attribute map
     * @todo add preference structure
     */
    protected IBaseItem( final List<Integer> p_leftupper, final List<Integer> p_rightbottom, final String p_color, final Map<String, ?> p_attribute )
    {
        if ( p_color.isEmpty() )
            throw new RuntimeException( "color need not to be empty" );
        if ( ( p_leftupper == null ) || ( p_leftupper.size() != 2 ) )
            throw new RuntimeException( "left-upper corner is not set" );
        if ( ( p_rightbottom == null ) || ( p_rightbottom.size() != 2 ) )
            throw new RuntimeException( "right-bottom corner is not set" );

        m_color = Color.valueOf( p_color );
        m_position = new DenseDoubleMatrix1D( new double[]{
            Math.min( p_leftupper.get( 0 ), p_rightbottom.get( 0 ) ),
            Math.min( p_leftupper.get( 1 ), p_rightbottom.get( 1 ) ),

            Math.abs( p_rightbottom.get( 0 ) - p_leftupper.get( 0 ) ),
            Math.abs( p_rightbottom.get( 1 ) - p_leftupper.get( 1 ) )
        } );

        this.m_attribute = Collections.unmodifiableSet(
            Stream.concat(
                Stream.of(
                    CLiteral.from( "topleft",  IBaseItem.streamposition( m_position.getQuick( 0 ), m_position.getQuick( 1 ) ) ),
                    CLiteral.from( "bottomright",
                                   IBaseItem.streamposition(
                                        m_position.getQuick( 0 ) + m_position.getQuick( 2 ),
                                        m_position.getQuick( 1 ) + m_position.getQuick( 3 )
                                   )
                    )
                ),

                p_attribute.entrySet().parallelStream().map( i -> CLiteral.from( i.getKey().toLowerCase(), Stream.of( CRawTerm.from( i.getValue() ) ) ) )
            )
            .collect( Collectors.toSet() ) );
    }

    /**
     * creates a stream with position data
     *
     * @param p_ypos y-position
     * @param p_xpos x-position
     * @return literal stream
     */
    @SuppressWarnings( "unchecked" )
    private static Stream<ITerm> streamposition( final double p_ypos, final double p_xpos )
    {
        return Stream.of(
            CLiteral.from( "y", Stream.of( CRawTerm.from( (int) p_ypos ) ) ),
            CLiteral.from( "x", Stream.of( CRawTerm.from( (int) p_xpos ) ) )
        );
    }

    @Override
    public final Stream<ILiteral> attribute()
    {
        return m_attribute.parallelStream();
    }

    @Override
    public final Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public final void spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit )
    {
        // variables are defined as size1 = x-size & size2 = y-size
        final float l_size1 = p_cellsize * (int) m_position.getQuick( 3 );
        final float l_size2 = p_cellsize * (int) m_position.getQuick( 2 );

        // create a colored block of the item
        final Pixmap l_pixmap = new Pixmap( p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
        l_pixmap.setColor( m_color );
        l_pixmap.fillRectangle( 0, 0, (int) l_size2, (int) l_size1 );

        // add the square to a sprite (for visualization) and use 100% of cell size
        m_sprite = new Sprite( new Texture( l_pixmap ), 0, 0, (int) l_size2, (int) l_size1 );
        m_sprite.setSize( l_size1, l_size2 );
        m_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );
        m_sprite.setPosition( (float) m_position.get( 1 ), (float) m_position.get( 0 ) );
        m_sprite.setScale( p_unit );
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }
}
