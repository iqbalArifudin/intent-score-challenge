package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MatchActivity extends AppCompatActivity {
    private TextView homeText;
    private TextView awayText;
    private int homeScore;
    private int awayScore;
    private ImageView homeimg;
    private ImageView awayimg;
    private TextView homeTextScore;
    private TextView awayTextScore;
    private String listHomeScorer = "";
    private String listAwayScorer = "";
//    private TextView minuteText;

    private static final int HOME_SCORE_REQUEST_CODE = 1;
    private static final int AWAY_SCORE_REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);
        homeTextScore = findViewById(R.id.score_home);
        awayTextScore = findViewById(R.id.score_away);
        homeimg=findViewById(R.id.home_logo);
        awayimg=findViewById(R.id.away_logo);

//        minuteText=findViewById(R.id.textView4);
//        minuteText=findViewById(R.id.textView5);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String home = extras.getString(MainActivity.HOME_KEY);
            String away = extras.getString(MainActivity.AWAY_KEY);
            String homeURL=extras.getString(MainActivity.HOMEIMG_KEY);
            String awayURL=extras.getString(MainActivity.AWAYIMG_KEY);
            homeText.setText(home);
            awayText.setText(away);
            try {
                Bitmap homebitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(homeURL));
                Bitmap awaybitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(awayURL));
                homeimg.setImageBitmap(homebitmap);
                awayimg.setImageBitmap(awaybitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
        //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
        //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",
    }

    public void handleScorehome(View view) {
        Intent intent= new Intent(this, ScorerActivity.class);
        startActivityForResult(intent,1);
    }

    public void handleScoreaway(View view) {
        Intent intent= new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == HOME_SCORE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                homeScore++;
                homeTextScore.setText(String.valueOf(homeScore));
                // Get String data from Intent
                String tmp = "\n"+ data.getStringExtra("name_key") + " " +
                data.getStringExtra("minute_key") + " \"";

                listHomeScorer = listHomeScorer + tmp;
                // Set text view with string
                TextView textView = (TextView) findViewById(R.id.txtname1);
                textView.setText(listHomeScorer);
            }
        }

        else if (requestCode == AWAY_SCORE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                awayScore++;
                awayTextScore.setText(String.valueOf(awayScore));
                // Get String data from Intent
                String tmp = "\n"+ data.getStringExtra("name_key") + " " +
                        data.getStringExtra("minute_key") + " \"";

                listAwayScorer = listAwayScorer + tmp;
                // Set text view with string
                TextView textView = (TextView) findViewById(R.id.txtname2);
                textView.setText(listAwayScorer);
            }
        }
    }
}
