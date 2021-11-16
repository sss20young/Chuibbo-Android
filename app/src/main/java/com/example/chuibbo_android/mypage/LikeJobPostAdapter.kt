package com.example.chuibbo_android.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringResponse2
import com.example.chuibbo_android.home.ImageLoader
import com.example.chuibbo_android.home.JobPost
import kotlinx.android.synthetic.main.mypage_like_job_posting.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class LikeJobPostAdapter(private val onClick: (JobPost) -> Unit) :
    ListAdapter<JobPost, LikeJobPostAdapter.LikeJobPostViewHolder>(LikeJobPostDiffCallback) {

    /* ViewHolder for LikeJobPost, takes in the inflated view and the onClick behavior. */
    class LikeJobPostViewHolder(itemView: View, val onClick: (JobPost) -> Unit, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val companyName: TextView = itemView.company_name
        private val companyDesc: TextView = itemView.company_desc
        private val companyDeadline: TextView = itemView.company_deadline
        private val companyLogo: ImageView = itemView.company_logo
        private var currentLikeJobPost: JobPost? = null

        init {
            itemView.setOnClickListener {
                currentLikeJobPost?.let {
                    onClick(it)
                }
            }

            itemView.star.setOnClickListener {
                if (itemView.star.drawable.constantState == context.resources.getDrawable(R.drawable.ic_star_fill).constantState) {
                    JobPostApi.instance(context).deleteBookmark(currentLikeJobPost!!.id).enqueue(object :
                        Callback<SpringResponse2<String>> {
                        override fun onFailure(call: Call<SpringResponse2<String>>, t: Throwable) {
                            Log.d("retrofit fail", t.message)
                        }

                        override fun onResponse(
                            call: Call<SpringResponse2<String>>,
                            response: Response<SpringResponse2<String>>
                        ) {
                            if (response.isSuccessful) {
                                itemView.star.setImageResource(R.drawable.ic_star_empty)
                                // TODO: datasource에서 remove
                            }
                        }
                    })
                } else {
                    JobPostApi.instance(context).saveBookmark(currentLikeJobPost!!.id).enqueue(object :
                        Callback<SpringResponse2<String>> {
                        override fun onFailure(call: Call<SpringResponse2<String>>, t: Throwable) {
                            Log.d("retrofit fail", t.message)
                        }

                        override fun onResponse(
                            call: Call<SpringResponse2<String>>,
                            response: Response<SpringResponse2<String>>
                        ) {
                            if (response.isSuccessful) {
                                itemView.star.setImageResource(R.drawable.ic_star_fill)
                                // TODO: datasource에서 add
                            }
                        }
                    })
                }
            }
        }

        /* Bind likeJobPost name and image. */
        fun bind(likeJobPost: JobPost) {
            currentLikeJobPost = likeJobPost

            companyName.text = likeJobPost.companyName
            companyDesc.text = likeJobPost.subject

            companyDeadline.text = calculateDday(likeJobPost.endDate)

            if (likeJobPost.logoUrl != null) {

                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        ImageLoader.loadImage(likeJobPost.logoUrl)
                    }
                    companyLogo.setImageBitmap(bitmap)
                }
            }
        }

        private fun calculateDday(end: String): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val endDateTime = LocalDateTime.parse(end.replace("T", " "), formatter)
            val todayDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
            var difference = ChronoUnit.DAYS.between(todayDateTime.toLocalDate(), endDateTime.toLocalDate()).toInt() // 날짜만 계산

            if (endDateTime.isAfter(todayDateTime)) {
                if (endDateTime.year == todayDateTime.year && endDateTime.monthValue == todayDateTime.monthValue
                    && endDateTime.dayOfMonth == todayDateTime.dayOfMonth) {
                    return "D-Day"
                } else {
                    return "D-$difference"
                }
            } else
                return "마감"
        }
    }

    /* Creates and inflates view and return LikeJobPostViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeJobPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mypage_like_job_posting, parent, false)
        return LikeJobPostViewHolder(view, onClick, parent.context)
    }

    /* Gets current likeJobPost and uses it to bind view. */
    override fun onBindViewHolder(holder: LikeJobPostViewHolder, position: Int) {
        val likeJobPost = getItem(position)
        holder.bind(likeJobPost)
    }
}

object LikeJobPostDiffCallback : DiffUtil.ItemCallback<JobPost>() {
    override fun areItemsTheSame(oldItem: JobPost, newItem: JobPost): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: JobPost, newItem: JobPost): Boolean {
        return oldItem.id == newItem.id
    }
}
