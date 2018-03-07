package edu.cnm.deepdive.prodevme;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.cnm.deepdive.prodevme.models.Document;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeViewer extends Fragment implements OnItemClickListener {

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
    resumes.setOnItemClickListener(this);
    new ResumeGetter().execute();
    return resumeView;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    long documentId = ((Document)resumes.getItemAtPosition(position)).getId();
    Bundle bundle = new Bundle();
    bundle.putLong(SingleResume.DOCUMENT_KEY, documentId);
    SingleResume singleResume = new SingleResume();
    singleResume.setArguments(bundle);
    getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,
        android.R.anim.slide_out_right).replace(R.id.fragment_container,
        singleResume).addToBackStack("String").commit();
//    getFragmentManager().beginTransaction().replace
//        (R.id.fragment_container, singleResume).addToBackStack("String").commit();
  }

  private class ResumeGetter extends AsyncTask<Object, Object, List<Document>> {

    @Override
    protected List<Document> doInBackground(Object... objects) {
      return ((MainActivity) getActivity()).getDatabase().documentDao().getAllResumes();
    }

    @Override
    protected void onPostExecute(List<Document> preview) {
        resumes.setAdapter(new ArrayAdapter<Document>(getActivity(),
            R.layout.resume_item, preview));
      }
    }

  }
