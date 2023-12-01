package com.example.appreadingcomic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appreadingcomic.adapter.ComicAdapter;
import com.example.appreadingcomic.database.DatabaseHelper;
import com.example.appreadingcomic.object.Comic;
import com.example.appreadingcomic.retrofit.ApiServer;
import com.example.appreadingcomic.retrofit.RetrofitClient;
import com.example.appreadingcomic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    GridView gdvListComic;
    ComicAdapter adapter;
    ArrayList<Comic> comicArrayList;
    EditText edSearch;
    DatabaseHelper databaseHelper;
    ImageView btnUser;
    String loggedInUsername; // Biến lưu trữ tên người dùng đã đăng nhập
    ApiServer apiServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiServer = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiServer.class);

        anhXa();
        setUp();
        setClick();

        if(!isConnected(this)){
            Toast.makeText(getApplicationContext(), "khong co internet", Toast.LENGTH_LONG).show();
        }

        databaseHelper = new DatabaseHelper(this);

        // hiển thị
        comicArrayList = new ArrayList<>();
        Cursor dataComic = databaseHelper.getData2();
        while (dataComic.moveToNext()) {
            Integer id = dataComic.getInt(0);
            String nameComic = dataComic.getString(1);
//            String nameChap = dataComic.getString(2);
            String linkComic = dataComic.getString(3);
            String content = dataComic.getString(2);

            Comic comic = new Comic(id, nameComic, linkComic, content);
            comicArrayList.add(comic);
        }
        adapter = new ComicAdapter(this,  comicArrayList);
        gdvListComic.setAdapter(adapter);

        // Nhận giá trị loggedInUsername từ Intent (nếu có)
        Intent intent = getIntent();
        if (intent != null) {
            loggedInUsername = intent.getStringExtra("loggedInUsername");
        }
    }
    private void anhXa() {
        gdvListComic = findViewById(R.id.gdvListComic);
        edSearch = findViewById(R.id.edSearch);
        btnUser = findViewById(R.id.btnUser);
    }

    private void setUp() {
        comicArrayList = new ArrayList<>();
        adapter = new ComicAdapter(this,  comicArrayList);
    }

    private void setClick() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = edSearch.getText().toString();
                adapter.sortComic(s);
            }
        });

        gdvListComic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ComicDetailActivity.class);
                String nameComic = comicArrayList.get(position).getNameComic();
                String content = comicArrayList.get(position).getContent();
                String linkComic = comicArrayList.get(position).getLinkComic();
                intent.putExtra("nameComic", nameComic);
                intent.putExtra("content", content);
                intent.putExtra("linkComic", linkComic);
                //Log.e("Tên truyện : ",tent);
                startActivity(intent);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.show();
                // Xử lý sự kiện khi chọn một item trong menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menu_option1){
                            String userName = getIntent().getStringExtra("loggedInUsername");
                            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                            intent.putExtra("userNameInfo", userName);
                            startActivity(intent);
                        }
                        if (item.getItemId() == R.id.menu_option2) {
                            Intent intent = new Intent(MainActivity.this, MainLogin.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                });


            }
        });
    }

    private void getComicListFromServer() {
        Call<List<Comic>> call = apiServer.getComic();
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful()) {
                    List<Comic> comicList = response.body();
                    // Xử lý danh sách comic nhận được từ server
                    if (comicList != null) {
                        comicArrayList.clear();
                        for (Comic comic : comicList) {
                            int id = comic.getID();
                            String nameComic = comic.getNameComic();
                            String contentComic = comic.getContent();
                            String linkComic = comic.getLinkComic();

                            // Create a new Comic object with the extracted data
                            Comic updatedComic = new Comic(id, nameComic, contentComic, linkComic);

                            // Add the updatedComic to the comicArrayList
                            comicArrayList.add(updatedComic);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    // Xử lý lỗi
                    Toast.makeText(MainActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                // Xử lý lỗi kết nối
                Toast.makeText(MainActivity.this, "Failed to fetch comic data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void displayComicContent(Comic comic) {
        // Ở đây, bạn có thể thực hiện các hành động như chuyển sang màn hình mới để hiển thị nội dung của truyện,
        // hoặc hiển thị một Dialog chứa nội dung truyện, tuỳ thuộc vào cách bạn xây dựng ứng dụng của mình.
        String nameComic = comic.getNameComic();
        String linkComic = comic.getLinkComic();
        // Ví dụ: Chuyển sang màn hình mới và truyền dữ liệu của truyện
        Intent intent = new Intent(MainActivity.this, ComicDetailActivity.class);
        intent.putExtra("nameComic", nameComic); // Truyền tên comic
        intent.putExtra("linkComic", linkComic); // Truyền URL hình ảnh comic (nếu cần)
        startActivity(intent);

    }
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo((ConnectivityManager.TYPE_WIFI));
        NetworkInfo mobile = connectivityManager.getNetworkInfo((ConnectivityManager.TYPE_MOBILE));
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }
        else {
            return false;
        }
    }

}

