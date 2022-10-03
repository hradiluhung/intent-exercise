package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etFullname, etEmail, etPassword, etConfirmPassword, etHomepage, etAbout;
    private Button btnSubmit;
    private CircleImageView avatarImage;
    private ImageView btnEditImage;

    Uri imageUri = null;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullname = findViewById(R.id.text_fullname);
        etEmail = findViewById(R.id.text_email);
        etPassword = findViewById(R.id.text_password);
        etConfirmPassword = findViewById(R.id.text_confirm_password);
        etHomepage = findViewById(R.id.text_homepage);
        etAbout = findViewById(R.id.text_about);
        btnSubmit = findViewById(R.id.button_ok);
        avatarImage = findViewById(R.id.image_profile);
        btnEditImage = findViewById(R.id.imageView);

        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                intent.putExtra("fullname", etFullname.getText().toString());
                intent.putExtra("email", etEmail.getText().toString());
                intent.putExtra("password", etPassword.getText().toString());
                intent.putExtra("confirmPassword", etConfirmPassword.getText().toString());
                intent.putExtra("homepage", etHomepage.getText().toString());
                intent.putExtra("about", etAbout.getText().toString());

                String uriString = null;
                if(imageUri != null){
                    uriString = imageUri.toString();
                }

                intent.putExtra("image", uriString);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancel input image", Toast.LENGTH_SHORT).show();
            return;
        }else if (requestCode == GALLERY_REQUEST_CODE){
            if(data != null){
                try {
                    imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
