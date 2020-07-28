package com.fg.tree.model;

import java.io.Serializable;

public class TxnFolderDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rootName;

	private String hierarchy1;

	private String hierarchy2;

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getHierarchy1() {
		return hierarchy1;
	}

	public void setHierarchy1(String hierarchy1) {
		this.hierarchy1 = hierarchy1;
	}

	public String getHierarchy2() {
		return hierarchy2;
	}

	public void setHierarchy2(String hierarchy2) {
		this.hierarchy2 = hierarchy2;
	}

	@Override
	public String toString() {
		return "TxnFolderDetail [rootName=" + rootName + ", hierarchy1=" + hierarchy1 + ", hierarchy2=" + hierarchy2
				+ "]";
	}

}
