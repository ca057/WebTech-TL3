package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * The main application controller.
 * 
 * @author Christian
 */
public class Application extends Controller {

	/**
	 * The start page is rendered and returned.
	 * 
	 * @return the rendered start page.
	 */
	public static Result index() {
		return ok(views.html.index.render());
	}
}
