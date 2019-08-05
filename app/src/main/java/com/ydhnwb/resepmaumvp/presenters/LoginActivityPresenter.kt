package com.ydhnwb.resepmaumvp.presenters

import android.content.Context
import com.ydhnwb.resepmaumvp.contracts.LoginActivityContract
import com.ydhnwb.resepmaumvp.models.User
import com.ydhnwb.resepmaumvp.responses.WrappedResponse
import com.ydhnwb.resepmaumvp.utilities.APIClient
import com.ydhnwb.resepmaumvp.utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityPresenter(v : LoginActivityContract.LoginView) : LoginActivityContract.LoginPresenter {
    private var view : LoginActivityContract.LoginView? = v
    private var apiService = APIClient.APIService()

    override fun login(email: String, password: String, context: Context) {
        val request = apiService.login(email, password)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Cannot connect to server")
                println(t.message)
                view?.hideLoading()
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null && body.status.equals("1")){
                        Constants.setToken(context, body.data.api_token!!)
                        view?.showToast("Welcome back ${body.data.name}")
                        view?.successLogin()
                    }else{
                        view?.showToast("Failed to login. Check your email and password")
                    }
                    view?.hideLoading()
                }else{
                    view?.hideLoading()
                    view?.showToast("Something went wrong, try again later")
                }
            }
        })
    }

    override fun destroy() { view = null }
}