package com.example.appreadingcomic;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.appreadingcomic.adapter.ComicAdapter;
import com.example.appreadingcomic.database.DatabaseHelper;
import com.example.appreadingcomic.object.Comic;

import java.util.ArrayList;

public class MainAdmin extends AppCompatActivity {
    ListView listView;
    Button buttonAdd;

    ArrayList<Comic> comicArrayList;
    ComicAdapter comicAdapter;
    DatabaseHelper databaseHelper;
    ImageView btnUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        btnUser = findViewById(R.id.btnUserAdmin);
        listView = findViewById(R.id.listviewAdmin);
        buttonAdd = findViewById(R.id.btnAddComic);
        databaseHelper = new DatabaseHelper(this);
        String userName = getIntent().getStringExtra("loggedInUsername");
        initList();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogDelete(position);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy truyện được chọn từ danh sách truyện
                Comic selectedComic = comicArrayList.get(position);

                // Chuyển sang màn hình sửa truyện và truyền thông tin truyện qua Intent
                Intent intent = new Intent(MainAdmin.this, MainSuaBai.class);
                intent.putExtra("comic", selectedComic);
                startActivity(intent);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int id = intent.getIntExtra("Id", 0);
                Intent intent1 = new Intent(MainAdmin.this, MainDangBai.class);
                intent.putExtra("Id", id);
                startActivity(intent1);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainAdmin.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.show();
                // Xử lý sự kiện khi chọn một item trong menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menu_option1){
                            Intent intent = new Intent(MainAdmin.this, UserInfoActivity.class);
                            intent.putExtra("userNameInfo", userName);
                            startActivity(intent);
                        }
                        if (item.getItemId() == R.id.menu_option2) {
                            Intent intent = new Intent(MainAdmin.this, MainLogin.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                });
            }
        });
    }

    // Dialog Delete
    private void DialogDelete(int position) {

        // Tạo đối tượng cửa sổ dialog
        Dialog dialog = new Dialog(this);

        // Nạp layout vào
        dialog.setContentView(R.layout.dialogdelete);
        // Click No mới thoát, click ngoài không thoát
        dialog.setCanceledOnTouchOutside(false);

        // Ánh xạ
        Button btnYes = dialog.findViewById(R.id.buttonYes);
        Button btnNo = dialog.findViewById(R.id.buttonNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idtruyen = comicArrayList.get(position).getID();
                //Xóa trong SQL
                databaseHelper.Delete(idtruyen);
                //Cập nhật lại listview
                dialog.hide();
                Toast.makeText(MainAdmin.this,"Xóa truyện thành công",Toast.LENGTH_SHORT).show();
                // Cập nhật lại listview
                initList();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void initList() {
        comicArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        Cursor cursor1 = databaseHelper.getData1();

        while (cursor1.moveToNext()) {
            Integer id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);
            comicArrayList.add(new Comic(id, tentruyen, anh, noidung, id_tk));

            comicAdapter = new ComicAdapter(getApplicationContext(), comicArrayList);
            listView.setAdapter(comicAdapter);
        }
        cursor1.moveToFirst();
        cursor1.close();
    }
}
