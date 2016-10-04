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
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import org.lightjason.examples.pokemon.CCommon;
import org.lightjason.examples.pokemon.CConfiguration;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


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
    private static final String PARTICLEFILENAME = CCommon.PACKAGEPATH + "data/particle/{0}.efx";
    /**
     * map with existing particel effects
     */
    private final Map<String, ParticleEffect> m_effects = new HashMap<>();
    /**
     * particel system
     */
    private ParticleSystem m_system;


    /**
     * ctor
     */
    private CParticleSystem()
    {}


    /**
     * runs the particel effect immediatly
     *
     * @param p_name name effect
     * @param p_position emitter position
     * @return self reference
     */
    public final CParticleSystem execute( final String p_name, final DoubleMatrix1D p_position )
    {
        return this;
    }


    /**
     * creates the particle system
     *
     * @see http://stackoverflow.com/questions/25648386/android-libgdx-3d-particle-system-not-working-billboard
     * @see http://www.gamedev.net/page/share.php/_/creative/visual-arts/make-a-particle-explosion-effect-r2701
     * @see https://www.youtube.com/watch?v=HXYqg3G5kCo
     * @see http://codepoke.net/2011/12/27/opengl-libgdx-laser-fx/
     * @see http://stackoverflow.com/questions/14839648/libgdx-particleeffect-rotation
     * @see https://github.com/libgdx/libgdx/wiki/2D-ParticleEffects
     * @see https://github.com/libgdx/libgdx/wiki/3D-Particle-Effects
     */
    public final void create()
    {
        m_system = new ParticleSystem();

        //final ParticleEffectLoader.ParticleEffectLoadParameter l_parameter = new ParticleEffectLoader.ParticleEffectLoadParameter( m_system.getBatches() );
        //final ParticleEffectLoader l_loader = new ParticleEffectLoader( new InternalFileHandleResolver() );

        //m_effects.put( "firepsin", this.load( "firespin" ) );
    }


    /**
     * returns particle system instance
     * for rendering
     *
     * @return particle system instance
     */
    public final ParticleSystem get()
    {
        return m_system;
    }

    /**
     * loads a particle effect
     *
     * @param p_name filename of the configuration
     * @return particle effect
     * @see http://stackoverflow.com/questions/12261439/assetmanager-particleeffectloader-of-libgdx-android
     */
    private ParticleEffect load( final String p_name )
    {
        final String l_filename = MessageFormat.format( PARTICLEFILENAME, p_name );

        final ParticleEffectLoader.ParticleEffectLoadParameter l_parameter = new ParticleEffectLoader.ParticleEffectLoadParameter( m_system.getBatches() );
        CConfiguration.INSTANCE.asset().load( l_filename, ParticleEffect.class, l_parameter );
        CConfiguration.INSTANCE.asset().finishLoading();

        return CConfiguration.INSTANCE.asset().get( l_filename );
    }


}
