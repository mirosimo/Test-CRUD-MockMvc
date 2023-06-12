package cz.istep.javatest.repository;


import cz.istep.javatest.data.JavaScriptFramework;
import org.springframework.data.repository.CrudRepository;

public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {
	
	/* 
	 * Find framework by its name 
	 */
	public JavaScriptFramework findByName(String name);
}
