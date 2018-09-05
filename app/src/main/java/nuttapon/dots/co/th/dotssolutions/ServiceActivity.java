package nuttapon.dots.co.th.dotssolutions;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceActivity extends AppCompatActivity {

    private String jsonUserString, nameAnSurString, phoneString;
    private MyConstant myConstant = new MyConstant();
    private String[] columnStrings = myConstant.getColumnTcust();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

//        Get Value From Preference
        getValueFromPreference();

//        Create Toolbar
        createToolbar();

//        Create Menu
        createMenu();

//        Add Fragment
        addFragment(savedInstanceState);

    }   // Main Method

    private void addFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentServiceFragment, new DashBoardFragment())
                    .commit();

        }
    }

    private void createMenu() {
        ListView listView = findViewById(R.id.listViewMenu);
        MenuAdapter menuAdapter = new MenuAdapter(ServiceActivity.this,
                myConstant.getTitleMenuStrings(), myConstant.getIconInts());
        listView.setAdapter(menuAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moveToFragment(position);
            }
        });

    }

    private void moveToFragment(int position) {
        switch (position) {
            case 0:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment, new DashBoardFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment, new PackageFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment, new EbillFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment, new BillingCyclerFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment, new ServiceFragment())
                        .commit();
                drawerLayout.closeDrawers();
                break;
            case 5:
                finish();
                break;
        }
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);

//        Setup Name
        String firstName = findValueFromJSON(columnStrings[1]);
        String lastName = findValueFromJSON(columnStrings[2]);
        nameAnSurString = firstName + " " + lastName;
        getSupportActionBar().setSubtitle(nameAnSurString);

//        Setup Phone
        phoneString = findValueFromJSON(columnStrings[3]);
        phoneString = phoneString.trim();

        if (phoneString.length() != 11) {
            String s = phoneString;
            Log.d("4SepV2", "s ==> " + s);
            phoneString = s.substring(1, 11) + "\n" + s.substring(11, s.length());
        }

        TextView textView = findViewById(R.id.txtPhone);
        textView.setText(phoneString);

//        Create Hamburger
        drawerLayout = findViewById(R.id.drawerService);
        actionBarDrawerToggle = new ActionBarDrawerToggle(ServiceActivity.this,
                drawerLayout, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_hamberger);

    }   // Create Toolbar

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        actionBarDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String findValueFromJSON(String columnString) {

        String result = null;

        try {

            JSONArray jsonArray = new JSONArray(jsonUserString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            result = jsonObject.getString(columnString);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "Non";
        }


    }

    private void getValueFromPreference() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        jsonUserString = sharedPreferences.getString("User", "");
        Log.d("4SepV1", "jsonUserString ==> " + jsonUserString);


    }


}   // Main Class
