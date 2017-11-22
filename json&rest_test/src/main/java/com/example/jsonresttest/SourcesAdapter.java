package com.example.jsonresttest;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SourcesAdapter extends ArrayAdapter<Source> {

    private Context context;

    public SourcesAdapter(Context context, List<Source> sourceList) {
        super(context, 0, sourceList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.source_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String sourceName = getItem(position).getName();
        viewHolder.sourceName.setText(sourceName);
        return convertView;
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(parent.getContext(), PaperSourceArticlesActivity.class);
            intent.putExtra(PaperSourceArticlesActivity.INTENT_SOURCE_ID_KEY, getItem(position).getId());
            intent.putExtra(PaperSourceArticlesActivity.INTENT_PRIMARY_KEY, ""+getItem(position).getDbID());
            context.startActivity(intent);
        }
    };

    private class ViewHolder {

        ImageView sourceImage;
        TextView sourceName;

        ViewHolder(View view){
            sourceImage = view.findViewById(R.id.sourceImg);
            sourceName = view.findViewById(R.id.sourceName);
        }
    }
}
