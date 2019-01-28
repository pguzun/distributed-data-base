package ro.unitbv.pguzun.leaderless.client;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
public class MainApplication {
	
	public static void main(String[] args) throws Exception {
	    ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
	    
	    MainService mainService = context.getBean(MainService.class);
	    final String collection = "mytable";
	    String identifier = UUID.randomUUID().toString();
	    
	    Data myData = new Data(1, "{\"some field\": \"someValue\" }".getBytes("utf-8"));
        mainService.save(collection, identifier, myData);
	    
	    Data data = mainService.get(collection, identifier);
	    
	    assert myData.getValue().equals(data.getValue());
	}
	
	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
	
    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
