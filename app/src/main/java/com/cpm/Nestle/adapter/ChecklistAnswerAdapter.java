package com.cpm.Nestle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.getterSetter.WindowChecklistAnswer;

import java.util.ArrayList;
import java.util.List;

public class ChecklistAnswerAdapter extends ArrayAdapter<WindowChecklistAnswer> {
    List<WindowChecklistAnswer> list;
    Context context;
    int resourceId;

    public ChecklistAnswerAdapter(Context context, int resourceId, ArrayList<WindowChecklistAnswer> list) {
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
        WindowChecklistAnswer cm = list.get(position);
        TextView txt_spinner = (TextView) view.findViewById(R.id.txt_sp_text);
        txt_spinner.setText(list.get(position).getAnswer());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater.from(context));
        view = inflater.inflate(resourceId, parent, false);
        WindowChecklistAnswer cm = list.get(position);
        TextView txt_spinner = (TextView) view.findViewById(R.id.txt_sp_text);
        txt_spinner.setText(cm.getAnswer());

        return view;
    }

}