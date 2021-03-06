package com.example.pink;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.pink.firebase_classes.Chat;
import com.example.pink.utility.ChatRecyclerAdapter;
import com.example.pink.utility.Connection;
import com.example.pink.firebase_classes.Item;
import com.example.pink.utility.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    FloatingActionButton btnSend;
    EditText edtMessage;
    String uid;
    DatabaseReference databaseReference;
    Connection connection;
    UserInfo userInfo;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    ChatRecyclerAdapter chatRecyclerAdapter;
    ArrayList<Chat> arrayList;
    CircleImageView imageView;
    TextView tvName, tvInfo;
    DatabaseReference socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        imageView = findViewById(R.id.imageViewCircle);
        tvName = findViewById(R.id.tvName);
        tvInfo = findViewById(R.id.tvInfo);
        btnSend = findViewById(R.id.btnSend);
        edtMessage = findViewById(R.id.edtMessage);
        recyclerView = findViewById(R.id.recyclerView);
        uid = getIntent().getStringExtra("uid");
        connection = new Connection();
        userInfo = new UserInfo(this);
        arrayList = new ArrayList<>();

        socket=connection.getSocketReference();

        chatRecyclerAdapter = new ChatRecyclerAdapter(arrayList, this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatRecyclerAdapter);


        socket.child(uid).child("info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Item item=dataSnapshot.getValue(Item.class);
                Picasso.get().load(item.getPhoto()).into(imageView);
                tvName.setText(item.getName());
                tvInfo.setText(item.getInfo());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = edtMessage.getText().toString();
                if (text.length() > 0) {
                    databaseReference = connection.getMessage();
                    Chat chat = new Chat(text, "typing...", userInfo.getName(), userInfo.getUid());
                    databaseReference.child(uid).child(userInfo.getUid()).setValue(chat);
                    linearLayoutManager.scrollToPosition(arrayList.size());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edtMessage.getText().toString();
                if (text.length() > 0) {
                    databaseReference.child(uid).child(userInfo.getUid()).removeValue();
                    String time = new SimpleDateFormat("hh:mm a; dd-MM-yyyy").format(new Date());
                    databaseReference = connection.getMessage();
                    Chat chat = new Chat(text, time, userInfo.getName(), userInfo.getUid());
                    databaseReference.child(uid).push().setValue(chat);
                    socket.child(uid).child("info").child("time").setValue(time);
                    socket.child(uid).child("info").child("message").setValue(text);
                    edtMessage.setText("");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference connectionMessage = connection.getMessage();
        connectionMessage.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Chat chat = dataSnapshot1.getValue(Chat.class);
                    arrayList.add(chat);
                }
                chatRecyclerAdapter.notifyDataSetChanged();
                linearLayoutManager.scrollToPosition(arrayList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference = connection.getMessage();
        databaseReference.child(uid).child(userInfo.getUid()).removeValue();
    }
}
