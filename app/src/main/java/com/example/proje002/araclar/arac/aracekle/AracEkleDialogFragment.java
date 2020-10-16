package com.example.proje002.araclar.arac.aracekle;

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

import com.example.proje002.R;
import com.example.proje002.database.DatabaseSorgulari;
import com.example.proje002.model.Arac;
import com.example.proje002.util.Config;

public class AracEkleDialogFragment extends DialogFragment {
    private static IAracEkleListener aracEkleListener;

    private EditText pilakaEditText;
    private EditText markaEditText;
    private Button ekleButton;
    private Button vazgecButton;

    private String pilakaString = "";
    private String markaString = "";

    public AracEkleDialogFragment() {

    }
    public static AracEkleDialogFragment newinstance(String title, IAracEkleListener listener){
       aracEkleListener = listener;
       AracEkleDialogFragment aracEkleDialogFragment = new AracEkleDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        aracEkleDialogFragment.setArguments(args);
        aracEkleDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return aracEkleDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_arac_olustur_dialog, container, false);
        pilakaEditText = view.findViewById(R.id.pilakaEditText);
        markaEditText = view.findViewById(R.id.markaEditText);
        ekleButton = view.findViewById(R.id.ekleButton);
        vazgecButton = view.findViewById(R.id.vazgecButton);
        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        ekleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilakaString = pilakaEditText.getText().toString();
                markaString = markaEditText.getText().toString();

                Arac arac = new Arac(-1, pilakaString, markaString);
                DatabaseSorgulari databaseSorguları = new DatabaseSorgulari(getContext());
                long id = databaseSorguları.ekleArac(arac);
                if (id>0){
                    arac.setId(id);
                    aracEkleListener.onAracEkle(arac);
                    getDialog().dismiss();
                }
            }
        });
        vazgecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT; // genişlik
            int height = ViewGroup.LayoutParams.WRAP_CONTENT; // yükseklik
            dialog.getWindow().setLayout(width, height);
        }
    }
}

