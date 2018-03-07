package edu.cnm.deepdive.prodevme;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeScreenFragment extends Fragment {


  private TextView resume;

  public WelcomeScreenFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View welcome = inflater.inflate(R.layout.fragment_welcome_screen, container, false);
    TextView welcomeText = welcome.findViewById(R.id.welcomeMessage);
    resume = welcome.findViewById(R.id.resumePreview);
    welcomeText.setText(String.format("Welcome, %s!", ((MainActivity)
        getActivity()).getFirstName()));
    new ResumePreviewGetter().execute();
    return welcome;
  }

  public class ResumePreviewGetter extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String[] objects) {
      return ((MainActivity) getActivity()).getDatabase().documentDao().getLastResume();
    }

    @Override
    protected void onPostExecute(String preview) {
      if (preview != null) {
        resume.setText(String.format("%s...", preview));
      }
    }
  }
}
