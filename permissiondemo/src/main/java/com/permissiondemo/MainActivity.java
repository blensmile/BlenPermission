package com.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blen.permission.PermissionActivity;

public class MainActivity extends PermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        if(id== R.id.action_permission){
            checkAndRequestPermission(this, new String[]{/*Manifest.permission.CAMERA,*/Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_SMS,Manifest.permission.READ_EXTERNAL_STORAGE}, new onRequestPermissionsCallback() {
                @Override
                public void fail() {
                    Toast.makeText(getApplicationContext(),"获取权限失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void success() {
                    Toast.makeText(getApplicationContext(),"已获取权限",Toast.LENGTH_SHORT).show();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
