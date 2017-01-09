package com.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.bcel.util.ClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

/**
 * Spring Boot Demo Application
 * 
 * @author RomanPelepchuk
 *
 */
@SpringBootApplication
public class DemoApplication {

	/**
	 * The File Reader
	 */
	@Autowired
	FileReader fileReader;

	/**
	 * The Main method. (Spring Boot will look for this when starting up.)
	 * 
	 * @param args
	 *            the args
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * Will run upon startup by Spring
	 * 
	 * @param ctx
	 *            the application Context
	 * @return Implementation of {@link CommandLineRunner}functional Interface
	 */
	@Bean
	public CommandLineRunner runTasks(ApplicationContext ctx) {
		return args -> {

			// Init the Scanner
			Scanner scanner = new Scanner(System.in);
			try {

				System.out.println("Running Lohika test tasks.");
				Stream<String> fileStream = fileReader.readFile(ClassLoader.getSystemResource("words.txt").toURI());

				// Get a Map of words
				Map<String, Integer> result = fileStream.flatMap(line -> Stream.of(line.split("\\s+")))
						.map(String::toLowerCase).collect(Collectors.toMap(word -> word, word -> 1, Integer::sum));

				// Get user input for N
				System.out.println("Input Expected number of top word occurences (N): ");
				int n = scanner.nextInt();
				scanner.close();

				// Print out the top N occurences:
				System.out.println(String.format("Top %s Frequent Words: ", n));

				// Sort Map Entries by Value and print top records (if count is
				// identical sort by word)
				result.entrySet().stream().sorted((a, b) -> a.getValue() == b.getValue()
						? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue()).limit(n)
						.forEach(System.out::println);

				System.out.println("-----------\n");

				// Check every word in file if is a Palindrome:
				palindromeCheck(result.keySet());

			} catch (

			IOException e) {
				// Loggers would go here
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Basta.");
			}
		};
	}

	/**
	 * Checks a Collection of Strings if the values are Palindromes
	 * 
	 * @param collection
	 *            collection of Strings
	 */
	protected void palindromeCheck(Collection<String> collection) {

		Assert.isTrue(collection != null && !collection.isEmpty());
		System.out.println("-----------\n");

		System.out.println("Running the Palindrome test. Checking every word in collection if it's a palindrome: ");
		collection.forEach(s -> {
			System.out
					.println(String.format("%s %s a palindrome", s, PalindromeUtils.isPalindrome(s) ? "is" : "is not"));
		});

		// Demo of Java 8 filtering:
		Set<String> palindromesSet = collection.stream().filter(PalindromeUtils::isPalindrome)
				.collect(Collectors.toSet());
		System.out.println(String.format("%s palindrome(s) found: %s", palindromesSet.size(), palindromesSet));
		System.out.println("-----------\n");
	}

}
