package com.huloteam.kbecalculator;

import com.huloteam.kbecalculator.calculator.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KbeCalculatorApplicationTests {

	private Calculator calculator;


	@BeforeEach
	void init() {
		calculator = new Calculator();
	}


	@Test
	@DisplayName("Read JSON")
	void readJson() {
		assertEquals(0.19, calculator.getTaxMap().get("DE_REGULAR"));
	}

	@Test
	@DisplayName("Don't read JSON")
	void readJsonWithWrongMapKey() {
		assertNull(calculator.getTaxMap().get("DE_WHAT"));
	}

	@Test
	@DisplayName("Read description")
	void readDescription() {
		assertEquals("Regular German VAT.", calculator.getDescription().get("DE_REGULAR"));
	}

	@Test
	@DisplayName("Don't read description")
	void readDescriptionWithWrongMapKey() {
		assertNull(calculator.getDescription().get("DE_WHAT"));
	}

	@Test
	@DisplayName("Calculate with 10€")
	void calculateCommonPrice() {
		assertEquals(11.9, calculator.calculatePrice("DE_REGULAR", 10));
	}

	@Test
	@DisplayName("Calculate with -4€")
	void calculateUncommonPrice() {
		assertEquals(-1, calculator.calculatePrice("DE_REGULAR", -4));
	}

	@Test
	@DisplayName("Calculate with 0€")
	void calculateWithZero() {
		assertEquals(-1, calculator.calculatePrice("DE_REGULAR", 0));
	}

	@Test
	@DisplayName("Calculate with 10€ but wrong TaxInformation")
	void calculateWithWrongTaxInformation() {
		assertEquals(-1, calculator.calculatePrice("wrongTaxInformation", 10));
	}

	@Test
	@DisplayName("Calculate with Null")
	void calculateWithNull() {
		assertEquals(-1, calculator.calculatePrice(null, 0));
	}

}
