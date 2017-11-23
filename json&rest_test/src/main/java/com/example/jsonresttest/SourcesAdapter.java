package com.example.jsonresttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SourcesAdapter extends ArrayAdapter<Source> {

    SourcesAdapter(Context context, List<Source> sourceList) {
        super(context, 0, sourceList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.source_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Source source = getItem(position);
        String sourceName = "";

        if (source != null){
            sourceName = source.getName();
        }
        viewHolder.sourceName.setText(sourceName);
        return convertView;
    }



    private class ViewHolder {

        ImageView sourceImage;
        TextView sourceName;

        ViewHolder(View view){
            sourceImage = view.findViewById(R.id.sourceImg);
            sourceName = view.findViewById(R.id.sourceName);
        }
    }
}
