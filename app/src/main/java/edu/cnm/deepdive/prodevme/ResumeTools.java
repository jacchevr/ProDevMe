package edu.cnm.deepdive.prodevme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeTools extends Fragment {


  public ResumeTools() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View tools = inflater.inflate(R.layout.fragment_resume_tools, container, false);

    return tools;
  }

}
