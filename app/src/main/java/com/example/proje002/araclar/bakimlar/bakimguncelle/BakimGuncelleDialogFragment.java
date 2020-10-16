package com.example.proje002.araclar.bakimlar.bakimguncelle;

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
import com.example.proje002.model.Bakim;
import com.example.proje002.util.Config;

public class BakimGuncelleDialogFragment extends DialogFragment {
    private EditText bakim1EditText;
    private EditText bakim2EditText;

    private Button guncelleBtn;
    private Button vazgecBtn;
    private DatabaseSorgulari databaseSorgulari;
    //private Bakim mbakim;

    private static IBakimGuncelleListener bakimGuncelleListener;
    private static long bakimId;
    private static int position;

    public BakimGuncelleDialogFragment() {

    }

    public static BakimGuncelleDialogFragment newInstance(long bakId, int pos, IBakimGuncelleListener listener) {
        bakimId = bakId;
        position = pos;
        bakimGuncelleListener = listener;

        BakimGuncelleDialogFragment bakimGuncelleDialogFragment = new BakimGuncelleDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Bakım Güncelle Sayfası");
        bakimGuncelleDialogFragment.setArguments(args);

        bakimGuncelleDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return bakimGuncelleDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bakim_update_dialog, container, false);
        bakim1EditText = view.findViewById(R.id.bakim1EditText);
        bakim2EditText = view.findViewById(R.id.bakim2EditText);
        guncelleBtn = view.findViewById(R.id.guncelleBakimInfoButton);
        vazgecBtn = view.findViewById(R.id.vazgecBakimInfoButton);

        databaseSorgulari = new DatabaseSorgulari(getContext());
        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        Bakim bakim = databaseSorgulari.getBakimById((int) bakimId);
        bakim1EditText.setText(bakim.getBakim1());
        bakim2EditText.setText(bakim.getBakim2());

        guncelleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bakim1 = bakim1EditText.getText().toString();
                String bakim2 = bakim2EditText.getText().toString();

                Bakim bakim = new Bakim(-1, bakim1, bakim2);
                long rowCount = databaseSorgulari.guncelleBakimInfo(bakim);
                if (rowCount > 0) {
                    bakimGuncelleListener.onBakimInfoGuncelle(bakim, position);
                    getDialog().dismiss();
                }
            }
        });
        vazgecBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
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
