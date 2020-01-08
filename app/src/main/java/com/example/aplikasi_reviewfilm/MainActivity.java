package com.example.aplikasi_reviewfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasi_reviewfilm.adapter.AdapterListSimple;
import com.example.aplikasi_reviewfilm.movieshow.ShowModel;
import com.example.aplikasi_reviewfilm.service.APIClient;
import com.example.aplikasi_reviewfilm.service.APIInterfacesRest;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView lst_Movie;
    TextView txt_Cari;
    ImageButton btn_ImgCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst_Movie = findViewById(R.id.lst_Movie);
        txt_Cari = findViewById(R.id.txt_Cari);
        btn_ImgCari = findViewById(R.id.btn_ImgCari);


        btn_ImgCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Add_Movie.class);
                startActivity(intent);
            }
        });


        callMovielst();

    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void callMovielst(){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<ShowModel> call3 = apiInterface.getMovie();
        call3.enqueue(new Callback<ShowModel>() {
            @Override
            public void onResponse(Call<ShowModel> call, Response<ShowModel> response) {
                progressDialog.dismiss();
                ShowModel data = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (data !=null) {

                    AdapterListSimple adapter = new AdapterListSimple(MainActivity.this,data.getData().getMoviedb());

                    lst_Movie.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    lst_Movie.setItemAnimator(new DefaultItemAnimator());
                    lst_Movie.setAdapter(adapter);

                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShowModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });




    }
}
