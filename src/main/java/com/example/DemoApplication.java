package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.lambdas.Lesson1;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	Lesson1 lesson1;

	public static void main(String[] args) {
		// System.out.println("Welcome To Spring Boot! Args: " + args);
		SpringApplication.run(DemoApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			List<String> argsList = new ArrayList(Arrays.asList(args));
			argsList.add("abc");
			argsList.add("defG");
			argsList.add("Yz");

			argsList.forEach(s -> System.out.println("I am am an argument insude a consumer lambda: " + s));

			argsList.removeIf(s -> s.startsWith("--"));
			System.out.println("argsList after removeIf: " + argsList);

			argsList.sort((x, y) -> x.length() - y.length());
			System.out.println("argsList after lenght sort: " + argsList);

			argsList.sort((String x, String y) -> y.compareToIgnoreCase(x));
			System.out.println("argsList after reverse alphabetical sort: " + argsList);

			argsList.replaceAll(s -> s.toUpperCase());
			System.out.println("argsList after replaceAll: " + argsList);

		};
	}

	@Bean
	public CommandLineRunner commandLineRunnerStaticReference(ApplicationContext ctx) {
		return System.out::println;
	}

	@Bean
	public CommandLineRunner commandLineRunnerLambdaExcerices(ApplicationContext ctx) {
		return args -> {
			lesson1.runExercises();
		};
	}
}
