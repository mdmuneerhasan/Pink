package com.example.pink;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pink.firebase_classes.User;
import com.example.pink.utility.Connection;
import com.example.pink.utility.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    CircleImageView imageView;
    TextView tvName,tvEmail,tvPhone,tvAbout;
    UserInfo userInfo;
    User user;
    ImageButton btnName,btnPhone,btnAbout;
    FloatingActionButton btnPhoto;
    Connection connection;
    DatabaseReference databaseReferenceuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        connection=new Connection();
        databaseReferenceuser=connection.getUser();

        imageView=findViewById(R.id.imageViewCircle);
        tvName=findViewById(R.id.tvName);
        tvEmail=findViewById(R.id.tvEmail);
        tvAbout=findViewById(R.id.tvAbout);
        tvPhone=findViewById(R.id.tvNumber);
        btnName=findViewById(R.id.btnName);
        btnPhone=findViewById(R.id.btnPhone);
        btnPhoto=findViewById(R.id.btnPhoto);
        btnAbout=findViewById(R.id.btnAbout);



        userInfo = new UserInfo(this);
        tvName.setText(userInfo.getName());
        tvPhone.setText(userInfo.getNumber());
        tvEmail.setText(userInfo.getEmail());
        Picasso.get().load(userInfo.getPhoto()).into(imageView);

        btnPhone.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnName.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final EditText textInputEditText;
        AlertDialog.Builder alert;
        final View view;

        switch (v.getId()){
            case R.id.btnPhoto:
                view = LayoutInflater.from(this).inflate(R.layout.alert_take_input,null,false);
                textInputEditText = view.findViewById(R.id.textName);
                textInputEditText.setHint("Photo url");
                textInputEditText.setText("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg");
                alert = new AlertDialog.Builder(this);
                alert.setTitle("Enter photo url")
                        .setView(view)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userInfo.setPhoto(textInputEditText.getText().toString());
                                Picasso.get().load(userInfo.getPhoto()).into(imageView);
                                databaseReferenceuser.child(userInfo.getUid()).child("photo").setValue(userInfo.getPhoto());
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alert.show();
                break;
            case R.id.btnAbout:
                view = LayoutInflater.from(this).inflate(R.layout.alert_take_input,null,false);
                textInputEditText = view.findViewById(R.id.textName);
                textInputEditText.setHint("About");
                if(!userInfo.getInfo().equals("about"))textInputEditText.setText(userInfo.getInfo());
                alert = new AlertDialog.Builder(this);
                alert.setTitle("About you")
                        .setView(view)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userInfo.setInfo(textInputEditText.getText().toString());
                                tvAbout.setText(userInfo.getInfo());
                                databaseReferenceuser.child(userInfo.getUid()).child("about").setValue(userInfo.getInfo());
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alert.show();
                break;
            case R.id.btnName:
                view = LayoutInflater.from(this).inflate(R.layout.alert_take_input,null,false);
                textInputEditText = view.findViewById(R.id.textName);
                textInputEditText.setHint("Name");
                textInputEditText.setText(userInfo.getName());
                alert = new AlertDialog.Builder(this);
                alert.setTitle("Enter your name")
                        .setView(view)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userInfo.setName(textInputEditText.getText().toString());
                                tvName.setText(userInfo.getName());
                                databaseReferenceuser.child(userInfo.getUid()).child("name").setValue(userInfo.getName());
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alert.show();
                break;
            case R.id.btnPhone:
                view = LayoutInflater.from(this).inflate(R.layout.alert_take_input,null,false);
                textInputEditText = view.findViewById(R.id.textName);
                if(!userInfo.getNumber().equals("phone number"))textInputEditText.setText(userInfo.getNumber());
                textInputEditText.setHint("Phone number");
                alert = new AlertDialog.Builder(this);
                alert.setTitle("Enter phone number")
                        .setView(view)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userInfo.setNumber(textInputEditText.getText().toString());
                                tvPhone.setText(userInfo.getNumber());
                                databaseReferenceuser.child(userInfo.getUid()).child("number").setValue(userInfo.getNumber());
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                alert.show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!userInfo.getUid().equals("")){
            databaseReferenceuser.child(userInfo.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user=dataSnapshot.getValue(User.class);
                    if(user.getAbout()!=null){
                        userInfo.setInfo(user.getAbout());
                        tvAbout.setText(user.getAbout());
                    }
                    if(user.getNumber()!=null){
                        userInfo.setNumber(user.getNumber());
                        tvPhone.setText(user.getNumber());
                    }
                    if(user.getPhoto()!=null){
                        userInfo.setPhoto(user.getPhoto());
                        Picasso.get().load(userInfo.getPhoto()).into(imageView);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
