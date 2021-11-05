package com.example.chuibbo_android.mypage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.home.PhotoAlbumViewModel
import com.example.chuibbo_android.home.PhotoAlbumViewModelFactory
import com.example.chuibbo_android.preferences.PreferencesFragment
import com.example.chuibbo_android.utils.SessionManager
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.mypage_fragment.view.*

class MypageFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    private val photoAlbumViewModel by viewModels<PhotoAlbumViewModel> {
        context?.let { PhotoAlbumViewModelFactory(it) }!!
    }

    private val likeJobPostListViewModel by viewModels<LikeJobPostListViewModel> {
        context?.let { LikeJobPostListViewModelFactory(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.mypage_fragment, container, false)

        sessionManager = SessionManager(requireContext())

        val user_info = sessionManager.fetchUserInfo()

        view.user_name.text = "$user_info 님"
        view.user_name1.text = user_info
        view.user_name2.text = user_info

        // photoAlbum recyclerview
        val recyclerviewPhotoAlbum: RecyclerView = view.recyclerview_resume_photo
        recyclerviewPhotoAlbum.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val photoAlbumAdapter = PhotoAlbumAdapter { photoAlbum -> photoAlbumAdapterOnClick(photoAlbum, view) }

        recyclerviewPhotoAlbum.adapter = photoAlbumAdapter

        photoAlbumViewModel.photoAlbumsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                photoAlbumAdapter.submitList(it as MutableList<PhotoAlbum>)
            }
        })

        // likeJobPost recyclerview
        val recyclerviewLikeJobPost: RecyclerView = view.recyclerview_like_job_posting
        recyclerviewLikeJobPost.layoutManager = LinearLayoutManager(context)
        val likeJobPostAdapter = LikeJobPostAdapter { likeJobPost -> likeJobPostAdapterOnClick(likeJobPost, view) }

        recyclerviewLikeJobPost.adapter = likeJobPostAdapter

        likeJobPostListViewModel.likeJobPostsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                likeJobPostAdapter.submitList(it as MutableList<LikeJobPost>)
            }
        })

        view.resume_photo_count.text = photoAlbumViewModel.getSize().toString()
        view.like_job_posting_count.text = likeJobPostListViewModel.getSize().toString()

        activity?.toolbar_title!!.text = "마이페이지"
        activity?.settings_button!!.visibility = View.VISIBLE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.settings_button!!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesFragment())
                addToBackStack(null)
            }?.commit()
        }

        // TODO: star 클릭시 color 변화
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.settings_button!!.visibility = View.GONE
    }

    /* Opens dialog when RecyclerView item is clicked. */
    private fun photoAlbumAdapterOnClick(photoAlbum: PhotoAlbum, view: View) {
        var myalbumDialog = MypageMyalbumDialogFragment()
        myalbumDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기

        // 데이터 전달
        val args = Bundle()
        args.putString("image", photoAlbum.image)
        args.putString("dateAndDesc", photoAlbum.date + "\n" + photoAlbum.desc)
        myalbumDialog.arguments = args
        activity?.let { it1 -> myalbumDialog.show(it1.supportFragmentManager, "CustomDialog") }
    }

    /* Opens companyLink of LikeJobPost when RecyclerView item is clicked. */
    private fun likeJobPostAdapterOnClick(likeJobPost: LikeJobPost, view: View) {
        val url: String = likeJobPost.companyLink
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }
}
