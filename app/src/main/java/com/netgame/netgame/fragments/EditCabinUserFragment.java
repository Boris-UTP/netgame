package com.netgame.netgame.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.netgame.netgame.R;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.UserCabin;

import org.json.JSONObject;

import static com.netgame.netgame.network.NetGameApiService.PUT_INFO_CABIN_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditCabinUserFragment extends Fragment {

    private Switch isOpenSwitch;
    private EditText addressEditText;
    private String tag;
    private Gson gson;

    private ProgressDialog progressDialog;

    private UserCabin userCabin;

    public static EditCabinUserFragment newInstance(UserCabin userCabin){
        Bundle args = userCabin.toBundle();

        EditCabinUserFragment fragment = new EditCabinUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View context = inflater.inflate(R.layout.fragment_edit_cabin_user, container, false);

        userCabin = UserCabin.from(getArguments());

        tag = getActivity().getResources().getString(R.string.app_name);
        gson = new Gson();
        createProgressDialog();
        isOpenSwitch = context.findViewById(R.id.isOpenSwitch);
        addressEditText = context.findViewById(R.id.addressEditText);

        isOpenSwitch.setChecked(userCabin.getStateAttention() == 1 ? true : false);
        addressEditText.setText(userCabin.getAddress());

        return context;
    }

    private void createProgressDialog(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void putInfoCabin() {
        progressDialog.show();
        AndroidNetworking
                .put(PUT_INFO_CABIN_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(getActivity(), "token", ""))
                .addBodyParameter("address", addressEditText.getText().toString())
                .addBodyParameter("stateAttention", isOpenSwitch.isChecked() ? "1" : "0")
                .setTag(tag)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (responseObject.getStatusBody().getCode().equals("0")) {
                            Toast.makeText(getActivity(), responseObject.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(tag, responseObject.getStatusBody().getMessage());
                            // Toast.makeText(getApplicationContext(), responseObject.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.d(tag, anError.getMessage());
                    }
                });
    }


}
