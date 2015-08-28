package com.arthouse.common.dao;

public interface Entity<IDClass extends java.io.Serializable> {

	public IDClass getId();

	public void setId(IDClass id);
}
