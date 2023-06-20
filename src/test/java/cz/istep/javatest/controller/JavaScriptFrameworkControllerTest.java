package cz.istep.javatest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import cz.istep.javatest.model.JavaScriptFramework;
import cz.istep.javatest.service.JavaScriptFrameworkService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JavaScriptFrameworkService frameworkService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@BeforeEach
	public void prepareData() throws Exception {
		this.frameworkService.deleteAllEntities();
		
			JavaScriptFramework rootReact = frameworkService.saveEntity(new JavaScriptFramework("ROOT React", null, 0));
				frameworkService.saveEntity(new JavaScriptFramework("React ver 1.0", rootReact, 1));
				frameworkService.saveEntity(new JavaScriptFramework("React ver 2.0", rootReact, 2));
				JavaScriptFramework reactV3_0 = frameworkService.saveEntity(new JavaScriptFramework("React ver 3.0", rootReact, 3));
					frameworkService.saveEntity(new JavaScriptFramework("React ver 3.1", reactV3_0, 1));
					
			//JavaScriptFramework rootVue = frameworkService.saveEntity(new JavaScriptFramework("ROOT Vue", null, 1));
				//frameworkService.saveEntity(new JavaScriptFramework("Vue.js ver 1.0", rootVue, 1));
				//frameworkService.saveEntity(new JavaScriptFramework("Vue.js ver 2.0", rootVue, 2));
	}

	@Test
	@DisplayName("GET /framework-list - Should return all Frameworks.")
	public void frameworksTest() throws Exception {
		mockMvc.perform(get("/framework-list")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[0].id", notNullValue()))
				.andExpect(jsonPath("$[0].name", is("ROOT React")))
				.andExpect(jsonPath("$[1].id", notNullValue()))
				.andExpect(jsonPath("$[1].name", is("React ver 1.0")))
				.andExpect(jsonPath("$[2].id", notNullValue()))
				.andExpect(jsonPath("$[2].name", is("React ver 2.0")))
				.andExpect(jsonPath("$[3].id", notNullValue()))
				.andExpect(jsonPath("$[3].name", is("React ver 3.0")))
				.andExpect(jsonPath("$[4].id", notNullValue()))
				.andExpect(jsonPath("$[4].name", is("React ver 3.1")));

							
	}
	
	@Test
	@DisplayName("GET /framework/{id} - Get Framework by ID - id EXIST.")
	public void frameworkById() throws Exception {		
		Long entityId = frameworkService.findEntityByName("React ver 3.1").getId();		
		mockMvc.perform(get("/framework/{id}", entityId)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id", is(entityId.intValue())))
					.andExpect(jsonPath("$.name", is("React ver 3.1")));			
	}
	
	@Test
	@DisplayName("GET /framework/{id} - Get Framework by ID - id NOT EXIST.")
	public void frameworkBy_IdNotExists() throws Exception {
		// max id + some increment surely not exist
		Long notExistValue = this.frameworkService.getMaxId() + 14L;
		
		mockMvc.perform(get("/framework/{id}", notExistValue))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))			
			.andExpect(jsonPath("$.message", is("Entity with id " + notExistValue + " was not found.")));		
	}
	
	
	@Test
	@DisplayName("POST /framework-new - Add new Framework")
	public void addFrameworkTest() throws Exception {
		JavaScriptFramework parentFramework = this.frameworkService.findEntityByName("React ver 3.0");
		JavaScriptFramework react3_3 = new JavaScriptFramework("React ver 3.2", parentFramework, 1);
		
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(react3_3)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.name", is("React ver 3.2")));		
		
		assertThat(this.frameworkService.getAllEntities()).hasSize(6);
	}
	
	@Test
	@DisplayName("POST /framework-new - Add new Root Framework --> parent null")
	public void addFrameworkParentNullTest() throws Exception {
		JavaScriptFramework vueV3 = new JavaScriptFramework("Some new ROOT framework", null, 1);
		
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(vueV3)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.name", is("Some new ROOT framework")));		
		
		assertThat(this.frameworkService.getAllEntities()).hasSize(6);
	}
	
	@Test
	@DisplayName("POST /framework-new - Add new Framework - Parent Framework NOT Exist")
	public void addNotExistParentFramework() throws Exception {
		// Id greater then maxId surely NOT exist.
		Long notExistId = this.frameworkService.getMaxId() + 18L;
		//JavaScriptFramework parentFramework = new JavaScriptFramework(notExistId, "Framework which is not in db", null, 1);
		//JavaScriptFramework newFramework = new JavaScriptFramework(	"New Framework", parentFramework, 1);
		//newFramework.setParentFramework(parentFramework);
		
		//System.out.println("**** Mapper content ****");
		//System.out.println(mapper.writeValueAsString(parentFramework));
		//System.out.println(mapper.writeValueAsString(newFramework));
			
		String json = "{"
						+ "\"name\":\"New Framework\","
						+ "\"version\":1,\"level\":0,"
						+ "\"parent_framework\":"
						+ 		"{"
						+ 			"\"id\":" + notExistId+","
						+ 			"\"name\":\"Framework which is not in db\","
						+ 			"\"version\":1"
						+ 		"},"
						+ "\"nested_frameworks\":null}";
		
		//mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(newFramework)))
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isConflict())
		.andExpect(jsonPath("$.message", is("Parent Entity with id " + notExistId + " was not found.")));		
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
		Long delEntity = this.frameworkService.findEntityByName("React ver 3.1").getId();
		
		this.mockMvc.perform(delete("/framework-delete/{id}", delEntity))
				.andExpect(status().isNoContent());
		 
		assertThat(this.frameworkService.getAllEntities()).hasSize(4);
	}
	
}
