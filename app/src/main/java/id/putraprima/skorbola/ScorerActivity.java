package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ScorerActivity extends AppCompatActivity {

    private EditText minuteInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);

    }

    public void handleSubmit(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText1 = (EditText) findViewById(R.id.editText2);
        String stringToPassBack = editText.getText().toString();
        String stringToPassBack2 = editText1.getText().toString();

        // Put the String to pass back into an Intent and close this activity
        Intent intent = new Intent();
        intent.putExtra("name_key", stringToPassBack);
        intent.putExtra("minute_key", stringToPassBack2);
        setResult(RESULT_OK, intent);
        finish();
    }
}
