package com.cbbs.funny.api.service;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cbbs.funny.api.model.InputOutputPair;

@SpringBootTest
public class FunnyServiceTests {
	private final FunnyService funnyService;

	@Autowired
	public FunnyServiceTests(FunnyService funnyService) {
		this.funnyService = funnyService;
	}

	@TestFactory
	Stream<DynamicTest> base64DecodeTest() {
		// Theory
		List<InputOutputPair> testData = List.of(
				new InputOutputPair("YWJjZGVm", "abcdef"), 
				new InputOutputPair("MTIzNDU2", "123456"),
				new InputOutputPair("4LiX4LiU4Liq4Lit4Lia", "ทดสอบ"));
		
		return testData
				.stream()
				.map(test -> DynamicTest.dynamicTest("Input: " + test.input,
				() -> Assertions.assertEquals(test.output, 
						this.funnyService.base64Decode(test.input))));
	}
	
	@TestFactory
	Stream<DynamicTest> base64EncodeTest() {
		// Theory
		List<InputOutputPair> testData = List.of(
				new InputOutputPair("abcdef", "YWJjZGVm"), 
				new InputOutputPair("123456", "MTIzNDU2"),
				new InputOutputPair("ทดสอบ", "4LiX4LiU4Liq4Lit4Lia"));
		
		return testData
				.stream()
				.map(test -> DynamicTest.dynamicTest("Input: " + test.input,
				() -> Assertions.assertEquals(test.output, 
						this.funnyService.base64Encode(test.input))));
	}
	
	@TestFactory
	Stream<DynamicTest> validateInputTextTest() {
		// Theory
		List<String> testData = List.of(
				"ABCDEF", 
				"ABC DEF",
				"ABC  DEF",
				"ABC   DEF",
				" ABCDEF ",
				"  A B C D E F  ");
		
		return testData
				.stream()
				.map(test -> DynamicTest.dynamicTest("Input: " + test,
				() -> Assertions.assertDoesNotThrow(() -> 
					this.funnyService.validateInputText(test))));
	}
	
	@TestFactory
	Stream<DynamicTest> validateInputTextTest_IllegalArgumentException() {
		// Theory
		List<String> testData = List.of(
				"1  A B C D E F  ",
				"  A B C D E F 1 ",
				" - a B C D E F  ",
				"  aBCDEF  ",
				"  a B C D E F  ",
				"  a B C D E F  ",
				"  a B C D E F  ",
				"ทดสอบ");
		
		return testData
				.stream()
				.map(test -> DynamicTest.dynamicTest("Input: " + test,
				() -> Assertions.assertThrows(IllegalArgumentException.class, () -> 
					this.funnyService.validateInputText(test))));
	}
	
	@TestFactory
	Stream<DynamicTest> convertWhitespaceToNumberTest() {
		// Theory
		List<InputOutputPair> testData = List.of(
				new InputOutputPair("abc def", "abc1def"), 
				new InputOutputPair("123  456", "1232456"),
				new InputOutputPair(" abc def", "1abc1def"),
				new InputOutputPair("  abc def", "2abc1def"),
				new InputOutputPair("  abc def ", "2abc1def1"),
				new InputOutputPair(" ทดสอบ", "1ทดสอบ"));
		
		return testData
				.stream()
				.map(test -> DynamicTest.dynamicTest("Input: " + test.input,
				() -> Assertions.assertEquals(test.output, 
						this.funnyService.convertWhitespaceToNumber(test.input))));
	}
	
	@TestFactory
	Stream<DynamicTest> reverseStringTest() {
		// Theory
		List<InputOutputPair> testData = List.of(
				new InputOutputPair("abcdef", "fedcba"), 
				new InputOutputPair("123456", "654321"),
				new InputOutputPair("ทดสอบ", "บอสดท"));

		return testData
				.stream()
				.map(test -> DynamicTest.dynamicTest("Input: " + test.input,
				() -> Assertions.assertEquals(test.output, 
						this.funnyService.reverseString(test.input))));
	}
	
	@TestFactory
	Stream<DynamicTest> doFunnyTest() {
		// Theory
		List<InputOutputPair> testData = List.of(
				new InputOutputPair("VEhFIFdPUkxE", "ZGxyb3cxZWh0"), // "THE WORLD"
				new InputOutputPair("QSBIRU4gIEhBUyAgTUFOWSAgIENISUNLUw", "c2tjaWhjM3luYW0yc2FoMm5laDFh"), // "A HEN  HAS  MANY   CHICKS"
				new InputOutputPair("IFRIRSBXT1JMRCA", "MWRscm93MWVodDE=")); // " THE WORLD "

		return testData
				.stream()
				.map(test -> DynamicTest.dynamicTest("Input: " + test.input,
				() -> Assertions.assertEquals(test.output, 
						this.funnyService.doFunny(test.input))));
	}
	
	@TestFactory
	Stream<DynamicTest> doFunnyTest_IllegalArgumentException() {
		// Theory
		List<String> testData = List.of(
				"IFRIRSBXT1JMRCAxIA", // " THE WORLD 1 "
				"QSBIRU4gIEhBUyAgTUFOWSAgIENISUNLcw"); // "A HEN  HAS  MANY   CHICKs"

		return testData
				.stream()
				.map(test -> DynamicTest.dynamicTest("Input: " + test,
				() -> Assertions.assertThrows(IllegalArgumentException.class, () -> {
					this.funnyService.doFunny(test);
				})));
	}
}
