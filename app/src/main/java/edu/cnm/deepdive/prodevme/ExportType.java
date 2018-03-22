package edu.cnm.deepdive.prodevme;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import edu.cnm.deepdive.prodevme.models.Document;
import java.util.ArrayList;

public class ExportType extends DialogFragment {

  Document document = null;
  OnShareListener onShareListener = null;

  public OnShareListener getOnShareListener() { return onShareListener; }

  public void setOnShareListener(OnShareListener onShareListener) {
    this.onShareListener = onShareListener;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // Set the dialog title
    builder.setTitle("Export As:")
        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        .setSingleChoiceItems(R.array.export_options, 0, null)
        // Set the action buttons
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // User clicked OK, so save the mSelectedItems results somewhere
            // or return them to the component that opened the dialog
            int selectPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
            if (selectPosition == 0){
              onShareListener.shareText();
            } else {
              onShareListener.sharePdf();
            }
          }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
          }
        });

    return builder.create();
  }

  public interface OnShareListener {
    void shareText();
    void sharePdf();
  }
}
