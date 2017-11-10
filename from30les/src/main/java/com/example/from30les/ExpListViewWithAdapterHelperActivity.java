package com.example.from30les;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class ExpListViewWithAdapterHelperActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLog";

    ExpandableListView elvMain;
    AdapterHelper adapterHelper;
    SimpleExpandableListAdapter expandableListAdapter;
    TextView tvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view_listeners);

        tvInfo = findViewById(R.id.tvInfo);

        adapterHelper = new AdapterHelper(this);
        expandableListAdapter = adapterHelper.getAdapter();

        elvMain = findViewById(R.id.elvMain);
        elvMain.setAdapter(expandableListAdapter);

        elvMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Log.d(LOG_TAG, "onChildClick groupPosition = " + i +
                        " childPosition = " + i1 +
                        " id = " + l);
                tvInfo.setText(adapterHelper.getGroupChildText(i, i1));
                return false;
            }
        });

        elvMain.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Log.d(LOG_TAG, "onGroupClick groupPosition = " + i +
                        " id = " + l);
                if (i == 1) return true;

                return false;
            }
        });

        elvMain.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                Log.d(LOG_TAG, "onGroupExpand groupPosition = " + i);
                tvInfo.setText("Развернули " + adapterHelper.getGroupText(i));
            }
        });

        elvMain.expandGroup(2);
    }
}
