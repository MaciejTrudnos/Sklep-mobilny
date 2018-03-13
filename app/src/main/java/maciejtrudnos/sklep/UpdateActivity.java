package maciejtrudnos.sklep;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    String HttpURL = "https://projektsklep.000webhostapp.com/UpdateProdukty.php";
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText Product, URL, Cena;
    Button buttonUpdate;
    String IdHolder, ProductHolder, URLHolder, CenaHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Product = (EditText)findViewById(R.id.editText7);
        URL = (EditText)findViewById(R.id.editText8);
        Cena = (EditText)findViewById(R.id.editText9);

        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);

        IdHolder = getIntent().getStringExtra("id");
        ProductHolder = getIntent().getStringExtra("image_title");
        URLHolder = getIntent().getStringExtra("image_url");
        CenaHolder = getIntent().getStringExtra("Cena");

        Product.setText(ProductHolder);
        URL.setText(URLHolder);
        Cena.setText(CenaHolder);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetDataFromEditText();

                ProductRecordUpdate(IdHolder,ProductHolder,URLHolder, CenaHolder);

            }
        });


    }

    public void GetDataFromEditText(){

        ProductHolder = Product.getText().toString();
        URLHolder = URL.getText().toString();
        CenaHolder = Cena.getText().toString();

    }

    public void ProductRecordUpdate(final String ProduktID, final String image_title, final String image_url, final String Cena){

        class ProductRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UpdateActivity.this,"Wczytuję",null,true,true);

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(UpdateActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("ProduktID",params[0]);
                System.out.println(params[0]);
                hashMap.put("image_title",params[1]);
                System.out.println(params[1]);
                hashMap.put("image_url",params[2]);

                hashMap.put("Cena",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        ProductRecordUpdateClass productRecordUpdateClass = new ProductRecordUpdateClass();

        productRecordUpdateClass.execute(ProduktID,image_title,image_url,Cena);
    }
}