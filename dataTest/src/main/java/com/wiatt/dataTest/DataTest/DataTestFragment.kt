package com.wiatt.dataTest.DataTest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.dataTest.R
import com.wiatt.common.base.BaseFragment
import com.wiatt.common.mvp.BaseView
import com.wiatt.dataTest.data.GithubMsg
import com.wiatt.dataTest.data.Repo

@Route(path = "/dataTest/DataTestFragment")
class DataTestFragment : BaseFragment(), BaseView<DataTestFragment, DataTestPresenter, DataTestContract.IDtIView> {

    private lateinit var p: DataTestPresenter
    private lateinit var tvRFNResult: TextView
    private lateinit var btnRFN: Button
    private lateinit var btnGFD: Button
    private lateinit var btnGFF: Button
    private lateinit var tvGFDResult: TextView
    private lateinit var flContainer: FrameLayout
    private lateinit var fGFDMsg: GithubMsgFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = getPresenter()
        p.bindView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_data_test, container, false)
        btnRFN = rootView.findViewById(R.id.btnRFN)
        tvRFNResult = rootView.findViewById(R.id.tvRFNResult)
        btnGFD = rootView.findViewById(R.id.btnGFD)
        tvGFDResult = rootView.findViewById(R.id.tvGFDResult)
        flContainer = rootView.findViewById(R.id.flContainer)
        btnGFF = rootView.findViewById(R.id.btnGFF)
        fGFDMsg = GithubMsgFragment.newInstance()
        childFragmentManager.beginTransaction().add(R.id.flContainer, fGFDMsg).commit()
        childFragmentManager.beginTransaction().hide(fGFDMsg).commit()
        tvGFDResult.visibility = View.GONE
        btnRFN.setOnClickListener {
            p.getContract().getGithubMsgFromNet("octocat")
        }
        btnGFD.setOnClickListener {
            p.getContract().getRepoFromDatabase()
        }
        btnGFF.setOnClickListener {
            Toast.makeText(context, "功能建设中", Toast.LENGTH_LONG).show()
        }

        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        p.unBindView()
    }

    override fun getContract(): DataTestContract.IDtIView {
        return DtView()
    }

    override fun getPresenter(): DataTestPresenter {
        return DataTestPresenter()
    }

    companion object {
        @JvmStatic
        fun newInstance(): DataTestFragment {
            return DataTestFragment()
        }
    }

    inner class DtView: DataTestContract.IDtIView {
        override fun callBackGithubMsgFromNet(responseMsg: String) {
            this@DataTestFragment.activity?.runOnUiThread {
                tvRFNResult.text = responseMsg
            }
        }

        override fun callBackRepoFromDatabase(githubMsgs: MutableList<GithubMsg>) {
            if (githubMsgs.isEmpty()) {
                childFragmentManager.beginTransaction().hide(fGFDMsg).commit()
                tvGFDResult.text = "github message get fail from database"
                tvGFDResult.visibility = View.VISIBLE
            } else {
                tvGFDResult.visibility = View.GONE
                fGFDMsg.update(githubMsgs)
                childFragmentManager.beginTransaction().show(fGFDMsg).commit()
            }
        }

        override fun callBackRepoFromFile(repos: List<Repo>) {
        }

    }
}