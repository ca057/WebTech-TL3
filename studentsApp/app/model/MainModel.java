package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {

	List<Module> modules;
	List<ModuleResult> results;

	public MainModel() {

	}

	public void addModule(int moduleID, String name, int ects) {
		modules.add(new Module(moduleID, name, ects));
		setChanged();
		notifyObservers();
	}

	public void addResult(int resultID, String name, int ects, float grade, int moduleID) {
		setChanged();
		notifyObservers();
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
