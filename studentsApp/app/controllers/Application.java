package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Module;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * 
 * @author ca
 *
 */
public class Application extends Controller {

	/**
	 * 
	 * @return
	 */
	public static Result index() {
		return ok(views.html.index.render());
	}

	/**
	 * 
	 * @return
	 */
	private static List<Module> generateListWithAllResults() {
		List<Module> allModulesAndResults = new ArrayList<Module>();

		return allModulesAndResults;
	}

}
