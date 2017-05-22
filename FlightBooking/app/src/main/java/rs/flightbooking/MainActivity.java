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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
//    private android.widget.RelativeLayout mainContent;
    private ActionBarDrawerToggle actionBarDrawerToggle;

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
