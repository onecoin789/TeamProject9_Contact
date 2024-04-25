package com.example.teamproject9_contact.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject9_contact.FragmentDataListener
import com.example.teamproject9_contact.R
import com.example.teamproject9_contact.data.ContactList
import com.example.teamproject9_contact.databinding.FragmentContactListBinding
import java.lang.RuntimeException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

        val adapterList = ContactListAdapter(ContactList.list)
        val adapterGrid = ContactGridAdapter(ContactList.list)

        binding.layoutRecyclerview.apply {
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
            layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            this.adapter = adapterList
        }
//        클릭 동작 수행
        adapterList.click = object : ContactListAdapter.Click {
            override fun clicked(view: View, position: Int) {
                onClick(position)
            }
        }

        binding.layoutGridview.adapter = adapterGrid
        adapterGrid.click = object : ContactGridAdapter.Click {
            override fun clicked(view: View, position: Int) {
                onClick(position)
            }
        }

    }

    private fun onClick(position: Int) {
        val selectedData = ContactList.list[position]
        listener?.onDataReceived(selectedData)

        val fragment = ContactDetailFragment.newInstance(selectedData)
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out)
            .replace(R.id.frame, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}