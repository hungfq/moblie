package hcmute.edu.vn.mssv18110276;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 1337;
    ImageButton ibtn_camera;
    TextView tv_login;
    ImageView iv_avatar;
    EditText et_name, et_email, et_phone, et_password, et_confirmpassword;
    Button btn_register;
    DatabaseHandler db;
    // Khai báo
    String emailInput;
    String nameInput;
    String phoneInput;
    String passwordInput;
    String passwordConfirm;
    byte[] source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHandler(this);
        Mapping();

        ibtn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent_camera, CAMERA_PIC_REQUEST);
            }
        });


        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                // send data
                intent.putExtra(MainActivity.KEY_USER_FROM_REGISTER, "");

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidate()){
                    ImageViewToByteArray();
                    Log.d("Dang ky",phoneInput + " " + emailInput + " " + source);
                    db.registerUser(new User(nameInput,emailInput,phoneInput,passwordInput,1,1,2,source));
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công! Vui lòng đăng nhập để vào ứng dụng.",Toast.LENGTH_LONG).show();
                    // create intent to send data back Login Activity
                    Intent intent = new Intent();

                    // send data
                    intent.putExtra(MainActivity.KEY_USER_FROM_REGISTER, emailInput);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void Mapping(){
        ibtn_camera = (ImageButton)findViewById(R.id.ibtn_camera);
        tv_login = (TextView)findViewById(R.id.tv_login);
        iv_avatar = (ImageView)findViewById(R.id.iv_avatar);
        et_name = (EditText)findViewById(R.id.et_name);
        et_email = (EditText)findViewById(R.id.et_email);
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_password = (EditText)findViewById(R.id.et_password);
        et_confirmpassword = (EditText)findViewById(R.id.et_confirmpassword);
        btn_register = (Button)findViewById(R.id.btn_register);
    }

    private boolean checkValidate(){
        if(validateName() == true && validateEmail() == true && validatePhone() == true && validatePassword() == true && validatePasswordConfirm() == true){
            return true;
        }
        return false;
    }

    private boolean validateEmail() {
        emailInput = et_email.getText().toString().trim();

        if (db.checkIfEmailExists(emailInput)) {
            et_email.setError("Email already exist");
            return false;
        } else if (emailInput.isEmpty()) {
            et_email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            et_email.setError("Please enter a valid email address");
            return false;
        } else {
            et_email.setError(null);
            return true;
        }
    }

    private boolean validateName() {
        nameInput = et_name.getText().toString().trim();

        if (db.checkIfNameExists(nameInput)) {
            et_name.setError("Name already exist");
            return false;
        } else if (nameInput.isEmpty()) {
            et_name.setError("Field can't be empty");
            return false;
        } else {
            et_name.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        phoneInput = et_phone.getText().toString().trim();

        if (db.checkIfPhoneExists(phoneInput)) {
            et_phone.setError("Phone already exist");
            return false;
        } else if (phoneInput.isEmpty()) {
            et_phone.setError("Field can't be empty");
            return false;
        } else if (!Patterns.PHONE.matcher(phoneInput).matches()) {
            et_phone.setError("Please enter a valid phone");
            return false;
        } else {
            et_phone.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        passwordInput = et_password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            et_password.setError("Field can't be empty");
            return false;
        } else if(passwordInput.length() < 8){
            et_password.setError("Password must contain minimum 8 characters");
            return false;
        }
        else {
            et_password.setError(null);
            return true;
        }
    }

    private boolean validatePasswordConfirm() {
        passwordConfirm = et_confirmpassword.getText().toString().trim();
        if (passwordConfirm.isEmpty()) {
            et_confirmpassword.setError("Field can't be empty");
            return false;
        } else if(!passwordConfirm.equals(et_password.getText().toString().trim())){
            et_confirmpassword.setError("Password and confirm password does not match");
            return false;
        }
        else {
            et_confirmpassword.setError(null);
            return true;
        }
    }

    private void ImageViewToByteArray(){
        // Lưu hình dạng byte[]
        Bitmap image = ((BitmapDrawable) iv_avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        source = baos.toByteArray();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView iv_avatar = (ImageView) findViewById(R.id.iv_avatar); //sets imageview as the bitmap
            iv_avatar.setImageBitmap(image);
        }
    }
}