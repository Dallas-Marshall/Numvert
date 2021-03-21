package au.edu.jcu.cp3406.numvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void convertCLicked(View view) {
        Intent intent = new Intent(this, ConvertActivity.class);
        startActivity(intent);
    }
}