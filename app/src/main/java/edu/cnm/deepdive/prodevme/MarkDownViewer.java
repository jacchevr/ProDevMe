package edu.cnm.deepdive.prodevme;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.cnm.deepdive.prodevme.ConfirmDeletion.OnDeleteListener;
import edu.cnm.deepdive.prodevme.models.Document;
import us.feras.mdv.MarkdownView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarkDownViewer extends Fragment implements OnClickListener {


  public static final String DOCUMENT_KEY = "documentId";
  private View single;
  private Button edit;
  private Button delete;
  private Document document;
  private Toast deleted;
  private FloatingActionButton fab;
  private Toast text;

  public MarkDownViewer() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    single = inflater.inflate(R.layout.fragment_mark_down_viewer, container, false);
    new OneResume().execute();
    MarkdownView markdownView = (MarkdownView) single.findViewById(R.id.resume);
    //Used for buttons at the bottom of the page
//    edit = (Button) single.findViewById(R.id.edit);
//    edit.setOnClickListener(this);
//    delete = (Button) single.findViewById(R.id.delete_button);
//    delete.setOnClickListener(this);
    fab = (FloatingActionButton) single.findViewById(R.id.fab);
    fab.setOnClickListener(this);
    deleted = Toast.makeText(getActivity(), "Resume Deleted", Toast.LENGTH_SHORT);
    text = Toast.makeText(getActivity(), "Plain Text View", Toast.LENGTH_SHORT);
    return single;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  //Used for buttons at the bottom of the page
  @Override
  public void onClick(View v) {
    if (v.getId() == fab.getId()) {
//      ResumeFragment fragmentResumeEdit = new ResumeFragment();
//      Bundle bundle = new Bundle();
//      bundle.putLong(DOCUMENT_KEY, getArguments().getLong(DOCUMENT_KEY));
//      fragmentResumeEdit.setArguments(bundle);
//      getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
//          R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
//          .replace(R.id.fragment_container,
//              fragmentResumeEdit).addToBackStack("String").commit();
//    } else if (v.getId() == delete.getId()){
//      ConfirmDeletion confirmDelete = new ConfirmDeletion();
//      confirmDelete.setDocument(document);
//      confirmDelete.setOnDeleteListener(new OnDeleteListener() {
//        @Override
//        public void onDelete() {
//          deleted.show();
//          getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
//              R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
//              .replace(R.id.fragment_container,
//                  new ResumeViewer()).commit();
//        }
//      });
//      confirmDelete.show(getFragmentManager(), "dialog");
//    } else {
      SingleResume fragment = new SingleResume();
      fragment.setArguments(getArguments());
      getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
          R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left).
          replace(R.id.fragment_container, fragment).addToBackStack("String").commit();
      text.show();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.edit_resume) {
      ResumeFragment fragmentResumeEdit = new ResumeFragment();
      Bundle bundle = new Bundle();
      bundle.putLong(DOCUMENT_KEY, getArguments().getLong(DOCUMENT_KEY));
      fragmentResumeEdit.setArguments(bundle);
      getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
          R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
          .replace(R.id.fragment_container,
              fragmentResumeEdit).addToBackStack("String").commit();
    } else if (item.getItemId() == R.id.delete_resume) {
      ConfirmDeletion confirmDelete = new ConfirmDeletion();
      confirmDelete.setDocument(document);
      confirmDelete.setOnDeleteListener(new OnDeleteListener() {
        @Override
        public void onDelete() {
          deleted.show();
          getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
              R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
              .replace(R.id.fragment_container,
                  new ResumeViewer()).commit();
        }
      });
      confirmDelete.show(getFragmentManager(), "dialog");
    }
      return super.onOptionsItemSelected(item);
  }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
      // Inflate the menu; this adds items to the action bar if it is present.
      inflater.inflate(R.menu.resume_menu, menu);
      super.onCreateOptionsMenu(menu, inflater);
    }

    private class OneResume extends AsyncTask<Object, Object, Document> {

      @Override
      protected Document doInBackground(Object... objects) {
        return ((MainActivity) getActivity()).getDatabase().documentDao().
            findById(getArguments().getLong(DOCUMENT_KEY));
      }

      @Override
      protected void onPostExecute(Document show) {
        document = show;
        ((TextView) single.findViewById(R.id.industry)).setText(show.getIndustry());
        ((TextView) single.findViewById(R.id.profession)).setText(show.getProfession());
        ((MarkdownView) single.findViewById(R.id.resume)).loadMarkdown(show.getResume());
      }
    }
}
