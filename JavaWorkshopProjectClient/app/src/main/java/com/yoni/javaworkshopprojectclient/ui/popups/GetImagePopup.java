package com.yoni.javaworkshopprojectclient.ui.popups;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

import java.io.File;
import java.util.Arrays;


public class GetImagePopup extends AlertDialog {

    private static final String TAG = "GetImagePopup";
    private static final int MAX_IMAGE_DIMEN = 256;

    private ParentActivity parentActivity;

    private Consumer<String> onNewImage; // param is a base64 image

    private static final String CAMERA_SAVE_IMAGE_FILE_NAME = "photo.jpg";
    private Uri cameraImageUri;

    private final OnRequestPermissionResultListener onPermissionResultListener = new OnRequestPermissionResultListener() {
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            Log.i(TAG, String.format("onRequestPermissionsResult - requestCode: %d, permissions: %s, grandResults: %s", requestCode, Arrays.toString(permissions), Arrays.toString(grantResults)));
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
                Toast.makeText(parentActivity, parentActivity.getString(R.string.permission_denied_message), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final OnActivityResultListener onActivityResultListener = new OnActivityResultListener() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            Log.i(TAG, String.format("onActivityResult - requestCode: %d, resultCode: %d, data: %s", requestCode, resultCode, data));
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

        Button btnBack = layout.findViewById(R.id.get_image_popup_btn_back);
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

        btnBack.setOnClickListener(v -> dismiss());

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
        File imageFile = new File(parentActivity.getFilesDir(), CAMERA_SAVE_IMAGE_FILE_NAME);
        cameraImageUri = FileProvider.getUriForFile(parentActivity, BuildConfig.APPLICATION_ID+".provider", imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
        parentActivity.startActivityForResult(cameraIntent, RequestCodes.ActivityCodes.GET_IMAGE_CAMERA);
    }

    private void chooseFromStorage(){
        final String imageMimeType = "image/*";
        Intent storageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        storageIntent.setType(imageMimeType);

        Intent pickerIntent = new Intent(Intent.ACTION_PICK);
        pickerIntent.setDataAndType( android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageMimeType);

        Intent chooseIntent = Intent.createChooser(storageIntent, "Select Image");
        chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickerIntent});

        parentActivity.startActivityForResult(chooseIntent, RequestCodes.ActivityCodes.GET_IMAGE_STORAGE);
    }

    private void handleData(Uri uri){
        String base64Image = BitmapUtils.readBase64ImageFromUri(
                parentActivity.getContentResolver(),
                uri,
                MAX_IMAGE_DIMEN,
                MAX_IMAGE_DIMEN);

        if(base64Image != null) {
            onNewImage.accept(base64Image);
        }
        else {
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
    protected void onStop() {
        super.onStop();
        parentActivity.removeOnActivityResultListener(onActivityResultListener);
        parentActivity.removeOnPermissionsResultListener(onPermissionResultListener);
    }
}
