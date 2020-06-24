package main.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import commom.model.Database;
import main.camera.presentation.AddActivity;

import com.joaquim.instagramfake.R;

import commom.view.AbstractActivity;
import main.home.datasoure.HomeDataSource;
import main.home.datasoure.HomeFireDataSource;
import main.home.datasoure.HomeLocalDataSource;
import main.home.presentation.HomeFragment;
import main.home.presentation.HomePresenter;
import main.profile.datasource.ProfileDataSource;
import main.profile.datasource.ProfileFireDataSource;
import main.profile.datasource.ProfileLocalDataSource;
import main.profile.presentation.ProfileFragment;
import main.profile.presentation.ProfilePresenter;
import main.search.datasource.SearchDataSource;
import main.search.datasource.SearchFireDataSource;
import main.search.datasource.SearchLocalDataSource;
import main.search.presentation.SearchFragment;
import main.search.presentation.SearchPresenter;

public class MainActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView {

    public static final int LOGIN_ACTIVITY = 0;
    public static final int REGISTER_ACTIVITY = 1;
    public static String ACT_SOURCE = "act_source";
    private ProfilePresenter profilePresenter;
    private HomePresenter homePresenter;
    private SearchPresenter searchPresenter;

    Fragment homeFragment;
    Fragment profileFragment;
    //    Fragment cameraFragment;
    Fragment searchFragment;
    Fragment active;

    ProfileFragment profileDetailFragment;

    public static void launch(Context context, int source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACT_SOURCE, source);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            Drawable drawable = getResources().getDrawable(R.drawable.ic_insta_camera);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }

    @Override
    protected void onInject() {
        ProfileDataSource profileDataSource = new ProfileFireDataSource();
        HomeDataSource homeDataSource = new HomeFireDataSource();
        profilePresenter = new ProfilePresenter(profileDataSource);
        homePresenter = new HomePresenter(homeDataSource);

        homeFragment = HomeFragment.newInstance(this, homePresenter);
        profileFragment = ProfileFragment.newInstance(this, profilePresenter);

        SearchDataSource searchDataSource = new SearchFireDataSource();
        searchPresenter = new SearchPresenter(searchDataSource);

//        cameraFragment = new CameraFragment();
        searchFragment = SearchFragment.newInstance(this, searchPresenter);

        active = homeFragment;

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.main_fragment, profileFragment).hide(profileFragment).commit();
//        fm.beginTransaction().add(R.id.main_fragment, cameraFragment).hide(cameraFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, searchFragment).hide(searchFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, homeFragment).hide(homeFragment).commit();
    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.main_progress).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.main_progress).setVisibility(View.GONE);
    }

    @Override
    public void showProfile(String user) {
        ProfileDataSource profileLocalDataSource = new ProfileFireDataSource();
        ProfilePresenter profilePresenter = new ProfilePresenter(profileLocalDataSource, user);

        profileDetailFragment = ProfileFragment.newInstance(this, profilePresenter);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fragment, profileDetailFragment, "detail");
        transaction.hide(active);
        transaction.commit();

        scrollToolbarEnabled(true);

        if (getSupportActionBar() != null) {
            Drawable drawable = findDrawable(R.drawable.ic_arrow_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void disposeProfileDetail() {
        if (getSupportActionBar() != null) {
            Drawable drawable = findDrawable(R.drawable.ic_insta_camera);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(profileDetailFragment);
        transaction.show(active);
        transaction.commit();

        profileDetailFragment = null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BottomNavigationView bv = findViewById(R.id.main_bottom_nav);

        bv.setOnNavigationItemSelectedListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int source = extras.getInt(ACT_SOURCE);
            if (source == REGISTER_ACTIVITY) {
                getSupportFragmentManager().beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(true);

                profilePresenter.findUsers();
            }
        }


    }

    @Override
    public void scrollToolbarEnabled(boolean enabled) {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.main_appbar);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();

        if (enabled) {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
            appBarLayout.setLayoutParams(appBarLayoutParams);
        } else {
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
            appBarLayout.setLayoutParams(appBarLayoutParams);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        FragmentManager fm = getSupportFragmentManager();
        switch (menuItem.getItemId()) {
            case R.id.menu_bottom_home:
                if (profileDetailFragment != null)
                    disposeProfileDetail();
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
//                homePresenter.findFeed();
                scrollToolbarEnabled(false);
                return true;
            case R.id.menu_bottom_search:
                if (profileDetailFragment == null){
                fm.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                scrollToolbarEnabled(false);}
                return true;

            case R.id.menu_bottom_add:
//                fm.beginTransaction().hide(active).show(cameraFragment).commit();
//                active = cameraFragment;
                AddActivity.launch(this);
                return true;

            case R.id.menu_bottom_profile:
                if (profileDetailFragment != null)
                    disposeProfileDetail();
                fm.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(true);
                profilePresenter.findUsers();
                return true;
        }
        return false;
    }
}