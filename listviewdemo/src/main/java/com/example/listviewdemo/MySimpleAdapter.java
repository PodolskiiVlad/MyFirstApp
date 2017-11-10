package com.example.listviewdemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MySimpleAdapter extends ArrayAdapter<MyListItem> {

    private MainActivity activity;

    MySimpleAdapter(MainActivity activity, List<MyListItem> objects) {
        super(activity.getBaseContext(), 0, objects);
        this.activity = activity;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final MyListItem myListItemElem = getItem(position);
        String image= myListItemElem.getImagePath();
        String text = myListItemElem.getElementName();
        final boolean switchState = myListItemElem.getSwitchState();

        viewHolder.elementName.setText(text);

        Picasso.with(parent.getContext()).load(image).into(viewHolder.elementImage);

        viewHolder.elementSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int imgVisibility = !b ? View.GONE : View.VISIBLE;
                int textVisibility = !b ? View.VISIBLE : View.GONE;

                viewHolder.elementImage.setVisibility(imgVisibility);
                viewHolder.elementName.setVisibility(textVisibility);
                myListItemElem.setSwitchState(b);
            }
        });
        viewHolder.elementSwitch.setChecked(switchState);

        return convertView;
    }

    AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            activity.getDialog(getItem(i));
            return false;
        }
    };

    private class ViewHolder {

        private ImageView elementImage;
        private TextView elementName;
        private Switch elementSwitch;

        private ViewHolder(View view) {
            this.elementImage = view.findViewById(R.id.imgItem);
            this.elementName = view.findViewById(R.id.tvItem);
            this.elementSwitch = view.findViewById(R.id.swItem);
        }

        public TextView getText() {
            return elementName;
        }
    }
}
