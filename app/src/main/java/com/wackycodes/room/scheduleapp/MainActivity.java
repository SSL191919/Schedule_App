package com.wackycodes.room.scheduleapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    public static FrameLayout frameLayoutMainActivity;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // Assign
        frameLayoutMainActivity = findViewById( R.id.main_content_frame_layout );

        toolbar = findViewById( R.id.app_toolbar );
        drawer = findViewById( R.id.drawer_layout );
//        navigationView = findViewById( R.id.nav_view );
        setSupportActionBar( toolbar );
        try {
            getSupportActionBar().setTitle( "Set your schedule" );
            getSupportActionBar().setDisplayShowTitleEnabled( true );
        }catch (NullPointerException ignored){ }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this,drawer,toolbar,  R.string.nav_open , R.string.nav_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();


        setMainFrameLayout( new MainFragment() );

    }


    private void setMainFrameLayout(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( frameLayoutMainActivity.getId(), fragment );
        fragmentTransaction.commit();
    }


    
}