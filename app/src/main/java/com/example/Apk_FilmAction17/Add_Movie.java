package com.example.Apk_FilmAction17;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Apk_FilmAction17.AddMovie.MovieModel;
import com.example.Apk_FilmAction17.genre.GenreModel;
import com.example.Apk_FilmAction17.rating.RatingModel;
import com.example.Apk_FilmAction17.service.APIClient;
import com.example.Apk_FilmAction17.service.APIInterfacesRest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Movie extends AppCompatActivity {

    ImageButton img_btn1,img_btn2,img_btn3;
    EditText txt_Judul,txt_Directby,txt_Writenby,txt_Studio;
    Button button_Send;
    Spinner spn_Rating,spn_Genre;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__movie);

        //Button
        img_btn1 = findViewById(R.id.img_btn1);
        img_btn2 = findViewById(R.id.img_btn2);
        img_btn3 = findViewById(R.id.img_btn3);
        button_Send = findViewById(R.id.button_Send);

        //Edittext
        txt_Judul = findViewById(R.id.txt_Judul);
        txt_Directby = findViewById(R.id.txt_Directby);
        txt_Writenby = findViewById(R.id.txt_Writenby);
        txt_Studio =findViewById(R.id.txt_Studio);

        //Spinner
        spn_Rating = findViewById(R.id.spn_Rating);
        spn_Genre = findViewById(R.id.spn_Genre);

        img_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder1();
            }
        });

        img_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder2();
            }
        });

        img_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder3();
            }
        });


spinerRating();
spinnerGenres();

    }


    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;


    public RequestBody toRequestBody(String value) {
        if (value==null){
            value="";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public void addmovie (String judul,String rating,String genre,String directedby,String writenby,String intheater,String studio){

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"),byteArray);
        MultipartBody.Part bodyImg1 = MultipartBody.Part.createFormData("photo", "dewa.png", requestFile);

        RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"),byteArray);
        MultipartBody.Part bodyImg2 = MultipartBody.Part.createFormData("photo", "dewa.png", requestFile2);

        RequestBody requestFile3 = RequestBody.create(MediaType.parse("image/jpeg"),byteArray);
        MultipartBody.Part bodyImg3 = MultipartBody.Part.createFormData("photo", "dewa.png", requestFile3);



        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(Add_Movie.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<MovieModel> call3 = apiInterface.addmovie(toRequestBody(judul),toRequestBody(rating),toRequestBody(genre)
                ,toRequestBody(directedby),toRequestBody(writenby),toRequestBody(intheater),toRequestBody(studio),bodyImg1,bodyImg2,bodyImg3);
        call3.enqueue(new Callback<MovieModel>() {


            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                progressDialog.dismiss();
                MovieModel data = response.body();

                if (data !=null) {


                    Toast.makeText(Add_Movie.this,data.getMessage(),Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Add_Movie.this,MainActivity.class);
                    startActivity(intent);




                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Add_Movie.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Add_Movie.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });


    }


    public Uri fileUri;
   // private int CAMERA_REQUEST = 100;
    //private int CAMERA_REQUEST2 = 200;
   // private int CAMERA_REQUEST3 = 300;

    private int REQUEST_GALLERY = 100;
    private int REQUEST_GALLERY2 = 200;
    private int REQUEST_GALLERY3 = 300;
/*
   void openCamera() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    void openCamera2() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST2);

    }

    void openCamera3() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST3);

    }

*/

    public void openFolder1() {

        Intent folderIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(folderIntent, REQUEST_GALLERY);

    }


    public void openFolder2() {

        Intent folderIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(folderIntent, REQUEST_GALLERY2);

    }


    public void openFolder3() {

        Intent folderIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(folderIntent, REQUEST_GALLERY3);

    }


    Bitmap bitmap;
    byte[] byteArray,byteArray2,byteArray3;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            img_btn1.setImageURI(selectedImage);
            Bitmap bitmap = ((BitmapDrawable) img_btn1.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byteArray = baos.toByteArray();
           // img_btn1.setImageBitmap(bitmap);

        }else if (requestCode == REQUEST_GALLERY2 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            img_btn2.setImageURI(selectedImage);
            Bitmap bitmap = ((BitmapDrawable) img_btn2.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byteArray = baos.toByteArray();
            // img_btn2.setImageBitmap(bitmap);

    }else  if (requestCode == REQUEST_GALLERY3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            img_btn3.setImageURI(selectedImage);
            Bitmap bitmap = ((BitmapDrawable) img_btn3.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byteArray = baos.toByteArray();
            // img_btn3.setImageBitmap(bitmap);

        }
    }

    public void spinerRating(){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        /*progressDialog = new ProgressDialog(AddActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();*/
        Call<RatingModel> call3 = apiInterface.getRating();
        call3.enqueue(new Callback<RatingModel>() {
            @Override
            public void onResponse(Call<RatingModel> call, Response<RatingModel> response) {
                //progressDialog.dismiss();
                RatingModel data = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (data !=null) {


                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < data.getData().getRating().size(); i++){
                        listSpinner.add(data.getData().getRating().get(i).getRating());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_Movie.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_Rating.setAdapter(adapter);

                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Add_Movie.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Add_Movie.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<RatingModel> call, Throwable t) {
                //progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });}

    public void spinnerGenres(){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        /*progressDialog = new ProgressDialog(AddActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();*/
        Call<GenreModel> call3 = apiInterface.getGenre();
        call3.enqueue(new Callback<GenreModel>() {
            @Override
            public void onResponse(Call<GenreModel> call, Response<GenreModel> response) {
                // progressDialog.dismiss();
                GenreModel data = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (data !=null) {


                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < data.getData().getGenre().size(); i++){
                        listSpinner.add(data.getData().getGenre().get(i).getGenre());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_Movie.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_Genre.setAdapter(adapter);





                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Add_Movie.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Add_Movie.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GenreModel> call, Throwable t) {
                // progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });}

}
