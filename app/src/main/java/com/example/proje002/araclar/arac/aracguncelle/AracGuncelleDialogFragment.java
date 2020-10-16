package com.example.proje002.araclar.arac.aracguncelle;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.proje002.R;
import com.example.proje002.araclar.arac.aracgoster.AracListActivity;
import com.example.proje002.database.DatabaseSorgulari;
import com.example.proje002.model.Arac;
import com.example.proje002.util.Config;

public class AracGuncelleDialogFragment extends DialogFragment {
    private static IAracGuncelleListener aracGuncelleListener;
    private static int aracItemPosition;
    private static long aracId;
    private Arac mArac;
    private EditText pilakaEditText;
    private EditText markaEditText;
    private Button guncelleBtn;
    private Button vazgecBtn;
    private String pilakaString = "";
    private String markaString = "";
    private DatabaseSorgulari databaseSorgulari;

    public AracGuncelleDialogFragment(){

    }

    public static AracGuncelleDialogFragment newInstance(long id, int position, IAracGuncelleListener listener) {
        aracId = id;
        aracItemPosition = position;
        aracGuncelleListener = listener;
        AracGuncelleDialogFragment aracGuncelleDialogFragment = new AracGuncelleDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "GUNCELLE");
        aracGuncelleDialogFragment.setArguments(args);
        aracGuncelleDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return aracGuncelleDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_arac_update_dialog, container, false);
        databaseSorgulari = new DatabaseSorgulari(getContext());
        pilakaEditText = view.findViewById(R.id.pilakaEditText);
        markaEditText = view.findViewById(R.id.markaEditText);
        guncelleBtn = view.findViewById(R.id.guncelleAracInfoButton);
        vazgecBtn = view.findViewById(R.id.vazgecAracInfoButton);
        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);
        mArac = databaseSorgulari.getAracById(aracId);
        if (mArac != null){
            pilakaEditText.setText(mArac.getPilaka());
            markaEditText.setText(mArac.getMarka());
            guncelleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pilakaString = pilakaEditText.getText().toString();
                    markaString = markaEditText.getText().toString();
                    mArac.setPilaka(pilakaString);
                    mArac.setMarka(markaString);
                    Long id = databaseSorgulari.guncelleAracInfo(mArac);
                    if (id>0){
                        aracGuncelleListener.onAracInfoGuncelle(mArac, aracItemPosition);
                        getDialog().dismiss();
                    }
                }
            });
            vazgecBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });
        }
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

}
