package com.qbao.xproject.app.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.qbao.xproject.app.entity.ResponseEntity;

import com.qbao.xproject.app.db.ResponseEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig responseEntityDaoConfig;

    private final ResponseEntityDao responseEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        responseEntityDaoConfig = daoConfigMap.get(ResponseEntityDao.class).clone();
        responseEntityDaoConfig.initIdentityScope(type);

        responseEntityDao = new ResponseEntityDao(responseEntityDaoConfig, this);

        registerDao(ResponseEntity.class, responseEntityDao);
    }
    
    public void clear() {
        responseEntityDaoConfig.clearIdentityScope();
    }

    public ResponseEntityDao getResponseEntityDao() {
        return responseEntityDao;
    }

}