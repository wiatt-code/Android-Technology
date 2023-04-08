package com.wiatt.engine.database.greenDao.daoImpl

import com.wiatt.engine.database.entity.LicenseDb
import com.wiatt.engine.database.greenDao.dao.LicenseDbDao

class LicenseDaoImpl constructor(private var licenseDao: LicenseDbDao){

    fun insertLicense(license: LicenseDb): Long{
        var old: LicenseDb? = licenseDao.queryBuilder()
                .where(LicenseDbDao.Properties.Node_id.eq(license.node_id))
                .unique()
        return if (old == null) {
            licenseDao.insert(license)
        } else {
            0L
        }
    }

    fun insertLicense(licenses: List<LicenseDb>) {
        for (license in licenses) {
            insertLicense(license)
        }
    }
}