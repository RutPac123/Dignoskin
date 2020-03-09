package com.example.dignoskin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.dignoskin.Adapters.Doc_Names_Adapter;
import com.example.dignoskin.ui.MainChatActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Doctors_Online extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> docnameslist;
    private ArrayList<String> docloclist;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors__online);

        docnameslist = new ArrayList<>();
        docnameslist.add("Dr. Mohit");
        docnameslist.add("Dr. Krishna");
        docnameslist.add("Dr. Ram");
        docnameslist.add("Dr. Mohammad");
        docnameslist.add("Dr. Christ");
        docnameslist.add("Dr. Strange");
        docnameslist.add("Dr. Manhattan");

        docloclist = new ArrayList<>();
        docloclist.add("Akola");
        docloclist.add("Mumbai");
        docloclist.add("Bhusaval");
        docloclist.add("Jalna");
        docloclist.add("Nagpur");
        docloclist.add("Delhi");
        docloclist.add("New York");

        recyclerView = findViewById(R.id.doc_list);

        Doc_Names_Adapter doc_names_adapter = new Doc_Names_Adapter(this,docnameslist,docloclist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(doc_names_adapter);

        doc_names_adapter.setOnItemClickListener(new Doc_Names_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(Doctors_Online.this, MainChatActivity.class));
            }
        });

        //setMyRecyclerView();
    }

//    private void setMyRecyclerView() {
//
//
//    }

}
