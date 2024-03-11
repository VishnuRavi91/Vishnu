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



public class SimpleAdapterReferrals extends
        RecyclerView.Adapter<SimpleAdapterReferrals.MyViewHolder> {

    private JSONArray  list_item ;
    private JSONObject jsonObject;
    public Context mcontext;
    public String[] List1;
    Boolean alert,delete;
    public List<String> list = new ArrayList<String>();
    RecyclerView recyclerView;
    public SimpleAdapterReferrals(JSONArray list, Context context)  {
        alert=false;
        list_item = list;
        mcontext=context;
    }


    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public SimpleAdapterReferrals.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.referralslist, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {
        try {
            jsonObject=list_item.getJSONObject(position);
            viewHolder.Module.setText(jsonObject.getString("name"));
            viewHolder.link.setText(jsonObject.getString("link"));
            viewHolder.friendlink.setText(jsonObject.getString("refer_a_friend_link"));
            viewHolder.sdsso.setText(jsonObject.getString("SDSSO"));
            viewHolder.btnOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RefferalDtl.link=viewHolder.link.getText().toString();
                    RefferalDtl.demo=false;
                    Intent intent = new Intent(mcontext, RefferalDtl.class);
                    mcontext.startActivity(intent);
                }
            });
            viewHolder.btnSDSSO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RefferalDtl.link=viewHolder.sdsso.getText().toString();
                    RefferalDtl.demo=false;
                    Intent intent = new Intent(mcontext, RefferalDtl.class);
                    mcontext.startActivity(intent);
                }
            });
            viewHolder.btnRef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SecondDtl.from= "REFER & EARN";
                   // SecondDtl.link="https://docs.google.com/forms/d/e/1FAIpQLSf3YcxSkA0owuBP3yFBY2es9AZYmo18QUZlxtkcL4xuuSua0w/viewform?usp=sf_link";
                    RefferalDtl.link=viewHolder.friendlink.getText().toString();
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
        public TextView Module,link,friendlink,sdsso;
        public Button btnRef,btnOffer,btnSDSSO;
        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            Module= (TextView) itemLayoutView.findViewById(R.id.name);
            link= (TextView) itemLayoutView.findViewById(R.id.link);
            friendlink= (TextView) itemLayoutView.findViewById(R.id.friendlink);
            sdsso= (TextView) itemLayoutView.findViewById(R.id.sdsso);
            btnRef=(Button)itemLayoutView.findViewById(R.id.btnRef);
            btnOffer=(Button)itemLayoutView.findViewById(R.id.btnOfrn);
            btnSDSSO=(Button)itemLayoutView.findViewById(R.id.btnSDSSO);
        }
    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return list_item.length();
    }

}