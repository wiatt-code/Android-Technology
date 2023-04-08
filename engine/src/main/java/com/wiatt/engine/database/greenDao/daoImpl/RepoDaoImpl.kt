package com.wiatt.engine.database.greenDao.daoImpl

import com.wiatt.engine.database.entity.LicenseDb
import com.wiatt.engine.database.entity.RepoDb
import com.wiatt.engine.database.greenDao.dao.LicenseDbDao
import com.wiatt.engine.database.greenDao.dao.RepoDbDao
import org.greenrobot.greendao.query.QueryBuilder

class RepoDaoImpl constructor(private var repoDbDao: RepoDbDao){

    fun insertRepo(repoDb: RepoDb): Long {
        var old: RepoDb? = repoDbDao.queryBuilder()
                .where(RepoDbDao.Properties.IdReal.eq(repoDb.idReal))
                .unique()
        return if (old == null) {
            repoDbDao.insert(repoDb)
        } else {
            0L
        }
    }

    fun insertRepo(repos: List<RepoDb>) {
        for (repo in repos) {
            insertRepo(repo)
        }
    }

    fun deleteRepo(repo: RepoDb) {
        repoDbDao.delete(repo)
    }

    fun deleteAll() {
        repoDbDao.deleteAll()
    }

    fun loadReposByOwner(ownerId: Int): List<RepoDb> {
        return repoDbDao.queryBuilder()
            .where(RepoDbDao.Properties.OwnerId.eq(ownerId))
            .list()
    }

    /**
     * 多表查询
     */
    fun loadReposByLicenseKey(licenseKey: String): List<RepoDb> {
        val qb: QueryBuilder<RepoDb> = repoDbDao.queryBuilder()
        qb.join(LicenseDb::class.java, LicenseDbDao.Properties.Node_id)
            .where(LicenseDbDao.Properties.Key.eq(licenseKey))
        return qb.list()
    }
}