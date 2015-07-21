package model;

public class Module {

	private int moduleID;
	private String name;
	private int ects;

	public Module(int moduleID, String name, int ects) {
		this.moduleID = moduleID;
		this.name = name;
		this.ects = ects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public int getModuleID() {
		return moduleID;
	}
}
