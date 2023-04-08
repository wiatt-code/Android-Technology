package com.wiatt.engine.database.greenDao.daoImpl

import com.wiatt.engine.database.entity.OwnerDb
import com.wiatt.engine.database.entity.RepoDb
import com.wiatt.engine.database.greenDao.dao.OwnerDbDao

class OwnerDaoImpl constructor(private var ownerDbDao: OwnerDbDao){

    fun insertOwner(ownerDb: OwnerDb): Long {
        var old: OwnerDb? = ownerDbDao.queryBuilder()
                .where(OwnerDbDao.Properties.IdReal.eq(ownerDb.idReal))
                .unique()
        return if (old == null) {
            ownerDbDao.insert(ownerDb)
        } else {
            0L
        }
    }

    fun insertOwner(owners: List<OwnerDb>) {
        for (owner in owners) {
            insertOwner(owner)
        }
    }

    fun deleteOwner(ownerDb: OwnerDb) {
        ownerDbDao.delete(ownerDb)
    }

    fun deleteAll() {
        ownerDbDao.deleteAll()
    }

    fun updateOwnerWithRepos(id: Int, repoDbs: List<RepoDb>) {
        var owner = loadOwnerById(id)
        if (owner != null) {
            owner.repoDbs.addAll(repoDbs)
            ownerDbDao.update(owner)
        }
    }

    fun loadAllOwner(): List<OwnerDb>? {
        return ownerDbDao.loadAll()
    }

    fun loadOwnerById(id: Int): OwnerDb? {
        return ownerDbDao.queryBuilder().where(OwnerDbDao.Properties.Id.eq(id)).unique()
    }
}