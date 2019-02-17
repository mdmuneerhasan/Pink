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
import android.widget.EditText
import android.widget.Toast
import com.example.pink.fragments.Chats
import com.example.pink.fragments.People
import com.example.pink.fragments.Suggestion
import com.example.pink.utility.Connection
import com.example.pink.utility.PagerAdapter
import com.example.pink.utility.UserInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert_join_group.*
import kotlinx.android.synthetic.main.alert_join_group.view.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var userInfo :UserInfo
    val list by lazy {
        ArrayList<Fragment>()
    }
    lateinit var password:EditText
    lateinit var group_id:EditText
    lateinit var databaseReferencesocket :DatabaseReference
    lateinit var databaseReferencerecentChats :DatabaseReference
    lateinit var connection:Connection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.add(Chats())
        list.add(People())
        list.add(Suggestion())
        val pagerAdapter=PagerAdapter(supportFragmentManager,list)
        viewPager.adapter=pagerAdapter
        tabLayout.setupWithViewPager(viewPager,true)
        userInfo = UserInfo(this)
        connection= Connection()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when(item.itemId){
                R.id.search->{

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
                password=view.findViewById(R.id.password)
                group_id= view.findViewById(R.id.group_id)
                val password1 = password.text.toString()
                val groupName = group_id.text.toString()
                if (groupName.length < 3) {
                    group_id.error="Minimum three character required"
                }else if (password1.length < 4) {
                    password.error="Minimum four character required"
                }else {
                    databaseReferencesocket=connection.socketReference
                    databaseReferencerecentChats=connection.recentChats
                    databaseReferencesocket.child(groupName + "ms" + password1).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val value = dataSnapshot.child("admins").hasChildren()
                            if(!value) {
                                databaseReferencesocket.child(groupName + "ms" + password1).child("admins").child(userInfo.uid)
                                    .setValue(userInfo.uid)
                                Toast.makeText(baseContext, "Group created successfully", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(baseContext, "Group joined successfully", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                    databaseReferencesocket.child(groupName + "ms" + password1).child("users").child(userInfo.uid)
                        .setValue(userInfo.uid)
                    databaseReferencerecentChats.child(userInfo.uid).child(groupName + "ms" + password1)
                        .setValue(groupName)
                    }
            }
            .setNegativeButton("cancel") { dialog, which ->

            }
        alert.show()
    }

    override fun onStart() {
        super.onStart()
        if (userInfo.uid.equals("")){

            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)


        }
    }
}
