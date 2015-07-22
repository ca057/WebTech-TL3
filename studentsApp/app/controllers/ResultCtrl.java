package controllers;

import models.ExamResult;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ResultCtrl extends Controller {

	static Form<ExamResult> resultForm = Form.form(ExamResult.class);

	public ResultCtrl() {
		// TODO Auto-generated constructor stub
	}

	public static Result examResults() {
		return TODO;
	}

	public static Result newResult() {
		return ok(views.html.addresult.render(""));
	}

	public static Result changeResult() {
		return ok(views.html.editresult.render());
	}

	public static Result addResult() {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		String name = dynamicForm.get("name");
		String module = dynamicForm.get("module");
		int ects = 0;
		// check if ects is number
		try {
			ects = Integer.parseInt(dynamicForm.get("ects"));
		} catch (NumberFormatException e) {
			return ok(views.html.addmodule
					.render("Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden."));
		}
		// check if grade is number
		float grade = 0;
		try {
			grade = Float.valueOf(dynamicForm.get("grade"));
		} catch (NumberFormatException e) {
			return ok(views.html.addmodule
					.render("Die eingegebene Note ist keine Zahl und konnte nicht verarbeitet werden."));
		}
		// check if grade and ects are larger than 0
		if (ects <= 0 || grade <= 0) {
			return ok(
					views.html.addmodule.render("Die eingegebenen ECTS-Punkte oder die Note muss größer als 0 sein."));
		} else {
			// ExamResult.create(new ExamResult(module, name, ects, grade));
			return Application.index();
		}
	}

	public static Result editResult(int id) {
		return TODO;
	}

	public static Result deleteResult(int id) {
		return TODO;
	}

}
