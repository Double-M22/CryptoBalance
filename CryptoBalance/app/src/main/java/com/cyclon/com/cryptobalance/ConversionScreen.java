package com.cyclon.com.cryptobalance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The conversion screen.
 */
public class ConversionScreen extends AppCompatActivity {

    EditText amount;
    TextView currencyText, country;
    ImageView imgView;

    private double currency_value, country_value;
    String currency_name, country_name;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_screen);

        amount = (EditText)findViewById(R.id.amount);
        currencyText = (TextView)findViewById(R.id.currencyText);
        country = (TextView)findViewById(R.id.country);
        imgView = (ImageView)findViewById(R.id.imageView);

        //This receives the extras added to the intent from the BTC and ETH fragments.
        intent = getIntent();
        currency_value = intent.getDoubleExtra("Currency", 0.00);
        country_value = intent.getDoubleExtra("Country", 0.00);
        currency_name = intent.getStringExtra("Currency_name");
        country_name = intent.getStringExtra("Country_name");

        //This checks if the cryptoCurrency is bitcoin or ethereum, and get the symbol from the drawable folder.
        if(currency_name.equals("BTC")){
            imgView.setImageResource(R.drawable.btc);
        }else if(currency_name.equals("ETH")){
            imgView.setImageResource(R.drawable.eth);
        }

        // Sets the countries abbreviation.
        country.setText(country_name);


        /*
        This is an addChangeListener that listens for change in the editText
        and performs arithmetic conversion operations to the amount added,
        converting the countries amount entered to cryptoCurrency equivalent.

        So as user enters amount to the editText it is immediately converted to its equivalent
        and when the amount is too large the user is alerted that the amount is to large.

        And values remains zero until a non zero number is seen in the second decimal place.
         */
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable no) {
                if(no.length() > 2 && no.length() < 15){
                    imgView.setVisibility(View.VISIBLE);
                    double  a = Double.parseDouble(amount.getText().toString());
                    String text = arithmetic(a);
                    currencyText.setText(text);
                }else if(no.length() == 15) {
                    currencyText.setText("Value too large!!!");
                    imgView.setVisibility(View.INVISIBLE);
                }else if(no.length() < 2) {
                    currencyText.setText("0.00");
                    imgView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     *The arithmetic class where the arithmetic operations are carried out.
     * The result is returned with two decimal places to avoid excess decimal point vales.
     */
    private String arithmetic (double a){
        String string = null;
        double ans;
        ans = (a * currency_value)/country_value;
        if(ans < 0.01)
            string ="0.00";
        else {
            string = String.format("%.2f", ans);
        }
        return string ;
    }
}
