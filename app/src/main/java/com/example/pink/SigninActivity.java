package com.example.pink;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.example.pink.utility.Connection;
import com.example.pink.utility.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;

public class SigninActivity extends AppCompatActivity {
    Connection connection;
    DatabaseReference userRef;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        connection=new Connection();
        userRef =connection.getUser();


        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                set(FirebaseAuth.getInstance().getCurrentUser());
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("780894898674-ulql30ta4brte04lugi768dn5k1viojc.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signinInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signinInIntent,12345);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12345){
           try {
               Task<GoogleSignInAccount> acountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
               GoogleSignInAccount account = acountTask.getResult();
               AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
               FirebaseAuth.getInstance().signInWithCredential(credential);
           }catch (Exception e){
               Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
               e.printStackTrace();
           }
        }
    }

    private void set(FirebaseUser user) {
        if(user!=null){
            UserInfo userInfo=new UserInfo(getBaseContext());
            userInfo.setUid(user.getUid());
            userInfo.setEmail(user.getEmail());
            userInfo.setName(user.getDisplayName());
            Toast.makeText(this,"Welcome "+user.getDisplayName(),Toast.LENGTH_LONG).show();
            userRef.child(userInfo.getUid()).child("name").setValue(userInfo.getName());
            userRef.child(userInfo.getUid()).child("email").setValue(userInfo.getEmail());
            finish();
        }
    }
}