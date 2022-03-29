package com.huloteam.kbecalculator.calculator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class calculates added value tax (VAT) onto a price.
 */
public class Calculator {

    private final Map<String, Double> taxMap;
    private final Map<String, String> description;
    private boolean isReadyToCalculateFlag = false;

    public Calculator() {

        taxMap = new HashMap<>();
        description = new HashMap<>();
        getTaxesFromJSON();

    }

    /**
     * Reads a JSON file.
     */
    private void getTaxesFromJSON() {

        //String fileName = "./src/main/resources/static/vats.json";
        String fileName = "vats.json";
        Object object = null;

        try {
            object = new JSONParser().parse(new FileReader(fileName));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        if (object != null) {

            JSONArray jsonArray = (JSONArray) object;
            saveIntoMap(jsonArray);

        }

    }

    /**
     * Saves the JSON information into a Map (taxMap).
     * @param jsonArray contains taxes.
     */
    private void saveIntoMap(JSONArray jsonArray) {

        for (Object object : jsonArray) {

            JSONObject jsonObject = (JSONObject) object;
            taxMap.put(jsonObject.get("id").toString(), Double.valueOf(jsonObject.get("value").toString()));
            description.put(jsonObject.get("id").toString(), jsonObject.get("description").toString());

        }

        if (!taxMap.isEmpty()) {
            isReadyToCalculateFlag = true;
        }

    }

    /**
     * Multiplies a specific added value tax (VAT) with a price.
     * @param taxInformation says which VAT should be used.
     * @param price of a product.
     * @return a price calculated with VAT.
     */
    public double calculatePrice(String taxInformation, double price) {

        if (isReadyToCalculateFlag) {
            if (taxInformation != null) {
                if (price > 0) {

                    for (String id : taxMap.keySet()) {
                        if (id.contains(taxInformation)) {
                            // parseDouble expects a US localized String (dot instead of comma)
                            return Double.parseDouble(String.format(Locale.US, "%.2f", price + (price * taxMap.get(id))));
                        }
                    }

                }
            }
        }

        return -1;

    }


    public Map<String, Double> getTaxMap() {
        return taxMap;
    }

    public Map<String, String> getDescription() {
        return description;
    }

}
