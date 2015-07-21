package controllers;

import java.util.ArrayList;
import java.util.List;

import model.MainModel;
import model.Module;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	private static MainModel model;

	private Application() {
		model = new MainModel();
	}

	public static Result index() {
		return ok(index.render(generateListWithAllResults()));
	}

	/**
	 * 
	 * @return
	 */
	private static List<ModuleGroup> generateListWithAllResults() {
		List<ModuleGroup> allModulesAndResults = new ArrayList<ModuleGroup>();

		if (!model.getAllModules().isEmpty()) {
			for (Module m : model.getAllModules()) {
				allModulesAndResults
						.add(new ModuleGroup(m.getName(), m.getEcts(), model.getResultsForModule(m.getModuleID())));
			}
		}

		return allModulesAndResults;
	}

}
