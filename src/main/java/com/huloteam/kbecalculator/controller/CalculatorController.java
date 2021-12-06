package com.huloteam.kbecalculator.controller;

import com.huloteam.kbecalculator.calculator.Calculator;
import com.huloteam.kbecalculator.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculatorController {

    private final Calculator calculator = new Calculator();

    // Get - VAT
    @RequestMapping(value = "/calculator")
    public ResponseEntity<Object> provideCalculatedPrice(@RequestParam(name="vatId") String vatId, @RequestParam(name="price") String price) {

        if (Validator.checkValue(price)) {
            if (Validator.checkVatId(vatId, calculator)) {
                return new ResponseEntity<>(calculator.calculatePrice(vatId, Double.parseDouble(price)), HttpStatus.OK);
            } else  {
                return new ResponseEntity<>("No valid VAT id!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("No valid price!", HttpStatus.BAD_REQUEST);
        }

    }

    // Get - Info
    @RequestMapping(value = "/calculator/info")
    public ResponseEntity<Object> provideInformation() {
        return new ResponseEntity<>(calculator.getDescription().toString(), HttpStatus.OK);
    }

}
