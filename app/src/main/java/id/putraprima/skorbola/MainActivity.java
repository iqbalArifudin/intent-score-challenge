package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {
    public static final String HOME_KEY = "home";
    public static final String AWAY_KEY = "away";
    public static final String HOMEIMG_KEY="homeURL";
    public static final String AWAYIMG_KEY="awayURL";
    private static final int HOME_REQUEST_CODE = 1;
    private static final int AWAY_REQUEST_CODE = 2;

    @NotEmpty
    private EditText homeInput;
    @NotEmpty
    private EditText awayInput;

    private ImageView homeImage;
    private ImageView awayImage;
    private String homeURL;
    private String awayURL;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Fitur Main Activity
        //1. Validasi Input Home Team
        homeInput=findViewById(R.id.home_team);
        validator=new Validator(this);
        validator.setValidationListener(this);
        //2. Validasi Input Away Team
        awayInput=findViewById(R.id.away_team);
        //3. Ganti Logo Home Team
        homeImage=findViewById(R.id.home_logo);
        //4. Ganti Logo Away Team
        awayImage=findViewById(R.id.away_logo);
        //5. Next Button Pindah Ke MatchActivity
    }

    public void handleNext(View view) { validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        String hometeam=homeInput.getText().toString();
        String awayteam=awayInput.getText().toString();
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra(HOME_KEY, hometeam);
        intent.putExtra(AWAY_KEY, awayteam);
        intent.putExtra(HOMEIMG_KEY,homeURL);
        intent.putExtra(AWAYIMG_KEY,awayURL);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


    public void handleLogo1(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, HOME_REQUEST_CODE);
    }

    public void handleLogo2(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AWAY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == HOME_REQUEST_CODE) {
            if (data != null) {
                try {
                    homeURL = data.getDataString();
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    homeImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                }
            }
        }

        else if (requestCode == AWAY_REQUEST_CODE) {
            if (data != null) {
                try {
                    awayURL = data.getDataString();
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    awayImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
