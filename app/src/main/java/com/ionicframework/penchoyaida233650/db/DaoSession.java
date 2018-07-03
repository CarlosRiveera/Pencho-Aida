package com.ionicframework.penchoyaida233650.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.ionicframework.penchoyaida233650.Fotos.db.PhotoAlbum;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotoAlbumDao;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotoDetalle;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotoDetalleDao;
import com.ionicframework.penchoyaida233650.Fotos.db.Photoset;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotosetDao;
import com.ionicframework.penchoyaida233650.detallepost.db.DBDuracion;
import com.ionicframework.penchoyaida233650.detallepost.db.DBDuracionDao;
import com.ionicframework.penchoyaida233650.detallepost.db.DBPostPlay;
import com.ionicframework.penchoyaida233650.detallepost.db.DBPostPlayDao;
import com.ionicframework.penchoyaida233650.play.db.DBCancion;
import com.ionicframework.penchoyaida233650.play.db.DBCancionDao;
import com.ionicframework.penchoyaida233650.play.db.DBEstado;
import com.ionicframework.penchoyaida233650.play.db.DBEstadoDao;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.DetallePodcastPrincipalDeportes;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.DetallePodcastPrincipalDeportesDao;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.PostcadDeportes;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.PostcadDeportesDao;
import com.ionicframework.penchoyaida233650.videos.db.DetalleVideo;
import com.ionicframework.penchoyaida233650.videos.db.DetalleVideoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig portadaDaoConfig;
    private final DaoConfig postcastDaoConfig;
    private final DaoConfig detallePodcastDaoConfig;
    private final DaoConfig detallePodcastPrincipalDaoConfig;
    private final DaoConfig busquedasTemporalesDaoConfig;
    private final DaoConfig postcast_PrincipalesDaoConfig;
    private final DaoConfig cancionConfig;
    private final DaoConfig estadoConfig;
    private final DaoConfig postplayConfig;
    private final DaoConfig duracionConfig;
    private final DaoConfig postcadDeportesDaoConfig;
    private final DaoConfig detallePodcastPrincipalDeportesDaoConfig;
    private final DaoConfig photoAlbumConfig;
    private final DaoConfig photoDetalleConfig;
    private final DaoConfig photoSetConfig;
    private final DaoConfig detalleVideoConfig;

    private final PortadaDao portadaDao;
    private final PostcastDao postcastDao;
    private final DetallePodcastDao detallePodcastDao;
    private final DetallePodcastPrincipalDao detallePodcastPrincipalDao;
    private final BusquedasTemporalesDao busquedasTemporalesDao;
    private final Postcast_PrincipalesDao postcast_PrincipalesDao;
    private final DBCancionDao cancionDao;
    private final DBEstadoDao estadoDao;
    private final DBPostPlayDao postPlayDao;
    private final DBDuracionDao duracionDao;
    private final PostcadDeportesDao postcadDeportesDao;
    private final DetallePodcastPrincipalDeportesDao detallePodcastPrincipalDeportesDao;
    private final PhotoAlbumDao photoAlbumDao;
    private final PhotoDetalleDao photoDetalleDao;
    private final PhotosetDao photosetDao;
    private final DetalleVideoDao detalleVideoDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        portadaDaoConfig = daoConfigMap.get(PortadaDao.class).clone();
        portadaDaoConfig.initIdentityScope(type);

        postcastDaoConfig = daoConfigMap.get(PostcastDao.class).clone();
        postcastDaoConfig.initIdentityScope(type);

        detallePodcastDaoConfig = daoConfigMap.get(DetallePodcastDao.class).clone();
        detallePodcastDaoConfig.initIdentityScope(type);

        detallePodcastPrincipalDaoConfig = daoConfigMap.get(DetallePodcastPrincipalDao.class).clone();
        detallePodcastPrincipalDaoConfig.initIdentityScope(type);

        busquedasTemporalesDaoConfig = daoConfigMap.get(BusquedasTemporalesDao.class).clone();
        busquedasTemporalesDaoConfig.initIdentityScope(type);

        postcast_PrincipalesDaoConfig = daoConfigMap.get(Postcast_PrincipalesDao.class).clone();
        postcast_PrincipalesDaoConfig.initIdentityScope(type);

        cancionConfig = daoConfigMap.get(DBCancionDao.class).clone();
        cancionConfig.initIdentityScope(type);

        estadoConfig = daoConfigMap.get(DBEstadoDao.class).clone();
        estadoConfig.initIdentityScope(type);

        postplayConfig = daoConfigMap.get(DBPostPlayDao.class).clone();
        postplayConfig.initIdentityScope(type);

        duracionConfig = daoConfigMap.get(DBDuracionDao.class);
        duracionConfig.initIdentityScope(type);

        postcadDeportesDaoConfig = daoConfigMap.get(PostcadDeportesDao.class).clone();
        postcadDeportesDaoConfig.initIdentityScope(type);

        detallePodcastPrincipalDeportesDaoConfig = daoConfigMap.get(DetallePodcastPrincipalDeportesDao.class).clone();
        detallePodcastPrincipalDeportesDaoConfig.initIdentityScope(type);

        photoAlbumConfig = daoConfigMap.get(PhotoAlbumDao.class).clone();
        photoAlbumConfig.initIdentityScope(type);

        photoDetalleConfig = daoConfigMap.get(PhotoDetalleDao.class).clone();
        photoDetalleConfig.initIdentityScope(type);

        photoSetConfig = daoConfigMap.get(PhotosetDao.class).clone();
        photoSetConfig.initIdentityScope(type);

        detalleVideoConfig = daoConfigMap.get(DetalleVideoDao.class).clone();
        detalleVideoConfig.initIdentityScope(type);

        portadaDao                  = new PortadaDao(portadaDaoConfig, this);
        postcastDao                 = new PostcastDao(postcastDaoConfig, this);
        detallePodcastDao           = new DetallePodcastDao(detallePodcastDaoConfig, this);
        detallePodcastPrincipalDao  = new DetallePodcastPrincipalDao(detallePodcastPrincipalDaoConfig, this);
        busquedasTemporalesDao      = new BusquedasTemporalesDao(busquedasTemporalesDaoConfig, this);
        postcast_PrincipalesDao     = new Postcast_PrincipalesDao(postcast_PrincipalesDaoConfig, this);
        cancionDao                  = new DBCancionDao(cancionConfig, this);
        estadoDao                   = new DBEstadoDao(estadoConfig, this);
        postPlayDao                 = new DBPostPlayDao(postplayConfig, this);
        duracionDao                 = new DBDuracionDao(duracionConfig, this);
        postcadDeportesDao          = new PostcadDeportesDao(postcadDeportesDaoConfig, this);
        detallePodcastPrincipalDeportesDao = new DetallePodcastPrincipalDeportesDao(detallePodcastPrincipalDeportesDaoConfig, this);
        photoAlbumDao               = new PhotoAlbumDao(photoAlbumConfig, this);
        photoDetalleDao             = new PhotoDetalleDao(photoDetalleConfig, this);
        photosetDao                 = new PhotosetDao(photoSetConfig, this);
        detalleVideoDao             = new DetalleVideoDao(detalleVideoConfig, this);



        registerDao(Portada.class, portadaDao);
        registerDao(Postcast.class, postcastDao);
        registerDao(DetallePodcast.class, detallePodcastDao);
        registerDao(DetallePodcastPrincipal.class, detallePodcastPrincipalDao);
        registerDao(BusquedasTemporales.class, busquedasTemporalesDao);
        registerDao(Postcast_Principales.class, postcast_PrincipalesDao);
        registerDao(DBCancion.class, cancionDao);
        registerDao(DBEstado.class, estadoDao);
        registerDao(DBPostPlay.class, postPlayDao);
        registerDao(DBDuracion.class, duracionDao);
        registerDao(PostcadDeportes.class, postcadDeportesDao);
        registerDao(DetallePodcastPrincipalDeportes.class, detallePodcastPrincipalDeportesDao);
        registerDao(PhotoAlbum.class, photoAlbumDao);
        registerDao(PhotoDetalle.class, photoDetalleDao);
        registerDao(Photoset.class, photosetDao);
        registerDao(DetalleVideo.class, detalleVideoDao);
    }
    
    public void clear() {
        portadaDaoConfig.getIdentityScope().clear();
        postcastDaoConfig.getIdentityScope().clear();
        detallePodcastDaoConfig.getIdentityScope().clear();
        detallePodcastPrincipalDaoConfig.getIdentityScope().clear();
        busquedasTemporalesDaoConfig.getIdentityScope().clear();
        postcast_PrincipalesDaoConfig.getIdentityScope().clear();
        cancionConfig.getIdentityScope().clear();
        estadoConfig.getIdentityScope().clear();
        postplayConfig.getIdentityScope().clear();
        duracionConfig.getIdentityScope().clear();
        postcadDeportesDaoConfig.getIdentityScope().clear();
        detallePodcastPrincipalDeportesDaoConfig.getIdentityScope().clear();
        photoAlbumConfig.getIdentityScope().clear();
        photoDetalleConfig.getIdentityScope().clear();
        photoSetConfig.getIdentityScope().clear();
        detalleVideoConfig.getIdentityScope().clear();
    }


    public DetalleVideoDao getDetalleVideoDao() {
        return detalleVideoDao;
    }

    public PhotosetDao getPhotosetDao() {
        return photosetDao;
    }

    public PhotoAlbumDao getPhotoAlbumDao() {
        return photoAlbumDao;
    }

    public PhotoDetalleDao getPhotoDetalleDao() {
        return photoDetalleDao;
    }

    public DetallePodcastPrincipalDeportesDao getDetallePodcastPrincipalDeportesDao() {
        return detallePodcastPrincipalDeportesDao;
    }

    public PostcadDeportesDao getPostcadDeportesDao() {
        return postcadDeportesDao;
    }

    public DBDuracionDao getDuracionDao() {
        return duracionDao;
    }

    public DBPostPlayDao getPostPlayDao() {
        return postPlayDao;
    }

    public DBEstadoDao getEstadoDao() {
        return estadoDao;
    }

    public DBCancionDao getCancionDao() {
        return cancionDao;
    }

    public PortadaDao getPortadaDao() {
        return portadaDao;
    }

    public PostcastDao getPostcastDao() {
        return postcastDao;
    }

    public DetallePodcastDao getDetallePodcastDao() {
        return detallePodcastDao;
    }

    public DetallePodcastPrincipalDao getDetallePodcastPrincipalDao() {
        return detallePodcastPrincipalDao;
    }

    public BusquedasTemporalesDao getBusquedasTemporalesDao() {
        return busquedasTemporalesDao;
    }

    public Postcast_PrincipalesDao getPostcast_PrincipalesDao() {
        return postcast_PrincipalesDao;
    }

}
