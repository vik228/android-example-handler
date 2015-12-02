package com.fragmentinteraction;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by vikas-pc on 30/11/15.
 */
public class DusriActivity extends AppCompatActivity implements BaseFragment.Addition{

    private static final String TAG = "DusriActivity";
int color=-1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dusri_activity);
        color =getIntent().getExtras().getInt(MainActivity.COLOR);
        FragmentTransaction ft= getFragmentManager().beginTransaction();
        ft.add(R.id.dusri, BaseFragment.newInstance(color));
        ft.commit();
    }

    @Override
    public void onFragmentInteract(TextView tv, String s) {

        tv.setText(s);
        color =Color.BLUE;
    }

    @Override
    public void switchActivity() {
        Intent intent= new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.COLOR, color);
        startActivity(intent);
        finish();
    }


}
