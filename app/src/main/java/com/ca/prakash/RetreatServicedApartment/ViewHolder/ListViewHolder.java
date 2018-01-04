package com.ca.prakash.RetreatServicedApartment.ViewHolder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;


import com.ca.prakash.RetreatServicedApartment.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;



/**
 * Created by prakash on 8/16/2017.
 */

public class ListViewHolder extends ChildViewHolder {
    private TextView lists;
    Typeface type;
    public ListViewHolder(View itemView) {
        super(itemView);
        type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/Raleway-ExtraLight.ttf");
        lists=(TextView)itemView.findViewById(R.id.single_list_id);
        lists.setTypeface(type);
    }
    public void setListname(String name)
    {
        lists.setText(name);
    }
}
