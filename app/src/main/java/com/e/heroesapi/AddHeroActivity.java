package com.e.heroesapi;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import heroesapi.HeroesAPI;
import model.Heroes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import url.Url;

public class AddHeroActivity extends AppCompatActivity {

    private EditText etName, etDesc;
    private Button btnSave;
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hero);

        etName=findViewById(R.id.etName);
        etDesc=findViewById(R.id.etDesc);
        btnSave=findViewById(R.id.btnSave);
        imgPhoto=findViewById(R.id.impPhoto);
        loadFormUrl();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }

            private void Save() {
                String name=etName.getText().toString();
                String desc=etDesc.getText().toString();

                Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)


                        .addConverterFactory(GsonConverterFactory.create()).build();

                HeroesAPI heroesAPI= retrofit.create(HeroesAPI.class);

                Call <Void> heroesCall = heroesAPI.addHero(name,desc);

                heroesCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(AddHeroActivity.this, "Code", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        Toast.makeText(AddHeroActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddHeroActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });
}

    private void StrictMode(){
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }
    private void loadFormUrl() {
        StrictMode();
        try{
            String imgUrl ="https://cdn.shopify.com/s/files/1/1803/0853/products/Gypsophila_Xlence_1400x.jpg?v=1533680754";
            URL url= new URL(imgUrl);
            imgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        }
        catch (IOException e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

    }
}
