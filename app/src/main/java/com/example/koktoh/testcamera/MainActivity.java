package com.example.koktoh.testcamera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Size;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;

    private Size _previewSize;
    private CameraManager _manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        _manager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            for (String id : _manager.getCameraIdList()) {
                CameraCharacteristics info = _manager.getCameraCharacteristics(id);
                int facing = info.get(CameraCharacteristics.LENS_FACING);
                if (facing != CameraCharacteristics.LENS_FACING_BACK) {
                    continue;
                }
                StreamConfigurationMap map = info.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                _previewSize = map.getOutputSizes(SurfaceTexture.class)[0];
                _manager.openCamera(id, _deviceStateCallback, null);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraDevice.StateCallback _deviceStateCallback = new CameraDevice.StateCallback(){
        @Override
    public void onOpened(CameraDevice camera){

        }

        @Override
    public void onDisconnected(CameraDevice camera){

        }

        @Override
    public void onError(CameraDevice camera, int error){

        }
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
}
