package edu.cnm.deepdive.prodevme;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.prodevme.dao.DocumentDao;
import edu.cnm.deepdive.prodevme.dao.UserDao;
import edu.cnm.deepdive.prodevme.models.Document;
import edu.cnm.deepdive.prodevme.models.User;

@Database(entities = {Document.class, User.class}, version = 1, exportSchema = true)
public abstract class ResumeDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "document";
  private static ResumeDatabase instance = null;

  public abstract DocumentDao documentDao();

  public abstract UserDao userDao();

  static ResumeDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), ResumeDatabase.class,
          DATABASE_NAME).addCallback(new Callback(context)).build();
    }
    return instance;
  }

  private static class Callback extends RoomDatabase.Callback {
    private Context context;

    private Callback(Context context){
      this.context = context;
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      // TODO Database Initialization
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }
  }

}
