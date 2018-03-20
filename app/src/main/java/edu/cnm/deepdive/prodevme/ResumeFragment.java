package edu.cnm.deepdive.prodevme;


import static edu.cnm.deepdive.prodevme.SingleResume.DOCUMENT_KEY;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.cnm.deepdive.prodevme.ConfirmDeletion.OnDeleteListener;
import edu.cnm.deepdive.prodevme.models.Document;
import edu.cnm.deepdive.prodevme.models.User;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment implements OnClickListener {

  public static final String RESUME_TEXT_KEY = "resumeTextKey";
  private View view;
  private Button submit;
  private Button clear;
  private Toast submitted;
  private Toast cleared;
  private Document document;


  public ResumeFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_resume, container, false);
    //Used for buttons at the bottom of the page
//    submit = (view.findViewById(R.id.submit_button));
//    clear = (view.findViewById(R.id.clear_button));
//    submit.setOnClickListener(this);
//    clear.setOnClickListener(this);
    submitted = Toast.makeText(getActivity(), "Your information has been "
        + "stored!", Toast.LENGTH_SHORT);
    cleared = Toast.makeText(getActivity(), "Cleared!", Toast.LENGTH_SHORT);
    if (getArguments() != null && getArguments().containsKey(RESUME_TEXT_KEY)) {
      StringBuilder stringBuilder = new StringBuilder();
      String[] lines = getArguments().getStringArray(RESUME_TEXT_KEY);
      for (int i = 0; i < lines.length; i++) {
        stringBuilder.append(lines[i]);
        stringBuilder.append("\n");
      }
      ((EditText) view.findViewById(R.id.resume_insert)).setText(stringBuilder.toString());
    } else if ((getArguments() != null && getArguments().containsKey(DOCUMENT_KEY))) {
      new OneResume().execute();
    }
    return view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onClick(View v) {
//    if (v.getId() == submit.getId()) {
//      new Thread(new Runnable() {
//        @Override
//        public void run() {
//          String resume = ((EditText) view.findViewById(R.id.resumeInsert)).getText().toString();
//          String industry = ((EditText) view.findViewById(R.id.industry)).getText().toString();
//          String profession = ((EditText) view.findViewById(R.id.profession)).getText().toString();
//          //Creating new document instance if none is present.
//          if (document == null) {
//            document = new Document();
//          }
//          document.setProfession(profession);
//          document.setIndustry(industry);
//          document.setResume(resume);
//          document.setUserId(((MainActivity) getActivity()).getUserId());
//          long documentId;
//          if (document.getId() == 0) {
//            documentId = ((MainActivity) getActivity()).getDatabase().documentDao()
//                .insert(document);
//          } else {
//            documentId = document.getId();
//            ((MainActivity) getActivity()).getDatabase().documentDao().update(document);
//          }
//          Bundle bundle = new Bundle();
//          bundle.putLong(SingleResume.DOCUMENT_KEY, documentId);
//          SingleResume singleResume = new SingleResume();
//          singleResume.setArguments(bundle);
//          getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
//              R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left).
//              replace(R.id.fragment_container, singleResume).commit();
//        }
//      }).start();
//      submitted.show();
//
//    } else if (v.getId() == clear.getId()) {
//      ((EditText) view.findViewById(R.id.resumeInsert)).setText("");
//      ((EditText) view.findViewById(R.id.industry)).setText("");
//      ((EditText) view.findViewById(R.id.profession)).setText("");
//      cleared.show();
//    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.submit_resume) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          String resume = ((EditText) view.findViewById(R.id.resume_insert)).getText().toString();
          String industry = ((EditText) view.findViewById(R.id.industry)).getText().toString();
          String profession = ((EditText) view.findViewById(R.id.profession)).getText().toString();
          //Creating new document instance if none is present.
          if (document == null) {
            document = new Document();
          }
          document.setProfession(profession);
          document.setIndustry(industry);
          document.setResume(resume);
          document.setUserId(((MainActivity) getActivity()).getUserId());
          long documentId;
          if (document.getId() == 0) {
            documentId = ((MainActivity) getActivity()).getDatabase().documentDao()
                .insert(document);
          } else {
            documentId = document.getId();
            ((MainActivity) getActivity()).getDatabase().documentDao().update(document);
          }
          Bundle bundle = new Bundle();
          bundle.putLong(SingleResume.DOCUMENT_KEY, documentId);
          SingleResume singleResume = new SingleResume();
          singleResume.setArguments(bundle);
          getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
              R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left).
              replace(R.id.fragment_container, singleResume).commit();
        }
      }).start();
      submitted.show();
    } else {
      ((EditText) view.findViewById(R.id.resume_insert)).setText("");
      ((EditText) view.findViewById(R.id.industry)).setText("");
      ((EditText) view.findViewById(R.id.profession)).setText("");
      cleared.show();
          }
          return super.onOptionsItemSelected(item);
        }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // Inflate the menu; this adds items to the action bar if it is present.
    inflater.inflate(R.menu.resume_creator_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  private class OneResume extends AsyncTask<Object, Object, Document> {

    @Override
    protected Document doInBackground(Object... objects) {
      return ((MainActivity) getActivity()).getDatabase().documentDao().
          findById(getArguments().getLong(DOCUMENT_KEY));
    }

    @Override
    protected void onPostExecute(Document show) {
      document = show;
      ((TextView) view.findViewById(R.id.industry)).setText(show.getIndustry());
      ((TextView) view.findViewById(R.id.profession)).setText(show.getProfession());
      ((TextView) view.findViewById(R.id.resume_insert)).setText(show.getResume());
    }
  }
}

