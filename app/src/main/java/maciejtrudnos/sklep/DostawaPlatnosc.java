package maciejtrudnos.sklep;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class DostawaPlatnosc extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String ServerURL = "https://projektsklep.000webhostapp.com/zamowienie.php" ;

    EditText OsobaKontaktowa, Adres, KodPocztowy, Miasto, Telefon, Email;
    String Produkt, Cena;
    Button ZamawiamButton;
    String TempOsobaKontaktowa, TempAdres, TempKodPocztowy, TempMiasto, TempTelefon, TempEmail, TempMetodaPlatnosci, TempProdukt, TempCena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dostawa_platnosc);

        Intent intent = getIntent();

        Produkt = intent.getStringExtra("produkt");
        Cena = intent.getStringExtra("cena");

        ((TextView)findViewById(R.id.ProduktView)).setText(Produkt);
        ((TextView)findViewById(R.id.CenaView)).setText(Cena);

        Spinner spinner = (Spinner) findViewById(R.id.MetodaPlatnosci);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.MetodaPlatnosci,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        OsobaKontaktowa = (EditText)findViewById(R.id.editText);
        Adres = (EditText)findViewById(R.id.editText2);
        KodPocztowy = (EditText)findViewById(R.id.editText3);
        Miasto = (EditText)findViewById(R.id.editText4);
        Telefon = (EditText)findViewById(R.id.editText5);
        Email = (EditText)findViewById(R.id.editText6);

        ZamawiamButton = (Button)findViewById(R.id.ZamawiamButton);

        ZamawiamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (OsobaKontaktowa.getText().length()==0){
                    OsobaKontaktowa.setError("Uzupełnij pole");
                }

                if (Adres.getText().length()==0){
                    Adres.setError("Uzupełnij pole");
                }

                if (KodPocztowy.getText().length()==0){
                    KodPocztowy.setError("Uzupełnij pole");
                }

                if (Miasto.getText().length()==0){
                    Miasto.setError("Uzupełnij pole");
                }

                if (Telefon.getText().length()==0){
                    Telefon.setError("Uzupełnij pole");
                }

                if (Email.getText().length()==0){
                    Email.setError("Uzupełnij pole");
                }

                else if (view == ZamawiamButton){
                    GetData();
                    InsertData(TempOsobaKontaktowa, TempAdres, TempKodPocztowy, TempMiasto, TempTelefon, TempEmail, TempMetodaPlatnosci, TempProdukt, TempCena);
                }

            }
        });
    }

    public void GetData(){

        TempOsobaKontaktowa = OsobaKontaktowa.getText().toString();
        TempAdres = Adres.getText().toString();
        TempKodPocztowy = KodPocztowy.getText().toString();
        TempMiasto = Miasto.getText().toString();
        TempTelefon = Telefon.getText().toString();
        TempEmail = Email.getText().toString();

        Spinner Spinner =(Spinner) findViewById(R.id.MetodaPlatnosci);
        TempMetodaPlatnosci = Spinner.getSelectedItem().toString();

        TempProdukt = Produkt.toString();
        TempCena = Cena.toString();

    }

    public void InsertData(final String OsobaKontaktowa, final String Adres, final String KodPocztowy, final String Miasto, final String Telefon, final String Email, final String MetodaPlatnosci, final String Produkt, final String Cena){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String OsobaKontaktowaHolder = OsobaKontaktowa ;
                String AdresHolder = Adres ;
                String KodPocztowyHolder = KodPocztowy ;
                String MiastoHolder = Miasto ;
                String TelefonHolder = Telefon ;
                String EmailHolder = Email ;
                String MetodaPlatnosciHolder = MetodaPlatnosci ;
                String ProduktHolder = Produkt ;
                String CenaHolder = Cena ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("OsobaKontaktowa", OsobaKontaktowaHolder));
                nameValuePairs.add(new BasicNameValuePair("Adres", AdresHolder));
                nameValuePairs.add(new BasicNameValuePair("KodPocztowy", KodPocztowyHolder));
                nameValuePairs.add(new BasicNameValuePair("Miasto", MiastoHolder));
                nameValuePairs.add(new BasicNameValuePair("Telefon", TelefonHolder));
                nameValuePairs.add(new BasicNameValuePair("Email", EmailHolder));
                nameValuePairs.add(new BasicNameValuePair("SposobPlatnosci", MetodaPlatnosciHolder));
                nameValuePairs.add(new BasicNameValuePair("Produkt", ProduktHolder));
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
                alert();
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(OsobaKontaktowa, Adres, KodPocztowy, Miasto, Telefon, Email, MetodaPlatnosci, Produkt, Cena);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void alert() {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage ("Twoje zamówienie zostało złożone poprawnie.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MenuGlowne.class);
                        startActivity(intent);

                    }
                })
                .setTitle("Gratulacje")
                .create();
        myAlert.show();
    }

}