package com.example.manaspande.indianrailways.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;

import java.util.List;

/**
 * Created by manaspande on 2017-03-04.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context mContext;

    public SpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);

        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        Typeface font = Typer.set(mContext).getFont(Font.ROBOTO_LIGHT);
        view.setTypeface(font);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        Typeface font = Typer.set(mContext).getFont(Font.ROBOTO_LIGHT);
        view.setTypeface(font);
        return view;
    }
}