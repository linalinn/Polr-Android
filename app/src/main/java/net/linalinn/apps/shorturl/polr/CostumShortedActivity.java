package net.linalinn.apps.shorturl.polr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CostumShortedActivity extends AppCompatActivity implements View.OnClickListener {
    private Button      short_button;
    private ImageButton share_button;
    private TextView    shorturl_TextView;
    private EditText    URL_EditText;
    private EditText    RURL_EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costum_shorted);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        short_button        = (Button)      findViewById(R.id.button_short);
        share_button        = (ImageButton) findViewById(R.id.button_sahre);
        shorturl_TextView   = (TextView)    findViewById(R.id.shorturl);
        URL_EditText        = (EditText)    findViewById(R.id.URL);
        RURL_EditText       = (EditText)    findViewById(R.id.RURL);
        short_button.setOnClickListener(this);
        share_button.setOnClickListener(this);

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            URL_EditText.setText(sharedText);
        }

    }
    @Override
    public void onStop(){
        super.onStop();  // Always call the superclass method first
        super.finish();
    }



    @Override
    public void onClick(View v) {

        if (R.id.button_short == v.getId() && RURL_EditText.getText().toString().length() != 0){

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.api_key), Context.MODE_PRIVATE);
            String apikey = sharedPref.getString("api", "api34c");
            String apiserver = sharedPref.getString("apiserver", "api34c");
            String CostumEnding = RURL_EditText.getText().toString();
            try {

                if (apikey != "api34c" && apiserver != "api34c"){
                    if (!Polrapi.isUsed(apiserver,apikey,CostumEnding)) {
                        shorturl_TextView.setText(Polrapi.shortUrl(apiserver, apikey, URL_EditText.getText().toString(), CostumEnding));
                    } else {
                        shorturl_TextView.setText("Sorry, but this URL ending is already in use.");
                    }

                } else {

                    if (apikey == "api34c") {
                        Toast.makeText(getApplicationContext(), "Set ApiKey", Toast.LENGTH_LONG).show();
                    }
                    if (apiserver == "api34c") {
                        Toast.makeText(getApplicationContext(), "Set ApiServer", Toast.LENGTH_LONG).show();
                    }

                }

            } catch (Exception e) {
                if (e.getMessage() == "malformed URL"){
                    shorturl_TextView.setText("Malformed URL");
                }
                e.printStackTrace();
                Log.e("NETWORK",e.toString());
                shorturl_TextView.setText("Network Error");

            }

        } else {
            Toast.makeText(getApplicationContext(),"Set a ShortURL",Toast.LENGTH_LONG).show();
        }

        if (R.id.button_sahre == v.getId() && (shorturl_TextView.getText().toString() != "Short URL")){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shorturl_TextView.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }
}
