package com.blen.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Blensmile on 2016/4/2.
 */
public  class PermissionActivity extends AppCompatActivity {
    private Context mContext;
    private onRequestPermissionsCallback back;
    private final String PACKAGE_URL_SCHEME = "package:";
    public final int PERMISSIONS_GRANTED = 0; // permitted
    public final int PERMISSIONS_DENIED = 1; // not permitted
    private final int PERMISSION_REQUEST_CODE =1;

    /**
     *  this is the call back interface for user
     */
    public interface onRequestPermissionsCallback  {
         public void fail();
         public void success();
    }

    /** this is used to check permission, one by one
     *
     * @param permission
     * @return
     */
    private  boolean checkPermission(String permission) {
        Log.i("HelloBlen","checkPermission");
        return ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**  this is the callback of the permission request, from v4.app.ActivityCompat.OnRequestPermissionsResultCallback,
     *   AppCompatActivity is the subclass of FragmentActivity and FragmentActivity implemented the interface,
     *   so we should override this function to
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    boolean isPermited = true;
                    for(int i =0;i<grantResults.length;i++){
                        if( grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            isPermited = false;
                            //only if there was a request,and the request was refused should show the dialog
                            showMissingPermissionDialog();
                            break;
                        }
                    }
                    if(isPermited) {
                        back.success();
                        return;
                    }
                }
                back.fail();
                //maybe here could be better, for example, add a function canceled,but it seems of no use
            }
        }
    }


    /** this function used to check and request permissions,
     *  if the permission was delied ,a dialog will show to lead the user to check the permission setting
     *
     *  Activity context is needed here
     *
     * @param context
     * @param permision
     * @param back
     */
    public  void checkAndRequestPermission(Activity context, String[] permision, onRequestPermissionsCallback back) {
        this.back = back;
        this.mContext = context;
        ArrayList<String> list = new ArrayList<>();

        for(int i =0;i<permision.length;i++){
            if(!checkPermission( permision[i])) {
                list.add(permision[i]);
            }
        }
        if (list.size()==0) {
            back.success();
        }else{
            String[] str = new String[list.size()];
            for(int i = 0;i<str.length;i++){
                str[i]=list.get(i);
            }
            ActivityCompat.requestPermissions(context, str, PERMISSION_REQUEST_CODE);
        }
    }

    /** show diaglog to imform the users that there is no permission for application
     *  and lead them to check the permission
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_Content);

        // if get refused, exit activity
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        // go to to setting page for the application
        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    /**
     *  start the application setting
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
