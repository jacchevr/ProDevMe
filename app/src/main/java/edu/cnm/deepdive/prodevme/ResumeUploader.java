package edu.cnm.deepdive.prodevme;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeUploader extends Fragment {


  private Button openDialog;
  private ParcelFileDescriptor mInputPFD;
  private ProgressBar loading;


  public ResumeUploader() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View uploader = inflater.inflate(R.layout.fragment_resume_uploader, container, false);
    openDialog = (Button) uploader.findViewById(R.id.open_dialog);
    loading = (ProgressBar) uploader.findViewById(R.id.loading);
    openDialog.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent,3);
        loading.setVisibility(View.VISIBLE);
      }
    });

    return uploader;
  }
  @Override
  public void onActivityResult(int requestCode, int resultCode,
      Intent returnIntent) {
    final Bundle file = new Bundle();
    // If the selection didn't work
    if (resultCode != RESULT_OK) {
      // Exit without doing anything else
      return;
    } else {
      // Get the file's content URI from the incoming Intent
      final Uri returnUri = returnIntent.getData();
      Log.v("FilePath", returnUri.toString());
      /*
       * Try to open the file for "read" access using the
       * returned URI. If the file isn't found, write to the
       * error log and return.
       */
      try {
        /*
         * Get the content resolver instance for this context, and use it
         * to get a ParcelFileDescriptor for the file.
         */
        mInputPFD = getActivity().getContentResolver().openFileDescriptor(returnUri, "r");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        Log.e("MainActivity", "File not found.");
        return;
      }
      // Get a regular file descriptor for the file
      FileDescriptor fd = mInputPFD.getFileDescriptor();
      PDFBoxResourceLoader.init(getActivity().getApplicationContext());
      new Thread(new Runnable() {
        @Override
        public void run() {
      try {
        String[] strippedPdf = PdfStripper.strip(getActivity().getContentResolver().
            openInputStream(returnUri));
        file.putStringArray(ResumeFragment.RESUME_TEXT_KEY, strippedPdf);
        ResumeFragment resumeFragment = new ResumeFragment();
        resumeFragment.setArguments(file);
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
            R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.fragment_container,
                resumeFragment).addToBackStack("String").commit();
      } catch (IOException e) {
        e.printStackTrace();
      }
        }
      }).start();
    }

  }

}
