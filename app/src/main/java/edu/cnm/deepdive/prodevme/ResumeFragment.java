package edu.cnm.deepdive.prodevme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import edu.cnm.deepdive.prodevme.models.Document;
import edu.cnm.deepdive.prodevme.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment {


  public ResumeFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_resume, container, false);
    ((EditText)view.findViewById(R.id.resumeInsert)).getText().toString();
    new Thread(new Runnable() {
      @Override
      public void run() {
        User user = new User();
        user.setFirstName("Steve");
        Document document = new Document();
        document.setProfession("Java Programmer");
        document.setIndustry("IT");
        document.setResume(EditText)R.id.resumeInsert; //-Chris
        document.setUserId(((MainActivity) getActivity()).getDatabase().userDao().insert(user));
//        document.setUserId(user.getId());
    ((MainActivity)getActivity()).getDatabase().documentDao().insert(document);
      }
    }).start();


    return view;
  }

  

}
