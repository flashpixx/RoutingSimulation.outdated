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

import agentrouting.CConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.language.CCommon;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * enum to define all sprites by name
 */
public enum EPokemon
{
    ABRA( 3 ),
    AMONITAS( 2 ),
    BISASAM( 3 ),
    BLUZUK( 2 ),
    DIGDA( 2 ),
    DITTO( 1 ),
    DODU( 2 ),
    DRATINI( 2 ),
    ENTON( 2 ),
    EVOLI( 4 ),
    FLEGMON( 2 ),
    FLUFFELUFF( 2 ),
    FUKANO( 2 ),
    GLUMANDA( 3 ),
    GOLDINI( 2  ),
    HABITAK( 2 ),
    HORNLIU( 2 ),
    JURON( 2 ),
    KARPADOR( 2 ),
    KLEINSTEIN( 3 ),
    KONFENSA( 3 ),
    KRABBY( 2 ),
    LAPRAS( 1 ),
    MACHOLLO( 3 ),
    MAGNETILO( 2 ),
    MAMPFAXO( 1 ),
    MAUZI( 2 ),
    MENKI( 2 ),
    MUSCHAS( 2 ),
    MYPRAPLA( 3 ),
    NEBULAK( 3 ),
    NIDORAN_FEMALE( 3 ),
    NIDORAN_MALE( 3 ),
    ONIX( 1 ),
    OWEI( 2 ),
    PARAS( 2 ),
    PICHU( 2 ),
    PII( 2 ),
    PONITA( 2 ),
    PORENTA( 1 ),
    QUAPSEL( 3 ),
    RATTFRATZ( 2 ),
    RAUPY( 3 ),
    RETTAN( 2 ),
    RIHORN( 2 ),
    SANDAN( 2 ),
    SCHIGGY( 3 ),
    SEEPER( 2 ),
    SLEIMA( 2 ),
    SMOGON( 2 ),
    STERNDU( 2 ),
    TANGELA( 2 ),
    TAUBSI( 3 ),
    TENTACHA( 2 ),
    TRAGOSSO( 2 ),
    TRAUMATO( 2 ),
    VOLTOBAL( 2 ),
    VULPIX( 2 ),
    WONNEIRA( 2 ),
    ZUBAT( 2 );


    /**
     * logger
     */
    private static final Logger LOGGER = Logger.getLogger( EPokemon.class.getName() );
    /**
     * number of icons
     */
    private final int m_icons;

    /**
     * sprite list
     */
    private List<Sprite> m_sprites;



    /**
     * ctor
     *
     * @param p_icons number of icons
     */
    EPokemon( final int p_icons )
    {
        m_icons = p_icons;
    }

    /**
     * returns the number of sprites
     *
     * @return sprite number
     */
    public final int size()
    {
        return m_icons;
    }

    /**
     * initialize sprites
     * @param p_cellsize cell size
     * @param p_unit unit scale
     */
    public final synchronized Sprite initialize( final int p_cellsize, final float p_unit )
    {
        if ( m_sprites != null )
            return m_sprites.get( 0 );

        m_sprites = Collections.unmodifiableList(
            IntStream.range( 0, m_icons )
                     .mapToObj(
                         i -> {
                             try
                             {
                                 return new Sprite(
                                     new Texture(
                                         Gdx.files.absolute(
                                             Paths.get(
                                                agentrouting.CCommon.getResourceURL(
                                                    MessageFormat.format( "agentrouting/sprites/{0}_{1}.png", this.name().toLowerCase(), i )
                                                ).toURI()
                                             ).toFile().toString()
                                         )
                                     ),
                                     0, 0, p_cellsize, p_cellsize
                                 );
                             }
                             catch ( final MalformedURLException | URISyntaxException l_exception )
                             {
                                 LOGGER.warning( l_exception.toString() );
                                 return null;
                             }
                         }
                     )
                     .filter( i -> i != null )
                     .map( i -> {
                         i.setSize( 0.9f * p_cellsize, 0.9f * p_cellsize );
                         i.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );
                         i.setScale( p_unit );
                         return i;
                     } )
                     .collect( Collectors.toList() )
        );

        if ( ( m_sprites.isEmpty() ) || ( m_icons != m_sprites.size() ) )
            throw new RuntimeException( MessageFormat.format( "sprite [{0}] cannot initialize", this ) );

        return m_sprites.get( 0 );
    }

    /**
     * returns pokemon sprite
     *
     * @param p_index index number
     * @return sprite
     */
    public final synchronized Sprite sprite( final int p_index )
    {
        return m_sprites.get( p_index );
    }

}
