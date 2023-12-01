package com.example.appreadingcomic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadingcomic.database.DatabaseHelper;

public class ResetPassActivity extends AppCompatActivity {
    Button btnSubmit;
    Button btnHome;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        btnSubmit = findViewById(R.id.resetPass);
        btnHome = findViewById(R.id.homeLogin);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ EditText
                EditText userNameEditText = findViewById(R.id.UserName);
                EditText emailEditText = findViewById(R.id.Email);
                EditText newPassEditText = findViewById(R.id.newPass);
                String username = userNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String newPassword = newPassEditText.getText().toString();

                // Kiểm tra tài khoản và email trong database
                databaseHelper = new DatabaseHelper(ResetPassActivity.this);
                boolean credentialsValid = databaseHelper.checkCredentials(username, email);

                if (credentialsValid) {
                    // Cập nhật mật khẩu mới trong database
                    boolean passwordUpdated = databaseHelper.updatePassword(username, newPassword);
                    if (passwordUpdated) {
                        // Thực hiện hành động sau khi cập nhật mật khẩu thành công
                        Toast.makeText(getApplicationContext(), "Mật khẩu đã được cập nhật", Toast.LENGTH_SHORT).show();
                        // Có thể chuyển đến activity khác ở đây nếu cần
                    } else {
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tài khoản hoặc email không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassActivity.this, MainLogin.class);
                startActivity(intent);
            }
        });
    }
}
