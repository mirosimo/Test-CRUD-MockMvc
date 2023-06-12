package cz.istep.javatest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.istep.javatest.data.JavaScriptFramework;
import cz.istep.javatest.service.JavaScriptFrameworkService;

@RestController
public class JavaScriptFrameworkController {
	
	private final JavaScriptFrameworkService frameworkService;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkService frameworkService) {
		this.frameworkService = frameworkService;
	}

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
	public JavaScriptFramework getFrameworkById(@PathVariable int id) {		
		return this.frameworkService.findEntityById(id);
	}
	
	/*
	 * Add new framework 
	 */
	@PostMapping("/framework-new")
	public ResponseEntity<JavaScriptFramework> frameworkNew(@RequestBody JavaScriptFramework framework) {
		JavaScriptFramework savedFramework = this.frameworkService.saveEntity(framework);				
		return new ResponseEntity<JavaScriptFramework>(savedFramework, HttpStatus.CREATED);	
	}
	
	/*
	 * Delete framework by id
	 */
	@DeleteMapping("/framework-delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFramework(@PathVariable("id") int frameworkId) {
		this.frameworkService.deleteEntityById(frameworkId);
	}
	
	/*
	 * Updates framework
	 */
	@PutMapping("/framework-update")
	public JavaScriptFramework frameworkUpdate(@RequestBody JavaScriptFramework framework) {
		JavaScriptFramework updatedEntity = this.frameworkService.updateEntity(framework);
		return updatedEntity;
	}
	
	 
	
	
}
