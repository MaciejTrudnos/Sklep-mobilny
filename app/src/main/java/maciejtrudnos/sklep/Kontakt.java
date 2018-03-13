package maciejtrudnos.sklep;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Kontakt extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakt);

        final EditText osoba_kontaktowa        = (EditText) findViewById(R.id.OsobaKontakt);
        final EditText adres_mail       = (EditText) findViewById(R.id.mailKontakt);
        final EditText temat     = (EditText) findViewById(R.id.tematKontakt);
        final EditText wiadomosc     = (EditText) findViewById(R.id.wiadomoscKontakt);

        Button email = (Button) findViewById(R.id.buttonKontakt);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name      = osoba_kontaktowa.getText().toString();
                String subject   = temat.getText().toString();
                String email     = adres_mail.getText().toString();
                String message   = wiadomosc.getText().toString();

                if (TextUtils.isEmpty(name)){
                    osoba_kontaktowa.setError("Uzupełnij pole");
                    osoba_kontaktowa.requestFocus();
                    return;
                }

                if  (TextUtils.isEmpty(subject)){
                    temat.setError("Uzupełnij pole");
                    temat.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    adres_mail.setError("Uzupełnij pole");
                    adres_mail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(message)){
                    wiadomosc.setError("Uzupełnij pole");
                    wiadomosc.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"maciejt93@gmail.com"});
                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Osoba kontaktowa:"+name+'\n'+"Adres e-mail:"+email+'\n'+"Wiadomość:"+'\n'+message);

                startActivity(Intent.createChooser(sendEmail, "Wysyłanie wiadomości..."));

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //Get a Tracker (should auto-report)


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void callbtn (View view) {
        String phone = "796931303";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }


}
