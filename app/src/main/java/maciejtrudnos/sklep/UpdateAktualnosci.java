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

public class UpdateAktualnosci extends AppCompatActivity {

    String HttpURL = "https://projektsklep.000webhostapp.com/UpdateWiadomosc.php";
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText wiadomosc;
    Button UpdateWiadomosc;
    String WiadomoscHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_aktualnosci);

        wiadomosc = (EditText)findViewById(R.id.editTextWiadomosc);
        UpdateWiadomosc = (Button)findViewById(R.id.buttonUpdateWiadomosc);


        UpdateWiadomosc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetDataFromEditText();

                WiadomoscRecordUpdate(WiadomoscHolder);

            }
        });


    }

    public void GetDataFromEditText(){

        WiadomoscHolder = wiadomosc.getText().toString();

    }

    public void WiadomoscRecordUpdate(final String wiadomosc){

        class WiadomoscRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UpdateAktualnosci.this,"Wczytuję",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(UpdateAktualnosci.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("wiadomosc",params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        WiadomoscRecordUpdateClass wiadomoscRecordUpdateClass = new WiadomoscRecordUpdateClass();
        wiadomoscRecordUpdateClass.execute(wiadomosc);
    }
}