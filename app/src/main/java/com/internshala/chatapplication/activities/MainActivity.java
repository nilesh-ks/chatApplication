package com.internshala.chatapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.internshala.chatapplication.R;


public class MainActivity extends AppCompatActivity {

//    private Button btnLogout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();


//        btnLogout=findViewById(R.id.btnLogout);
//        btnLogout.setOnClickListener(view -> {
//            logout();
//        });
    }
    private void logout(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Log Out");
        dialog.setMessage("Do you want to Log out?");
        dialog.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface,int d){
                auth.signOut();
                SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("isLoggedIn",false).commit();
                Intent intent=new Intent(new MainActivity(),LoginActivity.class);
                startActivity(intent);
                finish();
            }

        })
        .setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface,int d){

            }
        });
        dialog.create();
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
            case R.id.profile:
                //todo
            default:
                break;
        }
        return false;
    }
}