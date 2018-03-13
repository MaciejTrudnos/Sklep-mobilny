package maciejtrudnos.sklep;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MenuGlowne extends AppCompatActivity {

    HttpResponse httpResponse;
    TextView aktualnosci;
    JSONObject jsonObject = null ;
    String StringHolder = "" ;

    String HttpURL = "https://projektsklep.000webhostapp.com/JSonTextView.php";
    String url = "https://projektsklep.000webhostapp.com/Baner.jpg";

    ImageView Baner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_glowne);

        Baner = (ImageView) findViewById(R.id.Baner);
        loadBaner(url);

        aktualnosci = (TextView)findViewById(R.id.textView3);
        new GetDataFromServerIntoTextView(MenuGlowne.this).execute();

    }

    void loadBaner(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(Baner,new com.squareup.picasso.Callback(){

                    public void onSuccess() {

                    }
                    public void onError() {
                    }

                });

    }

    public void WszystkieKategorie (View view) {
        Intent intent = new Intent(this, WszystkieKategorie.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Activity dla Item
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.Kontakt){
            Intent myintent = new Intent (this, Kontakt.class);
            startActivity(myintent);

            return true;
        }

        if (id == R.id.Reklamacja){
            Intent myintent = new Intent (this, Reklamacja.class);
            startActivity(myintent);

            return true;
        }

        if (id == R.id.AplikacjaInfo){
            Intent myintent = new Intent (this, AplikacjaInfo.class);
            startActivity(myintent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class GetDataFromServerIntoTextView extends AsyncTask<Void, Void, Void> {

        public Context context;

        public GetDataFromServerIntoTextView(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(HttpURL);

            try {
                httpResponse = httpClient.execute(httpPost);

                StringHolder = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{

                JSONArray jsonArray = new JSONArray(StringHolder);
                jsonObject = jsonArray.getJSONObject(0);


            } catch ( JSONException e) {
                e.printStackTrace();
            }

            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            try {

                aktualnosci.setText(jsonObject.getString("wiadomosc"));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
