package com.gorih.familycoffers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gorih.familycoffers.controller.CategoriesListAdapter;
import com.gorih.familycoffers.controller.DBWorker;
import com.gorih.familycoffers.controller.FileWorker;
import com.gorih.familycoffers.controller.TabsPagerFragmentAdapter;
import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Expanse;
import com.gorih.familycoffers.presenter.dialog.dlgAbout;
import com.gorih.familycoffers.presenter.dialog.dlgAddCategory;
import com.gorih.familycoffers.presenter.dialog.dlgAddExpanse;
import com.gorih.familycoffers.presenter.dialog.dlgEditCategory;
import com.gorih.familycoffers.presenter.dialog.dlgHelp;
import com.gorih.familycoffers.presenter.fragment.StatisticsFragment;

public class MainActivity extends AppCompatActivity
        implements dlgAddExpanse.OnNewExpanseAddedListener, dlgAddCategory.OnNewCategoryAddedListener,
        dlgEditCategory.CategoryEditedListener {

    private static final String TAG = "--MainActivity--";

    private Toolbar toolbar;
    private static final int LAYOUT = R.layout.activity_main;
    private DBWorker dbWorker = DBWorker.getInstance(this);
    SharedPreferences sharedPreferences;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Categories.initiation(this);
        initToolbar();
        initNavigationView();
        initTabs();
        sharedPreferences = getSharedPreferences("com.gorih.familycoffers", MODE_PRIVATE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }




    private void initNavigationView() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_clear_categories:
                        alertDialog(Constants.ACTION_CLEAR_CATEGORIES);
                        break;
                    case R.id.menu_about:
                        dlgAbout dlgA = dlgAbout.newInstance();
                        dlgA.show(getSupportFragmentManager(), "about");
                        break;
                    case R.id.menu_item_clear_database:
                        alertDialog(Constants.ACTION_ERASE_DB);
                        break;
                    case R.id.menu_help:
                        dlgHelp dlgH = dlgHelp.newInstance();
                        dlgH.show(getSupportFragmentManager(), "help");
                        break;
                }
                return true;
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_diamond);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.gorih.familycoffers/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.gorih.familycoffers/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        FileWorker.getInstance(this).rewriteCategoriesList();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Sorry...no better ideas
        System.exit(0);
    }

    @Override
    public void onNewCategoryAdded(String newCategoryName, int newCategoryColor) {
        Categories.instance.addNewCategory(newCategoryName, newCategoryColor);
        CategoriesListAdapter.getInstance().setNewList();
        CategoriesListAdapter.getInstance().notifyDataSetChanged();
    }

    @Override
    public void onCategoryChanged(String categoryNewName, int categoryNewColor, int targetCategoryId) {
        Categories.instance.changeCategory(targetCategoryId, categoryNewName, categoryNewColor);
        CategoriesListAdapter.getInstance().notifyDataSetChanged();
        StatisticsFragment.statisticsFragment.refresh();
    }


    @Override
    public void onNewExpanseAdded(Expanse newExpanse) {
        dbWorker.addToDB(newExpanse);
    }

    private void alertDialog(final int actionID) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setTitle(R.string.alert_erasing_db_title)
                .setMessage(R.string.alert_erasing_db_message)
                .setCancelable(false)
                .setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (actionID == Constants.ACTION_ERASE_DB) {
                            DBWorker.getInstance(getApplicationContext()).eraseDB();
                            Toast.makeText(getApplicationContext(), R.string.toast_after_erasing_db, Toast.LENGTH_SHORT).show();
                        }
                        if (actionID == Constants.ACTION_CLEAR_CATEGORIES) {
                            DBWorker.getInstance(getApplicationContext()).eraseDB();
                            Categories.instance.removeAllCategories();
                            CategoriesListAdapter.getInstance().setNewList();
                            CategoriesListAdapter.getInstance().notifyDataSetChanged();
                            FileWorker.getInstance(getApplicationContext()).removeFile();
                        }

                    }
                });

        alertBuilder.create();
        alertBuilder.show();
    }

}