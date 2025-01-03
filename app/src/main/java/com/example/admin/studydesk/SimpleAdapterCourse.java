package com.example.admin.studydesk;

import android.content.Context;
import android.content.Intent;

import android.text.Html;
import android.text.method.LinkMovementMethod;
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



public class SimpleAdapterCourse extends
        RecyclerView.Adapter<SimpleAdapterCourse.MyViewHolder> {

    private JSONObject jsonObject ;
    private JSONArray list_item ;
    public Context mcontext;
    public String[] List1;
    Boolean alert,delete;
    public List<String> list = new ArrayList<String>();
    RecyclerView recyclerView;
    public SimpleAdapterCourse(JSONArray list, Context context)  {
        alert=false;
        list_item = list;
        mcontext=context;
    }


    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public SimpleAdapterCourse.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.courselist, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {
        try {
            jsonObject=list_item.getJSONObject(position);
            viewHolder.Module.setText(jsonObject.getString("course_name").toString());
            viewHolder.link.setText(jsonObject.getString("id").toString());
//            viewHolder.university.setText("University : "+jsonObject.getString("university").toString());
//            viewHolder.offamt.setText("Offer Amount : "+jsonObject.getString("offer_amt").toString());
//            viewHolder.subamt.setText("Subscription Amount : "+jsonObject.getString("subscription_amt").toString());
//            //viewHolder.demo.setText(jsonObject.getString("demo_link").toString());
//            //viewHolder.syllabus.setText(jsonObject.getString("syllabus").toString());
//
//            viewHolder.syllabus.setClickable(true);
//            viewHolder.syllabus.setMovementMethod(LinkMovementMethod.getInstance());
//            String text = "<a href='"+jsonObject.getString("syllabus").toString()+"'> Download Syllabus </a>";
//            viewHolder.syllabus.setText(Html.fromHtml(text));
//
//            viewHolder.demo.setClickable(true);
//            viewHolder.demo.setMovementMethod(LinkMovementMethod.getInstance());
//            String text1 = "<a href='"+jsonObject.getString("demo_link").toString()+"'> Watch Demo </a>";
//            viewHolder.demo.setText(Html.fromHtml(text1));
//            if (jsonObject.getString("active").toString().trim().equals("Y") ){
//                viewHolder.status.setText("Status : Active");
//            }else{
//                viewHolder.status.setText("Status : Inactive");
//            }


            viewHolder.Module.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        if (jsonObject.getString("active").toString().trim().equals("Y") ) {
                            ModuleSelection.courseid = viewHolder.link.getText().toString().trim();
                            Intent intent = new Intent(mcontext, ModuleSelection.class);
                            mcontext.startActivity(intent);
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (jsonObject.getString("active").toString().trim().equals("Y") ) {
                            ModuleSelection.courseid = viewHolder.link.getText().toString().trim();
                            Intent intent = new Intent(mcontext, ModuleSelection.class);
                            mcontext.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.syllabus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (jsonObject.getString("active").toString().trim().equals("Y") ) {
                            ModuleSelection.courseid = viewHolder.link.getText().toString().trim();
                            Intent intent = new Intent(mcontext, ModuleSelection.class);
                            mcontext.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.demo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (jsonObject.getString("active").toString().trim().equals("Y") ) {
                            ModuleSelection.courseid = viewHolder.link.getText().toString().trim();
                            Intent intent = new Intent(mcontext, ModuleSelection.class);
                            mcontext.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.offamt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (jsonObject.getString("active").toString().trim().equals("Y") ) {
                            ModuleSelection.courseid = viewHolder.link.getText().toString().trim();
                            Intent intent = new Intent(mcontext, ModuleSelection.class);
                            mcontext.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.subamt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (jsonObject.getString("active").toString().trim().equals("Y") ) {
                            ModuleSelection.courseid = viewHolder.link.getText().toString().trim();
                            Intent intent = new Intent(mcontext, ModuleSelection.class);
                            mcontext.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.university.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (jsonObject.getString("active").toString().trim().equals("Y") ) {
                            ModuleSelection.courseid = viewHolder.link.getText().toString().trim();
                            Intent intent = new Intent(mcontext, ModuleSelection.class);
                            mcontext.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //viewHolder.link.setText(list_item.get(position).substring(101,1100).trim());

    }

    // initializes some private fields to be used by RecyclerView.
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Module,link,syllabus,university,subamt,offamt,demo,status;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            Module= (TextView) itemLayoutView.findViewById(R.id.course);
            link= (TextView) itemLayoutView.findViewById(R.id.courseid);
            syllabus= (TextView) itemLayoutView.findViewById(R.id.syllabus);
            demo= (TextView) itemLayoutView.findViewById(R.id.demo);
            university= (TextView) itemLayoutView.findViewById(R.id.university);
            subamt= (TextView) itemLayoutView.findViewById(R.id.subscriptionamt);
            offamt= (TextView) itemLayoutView.findViewById(R.id.offeramt);
            status= (TextView) itemLayoutView.findViewById(R.id.status);


        }
    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return list_item.length();
    }

}