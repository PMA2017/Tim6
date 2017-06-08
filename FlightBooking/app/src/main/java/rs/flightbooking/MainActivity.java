package rs.flightbooking;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import rs.reservation.Reservations;

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
        listSliding.add(new ItemSlideMenu(R.drawable.map,"Maps"));

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
        testRest();


    }

    private void testRest(){
        RequestParams rp = new RequestParams();
        // rp.add("username", "aaa"); rp.add("password", "aaa@123");

        HttpUtils.get("api/get/Country", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
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
                //if the response is JSONArray
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
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
        Fragment fragment = null;
        switch(pos) {
            case 0:
                fragment=new Reservations();
                break;
            case 1:
                fragment=new Flights();
                break;
            case 2:
                fragment=new Informations();
                break;
            case 3:
                //fragment = new Maps();
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                break;
            default:
                break;
        }

        if(null!=fragment){
            FragmentManager fm= getFragmentManager();
            FragmentTransaction t=fm.beginTransaction();
            t.replace(R.id.main_content,fragment);
            t.addToBackStack(null);
            t.commit();
        }

    }



//    public void letovi(View view){
//
//        startActivity(new Intent(this,ActivityOne.class));
//
//    }
//
//    public void avio_kompanije(View view){
//
//        startActivity(new Intent(this,ActivityTwo.class));
//
//    }
//
//    public void registracija(View view){
//
//        startActivity(new Intent(this,ActivityThree.class));
//
//    }


}
