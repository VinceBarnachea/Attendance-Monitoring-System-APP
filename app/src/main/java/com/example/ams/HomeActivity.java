package com.example.ams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button btnCheck;
    String section,Block,Dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, new Import_check()).commit();
            navigationView.setCheckedItem(R.id.nav_check);

        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_add:
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,new Import_add()).commit();
                    break;
                case R.id.nav_check:
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,new Import_check()).commit();
                    break;
                case R.id.nav_view:
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,new Import_view()).commit();
                    break;
                case R.id.nav_change:
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,new Import_change()).commit();
                    break;

                case R.id.profileUser:
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,new Import_profile()).commit();
                    break;

                case R.id.about_us:
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,new Import_about()).commit();
                    break;


                case R.id.nav_log_out:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(this, "Successfully log out!", Toast.LENGTH_SHORT).show();
                    break;
            }
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        public void sendDataView(String s1)
        {
            section = s1;
        }
        public void sendDataBlock(String block){
            Block=block;
        }

    public void sendDataDate(String dates){
            Dates=dates;
    }

    public void goToData(View view){
        Intent intent = new Intent(HomeActivity.this, DataRetrieve.class);
       intent.putExtra("Block", section);
       intent.putExtra("Date", Dates);
        startActivity(intent);
        }


    public void goToCheckAttendance(View view){
        Intent intent = new Intent(HomeActivity.this, MainAdapter.class);
            intent.putExtra("Block",Block);
            intent.putExtra("Date",Dates);
        startActivity(intent);
    }



}
