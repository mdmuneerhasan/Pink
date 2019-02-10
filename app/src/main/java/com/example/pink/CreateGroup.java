package com.example.pink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.pink.firebase_classes.User;
import com.example.pink.utility.Connection;
import com.example.pink.utility.UserInfo;
import com.google.firebase.database.DatabaseReference;

public class CreateGroup extends AppCompatActivity {
    Connection connection;
    UserInfo userInfo;
    EditText editTextid,editTextPassword,editTextPassword2;
    Button buttonCreate;
    DatabaseReference databaseReferencesocket;
    DatabaseReference databaseReferencerecentChats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        editTextid =findViewById(R.id.group_id);
        editTextPassword =findViewById(R.id.password);
        editTextPassword2 =findViewById(R.id.password2);
        buttonCreate=findViewById(R.id.buttonCreate);


        userInfo=new UserInfo(this);
        connection=new Connection();
        databaseReferencesocket=connection.getSocketReference();
        databaseReferencerecentChats=connection.getRecentChats();


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1=editTextPassword.getText().toString();
                String password2=editTextPassword2.getText().toString();
                String groupName=editTextid.getText().toString();
                if(groupName.length()<3){
                    editTextid.setError("Minimum three character required");
                    return;
                }
                if(password1.length()<4){
                    editTextPassword.setError("Minimum four character required");
                    return;
                }
                if(!password1.equals(password2)) {
                    editTextPassword2.setError("Password not matching");
                    return;
                }
                User user=new User(userInfo.getName(),userInfo.getEmail(),userInfo.getPhoto(),userInfo.getUid(),userInfo.getNumber());
                databaseReferencesocket.child(groupName+"ms"+password1).child("admins").setValue(user);
                databaseReferencesocket.child(groupName+"ms"+password1).child("users").setValue(user);
                databaseReferencerecentChats.child(userInfo.getName()).push().setValue(groupName+"ms"+password1);
                Toast.makeText(getBaseContext(),"Group created successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
