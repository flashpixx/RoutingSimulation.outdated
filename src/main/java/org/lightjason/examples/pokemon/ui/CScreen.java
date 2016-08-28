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

package org.lightjason.examples.pokemon.ui;

import org.lightjason.examples.pokemon.CConfiguration;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.text.MessageFormat;
import java.util.List;


/**
 * screen entry point, all graphical components
 * based on the LibGDX library
 *
 * @note with "s" a screenshot can be created
 * @warning rendering elements must be set within the create call for avoid instantiation error
 * @see https://libgdx.badlogicgames.com/
 * @see https://github.com/libgdx/libgdx/wiki/Tile-maps
 * @see http://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx
 */
public final class CScreen extends ApplicationAdapter implements InputProcessor
{
    /**
     * environment tilemap reference
     */
    private final ITileMap m_environment;
    /**
     * sprite list
     */
    private final List<? extends ISprite> m_sprites;
    /**
     * screenshot
     */
    private final Triple<String, String, Integer> m_screenshot;
    /**
     * last camera position
     */
    private final Vector3 m_lastTouch = new Vector3();
    /**
     * show status visibility
     */
    private final boolean m_statusvisibility;
    /**
     * camera definition
     */
    private OrthographicCamera m_camera;
    /**
     * font object
     */
    private BitmapFont m_font;
    /**
     * batch painting
     */
    private SpriteBatch m_batch;
    /**
     * renderer
     */
    private OrthogonalTiledMapRenderer m_render;
    /**
     * simulation step
     */
    private int m_iteration;
    /**
     * flag for disposed screen
     */
    private volatile boolean m_isdisposed;
    /**
     * flag for taking a screenshot
     */
    private volatile boolean m_screenshottake;



    /**
     * ctor
     *
     * @param p_sprites list with executables
     * @param p_environment environment reference
     * @param p_screenshot screenshot configuration
     * @param p_statusvisibility status visibility
     */
    public CScreen( final List<? extends ISprite> p_sprites, final ITileMap p_environment, final Triple<String, String, Integer> p_screenshot,
                    final boolean p_statusvisibility
    )
    {
        m_environment = p_environment;
        m_sprites = p_sprites;
        m_screenshot = p_screenshot;
        m_statusvisibility = p_statusvisibility;
    }

    @Override
    public final void create()
    {
        // create orthogonal camera perspective
        final float l_unit = 1.0f / m_environment.cellsize();

        // create execution structure for painting
        m_batch = new SpriteBatch();
        //m_font = CScreen.font( CCommon.PACKAGEPATH + "Hanken-Light.ttf", 12, Color.WHITE );

        // create environment view and put all objects in it
        m_render = new OrthogonalTiledMapRenderer( m_environment.map(), l_unit, m_batch );

        m_camera = new OrthographicCamera( m_environment.column(), m_environment.row() );
        m_camera.setToOrtho( false, m_environment.column() * l_unit, m_environment.row() * l_unit );
        m_camera.position.set( m_environment.column() / 2f, m_environment.row() / 2f, 0 );
        m_camera.zoom = m_environment.cellsize();

        m_sprites.forEach( i -> i.spriteinitialize( m_environment.row(), m_environment.column(), m_environment.cellsize(), l_unit ) );
        m_render.setView( m_camera );

        // set input processor
        Gdx.input.setInputProcessor( this );
    }

    /**
     * creates a font object based on a TTF file
     *
     * @param p_path string path of TTF file
     * @param p_size font size
     * @param p_color font color
     * @return font object
     */
    private static BitmapFont font( final String p_path, final int p_size, final Color p_color )
    {
        final FreeTypeFontGenerator l_fontgenerator = new FreeTypeFontGenerator( Gdx.files.internal(  p_path ) );
        final FreeTypeFontGenerator.FreeTypeFontParameter l_fontparameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        l_fontparameter.size = p_size;
        l_fontparameter.color = p_color;

        final BitmapFont l_font = l_fontgenerator.generateFont( l_fontparameter );
        l_fontgenerator.dispose();

        return l_font;
    }

    @Override
    public final void render()
    {
        // camera update must be the first for reaction on input device events
        m_camera.update();

        // create black background
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // environment tilemap painting
        m_render.setView( m_camera );
        m_render.render();

        // object sprite painting
        m_batch.setProjectionMatrix( m_camera.combined );
        m_batch.begin();
        m_sprites.forEach( i -> i.sprite().draw( m_batch ) );

        if ( ( m_font != null ) && m_statusvisibility )
            m_font.draw(
                m_batch,
                MessageFormat.format(
                    "Agents {0} - Environment [{1}x{2}] - FPS: {3} - Iteration {4}",
                    m_sprites.size(),
                    m_environment.row(),
                    m_environment.column(),
                    m_iteration,
                    Gdx.graphics.getFramesPerSecond()
                ),
                1, 5
            );

        m_batch.end();

        // take screenshot at the rendering end
        this.screenshot();
    }

    @Override
    public final void dispose()
    {
        // dispose flag is set to stop parallel simulation execution outside the screen

        m_isdisposed = true;
        if ( m_font != null )
            m_font.dispose();
        m_batch.dispose();
        m_render.dispose();
        super.dispose();
    }

    /**
     * returns if the screen is disposed
     *
     * @return disposed flag
     */
    public final boolean isDisposed()
    {
        return m_isdisposed;
    }


    /**
     * sets the iteration value
     *
     * @param p_iteration iteration value
     * @return screen reference
     */
    public final CScreen iteration( final int p_iteration )
    {
        m_iteration = p_iteration;

        // screenshot-take flag set
        if ( !m_screenshottake )
            m_screenshottake = ( !m_screenshot.getLeft().isEmpty() ) && ( !m_screenshot.getMiddle().isEmpty() )
                               && ( m_screenshot.getRight() > 0 ) && ( p_iteration % m_screenshot.getRight() == 0 );
        return this;
    }

    @Override
    public final boolean keyDown( final int p_key )
    {
        // if "s" key pressed, create a screenshot
        if ( p_key == 47 )
            m_screenshottake = ( !m_screenshot.getLeft().isEmpty() ) && ( !m_screenshot.getMiddle().isEmpty() );

        return false;
    }

    @Override
    public final boolean keyUp( final int p_key )
    {
        return false;
    }

    @Override
    public final boolean keyTyped( final char p_char )
    {
        return false;
    }

    @Override
    public final boolean touchDown( final int p_screenx, final int p_screeny, final int p_pointer, final int p_button )
    {
        m_lastTouch.set( p_screenx, p_screeny, 0 );
        return false;
    }

    @Override
    public final boolean touchUp( final int p_xposition, final int p_yposition, final int p_pointer, final int p_button )
    {
        return false;
    }

    @Override
    public final boolean touchDragged( final int p_screenx, final int p_screeny, final int p_pointer )
    {
        m_camera.translate(
            new Vector3().set( p_screenx, p_screeny, 0 )
                         .sub( m_lastTouch )
                         .scl( -CConfiguration.INSTANCE.dragspeed(), CConfiguration.INSTANCE.dragspeed(), 0 )
                         .scl( m_camera.zoom )
        );
        m_lastTouch.set( p_screenx, p_screeny, 0 );
        return false;
    }

    @Override
    public final boolean mouseMoved( final int p_xposition, final int p_yposition )
    {
        return false;
    }

    @Override
    public final boolean scrolled( final int p_amount )
    {
        m_camera.zoom *= p_amount > 0
                         ? 1 + CConfiguration.INSTANCE.zoomspeed()
                         : 1 - CConfiguration.INSTANCE.zoomspeed();
        return false;
    }

    /**
     * takes a screenshot
     *
     * @return self reference
     */
    private CScreen screenshot()
    {
        if ( !m_screenshottake )
            return this;

        m_screenshottake = false;
        final byte[] l_pixels = ScreenUtils.getFrameBufferPixels( 0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true );
        final Pixmap l_pixmap = new Pixmap( Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888 );

        BufferUtils.copy( l_pixels, 0, l_pixmap.getPixels(), l_pixels.length );
        PixmapIO.writePNG(
            new FileHandle( MessageFormat.format( m_screenshot.getLeft(), String.format( m_screenshot.getMiddle(), m_iteration ) ) + ".png" ),
            l_pixmap
        );
        l_pixmap.dispose();
        return this;
    }

}
