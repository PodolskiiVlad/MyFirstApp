package com.example.listviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EditElementActivity extends AppCompatActivity{

    public static final String ELEMENT_THAT_CHANGE_KEY = "element";
    public static final String TEXT_CHANGE = "text";
    public static final String IMAGE_CHANGE = "image";
    public static final String CHANGE_KEY = "change";
    private String elementToChange;

    private EditText elementName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_element);

        ImageView imageOnScreen = findViewById(R.id.imageEditActivity);

        String imagePath = getIntent().getStringExtra(MainActivity.MAIN_IMAGE_KEY);
        Picasso.with(getBaseContext()).load(imagePath).into(imageOnScreen);

        TextView textOnScreen = findViewById(R.id.textEditActivity);
        textOnScreen.setText(getIntent().getStringExtra(MainActivity.MAIN_ELEMENT_NAME_KEY));
    }

    public void onClick(View view) {
        String dialogTitleText = "";
        switch (view.getId()) {
            case R.id.btnChangeImage:
                elementToChange = IMAGE_CHANGE;
                dialogTitleText = "Path to image:";
                break;
            case R.id.btnChangeText:
                elementToChange = TEXT_CHANGE;
                dialogTitleText = "Element name:";
                break;
        }
        getDialog(dialogTitleText);
    }


    public void getDialog(String elementToChangeName){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.edit_activity_dialog, null);
        elementName = dialogView.findViewById(R.id.editActivityDialogEditText);
        TextView dialogTitle = dialogView.findViewById(R.id.editActivityDialogTitleText);
        dialogTitle.setText(elementToChangeName);

        dialogView.findViewById(R.id.editActivityDialogBtnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataBack();
            }
        });
        builder.setView(dialogView).show();
    }

    private void sendDataBack(){
        Intent intent = new Intent();
        intent.putExtra(CHANGE_KEY, elementName.getText().toString());
        intent.putExtra(ELEMENT_THAT_CHANGE_KEY, elementToChange);
        setResult(RESULT_OK, intent);
        finish();
    }
}
