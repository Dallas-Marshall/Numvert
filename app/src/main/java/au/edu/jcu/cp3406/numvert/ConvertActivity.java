package au.edu.jcu.cp3406.numvert;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ConvertActivity extends AppCompatActivity {
    private double fromUnits; // Units to convert
    private String fromUnitsDisplayText; // Text displayed in fromUnitsEntry
    private TextView fromUnitsEntry;
    private boolean hasDecimalBeenEntered;
    private boolean isEntryFrozen; // Freeze entry when fromUnits limit is reached
    private final Locale locale = Locale.getDefault();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        fromUnitsEntry = findViewById(R.id.fromUnitsEntry);
    }

    /**
     * Handles event when keypadButton is pressed.
     *
     * @param buttonPressed The keypadButton view element that was pressed
     */
    public void keypadClicked(View buttonPressed) {
        Button keypadButton = (Button) buttonPressed;
        int numberEntered = Integer.parseInt(keypadButton.getText().toString());

        if (!isEntryFrozen) {
            if (fromUnits == 0) { // Replace default value.
                fromUnits = numberEntered;
                fromUnitsDisplayText = String.format(locale, "%1.0f", fromUnits);
            } else if (!hasDecimalBeenEntered) { // Append to existing value.
                fromUnitsDisplayText = String.format(locale, "%1.0f%d", fromUnits, numberEntered);
                fromUnits = Double.parseDouble(fromUnitsDisplayText);
                if (fromUnitsDisplayText.length() == 6) { // Limit to 6 digits
                    isEntryFrozen = true;
                }
            } else { // Append after decimal point
                fromUnitsDisplayText = String.format(locale, "%1.0f.%d", fromUnits, numberEntered);
                fromUnits = Double.parseDouble(fromUnitsDisplayText);
                isEntryFrozen = true; // Limit to 1 decimal place
            }
        }
        updateFromUnitsEntry();
    }

    /**
     * Handles event when decimalButton is pressed.
     *
     * @param buttonPressed The decimalButton view element.
     */
    public void decimalClicked(View buttonPressed) {
        hasDecimalBeenEntered = true;

        if (!isEntryFrozen) {
            Locale locale = Locale.getDefault();
            fromUnitsDisplayText = String.format(locale, "%1.1f", fromUnits);
            fromUnits = Double.parseDouble(fromUnitsDisplayText);
            updateFromUnitsEntry();
        }
    }

    private void updateFromUnitsEntry() {
        fromUnitsEntry.setText(fromUnitsDisplayText);
    }
}