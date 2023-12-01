package com.example.appreadingcomic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadingcomic.database.DatabaseHelper;

public class MainLogin extends AppCompatActivity {
    EditText edtUserName, edtPassword;
    Button btnLogin, btnSignup;
    Button btnResetPass;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();

        databaseHelper = new DatabaseHelper(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLogin.this, MainSignup.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentaikhoan = edtUserName.getText().toString();
                String matkhau = edtPassword.getText().toString();
                Cursor cursor = databaseHelper.getData();
                while (cursor.moveToNext()) {
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);
                    int phanquyen = cursor.getInt(4);
                    int idd = cursor.getInt(0);
                    String tentk = cursor.getString(1);
                    String email = cursor.getString(3);

                    if (datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)) {

                        if (phanquyen == 2) {
                            Intent intent = new Intent(MainLogin.this, MainAdmin.class);
                            intent.putExtra("idd", idd);
                            intent.putExtra("email", email);
                            intent.putExtra("tentaikhoan", tentk);
                            // Truyền thông tin người dùng
                            String loggedInUsername = edtUserName.getText().toString().trim();
                            intent.putExtra("loggedInUsername", loggedInUsername);
                            startActivity(intent);
                            Log.e("Đăng nhập: ", "Thành công (Admin)");
                        } else if (phanquyen == 1) {
                            Intent intent = new Intent(MainLogin.this, MainActivity.class);
                            intent.putExtra("idd", idd);
                            intent.putExtra("email", email);
                            intent.putExtra("tentaikhoan", tentk);
                            String loggedInUsername = edtUserName.getText().toString().trim();
                            intent.putExtra("loggedInUsername", loggedInUsername);
                            startActivity(intent);
                            Log.e("Đăng nhập: ", "Thành công");
                        }
                        return;
                    }
                }

                // Nếu không tìm thấy tài khoản đăng nhập
                Log.e("Đăng nhập: ", "Không thành công");
                cursor.close();
            }
        });
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLogin.this, ResetPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        edtUserName = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnSignup = findViewById(R.id.signup);
        btnResetPass = findViewById(R.id.QuenMK);
    }
}
