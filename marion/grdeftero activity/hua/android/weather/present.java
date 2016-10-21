package gr.hua.android.weather;

import android.app.Activity;
//import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class present extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);

        String temp = new String();
        String hum = new String();
        String country = new String();
        String city = new String();

        Intent intent = getIntent();

        temp = intent.getExtras().getCharSequence("temp", "Temperature is not Specified!").toString();
        hum = intent.getExtras().getCharSequence("hum", "Humidity is not Specified!").toString();
        country = intent.getExtras().getCharSequence("country", "Country is not Specified!").toString();
        city = intent.getExtras().getCharSequence("city", "City not Specified!").toString();

        TextView tempView = (TextView) findViewById(R.id.temperature);
        TextView humView = (TextView) findViewById(R.id.humidity);
        TextView countryView = (TextView) findViewById(R.id.country);
        TextView cityView = (TextView) findViewById(R.id.city);

        tempView.setText("Temperature: " + temp + " F");
        humView.setText("Humidity: " + hum + "%");
        countryView.setText("Country: " + country);
        cityView.setText("City: " + city);

    }

}
