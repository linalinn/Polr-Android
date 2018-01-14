package net.linalinn.apps.shorturl.polr;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by linalinn on 14.01.18.
 */
//?key=666e54872f138c57c3e393f1b4713b&url=https://google.com
public class Polrapi {
    public static String shortUrl(String server, String ApiKey, String URL){
        try {

            URL url = new URL("https://" + server + "/api/v2/action/shorten?key=" + ApiKey +"&url=" + URL );

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedInputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e("NETWORK",e.getLocalizedMessage());
            return e.toString();
        }

    }

    public static String shortUrl(String server, String ApiKey, String URL, String Coustom) throws Exception{
        URL url = new URL("https://" + server + "/api/v2/action/shorten?key=" + ApiKey +"&url=" + URL + "&custom_ending=" + Coustom);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedInputStream is = new BufferedInputStream(con.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String inputLine = "";
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        return sb.toString();

    }
}
