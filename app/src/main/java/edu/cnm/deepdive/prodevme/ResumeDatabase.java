package edu.cnm.deepdive.prodevme;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import edu.cnm.deepdive.prodevme.dao.DocumentDao;
import edu.cnm.deepdive.prodevme.dao.UserDao;
import edu.cnm.deepdive.prodevme.models.Document;
import edu.cnm.deepdive.prodevme.models.User;

@Database(entities = {Document.class, User.class}, version = 1, exportSchema = true)
public abstract class ResumeDatabase extends RoomDatabase {

  public abstract DocumentDao documentDao();

  public abstract UserDao userDao();
}
