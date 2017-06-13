package rs.flightbooking;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import rs.SQLite.FlightAddFragment;
import rs.SQLite.FlightListFragment;
import rs.contact.ContactFragment;
import rs.maps.MapFragment;
import rs.reservation.Reservations;
import rs.settings.SettingsFragment;
import tools.Session;

public class MainActivity extends ActionBarActivity {

    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
//    private android.widget.RelativeLayout mainContent;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String testResponse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listViewSliding= (android.widget.ListView)findViewById(R.id.lv_sliding_menu);
        drawerLayout= (android.support.v4.widget.DrawerLayout)findViewById(R.id.drawer_layout);
//        mainContent= (android.widget.RelativeLayout)findViewById(R.id.main_content);
        listSliding= new ArrayList<>();

        listSliding.add(new ItemSlideMenu(R.drawable.reservation,"Reservations"));
        listSliding.add(new ItemSlideMenu(R.drawable.flights,"Flights"));
        listSliding.add(new ItemSlideMenu(R.drawable.information,"Informations"));
        listSliding.add(new ItemSlideMenu(R.drawable.information,"Contact"));
        listSliding.add(new ItemSlideMenu(R.drawable.map,"Maps"));
        listSliding.add(new ItemSlideMenu(R.drawable.information,"Add"));


        adapter= new SlidingMenuAdapter(this,listSliding);
        listViewSliding.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(listSliding.get(0).getTitle());

        listViewSliding.setItemChecked(0, true);

        drawerLayout.closeDrawer(listViewSliding);

        replaceFragment(0);


        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            setTitle(listSliding.get(position).getTitle());
            listViewSliding.setItemChecked(position, true);
            replaceFragment(position);
            drawerLayout.closeDrawer(listViewSliding);
            }
        });



        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed){

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //testRest();


        Session session = new Session(this.getApplicationContext());
        String username = session.getUsername();


    }

    /*private void testRest(){
        RequestParams rp = new RequestParams();
        // rp.add("username", "aaa"); rp.add("password", "aaa@123");

        HttpUtils.get("api/get/Country", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the responseObject is JSONObject instead of expected JSONArray
                testResponse = response.toString();
                displayResult();

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray arrayResponse){
                //if the responseObject is JSONArray
                testResponse = arrayResponse.toString();
                displayResult();
            }

            @Override
             public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("CALL FAILED:" +responseString);
                System.out.println("STATUS CODE:" + statusCode);
            }
            //@Override
            // public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
            //    // Pull out the first event on the public timeline
            //
            //}
        });

    }

    public void displayResult(){
        Toast.makeText(this,testResponse,Toast.LENGTH_LONG).show();
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setTitle(R.string.settings);
                drawerLayout.closeDrawer(listViewSliding);
                item.setChecked(true);
                replaceFragment(10);
                return true;
            }


        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void replaceFragment(int pos){
        android.support.v4.app.Fragment v4Fragment = null;
        switch(pos) {
            case 0:
                v4Fragment=new Reservations();
                break;
            case 1:
                v4Fragment = new FlightListFragment();
           //     fragment=new Flights();
                break;
            case 2:
                v4Fragment=new Informations();
                break;
            case 3:

                v4Fragment = new ContactFragment();
                break;
            case 5:
                v4Fragment = new FlightAddFragment();
                break;
            case 4:
                v4Fragment = new MapFragment();
                Bundle bundle = new Bundle();
                bundle.putString("nameA", "New York");
                bundle.putString("nameB","Serbia");
                bundle.putDouble("latA",45.7128);
                bundle.putDouble("latB",45.35);
                bundle.putDouble("lonA",20);
                bundle.putDouble("lonB",0);
                v4Fragment.setArguments(bundle);

                //startActivity(new Intent(MainActivity.this, MapsActivity.class));
                break;
            case 10:
                v4Fragment = new SettingsFragment();
                break;

            default:
                break;
        }

        if(null!=v4Fragment){
            android.support.v4.app.FragmentManager fm= getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction t=fm.beginTransaction();
            t.replace(R.id.main_content,v4Fragment);
            t.addToBackStack(null);
            t.commit();


        }

    }




}
