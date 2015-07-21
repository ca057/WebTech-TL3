package controllers;

import java.util.ArrayList;
import java.util.List;

import model.ExamResult;

public class ModuleGroup {

	private String moduleName;

	private int ects;

	private int progress = 0;

	public List<ExamResult> allResults;

	ModuleGroup(String name, int ects) {
		if (name == null || name.isEmpty() || ects <= 0) {
			throw new IllegalArgumentException("The given values for the ModuleGroup are not valid.");
		}
		moduleName = name;
		this.ects = ects;
		allResults = new ArrayList<ExamResult>();
		progress = computeProgress();
	}

	ModuleGroup(String name, int ects, List<ExamResult> allResults) {
		if (name == null || name.isEmpty() || ects <= 0 || allResults == null || allResults.isEmpty()) {
			throw new IllegalArgumentException("The given values for the ModuleGroup are not valid.");
		}
		moduleName = name;
		this.ects = ects;
		this.allResults = allResults;
		progress = computeProgress();
	}

	public void setResultList(List<ExamResult> allResults) {
		if (allResults == null || allResults.isEmpty()) {
			throw new IllegalArgumentException("The given list with all results is not valid or contains no elements.");
		}
		this.allResults = allResults;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @return the ects
	 */
	public int getEcts() {
		return ects;
	}

	/**
	 * @return the progress
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * @return the allResults
	 */
	public List<ExamResult> getAllResults() {
		return allResults;
	}

	/**
	 * 
	 * @return the progress in percent
	 */
	private int computeProgress() {
		int tmp = 0;
		for (ExamResult mr : allResults) {
			tmp += mr.getEcts();
		}
		return (100 / ects) * tmp;
	}
}
