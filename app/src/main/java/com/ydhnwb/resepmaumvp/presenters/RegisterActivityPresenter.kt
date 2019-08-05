package com.ydhnwb.resepmaumvp.presenters

import android.content.Context
import com.ydhnwb.resepmaumvp.contracts.RegisterActivityContract
import com.ydhnwb.resepmaumvp.models.User
import com.ydhnwb.resepmaumvp.responses.WrappedResponse
import com.ydhnwb.resepmaumvp.utilities.APIClient
import com.ydhnwb.resepmaumvp.utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivityPresenter(v : RegisterActivityContract.RegisterView) : RegisterActivityContract.RegisterPresenter {
    private var view : RegisterActivityContract.RegisterView? = v
    private var apiService = APIClient.APIService()

    override fun register(name: String, email: String, password: String, context: Context) {
        view?.showLoading()
        val request = apiService.register(name, email, password)
        request.enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Cannot connect to the server")
                println(t.message)
                view?.hideLoading()
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null && body.status.equals("1")){
                        Constants.setToken(context, body.data.api_token!!)
                        view?.showToast("Register successfull")
                        view?.successRegister()
                    }else{
                        view?.showToast("Failed to login, email might be already used")
                    }
                    view?.hideLoading()
                }else{
                    view?.showToast("Something went wrong, try again later")
                    view?.hideLoading()
                }
            }
        })
    }

    override fun destroy(){ view = null }
}