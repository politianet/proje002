package com.example.proje002.araclar.arac.aracgoster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proje002.R;
import com.example.proje002.araclar.arac.aracguncelle.AracGuncelleDialogFragment;
import com.example.proje002.araclar.arac.aracguncelle.IAracGuncelleListener;
import com.example.proje002.araclar.bakimlar.bakimgoster.BakimListActivity;
import com.example.proje002.database.DatabaseSorgulari;
import com.example.proje002.model.Arac;
import com.example.proje002.util.Config;

import java.util.List;

public class AracListReyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    public Context context;
    private List<Arac> aracList;
    private DatabaseSorgulari databaseSorgulari;

    public AracListReyclerViewAdapter(Context context, List<Arac> aracList) {
        this.context = context;
        this.aracList = aracList;
        databaseSorgulari = new DatabaseSorgulari(context);
        // Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_arac, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        final int listPosition = position;
        final Arac arac = aracList.get(position);

        holder.pilakaTextView.setText(arac.getPilaka());
        holder.markaTextView.setText(arac.getMarka());
        holder.silBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Silmek istediğine emin misin?");
                alertDialogBuilder.setPositiveButton("Evet",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aracSil(listPosition);
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
                AracGuncelleDialogFragment aracGuncelleDialogFragment = AracGuncelleDialogFragment.newInstance(arac.getId(), listPosition, new IAracGuncelleListener() {
                    @Override
                    public void onAracInfoGuncelle(Arac arac, int position) {
                        aracList.set(position, arac);
                        notifyDataSetChanged();
                    }
                });
                aracGuncelleDialogFragment.show(((AracListActivity) context).getSupportFragmentManager(), Config.UPDATE_ARAC);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BakimListActivity.class);
                String hai = String.valueOf(arac.getId());
                intent.putExtra(Config.ARAC_REGISTRATION, arac.getId());
                context.startActivity(intent);
            }
        });
    }

    private void aracSil(int position) {
        Arac arac = aracList.get(position);
        long count = databaseSorgulari.silAracById(arac.getId());
        if (count > 0) {
            aracList.remove(position);
            notifyDataSetChanged();
            ((AracListActivity) context).viewVisibility();
            Toast.makeText(context, "Araç silindi", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Araç silinemedi", Toast.LENGTH_LONG).show();
    }


    @Override
    public int getItemCount() {
        return aracList.size();
    }
}
