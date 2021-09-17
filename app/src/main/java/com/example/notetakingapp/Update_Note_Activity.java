package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_Note_Activity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "description";

    private Button save, cancel;
    private EditText editTextTitle, editTextDesc;
    int getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        setTitle("Edit Note");


        save = findViewById(R.id.btnsaveUpdate);
        cancel = findViewById(R.id.btncancelUpdate);

        editTextTitle = findViewById(R.id.editTextTitleUpdate);
        editTextDesc = findViewById(R.id.editTextDescUpdate);

        addData();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Nothing Update", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });


    }

    private void save() {
        String updatedTitle = editTextTitle.getText().toString();
        String updatedDesc = editTextDesc.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("updatedTitle",updatedTitle);
        intent.putExtra("updatedDesc",updatedDesc);

        if (getID != -1) {
            intent.putExtra("lastId",getID);
            setResult(RESULT_OK, intent);
            finish();
        }


    }

    private void addData() {
        Intent intent = getIntent();
        getID = intent.getIntExtra("id", -1);
        String getTitle = intent.getStringExtra("title");
        String getDescription = intent.getStringExtra("desc");
        editTextTitle.setText(getTitle);
        editTextDesc.setText(getDescription);
    }
}