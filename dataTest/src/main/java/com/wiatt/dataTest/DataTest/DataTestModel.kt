package com.wiatt.dataTest.DataTest

import android.os.Looper
import android.os.Message
import com.wiatt.common.AppExecutors
import com.wiatt.common.WeakHandler
import com.wiatt.common.mvp.BaseModel
import com.wiatt.dataTest.data.*
import com.wiatt.engine.database.entity.LicenseDb
import com.wiatt.engine.database.entity.OwnerDb
import com.wiatt.engine.database.entity.RepoDb
import com.wiatt.engine.database.greenDao.daoImpl.DaoFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val MSG_REQUEST_GITHUB_FROM_NET = 1001
private const val MSG_RESPONSE_OWNER_FROM_NET = 1002
private const val MSG_RESPONSE_REPO_FROM_NET = 1003
private const val MSG_RESPONSE_GITHUB_FAIL_FROM_NET = 1004
private const val MSG_GET_GITHUB_RESULT_FROM_DB = 1011

class DataTestModel(override var p: DataTestPresenter) :
        BaseModel<DataTestModel, DataTestPresenter, DataTestContract.IDtIModel>(p) {

    private val githubNetLock: Any = Any()
    private val githubDbLock: Any = Any()
    private var mHandler: DataTestModelHandler = DataTestModelHandler(Looper.getMainLooper(), this)

    override fun getContract(): DataTestContract.IDtIModel {
        return DtModel()
    }

    private fun requestGithubMsg(userName: String) {
        TestApi().getOwner(userName).enqueue(object : Callback<OwnerMain> {
            override fun onResponse(call: Call<OwnerMain>, response: Response<OwnerMain>) {
                println("owner 请求成功")
                val ownerMain: OwnerMain? = response.body()
                if (ownerMain != null) {
                    saveOwnerInDatabase(ownerMain)
                    mHandler.sendHandlerMessage(MSG_RESPONSE_OWNER_FROM_NET, "github message request success from net", 0, 0, 0)
                } else {
                    println("owner 值为空")
                    mHandler.sendHandlerMessage(MSG_RESPONSE_GITHUB_FAIL_FROM_NET,
                        "github message request fail from net", 0, 0, 0)
                }
            }

            override fun onFailure(call: Call<OwnerMain>, t: Throwable) {
                println("owner 请求失败")
                mHandler.sendHandlerMessage(MSG_RESPONSE_GITHUB_FAIL_FROM_NET,
                    "github message request fail from net", 0, 0, 0)
            }

        })

        TestApi().getRepos(userName).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                println("repo 请求成功")
                val repos: List<Repo>? = response.body()
                if (repos != null) {
                    saveRepoInDatabase(repos)
                    mHandler.sendHandlerMessage(MSG_RESPONSE_OWNER_FROM_NET,
                        "github message request success from net", 0, 0, 0)
                } else {
                    println("repo 值为空")
                    mHandler.sendHandlerMessage(MSG_RESPONSE_GITHUB_FAIL_FROM_NET,
                        "github message request fail from net", 0, 0, 0)
                }
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                println("repo 请求失败")
                mHandler.sendHandlerMessage(MSG_RESPONSE_GITHUB_FAIL_FROM_NET,
                    "github message request fail from net", 0, 0, 0)
            }
        })
    }

    private fun saveOwnerInDatabase(ownerMain: OwnerMain) {
        val ownerDb = OwnerDb(null, ownerMain.id, ownerMain.login, ownerMain.node_id, ownerMain.html_url,
            ownerMain.subscriptions_url, ownerMain.repos_url, ownerMain.type, ownerMain.name,
            ownerMain.company, ownerMain.location, ownerMain.public_repos)
        DaoFactory.instance.ownerDaoImpl?.insertOwner(ownerDb)
    }

    private fun saveRepoInDatabase(repos: List<Repo>) {
        val repoDbList = mutableListOf<RepoDb>()
        val licenseDbList = mutableListOf<LicenseDb>()
        for (repo in repos) {
            val license: License? = repo.license
            val repoDb = RepoDb(null, repo.id, repo.node_id, repo.name, repo.full_name,
                repo.owner.id, null, repo.html_url, repo.description,
                repo.events_url, repo.created_at, repo.updated_at, repo.pushed_at,
                repo.git_url, repo.ssh_url, repo.clone_url, repo.svn_url,
                repo.stargazers_count, repo.watchers_count, repo.forks_count, repo.open_issues_count)
            if (license != null) {
                val licenseDb = LicenseDb(null, license.node_id, license.key, license.name,
                        license.spdx_id, license.url)
                licenseDbList.add(licenseDb)
            }
            repoDbList.add(repoDb)
        }
        DaoFactory.instance.repoDaoImpl?.insertRepo(repoDbList)
        DaoFactory.instance.licenseDaoImpl?.insertLicense(licenseDbList)
    }

    private fun getGithubMsgFromDb() {
        println("DataTestModel, getGithubMsgFromDb")
        AppExecutors.getInstance().diskIO().execute(Runnable {
            var ownerDbs: List<OwnerDb>? = DaoFactory.instance.ownerDaoImpl?.loadAllOwner()
            if (ownerDbs.isNullOrEmpty()) {
                println("DataTestModel, ownerDbs is empty")
                mHandler.sendHandlerMessage(MSG_GET_GITHUB_RESULT_FROM_DB, mutableListOf<GithubMsg>(), 0, 0, 0)
            } else {
                var ownerDb = ownerDbs[0]
                var repoDbs: List<RepoDb>? = DaoFactory.instance.repoDaoImpl?.loadReposByOwner(ownerDb.idReal)
                if (repoDbs.isNullOrEmpty()) {
                    println("DataTestModel, repoDbs is empty")
                    mHandler.sendHandlerMessage(MSG_GET_GITHUB_RESULT_FROM_DB, mutableListOf<GithubMsg>(), 0, 0, 0)
                } else {
                    val githubMsgs: MutableList<GithubMsg> = mutableListOf()
                    githubMsgs.add(GithubMsg(GithubMsg.TYPE_OWNER, ownerDb))
                    for (repoDb in repoDbs) {
                        githubMsgs.add(GithubMsg(GithubMsg.TYPE_REPO, repoDb))
                    }
                    println("DataTestModel, githubMsgs size is ${githubMsgs.size}")
                    mHandler.sendHandlerMessage(MSG_GET_GITHUB_RESULT_FROM_DB, githubMsgs, 0, 0, 0)
                }
            }
        })
    }

    inner class DtModel: DataTestContract.IDtIModel {
        override fun requestGithubMsgFromNet(userName: String) {
            mHandler.sendHandlerMessage(MSG_REQUEST_GITHUB_FROM_NET, userName, 0, 0, 0)
        }

        override fun requestRepoFromDataBase() {
            getGithubMsgFromDb()
        }

        override fun requestRepoFromFile() {
            p.getContract().responseRepoFromFile(arrayListOf<Repo>())
        }
    }

    inner class DataTestModelHandler(looper: Looper, owner: DataTestModel):
        WeakHandler<DataTestModel>(looper, owner) {
        private var githubSignal = -1
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                MSG_REQUEST_GITHUB_FROM_NET -> {
                    synchronized(githubNetLock) {
                        if (githubSignal == -1) {
                            githubSignal = 0
                            requestGithubMsg(msg.obj as String)
                        }
                    }
                }
                MSG_RESPONSE_OWNER_FROM_NET -> {
                    synchronized(githubNetLock) {
                        if (githubSignal == 0) {
                            githubSignal = 1
                        } else if (githubSignal == 1){
                            githubSignal = -1
                            p.getContract().responseGithubMsgFromNet(msg.obj as String)
                        }
                    }
                }
                MSG_RESPONSE_REPO_FROM_NET -> {
                    synchronized(githubNetLock) {
                        if (githubSignal == 0) {
                            githubSignal = 1
                        } else if (githubSignal == 1) {
                            githubSignal = -1
                            p.getContract().responseGithubMsgFromNet(msg.obj as String)
                        }
                    }
                }
                MSG_RESPONSE_GITHUB_FAIL_FROM_NET -> {
                    synchronized(githubNetLock) {
                        githubSignal = -1
                        mHandler.removeMessages(MSG_RESPONSE_OWNER_FROM_NET, MSG_RESPONSE_REPO_FROM_NET)
                    }
                }
                MSG_GET_GITHUB_RESULT_FROM_DB -> {
                    var githubmsgs = msg.obj as MutableList<GithubMsg>
                    p.getContract().responseRepoFromDataBase(githubmsgs)
                }
                else -> {}
            }
        }
    }
}