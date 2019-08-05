package com.ydhnwb.resepmaumvp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ydhnwb.resepmaumvp.adapters.PostAdapter
import com.ydhnwb.resepmaumvp.contracts.MainActivityContract
import com.ydhnwb.resepmaumvp.models.Post
import com.ydhnwb.resepmaumvp.presenters.MainActivityPresenter
import com.ydhnwb.resepmaumvp.utilities.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() , MainActivityContract.MainActivityView{
    private var presenter : MainActivityContract.MainActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        checkIsLoggedIn()
        fab.setOnClickListener { view ->
            startActivity(Intent(this@MainActivity, RecipeActivity::class.java).apply {
                putExtra("IS_NEW", true)
            })
        }
        presenter = MainActivityPresenter(this)
    }

    private fun getPosts() = presenter?.all("Bearer ${Constants.getToken(this@MainActivity)}")

    override fun attachToRecycler(posts: List<Post>) {
        rv_post.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(posts, this@MainActivity)
        }
    }

    override fun showToast(message: String) = Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val b = AlertDialog.Builder(this@MainActivity)
                b.apply {
                    setMessage("Are you sure want to log out?")
                    setPositiveButton("LOGOUT"){ dialogInterface, i ->
                        Constants.clearToken(this@MainActivity)
                        checkIsLoggedIn()
                    }
                    setNegativeButton("CANCEL"){ dialogInterface, i -> dialogInterface.cancel()  }
                }
                val alert = b.create()
                alert.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    private fun checkIsLoggedIn(){
        val token = Constants.getToken(this@MainActivity)
        if(token == null || token.equals("UNDEFINED")){
            startActivity(Intent(this@MainActivity, LoginActivity::class.java).also { finish() })
        }
    }

    override fun onResume() {
        super.onResume()
        getPosts()
    }
}
