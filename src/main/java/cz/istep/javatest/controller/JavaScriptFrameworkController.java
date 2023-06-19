package cz.istep.javatest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.istep.javatest.model.JavaScriptFramework;
import cz.istep.javatest.service.JavaScriptFrameworkService;

@RestController
public class JavaScriptFrameworkController {
	@Autowired
	private JavaScriptFrameworkService frameworkService;
 
	/*
	 * Get all frameworks
	 */
	@GetMapping("/framework-list")
	public List<JavaScriptFramework> frameworks() {		
		return this.frameworkService.getAllEntities();
	}
	
	/*
	 * Get one framework by id
	 */
	@GetMapping("/framework/{id}")
	public JavaScriptFramework getFrameworkById(@PathVariable Long id) {		
		return this.frameworkService.findEntityById(id);
	}
	
	/*
	 * Add new framework 
	 */
	@PostMapping("/framework-new")
	public ResponseEntity<JavaScriptFramework> frameworkNew(@Valid @RequestBody JavaScriptFramework framework) {				
		/* 
		 * Roots frameworks have parent field null
		 * 
		 * When is parent found, than is saved
		 * otherwise
		 * is thrown AppEntityNotFoundException and catched in RestExceptionHandler
		 *  
		 */
		if (framework.getParentFramework() != null) {
			return new ResponseEntity<JavaScriptFramework>(this.frameworkService.findParentEntity(framework.getParentFramework().getId()), HttpStatus.CREATED);
		} else { 		
		
			JavaScriptFramework savedFramework = this.frameworkService.saveEntity(framework);				
			return new ResponseEntity<JavaScriptFramework>(savedFramework, HttpStatus.CREATED);
		}
		
	}
	
	
	/*
	 * Delete framework by id
	 */
	@DeleteMapping("/framework-delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFramework(@PathVariable("id") Long frameworkId) {
		this.frameworkService.deleteEntityById(frameworkId);
	}
}
