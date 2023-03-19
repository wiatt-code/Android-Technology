package com.wiatt.dataTest.DataTest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.dataTest.R
import com.wiatt.dataTest.base.BaseFragment
import com.wiatt.common.mvp.BaseView
import com.wiatt.dataTest.data.Repo
import com.wiatt.dataTest.data.TestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Route(path = "/dataTest/DataTestFragment")
class DataTestFragment : BaseFragment(), BaseView<DataTestFragment, DataTestPresenter, DataTestContract.IDtIView> {

    private lateinit var p: DataTestPresenter
    private lateinit var tvResult: TextView
    private lateinit var btnRequest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = getPresenter()
        p.bindView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_data_test, container, false)
        tvResult = rootView.findViewById(R.id.tvResult)
        btnRequest = rootView.findViewById(R.id.btnRequest)
        btnRequest.setOnClickListener { p.getContract().getRepoFromNet("octocat") }
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
        override fun callBackRepoFromNet(reposData: String) {
            this@DataTestFragment.activity?.runOnUiThread {
                tvResult.text = "请求成功，repo = $reposData"
            }
        }

        override fun callBackRepoFromDatabase(repos: List<Repo>) {
        }

        override fun callBackRepoFromFile(repos: List<Repo>) {
        }

    }
}