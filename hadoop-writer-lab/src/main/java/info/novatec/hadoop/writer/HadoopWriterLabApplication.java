package info.novatec.hadoop.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HadoopWriterLabApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(HadoopWriterLabApplication.class);
	

	@Autowired
	private IngestProcessor ingestProcessor;
	
    public static void main(String[] args) {
        SpringApplication.run(HadoopWriterLabApplication.class, args);
    }
    
    @Override
	public void run(String... arg0) throws Exception {
        LOG.info("*** RUNNING ...");
		ingestProcessor.process();
	}
}
