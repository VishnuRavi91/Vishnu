package com.example.admin.studydesk;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.studydesk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class SimpleAdapterDemo extends
        RecyclerView.Adapter<SimpleAdapterDemo.MyViewHolder> {

    private JSONArray  list_item ;
    private JSONObject jsonObject;
    public Context mcontext;
    public String[] List1;
    Boolean alert,delete;
    public List<String> list = new ArrayList<String>();
    RecyclerView recyclerView;
    public SimpleAdapterDemo(JSONArray list, Context context)  {
        alert=false;
        list_item = list;
        mcontext=context;
    }


    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public SimpleAdapterDemo.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.demolist, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {
        try {
            jsonObject=list_item.getJSONObject(position);
            viewHolder.Module.setText(jsonObject.getString("name"));
            viewHolder.uni.setText(jsonObject.getString("university"));
            viewHolder.syllink.setText(jsonObject.getString("syllabus_link"));
            viewHolder.demolink.setText(jsonObject.getString("demo_link"));
            viewHolder.feelink.setText(jsonObject.getString("fee_link"));
            viewHolder.btnDemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RefferalDtl.link=viewHolder.demolink.getText().toString();
                    RefferalDtl.demo=false;
                    Intent intent = new Intent(mcontext, RefferalDtl.class);
                    mcontext.startActivity(intent);
                }
            });
            viewHolder.btnFee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RefferalDtl.link=viewHolder.feelink.getText().toString();
                    RefferalDtl.demo=false;
                    Intent intent = new Intent(mcontext, RefferalDtl.class);
                    mcontext.startActivity(intent);
                }
            });
            viewHolder.btnSyl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RefferalDtl.link=viewHolder.syllink.getText().toString();
                    RefferalDtl.demo=false;
                    Intent intent = new Intent(mcontext, RefferalDtl.class);
                    mcontext.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // initializes some private fields to be used by RecyclerView.
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Module,syllink,feelink,demolink,uni;
        public Button btnSyl,btnFee,btnDemo;
        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            Module= (TextView) itemLayoutView.findViewById(R.id.name);
            uni= (TextView) itemLayoutView.findViewById(R.id.uni);
            syllink= (TextView) itemLayoutView.findViewById(R.id.syllink);
            feelink= (TextView) itemLayoutView.findViewById(R.id.feelink);
            demolink= (TextView) itemLayoutView.findViewById(R.id.demolink);
            btnSyl=(Button)itemLayoutView.findViewById(R.id.btnSyllabus);
            btnFee=(Button)itemLayoutView.findViewById(R.id.btnSubCharge);
            btnDemo=(Button)itemLayoutView.findViewById(R.id.btndemo);
        }
    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return list_item.length();
    }

}