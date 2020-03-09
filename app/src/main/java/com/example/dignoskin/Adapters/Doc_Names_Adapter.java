package com.example.dignoskin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dignoskin.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Doc_Names_Adapter extends RecyclerView.Adapter<Doc_Names_Adapter.Doc_Names_Viewholder> {
    Context context;
    OnItemClickListener mclicklistener;

    ArrayList<String> doclist;
    ArrayList<String> loclist;

    String doc_name;
    String doc_loc;

    public Doc_Names_Adapter(Context context, ArrayList<String> doclist,ArrayList<String> loclist){
        this.context = context;
        this.doclist = doclist;
        this.loclist = loclist;
    }


    @NonNull
    @Override
    public Doc_Names_Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.doc_item ,viewGroup,false);
        return new Doc_Names_Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Doc_Names_Viewholder doc_names_viewholder, final int i) {
        doc_names_viewholder.docname.setText(doclist.get(i));
        doc_names_viewholder.docloc.setText(loclist.get(i));

        doc_names_viewholder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mclicklistener.onItemClick(v,i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return  doclist.size();
    }


    public class Doc_Names_Viewholder extends RecyclerView.ViewHolder{
        public TextView docname;
        public TextView docloc;
        public CardView card;

        public Doc_Names_Viewholder(@NonNull View itemView) {
            super(itemView);

            docname = itemView.findViewById(R.id.docnametxt);
            docloc = itemView.findViewById(R.id.docloc);
            card = itemView.findViewById(R.id.mcard);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        doc_name = doclist.get(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener clicklistener){
        this.mclicklistener = clicklistener;
    }

}
