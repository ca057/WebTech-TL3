package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ResultCtrl extends Controller {

	public ResultCtrl() {
		// TODO Auto-generated constructor stub
	}

	public static Result newResult() {
		return ok(views.html.addresult.render());
	}

	public static Result changeResult() {
		return ok(views.html.editresult.render());
	}

	public static Result addResult(int moduleid) {
		return TODO;
	}

	public static Result editResult(int id) {
		return TODO;
	}

	public static Result deleteResult(int id) {
		return TODO;
	}

}
