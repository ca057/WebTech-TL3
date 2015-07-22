package controllers;

import models.ExamResult;
import models.Module;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ResultCtrl extends Controller {

	static Form<ExamResult> resultForm = Form.form(ExamResult.class);

	public ResultCtrl() {
		// TODO Auto-generated constructor stub
	}

	public static Result newResult() {
		return ok(views.html.addresult.render(""));
	}

	public static Result changeResult() {
		return ok(views.html.changeresult.render(""));
	}

	public static Result selectResultEditingMode() {
		return TODO;
	}

	public static Result addResult() {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		String name = dynamicForm.get("name");
		int moduleId = Integer.parseInt(dynamicForm.get("module"));
		int ects = 0;
		// check if ects is number
		System.out.println("1");
		try {
			ects = Integer.parseInt(dynamicForm.get("ects"));
		} catch (NumberFormatException e) {
			return ok(views.html.addresult
					.render("Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden."));
		}
		// check if grade is number
		System.out.println("2");
		float grade = 0;
		try {
			grade = Float.valueOf(dynamicForm.get("grade"));
		} catch (NumberFormatException e) {
			return ok(views.html.addresult
					.render("Die eingegebene Note ist keine Zahl und konnte nicht verarbeitet werden."));
		}
		// check if grade and ects are larger than 0
		System.out.println("3");
		if (ects < 0 || grade <= 0 || grade >= 5) {
			return ok(views.html.addresult.render(
					"Die eingegebenen ECTS-Punkte dürfen nicht negativ und die Note muss zwischen 0 und 5 sein."));
		} else {
			ExamResult.create(new ExamResult(Module.getModuleById(moduleId), name, ects, grade));
			System.out.println("finished");
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
