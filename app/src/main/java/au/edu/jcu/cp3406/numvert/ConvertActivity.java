package au.edu.jcu.cp3406.numvert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ConvertActivity extends AppCompatActivity {
    public static final int MAXIMUM_INPUT_LENGTH = 6;
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
    private boolean isMaxEntryReached; // Freeze entry when fromMeasurement limit is reached
    private final Locale locale = Locale.getDefault();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        // Define view elements.
        fromMeasurementEntry = findViewById(R.id.fromMeasurementEntry);
        conversionResultDisplay = findViewById(R.id.conversionDisplay);
        fromUnitsDisplay = findViewById(R.id.fromUnitsDisplay);
        toUnitsDisplay = findViewById(R.id.toUnitsDisplay);

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

        if (!isMaxEntryReached) { // Maximum input entered

            if ((fromMeasurement == 0) && (!hasDecimalBeenEntered)) { // Replace default value.
                fromMeasurement = numberEntered;
                fromMeasurementDisplayText = String.format(locale, "%1.0f", fromMeasurement);

            } else if (!hasDecimalBeenEntered) { // Natural Number
                // Append number to existing value.
                fromMeasurementDisplayText = String.format(locale, "%1.0f%d", fromMeasurement, numberEntered);
                fromMeasurement = Double.parseDouble(fromMeasurementDisplayText);

                if (fromMeasurementDisplayText.length() == MAXIMUM_INPUT_LENGTH) { // Number Reached maximum size.
                    isMaxEntryReached = true;
                }
            } else if (numberEntered != 0) { // .0 already displayed
                fromMeasurementDisplayText = String.format(locale, "%1.0f.%d", fromMeasurement, numberEntered);
                fromMeasurement = Double.parseDouble(fromMeasurementDisplayText);
                isMaxEntryReached = true; // Limit to 1 decimal place
            }
        }

        updateConversionResultDisplay();
        updateFromMeasurementEntry();
    }

    /**
     * Update conversionResultDisplay with the current conversion.
     */
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

        if (!isMaxEntryReached) {
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
        resetDisplays();
    }

    /**
     * Reset the state of the Displays.
     */
    public void resetDisplays() {
        fromMeasurement = 0;
        fromMeasurementDisplayText = "0";
        conversionResultDisplayText = "0";
        isMaxEntryReached = hasDecimalBeenEntered = false;
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
        intent.putExtra("fromUnits", fromUnits);
        intent.putExtra("toUnits", toUnits);
        startActivityForResult(intent, 1);
    }

    /**
     * Receive information back from settings activity.
     *
     * @param requestCode Code that was sent in settingsClicked.
     * @param resultCode  Status code that was returned by SettingsActivity.
     * @param data        Intent that stores the data returned by SettingsActivity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            // Get spinner selected units
            fromUnits = data.getStringExtra("fromUnits");
            toUnits = data.getStringExtra("toUnits");

            // Update view
            resetDisplays();
            updateUnitsDisplays();
        }
    }

    /**
     * Update the from and to unit display labels.
     */
    public void updateUnitsDisplays() {
        if (fromUnits == null) { // Set defaults
            fromUnits = "mm";
            toUnits = "m";
        }
        fromUnitsDisplay.setText(fromUnits);
        toUnitsDisplay.setText(toUnits);
    }
}