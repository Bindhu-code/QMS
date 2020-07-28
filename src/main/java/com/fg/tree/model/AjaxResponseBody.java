package com.fg.tree.model;

import java.io.Serializable;
import java.util.List;

public class AjaxResponseBody<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msg;

	private List<T> list;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "AjaxResponseBody [msg=" + msg + ", list=" + list + "]";
	}

}
