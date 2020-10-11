package com.cbbs.funny.api.service;

import java.util.Base64;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class FunnyService {
	public String doFunny(String input) throws IllegalArgumentException {
		String decodedInput = base64Decode(input);

		validateInputText(decodedInput);

		// 4c-2
		String lowerCaseInput = decodedInput.toLowerCase();
		
		String whitespaceCountedText = convertWhitespaceToNumber(lowerCaseInput);
		
		String reversedText = reverseString(whitespaceCountedText);
		
		String output = base64Encode(reversedText);
		return output;
	}

	public String base64Decode(String input) {
		// 4b
		byte[] decodedByteArray = Base64.getDecoder().decode(input);
		String decodedText = new String(decodedByteArray);
		return decodedText;
	}
	
	public String base64Encode(String input) {
		// 5
		byte[] inputByteArray = input.getBytes();
		String encodedText = Base64.getEncoder().encodeToString(inputByteArray);
		return encodedText;
	}
	
	public void validateInputText(String input) {
		// 4c-1
		if (!Pattern.matches("[A-Z ]*", input)) {
			throw new IllegalArgumentException();
		}
	}
	
	public String convertWhitespaceToNumber(String input) {
		// 4d
		char[] inputCharArray = input.toCharArray();
		int inputCharArrayLength = inputCharArray.length;
		
		StringBuilder outputSb = new StringBuilder();
		int whiteSpaceCount = 0;

		for (int i = 0; i < inputCharArrayLength; i++) {
			char ch = inputCharArray[i];
			if (ch == ' ') {
				whiteSpaceCount++;

				if (i == inputCharArrayLength - 1) {
					// This is the last character which is a white space.
					outputSb.append(Integer.toString(whiteSpaceCount));
				}
			} else {
				if (whiteSpaceCount > 0) {
					outputSb.append(Integer.toString(whiteSpaceCount));
					whiteSpaceCount = 0;
				}
				outputSb.append(ch);
			}
		}
		
		String output = outputSb.toString();
		return output;
	}
	
	public String reverseString(String input) {
		// 4e
		StringBuilder inputSb = new StringBuilder(input);
		StringBuilder reverseInputSb = inputSb.reverse();
		String output = reverseInputSb.toString();
		return output;
	}
}
