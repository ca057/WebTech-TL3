package model;

import java.util.LinkedList;
import java.util.List;

public class MainModel {

	List<Module> modules;
	List<ModuleResult> results;

	public MainModel() {

	}

	public void addModule(int moduleID, String name, int ects) {
		modules.add(new Module(moduleID, name, ects));
	}

	public void addResult(int resultID, String name, int ects, float grade, int moduleID) {
		results.add(new ModuleResult(resultID, name, ects, grade, moduleID));
	}

	public List<Module> getAllModules() {
		return modules;
	}

	public List<ModuleResult> getAllResults() {
		return results;
	}

	public List<ModuleResult> getResultsForModule(int moduleID) {
		List<ModuleResult> resultsWithModuleID = new LinkedList<>();

		for (ModuleResult result : results) {
			if (result.getResultID() == moduleID) {
				resultsWithModuleID.add(result);
			}
		}

		return resultsWithModuleID;
	}

	public float getAverageGradeOfModule(int moduleID) {
		List<ModuleResult> resultsInModule = getResultsForModule(moduleID);

		float sumOfGradeTimesEcts = 0;
		float sumOfEcts = 0;

		for (ModuleResult result : resultsInModule) {
			sumOfGradeTimesEcts += result.getGrade() * result.getEcts();
			sumOfEcts += result.getEcts();
		}

		return (sumOfEcts != 0) ? sumOfGradeTimesEcts / sumOfEcts : 0;
	}

	public float getTotalAverageGrade() {
		float sumOfGradeTimesEcts = 0;
		float sumOfEcts = 0;

		for (ModuleResult result : results) {
			sumOfGradeTimesEcts += result.getGrade() * result.getEcts();
			sumOfEcts += result.getEcts();
		}

		return (sumOfEcts != 0) ? sumOfGradeTimesEcts / sumOfEcts : 0;
	}

}
