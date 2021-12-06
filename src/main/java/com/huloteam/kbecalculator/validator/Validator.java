package com.huloteam.kbecalculator.validator;

import com.huloteam.kbecalculator.calculator.Calculator;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * This class holds a bunch of methods to validate values.
 */
public class Validator {

    /**
     * Checks if given String contains only numbers.
     * @param value is a String.
     * @return true - only numbers / false - contains letters or symbols.
     */
    public static boolean checkValue(String value) {
        return NumberUtils.isCreatable(value);
    }

    /**
     * Checks if taxMap (Collection) in calculator (Class) contains vatId (String).
     * @param vatId is a String.
     * @param calculator is an object of Calculator (Class).
     * @return true - contains vatId / false - doesn't contain vatId.
     */
    public static boolean checkVatId(String vatId, Calculator calculator) {

        for (String id : calculator.getTaxMap().keySet()) {
            if (id.contains(vatId)) {
                return true;
            }
        }

        return false;

    }

}
