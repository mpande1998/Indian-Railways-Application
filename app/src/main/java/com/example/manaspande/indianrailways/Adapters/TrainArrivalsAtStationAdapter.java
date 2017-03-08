package com.example.manaspande.indianrailways.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.example.manaspande.indianrailways.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by manaspande on 2017-01-22.
 */

public class TrainArrivalsAtStationAdapter extends RecyclerView.Adapter<TrainArrivalsAtStationAdapter.ViewHolder> {

    Integer mNumberItems;
    ArrayList<ArrayList<String>> mArrayList;
    Context mContext;

    public TrainArrivalsAtStationAdapter(Context context, int numberItems, ArrayList<ArrayList<String>> arrayList) {
        mNumberItems = numberItems;
        mArrayList = arrayList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Integer layoutId = R.layout.train_arrivals_at_station_viewholder;
        Boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);
        ViewHolder resultViewHolder = new ViewHolder(view);

        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArrayList<String> arrayListToBind = mArrayList.get(position);
        String scharr = "Scheduled Arrival: " + arrayListToBind.get(0);
        String delayarr = "Delayed Arrival: " + arrayListToBind.get(1);
        String schdep = "Scheduled Departure: " + arrayListToBind.get(2);
        String delaydep = "Delayed Departure: " + arrayListToBind.get(3);
        String name = arrayListToBind.get(4);
        String number = arrayListToBind.get(5);

        Typeface font = Typer.set(mContext).getFont(Font.ROBOTO_LIGHT);
        Typeface font2 = Typer.set(mContext).getFont(Font.ROBOTO_MEDIUM);

        holder.scharr.setText(scharr);
        holder.scharr.setTypeface(font);
        holder.delayarr.setText(delayarr);
        holder.delayarr.setTypeface(font);
        holder.schdep.setText(schdep);
        holder.schdep.setTypeface(font);
        holder.delaydep.setText(delaydep);
        holder.delaydep.setTypeface(font);
        holder.name.setText(name);
        holder.name.setTypeface(font2);
        holder.number.setText(number);
        holder.number.setTypeface(font2);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.scharr) TextView scharr;
        @BindView(R.id.delayarr) TextView delayarr;
        @BindView(R.id.schdep) TextView schdep;
        @BindView(R.id.delaydep) TextView delaydep;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.number) TextView number;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
