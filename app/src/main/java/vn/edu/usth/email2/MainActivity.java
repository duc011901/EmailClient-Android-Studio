package vn.edu.usth.email2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.edu.usth.email2.fragment.ChangePasswordFragment;
import vn.edu.usth.email2.fragment.InboxFragment;
import vn.edu.usth.email2.fragment.ProfileFragment;
import vn.edu.usth.email2.fragment.SentFragment;
import vn.edu.usth.email2.fragment.StarredFragment;
import vn.edu.usth.email2.fragment.TrashFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private static final int FRAGMENT_INBOX = 0;
    private static final int FRAGMENT_SENT = 1;
    private static final int FRAGMENT_STARRED = 2;
    private static final int PROFILE = 3;
    private static final int PW = 4;

    private int mCurrentFragment = FRAGMENT_INBOX;
    private ImageView imageView;
    private TextView tvname, tvemail;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();

        mDrawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new InboxFragment());
        navigationView.getMenu().findItem(R.id.inbox).setChecked(true);

        information();
    }

    private void initUI() {
        navigationView = findViewById(R.id.nav);
        imageView = navigationView.getHeaderView(0).findViewById(R.id.dp);
        tvname = navigationView.getHeaderView(0).findViewById(R.id.name);
        tvemail = navigationView.getHeaderView(0).findViewById(R.id.email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.inbox){
            if(mCurrentFragment != FRAGMENT_INBOX){
                replaceFragment(new InboxFragment());
                mCurrentFragment = FRAGMENT_INBOX;
            }
        }

        if( id == R.id.sent){
            if(mCurrentFragment != FRAGMENT_SENT){
                replaceFragment(new SentFragment());
                mCurrentFragment = FRAGMENT_SENT;
            }
        }

        if( id == R.id.starred){
            if(mCurrentFragment != FRAGMENT_STARRED){
                replaceFragment(new StarredFragment());
                mCurrentFragment = FRAGMENT_STARRED;
            }
        }

        if( id == R.id.password){
            if(mCurrentFragment != PW){
                replaceFragment(new ChangePasswordFragment());
                mCurrentFragment = PW;
            }
        }


        if( id == R.id.signout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if( id == R.id.profile){
            if(mCurrentFragment != PROFILE){
            replaceFragment(new ProfileFragment());
            mCurrentFragment = PROFILE;
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void onCompose(View view){
        Intent intent = new Intent(this,ComposeActivity.class);
        startActivity(intent);
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    public void information(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }else{
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            tvname.setText(name);
            tvemail.setText(email);
            Glide.with(this).load(photoUrl).error(R.drawable.ic_baseline_account_circle_24).into(imageView);
        }
    }
}