package com.zx.wfm.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.zx.wfm.R;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.service.Impl.NetWorkServerImpl;
import com.zx.wfm.service.NetWorkServer;
import com.zx.wfm.service.ParseUrlServer;
import com.zx.wfm.utils.Constants;

import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ${zhouxue} on 16/10/11 02: 23.
 * QQ:515278502
 */
public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,ParseUrlServer{
    private String TAG=BaseActivity.class.getSimpleName();
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;
    protected NetWorkServer server;

    @Override
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor=preferences.edit();
        server=new NetWorkServerImpl(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(Constants.Request_Code.RC_CAMERA_AND_WIFI)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.CHANGE_WIFI_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_wifi_rationale),
                    Constants.Request_Code.RC_CAMERA_AND_WIFI, perms);
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(Constants.Request_Code.RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.Request_Code.RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void OnGetTelevisionFromLeadCload(List<Televisionbean> list, String url) {

    }
    @Override
    public void OnGetMovieFromLeadCload(List<Moviebean> list, String url) {

    }

    @Override
    public void onParsrTelevisionUrlCallback(List<Televisionbean> list, String url) {

    }

    @Override
    public void onParsrMovieUrlCallback(List<Moviebean> list, String url) {

    }

    @Override
    public void onParseUrlError(Exception e) {

    }
}
