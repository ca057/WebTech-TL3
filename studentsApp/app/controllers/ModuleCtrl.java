package controllers;

import model.Module;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ModuleCtrl extends Controller {

	static Form<Module> moduleForm = Form.form(Module.class);

	public ModuleCtrl() {
		// TODO Auto-generated constructor stub
	}

	public static Result modules() {
		return TODO;
	}

	public static Result newModule() {
		return ok(views.html.addmodule.render(""));
	}

	public static Result changeModule() {
		return ok(views.html.editmodule.render());
	}

	public static Result addModule() {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		String name = dynamicForm.get("name");
		int ects = 0;
		try {
			ects = Integer.parseInt(dynamicForm.get("ects"));
		} catch (NumberFormatException e) {
			return ok(views.html.addmodule
					.render("Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden."));
		}

		if (ects <= 0) {
			return ok(views.html.addmodule.render("Die eingegebenen ECTS-Punkte müssen größer als 0 sein."));
		} else {
			Module.create(new Module(name, ects));
			return Application.index();
		}
	}

	public static Result editModule(long id) {
		return TODO;
	}

	public static Result deleteModule(int id) {
		return TODO;
	}

}
