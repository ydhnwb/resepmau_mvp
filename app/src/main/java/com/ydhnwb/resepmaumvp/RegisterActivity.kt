package com.ydhnwb.resepmaumvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ydhnwb.resepmaumvp.contracts.RegisterActivityContract
import com.ydhnwb.resepmaumvp.presenters.RegisterActivityPresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterActivityContract.RegisterView {
    private var presenter : RegisterActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        presenter = RegisterActivityPresenter(this)
        doRegister()
    }

    override fun showToast(message: String) = Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()

    override fun successRegister() = finish()

    override fun showLoading() {
        btn_register.isEnabled = false
        btn_back.isEnabled = false
        loading.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        btn_register.isEnabled = true
        btn_back.isEnabled = true
        loading.apply {
            isIndeterminate = false
            progress = 0
        }
    }

    private fun doRegister(){
        btn_register.setOnClickListener {
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val pass = et_password.text.toString().trim()
            if(name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
                if(pass.length > 8){
                    if(name.length > 5){
                        presenter?.register(name, email, pass, this@RegisterActivity)
                    }else{
                        showToast("Name must be 5 characters or more")
                    }
                }else{
                    showToast("Password must be 8 characters or more")
                }
            }else{
                showToast("Please fill all forms first")
            }
        }

        btn_back.setOnClickListener { finish() }
    }
}
