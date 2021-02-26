package com.jiang.articlegenerator.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jiang.articlegenerator.R
import com.jiang.articlegenerator.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            tvLink.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(tvLink.text.toString())))
            }
            tvEmail.setOnClickListener {
                val toString = tvEmail.text.toString()
                val uri = Uri.parse("mailto:$toString")
                val email = arrayOf(toString)
                val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
                    putExtra(Intent.EXTRA_CC, email)
                    putExtra(Intent.EXTRA_SUBJECT, "${getString(R.string.app_name)} issue")
                    putExtra(Intent.EXTRA_TEXT, "issue:")
                }
                startActivity(Intent.createChooser(intent, getString(R.string.select_send_app)))

            }
        }
    }
}