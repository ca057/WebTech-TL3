package model;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;

@Entity
public class ModuleResult extends Module {

	@Id
	private int resultID;

	@Required
	private float grade;

	public ModuleResult(int resultID, String name, int ects, float grade, int moduleID) {
		super(moduleID, name, ects);
		setGrade(grade);
		this.resultID = resultID;
		this.grade = grade;
	}

	public void setGrade(float grade) {
		if (grade <= 0 || grade > 5.0) {
			throw new IllegalArgumentException("Illegal arguments for result.");
		}
		this.grade = grade;
	}

	public float getGrade() {
		return grade;
	}

	public int getResultID() {
		return resultID;
	}
}
