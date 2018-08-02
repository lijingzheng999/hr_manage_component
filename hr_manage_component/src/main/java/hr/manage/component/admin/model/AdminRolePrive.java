package hr.manage.component.admin.model;

import java.io.Serializable;

public class AdminRolePrive implements Serializable{

	private static final long serialVersionUID = 9035107375678009630L;

	private int roleid;
	
	private String m;
	
	private String c;
	
	private String a;

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
}
