package com.example.teamproject9_contact.fragment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject9_contact.R

class ItemTouchCallbackContactList(
    private val recyclerView: RecyclerView
) : ItemTouchHelper.SimpleCallback(
    0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val adapter = recyclerView.adapter as ContactListAdapter
        if (direction == ItemTouchHelper.LEFT) {
//            adapter.message()
        } else if (direction == ItemTouchHelper.RIGHT) {
//            adapter.call()
        }
        adapter.notifyItemChanged(position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        with(itemView) {
            if (dX > 0) { // 오른쪽으로 스와이프한 경우
                setBackgroundColor(ContextCompat.getColor(recyclerView.context, R.color.callGreen))
                background.setBounds(
                    left,
                    top,
                    left + dX.toInt(),
                    bottom
                )
            } else if (dX < 0) { // 왼쪽으로 스와이프한 경우
                setBackgroundColor(
                    ContextCompat.getColor(
                        recyclerView.context,
                        R.color.messageBlue
                    )
                )
                background.setBounds(
                    right + dX.toInt(),
                    top,
                    right,
                    bottom
                )
            } else { // 스와이프하지 않은 경우
                setBackgroundColor(Color.WHITE)
                background.setBounds(0, 0, 0, 0)
            }
            background.draw(c)
        }
    }

}