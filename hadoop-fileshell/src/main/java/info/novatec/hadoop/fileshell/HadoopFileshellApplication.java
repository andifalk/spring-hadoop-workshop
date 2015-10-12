package info.novatec.hadoop.fileshell;

import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;

@SpringBootApplication
public class HadoopFileshellApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(HadoopFileshellApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HadoopFileshellApplication.class, args);
    }
    
    
    @Autowired
	private FsShell fsShell;

	@Override
	public void run(String... args) {
		for (FileStatus s : fsShell.lsr("/tmp")) {
			LOG.info("> {}", s.getPath());
		}
	}
}
