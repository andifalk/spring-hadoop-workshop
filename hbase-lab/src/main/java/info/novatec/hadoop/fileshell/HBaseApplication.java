package info.novatec.hadoop.fileshell;

import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.hadoop.boot.HadoopFsShellAutoConfiguration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

@SpringBootApplication(exclude= {HadoopFsShellAutoConfiguration.class})
@ImportResource("spring/spring-hbase-config.xml")
public class HBaseApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(HBaseApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HBaseApplication.class, args);
    }
    
    
    @Autowired
	private HbaseTemplate hbaseTemplate;

	@Override
	public void run(String... args) {
		
		List<String> results = hbaseTemplate.find("test", "cf", new HBaseRowMapper());
		
		for (String entry : results) {
			LOG.info("HBase row: {}", entry);
		}
		
	}
	
	
	static class HBaseRowMapper implements RowMapper<String> {

		@Override
		public String mapRow(Result result, int rowNum) throws Exception {
			return result.toString();
		}
		
	}
}
