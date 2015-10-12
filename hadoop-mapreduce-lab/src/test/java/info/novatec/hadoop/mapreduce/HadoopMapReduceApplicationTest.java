package info.novatec.hadoop.mapreduce;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.data.hadoop.mapreduce.JobUtils.JobStatus;
import org.springframework.data.hadoop.test.context.HadoopDelegatingSmartContextLoader;
import org.springframework.data.hadoop.test.context.MiniHadoopCluster;
import org.springframework.data.hadoop.test.junit.AbstractMapReduceTests;
import org.springframework.data.hadoop.test.support.HadoopClusterFactoryBean;
import org.springframework.data.hadoop.test.support.StandaloneHadoopCluster;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=HadoopDelegatingSmartContextLoader.class)
@MiniHadoopCluster
public class HadoopMapReduceApplicationTest extends AbstractMapReduceTests {
	
	@Qualifier("runner")
	@Autowired
	private JobRunner runner;
	
	@Qualifier("wordcountJob")
	@Autowired
	private Job wordcountJob;

	@Test
	public void verifyMapReduce() throws Exception {
		// run blocks and throws exception if job failed
	    runner.call();

	    JobStatus finishedStatus = waitFinishedStatus(wordcountJob, 60, TimeUnit.SECONDS);
	    assertThat(finishedStatus, notNullValue());

	    // get output files from a job
	    Path[] outputFiles = getOutputFilePaths("/user/gutenberg/output/word/");
	    assertEquals(1, outputFiles.length);
	    assertThat(getFileSystem().getFileStatus(outputFiles[0]).getLen(), greaterThan(0l));

	    // read through the file and check that line with
	    // "themselves  6" was found
	    boolean found = false;
	    InputStream in = getFileSystem().open(outputFiles[0]);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      if (line.startsWith("themselves")) {
	        assertThat(line, is("themselves\t6"));
	        found = true;
	      }
	    }
	    reader.close();
	}
	
	
	@Configuration
	static class HadoopTestConfiguration {
		
		@Bean
		public StandaloneHadoopCluster hadoopCluster() throws Exception {
			HadoopClusterFactoryBean hadoopClusterFactoryBean = new HadoopClusterFactoryBean();
			hadoopClusterFactoryBean.setAutoStart(true);
			hadoopClusterFactoryBean.setNodes(1);
			hadoopClusterFactoryBean.setClusterId("myTestCluster");
			return hadoopClusterFactoryBean.getObject();
		}
		
	}

}
