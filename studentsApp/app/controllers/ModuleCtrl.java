package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ModuleCtrl extends Controller {

	public ModuleCtrl() {
		// TODO Auto-generated constructor stub
	}

	public static Result newModule() {
		return ok(views.html.addmodule.render());
	}

	public static Result changeModule() {
		return ok(views.html.editmodule.render());
	}

	public static Result addModule() {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		System.out.println(dynamicForm.get("name"));
		System.out.println(dynamicForm.get("ects"));
		return Application.index();
	}

	public static Result editModule(long id) {
		return TODO;
	}

	public static Result deleteModule(int id) {
		return TODO;
	}

}
