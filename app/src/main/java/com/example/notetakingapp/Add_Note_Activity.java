package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Note_Activity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "description";

    private Button save, cancel;
    private EditText editTextTitle, editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        setTitle("Add Note");

        save = findViewById(R.id.btnsaveUpdate);
        cancel = findViewById(R.id.btncancelUpdate);

        editTextTitle = findViewById(R.id.editTextTitleUpdate);
        editTextDesc = findViewById(R.id.editTextDescUpdate);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Nothing Save", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTitle = editTextTitle.getText().toString();
                String userDesc = editTextDesc.getText().toString();

                if(userTitle.trim().isEmpty() || userDesc.trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please insert info", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_TITLE,userTitle);
                    intent.putExtra(EXTRA_DESCRIPTION,userDesc);

                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }
}