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

public class Rejestracja extends AppCompatActivity {

    String ServerURL = "https://projektsklep.000webhostapp.com/NewUser.php" ;
    EditText uzytkownik, haslo;
    Button buttonRej;
    String TempUzytkownik, TempHaslo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);


        uzytkownik = (EditText)findViewById(R.id.editTextUser);
        haslo = (EditText)findViewById(R.id.editTextPassword);

        buttonRej = (Button)findViewById(R.id.buttonRejestruj);

        buttonRej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (uzytkownik.getText().length()==0){
                    uzytkownik.setError("Uzupełnij pole");
                }

                if (haslo.getText().length()==0){
                    haslo.setError("Uzupełnij pole");
                }


                else if (view == buttonRej) {

                    GetData();

                    InsertData(TempUzytkownik, TempHaslo);
                }

            }
        });
    }

    public void GetData(){

        TempUzytkownik = uzytkownik.getText().toString();
        TempHaslo = haslo.getText().toString();

    }

    public void InsertData(final String uzytkownik, final String haslo){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String UzytkownikHolder = uzytkownik ;
                String HasloHolder = haslo ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("uzytkownik", UzytkownikHolder));
                nameValuePairs.add(new BasicNameValuePair("haslo", HasloHolder));

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

                Toast.makeText(Rejestracja.this, "Rejestracja przebiegła pomyślnie", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(uzytkownik, haslo);
    }

}