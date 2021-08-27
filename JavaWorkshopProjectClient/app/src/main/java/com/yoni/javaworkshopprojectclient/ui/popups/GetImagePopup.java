package com.yoni.javaworkshopprojectclient.ui.popups;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.events.OnActivityResultListener;
import com.yoni.javaworkshopprojectclient.events.OnRequestPermissionResultListener;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.RequestCodes;

import java.util.Arrays;


public class GetImagePopup extends AlertDialog {

    private ParentActivity parentActivity;

    private Consumer<Product> onNewImage;

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
                        chooseFromFileSystem();
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
            switch (requestCode){
                case RequestCodes.ActivityCodes.GET_IMAGE_CAMERA:
                    break;
                case RequestCodes.ActivityCodes.GET_IMAGE_FILE:
                    break;
            }
        }
    };


    public GetImagePopup(ParentActivity parentActivity, Consumer<Product> onNewProductCreated){
        super(parentActivity);
        this.parentActivity = parentActivity;

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.popup_product_details, null, false);

        Button btnCamera = layout.findViewById(R.id.products_details_popup_admin_buttons_group);
        Button btnFile = layout.findViewById(R.id.products_details_popup_btn_edit_image);


        parentActivity.addOnPermissionsResultListener(onPermissionResultListener);

        btnCamera.setOnClickListener(v -> {
            // todo - get image from gallery or camera, convert to base64 and display
            if(requestPermissionForCamera()){
                // permission was requested
            }
            else{
                chooseFromCamera();
            }
        });
    }

    private boolean requestPermissionForCamera(){
        return parentActivity.requestPermissions(RequestCodes.PermissionCodes.GET_IMAGE_CAMERA_PERMISSION, Manifest.permission.CAMERA);
    }

    private void chooseFromCamera(){

    }

    private void chooseFromFileSystem(){

    }

    @Override
    public void dismiss() {
        super.dismiss();
        parentActivity.removeOnPermissionsResultListener(onPermissionResultListener);
    }
}
