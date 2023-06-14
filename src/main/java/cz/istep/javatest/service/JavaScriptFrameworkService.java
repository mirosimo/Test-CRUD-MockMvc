package cz.istep.javatest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cz.istep.javatest.data.JavaScriptFramework;
import cz.istep.javatest.exceptions.AppEntityNotFoundException;
import cz.istep.javatest.repository.JavaScriptFrameworkRepository;
import cz.istep.javatest.rest.CustomErrorType;

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
		AppEntityNotFoundException exception = new AppEntityNotFoundException();
		exception.setErrorType(CustomErrorType.ID_NOT_FOUND);
		exception.setHttpStatus(HttpStatus.NOT_FOUND);
		exception.setMessage("Entity JavaScriptFramework with id " + id + " was not found");
		return this.frameworkRepository.findById(Long.valueOf(id))
				.orElseThrow(() -> exception);
	}
	
	public JavaScriptFramework findEntityById(int id, CustomErrorType errorType) {
		AppEntityNotFoundException exception = new AppEntityNotFoundException();
		
		exception.setErrorType(errorType);		
		
		switch (errorType) {
			case PARENT_ENTITY_NOT_FOUND:
				exception.setMessage("Parent entity with id " + id + " was not found");	
				exception.setHttpStatus(HttpStatus.CONFLICT);
				break;
			default:
				exception.setMessage("Entity JavaScriptFramework with id " + id + " was not found");
				exception.setHttpStatus(HttpStatus.NOT_FOUND);
		}
		
		return this.frameworkRepository.findById(Long.valueOf(id))
				.orElseThrow(() -> exception); 
		
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
