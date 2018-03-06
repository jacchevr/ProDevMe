package edu.cnm.deepdive.prodevme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.cnm.deepdive.prodevme.models.Document;
import edu.cnm.deepdive.prodevme.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment implements OnClickListener {

  private View view;
  private Button submit;
  private Button clear;
  private Toast submitted;
  private Toast cleared;


  public ResumeFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_resume, container, false);
    submit = (view.findViewById(R.id.submit_button));
    clear = (view.findViewById(R.id.clear_button));
    submit.setOnClickListener(this);
    clear.setOnClickListener(this);
    submitted = Toast.makeText(getActivity(), "Your information has been "
        + "stored!",Toast.LENGTH_SHORT);
    cleared = Toast.makeText(getActivity(), "Cleared!",Toast.LENGTH_SHORT);

    return view;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == submit.getId()) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          String resume = ((EditText) view.findViewById(R.id.resumeInsert)).getText().toString();
          String industry = ((EditText) view.findViewById(R.id.industry)).getText().toString();
          String profession = ((EditText) view.findViewById(R.id.profession)).getText().toString();
          Document document = new Document();
          document.setProfession(profession);
          document.setIndustry(industry);
          document.setResume(resume);
          document.setUserId(((MainActivity) getActivity()).getUserId());
          ((MainActivity) getActivity()).getDatabase().documentDao().insert(document);
        }
      }).start();
      submitted.show();

    } else if (v.getId() == clear.getId()) {
      ((EditText) view.findViewById(R.id.resumeInsert)).setText("");
      ((EditText) view.findViewById(R.id.industry)).setText("");
      ((EditText) view.findViewById(R.id.profession)).setText("");
      cleared.show();
    }
  }
}
