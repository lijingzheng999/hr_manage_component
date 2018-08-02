package hr.manage.component.common.constants;

public enum UnitConstants {
	
	
	QT (10, "全通"),
	 
	BJ (11, "北京物联网"),

	CD (12, "成都物联网"),

	CQ (13, "重庆物联网"),

	BD (14, "百度");

	
	private int id;

	private String content;
	
	private UnitConstants(int id, String content) {
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

	public static String seletCode(String content) {
		for (UnitConstants unit : UnitConstants.values()) {
			if(unit.getContent().equals(content)){
				return unit.name();
			}
		}
		return "";
	}
}
