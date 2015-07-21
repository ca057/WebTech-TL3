package controllers;

import java.util.ArrayList;
import java.util.List;

import model.MainModel;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	private MainModel model;

	private Application() {
		model = new MainModel();
	}

	public static Result index() {
		return ok(index.render(generateListWithAllResults()));
	}

	private static List<ModuleGroup> generateListWithAllResults() {
		List<ModuleGroup> allModulesAndResults = new ArrayList<ModuleGroup>();

		return allModulesAndResults;
	}

}
