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
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    public static final int PERMISSIONS_GRANTED = 0; // 权限授权
    public static final int PERMISSIONS_DENIED = 1; // 权限拒绝


    public interface onRequestPermissionsCallback  {
         public void fail();
         public void success();
    }
    //判断版本号

    //检查权限状态
    //其实可以是public,因为没有用到activity_context
    private  boolean checkPermission(String permission) {
        Log.i("HelloBlen","checkPermission");
        return ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    //这个方法很傲娇,是来自于support-V4包的app.ActivityCompat.OnRequestPermissionsResultCallback,
    //ActivityCompat.requestPermissions(context,requestString[],requestCode),中的context必须是activity的,相当傲娇
    //AppCompatActivity中的FragmentActivity实现了上面哪个v4包的接口,也就是这个方法,所以,如果想抛开activity自己玩,根据上面那一条,是没法玩的,
    //所以必须把本类和要申请权限的类(activity)关联起来,所以想到了继承,再实现一下接口,然后世界就好了.
    //想明白了上面几条,后面的其实就好说了
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    boolean isPermited = true;
                    for(int i =0;i<grantResults.length;i++){
                        if( grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                            isPermited = false;
                            break;
                        }
                    }
                    if(isPermited) {
                        Log.i("HelloBlen", "get permission success");
                        back.success();
                        return;
                    }
                }
                    Log.i("HelloBlen","get permission failed");
                    showMissingPermissionDialog();
                    back.fail();
            }
        }
    }


    //检查并且申请权限
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
            Log.i("HelloBlen","check is right");
            back.success();
        }else{
            Log.i("HelloBlen", "Check is no right");
            String[] str = new String[list.size()];
            for(int i = 0;i<str.length;i++){
                str[i]=list.get(i);
            }
            ActivityCompat.requestPermissions(context, str, 1);
        }
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_Content);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.setCancelable(false);

        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
