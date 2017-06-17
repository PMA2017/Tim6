package rs.flights;

/**
 * Created by Rale on 6/11/2017.
 */


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import rs.SQLite.CustomFlightDialogFragment;
import rs.flights.FlightAddFragment;
import rs.flightbooking.R;

public class FlightActivity extends FragmentActivity implements
        CustomFlightDialogFragment.FlightDialogFragmentListener {

    private Fragment contentFragment;
    private FlightListFragment flightListFragment;
    private FlightAddFragment flightAddFragment;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();



        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(FlightAddFragment.ARG_ITEM_ID)) {
                    if (fragmentManager
                            .findFragmentByTag(FlightAddFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.add_flight);
                        contentFragment = fragmentManager
                                .findFragmentByTag(FlightAddFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(FlightListFragment.ARG_ITEM_ID) != null) {
                flightListFragment = (FlightListFragment) fragmentManager
                        .findFragmentByTag(FlightListFragment.ARG_ITEM_ID);
                contentFragment = flightListFragment;
            }
        } else {
            flightListFragment = new FlightListFragment();
            setFragmentTitle(R.string.app_name);
            switchContent(flightListFragment, FlightListFragment.ARG_ITEM_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof FlightAddFragment) {
            outState.putString("content", FlightAddFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", FlightListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                setFragmentTitle(R.string.add_flight);
                flightAddFragment = new FlightAddFragment();
                switchContent(flightAddFragment, FlightAddFragment.ARG_ITEM_ID);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;


if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            // Only EmpAddFragment is added to the back stack.
            if (!(fragment instanceof FlightListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        getActionBar().setTitle(resourseId);

    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof FlightListFragment
                || fm.getBackStackEntryCount() == 0) {
            //Shows an alert dialog on quit
            onShowQuitDialog();
        }
    }

    public void onShowQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setMessage("Do You Want To Quit?");
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
 @Override
    public void onFinishDialog() {
        if (flightListFragment != null) {
            flightListFragment.updateView();
        }
    }

}

