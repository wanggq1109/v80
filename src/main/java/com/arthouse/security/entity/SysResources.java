package com.arthouse.security.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 资源
 * Created by IntelliJ IDEA.
 * User: barry.wang
 * Date: 11-6-7
 * Time: 下午5:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(uniqueConstraints = {}, name = "sys_resources")
public class SysResources implements Serializable{

    private Long id;
    private String resourceName;
	private String resourceDesc;
	private String resourceType;
	private String resourceString;
	private Boolean priority;

	//是否可用，0为不可用，1为可用。
	private Integer enabled;

	//是否是超级。0为不超级，1为超级。
	private Integer issys;

	private String module;
    
	private Set<SysAuthoritiesResources> pubAuthoritiesResourceses = new HashSet<SysAuthoritiesResources>();

	public SysResources() {
	}

	public SysResources(Long id) {
		this.id = id;
	}

	public SysResources( String resourceName,
			String resourceDesc, String resourceType, String resourceString,
			Boolean priority, Integer enabled, Integer issys, String module,
			Set pubAuthoritiesResourceses) {

		this.resourceName = resourceName;
		this.resourceDesc = resourceDesc;
		this.resourceType = resourceType;
		this.resourceString = resourceString;
		this.priority = priority;
		this.enabled = enabled;
		this.issys = issys;
		this.module = module;
		this.pubAuthoritiesResourceses = pubAuthoritiesResourceses;
	}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceString() {
        return resourceString;
    }

    public void setResourceString(String resourceString) {
        this.resourceString = resourceString;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getIssys() {
        return issys;
    }

    public void setIssys(Integer issys) {
        this.issys = issys;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @OneToMany(mappedBy = "sysResources",cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.LAZY)
    @OrderBy(value = "id ASC")
    public Set<SysAuthoritiesResources> getPubAuthoritiesResourceses() {
        return pubAuthoritiesResourceses;
    }

    public void setPubAuthoritiesResourceses(Set<SysAuthoritiesResources> pubAuthoritiesResourceses) {
        this.pubAuthoritiesResourceses = pubAuthoritiesResourceses;
    }
}
