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
import com.example.pink.fragments.Suggestion
import com.example.pink.utility.Connection
import com.example.pink.firebase_classes.Item
import com.example.pink.utility.PagerAdapter
import com.example.pink.utility.UserInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var userInfo :UserInfo
    val list by lazy {
        ArrayList<Fragment>()
    }
     val alert by lazy {
         AlertDialog.Builder(this)
     }
    lateinit var item: Item
    lateinit var groupdetail: Item
    var groupName: String = ""
    var password1: String = ""
    lateinit var password:EditText
    lateinit var group_id:EditText
    lateinit var databaseReferencesocket :DatabaseReference
    lateinit var databaseReferencerecentChats :DatabaseReference
    lateinit var connection:Connection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.add(Chats())
//        list.add(People())
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
        password=view.findViewById(R.id.password)
        group_id= view.findViewById(R.id.group_id)
        password.setText(password1)
        group_id.setText(groupName)
        item= Item(
            userInfo.photo,
            userInfo.name,
            userInfo.info,
            null,
            userInfo.number,
            userInfo.uid
        )
        alert.setTitle("New Group")
            .setView(view)
            .setPositiveButton("ok") { dialog, which ->
                password1 = password.text.toString()
                groupName = group_id.text.toString()
                if (groupName.length < 3) {
                    Toast.makeText(baseContext, "Minimum three character name required", Toast.LENGTH_SHORT).show()
                }else if (password1.length < 4) {
                    Toast.makeText(baseContext, "Minimum four character password required", Toast.LENGTH_SHORT).show()
                }else {
                    databaseReferencesocket=connection.socketReference
                    databaseReferencerecentChats=connection.recentChats
                    databaseReferencesocket.child(groupName + "ms" + password1).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val value = dataSnapshot.child("admins").hasChildren()
                            if(!value) {
                                databaseReferencesocket.child(groupName + "ms" + password1).child("admins").child(userInfo.uid)
                                    .setValue(item)
                                Toast.makeText(baseContext, "Group created successfully", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(baseContext, "Group joined successfully", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                    groupdetail= Item(
                        userInfo.photo,
                        groupName,
                        "created by " + userInfo.name,
                        null,
                        "",
                        groupName + "ms" + password1
                    )
                    databaseReferencesocket.child(groupName + "ms" + password1).child("info")
                        .setValue(groupdetail)
                    databaseReferencesocket.child(groupName + "ms" + password1).child("users").child(userInfo.uid)
                        .setValue(item)
                    groupdetail.info = "created on "+SimpleDateFormat("hh:mm a; dd-MM-yyyy").format(Date())
                    databaseReferencerecentChats.child(userInfo.uid).child(groupName + "ms" + password1)
                        .setValue(groupdetail)
                    password1="";
                    groupName="";
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
