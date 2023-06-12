package cz.istep.javatest.data;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*
 * Frameworks are organized in a tree structure.
 * Each framework item could have zero or more sub items (versions) 
 * So we could have multipple levels of items (versions). 
 * 
 * In root item (zero level) are collapsed all subitems. 
 * You can imagine like this.
 * 
 * 0 -- ROOT
 * 		-- React 1.0 
 *      -- React 2.0
 *      -- React 3.0
 * 					-- React 3.1
 * 					-- React 3.2
 *      			-- React 3.3
 *      					-- 3-3.1 React 3.1
 *      					-- 3.3.2 React 3.2
 *      			-- React 3.4
 *      
 * 		-- Vue   1.0
 *   				-- Vue 1.1
 *   				-- Vue 1.2
 */


@Entity
@Table(name="framework_item")
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 3, max = 30)
	private String name;
	
	/*
	 * Id of parent framework item
	 */
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private JavaScriptFramework parentFramework;
    
	
	private int versionOrder;
	
	@JsonIgnore
	@OneToMany(mappedBy = "parentFramework", 
			fetch = FetchType.EAGER, 
			cascade = {CascadeType.ALL})
	@OrderBy("version_order")
	private Set<JavaScriptFramework> nestedFrameworks;
	
	
	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name) {
		this.name = name;
	}
	
	public JavaScriptFramework(String name, JavaScriptFramework parentFramework, int versionOrder) {
		this.name = name;
		this.parentFramework = parentFramework;
		this.versionOrder = versionOrder;
	}
	
	public JavaScriptFramework(Long id, String name, JavaScriptFramework parentFramework, int versionOrder) {
		this.id = id;
		this.name = name;
		this.parentFramework = parentFramework;
		this.versionOrder = versionOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	public int getVersionOrder() {
		return versionOrder;
	}

	public void setVersionOrder(int versionOrder) {
		this.versionOrder = versionOrder;
	}
	
	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
	}



}