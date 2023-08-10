package com.learn.newproject.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learn.newproject.R
import com.learn.newproject.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {
   private lateinit var binding: FragmentAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {

    }
}