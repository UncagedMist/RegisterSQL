package kk.techbytecare.registersql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kk.techbytecare.registersql.Database.Database;

public class LoginActivity extends AppCompatActivity {

    EditText edtPhone,edtPassword;
    Button btnLogin;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = new Database(this);

        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String phone = edtPhone.getText().toString();
        String password = edtPassword.getText().toString();


        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone can't be empty...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password can't be empty...", Toast.LENGTH_SHORT).show();
        }
        else    {
            Boolean checkUser = database.checkUserExists(phone);
            Boolean loginUser = database.loginUser(phone,password);

            if (checkUser)    {
                if (loginUser)  {
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    Toast.makeText(this, "Login Successful...", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else    {
                    Toast.makeText(this, "Login Error...", Toast.LENGTH_SHORT).show();
                }
            }
            else    {
                Toast.makeText(this, "User doesn't exists...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
