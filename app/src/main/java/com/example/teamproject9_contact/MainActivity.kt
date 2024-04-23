package com.example.teamproject9_contact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.teamproject9_contact.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), FragmentDataListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setFragment1(ContactListFragment())

    }

    override fun onDataReceived(infoData: DataClass) {
        TODO("Not yet implemented")
    }

    private fun setFragment1(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.layout_fragment, fragment)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

}