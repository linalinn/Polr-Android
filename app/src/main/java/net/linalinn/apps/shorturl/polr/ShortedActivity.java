package net.linalinn.apps.shorturl.polr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShortedActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView shorturl_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorted);
        shorturl_TextView   = (TextView)    findViewById(R.id.shorturl);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.api_key), Context.MODE_PRIVATE);
            String apikey = sharedPref.getString("api", "api34c");
            String apiserver = sharedPref.getString("apiserver", "api34c");

            if (apikey != "api34c" || apiserver != "api34c"){

                shorturl_TextView.setText(Polrapi.shortUrl(apiserver, apikey, sharedText));

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

    @Override
    public void onClick(View v) {
        if (R.id.button_sahre == v.getId() && (shorturl_TextView.getText().toString() != "Short URL")){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shorturl_TextView.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }
}
