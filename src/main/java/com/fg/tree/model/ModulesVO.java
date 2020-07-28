package com.fg.tree.model;

import java.io.Serializable;

public class ModulesVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Level1;
	private String Level2;
	private String Level3;
	private String Level4;
	
	public ModulesVO(String level1,String level2,String level3,String level4) {
		this.Level1=level1;
		this.Level2=level2;
		this.Level3=level3;
		this.Level4=level4;
		
		
		
	}

	public String getLevel1() {
		return Level1;
	}
	public void setLevel1(String level1) {
		Level1 = level1;
	}
	public String getLevel2() {
		return Level2;
	}
	public void setLevel2(String level2) {
		Level2 = level2;
	}
	public String getLevel3() {
		return Level3;
	}
	public void setLevel3(String level3) {
		Level3 = level3;
	}
	public String getLevel4() {
		return Level4;
	}
	public void setLevel4(String level4) {
		Level4 = level4;
	}


	@Override
	public String toString() {
		return "ModulesVO [Level1=" + Level1 + ", Level2=" + Level2 + ", Level3=" + Level3 + ", Level4=" + Level4 + "]";
	}
	


}
