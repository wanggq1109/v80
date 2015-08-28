package com.arthouse.common.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 工作室信息介绍
 * @author wanggq
 *
 */
@javax.persistence.Entity
@Table(name = "web_studioInfo")
public class StudioInfo {
	
	private Long id;
	
	private String content;
	
	
	private Set<StudioPhoto>  studioPhotoCollection = new  HashSet<StudioPhoto>();
	
	private Set<Employee>  employeePhotoCollection = new  HashSet<Employee>();
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Lob 
    @Basic(fetch = FetchType.LAZY) 
    @Column(name="Content", columnDefinition="LongText", nullable=true)
	public String getContent() {
		return content;
	}
	
	@OneToMany(mappedBy = "studioInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	public Set<StudioPhoto> getStudioPhotoCollection() {
		return studioPhotoCollection;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public void setStudioPhotoCollection(Set<StudioPhoto> studioPhotoCollection) {
		this.studioPhotoCollection = studioPhotoCollection;
	}

	@OneToMany(mappedBy = "studioInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	public Set<Employee> getEmployeePhotoCollection() {
		return employeePhotoCollection;
	}

	public void setEmployeePhotoCollection(Set<Employee> employeePhotoCollection) {
		this.employeePhotoCollection = employeePhotoCollection;
	}

	
	

}
