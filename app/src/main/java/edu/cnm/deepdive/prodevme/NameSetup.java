package edu.cnm.deepdive.prodevme;


import android.os.AsyncTask;
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
public class NameSetup extends Fragment implements OnClickListener {

  private View setup;
  private Button submit;
  private Button clear;
  private Toast submitted;
  private Toast cleared;
  private String resumePreview;


  public NameSetup() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    setup = inflater.inflate(R.layout.fragment_name_setup, container, false);
    submit = (setup.findViewById(R.id.submit_name_button));
    clear = (setup.findViewById(R.id.clear_name_button));
    submit.setOnClickListener(this);
    clear.setOnClickListener(this);
    submitted = Toast.makeText(getActivity(), "Your information has been "
        + "stored!", Toast.LENGTH_SHORT);
    cleared = Toast.makeText(getActivity(), "Cleared!", Toast.LENGTH_SHORT);
    return setup;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == submit.getId()) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          String firstName = ((EditText) setup.findViewById(R.id.first_name)).getText().toString();
          String lastName = ((EditText) setup.findViewById(R.id.last_name)).getText().toString();
          ((MainActivity) getActivity()).setFirstName(firstName);
          User user = new User();
          user.setFirstName(firstName);
          user.setLastName(lastName);
          ((MainActivity) getActivity()).setUserId(((MainActivity) getActivity()).getDatabase().
              userDao().insert(user));
        }
      }).start();
      submitted.show();
      getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,
          android.R.anim.slide_out_right).replace(R.id.fragment_container,
          new WelcomeScreenFragment()).commit();

    } else if (v.getId() == clear.getId()) {
      ((EditText) setup.findViewById(R.id.first_name)).setText("");
      ((EditText) setup.findViewById(R.id.last_name)).setText("");
      cleared.show();
    }
  }

}
