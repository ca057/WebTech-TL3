package controllers;

import models.Module;
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

	public static Result addModule() {
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.get("name");
		int ects = 0;
		try {
			ects = Integer.parseInt(form.get("ects"));
		} catch (NumberFormatException e) {
			return ok(views.html.addmodule
					.render("Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden."));
		}

		if (ects < 0) {
			return ok(views.html.addmodule.render("Die eingegebenen ECTS-Punkte dürfen nicht negativ sein."));
		} else {
			Module.create(new Module(name, ects));
			return Application.index();
		}
	}

	public static Result changeModule() {
		return ok(views.html.changemodule.render(""));
	}

	public static Result selectModuleEditingMode() {
		DynamicForm form = Form.form().bindFromRequest();
		String value = form.get("moduleEditingMode");
		Module module = Module.getModuleById(Integer.parseInt(form.get("module")));
		if ("edit".equals(value)) {
			return ok(views.html.editmodule.render("", module));
		} else if ("delete".equals(value)) {
			return ok(views.html.deletemodule.render("", module));
		}
		return ok(views.html.changemodule
				.render("Die Eingabe konnte nicht verarbeitet werden, bitte treffe eine neue Auswahl."));
	}

	public static Result editModule() {
		DynamicForm form = Form.form().bindFromRequest();
		Module module = Module.getModuleById(Integer.parseInt(form.get("editModule")));
		String name = form.get("name");
		System.out.println(module.getName());
		System.out.println(form.get("name"));
		if (form.get("name").isEmpty()) {
			System.out.println(form.get("name"));
			name = module.getName();
		}
		module.setName(name);
		System.out.println(module.getName());

		int ects = 0;
		System.out.println(module.getEcts());
		System.out.println(form.get("ects"));
		if (!form.get("ects").isEmpty()) {
			try {
				ects = Integer.parseInt(form.get("ects"));
				if (ects < 0) {
					return ok(views.html.editmodule.render("Die eingegebenen ECTS-Punkte dürfen nicht negativ sein.",
							module));
				} else {
					module.setEcts(ects);
					System.out.println(module.getEcts());
				}
			} catch (NumberFormatException e) {
				return ok(views.html.editmodule.render(
						"Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden.", module));
			}
		}

		return Application.index();
	}

	public static Result deleteModule() {
		DynamicForm form = Form.form().bindFromRequest();
		String moduleId = form.get("delMod");
		if (!moduleId.isEmpty()) {
			Module.delete(Integer.parseInt(moduleId));
		} else {
			return ok(views.html.deletemodule.render(
					"Das Modul konnte nicht gelöscht werden, bitte treffe eine neue Auswahl.",
					Module.getModuleById(Integer.parseInt(moduleId))));
		}

		return Application.index();
	}

}
