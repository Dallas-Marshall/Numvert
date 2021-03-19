package au.edu.jcu.cp3406.numvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ConvertActivity extends AppCompatActivity {
    private int entryUnits;
    private String entryUnitsString;
    private TextView fromUnitsEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromUnitsEntry = findViewById(R.id.fromUnitsEntry);
    }

    /**
     * Updates fromEntryUnits TextView when a keypadButton is clicked.
     *
     * @param button The button that triggered event.
     */
    public void keypadClicked(View button) {
        Button keypadButton = (Button) button;
        String numberEntered = keypadButton.getText().toString();

        if (entryUnits == 0) { // Replace default 0
            entryUnits = Integer.parseInt(numberEntered);
            entryUnitsString = numberEntered;
        } else { // Append number entered
            Locale locale = Locale.getDefault();
            entryUnitsString = String.format(locale, "%d%s", entryUnits, numberEntered);
            entryUnits = Integer.parseInt(entryUnitsString);
        }

        fromUnitsEntry.setText(entryUnitsString);
    }
}