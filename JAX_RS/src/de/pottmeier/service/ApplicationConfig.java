package de.pottmeier.service;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/pottmeier")
public class ApplicationConfig extends Application {
	
	@Inject
	private UserRepository repo;
	
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>();
    }

	@Override
	public Set<Object> getSingletons() {
		 return new HashSet<Object>(Arrays.asList(repo));
	}
    
    
}
