package cz.istep.javatest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.istep.javatest.data.JavaScriptFramework;
import cz.istep.javatest.service.JavaScriptFrameworkService;


@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkTests {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private JavaScriptFrameworkService frameworkService;
	

	
	@BeforeEach
	public void prepareData() throws Exception {
		this.frameworkService.deleteAllEntities();
		
		JavaScriptFramework rootReact = new JavaScriptFramework("ROOT React", null, 0);		
			JavaScriptFramework reactV1_0 = new JavaScriptFramework("React ver 1.0", rootReact, 1);
			JavaScriptFramework reactV2_0 = new JavaScriptFramework("React ver 2.0", rootReact, 2);
			JavaScriptFramework reactV3_0 = new JavaScriptFramework("React ver 3.0", rootReact, 3);
				JavaScriptFramework reactV3_1 = new JavaScriptFramework("React ver 3.1", reactV3_0, 1);
				JavaScriptFramework reactV3_2 = new JavaScriptFramework("React ver 3.2", reactV3_0, 2);
		
		JavaScriptFramework rootVue = new JavaScriptFramework("ROOT Vue", null, 1);		
			JavaScriptFramework vueV1_0 = new JavaScriptFramework("Vue.js ver 1.0", rootVue, 1);
			JavaScriptFramework vueV2_0 = new JavaScriptFramework("Vue.js ver 2.0", rootVue, 2);
		
			frameworkService.saveEntity(rootReact);
				frameworkService.saveEntity(reactV1_0);
				frameworkService.saveEntity(reactV2_0);
				frameworkService.saveEntity(reactV3_0);
					frameworkService.saveEntity(reactV3_1);
					frameworkService.saveEntity(reactV3_2);
					
			frameworkService.saveEntity(rootVue);
				frameworkService.saveEntity(vueV1_0);
				frameworkService.saveEntity(vueV2_0);
	}

	@Test
	@DisplayName("GET /framework-list - Should return all Frameworks.")
	public void frameworksTest() throws Exception {
		prepareData();

		mockMvc.perform(get("/framework-list")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(9)))
				.andExpect(jsonPath("$[0].name", is("ROOT React")))
				.andExpect(jsonPath("$[1].name", is("React ver 1.0")));		
				// To Do
	}
	
	
	@Test
	@DisplayName("GET /framework/{id} - Get Framework by ID.")
	public void frameworkById() throws Exception {		
		JavaScriptFramework angular = frameworkService.saveEntity(new JavaScriptFramework("ROOT Angular", null, 2));
		Long angId = angular.getId();
		mockMvc.perform(get("/framework/{id}", angId)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id", is(angId.intValue())))
					.andExpect(jsonPath("$.name", is("ROOT Angular")));
			
	}
	
	@Test
	@DisplayName("GET /framework/{id} - Get Framework by ID - id NOT exists.")
	public void frameworkBy_IdNotExists() throws Exception {
		mockMvc.perform(get("/framework/{id}", 999L))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.errorType", is("ID_NOT_FOUND")))
			.andExpect(jsonPath("$.message", is("Entity JavaScriptFramework with id 999 was not found")));		
	}
	
	
	@Test
	@DisplayName("POST /framework-new - Add new Framework")
	public void addFrameworkTest() throws Exception {
		JavaScriptFramework parentFramework = this.frameworkService.findEntityByName("ROOT React");
		JavaScriptFramework vueV3 = new JavaScriptFramework("React ver 3.0", parentFramework, 1);
		
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(vueV3)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.name", is("React ver 3.0")));		
		
		assertThat(this.frameworkService.getAllEntities()).hasSize(10);
	}
	
	@Test
	@DisplayName("POST /framework-new - Add new Root Framework --> parent null")
	public void addFrameworkParentNullTest() throws Exception {
		JavaScriptFramework vueV3 = new JavaScriptFramework("Some new ROOT framework", null, 1);
		
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(vueV3)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.name", is("Some new ROOT framework")));		
		
		assertThat(this.frameworkService.getAllEntities()).hasSize(10);
	}
	

	@Test
	@DisplayName("POST /framework-new - Add new Framework - Parent Framework NOT Exist")
	public void addNotExistParentFramework() throws Exception {
		JavaScriptFramework parentFramework = new JavaScriptFramework(400L, "Framework which is not in db", null, 1);
		JavaScriptFramework newFramework = new JavaScriptFramework("New Framework", parentFramework, 1);
		
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(newFramework)))
		.andExpect(status().isConflict())
		.andExpect(jsonPath("$.errorType", is("PARENT_ENTITY_NOT_FOUND")))
		.andExpect(jsonPath("$.message", is("Parent entity with id 400 was not found")));		
	}
	

	@Test
	@DisplayName("POST /framework-new - Error - empty Framework, Framework name exceed the limit length")
	public void addFrameworkInvalid() throws Exception {						
		JavaScriptFramework framework = new JavaScriptFramework();
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("name")))
				.andExpect(jsonPath("$.errors[0].message", is("NotEmpty")));
		
		framework.setName("verylongnameofthejavascriptframeworkjavaisthebest");
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors", hasSize(1)))
			.andExpect(jsonPath("$.errors[0].field", is("name")))
			.andExpect(jsonPath("$.errors[0].message", is("Size")));
	}
	
	
	@Test
	@DisplayName("DELETE /framework-delete/{id} - Check if Framework was deleted")
	public void deleteFrameworkById() throws Exception {
		
		this.mockMvc.perform(delete("/framework-delete/{id}", this.frameworkService.findEntityByName("React ver 3.2").getId()))
				.andExpect(status().isNoContent());
		
		// Must be tunned yet ... 
		//assertThat(this.frameworkService.getAllEntities()).hasSize(5);
	}
	
	
	
			
	


}
