package com.gorih.familycoffers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.gorih.familycoffers.controller.DBWorker;
import com.gorih.familycoffers.controller.TabsPagerFragmentAdapter;
import com.gorih.familycoffers.model.Expanse;
import com.gorih.familycoffers.presenter.dialog.dlgAddExpanse;
import com.gorih.familycoffers.presenter.fragment.StatisticsFragment;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.security.interfaces.ECKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements dlgAddExpanse.OnNewExpanseAddedListener {

    private Toolbar toolbar;
    private static final int LAYOUT = R.layout.activity_main;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private DBWorker dbWorker = DBWorker.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initNavigationView();
        initTabs();
//        DBWorker.getInstance(this).eraseDB();
    }


    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.view_navigation_open, R.string.view_navigation_close);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.action_tab_one_item:
                        showTabExpanses();
                        break;
                    case R.id.action_tab_two_item:
                        showTabStatistics();
                        break;
                    case R.id.action_tab_three_item:
                        showTabHistory();
                        break;
                }
                return true;
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }

    @Override
    public void onNewExpanseAdded(float newExpanseValue, long date, String category) {
        Expanse newExpanse = new Expanse(newExpanseValue, date, category);

        dbWorker.addToDB(newExpanse);
        Log.d("dlgAddExpanse", "new expanse added: " + newExpanse.toString());

        StatisticsFragment.statisticsFragment.refresh();
    }

    private void showTabHistory(){ viewPager.setCurrentItem(Constants.TAB_HISTORY); }
    private void showTabExpanses(){
        viewPager.setCurrentItem(Constants.TAB_EXPANSES_LIST);
    }
    private void showTabStatistics(){ viewPager.setCurrentItem(Constants.TAB_STATISTICS); }
}
