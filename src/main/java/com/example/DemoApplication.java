package com.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.bcel.util.ClassLoader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	FileReader fileReader;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner runTasks(ApplicationContext ctx) {
		return args -> {
			readerTask();
		};

	}

	private void readerTask() {
		Scanner scanner = new Scanner(System.in);
		try {

			System.out.println("Running Reader test. Input Expected number of top occurences (N): ");

			int n = scanner.nextInt();
			scanner.close();

			Stream<String> fileStream = fileReader.readFile(ClassLoader.getSystemResource("words.txt").toURI());

			// Get a Map of words and count
			Map<String, Integer> result = fileStream.flatMap(line -> Stream.of(line.split("\\s+")))
					.map(String::toLowerCase).collect(Collectors.toMap(word -> word, word -> 1, Integer::sum));

			// Print out the top N occurences:
			result.entrySet().stream().sorted((a, b) -> a.getValue() == b.getValue() ? a.getKey().compareTo(b.getKey())
					: b.getValue() - a.getValue()).limit(n).forEach(System.out::println);

			System.out.println("-----------\n");
			System.out.println("-----------\n");
			System.out.println("Running Palindrome test. Checking every word in file if it's a palindrome ");

			// Check every word in file if is a Palindrome:
			for (String s : result.keySet()) {
				palindromeCheck(s);
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void palindromeCheck(String string) {
		boolean isPalindrome = isPalindrome(string);

		System.out.println(String.format("The line %s %s a palindrome", string, isPalindrome ? "is" : "is not"));
		System.out.println("-----------\n");
	}

	private boolean isPalindrome(String string) {

		Assert.isTrue(StringUtils.isNotBlank(string), "String value must be provided");

		int n = string.length();

		for (int i = 0; i < n / 2; ++i) {
			if (string.charAt(i) != string.charAt(n - i - 1))
				return false;
		}
		return true;
	}
}
