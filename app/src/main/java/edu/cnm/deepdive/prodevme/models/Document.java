package edu.cnm.deepdive.prodevme.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Document {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @ColumnInfo(name = "profession")
  private String profession;

  @ColumnInfo(name = "industry")
  private String industry;

  @ColumnInfo(name = "resume")
  private String resume;

  @ColumnInfo(name = "cover_letter")
  private String coverLetter;

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

  @Override
  public String toString() { return profession + " in " + industry + "/n" + resume;}
}
