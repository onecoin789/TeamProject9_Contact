package com.example.contact_refac.presentation.main.fragment.contactList

import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_refac.R
import com.example.contact_refac.data.ContactList
import com.example.contact_refac.databinding.FragmentContactListBinding
import com.example.contact_refac.presentation.main.fragment.addDialog.AddDialog

class ContactListFragment : Fragment() {
    private lateinit var binding: FragmentContactListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getList(){
        val adapter = ContactListRecyclerViewAdapter()
        adapter.submitList(ContactList.list)
        binding.recyclerView.adapter = adapter
        binding.gridView.adapter = ContactListGridViewAdapter(ContactList.list)
    }

    fun switchGridView() = with(binding){
        gridView.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    fun switchRecyclerView() = with(binding){
        gridView.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getList()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemTouchHelper()
        addContact()
    }

    private fun itemTouchHelper(){
        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val viewHolder = viewHolder as ContactListRecyclerViewAdapter.ViewHolder
                if(direction == ItemTouchHelper.LEFT){
                    viewHolder.message()
                } else if(direction == ItemTouchHelper.RIGHT){
                    viewHolder.call()
                }
                binding.recyclerView.adapter?.notifyDataSetChanged()
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
                    } else{
                        background.setBounds(0, 0, 0, 0)
                    }
                    background.draw(c)
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun addContact() {
        binding.fbMain.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val addDialog = AddDialog()
            addDialog.show(fragmentManager, "AddDialog")
        }
    }

}