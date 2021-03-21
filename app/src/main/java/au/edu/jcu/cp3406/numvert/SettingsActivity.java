package au.edu.jcu.cp3406.numvert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String fromUnits;
    private String toUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Setup spinners
        Spinner fromUnitsSpinner = findViewById(R.id.fromUnitsSpinner);
        Spinner toUnitsSpinner = findViewById(R.id.toUnitsSpinner);

        ArrayAdapter<CharSequence> adapterFromUnits = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        adapterFromUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitsSpinner.setAdapter(adapterFromUnits);

        ArrayAdapter<CharSequence> adapterToUnits = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        adapterToUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toUnitsSpinner.setAdapter(adapterToUnits);

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