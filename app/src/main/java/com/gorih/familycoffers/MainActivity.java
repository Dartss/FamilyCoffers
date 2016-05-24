package com.gorih.familycoffers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.gorih.familycoffers.controller.DBWorker;
import com.gorih.familycoffers.controller.TabsPagerFragmentAdapter;
import com.gorih.familycoffers.model.Expanse;
import com.gorih.familycoffers.presenter.dialog.dlgAddExpanse;
import com.gorih.familycoffers.presenter.dialog.dlgFirstLaunch;
import com.gorih.familycoffers.presenter.fragment.StatisticsFragment;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements dlgAddExpanse.OnNewExpanseAddedListener, dlgFirstLaunch.ModeSelectionListener {
    private static final String TAG = "--MainActivity--";

    private Toolbar toolbar;
    private static final int LAYOUT = R.layout.activity_main;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private boolean flag = true;



    private DBWorker dbWorker = DBWorker.getInstance(this);
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        Log.d(TAG, "onCreate");
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initToolbar();
        initNavigationView();
        initTabs();
        initFamilyId();
//        new ExpanseGetter().execute();
//        DBWorker.getInstance(this).eraseDB();
    }

    private void initFamilyId() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean firstLaunch = sharedPreferences.getBoolean(Constants.LAUNCHED_FIRST_TIME, true);

        if (firstLaunch) {
            DialogFragment firsLaunchDlg = dlgFirstLaunch.newInstance();
            firsLaunchDlg.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
            firsLaunchDlg.show(getSupportFragmentManager(), "first launch");
        }
    }

    private void saveFamilyId(long memberId) {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.MEMBER_ID, memberId);
        editor.apply();
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_join_family:
//                        Toast.makeText(getApplicationContext(), "This function not avalible yet", Toast.LENGTH_SHORT).show();
                        fillDBWithDefaultData();
                        break;
                    case R.id.menu_item_create_new_family:
                        Toast.makeText(getApplicationContext(), "This function not avalible yet", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_item_clear_database:
                        alertDialog();
                        break;
                    case R.id.menu_item_synchronize:
                        Toast.makeText(getApplicationContext(), "This function not avalible yet", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.app_name);

        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }

    @Override
    public void onNewExpanseAdded(Expanse newExpanse) {
        if (isOnline()) {
            new NewExpanseSender().execute(newExpanse);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
        dbWorker.addToDB(newExpanse);
        Log.d("dlgAddExpanse", "new expanse added: " + newExpanse.toString());

//        StatisticsFragment.statisticsFragment.refresh();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onModeSelected(int mode) {
        if(mode == Constants.OFFLINE_MODE) {
            Toast.makeText(this, "Offline mode", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Online mode", Toast.LENGTH_SHORT).show();
        }
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.LAUNCHED_FIRST_TIME, false);
        editor.apply();
    }

    private class NewExpanseSender extends AsyncTask<Expanse, Void, Void> {
        @Override
        protected Void doInBackground(Expanse... params) {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            try {
                for (Expanse expanse : params) {
                    Expanse added = template.postForObject(Constants.URL.POST_EXPANSE_ITEM, expanse, Expanse.class);
                    Log.d("Expanse Sender", added.toString());
                }
            } catch (Exception e) {
                Log.d("Sending data failed", e.toString());
            }
            return null;
        }
    }

    private class FamilyIdGetter extends AsyncTask<Void, Void, Long> {
        @Override
        protected Long doInBackground(Void... params) {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<Long> responseEntity =
                    template.getForEntity(Constants.URL.GET_FAMILY_ID, Long.class);

            return responseEntity.getBody();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            saveFamilyId(aLong);
        }
    }

//    private class ExpanseGetter extends AsyncTask<Void, Void, Expanse[]> {
//
//        @Override
//        protected Expanse[] doInBackground(Void... params) {
//            RestTemplate template = new RestTemplate();
//            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            ResponseEntity<Expanse[]> responseEntity =
//                    template.getForEntity(Constants.URL.GET_EXPANSE_ITEM, Expanse[].class);
//            return responseEntity.getBody();
//        }
//
//        @Override
//        protected void onPostExecute(Expanse[] expanses) {
//            for(Expanse expanse : expanses) {
//                onNewExpanseAdded(expanse);
//            }
//        }
//    }

    public void fillDBWithDefaultData() {
        ArrayList<Expanse> expanses = new ArrayList<>();
        for(int i = 0; i < 4; i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, i);

            Expanse expanse = new Expanse(i + 8, calendar.getTimeInMillis(), "Food");
            expanses.add(expanse);
        }
        for(int i = 0; i < 4; i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, i);

            Expanse expanse = new Expanse(i + 10, calendar.getTimeInMillis(), "Car");
            expanses.add(expanse);
        }
        for(int i = 0; i < 4; i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, i);

            Expanse expanse = new Expanse(i + 12, calendar.getTimeInMillis(), "Health");
            expanses.add(expanse);
        }
        DBWorker.getInstance(this).addExpansesList(expanses);
    }

    private void alertDialog() {
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
                            DBWorker.getInstance(getApplicationContext()).eraseDB();
                            Toast.makeText(getApplicationContext(), R.string.toast_after_erasing_db, Toast.LENGTH_SHORT).show();
                        }
                    });

        alertBuilder.create();
        alertBuilder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Sorry...no better ideas
        System.exit(0);
    }
}
