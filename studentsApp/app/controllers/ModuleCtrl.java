package controllers;

import models.Module;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * The controller for all actions associated with a {@link Module}.
 * 
 * @author Christian
 *
 */
public class ModuleCtrl extends Controller {

	/**
	 * If a new module should be created, this method returns the page for
	 * adding new modules.
	 * 
	 * @return the rendered view for adding a new module
	 */
	public static Result newModule() {
		return ok(views.html.addmodule.render(""));
	}

	/**
	 * Evaluates the form for adding a new module. If all input is valid, the
	 * new {@link Module} is created.
	 * 
	 * @return the main page if module could be added, the page for adding a new
	 *         module with a specific warning otherwise
	 */
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

	/**
	 * Shows the view for selecting, which action will be performed next.
	 * 
	 * @return the page for selecting the next action
	 */
	public static Result changeModule() {
		return ok(views.html.changemodule.render(""));
	}

	/**
	 * Evaluates the selection of the next action, options are editing or
	 * deleting a module. If the selection could not be evaluated, the page for
	 * editing a module is shown again with a specific error message.
	 * 
	 * @return the page for editing or deleting if the selection could be
	 *         evaluated, the page for selecting the next action with a specific
	 *         error warning otherwise
	 */
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
				.render("Die Eingabe konnte nicht verarbeitet werden, eine neue Auswahl muss getroffen werden."));
	}

	/**
	 * Evaluates the form for editing a new module. Every value is checked and,
	 * if valid, set. If an error or invalid input occurs, the page for editing
	 * the module is shown again, a specific error message is added. Otherwise
	 * the main page is shown after editing is finished.
	 * 
	 * @return the main page if everything went good or the page for editing
	 *         with a specific error message if an error occurs
	 */
	public static Result editModule() {
		DynamicForm form = Form.form().bindFromRequest();
		Module module = Module.getModuleById(Integer.parseInt(form.get("editModule")));
		String name = form.get("name");
		if (form.get("name").isEmpty()) {
			name = module.getName();
		}
		module.setName(name);

		int ects = 0;
		if (!form.get("ects").isEmpty()) {
			try {
				ects = Integer.parseInt(form.get("ects"));
				if (ects < 0) {
					return ok(views.html.editmodule.render("Die eingegebenen ECTS-Punkte dürfen nicht negativ sein.",
							module));
				}
				module.setEcts(ects);
			} catch (NumberFormatException e) {
				return ok(views.html.editmodule.render(
						"Die eingegebenen ECTS-Punkte sind keine Zahl und konnten nicht verarbeitet werden.", module));
			}
		}

		return Application.index();
	}

	/**
	 * If the user submits deleting the module, the {@link Module} is deleted.
	 * 
	 * @return the main page after deleting the module
	 */
	public static Result deleteModule() {
		DynamicForm form = Form.form().bindFromRequest();
		Module.delete(Integer.parseInt(form.get("delMod")));
		return Application.index();
	}

}
