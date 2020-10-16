package com.example.proje002.araclar.bakimlar.bakimekle;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.proje002.model.Bakim;

public class BakimEkleDialogFragment extends DialogFragment {
    private static int idArac;
    private static IBakimEkleListener bakimEkleListener;
    private EditText bakim1EditText;
    private EditText bakim2EditText;
    private Button ekleBtn;
    private Button vazgecBtn;
    private String bakim1 = "";
    private String bakim2 = "";
    public BakimEkleDialogFragment(){

    }
    public static BakimEkleDialogFragment newInstance(int id_Arac, IBakimEkleListener listener){
        idArac = id_Arac;
        String testt = String.valueOf(idArac);
        Log.d("Bakımların", testt);
        bakimEkleListener = listener;
        BakimEkleDialogFragment bakimEkleDialogFragment = new BakimEkleDialogFragment();
        bakimEkleDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return bakimEkleDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bakim_olustur_dialog, container, false);
        getDialog().setTitle("Bakım Ekle");

        bakim1EditText = view.findViewById(R.id.bakim1EditText);
        bakim2EditText = view.findViewById(R.id.bakim2EditText);
        ekleBtn = view.findViewById(R.id.ekleButton);
        vazgecBtn = view.findViewById(R.id.vazgecButton);

        ekleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bakim1 = bakim1EditText.getText().toString();
                bakim2 = bakim2EditText.getText().toString();

                Bakim bakim = new Bakim(-1, bakim1, bakim2);
                DatabaseSorgulari databaseSorgulari = new DatabaseSorgulari(getContext());
                long id = databaseSorgulari.ekleBakim(bakim, idArac);
                if(id>0){
                    bakim.setId(id);
                    bakimEkleListener.onBakimEkle(bakim);
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
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

}
