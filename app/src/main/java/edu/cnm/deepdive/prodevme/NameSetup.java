package edu.cnm.deepdive.prodevme;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.cnm.deepdive.prodevme.models.User;
import java.util.List;


/**
 *
 * This activity is used when the user
 * first opens the app. It prompts the
 * user to enter their first and last
 * name and stores those values in
 * the user table within the database.
 */
public class NameSetup extends AppCompatActivity implements OnClickListener {

  private Button submit;
  private Button clear;
  private Toast submitted;
  private Toast cleared;
  private String resumePreview;


  public NameSetup() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Inflate the layout for this fragment
    setContentView(R.layout.activity_name_setup);
    submit = findViewById(R.id.submit_name_button);
    clear = findViewById(R.id.clear_name_button);
    submit.setOnClickListener(this);
    clear.setOnClickListener(this);
    submitted = Toast.makeText(this, "Your information has been "
        + "stored!", Toast.LENGTH_SHORT);
    cleared = Toast.makeText(this, "Cleared!", Toast.LENGTH_SHORT);
    new QueryUsers().execute();
  }

  @Override
  public void onClick(View v) {
    if (v == submit) {
      String firstName = ((EditText) findViewById(R.id.first_name)).getText().toString();
      String lastName = ((EditText) findViewById(R.id.last_name)).getText().toString();
//          ((MainActivity) getActivity()).setFirstName(firstName);
      User user = new User();
      user.setFirstName(firstName);
      user.setLastName(lastName);
      new CreateUser().execute(user);
//      getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,
//          android.R.anim.slide_out_right).replace(R.id.fragment_container,
//          new WelcomeScreenFragment()).commit();

    } else if (v == clear) {
      ((EditText) findViewById(R.id.first_name)).setText("");
      ((EditText) findViewById(R.id.last_name)).setText("");
      cleared.show();
    }
  }

  private void transferToMain(long userId, String userName) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtra(MainActivity.USER_ID_KEY, userId);
    intent.putExtra(MainActivity.USER_NAME_KEY, userName);
    startActivity(intent);
    finish();
    overridePendingTransition(android.R.anim.slide_in_left,
          android.R.anim.slide_out_right);
  }

  private class CreateUser extends AsyncTask<User, Void, Long> {

    private User user;

    @Override
    protected Long doInBackground(User... users) {
      user = users[0];
      return ResumeDatabase.getInstance(NameSetup.this).userDao().insert(user);
    }

    @Override
    protected void onPostExecute(Long userId) {
      submitted.show();
      transferToMain(userId, user.getFirstName());

    }
  }

  private class QueryUsers extends AsyncTask<Void, Void, List<User>> {

    @Override
    protected List<User> doInBackground(Void... voids) {
      return ResumeDatabase.getInstance(NameSetup.this).userDao().getAll();
    }

    @Override
    protected void onPostExecute(List<User> users) {
      if (!users.isEmpty()) {
        User user = users.get(0);
        transferToMain(user.getId(), user.getFirstName());
      }
    }


  }
}
