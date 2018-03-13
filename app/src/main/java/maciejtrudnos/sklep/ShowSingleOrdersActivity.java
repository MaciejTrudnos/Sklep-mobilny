package maciejtrudnos.sklep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class ShowSingleOrdersActivity extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;

    String HttpURL = "https://projektsklep.000webhostapp.com/FiltrZamowienie.php";

    String HttpUrlDeleteRecord = "https://projektsklep.000webhostapp.com/UsunZamowienie.php";

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView Produkt, Cena, OsobaKontaktowa, Adres, KodPocztowy, Miasto, Telefon, Email, SposobPlatnosci;
    String ProduktHolder, CenaHolder, OsobaKontaktowaHolder, AdresHolder, KodPocztowyHolder, MiastoHolder, TelefonHolder, EmailHolder, SposobPlatnosciHolder;
    Button DeleteButton;
    String TempItem;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_orders);

        Produkt = (TextView)findViewById(R.id.textView38);
        Cena = (TextView)findViewById(R.id.textView39);
        OsobaKontaktowa = (TextView)findViewById(R.id.textView40);
        Adres = (TextView)findViewById(R.id.textView41);
        KodPocztowy = (TextView)findViewById(R.id.textView42);
        Miasto = (TextView)findViewById(R.id.textView43);
        Telefon = (TextView)findViewById(R.id.textView44);
        Email = (TextView)findViewById(R.id.textView45);
        SposobPlatnosci = (TextView)findViewById(R.id.textView46);

        DeleteButton = (Button)findViewById(R.id.buttonUsunZamowienie);

        TempItem = getIntent().getStringExtra("ListViewValue");

        HttpWebCall(TempItem);

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               OrderDelete(TempItem);

            }
        });

    }

    public void OrderDelete(final String StudentID) {

        class OrderDeleteClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(ShowSingleOrdersActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(ShowSingleOrdersActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                // Sending STUDENT id.
                hashMap.put("ProduktID", params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpUrlDeleteRecord);

                return finalResult;
            }
        }

        OrderDeleteClass orderDeleteClass = new OrderDeleteClass();
        orderDeleteClass.execute(StudentID);
    }

    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(ShowSingleOrdersActivity.this,"Wczytuję",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                FinalJSonObject = httpResponseMsg ;

                new ShowSingleOrdersActivity.GetHttpResponse(ShowSingleOrdersActivity.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("ProduktID",params[0]);
                System.out.println(ResultHash);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
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
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            // Storing Student Name, Phone Number, Class into Variables.
                            ProduktHolder = jsonObject.getString("Produkt").toString() ;
                            CenaHolder= jsonObject.getString("Cena").toString() ;
                            OsobaKontaktowaHolder = jsonObject.getString("OsobaKontaktowa").toString() ;
                            AdresHolder= jsonObject.getString("Adres").toString() ;
                            KodPocztowyHolder= jsonObject.getString("KodPocztowy").toString() ;
                            MiastoHolder= jsonObject.getString("Miasto").toString() ;
                            TelefonHolder= jsonObject.getString("Telefon").toString() ;
                            EmailHolder= jsonObject.getString("Email").toString() ;
                            SposobPlatnosciHolder= jsonObject.getString("SposobPlatnosci").toString() ;


                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

            Produkt.setText(ProduktHolder);
            Cena.setText(CenaHolder);
            OsobaKontaktowa.setText(OsobaKontaktowaHolder);
            Adres.setText(AdresHolder);
            KodPocztowy.setText(KodPocztowyHolder);
            Miasto.setText(MiastoHolder);
            Telefon.setText(TelefonHolder);
            Email.setText(EmailHolder);
            SposobPlatnosci.setText(SposobPlatnosciHolder);


        }
    }

}