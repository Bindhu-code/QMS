package com.fg.tree.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mst_folder_mapping database table.
 * 
 */
@Entity
@Table(name="mst_folder_mapping")
@NamedQuery(name="MstFolderMapping.findAll", query="SELECT m FROM MstFolderMapping m")
public class MstFolderMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	private int parentid;

	public MstFolderMapping() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentid() {
		return this.parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

}