package com.example.proje002.araclar.arac.aracgoster;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proje002.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView pilakaTextView;
    TextView markaTextView;
    ImageView silBtnImageView;
    ImageView duzenleBtnImageView;

    public CustomViewHolder( View itemView) {
        super(itemView);
        pilakaTextView = itemView.findViewById(R.id.pilakaTextView);
        markaTextView = itemView.findViewById(R.id.markaTextView);
        silBtnImageView = itemView.findViewById(R.id.silImageView);
        duzenleBtnImageView = itemView.findViewById(R.id.duzenleImageView);
    }
}
