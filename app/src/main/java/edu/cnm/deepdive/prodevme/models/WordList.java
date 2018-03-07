package edu.cnm.deepdive.prodevme.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WordList {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "profession")
  private String profession;

  @ColumnInfo(name = "word")
  private String word;

}
