package com.wiatt.engine.database.greenDao.daoImpl

import com.wiatt.engine.EngineApplication
import com.wiatt.engine.database.greenDao.dao.LicenseDbDao
import com.wiatt.engine.database.greenDao.dao.OwnerDbDao
import com.wiatt.engine.database.greenDao.dao.RepoDbDao

class DaoFactory private constructor(){

    var daoSession = EngineApplication.mApplication?.daoSession
    val ownerDaoImpl: OwnerDaoImpl? = null
        get() {
            return if (field == null) {
                val ownerDao: OwnerDbDao? = daoSession?.ownerDbDao
                if (ownerDao != null) {
                    OwnerDaoImpl(ownerDao)
                } else {
                    null
                }
            } else {
                field
            }
        }

    val repoDaoImpl: RepoDaoImpl? = null
        get() {
            return if (field == null) {
                val repoDao: RepoDbDao? = daoSession?.repoDbDao
                if (repoDao != null) {
                    RepoDaoImpl(repoDao)
                } else {
                    null
                }
            } else {
                field
            }
        }

    val licenseDaoImpl: LicenseDaoImpl? = null
        get() {
            return if (field == null) {
                val licenseDao: LicenseDbDao? = daoSession?.licenseDbDao
                if (licenseDao != null) {
                    LicenseDaoImpl(licenseDao)
                } else {
                    null
                }
            } else {
                field
            }
        }

    companion object {
        val instance: DaoFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DaoFactory()
        }
    }
}