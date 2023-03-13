package com.wiatt.custom_view.CustomViewTouchDrag

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.wiatt.custom_view.R

class DragToCollectLayout(context: Context, attrs: AttributeSet?)
    : ConstraintLayout(context, attrs) {
    private var dragStarter = OnLongClickListener { v ->
        val imageData = ClipData.newPlainText("name", v.contentDescription)
        ViewCompat.startDragAndDrop(v, imageData, DragShadowBuilder(v), null, 0)
    }
    private var dragListener: OnDragListener = CollectListener()
    private val avatarView: ImageView = findViewById(R.id.avatarView)
    private val logoView: ImageView = findViewById(R.id.logoView)
    private val collectorLayout: LinearLayout = findViewById(R.id.collectorLayout)

    override fun onFinishInflate() {
        super.onFinishInflate()
        avatarView.setOnLongClickListener(dragStarter)
        logoView.setOnLongClickListener(dragStarter)
        collectorLayout.setOnDragListener(dragListener)
    }

    inner class CollectListener: OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when(event.action) {
                DragEvent.ACTION_DROP -> if (v is LinearLayout) {
                    val textView = TextView(context)
                    textView.textSize = 16f
                    textView.text = event.clipData.getItemAt(0).text
                    v.addView(textView)
                }
            }
            return true
        }

    }
}