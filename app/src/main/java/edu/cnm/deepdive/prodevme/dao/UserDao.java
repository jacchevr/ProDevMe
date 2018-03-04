package edu.cnm.deepdive.prodevme.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.prodevme.models.Document;
import edu.cnm.deepdive.prodevme.models.User;
import java.util.List;

@Dao
public interface UserDao {

  @Query("SELECT * FROM user")
  List<User> getAll();

  @Query("SELECT * FROM user WHERE first_name LIKE :firstName AND last_name LIKE :lastName LIMIT 1")
  User findByFullName(String firstName, String lastName);

  @Query("SELECT * FROM user WHERE first_name LIKE :firstName LIMIT 1")
  User findByFirstName(String firstName);

  @Query("SELECT * FROM user WHERE last_name LIKE :lastName LIMIT 1")
  User findByLastName(String lastName);

  @Insert
  long insert(User user);

}
