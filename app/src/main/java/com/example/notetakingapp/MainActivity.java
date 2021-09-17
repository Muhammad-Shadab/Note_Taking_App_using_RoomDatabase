package com.example.notetakingapp;

import static android.nfc.NfcAdapter.EXTRA_ID;
import static com.example.notetakingapp.Add_Note_Activity.EXTRA_DESCRIPTION;
import static com.example.notetakingapp.Add_Note_Activity.EXTRA_TITLE;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static final int EDIT_CODE = 2;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        menu = findViewById(R.id.main_menu);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
               noteAdapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.Delete(noteAdapter.getNote(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Note note) {
               Intent intent = new Intent(MainActivity.this, Update_Note_Activity.class);
               intent.putExtra("id",note.getId());
               intent.putExtra("title",note.getName());
               intent.putExtra("desc",note.getDescription());
               startActivityForResult(intent,EDIT_CODE);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_menu:
                Intent intent = new Intent(MainActivity.this,Add_Note_Activity.class);
                startActivityForResult(intent, REQUEST_CODE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            String titleFromAdd = data.getStringExtra(EXTRA_TITLE);
            String descFromAdd = data.getStringExtra(EXTRA_DESCRIPTION);
            Note note = new Note(titleFromAdd,descFromAdd);
            noteViewModel.Insert(note);
        }
        else if(requestCode == EDIT_CODE && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra("lastId",-1);
            String updateFromAddNote = data.getStringExtra("updatedTitle");
            String updatedDesc = data.getStringExtra("updatedDesc");
            Note note = new Note(updateFromAddNote,updatedDesc);
            note.setId(id);
            noteViewModel.Update(note);


        }
    }
}