package info.novatec.hadoop.mapreduce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.hadoop.config.annotation.EnableHadoop;
import org.springframework.data.hadoop.config.annotation.SpringHadoopConfigurerAdapter;
import org.springframework.data.hadoop.config.annotation.builders.HadoopConfigConfigurer;

@SpringBootApplication
@ImportResource({"classpath:spring/spring-mapreduce-job.xml"})
public class HadoopMapReduceApplication {
	
	private static final Logger LOG = LoggerFactory.getLogger(HadoopMapReduceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HadoopMapReduceApplication.class, args);
    }
    
}
