package com.ydhnwb.resepmaumvp.presenters

import com.ydhnwb.resepmaumvp.contracts.RecipeActivityContract
import com.ydhnwb.resepmaumvp.models.Post
import com.ydhnwb.resepmaumvp.responses.WrappedResponse
import com.ydhnwb.resepmaumvp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeActivityPresenter(v : RecipeActivityContract.RecipeView) : RecipeActivityContract.RecipePresenter {
    private var view : RecipeActivityContract.RecipeView? = v
    private var apiService = APIClient.APIService()

    override fun create(token: String, title: String, content: String) {
        view?.showLoading()
        val request = apiService.create(token, title, content)
        request.enqueue(object : Callback<WrappedResponse<Post>>{
            override fun onFailure(call: Call<WrappedResponse<Post>>, t: Throwable) {
                println(t.message)
                view?.toast("Cannot connect to server")
                view?.hideLoading()
            }

            override fun onResponse(call: Call<WrappedResponse<Post>>, response: Response<WrappedResponse<Post>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null && body.status.equals("1")){
                        view?.toast("Success create recipe")
                        view?.success()
                    }else{
                        view?.toast("Cannot create data, try again later")
                    }
                    view?.hideLoading()
                }else{
                    view?.toast("Something went wrong, try again later")
                    view?.hideLoading()
                }
            }
        })
    }

    override fun update(token: String, id: String, title: String, content: String) {
        view?.showLoading()
        val request = apiService.update(token, id, title, content)
        request.enqueue(object : Callback<WrappedResponse<Post>>{
            override fun onFailure(call: Call<WrappedResponse<Post>>, t: Throwable) {
                view?.hideLoading()
                view?.toast("Cannot connect to server")
            }

            override fun onResponse(call: Call<WrappedResponse<Post>>, response: Response<WrappedResponse<Post>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null && body.status.equals("1")){
                        view?.toast("Successfully updated")
                        view?.success()
                    }else{
                        view?.toast("Failed to update data")
                    }
                    view?.hideLoading()
                }else{
                    view?.toast("Something went wrong, try again later")
                    view?.hideLoading()
                }
            }
        })
    }

    override fun delete(token: String, id: String) {
        view?.showLoading()
        val req = apiService.delete(token, id)
        req.enqueue(object : Callback<WrappedResponse<Post>>{
            override fun onFailure(call: Call<WrappedResponse<Post>>, t: Throwable) {
                view?.hideLoading()
                view?.toast("Cannot connect to server")
            }

            override fun onResponse(call: Call<WrappedResponse<Post>>, response: Response<WrappedResponse<Post>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null && body.status.equals("1")){
                        view?.toast("Successfully deleted")
                        view?.success()
                    }else{
                        view?.toast("Failed to delete data")
                    }
                    view?.hideLoading()
                }else{
                    view?.toast("Something went wrong, try again later")
                    view?.hideLoading()
                }
            }
        })
    }

    override fun destroy() {
        view = null
    }
}