package com.sarkerjr.onfire;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<NotenNodes> {

    public NoteAdapter(Activity context, ArrayList<NotenNodes> notenNotes) {
        super(context, 0, notenNotes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list, parent, false);
        }

        NotenNodes currentNote = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.listOfTitles);
        title.setText(currentNote.getTitle());

        return listItemView;
    }
}
