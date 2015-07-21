package model;

import java.util.LinkedList;
import java.util.List;

public class MainModel {

	List<Module> modules;
	List<Result> results;

	public MainModel() {

	}

	public void addModule(int moduleID, String name, int ects) {
		modules.add(new Module(moduleID, name, ects));
	}

	public void addResult(int resultID, String name, int ects, float grade, int moduleID) {
		results.add(new Result(resultID, name, ects, grade, moduleID));
	}

	public List<Module> getAllModules() {
		return modules;
	}

	public List<Result> getAllResults() {
		return results;
	}

	public List<Result> getResultsForModule(int moduleID) {
		List<Result> resultsWithModuleID = new LinkedList<>();

		for (Result result : results) {
			if (result.getResultID() == moduleID) {
				resultsWithModuleID.add(result);
			}
		}

		return resultsWithModuleID;
	}

	public float getAverageGradeOfModule(int moduleID) {
		List<Result> resultsInModule = getResultsForModule(moduleID);

		float sumOfGradeTimesEcts = 0;
		float sumOfEcts = 0;

		for (Result result : resultsInModule) {
			sumOfGradeTimesEcts += result.getGrade() * result.getEcts();
			sumOfEcts += result.getEcts();
		}

		return (sumOfEcts != 0) ? sumOfGradeTimesEcts / sumOfEcts : 0;
	}

	public float getTotalAverageGrade() {
		float sumOfGradeTimesEcts = 0;
		float sumOfEcts = 0;

		for (Result result : results) {
			sumOfGradeTimesEcts += result.getGrade() * result.getEcts();
			sumOfEcts += result.getEcts();
		}

		return (sumOfEcts != 0) ? sumOfGradeTimesEcts / sumOfEcts : 0;
	}

}
