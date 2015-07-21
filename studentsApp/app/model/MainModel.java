package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {

	private List<Module> modules;
	private List<ModuleResult> results;

	private int currentModuleID;
	private int currentResultID;

	public MainModel() {
		modules = new LinkedList<>();
		results = new LinkedList<>();

		currentModuleID = 0;
		currentResultID = 0;
	}

	public void addModule(String name, int ects) {
		modules.add(new Module(currentModuleID, name, ects));
		currentModuleID++;
		setChanged();
		notifyObservers();
	}

	public void addResult(String name, int ects, float grade, int moduleID) {
		results.add(new ModuleResult(currentResultID, name, ects, grade, moduleID));
		currentResultID++;
		setChanged();
		notifyObservers();
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
