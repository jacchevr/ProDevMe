package edu.cnm.deepdive.prodevme;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.cnm.deepdive.prodevme.ConfirmDeletion.OnDeleteListener;
import edu.cnm.deepdive.prodevme.models.Document;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleResume extends Fragment implements OnClickListener {


  public static final String DOCUMENT_KEY = "documentId";
  private View single;
  private Button edit;
  private Button delete;
  private Document document;
  private Toast deleted;

  public SingleResume() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    single = inflater.inflate(R.layout.fragment_single_resume, container, false);
    new OneResume().execute();
    edit = (Button) single.findViewById(R.id.edit);
    edit.setOnClickListener(this);
    delete = (Button) single.findViewById(R.id.delete_button);
    delete.setOnClickListener(this);
    deleted = Toast.makeText(getActivity(), "Resume Deleted", Toast.LENGTH_SHORT);
    return single;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == edit.getId()) {
      ResumeFragment fragmentResumeEdit = new ResumeFragment();
      Bundle bundle = new Bundle();
      bundle.putLong(DOCUMENT_KEY, getArguments().getLong(DOCUMENT_KEY));
      fragmentResumeEdit.setArguments(bundle);
      getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
          R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
          .replace(R.id.fragment_container,
              fragmentResumeEdit).addToBackStack("String").commit();
    } else {
      ConfirmDeletion confirmDelete = new ConfirmDeletion();
      confirmDelete.setDocument(document);
      confirmDelete.setOnDeleteListener(new OnDeleteListener() {
        @Override
        public void onDelete() {
          deleted.show();
          getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
              R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
              .replace(R.id.fragment_container,
                  new ResumeViewer()).commit();
        }
      });
      confirmDelete.show(getFragmentManager(), "dialog");
    }
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
      ((TextView)single.findViewById(R.id.industry)).setText(show.getIndustry());
      ((TextView)single.findViewById(R.id.profession)).setText(show.getProfession());
      ((TextView)single.findViewById(R.id.resume)).setText(show.getResume());
    }
  }

}
