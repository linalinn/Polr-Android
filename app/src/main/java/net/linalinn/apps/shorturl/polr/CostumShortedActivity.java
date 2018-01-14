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

public class CostumShortedActivity extends AppCompatActivity implements View.OnClickListener {
    private Button short_button;
    private Button      apiKey_button;
    private ImageButton settings_button;
    private ImageButton share_button;
    private TextView shorturl_TextView;
    private EditText apikey_EditText;
    private EditText    URL_EditText;
    private EditText    RURL_EditText;
    private EditText    apiserver_EditText;


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
        apiKey_button       = (Button)      findViewById(R.id.button_API);
        settings_button     = (ImageButton) findViewById(R.id.button_settings);
        share_button        = (ImageButton) findViewById(R.id.button_sahre);
        shorturl_TextView   = (TextView)    findViewById(R.id.shorturl);
        apikey_EditText     = (EditText)    findViewById(R.id.apikey);
        apiserver_EditText  = (EditText)    findViewById(R.id.apiserver);
        URL_EditText        = (EditText)    findViewById(R.id.URL);
        RURL_EditText       = (EditText)    findViewById(R.id.RURL);
        short_button.setOnClickListener(this);
        settings_button.setOnClickListener(this);
        share_button.setOnClickListener(this);

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            URL_EditText.setText(sharedText);
        }

    }


    @Override
    public void onClick(View v) {

        if (RURL_EditText.getText().toString() != ""){

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.api_key), Context.MODE_PRIVATE);
            String apikey = sharedPref.getString("api", "api34c");
            String apiserver = sharedPref.getString("apiserver", "api34c");
            try {

                shorturl_TextView.setText(Polrapi.shortUrl(apiserver,apikey, URL_EditText.getText().toString(), RURL_EditText.getText().toString()));

            } catch (Exception e) {

                e.printStackTrace();
                Log.e("NETWORK",e.toString());
                shorturl_TextView.setText("Sorry, but this URL ending is already in use.");

            }

        }
    }
}
