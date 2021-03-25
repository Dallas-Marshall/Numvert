package au.edu.jcu.cp3406.numvert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestConverter {

    @Test
    public void testConversions() {
        double fromMeasurement = 5;

        // Test mm -> m (decimal output)
        String fromUnits = "mm";
        String toUnits = "m";
        String conversionResultDisplayText = Converter.convertLength(fromUnits, fromMeasurement, toUnits);
        assertEquals("0.005m", conversionResultDisplayText);

        // Test km -> m (decimal output)
        fromUnits = "km";
        toUnits = "m";
        conversionResultDisplayText = Converter.convertLength(fromUnits, fromMeasurement, toUnits);
        assertEquals("5000.0m", conversionResultDisplayText);

        // Test mm -> nmi (scientific notation output)
        fromUnits = "mm";
        toUnits = "nmi";
        conversionResultDisplayText = Converter.convertLength(fromUnits, fromMeasurement, toUnits);
        assertEquals("2.70e-06nmi", conversionResultDisplayText);
    }
}