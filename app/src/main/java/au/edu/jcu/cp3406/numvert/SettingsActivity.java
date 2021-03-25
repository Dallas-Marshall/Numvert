package au.edu.jcu.cp3406.numvert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String fromUnits;
    private String toUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Load currently selected units
        Intent intent = getIntent();
        fromUnits = intent.getStringExtra("fromUnits");
        toUnits = intent.getStringExtra("toUnits");
        setupSpinners();
    }

    private void setupSpinners() {
        // Setup spinners
        Spinner fromUnitsSpinner = findViewById(R.id.fromUnitsSpinner);
        Spinner toUnitsSpinner = findViewById(R.id.toUnitsSpinner);

        ArrayAdapter<CharSequence> adapterFromUnits = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        adapterFromUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitsSpinner.setAdapter(adapterFromUnits);

        ArrayAdapter<CharSequence> adapterToUnits = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        adapterToUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toUnitsSpinner.setAdapter(adapterToUnits);

        // Set Values
        int fromUnitsPosition = adapterFromUnits.getPosition(fromUnits);
        fromUnitsSpinner.setSelection(fromUnitsPosition);

        int toUnitsPosition = adapterToUnits.getPosition(toUnits);
        toUnitsSpinner.setSelection(toUnitsPosition);

        // Set listeners
        fromUnitsSpinner.setOnItemSelectedListener(this);
        toUnitsSpinner.setOnItemSelectedListener(this);
    }

    /**
     * Handles event when convertButton is pressed.
     *
     * @param buttonPressed The convertButton view element.
     */
    public void convertCLicked(View buttonPressed) {
        Intent intent = new Intent(this, ConvertActivity.class);
        intent.putExtra("fromUnits", fromUnits);
        intent.putExtra("toUnits", toUnits);
        startActivity(intent);
    }

    /**
     * Handles when a spinner is changed.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.fromUnitsSpinner) {
            fromUnits = parent.getItemAtPosition(position).toString();
        } else if (parent.getId() == R.id.toUnitsSpinner) {
            toUnits = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}