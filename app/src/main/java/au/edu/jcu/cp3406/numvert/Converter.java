package au.edu.jcu.cp3406.numvert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Helper class to convert from one unit of length measurement to another.
 */
public class Converter {
    // 2D Array to store conversionFactors
    private static final double[][] lengthConversionTable = {
            {1, 0.1, 0.001, 0.000001, 0.0393701, 0.00328084, 0.00109361, 0.00000062137, 0.00000053996},
            {10, 1, 0.01, 0.00001, 0.393701, 0.0328084, 0.0109361, 0.0000062137, 0.0000053996},
            {1000, 100, 1, 0.001, 39.3701, 3.28084, 1.09361, 0.000621371, 0.000539957},
            {1000000, 100000, 1000, 1, 39370.1, 3280.84, 1093.61, 0.621371, 0.539957},
            {25.4, 2.54, 0.0254, 0.0000254, 1, 0.0833333, 0.0277778, 0.000015783, 0.000013715},
            {304.8, 30.48, 0.3048, 0.0003048, 12, 1, 0.333333, 0.000189394, 0.000164579},
            {914.4, 91.44, 0.9144, 0.0009144, 36, 3, 1, 0.000568182, 0.000493737},
            {1609000, 160934, 1609.34, 1.60934, 63360, 5280, 1760, 1, 0.868976},
            {1852000, 185200, 1852, 1.852, 72913.4, 6076.12, 2025.37, 1.15078, 1}};

    // Array to store corresponding units to each row/column in lengthConversionTable
    private static final ArrayList<String> indexes = new ArrayList<>(
            Arrays.asList("mm", "cm", "m", "km", "in", "ft", "yd", "mi", "nmi"));

    /**
     * Convert a specified length measurement to another measurement.
     * Return formatted conversion in either decimal or scientific notation
     * with corresponding length unit.
     *
     * @param fromUnits       The units to convert from.
     * @param fromMeasurement The length to be converted.
     * @param toUnits         The units to convert to.
     * @return Converted measurement as a formatted String value.
     */
    public static String convertLength(String fromUnits, double fromMeasurement, String toUnits) {
        // Get index of row / column to search
        int fromSelectionIndex = indexes.indexOf(fromUnits);
        int toSelectionIndex = indexes.indexOf(toUnits);

        double conversionFactor = lengthConversionTable[fromSelectionIndex][toSelectionIndex];
        double conversionResult = fromMeasurement * conversionFactor;

        Locale locale = Locale.getDefault();

        int conversionResultLength = ("" + conversionResult).length();
        if (conversionResultLength > 6) { // Return scientific notation if large result
            return String.format(locale, "%4.3g%s", conversionResult, toUnits);
        } else { // Return decimal
            return (conversionResult + toUnits);
        }
    }
}
