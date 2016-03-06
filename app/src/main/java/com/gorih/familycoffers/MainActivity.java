package com.gorih.familycoffers;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gorih.familycoffers.controller.CategoriesListAdapter;
import com.gorih.familycoffers.controller.TabsPagerFragmentAdapter;
import com.gorih.familycoffers.model.Category;
import com.gorih.familycoffers.presenter.dialog.dlgAddCategory;

public class MainActivity extends AppCompatActivity implements dlgAddCategory.OnNewCategoryAddedListener{

    private Toolbar toolbar;
    private static final int LAYOUT = R.layout.activity_main;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initNavigationView();
        initTabs();
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
    public void onNewCategoryAdded(String newCategoryName) {
        CategoriesListAdapter.addCategory(new Category(newCategoryName, R.color.colorDefaultCategory));
    }

    private void showTabHistory(){ viewPager.setCurrentItem(Constants.TAB_HISTORY); }
    private void showTabExpanses(){
        viewPager.setCurrentItem(Constants.TAB_EXPANSES_LIST);
    }
    private void showTabStatistics(){
        viewPager.setCurrentItem(Constants.TAB_STATISTICS);
    }

}
