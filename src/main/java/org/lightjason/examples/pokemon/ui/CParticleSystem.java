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

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import org.lightjason.examples.pokemon.CCommon;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * particle effect class
 *
 * @see https://github.com/libgdx/libgdx/wiki/3D-Particle-Effects
 */
public final class CParticleSystem
{
    /**
     * singleton instance
     */
    public static final CParticleSystem INSTANCE = new CParticleSystem();
    /**
     * filename of the icon
     */
    private static final String PARTICLEFILENAME = CCommon.PACKAGEPATH + "data/particle/{0}/{0}.efx";
    /**
     * map with existing particel effects
     */
    private final Map<String, ParticleEffect> m_effects = new ConcurrentHashMap<>();
    /**
     * set with current active emitters
     */
    private final Collection<ParticleEffect> m_active = new CopyOnWriteArrayList<>();


    /**
     * ctor
     */
    private CParticleSystem()
    {
    }


    /**
     * runs the particel effect immediatly
     *
     * @param p_name name effect
     * @param p_position emitter position
     * @return self reference
     */
    public final CParticleSystem execute( final String p_name, final DoubleMatrix1D p_position )
    {
        final ParticleEffect l_base = m_effects.get( CParticleSystem.name( p_name ) );
        if ( l_base == null )
            throw new RuntimeException( MessageFormat.format( "particle effect [{0}] not found", p_name ) );

        final ParticleEffect l_effect = new ParticleEffect( l_base );
        l_effect.setPosition( (float) p_position.getQuick( 1 ), (float) p_position.getQuick( 0 ) );
        m_active.add( l_effect );
        l_effect.reset();
        l_effect.start();
        return this;
    }


    /**
     * creates the particle system
     *
     * @param p_unit scaling factor
     * @return self reference
     * @see http://stackoverflow.com/questions/25648386/android-libgdx-3d-particle-system-not-working-billboard
     * @see http://www.gamedev.net/page/share.php/_/creative/visual-arts/make-a-particle-explosion-effect-r2701
     * @see https://www.youtube.com/watch?v=HXYqg3G5kCo
     * @see http://codepoke.net/2011/12/27/opengl-libgdx-laser-fx/
     * @see http://stackoverflow.com/questions/14839648/libgdx-particleeffect-rotation
     * @see https://github.com/libgdx/libgdx/wiki/2D-ParticleEffects
     * @see https://github.com/libgdx/libgdx/wiki/3D-Particle-Effects
     * @see http://stackoverflow.com/questions/12261439/assetmanager-particleeffectloader-of-libgdx-android
     * @see https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/ParticleEmitterTest.java
     */
    public final CParticleSystem initialize( final String p_name, final float p_unit )
    {
        final String l_name = CParticleSystem.name( p_name );
        if ( l_name.isEmpty() )
            throw new RuntimeException( "particle system name need not to be empty" );
        if ( m_effects.containsKey( l_name ) )
            return this;

        // create particle system
        final FileHandle l_file = Gdx.files.internal( MessageFormat.format( PARTICLEFILENAME, l_name ) );

        final ParticleEffect l_effect = new ParticleEffect();
        l_effect.load( l_file, l_file.parent() );
        l_effect.scaleEffect( p_unit );
        l_effect.allowCompletion();

        m_effects.putIfAbsent( l_name, l_effect );
        return this;
    }


    /**
     * returns a stream over all emitter
     *
     * @return emitter stream
     */
    public final Stream<ParticleEffect> emitter()
    {
        return m_active.stream().filter( i -> !i.isComplete() );
    }


    /**
     * removes completed emitters
     *
     * @return self reference
     */
    public final CParticleSystem clean()
    {
        m_active.removeAll( m_active.parallelStream()
                                    .filter( ParticleEffect::isComplete )
                                    .map( i -> {
                                        i.dispose();
                                        return i;
                                    } )
                                    .collect( Collectors.toList() ) );
        return this;
    }

    /**
     * returns name of a particle system
     *
     * @param p_name name
     * @return formated name
     */
    private static String name( final String p_name )
    {
        return p_name.trim().toLowerCase();
    }


}
