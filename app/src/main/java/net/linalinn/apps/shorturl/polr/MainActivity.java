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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button      short_button;
    private Button      apiKey_button;
    private ImageButton settings_button;
    private ImageButton share_button;
    private TextView    shorturl_TextView;
    private EditText    apikey_EditText;
    private EditText    URL_EditText;
    private EditText    RURL_EditText;
    private EditText    apiserver_EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        apiKey_button.setOnClickListener(this);
        settings_button.setOnClickListener(this);
        share_button.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if (R.id.button_settings == v.getId()) {

            apiKey_button.setVisibility(View.VISIBLE);
            apikey_EditText.setVisibility(View.VISIBLE);
            apiserver_EditText.setVisibility(View.VISIBLE);
            shorturl_TextView.setVisibility(View.INVISIBLE);
            short_button.setVisibility(View.INVISIBLE);
            settings_button.setVisibility(View.INVISIBLE);
            share_button.setVisibility(View.INVISIBLE);
            URL_EditText.setVisibility(View.INVISIBLE);
            RURL_EditText.setVisibility(View.INVISIBLE);

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.api_key), Context.MODE_PRIVATE);
            String apikey = sharedPref.getString("api", "api34c");
            String apiserver = sharedPref.getString("apiserver", "api34c");

            if ("api34c" != apikey){

                apikey_EditText.setText(apikey);

            }
            if ("api34c" != apiserver){

                apiserver_EditText.setText(apiserver);

            }


        } else if (R.id.button_sahre == v.getId() && (shorturl_TextView.getText().toString() != "Short URL")){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shorturl_TextView.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            
        } else if (R.id.button_API == v.getId()) {

            getApplicationContext().getSharedPreferences(getString(R.string.api_key), Context.MODE_PRIVATE).edit().putString("api",apikey_EditText.getText().toString()).apply();
            getApplicationContext().getSharedPreferences(getString(R.string.api_key), Context.MODE_PRIVATE).edit().putString("apiserver",apiserver_EditText.getText().toString()).apply();

            apiKey_button.setVisibility(View.INVISIBLE);
            apikey_EditText.setVisibility(View.INVISIBLE);
            apiserver_EditText.setVisibility(View.INVISIBLE);
            shorturl_TextView.setVisibility(View.VISIBLE);
            short_button.setVisibility(View.VISIBLE);
            settings_button.setVisibility(View.VISIBLE);
            share_button.setVisibility(View.VISIBLE);
            URL_EditText.setVisibility(View.VISIBLE);
            RURL_EditText.setVisibility(View.VISIBLE);

        } else if (R.id.button_short == v.getId()) {

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.api_key), Context.MODE_PRIVATE);
            String apikey = sharedPref.getString("api", "api34c");
            String apiserver = sharedPref.getString("apiserver", "api34c");

            if (apikey != "api34c" || apiserver != "api34c"){

                Log.d("NETWORK",RURL_EditText.getText().toString());

                if (RURL_EditText.getText().toString().length() != 0){

                    try {

                        if (!Polrapi.isUsed(apiserver,apikey,RURL_EditText.getText().toString())) {
                            shorturl_TextView.setText(Polrapi.shortUrl(apiserver, apikey, URL_EditText.getText().toString(), RURL_EditText.getText().toString()));
                        } else {
                            shorturl_TextView.setText("Sorry, but this URL ending is already in use.");
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.e("NETWORKMain",e.toString());
                        if(e.getMessage() == "malformed URL"){
                            shorturl_TextView.setText("Malformed URL");
                        }

                    }

                } else {

                    try {
                        shorturl_TextView.setText(Polrapi.shortUrl(apiserver,apikey,URL_EditText.getText().toString()));
                    } catch (Exception e) {
                        if(e.getMessage() == "malformed URL"){
                            shorturl_TextView.setText("Malformed URL");
                        }
                        e.printStackTrace();
                    }

                }

            } else {

                if (apikey == "api34c"){

                    Toast.makeText(getApplicationContext(),"Set ApiKey", Toast.LENGTH_LONG).show();

                }

                if (apiserver == "api34c"){

                    Toast.makeText(getApplicationContext(),"Set ApiServer", Toast.LENGTH_LONG).show();

                }

            }

        }
    }
}
