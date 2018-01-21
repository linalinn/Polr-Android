package net.linalinn.apps.shorturl.polr;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by linalinn on 14.01.18.
 */

public class Polrapi {

    public static String shortUrl(String server, String ApiKey, String URL) throws Exception{
        URL url = new URL("https://" + server + "/api/v2/action/shorten?key=" + ApiKey +"&url=" + URL );

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        if (con.getResponseCode() == 400){
            throw new Exception("malformed URL");
        }

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

    public static String shortUrl(String server, String ApiKey, String URL, String Coustom) throws Exception{
        URL url = new URL("https://" + server + "/api/v2/action/shorten?key=" + ApiKey +"&url=" + URL + "&custom_ending=" + Coustom);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        if (con.getResponseCode() == 400){
            throw new Exception("malformed URL");
        }

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

    public static Boolean isUsed(String server, String ApiKey, String Coustom) throws Exception {
        URL url = new URL("https://" + server + "/api/v2/action/lookup?key=" + ApiKey +"&url_ending=" + Coustom);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int respons = con.getResponseCode();
        if(respons == 200 || respons == 401){
            return true;
        } else if (respons == 404){
            return false;
        } else if (respons == 400){
            throw new Exception("malformed URL");
        }
        return true;
    }
}
