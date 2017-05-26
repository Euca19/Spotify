package com.spotify.eucaris.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.spotify.eucaris.R;
import com.spotify.eucaris.fragments.ArtistFragment;

import butterknife.BindView;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_content)
    CoordinatorLayout main_content;
    String TAG = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArtistFragment fragment = new ArtistFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, TAG)
                .commit();


    }

}
