package com.kkraljic.task_express_eval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.kkraljic.task_express_eval"})
public class ExpressionEvaluatorApp {

	public static void main(String[] args) {
		SpringApplication.run(ExpressionEvaluatorApp.class, args);
	}

}
