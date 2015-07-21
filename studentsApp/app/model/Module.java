package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * This class represents a module group.
 * 
 * @author Simon
 *
 */
@SuppressWarnings("serial")
@Entity
public class Module extends Model {
	/**
	 * The ID of the module group. The column's name in the database is
	 * accordingly set as "MODULE_ID".
	 */
	@Id
	@Column(name = "MODULE_ID")
	private Integer id;
	/**
	 * The name attribute of the module group. Must not be <code>null</code>.
	 */
	@Required
	private String name;
	/**
	 * The number of ECTS associated with this module group. Must not be
	 * <code>null</code> or less than zero.
	 */
	@Required
	private int ects;
	/**
	 * With a module group, a list of exam results are associated. Can be
	 * <code>null</code>. If module group is removed, associated exam results
	 * will also be removed.
	 */
	@OneToMany(mappedBy = "module", cascade = CascadeType.REMOVE)
	private List<ExamResult> results;

	public Module(String name, int ects) {
		Integer id = 0;
		for (Module module : all()) {
			id = Math.max(id, module.getID());
		}
		this.id = id;

		setName(name);
		setEcts(ects);
	}

	public int getID() {
		return id;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		if (ects < 0) {
			throw new IllegalArgumentException("Illegal parameters for ects");
		}
		this.ects = ects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Illegal parameters for name");
		}
		this.name = name;
	}

	public void addModuleResult(ExamResult result) {
		this.results.add(result);
	}

	/**
	 * Get the average grade for results in this module.
	 * 
	 * <p>
	 * The average grade is determined as follows:
	 * <p>
	 * The sum of all grades multiplied by their respective number of ECTS is
	 * divided by the overall number of ECTS.
	 * 
	 * @return The average grade as {@code Float}, or <code>0</code> if no ECTS
	 *         were achieved yet.
	 * @return
	 */
	public Float getAverageGradeOfModule() {
		float sumOfGradeTimesEcts = 0;
		float sumOfEcts = 0;

		for (ExamResult result : this.results) {
			sumOfGradeTimesEcts += result.getGrade() * result.getEcts();
			sumOfEcts += result.getEcts();
		}

		return (sumOfEcts != 0) ? sumOfGradeTimesEcts / sumOfEcts : 0;
	}

	public static Finder<Integer, Module> find = new Finder<Integer, Module>(Integer.class, Module.class);

	public static List<Module> all() {
		return find.all();
	}

	public static void create(Module module) {
		module.save();
	}

	public static void delete(Integer id) {
		find.ref(id).delete();
	}
}
