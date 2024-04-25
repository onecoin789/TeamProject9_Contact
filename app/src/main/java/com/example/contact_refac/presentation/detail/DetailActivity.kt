package com.example.contact_refac.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.contact_refac.data.Contact
import com.example.contact_refac.data.ContactList
import com.example.contact_refac.data.EditProfileListener
import com.example.contact_refac.databinding.ActivityDetailBinding
import com.example.contact_refac.presentation.main.fragment.detailPage.DetailPageFragment

class DetailActivity : AppCompatActivity(), EditProfileListener {
    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private var position: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getParcelableExtra<Contact>("Contact")?.let{
            setFragment(DetailPageFragment.newInstance(it))
            position = ContactList.list.indexOf(it)
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.flDetail.id, fragment)
            .commit()
    }

    override fun notifyDataChanged(data: Contact?) {
        position?.let{setFragment(DetailPageFragment.newInstance(ContactList.list[it]))}
    }
}