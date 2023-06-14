package cz.istep.javatest.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.istep.javatest.data.JavaScriptFramework;
import cz.istep.javatest.repository.JavaScriptFrameworkRepository;

@Service
public class JavaScriptFrameworkService {
	
	@Autowired
	JavaScriptFrameworkRepository frameworkRepository;
	
	public JavaScriptFramework saveEntity(JavaScriptFramework entity) {
		return frameworkRepository.save(entity);
	}
	
	
	public List<JavaScriptFramework> getAllEntities() {
		List<JavaScriptFramework> listEntities = new ArrayList<>();
		this.frameworkRepository.findAll().forEach(listEntities::add);
		return listEntities;
	}
		
	public void deleteAllEntities() {
		this.frameworkRepository.deleteAll();
	}
	
	public JavaScriptFramework findEntityById(int id) {
		
		return this.frameworkRepository.findById(Long.valueOf(id))
				.orElseThrow(() -> new EntityNotFoundException("Entity JavaScriptFramework with id " + id + " was not found"));
	}
	
	public JavaScriptFramework findEntityByName(String name) {
		return this.frameworkRepository.findByName(name);
	}
	
	public void deleteEntityById(int id) {		
		this.frameworkRepository.deleteById(Long.valueOf(id));
	}
	
	public JavaScriptFramework updateEntity(JavaScriptFramework framework) {
		JavaScriptFramework fr = this.frameworkRepository.save(framework);
		return fr;
	}
	
}
