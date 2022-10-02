package com.planify.Planify_Auc_System_Karan;

import com.planify.Planify_Auc_System_Karan.service.AutoAucSystemExecutionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
public class PlanifyAucSystemKaranApplication {

	public static void main(String[] args) throws IOException {
		final ApplicationContext applicationContext = SpringApplication.run(PlanifyAucSystemKaranApplication.class, args);
		final AutoAucSystemExecutionService autoAucSystemExecutionService = applicationContext
				.getBean(AutoAucSystemExecutionService.class);

		if(args!=null && args.length>0) {
			for (String arg: args
				 ) {
				autoAucSystemExecutionService.automateSystem(arg);
			}
		}
		else{
			System.out.println();
			System.out.println("No file found. Please pass the required json file as a command line argument for auto processing.");
		}
	}

}
