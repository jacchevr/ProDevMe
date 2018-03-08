package edu.cnm.deepdive.prodevme;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import edu.cnm.deepdive.prodevme.models.Document;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeScreenFragment extends Fragment implements OnClickListener {


  private TextView resume;
  private Document document;

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
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);

    if(hour<12)
    {
      welcomeText.setText(String.format("Good morning, %s!", ((MainActivity)
          getActivity()).getFirstName()));
    }else if(hour<17)
    {
      welcomeText.setText(String.format("Good afternoon, %s!", ((MainActivity)
          getActivity()).getFirstName()));
    }else
    {
      welcomeText.setText(String.format("Good evening, %s!", ((MainActivity)
          getActivity()).getFirstName()));
    }
    // If non alternating welcome message is wanted. This will display "Welcome, (firstName)".
//    welcomeText.setText(String.format("Welcome, %s!", ((MainActivity)
//        getActivity()).getFirstName()));
    new ResumePreviewGetter().execute();
    return welcome;
  }

  @Override
  public void onClick(View v) {
    long documentId = document.getId();
    Bundle bundle = new Bundle();
    bundle.putLong(SingleResume.DOCUMENT_KEY, documentId);
    SingleResume singleResume = new SingleResume();
    singleResume.setArguments(bundle);
    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
        R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
        .replace(R.id.fragment_container,
        singleResume).addToBackStack("String").commit();
    // No Animation
//    getFragmentManager().beginTransaction().replace
//        (R.id.fragment_container, singleResume).addToBackStack("String").commit();
  }

  public class ResumePreviewGetter extends AsyncTask<String, String, Document> {

    @Override
    protected Document doInBackground(String[] objects) {
      return ((MainActivity) getActivity()).getDatabase().documentDao().getLastResume();
    }

    @Override
    protected void onPostExecute(Document preview) {
      document = preview;
      if (preview != null) {
        resume.setText(String.format("%s...", preview));
        resume.setOnClickListener(WelcomeScreenFragment.this);
      }
    }
  }
}
