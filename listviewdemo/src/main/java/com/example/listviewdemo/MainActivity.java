package com.example.listviewdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_IMAGE_KEY = "elementImage";
    public static final String MAIN_ELEMENT_NAME_KEY = "elementName";
    public static final String PARCE_KEY = "data";
    private final int INTENT_REQUEST_CODE = 1;

    private MyListItem itemToChange;
    private MySimpleAdapter adapter;
    private List<MyListItem> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            data = savedInstanceState.getParcelableArrayList(PARCE_KEY);
        } else {
            data = new ArrayList<>();
        }

        final ListView listView = findViewById(R.id.lvMain);

        adapter = new MySimpleAdapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(adapter.itemLongClickListener);

    }

    public void onClick(View view) {
        adapter.add(new MyListItem());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARCE_KEY, (ArrayList<? extends Parcelable>) data);
    }

    public void getDialog(final MyListItem listItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setNeutralButton(R.string.edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemToChange = listItem;
                Intent intent = new Intent(MainActivity.this, EditElementActivity.class);
                intent.putExtra(MAIN_IMAGE_KEY, listItem.getImagePath());
                intent.putExtra(MAIN_ELEMENT_NAME_KEY, listItem.getElementName());
                startActivityForResult(intent, INTENT_REQUEST_CODE);
            }
        });

        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, R.string.deleted, Toast.LENGTH_LONG).show();
                adapter.remove(listItem);
            }
        });

        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        String change = data.getStringExtra(EditElementActivity.CHANGE_KEY);
        switch (data.getStringExtra(EditElementActivity.ELEMENT_THAT_CHANGE_KEY)){
            case EditElementActivity.IMAGE_CHANGE:
                itemToChange.setImagePath(change);
                break;
            case EditElementActivity.TEXT_CHANGE:
                itemToChange.setElementName(change);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
