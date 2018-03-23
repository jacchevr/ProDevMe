package edu.cnm.deepdive.prodevme;

import static android.support.v4.content.FileProvider.getUriForFile;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import edu.cnm.deepdive.prodevme.ExportType.OnShareListener;
import edu.cnm.deepdive.prodevme.models.Document;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.crypto.tls.MACAlgorithm;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  public static final String USER_ID_KEY = "user_id";
  public static final String USER_NAME_KEY = "user_name";
  public static final String DOCUMENT_KEY = "documentId";
  private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");

  private ResumeDatabase rDatabase;
  private long userId;
  private String firstName;
  private File newFile;
  private Uri contentUri;
  private Document document;
  private Toast shared;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    Intent intent = getIntent();
    userId = intent.getLongExtra(USER_ID_KEY, 0);
    firstName = intent.getStringExtra(USER_NAME_KEY);
    shared = Toast.makeText(MainActivity.this, "Shared!", Toast.LENGTH_SHORT);
    getSupportFragmentManager().beginTransaction()
              .replace(R.id.fragment_container, new WelcomeScreenFragment())
              .commit();

//    new Thread(new Runnable() {
//      @Override
//      public void run() {
//        List<User> userList = getDatabase().userDao().getAll();
//        if (userList.isEmpty()){
//          getSupportFragmentManager().beginTransaction()
//              .replace(R.id.fragment_container, new NameSetup())
//              .commit();
//        } else {
//          setUserId(userList.get(0).getId());
//          setFirstName(userList.get(0).getFirstName());
//          getSupportFragmentManager().beginTransaction()
//              .replace(R.id.fragment_container, new WelcomeScreenFragment())
//              .commit();
//        }
//      }
//    }).start();


  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  // Deafult option menu for main activity
//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.main, menu);
//    return true;
//  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.menu_item_share) {
      final String wholeDocument = (document.getIndustry()) + "\n" + (document.getProfession()) + "\n" +
          "\n" + (document.getResume());
      final File textFilePath = new File(MainActivity.this.getFilesDir(), "export_resumes");
      ExportType exportType = new ExportType();
      newFile = new File(textFilePath, "resume_" + document.getId() + ".txt");
      textFilePath.mkdirs();
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        writer.write(wholeDocument);
        writer.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      contentUri = getUriForFile
          (MainActivity.this, "edu.cnm.deepdive.prodeveme.fileprovider", newFile);
      exportType.setOnShareListener(new OnShareListener() {
        @Override
        public void shareText() {
          Intent shareIntent = new Intent();
          shareIntent.setAction(Intent.ACTION_SEND);
          shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
          shareIntent.setType("text/plain");
          startActivityForResult(Intent.createChooser(shareIntent, "Share"), 0);
        }

        @Override
        public void sharePdf() {
          new Pdf().execute();
        }
      });
      exportType.show(getSupportFragmentManager(), "dialog");
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      shared.show();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    // Everything that is commented out is the same replacement, but with no animation.
    if (id == R.id.home) {
      getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
          R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
          .replace(R.id.fragment_container,
          new WelcomeScreenFragment()).addToBackStack("String").commit();
//      getSupportFragmentManager().beginTransaction()
//          .replace(R.id.fragment_container, new WelcomeScreenFragment())
//          .commit();
    } else if (id == R.id.my_resumes) {
      getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
          R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
          .replace(R.id.fragment_container,
          new ResumeViewer()).addToBackStack("String").commit();
//      getSupportFragmentManager().beginTransaction()
//          .replace(R.id.fragment_container, new ResumeViewer())
//          .commit();
    } else if (id == R.id.create_resume) {
      getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
          R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
          .replace(R.id.fragment_container,
          new ResumeFragment()).addToBackStack("String").commit();
//      getSupportFragmentManager().beginTransaction()
//          .replace(R.id.fragment_container, new ResumeFragment())
//          .commit();

    } else if (id == R.id.upload_resume) {
      getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,
          R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
          .replace(R.id.fragment_container,
              new ResumeUploader()).addToBackStack("String").commit();
    } else if (id == R.id.news) {

    } else if (id == R.id.resume_create) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  public ResumeDatabase getDatabase() {
    return ResumeDatabase.getInstance(this);
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  private class Pdf extends AsyncTask<Object, Object, Object> {

    public static final String MARKDOWN_URL = "http://www.markdowntopdf.com/app/download/";

    @Override
    protected Object doInBackground(Object... objects) {
      OkHttpClient client = new OkHttpClient();
      final File textFilePath = new File(MainActivity.this.getFilesDir(), "export_resumes");
      File pdfFile = new File(textFilePath, "resume_" + document.getId() + ".pdf");

      RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
          .addFormDataPart("file", newFile.getName(),
              RequestBody.create(MEDIA_TYPE_TEXT, newFile))
          .build();

      Request request = new Request.Builder().url("http://www.markdowntopdf.com/app/upload")
          .post(requestBody).build();

      Response response = null;
      try {
        response = client.newCall(request).execute();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      if (!response.isSuccessful()) {
        try {
          throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      String folderName = null;
      String fileName = null;
      try {
        JSONObject responseJson = new JSONObject(response.body().string());
        folderName = responseJson.getString("foldername");
        fileName = responseJson.getString("urlfilename");
      } catch (JSONException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      contentUri = getUriForFile
          (MainActivity.this, "edu.cnm.deepdive.prodeveme.fileprovider", pdfFile);
      downloadFile(MARKDOWN_URL + folderName + "/" + fileName, pdfFile);
      Intent shareIntent = new Intent();
      shareIntent.setAction(Intent.ACTION_SEND);
      shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
      shareIntent.setType("application/pdf");
      startActivityForResult(Intent.createChooser(shareIntent, "Share"), 0);
      return null;
    }
  }

  private static void downloadFile(String url, File outputFile) {
    try {
      URL u = new URL(url);
      InputStream is = u.openStream();

      DataInputStream dis = new DataInputStream(is);

      byte[] buffer = new byte[1024];
      int length;

      FileOutputStream fos = new FileOutputStream(outputFile);
      while ((length = dis.read(buffer))>0) {
        fos.write(buffer, 0, length);
      }

    } catch (MalformedURLException mue) {
      Log.e("SYNC getUpdate", "malformed url error", mue);
    } catch (IOException ioe) {
      Log.e("SYNC getUpdate", "io error", ioe);
    } catch (SecurityException se) {
      Log.e("SYNC getUpdate", "security error", se);
    }
  }

  public void setDocument(Document document) {
    this.document = document;
  }
}
