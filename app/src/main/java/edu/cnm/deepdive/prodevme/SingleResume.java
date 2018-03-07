package edu.cnm.deepdive.prodevme;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.cnm.deepdive.prodevme.models.Document;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleResume extends Fragment {


  public static final String DOCUMENT_KEY = "documentId";
  private View single;

  public SingleResume() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    single = inflater.inflate(R.layout.fragment_single_resume, container, false);
    new OneResume().execute();


    return single;
  }
  private class OneResume extends AsyncTask<Object, Object, Document> {

    @Override
    protected Document doInBackground(Object... objects) {
      return ((MainActivity) getActivity()).getDatabase().documentDao().
          findById(getArguments().getLong(DOCUMENT_KEY));
    }

    @Override
    protected void onPostExecute(Document show) {
      ((TextView)single.findViewById(R.id.industry)).setText(show.getIndustry());
      ((TextView)single.findViewById(R.id.profession)).setText(show.getProfession());
      ((TextView)single.findViewById(R.id.resume)).setText(show.getResume());
    }
  }

}
