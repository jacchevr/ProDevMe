package edu.cnm.deepdive.prodevme.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
    parentColumns = "id",
    childColumns = "user_id"))

public class Document {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "user_id")
  private long userId;

  @ColumnInfo(name = "profession")
  private String profession;

  @ColumnInfo(name = "industry")
  private String industry;

  @ColumnInfo(name = "resume")
  private String resume;

  @ColumnInfo(name = "cover_letter")
  private String coverLetter;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getResume() {
    return resume;
  }

  public void setResume(String resume) {
    this.resume = resume;
  }

  public String getCoverLetter() {
    return coverLetter;
  }

  public void setCoverLetter(String coverLetter) {
    this.coverLetter = coverLetter;
  }

  public long getUserId() { return userId; }

  public void setUserId(long userId) { this.userId = userId; }

  @Override
  public String toString() { return String.format("%s...",resume);}
}
