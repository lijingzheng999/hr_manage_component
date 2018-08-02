package hr.manage.component.common.constants;

public enum DepartmentConstants {
	
	
	CP (5, "产品事业部"),
	 
	SC (6, "市场技术部"),

	XY (7, "系统运维部"),

	XJ (8, "系统集成部"),

	ZQ (9, "政企支撑中心");

	
	private int id;

	private String content;
	
	private DepartmentConstants(int id, String content) {
		this.id = id;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static  String seletCode(String content) {
		for (DepartmentConstants department : DepartmentConstants.values()) {
			if(department.getContent().equals(content)){
				return department.name();
			}
		}
		return "";
	}
}
