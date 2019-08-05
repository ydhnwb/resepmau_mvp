package com.ydhnwb.resepmaumvp.presenters

import android.content.Context
import com.ydhnwb.resepmaumvp.contracts.MainActivityContract
import com.ydhnwb.resepmaumvp.models.Post
import com.ydhnwb.resepmaumvp.responses.WrappedListResponse
import com.ydhnwb.resepmaumvp.utilities.APIClient
import com.ydhnwb.resepmaumvp.utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter(v : MainActivityContract.MainActivityView?) : MainActivityContract.MainActivityPresenter {
    private var view : MainActivityContract.MainActivityView? = v
    private var apiService = APIClient.APIService()

    override fun all(token : String) {
        val request = apiService.all(token)
        request.enqueue(object : Callback<WrappedListResponse<Post>>{
            override fun onFailure(call: Call<WrappedListResponse<Post>>, t: Throwable) {
                println("Log : ${t.message}")
                view?.showToast("Cannot connect to server")
            }

            override fun onResponse(call: Call<WrappedListResponse<Post>>, response: Response<WrappedListResponse<Post>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null && body.status.equals("1")){
                        view?.attachToRecycler(body.data)
                    }
                }else{ view?.showToast("Something went wrong, try again later") }
            }
        })
    }

    override fun destroy(){ view = null }
}