package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView tvFullname, tvEmail, tvHomepage, tvAbout, btnHomepage;
    CircleImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvFullname = findViewById(R.id.label_fullname);
        tvEmail = findViewById(R.id.label_email);
        tvHomepage = findViewById(R.id.label_homepage);
        tvAbout = findViewById(R.id.label_about);
        btnHomepage = findViewById(R.id.button_homepage);
        avatarImage = findViewById(R.id.image_profile);

        String fullname = getIntent().getExtras().getString("fullname");
        String email = getIntent().getExtras().getString("email");
        String homepage = getIntent().getExtras().getString("homepage");
        String about = getIntent().getExtras().getString("about");

        String imageString = getIntent().getExtras().getString("image");
        if(imageString!=null){
            Uri imageUri = Uri.parse(imageString);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                avatarImage.setImageBitmap(bitmap);
            }catch (IOException e){
                Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                Log.e(this.getClass().getCanonicalName(), e.getMessage());
            }
        }

        tvFullname.setText(fullname);
        tvEmail.setText(email);
        tvHomepage.setText(homepage);
        tvAbout.setText(about);

        btnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(getIntent().getExtras().getString("homepage"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }
}
