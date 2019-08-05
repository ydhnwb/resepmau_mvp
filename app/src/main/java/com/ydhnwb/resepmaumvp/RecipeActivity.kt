package com.ydhnwb.resepmaumvp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;
import com.ydhnwb.resepmaumvp.contracts.RecipeActivityContract
import com.ydhnwb.resepmaumvp.models.Post
import com.ydhnwb.resepmaumvp.presenters.RecipeActivityPresenter
import com.ydhnwb.resepmaumvp.utilities.Constants
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.content_recipe.*

class RecipeActivity : AppCompatActivity(), RecipeActivityContract.RecipeView {
    private var presenter : RecipeActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setOnClickListener { finish() }
        presenter = RecipeActivityPresenter(this)
        saveChanges()
        fill()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(!isNew()){ menuInflater.inflate(R.menu.menu_recipe, menu) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean{
        when(item.itemId){
            R.id.action_delete -> {
                val builder = AlertDialog.Builder(this@RecipeActivity)
                builder.apply {
                    setMessage("Are you sure to delete this?")
                    setPositiveButton("DELETE"){ dialogInterface, i ->
                        delete()
                    }
                    setNegativeButton("CANCEL"){ dialogInterface, i ->
                        dialogInterface.cancel()
                    }
                }
                val alert = builder.create()
                alert.show()
                return true
            }
            else -> { return super.onOptionsItemSelected(item) }
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPost() : Post = intent.getParcelableExtra("POST")

    private fun fill(){
        if(!isNew()){
            et_title.setText(getPost().title)
            et_content.setText(getPost().content)
        }
    }

    private fun saveChanges(){
        fab.setOnClickListener { view ->
            val title = et_title.text.toString().trim()
            val content = et_content.text.toString().trim()
            if(title.isNotEmpty() && content.isNotEmpty()){
                if(title.length > 5 && content.length > 20){
                    if(isNew()){
                        presenter?.create("Bearer ${Constants.getToken(this@RecipeActivity)}", title, content)
                    }else{
                        presenter?.update("Bearer ${Constants.getToken(this@RecipeActivity)}", getPost().id.toString(), title, content)
                    }
                }else{
                    toast("Title must be at least 5 characters and description at least 20 chars or more")
                }
            }else{
                toast("Please fill all forms first")
            }
        }
    }

    override fun showLoading() = fab.hide()

    override fun hideLoading() = fab.show()

    override fun toast(message: String) = Toast.makeText(this@RecipeActivity, message, Toast.LENGTH_LONG).show()

    override fun success() = finish()

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    private fun delete() = presenter?.delete("Bearer ${Constants.getToken(this@RecipeActivity)}", getPost().id.toString())
}
