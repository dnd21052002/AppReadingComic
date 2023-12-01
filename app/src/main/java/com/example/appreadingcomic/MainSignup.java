package com.example.appreadingcomic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadingcomic.database.DatabaseHelper;
import com.example.appreadingcomic.object.Account;

public class MainSignup extends AppCompatActivity {
    EditText edtDKUserName,edtDKPassword,edtDKEmail;
    Button btnDKSignup,btnDKLogin;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        AnhXa();

        databaseHelper = new DatabaseHelper(this);

        btnDKSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String taikhoan = edtDKUserName.getText().toString();
                String matkhau = edtDKPassword.getText().toString();
                String email = edtDKEmail.getText().toString();

                Account taikhoan1 = CreateTaiKhoan();
                if(taikhoan.equals("") || matkhau.equals("") || email.equals("")){
                    Toast.makeText(MainSignup.this,"Bạn chưa nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    Log.e("Thông báo : ","Bạn chưa nhập đầy đủ thông tin");
                }
                //Nếu đầy đủ thông tin
                else{
                    //Kiểm tra xem trùng tài khoản không để có thể hiển thị thông báo tài khoản trùng

                    databaseHelper.AddTaiKhoan(taikhoan1);
                    Toast.makeText(MainSignup.this,"Đăng ký thành công ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDKLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private Account CreateTaiKhoan(){
        String taikhoan = edtDKUserName.getText().toString();
        String matkhau = edtDKPassword.getText().toString();
        String email = edtDKEmail.getText().toString();
        int phanquyen = 1;

        Account tk = new Account(taikhoan,matkhau,email,phanquyen);

        return tk;
    }
    private void AnhXa() {
        edtDKEmail = findViewById(R.id.dkEmail);
        edtDKPassword = findViewById(R.id.dkPassword);
        edtDKUserName = findViewById(R.id.dkUserName);
        btnDKSignup = findViewById(R.id.dkSignup);
        btnDKLogin = findViewById(R.id.dkLogin);

    }
}
