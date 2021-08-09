package com.example.chuibbo_android.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.main_activity.*

class DownloadFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.download_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.toolbar_title!!.text = "사진 저장"
        activity?.download_button!!.visibility = View.VISIBLE
        activity?.download_button!!.setOnClickListener {
            // TODO: 2021/04/09 이미지 로컬 갤러리 & 서버에 다운로드
        }
    }
}
