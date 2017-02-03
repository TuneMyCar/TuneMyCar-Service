package com.hsylabs.tunemycar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hsylabs.tunemycar.R;
import com.hsylabs.tunemycar.model.Browse;

import java.util.List;

/**
 * Created by Hassan Yousuf on 2/3/2017.
 */

public class ListBrowseAdapter extends BaseAdapter {
    private Context nContext;
    private List<Browse> nRowList;
    double sum = 0;

    public ListBrowseAdapter(Context nContext, List<Browse> nRowList) {
        this.nContext = nContext;
        this.nRowList = nRowList;
    }

    @Override
    public int getCount() {
        return nRowList.size();
    }

    @Override
    public Object getItem(int position) {
        return nRowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nRowList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(nContext, R.layout.item_listview, null);


        TextView s1 = (TextView)v.findViewById(R.id.s1);
        TextView s2 = (TextView)v.findViewById(R.id.s2);
        TextView s3 = (TextView)v.findViewById(R.id.s3);
        TextView s4 = (TextView)v.findViewById(R.id.s4);

        s1.setText("Volume: "+nRowList.get(position).getS2()+" gal");
        s2.setText(nRowList.get(position).getS3());
        s3.setText("Odometer: "+nRowList.get(position).getS1() +" mi");
        s4.setText("Price: RS. "+nRowList.get(position).getS4());

        return v;
    }
}
