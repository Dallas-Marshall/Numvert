package au.edu.jcu.cp3406.numvert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ConvertActivity extends AppCompatActivity {
    private double fromMeasurement; // Measurement to convert
    private String fromUnits;
    private TextView fromUnitsDisplay;
    private String fromMeasurementDisplayText; // Text displayed in fromMeasurementEntry
    private TextView fromMeasurementEntry;
    private String toUnits;
    private TextView toUnitsDisplay;
    private TextView conversionResultDisplay;
    private String conversionResultDisplayText; // Text displayed in conversionResultDisplay
    private boolean hasDecimalBeenEntered;
    private boolean isEntryFrozen; // Freeze entry when fromMeasurement limit is reached
    private final Locale locale = Locale.getDefault();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        fromMeasurementEntry = findViewById(R.id.fromMeasurementEntry);
        conversionResultDisplay = findViewById(R.id.conversionDisplay);
        fromUnitsDisplay = findViewById(R.id.fromUnitsDisplay);
        toUnitsDisplay = findViewById(R.id.toUnitsDisplay);

        Intent intent = getIntent();
        fromUnits = intent.getStringExtra("fromUnits");
        toUnits = intent.getStringExtra("toUnits");
        if (fromUnits == null) { // Set defaults
            fromUnits = "mm";
            toUnits = "m";
        }
        updateUnitsDisplays();
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
            if ((fromMeasurement == 0) && (!hasDecimalBeenEntered)) { // Replace default value.
                fromMeasurement = numberEntered;
                fromMeasurementDisplayText = String.format(locale, "%1.0f", fromMeasurement);
            } else if (!hasDecimalBeenEntered) { // Append to existing value.
                fromMeasurementDisplayText = String.format(locale, "%1.0f%d", fromMeasurement, numberEntered);
                fromMeasurement = Double.parseDouble(fromMeasurementDisplayText);
                if (fromMeasurementDisplayText.length() == 6) { // Limit to 6 digits
                    isEntryFrozen = true;
                }
            } else { // Append after decimal point
                if (numberEntered != 0) { // 0 already displayed
                    fromMeasurementDisplayText = String.format(locale, "%1.0f.%d", fromMeasurement, numberEntered);
                    fromMeasurement = Double.parseDouble(fromMeasurementDisplayText);
                    isEntryFrozen = true; // Limit to 1 decimal place
                }
            }
        }
        updateConversionResultDisplay();
        updateFromMeasurementEntry();
    }

    private void updateConversionResultDisplay() {
        conversionResultDisplayText = Converter.convertLength(fromUnits, fromMeasurement, toUnits);
        conversionResultDisplay.setText(conversionResultDisplayText);
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
            fromMeasurementDisplayText = String.format(locale, "%1.1f", fromMeasurement);
            fromMeasurement = Double.parseDouble(fromMeasurementDisplayText);
            updateFromMeasurementEntry();
        }
    }

    /**
     * Updates fromMeasurementEntry display.
     */
    private void updateFromMeasurementEntry() {
        fromMeasurementEntry.setText(fromMeasurementDisplayText);
    }

    /**
     * Handles event when clearButton is pressed.
     *
     * @param buttonPressed The clearButton view element.
     */
    public void clearClicked(View buttonPressed) {
        fromMeasurement = 0;
        fromMeasurementDisplayText = "0";
        conversionResultDisplayText = "0";
        isEntryFrozen = hasDecimalBeenEntered = false;
        updateFromMeasurementEntry();
        updateConversionResultDisplay();
    }

    /**
     * Handles event when settingsButton is pressed.
     *
     * @param buttonPressed the settingsButton view element.
     */
    public void settingsClicked(View buttonPressed) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public void updateUnitsDisplays() {
        fromUnitsDisplay.setText(fromUnits);
        toUnitsDisplay.setText(toUnits);
    }
}