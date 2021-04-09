package com.example.sampleapp;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.widget.Toast;

import com.example.sampleapp.Registration.Twitterrr.fragments.TwitdroidSearchFragment;


public class MainActivity_twitter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tt);

        getSupportActionBar().setDisplayHomeAsUpEnabled( false );

        Toast.makeText( this, "Welcome to HunterHunter", Toast.LENGTH_SHORT ).show();

        load_fragment( TwitdroidSearchFragment.class, getIntent().getExtras() );
    }

    public void load_fragment( Class clazz ) {
        load_fragment( clazz, new Bundle() );
    }
    public void load_fragment(Class clazz, Bundle args ) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment current = getSupportFragmentManager().findFragmentById( R.id.fragment_container );
            Fragment fragment = ( Fragment ) clazz.newInstance();
            String fragment_tag = fragment.getClass().getName();
            fragment.setArguments( args );
            if ( current == null ) {
                transaction.add( R.id.fragment_container, fragment, fragment_tag );
            }
            else {
                transaction.replace( R.id.fragment_container, fragment, fragment_tag );
                transaction.addToBackStack( fragment_tag );
            }
            transaction.commit();
        }
        catch ( Exception e ) {
            e.printStackTrace();
            // you should treat your exceptions with respect, you can't just let them go !
            // ... except when I'm doing tutorials...
        }
    }
}
