package controllers;

import models.ExamResult;
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
		return ok(views.html.addresult.render());
	}

	public static Result changeResult() {
		return ok(views.html.editresult.render());
	}

	public static Result addResult() {
		Form<ExamResult> filledForm = resultForm.bindFromRequest();

		return TODO;
	}

	public static Result editResult(int id) {
		return TODO;
	}

	public static Result deleteResult(int id) {
		return TODO;
	}

}
