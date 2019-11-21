package kz.ktzh.hserailways;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;

import kz.ktzh.hserailways.entity.UserInfo;
import kz.ktzh.hserailways.fragments.HomeFragment;
import kz.ktzh.hserailways.fragments.MyIncFragment;
import kz.ktzh.hserailways.fragments.MyProfileFragment;
import kz.ktzh.hserailways.fragments.SendIncFragment;
import kz.ktzh.hserailways.network.NetworkServiceResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        MyIncFragment.OnFragmentInteractionListener,
        SendIncFragment.OnFragmentInteractionListener,
        MyProfileFragment.OnFragmentInteractionListener{


    TextView tvUsername;
    TextView tvPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        onGetUserInfo(username, NetworkServiceResource.getInstance().getAccessToken());
        View hView = navigationView.getHeaderView(0);
        tvUsername = hView.findViewById(R.id.tvUsername);
        tvPosition = hView.findViewById(R.id.tvPosition);
        Fragment fragment = null;
        fragment = new HomeFragment();
        displaySelectedFragment(fragment);
    }

    public void onGetUserInfo(final String username, String accessToken){
        NetworkServiceResource.
                getInstance().
                getJSONApi().
                getUserInfo("Bearer " + accessToken, username).
                enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        NetworkServiceResource.getInstance().setUserInfo(response.body());
                        Log.i("USERINFO", ""+ NetworkServiceResource.getInstance().getUserInfo().getFullname());
                        String username = NetworkServiceResource.getInstance().getUserInfo().getFullname();
                        String position = NetworkServiceResource.getInstance().getUserInfo().getPosition();
                        tvUsername.setText(""+username);
                        tvPosition.setText(""+position);
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.i("USERINFO", "ERROR WHEN GETTING USERINFO!");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            displaySelectedFragment(fragment);
        } else if (id == R.id.nav_my_inc) {
            fragment = new MyIncFragment();
            displaySelectedFragment(fragment);
        } else if (id == R.id.nav_send_inc) {
            fragment = new SendIncFragment();
            displaySelectedFragment(fragment);
        } else if (id == R.id.nav_myprofile) {
            fragment = new MyProfileFragment();
            displaySelectedFragment(fragment);
        }

        /*else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
