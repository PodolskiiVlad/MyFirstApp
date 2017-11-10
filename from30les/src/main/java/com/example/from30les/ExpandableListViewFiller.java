package com.example.from30les;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpandableListViewFiller extends AppCompatActivity {

    final String ATTR_GROUP_NAME= "groupName";
    final String ATTR_PHONE_NAME= "phoneName";

    ArrayList<Map<String, String>> groupData;
    ArrayList<Map<String, String>> childDataItem;
    ArrayList<ArrayList<Map<String, String>>> childData;

    Map<String, String> attributes;

    ExpandableListView elvMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tree_with_expandable_listview);

        String [] groups = getResources().getStringArray(R.array.mobile_groups);
        groupData = new ArrayList<>();
        childData = new ArrayList<>();

        for (String group: groups) {
            String groupArrayName = group.concat("_phones");
            String[] groupPhones = getResources().getStringArray(getResources().getIdentifier(groupArrayName, "array", getPackageName())); //Примерно

            attributes = new HashMap<>();
            attributes.put(ATTR_GROUP_NAME, group);

            groupData.add(attributes);

            childDataItem = new ArrayList<>();
            for (String phone : groupPhones) {
                attributes = new HashMap<>();
                attributes.put(ATTR_PHONE_NAME, phone);
                childDataItem.add(attributes);
            }
            childData.add(childDataItem);
        }

        String [] childFrom = new String[] {ATTR_PHONE_NAME};
        int childTo[] = new int[] {android.R.id.text1};

        String[] groupFrom = new String[] {ATTR_GROUP_NAME};
        int [] groupTo = new int[] {android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        elvMain = findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }
}
