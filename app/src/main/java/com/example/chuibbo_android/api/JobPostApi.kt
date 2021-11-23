package com.example.chuibbo_android.api

import android.content.Context
import com.example.chuibbo_android.api.response.SpringResponse2
import com.example.chuibbo_android.home.JobPost
import retrofit2.Call
import retrofit2.http.*

interface JobPostApi {

    @GET("/api/v1/job_posts")
    fun getJobPosts(): Call<SpringResponse2<List<JobPost>>>

    @GET("/api/v1/job_posts/more")
    fun getJobPostsMore(
        @Query("page") page: Int
    ): Call<SpringResponse2<List<JobPost>>>

    @GET("/api/v1/job_post/bookmarks")
    fun getBookmarks(): Call<SpringResponse2<List<JobPost>>>

    @POST("/api/v1/job_post/{job_post_id}/bookmark")
    fun saveBookmark(
        @Path("job_post_id") id: Int
    ): Call<SpringResponse2<String>>

    @DELETE("/api/v1/job_post/{job_post_id}/bookmark")
    fun deleteBookmark(
        @Path("job_post_id") id: Int
    ): Call<SpringResponse2<String>>

    companion object {
        val instance = ApiGenerator().generateSpring2(JobPostApi::class.java)

        fun instance(context: Context): JobPostApi {
            return ApiGenerator().generateSpring(JobPostApi::class.java, context)
        }
    }
}
