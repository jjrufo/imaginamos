package imaginamos.puebra.prueba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import imaginamos.prueba.beans.Data2;
import imaginamos.prueba.load.DowloadAndLoad;
import imaginamos.prueba.view.ListAdapter;

public class MainActivity extends Activity implements DowloadAndLoad.download_complete {

    private String URL = "https://www.reddit.com/reddits.json";
    private Button iniciar;

    private ListView listChild;

    private ArrayList<Data2> lista;

    private ListAdapter adapter;

    private Context context;

    private DowloadAndLoad download_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = new ArrayList<Data2>();

        context = getApplicationContext();

        listChild = (ListView) findViewById(R.id.listChild);
        iniciar = (Button) findViewById(R.id.iniciar);

        adapter = new ListAdapter(this);
        listChild.setAdapter(adapter);

        download_data = new DowloadAndLoad((DowloadAndLoad.download_complete) this);

        iniciar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                download_data.download_data_from_link(URL);

            }
        });

        listChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android
                int posicion = position;

                //obtengo el valor del string del elemento donde se hizo clic
                // Data2 itemValue = (Data2) listChild.getItemAtPosition(position);

                Data2 itemValue = lista.get(0);

                //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención
                Intent intent = new Intent(getApplicationContext(),DetalleActivity.class);

                Bundle detalle = new Bundle();
                detalle.putString("title", itemValue.getTitle());
                detalle.putString("display_name", itemValue.getDisplayName());
                detalle.putString("header_title", itemValue.getHeaderTitle());
                detalle.putString("name", itemValue.getName());
                detalle.putString("url", itemValue.getUrl());

                // Poner el Id de la imagen como extra en la intención
                // intent.putExtra("logo",LogoId[position]);

                intent.putExtras(detalle);
                // Aquí pasaremos el parámetro de la intención creada previamente
                startActivity(intent);
            }
        });

    }

    public void get_data(String data) {
        try {
            JSONObject data_object = new JSONObject(data);

            String dataJson = data_object.getString("data");

            JSONObject data_object_data = new JSONObject(dataJson);

            String array = data_object_data.getString("children");

            JSONArray data_array=new JSONArray(array);
            for (int i = 0 ; i < data_array.length() ; i++)
            {
                JSONObject obj=new JSONObject(data_array.get(i).toString());

                String data2 = obj.getString("data");

                JSONObject objData2=new JSONObject(data2);

                Data2 add = new Data2();

                add.setTitle(objData2.getString("title"));
                add.setDisplayName(objData2.getString("display_name"));
                add.setHeaderTitle(objData2.getString("header_title"));
                add.setName(objData2.getString("name"));
                add.setUrl(objData2.getString("url"));

                lista.add(add);
            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Button getIniciar() {
        return iniciar;
    }

    public void setIniciar(Button iniciar) {
        this.iniciar = iniciar;
    }

    public ListView getListChild() {
        return listChild;
    }

    public void setListChild(ListView listChild) {
        this.listChild = listChild;
    }

    public ArrayList<Data2> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Data2> lista) {
        this.lista = lista;
    }

    public ListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DowloadAndLoad getDownload_data() {
        return download_data;
    }

    public void setDownload_data(DowloadAndLoad download_data) {
        this.download_data = download_data;
    }
}
