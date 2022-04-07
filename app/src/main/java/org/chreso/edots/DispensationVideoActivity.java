package org.chreso.edots;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DispensationVideoActivity extends AppCompatActivity {

    private Button btnRecordVideo;
    private static int CAMERA_PERMISSION_CODE = 100;
    private static int VIDE_RECORD_CODE = 101;
    private Uri videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensation_video);

        if(isCameraPresentInDevice()){
            getCameraPermission();
        }else{

        }

        btnRecordVideo = findViewById(R.id.btnRecordVideo);
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRecordVideo();
            }
        });
    }

    private void doRecordVideo(){
        recordVideo();
    }

    private boolean isCameraPresentInDevice(){
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        }
        else {
            return false;
        }
    }

    private void getCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    private void recordVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDE_RECORD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
            videoPath = data.getData();
            Log.i("VIDEO_RECORD_TAG", "Video recorded "+ videoPath);
        }else if(requestCode == RESULT_CANCELED){
            Log.i("VIDEO_RECORD_TAG", "Video is cancelled ");
        }else{
            Log.i("VIDEO_RECORD_TAG", "Recorded video Video has got an error");
        }
    }
}