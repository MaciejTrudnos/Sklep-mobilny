package maciejtrudnos.sklep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuPracownika extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pracownika);
    }

    public void buttonDodajProdukt (View view) {
        Intent intent = new Intent(this, NowyProdukt.class);
        startActivity(intent);

    }

    public void buttonLista (View view) {
        Intent intent = new Intent(this, ShowAllProductActivity.class);
        startActivity(intent);

    }

    public void buttonAktualnosci (View view) {
        Intent intent = new Intent(this, UpdateAktualnosci.class);
        startActivity(intent);

    }


    public void buttonZamowienia (View view) {
        Intent intent = new Intent(this, ShowAllOrdersActivity.class);
        startActivity(intent);

    }
}
