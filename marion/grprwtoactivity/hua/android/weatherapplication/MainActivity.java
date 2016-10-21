package gr.hua.android.weatherapplication;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity that = (Activity) v.getContext();
                EditText editText = (EditText) that.findViewById(R.id.editText);
                String city = editText.getText().toString().trim();
                if(city.isEmpty()){
                    Toast.makeText(that.getApplication(),"City can't be empty!",Toast.LENGTH_LONG).show();
                    return;
                }

                new RetrieveData().execute(city);

            }
        });

    }

    private class RetrieveData extends AsyncTask<String , Void , String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            //android.os.Debug.waitForDebugger();
            String[] list = new String[4];
            try {
                String city = params[0];
                String text = new String();
                String tag = new String();
                String temp = new String();
                String hum = new String();
                String country = new String();

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&mode=xml");
                XmlPullParserFactory xfo = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = xfo.newPullParser();
                xpp.setFeature(xpp.FEATURE_PROCESS_NAMESPACES, false);
                xpp.setInput(url.openStream(),null);


                while(xpp.getEventType() != xpp.END_DOCUMENT) {
                    if (xpp.getEventType() == xpp.START_TAG) {
                        tag = xpp.getName().toString();

                        if (tag.equals("temperature")) {
                            temp = xpp.getAttributeValue(0).toString();
                        } else if (tag.equals("humidity")) {
                            hum = xpp.getAttributeValue(0);
                        }
                        else if (tag.equals("country")) {
                            country = xpp.nextText();
                        }
                    }

                    xpp.next();
                }

                list[0] = temp;
                list[1] = hum;
                list[2] = country;
                list[3] = city;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);

            int c = 0;
            for(String item : strings) {
                if(item == null){
                    c++;
                }
            }

            if(c == 4) {
                Toast.makeText(MainActivity.this,"Invalid City",Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent();
            intent.setAction("gr.hua.android.weather.present");
            //intent.setClassName("gr.hua.android.weather", "gr.hua.android.weather.present");

            intent.putExtra("temp", strings[0]);
            intent.putExtra("hum", strings[1]);
            intent.putExtra("country", strings[2]);
            intent.putExtra("city", strings[3]);

            startActivity(intent);
        }
    }
}
