package com.example.chuibbo_android.background

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.chuibbo_android.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.background_synthesis_fragment.*

class BackgroundSynthesisFragment: Fragment() {

    @SuppressLint("ResourceAsColor")
    private fun changeView(index: Int) {
        when (index) {
            0 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.color_contents, BackgroundSynthesisSolidcolorFragment())
                    addToBackStack(null)
                }?.commit()
            }
            1 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.color_contents, BackgroundSynthesisGradationFragment())
                    addToBackStack(null)
                }?.commit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // color picker로 선택된 컬러로 배경 색상 변경
        setFragmentResultListener("Picked Color") { resultKey, bundle ->
            background_synthesis_image.setBackgroundColor(Color.parseColor("#"+bundle.getString("color")))
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.background_synthesis_fragment, container, false)

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.color_contents, BackgroundSynthesisSolidcolorFragment())
            addToBackStack(null)
        }?.commit()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        background_synthesis_image_tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var pos = tab.getPosition()
                changeView(pos)
                // TODO: tab 선택시 폰트 크기 증가, bold, 높이 증가
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // do nothing
            }
        })
    }
}