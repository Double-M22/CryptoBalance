package layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.cyclon.com.cryptobalance.ConversionScreen;
import com.cyclon.com.cryptobalance.R;
import com.cyclon.com.cryptobalance.data.cryptoHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Fragment that displays the bitcoin view.
 * */
public class Eth_fragment extends Fragment {

    //An array of 20 countries abbreviation .
    private final String COUNTRY[] = {"USD", "EUR", "NGN", "GBP", "CNY", "ZAR", "GHS", "RUB", "AUD", "JPY", "INR",
            "JMD", "MXN", "CAD", "MAD", "NZD", "KPW", "QAR", "PAB", "CZK"};
    private int flag;
    private final String CURRENCY[] = {"ETH", "ETHEREUM"};
    private double currency_value, country_value;
    private String currency, country;
    private TextView countryText;
    private CardView cardView;
    private ProgressBar pBar;
    private TextView loading;
    private ImageView imageView;
    TextView currencyText;
    Spinner spinner;
    ImageView currencyIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_btc_fragment, container, false);

        currencyText = (TextView)view.findViewById(R.id.currency_text);
        countryText = (TextView)view.findViewById(R.id.countryText);
        cardView = (CardView)view.findViewById(R.id.cardView);
        imageView = (ImageView)view.findViewById(R.id.imgView);
        spinner = (Spinner)view.findViewById(R.id.spinner);
        pBar = (ProgressBar)view.findViewById(R.id.progressBar);
        loading = (TextView)view.findViewById(R.id.loading);
        currencyIcon = (ImageView)view.findViewById(R.id.currency_icon);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getActivity(), R.layout.country_list, COUNTRY);
        spinner.setAdapter(myAdapter);

        //Checking if network is available and alerting user to turn on internet connection, to avoid application crashing.
        if(isNetworkAvailable()){
            country = COUNTRY[0];
            currency = CURRENCY[0];
            currencyText.setText(CURRENCY[1]);
            currencyIcon.setImageResource(R.drawable.eth);
            new JsonTask().execute(currency, country);

            /*
             * Used a spinner to display the list of countries, such that on user click on the spinner
             * a drop down list appears containing twenty countries from which user can pick from.
             * Images of flag corresponding to each country is also loaded to the imageView in the cardView.
             * */
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(isNetworkAvailable()){
                        switch (position){
                            case 0:
                                country = COUNTRY[0];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.united_states;
                                break;
                            case 1:
                                country = COUNTRY[1];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.germany;
                                break;
                            case 2:
                                country = COUNTRY[2];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.nigeria;
                                break;
                            case 3:
                                country = COUNTRY[3];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.united_kingdom;
                                break;
                            case 4:
                                country = COUNTRY[4];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.china;
                                break;
                            case 5:
                                country = COUNTRY[5];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.south_africa;
                                break;
                            case 6:
                                country = COUNTRY[6];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.ghana;
                                break;
                            case 7:
                                country = COUNTRY[7];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.russia;
                                break;
                            case 8:
                                country = COUNTRY[8];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.australia;
                                break;
                            case 9:
                                country = COUNTRY[9];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.japan;
                                break;
                            case 10:
                                country = COUNTRY[10];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.india;
                                break;
                            case 11:
                                country = COUNTRY[11];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.jamaica;
                                break;
                            case 12:
                                country = COUNTRY[12];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.mexico;
                                break;
                            case 13:
                                country = COUNTRY[13];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.canada;
                                break;
                            case 14:
                                country = COUNTRY[14];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.morocco;
                                break;
                            case 15:
                                country = COUNTRY[15];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.new_zealand;
                                break;
                            case 16:
                                country = COUNTRY[16];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.north_korea;
                                break;
                            case 17:
                                country = COUNTRY[17];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.qatar;
                                break;
                            case 18:
                                country = COUNTRY[18];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.panama;
                                break;
                            case 19:
                                country = COUNTRY[19];
                                new JsonTask().execute(currency, country);
                                flag = R.drawable.czech;
                                break;
                        }
                    }else{
                        dialogBuilder();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*
             *On userClick on the cardView a listener set to it takes user to the conversion screen.
             * CryptoCurrency values(currency_value) and the countries Currency worth(country_value)
             * is put as Extras to the conversion screen activity where it is extracted and used for the conversion.
             */
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ConversionScreen.class);
                    intent.putExtra("Currency", currency_value);
                    intent.putExtra("Country", country_value);
                    intent.putExtra("Currency_name", currency);
                    intent.putExtra("Country_name", country);
                    startActivity(intent);
                }
            });

        }else{
            dialogBuilder();
        }

        return view;
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     *This method parse the data received from the cryptoCompare API.
     */
    public void parseData (String dataToParse, String currency, String country){
        try {
            JSONObject jsonObject = new JSONObject(dataToParse);
            currency_value = jsonObject.getDouble(currency);
            country_value = jsonObject.getDouble(country);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * An alert diolog to alert user to turn on Internet connection.
     */
    private void dialogBuilder(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("You have no internet connections. Please go to system settings and enable internet connection.");
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    /**
     * The AsyncTask class that creats a doInBackground Thread that allows for Http request inthe background thread while the UI thread is
     * unaffected.
     */
    public class JsonTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cardView.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return cryptoHttpClient.getCryptoData(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parseData(s, currency, country);
            imageView.setImageResource(flag);
            pBar.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.VISIBLE);
            countryText.setText(""+country_value);
        }
    }
}
