package com.arthouse.common.dto;

public class ResourcesDto {
	    private Long id;
	    private String resourceName;
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

		private String resourceDesc;
		private String resourceType;
		private String resourceString;
		private Boolean priority;

		//是否可用，0为不可用，1为可用。
		private Integer enabled;

		//是否是超级。0为不超级，1为超级。
		private Integer issys;

		private String module;

}
