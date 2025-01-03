package com.example.admin.studydesk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.studydesk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class SimpleAdapterModule extends
        RecyclerView.Adapter<SimpleAdapterModule.MyViewHolder> {

    private JSONArray  list_item ;
    private JSONObject jsonObject;
    public Context mcontext;
    public String[] List1;
    Boolean alert,delete;
    public List<String> list = new ArrayList<String>();
    RecyclerView recyclerView;
    public SimpleAdapterModule(JSONArray list, Context context)  {
        alert=false;
        list_item = list;
        mcontext=context;
    }


    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public SimpleAdapterModule.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.modulelist, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {
        try {
            jsonObject=list_item.getJSONObject(position);
            viewHolder.Module.setTypeface(null, Typeface.NORMAL);
            viewHolder.Module.setText(jsonObject.getString("module_name"));
            if (viewHolder.Module.getText().toString().startsWith("*")){
                viewHolder.Module.setTypeface(null, Typeface.BOLD);
                viewHolder.Module.setTextColor(Color.parseColor("#11b1a2"));
                viewHolder.Module.setText(viewHolder.Module.getText().toString().substring(1,viewHolder.Module.getText().toString().length()));
            }else {
                viewHolder.Module.setTypeface(null, Typeface.NORMAL);
                viewHolder.Module.setTextColor(Color.parseColor("#050505"));
            }

            viewHolder.link.setText(jsonObject.getString("module_link"));
            viewHolder.Module.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.link=viewHolder.link.getText().toString();
                    Intent intent = new Intent(mcontext, MainActivity.class);
                    mcontext.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // initializes some private fields to be used by RecyclerView.
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Module,link;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            Module= (TextView) itemLayoutView.findViewById(R.id.module);
            link= (TextView) itemLayoutView.findViewById(R.id.link);

        }
    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return list_item.length();
    }

}