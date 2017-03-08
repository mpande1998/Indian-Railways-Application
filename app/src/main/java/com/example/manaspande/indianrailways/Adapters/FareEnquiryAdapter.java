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

public class FareEnquiryAdapter extends RecyclerView.Adapter<FareEnquiryAdapter.ViewHolder> {

    Integer mNumberItems;
    ArrayList<ArrayList<String>> mArrayList;
    Context mContext;

    public FareEnquiryAdapter(Context context, int numberItems, ArrayList<ArrayList<String>> arrayList) {
        mNumberItems = numberItems;
        mArrayList = arrayList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Integer layoutId = R.layout.fare_enquiry_viewholder;
        Boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);
        ViewHolder resultViewHolder = new ViewHolder(view);

        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArrayList<String> arrayListToBind = mArrayList.get(position);
        String code = arrayListToBind.get(0);
        String name = arrayListToBind.get(1);
        String fare = arrayListToBind.get(2);

        Typeface font = Typer.set(mContext).getFont(Font.ROBOTO_MEDIUM);
        holder.fareEnquiryCodeTextView.setTypeface(font);
        holder.fareEnquiryCodeTextView.setText(code);
        holder.fareEnquiryNameTextView.setTypeface(font);
        holder.fareEnquiryNameTextView.setText(name);
        holder.fareEnquiryFareTextView.setTypeface(font);
        holder.fareEnquiryFareTextView.setText("Rs. " + fare);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fareEnquiryCodeTextView) TextView fareEnquiryCodeTextView;
        @BindView(R.id.fareEnquiryNameTextView) TextView fareEnquiryNameTextView;
        @BindView(R.id.fareEnquiryFareTextView) TextView fareEnquiryFareTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
