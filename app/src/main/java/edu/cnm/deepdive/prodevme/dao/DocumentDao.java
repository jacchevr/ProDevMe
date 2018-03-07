package edu.cnm.deepdive.prodevme.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.prodevme.models.Document;
import java.util.List;

@Dao
public interface DocumentDao {

  @Query("SELECT * FROM document")
  List<Document> getAll();

  @Query("SELECT * FROM document WHERE resume LIKE :resume LIMIT 1")
  Document findByResume(String resume);

  @Query("SELECT * FROM document WHERE profession LIKE :profession LIMIT 1")
  Document findByProfession(String profession);

  @Query("SELECT * FROM document WHERE industry LIKE :industry LIMIT 1")
  Document findByIndustry(String industry);

  @Query("SELECT * FROM document WHERE id LIKE :id LIMIT 1")
  Document findById(int id);

  @Query("SELECT substr(resume, 0, 200) FROM document ORDER BY id DESC LIMIT 1")
  String getLastResume();

  @Query("SELECT substr(resume, 0, 200) FROM document ORDER BY id DESC")
  List<String> getAllResumes();

  @Insert
  long insert(Document document);
}
