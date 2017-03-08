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
 * Created by manaspande on 2017-02-27.
 */

public class SeatAvailabilityAdapter extends RecyclerView.Adapter<SeatAvailabilityAdapter.ViewHolder> {
    ArrayList<ArrayList<String>> mAl;
    Context mContext;

    public SeatAvailabilityAdapter(Context context, ArrayList<ArrayList<String>> al) {
        mAl = al;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.seat_availability_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String toBind = properCase(mAl.get(position).get(0).split(" ")[0]) + " " + mAl.get(position).get(0).split(" ")[1] + " on " + mAl.get(position).get(1);
        holder.resultsDisplay.setText(toBind);

        Typeface font = Typer.set(mContext).getFont(Font.ROBOTO_MEDIUM);
        holder.resultsDisplay.setTypeface(font);
    }

    @Override
    public int getItemCount() {
        return mAl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.seatAvailabilityResultsDisplay) TextView resultsDisplay;

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
