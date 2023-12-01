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

public class MainSuaBai extends AppCompatActivity {
    EditText edtNameComic,edtCotent,edtLinkComic;
    Button btnUpdate, btnBack;

    DatabaseHelper databaseHelper;
    private Comic selectedComic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_editcomic);

        edtNameComic = findViewById(R.id.nameComicEdit);
        edtCotent = findViewById(R.id.contentComicEdit);
        btnUpdate = findViewById(R.id.update);
        btnBack = findViewById(R.id.btnBack);
        edtLinkComic = findViewById(R.id.linkComicEdit);

        databaseHelper = new DatabaseHelper(this);

        // Lấy thông tin truyện được chọn từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("comic")) {
            selectedComic = intent.getParcelableExtra("comic");
        }

        // Hiển thị thông tin truyện trong các trường nhập liệu
        if (selectedComic != null) {
            edtNameComic.setText(selectedComic.getNameComic());
            edtCotent.setText(selectedComic.getContent());
            edtLinkComic.setText(selectedComic.getLinkComic());
        }

        // Xử lý sự kiện khi nhấn nút Lưu
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các trường nhập liệu
                String nameComic = edtNameComic.getText().toString();
                String content = edtCotent.getText().toString();
                String linkComic = edtLinkComic.getText().toString();

                // Cập nhật thông tin truyện trong cơ sở dữ liệu
                if (selectedComic != null) {
                    selectedComic.setNameComic(nameComic);
                    selectedComic.setContent(content);
                    selectedComic.setLinkComic(linkComic);
                    databaseHelper.UpdateTruyen(selectedComic.getID(), selectedComic);
                    onBackPressed();
                    Toast.makeText(MainSuaBai.this,"Sửa truyện thành công",Toast.LENGTH_SHORT).show();
                    Log.e("Sửa truyện : ","Thành công");
//                    Toast.makeText(MainSuaBai.this, "Đã lưu thông tin truyện", Toast.LENGTH_SHORT).show();
                }
//                finish(); // Kết thúc Activity sau khi lưu thông tin truyện
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}