package com.example.proje002.araclar.arac.aracgoster;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proje002.R;
import com.example.proje002.araclar.arac.aracekle.AracEkleDialogFragment;
import com.example.proje002.araclar.arac.aracekle.IAracEkleListener;
import com.example.proje002.database.DatabaseSorgulari;
import com.example.proje002.model.Arac;
import com.example.proje002.util.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AracListActivity extends AppCompatActivity implements IAracEkleListener {
    private long idArac;
    private DatabaseSorgulari databaseSorgulari = new DatabaseSorgulari(this);
    private List<Arac> aracList = new ArrayList<>();
    private TextView summaryTextView;
    private TextView aracListEmptyTextView;
    private RecyclerView reyclerView;
    private Toolbar toolbar;
    private AracListReyclerViewAdapter aracListReyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac_list);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        reyclerView = findViewById(R.id.recyclerView);
        //summaryTextView = findViewById(R.id.summaryTextView);
        aracListEmptyTextView = findViewById(R.id.emptyListTextView);
        aracList.addAll(databaseSorgulari.getAllArac());
        aracListReyclerViewAdapter = new AracListReyclerViewAdapter(this, aracList);
        reyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reyclerView.setAdapter(aracListReyclerViewAdapter);
        viewVisibility();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAracOlusturDialog();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete all prodis?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllProdi();
                            if(isAllDeleted){
                                prodiList.clear();
                                prodiListRecyclerViewAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
                        }
                    });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }*/


    private void openAracOlusturDialog() {
        AracEkleDialogFragment aracEkleDialogFragment = AracEkleDialogFragment.newinstance("Araç Ekle", this);
        aracEkleDialogFragment.show(getSupportFragmentManager(), Config.CREATE_ARAC);
    }


    public void viewVisibility() {
        if(aracList.isEmpty())
            aracListEmptyTextView.setVisibility(View.VISIBLE);
        else
            aracListEmptyTextView.setVisibility(View.GONE);
    }

    public void setSupportActionBar(Toolbar toolbar) {

        // gereksiz
    }


    @Override
    public void onAracEkle(Arac arac) {
        aracList.add(arac);
        aracListReyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
    }
}
