package controllers;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("", generateListWithAllResults()));
	}

	private static List<ModuleGroup> generateListWithAllResults() {
		List<ModuleGroup> allModulesAndResults = new ArrayList<ModuleGroup>();

		return allModulesAndResults;
	}

}
