package com.example.teamproject9_contact.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject9_contact.FragmentDataListener
import com.example.teamproject9_contact.R
import com.example.teamproject9_contact.data.ContactList
import com.example.teamproject9_contact.databinding.FragmentContactListBinding
import java.lang.RuntimeException

class ContactListFragment : Fragment() {

    private val binding: FragmentContactListBinding by lazy {
        FragmentContactListBinding.inflate(layoutInflater)
    }


    private var listener: FragmentDataListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentDataListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ContactListAdapter(ContactList.list)

        binding.layoutRecyclerview.apply {
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
            layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }

//        클릭 동작 수행
        adapter.click = object : ContactListAdapter.Click {
            override fun clicked(view: View, position: Int) {
                val selectedData = ContactList.list[position]
                listener?.onDataReceived(selectedData)

                val fragment = ContactDetailFragment.newInstance(selectedData)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

}