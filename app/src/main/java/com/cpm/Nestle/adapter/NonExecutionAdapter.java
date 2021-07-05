package com.cpm.Nestle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.getterSetter.NonPosmReason;

import java.util.ArrayList;
import java.util.List;

public class NonExecutionAdapter extends ArrayAdapter<NonPosmReason> {
    List<NonPosmReason> list;
    Context context;
    int resourceId;

    public NonExecutionAdapter(Context context, int resourceId, ArrayList<NonPosmReason> list) {
        super(context, resourceId, list);
        this.context = context;
        this.list = list;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater =(LayoutInflater.from(context));
        view = inflater.inflate(resourceId, parent, false);
        NonPosmReason cm = list.get(position);
        TextView txt_spinner = (TextView) view.findViewById(R.id.txt_sp_text);
        txt_spinner.setText(list.get(position).getReason());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater.from(context));
        view = inflater.inflate(resourceId, parent, false);
        NonPosmReason cm = list.get(position);
        TextView txt_spinner = (TextView) view.findViewById(R.id.txt_sp_text);
        txt_spinner.setText(cm.getReason());

        return view;
    }

}