package com.example.pink;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pink.utility.UserInfo;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    CircleImageView imageView;
    TextView tvName,tvEmail,tvPhone;
    UserInfo userInfo;
    ImageButton btnName,btnPhone;
    FloatingActionButton btnPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView=findViewById(R.id.imageViewCircle);
        tvName=findViewById(R.id.tvName);
        tvEmail=findViewById(R.id.tvEmail);
        tvPhone=findViewById(R.id.tvNumber);
        btnName=findViewById(R.id.btnName);
        btnPhone=findViewById(R.id.btnPhone);
        btnPhoto=findViewById(R.id.btnPhoto);


        userInfo = new UserInfo(this);
        tvName.setText(userInfo.getName());
        tvPhone.setText(userInfo.getNumber());
        tvEmail.setText(userInfo.getEmail());
        Picasso.get().load(userInfo.getPhoto()).into(imageView);

        btnPhone.setOnClickListener(this);
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
                alert = new AlertDialog.Builder(this);
                alert.setTitle("Enter your name")
                        .setView(view)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userInfo.setName(textInputEditText.getText().toString());
                                tvName.setText(userInfo.getName());
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
                textInputEditText.setHint("Phone number");
                alert = new AlertDialog.Builder(this);
                alert.setTitle("Enter phone number")
                        .setView(view)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userInfo.setNumber(textInputEditText.getText().toString());
                                tvPhone.setText(userInfo.getNumber());
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
}
