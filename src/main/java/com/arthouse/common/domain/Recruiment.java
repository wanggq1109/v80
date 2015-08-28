package com.arthouse.common.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 职位招聘管理
 * 
 * @author wanggq
 *
 */

@javax.persistence.Entity
@Table(name = "web_recruiment")
public class Recruiment {

	
	private Long id;
	
	//职位名称
	private String jobName;
	
	//职位内容描述
	private String intro;
	
	//名额
	private int sum;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getJobName() {
		return jobName;
	}
	
	@Lob 
    @Basic(fetch = FetchType.LAZY) 
    @Column(name="content", columnDefinition="LongText", nullable=true)
	public String getIntro() {
		return intro;
	}

	public int getSum() {
		return sum;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
	
}
