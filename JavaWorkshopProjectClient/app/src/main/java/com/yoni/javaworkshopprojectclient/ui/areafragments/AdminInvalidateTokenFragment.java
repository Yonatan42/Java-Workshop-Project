package com.yoni.javaworkshopprojectclient.ui.areafragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.SimpleMessagePopup;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

public class AdminInvalidateTokenFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_admin_invalidate_token, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnInvalidate = view.findViewById(R.id.admin_invalidate_token_btn_invalidate);
        EditText txtUserId = view.findViewById(R.id.admin_invalidate_token_txt_userid);

        btnInvalidate.setOnClickListener(v -> {
            int userId = UIUtils.tryGetIntValue(txtUserId, -1);
            if(userId < 0){
                Toast.makeText(getContext(), getString(R.string.invalid_search_user_id), Toast.LENGTH_SHORT).show();
                return;
            }
            btnInvalidate.setEnabled(false);
            RemoteServiceManager.getInstance().getUsersService().invalidateToken(
                    userId,
                    (call, response, result) -> {
                        btnInvalidate.setEnabled(true);
                        txtUserId.setText("");
                        SimpleMessagePopup.createGenericTimed(getContext(), getString(R.string.admin_invalidate_token_success)).show();
                    },
                    new StandardResponseErrorCallback<TokennedResult<Void>>((ParentActivity) getActivity()) {
                        @Override
                        public void onPreErrorResponse() {
                            btnInvalidate.setEnabled(true);
                        }
                    }
            );
        });
    }
}
