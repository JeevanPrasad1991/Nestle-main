package com.cpm.Nestle.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.getterSetter.StockNewGetterSetter;

import java.util.ArrayList;

public class SkuListAdapter extends RecyclerView.Adapter<SkuListAdapter.ViewHolder> {

    ArrayList<StockNewGetterSetter> stockList;
    String sale = "";
    int total_sale= 0;

    public SkuListAdapter(ArrayList<StockNewGetterSetter> stockList) {
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sku_view_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.sku_name.setText(stockList.get(position).getSku());
        viewHolder.os.setText(stockList.get(position).getOpening_facing());
        viewHolder.cs.setText(stockList.get(position).getClosing_stock());
        viewHolder.mds.setText(stockList.get(position).getEd_midFacing());
        sale = String.valueOf((Integer.parseInt(stockList.get(position).getOpening_facing()) + Integer.parseInt(stockList.get(position).getEd_midFacing()) - Integer.parseInt(stockList.get(position).getClosing_stock())));
        viewHolder.sale.setText(sale +"");

        total_sale = total_sale + Integer.parseInt(sale);

        if(position == stockList.size()-1){
            viewHolder.total_sale_layout.setVisibility(View.VISIBLE);
            viewHolder.total_sale.setText(total_sale+"");
        }else{
            viewHolder.total_sale_layout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sku_name,os,mds,cs,sale,total_sale;
        LinearLayout total_sale_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sku_name = (TextView)itemView.findViewById(R.id.sku_name);
            os   = (TextView)itemView.findViewById(R.id.os);
            mds  = (TextView)itemView.findViewById(R.id.mds);
            cs   = (TextView)itemView.findViewById(R.id.cs);
            sale = (TextView)itemView.findViewById(R.id.sale);
            total_sale = (TextView)itemView.findViewById(R.id.total_sale);
            total_sale_layout = (LinearLayout) itemView.findViewById(R.id.total_sale_layout);
        }
    }
}
