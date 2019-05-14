package hu.elte.szgy.tudor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class TudorApp //implements ApplicationRunner //egyelőre nem kell
{
	
	private static Logger log = LoggerFactory.getLogger(TudorApp.class);
	
    public static void main( String[] args ) throws Exception
    {
        //System.out.println( "Hello World!" );
    	SpringApplication.run(TudorApp.class, args);
    }
    
    /*@Override
    public void run(ApplicationArguments arg0) throws Exception {
    	System.out.println("Hello world from application runner!");
    }*/
    
    /*
    @Bean
    public Module hibernate5Module() {
    	log.info("Enabling Hibernate5Module");
    	return new Hibernate5Module();
    }
    */
}
