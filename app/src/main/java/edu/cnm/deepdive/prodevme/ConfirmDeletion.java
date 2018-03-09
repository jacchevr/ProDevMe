package edu.cnm.deepdive.prodevme;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import edu.cnm.deepdive.prodevme.models.Document;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmDeletion extends DialogFragment {

  Document document = null;
  OnDeleteListener onDeleteListener = null;

  public OnDeleteListener getOnDeleteListener() {
    return onDeleteListener;
  }

  public void setOnDeleteListener(
      OnDeleteListener onDeleteListener) {
    this.onDeleteListener = onDeleteListener;
  }

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage("Are you sure you want to delete?")
        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            new DeleteTask().execute(document);
          }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
          }
        });
    // Create the AlertDialog object and return it
    return builder.create();
  }

  private class DeleteTask extends AsyncTask<Document, Void, Integer> {

    @Override
    protected Integer doInBackground(Document... documents) {
      try {
        return ResumeDatabase.getInstance(getContext()).documentDao().delete(documents[0]);
      } catch (SQLException ex){
        cancel(true);
        return null;
      }
    }

    @Override
    protected void onPostExecute(Integer recordsAffected) {
      if (onDeleteListener != null) {
        onDeleteListener.onDelete();
      }
    }

    @Override
    protected void onCancelled(Integer recordsAffected) {
      super.onCancelled(recordsAffected);
    }
  }

  public interface OnDeleteListener {
    void onDelete();
  }
}