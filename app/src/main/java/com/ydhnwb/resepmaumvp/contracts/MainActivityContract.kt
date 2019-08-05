package com.ydhnwb.resepmaumvp.contracts

import android.content.Context
import com.ydhnwb.resepmaumvp.models.Post
import com.ydhnwb.resepmaumvp.responses.WrappedListResponse
import retrofit2.Call

interface MainActivityContract {

    interface MainActivityView {
        fun showToast(message : String)
        fun attachToRecycler(posts : List<Post>)
    }

    interface MainActivityPresenter {
        fun all(token : String)
        fun destroy()
    }

}