package com.keyeonacole.bakingapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;


import java.io.IOException;

import butterknife.BindView;

/**
 * Created by keyeona on 7/24/18.
 */

public class RecipeActivity extends AppCompatActivity {
    @BindView(R.id.tablet_view)
    FrameLayout tableView;
    private boolean mTwoPane;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
        DetailFragment detailFragment = null;
        MainFragment mainFragment = null;

        if (tableView != null){
            mTwoPane = true;

            try {
                Log.i("Tablet?", String.valueOf(mTwoPane));
                mainFragment = new MainFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer, mainFragment)
                        .commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                detailFragment = new DetailFragment();
                FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, detailFragment);
                fragmentTransaction.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                detailFragment = new DetailFragment();
                FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerRight, detailFragment);
                fragmentTransaction.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
