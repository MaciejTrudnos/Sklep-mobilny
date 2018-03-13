package maciejtrudnos.sklep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import android.os.AsyncTask;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

public class ShowAllOrdersActivity extends AppCompatActivity {

    ListView OrderListView;
    ProgressBar progressBar;
    String HttpUrl = "https://projektsklep.000webhostapp.com/WszystkieZamowienia.php";
    List<String> IdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_orders);

        OrderListView = (ListView)findViewById(R.id.listview1);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        new GetHttpResponse(ShowAllOrdersActivity.this).execute();

        OrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(ShowAllOrdersActivity.this,ShowSingleOrdersActivity.class);

                intent.putExtra("ListViewValue", IdList.get(position).toString());

                startActivity(intent);

            }
        });
    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String JSonResult;

        List<Product> productList;

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

            HttpServicesClass httpServicesClass = new HttpServicesClass(HttpUrl);
            try
            {
                httpServicesClass.ExecutePostRequest();

                if(httpServicesClass.getResponseCode() == 200)
                {
                    JSonResult = httpServicesClass.getResponse();

                    if(JSonResult != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(JSonResult);

                            JSONObject jsonObject;

                            Product order;

                            productList = new ArrayList<Product>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                order = new Product();

                                jsonObject = jsonArray.getJSONObject(i);

                                IdList.add(jsonObject.getString("ID").toString());

                                order.ProduktName = jsonObject.getString("OsobaKontaktowa").toString();

                                productList.add(order);

                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
            progressBar.setVisibility(View.GONE);

            OrderListView.setVisibility(View.VISIBLE);

            ListAdapterClass adapter = new ListAdapterClass(productList, context);

            OrderListView.setAdapter(adapter);

        }
    }
}
