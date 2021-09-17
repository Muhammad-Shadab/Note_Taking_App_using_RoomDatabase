package com.example.notetakingapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDatabaseCall)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback RoomDatabaseCall =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new populateDatabase(instance).execute();

                }
            };

    private static class populateDatabase extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        private populateDatabase(NoteDatabase database)
        {
            noteDao = database.noteDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1"));
            noteDao.insert(new Note("Title 2", "Description 2"));
            noteDao.insert(new Note("Title 3", "Description 3"));
            noteDao.insert(new Note("Title 4", "Description 4"));
            noteDao.insert(new Note("Title 5", "Description 5"));
            noteDao.insert(new Note("Title 6", "Description 6"));
            noteDao.insert(new Note("Title 7", "Description 7"));
            noteDao.insert(new Note("Title 8", "Description 8"));

            return null;
        }
    }
}
