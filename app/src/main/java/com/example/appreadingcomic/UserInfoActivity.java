package com.example.appreadingcomic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadingcomic.database.DatabaseHelper;
import com.example.appreadingcomic.object.Account;

public class UserInfoActivity extends AppCompatActivity {
    TextView edtName, edtEmail, edtPhanQuyen;
    String loggedInUsername;
    private Bundle savedInstanceState;
    DatabaseHelper databaseHelper;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhanQuyen = findViewById(R.id.edtPhanQuyen);
        btnReturn = findViewById(R.id.btnReturn);
        // Nhận giá trị loggedInUsername từ Intent (nếu có)
        Intent intent = getIntent();
        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);
        if (intent != null) {
            loggedInUsername = intent.getStringExtra("userNameInfo");
        }
        Account user = databaseHelper.getUserInfo(loggedInUsername);
        // Xử lý logic khi chọn option 1
        if (loggedInUsername != null) {

            if (user != null) {
                // Hiển thị thông tin người dùng trên giao diện
                String tenTaiKhoan = user.getUserName();
                String email = user.getEmail();
                int phanQuyen = user.getDecentralization();

                // Xác định chuỗi phân quyền dựa trên giá trị phanQuyen
                String phanQuyenText;
                if (phanQuyen == 1) {
                    phanQuyenText = "Người dùng";
                } else if (phanQuyen == 2) {
                    phanQuyenText = "Admin";
                } else {
                    phanQuyenText = "Không xác định";
                }
                // Hiển thị thông tin trên giao diện
                // Hiển thị thông tin trên giao diện
                edtName.setText(tenTaiKhoan);
                edtEmail.setText(email);
                edtPhanQuyen.setText(phanQuyenText);
            } else {
                Toast.makeText(UserInfoActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(UserInfoActivity.this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
        }

        btnReturn.setOnClickListener(new View.OnClickListener() {
            int phanQuyen = user.getDecentralization();
            @Override
            public void onClick(View v) {
                if (phanQuyen == 1) {
                    onBackPressed();
                }
                if (phanQuyen == 2) {
                    onBackPressed();
                }
            }
        });
    }
}
