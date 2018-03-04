package edu.cnm.deepdive.prodevme.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "last_name")
  private String lastName;

  @ColumnInfo(name = "first_name")
  private String firstName;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String toString() { return firstName + " " + lastName;}
}
