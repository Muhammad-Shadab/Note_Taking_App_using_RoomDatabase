package com.example.notetakingapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Database;

import java.util.List;

public class Repository {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNote;

    public Repository(Application application) {
        NoteDatabase db = NoteDatabase.getInstance(application);
        noteDao = db.noteDao();
        allNote = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNote(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNote(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNote(noteDao).execute(note);
    }

    public LiveData<List<Note>> getAllNote() {
        return allNote;
    }

    private static class InsertNote extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNote(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNote extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNote(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class UpdateNote extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNote(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }


}
