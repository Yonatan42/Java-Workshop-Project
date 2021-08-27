package com.yoni.javaworkshopprojectclient.ui.popups;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.BuildConfig;
import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.events.OnActivityResultListener;
import com.yoni.javaworkshopprojectclient.events.OnRequestPermissionResultListener;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.BitmapUtils;
import com.yoni.javaworkshopprojectclient.utils.RequestCodes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class GetImagePopup extends AlertDialog {

    private static final String TAG = "GetImagePopup";

    private ParentActivity parentActivity;

    private Consumer<String> onNewImage; // param is a base64 image

    private String cameraSaveImageFileName = "photo.jpg";
    private Uri cameraImageUri;

    private OnRequestPermissionResultListener onPermissionResultListener = new OnRequestPermissionResultListener() {
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            Log.i("PERMISSION_TEST", String.format("requestCode: %d, permissions: %s, grandResults: %s", requestCode, Arrays.toString(permissions), Arrays.toString(grantResults)));
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                switch (requestCode) {
                    case RequestCodes.PermissionCodes.GET_IMAGE_CAMERA_PERMISSION:
                        chooseFromCamera();
                        break;
                    case RequestCodes.PermissionCodes.GET_IMAGE_READ_STORAGE_PERMISSION:
                        chooseFromStorage();
                        break;
                }
            }
            else {
                Toast.makeText(parentActivity, parentActivity.getString(R.string.permission_denied_mesage), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private OnActivityResultListener onActivityResultListener = new OnActivityResultListener() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            Log.i("ACTIVITY_TEST", String.format("requestCode: %d, resultCode: %d, data: %s", requestCode, resultCode, data));
            if(resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case RequestCodes.ActivityCodes.GET_IMAGE_CAMERA:
                        handleCameraData();
                        break;
                    case RequestCodes.ActivityCodes.GET_IMAGE_STORAGE:
                        handleStorageData(data != null ? data.getData() : null);
                        break;
                }
            }
        }
    };


    public GetImagePopup(ParentActivity parentActivity, Consumer<String> onNewImage){
        super(parentActivity, R.style.WrapContentDialog);
        this.parentActivity = parentActivity;
        this.onNewImage = onNewImage;

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.popup_get_image, null, false);

        ImageButton btnCamera = layout.findViewById(R.id.get_image_popup_btn_camera);
        ImageButton btnFile = layout.findViewById(R.id.get_image_popup_btn_storage);

        if(!parentActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            btnCamera.setVisibility(View.GONE);
        }

        parentActivity.addOnPermissionsResultListener(onPermissionResultListener);
        parentActivity.addOnActivityResultListener(onActivityResultListener);

        btnCamera.setOnClickListener(v -> {
            if(!requestPermissionForCamera()){
                chooseFromCamera();
            }
        });

        btnFile.setOnClickListener(v -> {
            if(!requestPermissionForStorage()){
                chooseFromStorage();
            }
        });


        setView(layout);
    }

    private boolean requestPermissionForCamera(){
        return parentActivity.requestPermissions(RequestCodes.PermissionCodes.GET_IMAGE_CAMERA_PERMISSION, Manifest.permission.CAMERA);
    }

    private boolean requestPermissionForStorage(){
        return parentActivity.requestPermissions(RequestCodes.PermissionCodes.GET_IMAGE_READ_STORAGE_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void chooseFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = new File(parentActivity.getFilesDir(), cameraSaveImageFileName);
        cameraImageUri = FileProvider.getUriForFile(parentActivity, BuildConfig.APPLICATION_ID+".provider", imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
        parentActivity.startActivityForResult(cameraIntent, RequestCodes.ActivityCodes.GET_IMAGE_CAMERA);
    }

    private void chooseFromStorage(){
        Intent storageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        storageIntent.setType("image/*");

        Intent pickerIntent = new Intent(Intent.ACTION_PICK);
        pickerIntent.setDataAndType( android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        Intent chooseIntent = Intent.createChooser(storageIntent, "Select Image");
        chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickerIntent});

        parentActivity.startActivityForResult(chooseIntent, RequestCodes.ActivityCodes.GET_IMAGE_STORAGE);

    }

    private void handleData(Uri uri){
        ContentResolver contentResolver = parentActivity.getContentResolver();

        Bitmap bitmap;

        try {
            Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
            bitmap = BitmapUtils.scaleBitmap(originalBitmap, 512, 512);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

            onNewImage.accept(base64Image);
        } catch (IOException e) {
            Log.e(TAG, "getCameraData() file not found", e);
            Toast.makeText(parentActivity, parentActivity.getString(R.string.load_image_error), Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }

    private void handleCameraData(){
        handleData(cameraImageUri);
    }

    private void handleStorageData(Uri uri){
        if(uri == null) {
            Toast.makeText(parentActivity, parentActivity.getString(R.string.load_image_error), Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }
        handleData(uri);
    }

    @Override
    public void dismiss() {
        parentActivity.removeOnActivityResultListener(onActivityResultListener);
        parentActivity.removeOnPermissionsResultListener(onPermissionResultListener);
        super.dismiss();
    }
}
