package com.example.manaspande.indianrailways.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.example.manaspande.indianrailways.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by manaspande on 2017-01-21.
 */

public class TrainRouteAdapter extends RecyclerView.Adapter<TrainRouteAdapter.ViewHolder>{

    Integer mNumberItems;
    ArrayList<ArrayList<String>> mArrayList;
    Context mContext;

    public TrainRouteAdapter(Context context, int numberItems, ArrayList<ArrayList<String>> arrayList) {
        mNumberItems = numberItems;
        mArrayList = arrayList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Boolean shouldAttachToParentImmediately = false;
        int layoutId = R.layout.train_route_viewholder;
        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Typeface font = Typer.set(mContext).getFont(Font.ROBOTO_LIGHT);
        Typeface font2 = Typer.set(mContext).getFont(Font.ROBOTO_MEDIUM);

        holder.stationName.setText(properCase(mArrayList.get(position).get(0)));
        holder.stationName.setTypeface(font2);
        holder.arrivalTime.setText("Arrival Time: "+mArrayList.get(position).get(1));
        holder.arrivalTime.setTypeface(font);
        holder.departureTime.setText("Departure Time: "+mArrayList.get(position).get(2));
        holder.departureTime.setTypeface(font);
        if (mArrayList.get(position).get(3) == "Source" || mArrayList.get(position).get(3) == "Destination") {
            holder.distance.setText(mArrayList.get(position).get(3));
        } else {
            holder.distance.setText(mArrayList.get(position).get(3)+"KM");
        }
        holder.distance.setTypeface(font);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.train_photo) ImageView trainImage;
        @BindView(R.id.station_name) TextView stationName;
        @BindView(R.id.arrival_time) TextView arrivalTime;
        @BindView(R.id.departure_time) TextView departureTime;
        @BindView(R.id.distance) TextView distance;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String properCase(String s) {
        String result = s.substring(0,1);

        for(int i=1; i<s.length(); i++) {
            if(s.substring(i-1,i).equals(" ")) {
                result += s.substring(i,i+1);
            }
            else {
                result += s.substring(i,i+1).toLowerCase();
            }
        }
        return result;
    }
}
