package com.example.aplikasi_reviewfilm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasi_reviewfilm.AddMovie.MovieModel;
import com.example.aplikasi_reviewfilm.adapter.AdapterListSimple;
import com.example.aplikasi_reviewfilm.movieshow.ShowModel;
import com.example.aplikasi_reviewfilm.service.APIClient;
import com.example.aplikasi_reviewfilm.service.APIInterfacesRest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__movie);

        img_btn1 = findViewById(R.id.img_btn1);
        img_btn2 = findViewById(R.id.img_btn2);
        img_btn3 = findViewById(R.id.img_btn3);
        button_Send = findViewById(R.id.button_Send);

        txt_Judul = findViewById(R.id.txt_Judul);
        txt_Directby = findViewById(R.id.txt_Directby);
        txt_Writenby = findViewById(R.id.txt_Writenby);
        txt_Studio =findViewById(R.id.txt_Studio);

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
   // private int CAMERA_REQUEST2 = 200;
   // private int CAMERA_REQUEST3 = 300;

    private int REQUEST_GALLERY = 100;
    private int REQUEST_GALLERY2 = 200;
    private int REQUEST_GALLERY3 = 300;

  /*  void openCamera() {

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
    byte[] byteArray;

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
            // img_btn1.setImageBitmap(bitmap);

    }else  if (requestCode == REQUEST_GALLERY3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            img_btn3.setImageURI(selectedImage);
            Bitmap bitmap = ((BitmapDrawable) img_btn3.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byteArray = baos.toByteArray();
            // img_btn1.setImageBitmap(bitmap);

        }
    }
}
