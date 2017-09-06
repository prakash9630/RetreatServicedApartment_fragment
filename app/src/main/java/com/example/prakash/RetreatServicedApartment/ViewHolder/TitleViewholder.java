package com.example.prakash.RetreatServicedApartment.ViewHolder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.example.prakash.RetreatServicedApartment.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


/**
 * Created by prakash on 8/16/2017.
 */

public class TitleViewholder extends GroupViewHolder {
    private TextView listtitle;
    Typeface type;
    public TitleViewholder(View itemView) {
        super(itemView);
        type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/Raleway-ExtraLight.ttf");
        listtitle=(TextView)itemView.findViewById(R.id.list_title);
        listtitle.setTypeface(type);

    }
    public void setTitlename(String name)
    {
        listtitle.setText(name);
    }
}
