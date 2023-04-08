package com.wiatt.simpledemo.myGreendao

import com.wiatt.simpledemo.SimpleDemoApplication
import com.wiatt.simpledemo.myGreendao.dao.StudentDao
import com.wiatt.simpledemo.myGreendao.entity.Student
import org.greenrobot.greendao.query.DeleteQuery
import org.greenrobot.greendao.query.Query
import org.greenrobot.greendao.query.QueryBuilder

object studentDaoImpl {
    var daoSession = SimpleDemoApplication.mApplication?.daoSession

    fun insertStudent(student: Student): Long? {
        return daoSession?.insert(student)
    }

    fun insertOrReplace(student: Student): Long? {
        return daoSession?.insertOrReplace(student)
    }

    fun deleteStudent(student: Student) {
        daoSession?.delete(student)
    }

    fun deleteAllStudent() {
        daoSession?.deleteAll(Student::class.java)
    }

    fun updateStudent(student: Student) {
        daoSession?.update(student)
    }

    fun queryAllStudent(): List<Student> {
        return daoSession?.loadAll<Student, Long>(Student::class.java) as List<Student>
    }

    fun queryStudent(id: Long): List<Student> {
        return daoSession?.queryRaw<Student, Long>(Student::class.java, "where id = ?", id.toString()) as List<Student>
    }

    fun queryAllList(): MutableList<Student>? {
        return daoSession?.queryBuilder(Student::class.java)?.list()
    }

    fun queryListByMessage(name: String): MutableList<Student>? {
        return if (daoSession == null) {
            null
        } else {
            daoSession!!.queryBuilder(Student::class.java)
                    .where(StudentDao.Properties.Name.eq("一"))
                    .orderAsc(StudentDao.Properties.Name)
                    .list()
        }
    }

    fun queryList(): MutableList<Student>? {
        return if (daoSession == null) {
            null
        } else {
            var qb: QueryBuilder<Student> = daoSession!!.queryBuilder(Student::class.java)
            qb.where(StudentDao.Properties.Name.eq("一"),
                    qb.and(StudentDao.Properties.Id.gt(5),
                            StudentDao.Properties.Id.le(50))).list()
        }
    }

    fun queryListByOther(): MutableList<Student>? {
        return if (daoSession == null) {
            null
        } else {
            var qb: QueryBuilder<Student> = daoSession!!.queryBuilder(Student::class.java)
            qb.where(StudentDao.Properties.Id.gt(1)).limit(10).offset(2).list()
        }
    }

    /**
     * 多次查询
     */
    fun queryListByMoreTime(): MutableList<Student>? {
        return if (daoSession == null) {
            null
        } else {
            var qb: QueryBuilder<Student> = daoSession!!.queryBuilder(Student::class.java)
            var query: Query<Student> =
                    qb.where(StudentDao.Properties.Id.gt(1))
                    .limit(10).offset(2).build()
            var list: MutableList<Student> = query.list()
            query.setParameter(0, 5)
            query.list()
        }
    }

    /**
     * 批量删除
     */
    fun deleteItem(): Boolean {
        return if (daoSession == null) {
            false
        } else {
            var where: QueryBuilder<Student> = daoSession!!
                    .queryBuilder(Student::class.java)
                    .where(StudentDao.Properties.Id.gt(5))
            var deleteQuery: DeleteQuery<Student> = where.buildDelete()
            deleteQuery.executeDeleteWithoutDetachingEntities()
            true
        }
    }
}