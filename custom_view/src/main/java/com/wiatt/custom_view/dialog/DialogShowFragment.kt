package com.wiatt.custom_view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wiatt.common.ScreenSizeUtil
import com.wiatt.custom_view.R

/**
 * @author wiatt
 * @description:
 */
class DialogShowFragment private constructor(): Fragment(){

    private lateinit var rvDialog: RecyclerView
    private var infos: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_show_dialog, container, false)
        rvDialog = rootView.findViewById(R.id.rvDialog)

        initData()
        initView()
        return rootView
    }

    private fun initData() {
        infos.add("普通对话框")
        infos.add("列表对话框")
        infos.add("单选对话框")
        infos.add("多选对话框")
        infos.add("进度条对话框")
        infos.add("半自定义对话框")
        infos.add("全自定义对话框")
        infos.add("BottomSheetDialog")
        infos.add("WindowManager构造对话框")
        infos.add("PopupWindow")
    }

    private fun initView() {
        val adapter = DialogShowAdapter(context!!, infos)
        val callback = DialogShowCallback()
        adapter.callback = callback
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvDialog.adapter = adapter
        rvDialog.layoutManager = layoutManager
        rvDialog.addItemDecoration(
            DividerItemDecoration(context, layoutManager.orientation)
        )
    }

    /**
     * 普通对话框
     */
    private fun showNormalDialog() {
        var builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setIcon(R.mipmap.avatar_programmer)
            .setTitle("我是对话框")
            .setMessage("我是对话框的内容")
        builder = setButtonHelp(builder)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    /**
     * 列表对话框
     */
    private fun showListDialog() {
        val strs = arrayOf("item_1", "item_2", "item_3", "item_4", "item_5")
        var builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setIcon(R.mipmap.avatar_programmer)
            .setTitle("列表对话框")
            .setItems(strs
            ) { dialog, which ->
                Toast.makeText(this@DialogShowFragment.context,
                    "点击item, which = $which, item = ${strs[which]}", Toast.LENGTH_SHORT)
                    .show()
            }
        builder = setButtonHelp(builder)
        builder.show()
    }

    /**
     * 单选列表对话框
     */
    private fun showSingleChoiceDialog() {
        val strs = arrayOf("item_1", "item_2", "item_3", "item_4", "item_5")
        var builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setIcon(R.mipmap.avatar_programmer)
            .setTitle("列表对话框")
            .setSingleChoiceItems(strs, 1
            ) { dialog, which ->
                Toast.makeText(this@DialogShowFragment.context,
                    "点击item, which = $which, item = ${strs[which]}", Toast.LENGTH_SHORT)
                    .show()
            }
        builder = setButtonHelp(builder)
        builder.show()
    }

    /**
     * 复选列表对话框
     */
    private fun showMultiChoiceDialog() {
        val strs = arrayOf("item_1", "item_2", "item_3", "item_4", "item_5")
        val checks = booleanArrayOf(true, false, true, false, true)
        var builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setIcon(R.mipmap.avatar_programmer)
            .setTitle("列表对话框")
            .setMultiChoiceItems(strs, checks
            ) { dialog, which, isChecked ->
                Toast.makeText(
                    this@DialogShowFragment.context,
                    "点击item, which = $which, item = ${strs[which]}, isChecked = $isChecked",
                    Toast.LENGTH_SHORT
                ).show()
            }
        builder = setButtonHelp(builder)
        builder.show()
    }

    /**
     * 进度条对话框
     * 不再建议使用
     */
    private fun showProgressDialog() {
        var builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setIcon(R.mipmap.avatar_programmer)
            .setTitle("警告")
            .setMessage("进度条对话框不再建议使用，推荐替换为 dialog + progressbar，或者使用notifications通知进度")
        builder = setButtonHelp(builder)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    /**
     * 半自定义对话框
     */
    private fun showHalfCustomDialog() {
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_half_custom, null)
        val etHalfDialog: EditText = dialogView.findViewById(R.id.etHalfDialog)
        var builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setIcon(R.mipmap.avatar_programmer)
            .setTitle("半自定义对话框")
            .setView(dialogView)
        builder = setButtonHelp(builder)
        builder.show()
    }

    /**
     * 全自定义对话框
     */
    private fun showFullyCustomDialog() {
        val dialog = Dialog(context!!, R.style.NormalDialogStyle)
        val dialogView = layoutInflater.inflate(R.layout.dialog_fully_custom, null)
        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)
        dialog.setContentView(dialogView)
        dialog.setCanceledOnTouchOutside(true)
        dialogView.minimumHeight = (ScreenSizeUtil.getScreenHeight(context!!) * 0.23f).toInt()
        val dialogWindow = dialog.window
        val lp: WindowManager.LayoutParams = dialogWindow!!.attributes
        lp.width = ScreenSizeUtil.getScreenWidth(context!!)
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM
        dialogWindow.attributes = lp
        dialog.show()
        btnCancel.setOnClickListener { dialog.dismiss() }
    }

    private fun showPopupWindow() {
        val popupWindow = PopupWindow()
        val view: View = LayoutInflater.from(context!!).inflate(R.layout.dialog_popupwindow, null)
        popupWindow.contentView = view
        popupWindow.width = WindowManager.LayoutParams.MATCH_PARENT
        popupWindow.height = (ScreenSizeUtil.getScreenHeight(context!!) * 0.3f).toInt()
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.softInputMode =
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED

        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0)
    }

    /**
     * 辅助设置 “取消”、“确定” 按钮
     */
    private fun setButtonHelp(builder: AlertDialog.Builder): AlertDialog.Builder{
        builder.setNegativeButton("取消"
        ) { dialog, which ->
            // which: 当前点击的是哪一个按钮，比如：DialogInterface.BUTTON_NEGATIVE
            Toast.makeText(this@DialogShowFragment.context, "点击取消, which = $which", Toast.LENGTH_SHORT)
                .show()
            dialog?.dismiss()
        }
            .setPositiveButton("确定"
            ) { dialog, which ->
                Toast.makeText(this@DialogShowFragment.context, "点击确定, which = $which", Toast.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }
        return builder
    }

    inner class DialogShowCallback: DialogShowAdapter.IDialogShowAdapterCallback {
        override fun onClickItem(info: String) {
            when(info) {
                infos[0] -> {
                    showNormalDialog()
                }
                infos[1] -> {
                    showListDialog()
                }
                infos[2] -> {
                    showSingleChoiceDialog()
                }
                infos[3] -> {
                    showMultiChoiceDialog()
                }
                infos[4] -> {
                    showProgressDialog()
                }
                infos[5] -> {
                    showHalfCustomDialog()
                }
                infos[6] -> {
                    showFullyCustomDialog()
                }
                infos[7] -> {}
                infos[8] -> {}
                infos[9] -> {
                    showPopupWindow()
                }
            }
        }
    }

    companion object {
        fun newInstance() = DialogShowFragment()
    }
}