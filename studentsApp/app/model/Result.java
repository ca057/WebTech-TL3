package model;

public class Result extends Module {
	int resultID;
	float grade;

	public Result(int resultID, String name, int ects, float grade, int moduleID) {
		super(moduleID, name, ects);
		this.resultID = resultID;
		this.grade = grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public float getGrade() {
		return grade;
	}

	public int getResultID() {
		return resultID;
	}
}
