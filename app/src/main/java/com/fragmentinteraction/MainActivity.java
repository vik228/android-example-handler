package com.fragmentinteraction;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BaseFragment.Addition{

    public static String COLOR ="color";
    int color=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // String url = "http://localhost:1337/api/v0/controllers/get_string";
       // NetworkUtils.doGetCall(url,this);
        if (getIntent().getExtras()!= null) {
            color = getIntent().getExtras().getInt(COLOR);
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.new_frag, BaseFragment.newInstance(color));
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteract(TextView tv, String s) {
        tv.setText(s);
        color =Color.BLACK;


    }
    @Override
    public void switchActivity(){
        Intent intent = new Intent(this, DusriActivity.class);
        intent.putExtra(COLOR, color);
        startActivity(intent);
        finish();
    }


}
