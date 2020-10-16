package com.example.proje002.araclar.bakimlar.bakimgoster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proje002.R;
import com.example.proje002.araclar.bakimlar.bakimekle.BakimEkleDialogFragment;
import com.example.proje002.araclar.bakimlar.bakimekle.IBakimEkleListener;
import com.example.proje002.database.DatabaseSorgulari;
import com.example.proje002.model.Bakim;
import com.example.proje002.util.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BakimListActivity extends AppCompatActivity implements IBakimEkleListener {
    private int idArac;
    private DatabaseSorgulari databaseSorgulari = new DatabaseSorgulari(this);
    private List<Bakim> bakimList = new ArrayList<>();
    private TextView bakimListEmptyTextView;
    private RecyclerView recyclerView;
    private BakimListReyclerViewAdapter bakimListReyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakim_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        bakimListEmptyTextView = findViewById(R.id.emptyListTextView);
        idArac = getIntent().getIntExtra(Config.ARAC_REGISTRATION, 2);
        bakimList.addAll(databaseSorgulari.getAllBakimByRegNo(idArac));
        bakimListReyclerViewAdapter = new BakimListReyclerViewAdapter(this, bakimList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bakimListReyclerViewAdapter);
        viewVisibility();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acBakimEkleDialog();
            }
        });
    }

    private void acBakimEkleDialog() {
        BakimEkleDialogFragment bakimEkleDialogFragment = BakimEkleDialogFragment.newInstance(idArac, this);
        bakimEkleDialogFragment.show(getSupportFragmentManager(), Config.CREATE_BAKIM);
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Tüm bakımlar silinsin mi??");
                alertDialogBuilder.setPositiveButton("Evet",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                boolean isAllDeleted = databaseSorgulari.deleteAllBakimByRegNum(idArac);
                                if(isAllDeleted){
                                    bakimList.clear();
                                    bakimListReyclerViewAdapter.notifyDataSetChanged();
                                    viewVisibility();
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("Hayır",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    public void viewVisibility() {
        if(bakimList.isEmpty())
            bakimListEmptyTextView.setVisibility(View.VISIBLE);
        else
            bakimListEmptyTextView.setVisibility(View.GONE);
    }
    public void setSupportActionBar(Toolbar toolbar) {

        // gereksiz
    }
    @Override
    public void onBakimEkle(Bakim bakim) {
        bakimList.add(bakim);
        bakimListReyclerViewAdapter.notifyDataSetChanged();
    }

}
