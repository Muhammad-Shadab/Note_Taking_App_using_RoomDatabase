package com.example.notetakingapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allNotes = repository.getAllNote();
    }

    public void Insert(Note note) {
        repository.insert(note);
    }

    public void Update(Note note) {
        repository.update(note);
    }

    public void Delete(Note note) { repository.delete(note); }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
