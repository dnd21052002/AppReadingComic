package com.example.appreadingcomic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadingcomic.database.DatabaseHelper;
import com.example.appreadingcomic.object.Comic;
import com.example.appreadingcomic.retrofit.ApiServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainDangBai extends AppCompatActivity {
    EditText edtNameComic,edtCotent,edtLinkComic;
    Button btnDangBai;

    Button btnQuayLai;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_addcomic);

        edtNameComic = findViewById(R.id.nameComic);
        edtCotent = findViewById(R.id.contentComic);
        btnDangBai = findViewById(R.id.dbdangbai);
        btnQuayLai = findViewById(R.id.btnBack);
        edtLinkComic = findViewById(R.id.linkComic);

        databaseHelper = new DatabaseHelper(this);

        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameComic = edtNameComic.getText().toString();
                String contentComic = edtCotent.getText().toString();
                String linkComic = edtLinkComic.getText().toString();

                Comic comic = CreateComic();

                if(nameComic.equals("") || contentComic.equals("") || linkComic.equals("")){
                    Toast.makeText(MainDangBai.this,"Yêu cầu nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseHelper.AddTruyen(comic);
                    Intent intent = new Intent(MainDangBai.this, MainAdmin.class);
                    startActivity(intent);
                    Toast.makeText(MainDangBai.this,"Thêm truyện thành công",Toast.LENGTH_SHORT).show();
                    Log.e("Thêm truyện : ","Thành công");
                }
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private Comic CreateComic(){
        String tentruyen = edtNameComic.getText().toString();
        String noidung = edtCotent.getText().toString();
        String img = edtLinkComic.getText().toString();

        Intent intent = getIntent();
        int id = intent.getIntExtra("Id",0);

        Comic comic = new Comic(tentruyen,img,noidung,id);
        return comic;
    }
}
