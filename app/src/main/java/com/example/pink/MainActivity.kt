package com.example.pink

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.pink.fragments.Chats
import com.example.pink.fragments.People
import com.example.pink.fragments.Suggestion
import com.example.pink.utility.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val list by lazy {
        ArrayList<Fragment>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.add(Chats())
        list.add(People())
        list.add(Suggestion())
        val pagerAdapter=PagerAdapter(supportFragmentManager,list)
        viewPager.adapter=pagerAdapter
        tabLayout.setupWithViewPager(viewPager,true)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when(item.itemId){
                R.id.search->{
                    val intent = Intent(this, SigninActivity::class.java)
                    startActivity(intent)
                }
                R.id.my_profile->{
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.create_group->{
                    val intent = Intent(this, CreateGroup::class.java)
                    startActivity(intent)
                }
            }
        }
        return true
    }
    fun action(view2: View){

        val view=LayoutInflater.from(this).inflate(R.layout.alert_join_group,null,false)

        val alert = AlertDialog.Builder(this)
            .setTitle("New Group")
            .setView(view)
            .setPositiveButton("ok") { dialog, which ->


            }
            .setNegativeButton("cancel") { dialog, which ->

            }
        alert.show()
    }

}
