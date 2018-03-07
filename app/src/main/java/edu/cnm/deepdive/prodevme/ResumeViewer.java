package edu.cnm.deepdive.prodevme;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.cnm.deepdive.prodevme.models.Document;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeViewer extends Fragment {

  private ListView resumes;


  public ResumeViewer() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View resumeView = inflater.inflate(R.layout.fragment_resume_viewer,
        container, false);
    resumes = resumeView.findViewById(R.id.resume_list);
    new ResumeGetter().execute();
    return resumeView;
  }

  private class ResumeGetter extends AsyncTask<Object, Object, List<String>> {

    @Override
    protected List<String> doInBackground(Object... objects) {
      return ((MainActivity) getActivity()).getDatabase().documentDao().getAllResumes();
    }

    @Override
    protected void onPostExecute(List<String> preview) {
        resumes.setAdapter(new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1, preview));
      }
    }

  }
