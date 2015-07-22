package controllers;

import models.ExamResult;
import models.Module;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ResultCtrl extends Controller {

	public static Result newResult() {
		return ok(views.html.addresult.render(""));
	}

	public static Result changeResult() {
		return ok(views.html.changeresult.render(""));
	}

	public static Result selectResultEditingMode() {
		DynamicForm form = Form.form().bindFromRequest();
		String value = form.get("resultEditingMode");
		ExamResult result = ExamResult.getExamResultById(Integer.parseInt(form.get("result")));
		if ("edit".equals(value)) {
			return ok(views.html.editresult.render("", result));
		} else if ("delete".equals(value)) {
			return ok(views.html.deleteresult.render("", result));
		}
		return ok(views.html.changeresult
				.render("Die Eingabe konnte nicht verarbeitet werden, eine neue Auswahl muss getroffen werden."));
	}

	public static Result addResult() {
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.get("name");
		int moduleId = Integer.parseInt(form.get("module"));
		int ects = 0;
		// check if ects is number
		try {
			ects = Integer.parseInt(form.get("ects"));
		} catch (NumberFormatException e) {
			return ok(views.html.addresult
					.render("Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden."));
		}
		// check if grade is number
		float grade = 0;
		try {
			grade = Float.valueOf(form.get("grade"));
		} catch (NumberFormatException e) {
			return ok(views.html.addresult
					.render("Die eingegebene Note ist keine Zahl und konnte nicht verarbeitet werden."));
		}
		// check if grade and ects are larger than 0
		if (ects < 0 || grade <= 0 || grade >= 5) {
			return ok(views.html.addresult.render(
					"Die eingegebenen ECTS-Punkte dürfen nicht negativ und die Note muss zwischen 0 und 5 sein."));
		} else {
			ExamResult.create(new ExamResult(Module.getModuleById(moduleId), name, ects, grade));
			return Application.index();
		}
	}

	public static Result editResult() {
		DynamicForm form = Form.form().bindFromRequest();
		ExamResult result = ExamResult.getExamResultById(Integer.parseInt(form.get("editResult")));
		String name = form.get("name");
		if (!name.isEmpty()) {
			result.setName(name);
		}

		if (!form.get("ects").isEmpty()) {
			try {
				int ects = Integer.parseInt(form.get("ects"));
				if (ects < 0) {
					return ok(views.html.editresult.render("Die eingegebenen ECTS-Punkte dürfen nicht kleiner 0 sein.",
							result));
				}
				result.setEcts(ects);
			} catch (NumberFormatException e) {
				return ok(views.html.editresult.render(
						"Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden.", result));
			}
		}

		if (!form.get("grade").isEmpty()) {
			try {
				float grade = Float.valueOf(form.get("grade"));
				if (grade <= 0 || grade >= 5) {
					return ok(views.html.editresult.render("Die eingegebenen ECTS-Punkte dürfen nicht kleiner 0 sein.",
							result));
				}
				result.setGrade(grade);
			} catch (NumberFormatException e) {
				return ok(views.html.editresult.render(
						"Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden.", result));
			}
		}
		return Application.index();

	}

	public static Result deleteResult() {
		DynamicForm form = Form.form().bindFromRequest();
		ExamResult.delete(Integer.parseInt(form.get("delRes")));
		return Application.index();

	}

}
