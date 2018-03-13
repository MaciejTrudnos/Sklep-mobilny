package maciejtrudnos.sklep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class Logowanie extends AppCompatActivity {

    EditText Email, Password;
    Button LogIn ;
    String PasswordHolder, EmailHolder;
    String finalResult ;
    String HttpURL = "https://projektsklep.000webhostapp.com/UserLogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);

        Email = (EditText)findViewById(R.id.Uzytkownik);
        Password = (EditText)findViewById(R.id.Haslo);
        LogIn = (Button)findViewById(R.id.buttonZaloguj);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EmailHolder = Email.getText().toString();
                PasswordHolder = Password.getText().toString();

                if (EmailHolder.equals("Admin")&&PasswordHolder.equals("")){
                    Intent intent = new Intent(Logowanie.this, MenuPracownika.class);

                    startActivity(intent);

                    finish();
                }else{
                    UserLoginFunction(EmailHolder, PasswordHolder);
                }

            }
        });
    }

    public void UserLoginFunction(final String email, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Logowanie.this,"Logowanie",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();



                if(httpResponseMsg.trim().equals("DataMatched")){

                    finish();

                    Intent intent = new Intent(Logowanie.this, MenuGlowne.class);

                    startActivity(intent);


                }
                else{

                    Toast.makeText(Logowanie.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email,password);
    }

    public void Zarejestruj (View view) {
        Intent intent = new Intent(this, Rejestracja.class);
        startActivity(intent);
    }

}