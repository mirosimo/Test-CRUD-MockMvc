package cz.istep.javatest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cz.istep.javatest.exceptions.AppEntityNotFoundException;
import cz.istep.javatest.model.JavaScriptFramework;
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
		
	public JavaScriptFramework findEntityByName(String name) {
		return this.frameworkRepository.findByName(name);
	}
	
	public void deleteEntityById(Long id) {		
		this.frameworkRepository.deleteById(id);
	}
	
	public JavaScriptFramework findEntityById(Long id) {		
		return this.frameworkRepository.findById(id)
				.orElseThrow(() -> new AppEntityNotFoundException(id, 
						"Entity with id " + id + " was not found.", HttpStatus.NOT_FOUND));
	}
	
	/* Check if parent entity exist - if is in data */
	public JavaScriptFramework findParentEntity(Long parentId) {
		return this.frameworkRepository.findById(parentId)
				.orElseThrow(() -> new AppEntityNotFoundException(parentId, 
						"Parent Entity with id " + parentId + " was not found.", HttpStatus.CONFLICT));		
	}
	
	public Long getMaxId() {
		return this.frameworkRepository.getMaxId();
	}
	
}
