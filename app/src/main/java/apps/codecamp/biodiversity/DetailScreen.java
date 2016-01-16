package apps.codecamp.biodiversity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class DetailScreen extends AppCompatActivity {
    EditText originname, foundername, location, description;
    Button submit;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView image = (ImageView) findViewById(R.id.imageView3);

        image.setImageBitmap(bmp);

        originname = (EditText) findViewById(R.id.originname);
        foundername = (EditText) findViewById(R.id.foundername);
        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stroriginname = originname.getText().toString();
                String strfoundername = foundername.getText().toString();
                String strlocation = location.getText().toString();
                String strdescription = description.getText().toString();

                if (TextUtils.isEmpty(stroriginname)) {
                    originname.setError("Enter Origin Name");
                    return;
                } else if (TextUtils.isEmpty(strfoundername)) {
                    foundername.setError("Enter Founder Name");
                    return;
                } else if (TextUtils.isEmpty(strlocation)) {
                    location.setError("Enter Location");
                    return;
                } else if (TextUtils.isEmpty(strdescription)) {
                    description.setError("Enter Description");
                    return;
                } else {
                    dialog = ProgressDialog.show(DetailScreen.this, "", "Registering User", true);
                    new Thread(new Runnable() {
                        public void run() {
                            register();
                        }
                    }).start();
                }
            }
        });
    }
    protected void register(){
        try{
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://192.168.43.112/fasttrack_api/register"); // make sure the url is correct.
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("originname",originname.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("foundername",foundername.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("location",location.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("description", description.getText().toString().trim()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response=httpclient.execute(httppost);
            Log.d("RESPON HTTP ", ">>>" + response);
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(DetailScreen.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    Intent logform = new Intent(DetailScreen.this, MainActivity.class);
                    startActivity(logform);
                    finish();
                }
            });
        }
        catch(Exception e){
            dialog.dismiss();
        }
    }
}
