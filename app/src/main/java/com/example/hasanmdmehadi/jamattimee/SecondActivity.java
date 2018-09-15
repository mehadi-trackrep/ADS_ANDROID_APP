package com.example.hasanmdmehadi.jamattimee;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout_fun(2);
            }
        });
    }

    private void Logout_fun(int item_id){
        if(item_id == 2) {
            firebaseAuth.signOut();
            finish(); // to finish current activity
            startActivity(new Intent(SecondActivity.this, LoginActivity.class));
        }
        else if(item_id == 1){
//            finish(); // to finish current activity
            startActivity(new Intent(SecondActivity.this, MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu : Logout_fun(2);
            case R.id.homeMenu : Logout_fun(1);
        }
        return super.onOptionsItemSelected(item);
    }
}
