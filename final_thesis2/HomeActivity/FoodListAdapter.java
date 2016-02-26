package com.emmanbraulio.final_thesis2.HomeActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emmanbraulio.final_thesis2.R;

import java.util.ArrayList;

/**
 * Created by Derek on 2/8/2016.
 */
public abstract class FoodListAdapter extends BaseAdapter {
    Context context;
    ArrayList<FoodList> foodList;

    public FoodListAdapter(Context context,ArrayList<FoodList> list)
    {
        this.context = context;
        foodList = list;
    }

    public int getCount()
    {
        return foodList.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemID(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup arg2)
    {
        FoodList foodListL = foodList.get(position);
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout,null);
        }
        TextView foodName = (TextView) convertView.findViewById(R.id.edit_pref_text);
        foodName.setText(foodListL.getFoodName());
        return convertView;
    }
}
