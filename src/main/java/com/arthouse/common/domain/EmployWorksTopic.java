package com.arthouse.common.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 员工摄影主题作品
 * 
 * @author wanggq
 *
 */

@javax.persistence.Entity
@Table(name = "web_employworkstopic")
public class EmployWorksTopic {
	
	private Long id;
	
	private String name;
	
	private Employee employee;
	
	private Set<EmployeePhotos>  employeephotoscollection = new  HashSet<EmployeePhotos>();
	
	//默认为0；选中后为1
	private String status;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	@ManyToOne
	@JoinColumn(name = "employee_id")
	public Employee getEmployee() {
		return employee;
	}
	
	@OneToMany(mappedBy = "employworkstopic", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	public Set<EmployeePhotos> getEmployeephotoscollection() {
		return employeephotoscollection;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	

	public void setEmployeephotoscollection(
			Set<EmployeePhotos> employeephotoscollection) {
		this.employeephotoscollection = employeephotoscollection;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
