package com.cyclon.com.cryptobalance.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The httpClient class that reads the value from the CryptoCompare API .
 * It take the cryptoCurrency abbreviation as the first argument and the countries abbreviation as the second in its constructor.
 * Insert this abbreviations to the url and fetch the data from the server and returns a string of the data fetched.
 */
public class cryptoHttpClient {
    public static String getCryptoData(String crypto, String country){
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String cryptoUrl = "https://min-api.cryptocompare.com/data/price?fsym="+crypto+"&tsyms="+crypto+","+country;
        try {
            URL url = new URL(cryptoUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream input = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while((line = bufferedReader.readLine()) != null){
                buffer.append(line);
            }
            input.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            try {
                if(bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
