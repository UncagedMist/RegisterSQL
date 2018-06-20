package kk.techbytecare.registersql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kk.techbytecare.registersql.Database.Database;
import kk.techbytecare.registersql.Model.User;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName, edtPhone, edtEmail, edtPassword, edtCnfPassword;
    Button btnRegister;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = new Database(this);

        btnRegister = findViewById(R.id.btn_register);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtCnfPassword = findViewById(R.id.edtCnfPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtCnfPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name can't be empty...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone can't be empty...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email can't be empty...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password can't be empty...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "confirmPassword can't be empty...", Toast.LENGTH_SHORT).show();
        }
        else    {

            Boolean checkUser = database.checkUserExists(phone);
            Boolean registerUser = database.registerUser(name,phone,email,password);

            if (password.equals(confirmPassword))   {

                if (checkUser) {
                    Toast.makeText(this, "Phone Already Exists...", Toast.LENGTH_SHORT).show();
                }
                else    {
                    if (registerUser)   {
                        Toast.makeText(this, "Registered Successfully..", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else    {
                        Toast.makeText(this, "Error in Registration...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else    {
                Toast.makeText(this, "Password don't match...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
