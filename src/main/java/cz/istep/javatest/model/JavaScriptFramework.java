package cz.istep.javatest.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/*
 * Frameworks are organized in a tree structure.
 * Each framework item could have zero or more sub items (versions) 
 * So we could have multipple levels of items (versions). 
 * 
 * In root item (zero level) are collapsed all subitems. 
 * You can imagine like this.
 * 
 *  -- Root React
 * 		-- React 1.0
 * 		-- React 2.0
 *      -- React 3.0
 *      			-- React 3.1
 *      			-- React 3.2
 *      -- React 4.0
 *  
 *         
 *  -- Root Vue     
 *   	-- Vue 1.0
 *   	-- Vue 2.0
 *   				-- Vue 2.1
 *   				-- Vue 2.2
 *   				-- Vue 2.3
 */


@Entity
@Table(name="framework_item")
public class JavaScriptFramework {

	@Id
	@SequenceGenerator(
			name = "framework_sequence",
			sequenceName = "framework_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "framework_sequence"
	)
	private Long id;
	
	private String name;
	private int version;	
	private int level;		
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private JavaScriptFramework parentFramework;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "parentFramework", 
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL)
	private Set<JavaScriptFramework> nestedFrameworks;
	
	
	public JavaScriptFramework() {
		super();
	}

	public JavaScriptFramework(String name) {
		this.name = name;
	}
	
	public JavaScriptFramework(String name, JavaScriptFramework parentFramework, int versionOrder) {
		this.name = name;
		this.parentFramework = parentFramework;
		this.version = versionOrder;
	}
	
	public JavaScriptFramework(Long id, String name, JavaScriptFramework parentFramework, int versionOrder) {
		this.id = id;
		this.name = name;
		this.parentFramework = parentFramework;
		this.version = versionOrder;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty
	@Size(min = 3, max = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JavaScriptFramework getParentFramework() {
		return parentFramework;
	}
	public void setParentFramework(JavaScriptFramework parentFramework) {
		this.parentFramework = parentFramework;
	}

	public Set<JavaScriptFramework> getNestedFrameworks() {
		return nestedFrameworks;
	}
	public void setNestedFrameworks(Set<JavaScriptFramework> nestedFrameworks) {
		this.nestedFrameworks = nestedFrameworks;
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

}