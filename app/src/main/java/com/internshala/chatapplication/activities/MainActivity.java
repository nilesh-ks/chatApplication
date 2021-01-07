package com.internshala.chatapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.internshala.chatapplication.R;


public class MainActivity extends AppCompatActivity {

//    private Button btnLogout;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBarMain);
        progressBar.setVisibility(View.GONE);


    }
    private void logout(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Log Out");
        dialog.setMessage("Do you want to Log out?");
        dialog.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface,int d){
                progressBar.setVisibility(View.VISIBLE);
                auth.signOut();
                SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("isLoggedIn",false).commit();
                Context context=getApplicationContext();
                Intent intent=new Intent(context,LoginActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
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
        finish();
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