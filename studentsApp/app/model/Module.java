package model;

import javax.annotation.CheckForNull;
import javax.persistence.Basic;
import javax.persistence.Id;

public class Module {

	@Id
	@CheckForNull
	private int moduleID;

	@Basic
	@CheckForNull
	private String name;

	@Basic
	@CheckForNull
	private int ects;

	public Module(int moduleID, String name, int ects) {
		setName(name);
		setEcts(ects);
		this.moduleID = moduleID;
		this.name = name;
		this.ects = ects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Illegal parameters for  name");
		}
		this.name = name;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		if (ects < 0) {
			throw new IllegalArgumentException("Illegal parameters for ects");
		}
		this.ects = ects;
	}

	public int getModuleID() {
		return moduleID;
	}
}
