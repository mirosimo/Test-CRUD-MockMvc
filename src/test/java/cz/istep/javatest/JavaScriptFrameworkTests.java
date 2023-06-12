package cz.istep.javatest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
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
		
		JavaScriptFramework root = new JavaScriptFramework("root", null, 0);		
			JavaScriptFramework reactV1_0 = new JavaScriptFramework("React ver 1.0", root, 1);
			JavaScriptFramework reactV2_0 = new JavaScriptFramework("React ver 2.0", root, 2);
			JavaScriptFramework reactV3_0 = new JavaScriptFramework("React ver 3.0", root, 3);
				JavaScriptFramework reactV3_1 = new JavaScriptFramework("React ver 3.1", reactV3_0, 1);
				JavaScriptFramework reactV3_2 = new JavaScriptFramework("React ver 3.2", reactV3_0, 2);
				
			JavaScriptFramework vueV1_0 = new JavaScriptFramework("Vue.js ver 1.0", root, 1);
			JavaScriptFramework vueV2_0 = new JavaScriptFramework("Vue.js ver 2.0", root, 2);
		
			frameworkService.saveEntity(root);
				frameworkService.saveEntity(reactV1_0); 
				frameworkService.saveEntity(reactV2_0);
				frameworkService.saveEntity(reactV3_0);
					frameworkService.saveEntity(reactV3_1);
					frameworkService.saveEntity(reactV3_2); 		
				frameworkService.saveEntity(vueV1_0); 
				frameworkService.saveEntity(vueV2_0);
	}

	@Test
	@DisplayName("Get **/framework-list** - Should return all Frameworks.")
	public void frameworksTest() throws Exception {
		prepareData();

		mockMvc.perform(get("/framework-list")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(8)))
				.andExpect(jsonPath("$[0].name", is("root")))
				.andExpect(jsonPath("$[1].name", is("React ver 1.0")));		
				// To Do
	}
	
	
	@Test
	@DisplayName("Get **/framework/{id}** - Get Framework by ID.")
	public void frameworkById() throws Exception {
		// To Do			
	}
	
	@Test
	@DisplayName("Get **/framework/{id}** - Get Framework by ID - id not exists.")
	public void frameworkBy_IdNotExists() throws Exception {
		// To Do			
	}
	
	
	@Test
	@DisplayName("Post **/framework-new** - Check if new Framework added")
	public void addFrameworkTest() throws Exception {
		JavaScriptFramework parentFramework = this.frameworkService.findEntityByName("root");
		JavaScriptFramework vueV3 = new JavaScriptFramework("React ver 3.0", parentFramework, 1);
		
		mockMvc.perform(post("/framework-new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(vueV3)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.name", is("React ver 3.0")));		
		
		assertThat(this.frameworkService.getAllEntities()).hasSize(9);
	}
	
	
	@Test
	@DisplayName("Delete **/framework-delete/{id}** - Check if Framework was deleted")
	public void deleteFrameworkById() throws Exception {
		
		this.mockMvc.perform(delete("/framework-delete/{id}", this.frameworkService.findEntityByName("React ver 3.2").getId()))
				.andExpect(status().isNoContent());
		
		// Must be tunned yet ... 
		//assertThat(this.frameworkService.getAllEntities()).hasSize(5);
	}
	
	
	/* 
	 * Check if parent framework id exists 
	 */
	@Test
	@DisplayName("Post **/framework-new** - Parent Framework NOT Exist")
	public void existParentFramework() throws Exception {
		JavaScriptFramework parentFramework = new JavaScriptFramework(400L, "Framework which is not in db", null, 1);
		// To do
		/*assertThat(this.frameworkService.findEntityById(parentFramework.getId())).isNull();*/
	}
			
	
	@Test
	@DisplayName("Post **/framework-new** - Error - empty Framework, Framework exceed the limit length")
	public void addFrameworkInvalid() throws Exception {
		// To do				
		/*JavaScriptFramework framework = new JavaScriptFramework();
		mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("name")))
				.andExpect(jsonPath("$.errors[0].message", is("NotEmpty")));
		
		framework.setName("verylongnameofthejavascriptframeworkjavaisthebest");
		mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors", hasSize(1)))
			.andExpect(jsonPath("$.errors[0].field", is("name")))
			.andExpect(jsonPath("$.errors[0].message", is("Size")));*/
	}

}
