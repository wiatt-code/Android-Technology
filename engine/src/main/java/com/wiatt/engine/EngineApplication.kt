package com.wiatt.engine

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.wiatt.common.CommonApplication
import com.wiatt.engine.database.greenDao.dao.DaoMaster
import com.wiatt.engine.database.greenDao.dao.DaoSession

open class EngineApplication: CommonApplication() {

    var daoSession: DaoSession? = null
        private set

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        initGreenDao()
    }

    fun initGreenDao() {
        var helper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(this, "AtDb.db")
        var db: SQLiteDatabase = helper.writableDatabase
        var daoMaster: DaoMaster = DaoMaster(db)
        daoSession = daoMaster.newSession()
    }

    companion object {
        var mApplication: EngineApplication? = null
    }
}