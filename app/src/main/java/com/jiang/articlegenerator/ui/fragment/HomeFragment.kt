package com.jiang.articlegenerator.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jiang.articlegenerator.R
import com.jiang.articlegenerator.databinding.FragmentHomeBinding
import com.jiang.articlegenerator.tool.navigateByActionId


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            btnGenerate.setOnClickListener { navigateByActionId(R.id.action_homeFragment_to_generateTextFragment) }
            btnThesaurus.setOnClickListener { navigateByActionId(R.id.action_homeFragment_to_thesaurusFragment) }
            btnSetting.setOnClickListener { navigateByActionId(R.id.action_homeFragment_to_settingFragment) }
            btnAbout.setOnClickListener { navigateByActionId(R.id.action_homeFragment_to_aboutFragment) }
        }
    }

}