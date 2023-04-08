package com.wiatt.simpledemo

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.wiatt.simpledemo.myGreendao.dao.DaoMaster
import com.wiatt.simpledemo.myGreendao.dao.DaoSession

class SimpleDemoApplication: Application() {

    lateinit var daoSession: DaoSession
        private set

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        initGreenDao()
    }

    private fun initGreenDao() {
        var helper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(this, "aserbao.db")
        var db: SQLiteDatabase = helper.writableDatabase
        var daoMaster: DaoMaster = DaoMaster(db)
        daoSession = daoMaster.newSession()
    }

    companion object {
        var mApplication: SimpleDemoApplication? = null
    }
}