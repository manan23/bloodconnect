package com.example.user.bloodconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.user.bloodconnect.R;
import com.example.user.bloodconnect.adapter.tabadapter;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements ActionBar.TabListener,NavigationView.OnNavigationItemSelectedListener
{
    private FirebaseAuth mauth;
    private ViewPager viewPager;
    private tabadapter mAdapter;
    private ActionBar actionBar;
    private TabLayout tabLayout;

    //Tab title
    private String []tab={"CURRENT NEED","FIND DONAR","DONATION HISTORY"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         mauth=FirebaseAuth.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FindDonarActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar =getSupportActionBar();
        mAdapter = new tabadapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
        actionBar.setHomeButtonEnabled(false);
    // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Adding Tab

    //    for (String tabName : tab){
      //      actionBar.addTab(actionBar.newTab().setText(tabName).setTabListener(this));
      //  }
/*
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                actionBar.setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.profile) {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.notify)
        {
            Intent intent =new Intent(HomeActivity.this,NotifyActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.signout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.fd) {
            Intent intent = new Intent(HomeActivity.this,FindDonarActivity.class);
            startActivity(intent);
            return true;
        }

        if(id ==R.id.dh)
            {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                  startActivity(intent);
            }

        if(id ==R.id.fb)
        {
            Intent intent = new Intent(HomeActivity.this,FeedbackFormActivity.class);
            startActivity(intent);
        }
        if(id ==R.id.Acceptor)
        {
            Intent intent=new Intent(HomeActivity.this,AccepterActivity.class);
            startActivity(intent);
        }
        if(id ==R.id.team)
        {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        if(id ==R.id.rf)
        {
            Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}

