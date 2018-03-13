package maciejtrudnos.sklep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NowyProdukt extends AppCompatActivity {

    String ServerURL = "https://projektsklep.000webhostapp.com/NewProdukt.php" ;
    EditText nazwa, url, cena ;
    Button button;
    String TempNazwa, TempURL, TempCena ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowy_produkt);

        nazwa = (EditText)findViewById(R.id.editTextNazwa);
        url = (EditText)findViewById(R.id.editTextURL);
        cena = (EditText)findViewById(R.id.editTextCena);
        button = (Button)findViewById(R.id.buttonDodajProd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nazwa.getText().length()==0){
                    nazwa.setError("Uzupełnij pole");
                }

                if (url.getText().length()==0){
                    url.setError("Uzupełnij pole");
                }

                if (cena.getText().length()==0){
                    cena.setError("Uzupełnij pole");
                }

                else if (view == button) {

                    GetData();

                    InsertData(TempNazwa, TempURL, TempCena);
                }

            }
        });
    }

    public void GetData(){

        TempNazwa = nazwa.getText().toString();
        TempURL = url.getText().toString();
        TempCena = cena.getText().toString();

    }

    public void InsertData(final String nazwa, final String url, final String cena){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String NameHolder = nazwa ;
                String URLHolder = url ;
                String CenaHolder = cena ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("image_title", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("image_url", URLHolder));
                nameValuePairs.add(new BasicNameValuePair("Cena", CenaHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Sukces";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(NowyProdukt.this, "Produkt dodany prawidłowo", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(nazwa, url, cena);
    }

}