package com.example.from30les;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpandableListViewFillerMain extends AppCompatActivity{

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
        String [] HTCPhones = getResources().getStringArray(R.array.HTC_phones);
        String [] SamsungPhones = getResources().getStringArray(R.array.Samsung_phones);
        String [] LGPhones = getResources().getStringArray(R.array.LG_phones);

        groupData = new ArrayList<>();

        //Зачем каждый раз создавать по одной HashMap()?

        for (String group: groups) {
            attributes = new HashMap<String, String>();
            attributes.put(ATTR_GROUP_NAME, group);
            groupData.add(attributes);
        }

        childData = new ArrayList<ArrayList<Map<String, String>>>();

        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : HTCPhones) {
            attributes = new HashMap<String, String>();
            attributes.put(ATTR_PHONE_NAME, phone);
            childDataItem.add(attributes);
        }
        childData.add(childDataItem);

        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone :SamsungPhones) {
            attributes = new HashMap<>();
            attributes.put(ATTR_PHONE_NAME, phone);
            childDataItem.add(attributes);
        }
        childData.add(childDataItem);

        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone :LGPhones) {
            attributes = new HashMap<>();
            attributes.put(ATTR_PHONE_NAME, phone);
            childDataItem.add(attributes);
        }
        childData.add(childDataItem);



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
