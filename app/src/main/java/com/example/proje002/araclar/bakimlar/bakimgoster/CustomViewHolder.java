package com.example.proje002.araclar.bakimlar.bakimgoster;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proje002.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView bakim1TextView;
    TextView bakim2TextView;

    ImageView silBakimImageView;
    ImageView duzenleBakimImageView;

    public CustomViewHolder(@NonNull View itemView){
        super(itemView);
        bakim1TextView = itemView.findViewById(R.id.bakim1TextView);
        bakim2TextView = itemView.findViewById(R.id.bakim2TextView);
        silBakimImageView = itemView.findViewById(R.id.silBakimImageView);
        duzenleBakimImageView = itemView.findViewById(R.id.duzenleBakimImageView);
    }
}
