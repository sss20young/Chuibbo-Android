package com.example.chuibbo_android.background

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.chuibbo_android.R
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.background_synthesis_gradation_fragment.*

class BackgroundSynthesisGradationFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.background_synthesis_gradation_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()

        color_none_gradation.setOnClickListener { setFragmentResult("Picked Color", bundleOf("color" to "80FFFFFF")) }
        color_white_gradation.setOnClickListener { setFragmentResult("Picked Color", bundleOf("color" to "80F6F6F6")) }
        color_blue_gradation.setOnClickListener { setFragmentResult("Picked Color", bundleOf("color" to "809ED1FF")) }
        color_yellow_gradation.setOnClickListener { setFragmentResult("Picked Color", bundleOf("color" to "80FFD6AF")) }
        color_pink_gradation.setOnClickListener { setFragmentResult("Picked Color", bundleOf("color" to "80FFD1D1")) }
        color_picker_gradation.setOnClickListener {
            var colorPickerView = ColorPickerDialog.Builder(context)
                .setTitle("그라데이션 배경 색상 선택")
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton("확인",
                        ColorEnvelopeListener { envelope, fromUser ->
                            setFragmentResult("Picked Color", bundleOf("color" to envelope.hexCode))} )
                .setNegativeButton("취소",
                        DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
                .attachAlphaSlideBar(true) // the default value is true.
                .attachBrightnessSlideBar(true) // the default value is true.
                .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                .show()
        }
    }
}