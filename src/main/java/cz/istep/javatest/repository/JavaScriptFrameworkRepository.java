package cz.istep.javatest.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.istep.javatest.model.JavaScriptFramework;

public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {
	/* 
	 * Find framework by its name 
	 */
	public JavaScriptFramework findByName(String name);
	
	@Query(nativeQuery = true, value = "select max(id) from framework_item")
	public Long getMaxId();
}
