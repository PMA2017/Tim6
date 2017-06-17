package rs.flightbooking;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


import rs.authentification.SignupActivity;
import rs.flights.FlightAddFragment;
import rs.contact.ContactFragment;
import rs.flights.FlightListFragment;
import rs.maps.MapFragment;
import rs.reservation.form.Reservations;
import rs.settings.SettingsFragment;
import tools.Session;

public class MainActivity extends ActionBarActivity {

    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSliding= (android.widget.ListView)findViewById(R.id.lv_sliding_menu);
        drawerLayout= (android.support.v4.widget.DrawerLayout)findViewById(R.id.drawer_layout);
        listSliding= new ArrayList<>();

        listSliding.add(new ItemSlideMenu(R.drawable.reservation,getResources().getString(R.string.reservations)));
        listSliding.add(new ItemSlideMenu(R.drawable.flights,getResources().getString(R.string.flights)));
        listSliding.add(new ItemSlideMenu(R.drawable.information,getResources().getString(R.string.information)));
        listSliding.add(new ItemSlideMenu(R.drawable.map,getResources().getString(R.string.map)));
        listSliding.add(new ItemSlideMenu(R.drawable.information,getResources().getString(R.string.add)));

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
    }

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

        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setTitle(R.string.log_out);
                Session session = new Session(activity.getApplicationContext());
                session.setUsername(null);
                startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
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

    private void replaceFragment(int pos) {
        android.support.v4.app.Fragment v4Fragment = null;
        switch (pos) {
            case 0:
                v4Fragment = new Reservations();
                break;
            case 1:
                v4Fragment = new FlightListFragment();
                break;
            case 2:

                v4Fragment = new ContactFragment();
                break;
            case 4:
                v4Fragment = new FlightAddFragment();
                break;
            case 3:
                v4Fragment = new MapFragment();
                Bundle bundle = new Bundle();
                bundle.putString("nameA", "New York");
                bundle.putString("nameB", "Serbia");
                bundle.putDouble("latA", 45.7128);
                bundle.putDouble("latB", 45.35);
                bundle.putDouble("lonA", 20);
                bundle.putDouble("lonB", 0);
                v4Fragment.setArguments(bundle);

                //startActivity(new Intent(MainActivity.this, MapsActivity.class));
                break;
            case 10:
                v4Fragment = new SettingsFragment();
                break;

            default:
                break;
        }

        if (null != v4Fragment) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction t = fm.beginTransaction();
            t.replace(R.id.main_content, v4Fragment);
            t.addToBackStack(null);
            t.commit();
        }
    }
}
