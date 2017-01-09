package com.example;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * Demo of a Unitily Class for Palindrome Checking
 * 
 * @author RomanPelepchuk
 *
 */
public final class PalindromeUtils {

	private PalindromeUtils() {

	}

	/**
	 * Checks if a String is a Palindrome
	 * 
	 * @param string
	 *            the string
	 * @return true if is a Palindrome
	 */
	public static boolean isPalindrome(String string) {

		Assert.isTrue(StringUtils.isNotBlank(string), "String value must be provided");

		int n = string.length();

		for (int i = 0; i < n / 2; ++i) {
			if (string.charAt(i) != string.charAt(n - i - 1))
				return false;
		}
		return true;
	}
}
