package com.example.proje002.araclar.bakimlar.bakimgoster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proje002.R;
import com.example.proje002.araclar.bakimlar.bakimguncelle.BakimGuncelleDialogFragment;
import com.example.proje002.araclar.bakimlar.bakimguncelle.IBakimGuncelleListener;
import com.example.proje002.database.DatabaseSorgulari;
import com.example.proje002.model.Bakim;
import com.example.proje002.util.Config;

import java.util.List;

public class BakimListReyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Bakim> bakimList;

    public BakimListReyclerViewAdapter(Context context, List<Bakim> bakimList) {
        this.context = context;
        this.bakimList = bakimList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bakim, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int listPosition = position;
        final Bakim bakim = bakimList.get(position);
        holder.bakim1TextView.setText(bakim.getBakim1());
        holder.bakim2TextView.setText(bakim.getBakim2());
        holder.silBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Bakımı silmek istiyor musun?");
                alertDialogBuilder.setPositiveButton("Evet",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                silBakim(bakim);
                            }
                        });

                alertDialogBuilder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        holder.duzenleBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duzenleBakim(bakim.getId(), listPosition);
            }
        });
    }

    private void duzenleBakim(long bakimId, int listPosition) {
        BakimGuncelleDialogFragment bakimGuncelleDialogFragment = BakimGuncelleDialogFragment.newInstance(bakimId, listPosition, new IBakimGuncelleListener() {
            @Override
            public void onBakimInfoGuncelle(Bakim bakim, int position) {
                bakimList.set(position, bakim);
                notifyDataSetChanged();
            }
        });
        bakimGuncelleDialogFragment.show(((BakimListActivity) context).getSupportFragmentManager(), Config.UPDATE_BAKIM);
    }

    private void silBakim(Bakim bakim) {
        DatabaseSorgulari databaseSorgulari = new DatabaseSorgulari(context);
        boolean isDeleted = databaseSorgulari.silBakimByid(bakim.getId());

        if(isDeleted) {
            bakimList.remove(bakim);
            notifyDataSetChanged();
            ((BakimListActivity) context).viewVisibility();
        } else
            Toast.makeText(context, "Silinemedi!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return bakimList.size();
    }
}
