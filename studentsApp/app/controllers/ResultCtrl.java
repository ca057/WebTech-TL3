package controllers;

import models.ExamResult;
import models.Module;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controller for managing all actions associated with {@link ExamResult}s.
 *
 * @author Christian
 */
public class ResultCtrl extends Controller {

	/**
	 * The main page for adding new results is rendered and returned.
	 * 
	 * @return the rendered view for adding new results with status ok
	 */
	public static Result newResult() {
		return ok(views.html.addresult.render(""));
	}

	/**
	 * The page for selecting which action should be performed (edit or delete
	 * result) is rendered and returned.
	 * 
	 * @return the rendered view for selecting the next action
	 */
	public static Result changeResult() {
		return ok(views.html.changeresult.render(""));
	}

	/**
	 * Evaluates the form for selecting the next action. Possible options are
	 * editing or deleting a result.
	 * 
	 * @return the view for editing or deleting results, the selection view with
	 *         an error message if the form could not be evaluated
	 */
	public static Result selectResultEditingMode() {
		DynamicForm form = Form.form().bindFromRequest();
		ExamResult result = ExamResult.getExamResultById(Integer.parseInt(form.get("result")));
		if ("edit".equals(form.get("resultEditingMode"))) {
			return ok(views.html.editresult.render("", result));
		} else if ("delete".equals(form.get("resultEditingMode"))) {
			return ok(views.html.deleteresult.render("", result));
		}
		return ok(views.html.changeresult
				.render("Die Eingabe konnte nicht verarbeitet werden, eine neue Auswahl muss getroffen werden."));
	}

	/**
	 * Evaluates the form of adding a new result. If all inputs are valid, a new
	 * result is created. If any problem occurs, the view for adding results is
	 * shown again with a specific error message.
	 * 
	 * @return the start page if new result could be added, page for adding
	 *         results with error message otherwise
	 */
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

	/**
	 * Evaluates the form for editing results. Every valid input is added
	 * immediately, if an error occurs, the edit page is shown again with a
	 * specific warning.
	 * 
	 * @return the start page if new result could be added, page for editing
	 *         results otherwise
	 */
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

		if (!form.get("module").isEmpty()) {
			result.module = Module.getModuleById(Integer.parseInt(form.get("module")));
		}
		return Application.index();

	}

	/**
	 * If an user submits deleting a result, this method delegates the action to
	 * the {@link ExamResult}.
	 * 
	 * @return the main page after deleting the result
	 */
	public static Result deleteResult() {
		DynamicForm form = Form.form().bindFromRequest();
		ExamResult.delete(Integer.parseInt(form.get("delRes")));
		return Application.index();

	}

}
