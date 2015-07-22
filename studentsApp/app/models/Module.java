package models;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	public Integer id;
	/**
	 * The name attribute of the module group. Must not be <code>null</code>.
	 */
	@Required
	public String name;
	/**
	 * The number of ECTS associated with this module group. Must not be
	 * <code>null</code> or less than zero.
	 */
	@Required
	public static int ects;
	/**
	 * A list of exam results associated with module group. Can be
	 * <code>null</code>. If module group is removed, associated exam results
	 * will also be removed.
	 */
	@OneToMany(mappedBy = "module", cascade = CascadeType.REMOVE)
	public static List<ExamResult> results;

	/**
	 * Constructor of {@code Module}.
	 * 
	 * @param name
	 *            Name of the module. Must not be <code>null</code>.
	 * @param ects
	 *            Number of ECTS associated with this module. Must not be
	 *            <code>null</code> or smaller than zero.
	 */
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

	public String getName() {
		return name;
	}

	public int getEcts() {
		return ects;
	}

	/**
	 * @param name
	 *            Name of the module. Must not be <code>null</code>.
	 */
	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Illegal parameters for name");
		}
		this.name = name;
	}

	/**
	 * @param ects
	 *            Number of ECTS associated with this module. Must not be
	 *            <code>null</code> or smaller than zero.
	 */
	public void setEcts(int ects) {
		if (ects < 0) {
			throw new IllegalArgumentException("Illegal parameters for ects");
		}
		this.ects = ects;
	}

	/**
	 * Adds a {@code ExamResult} to this {@code Module}.
	 * 
	 * @param result
	 *            An {@code ExamResult} to add to context. Must not be
	 *            <code>null</code>.
	 */
	public void addExamResult(ExamResult result) {
		if (result == null) {
			throw new IllegalArgumentException("Result could not be added to Module");
		}
		this.results.add(result);
	}

	protected void removeExamResult(ExamResult result) {
		if (result == null) {
			throw new IllegalArgumentException("Result could not be removed from Module");
		}
		this.results.remove(result);
	}

	/**
	 * @return A {@code List} of all {@code ExamResult}s associated with this
	 *         module.
	 */
	public List<ExamResult> getExamResults() {
		return results;
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

	/**
	 * @return The current progress on this module as {@code Float}.
	 */
	public static Float getModuleProgress() {
		int sumOfResultEcts = 0;

		for (ExamResult result : results) {
			sumOfResultEcts += result.getEcts();
		}

		float result = (sumOfResultEcts / ects) * 100;
		BigDecimal tmp = new BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN);
		result = tmp.floatValue();

		return result;
	}

	/**
	 * @return The current overall progress of the student as {@code Float}.
	 */
	public static Float getOverallProgress() {
		int sumOfModuleEcts = 0;
		int sumOfResultEcts = 0;

		for (Module module : all()) {
			sumOfModuleEcts += module.getEcts();
		}
		for (ExamResult result : ExamResult.all()) {
			sumOfResultEcts += result.getEcts();
		}

		float result = (sumOfModuleEcts != 0) ? (sumOfResultEcts / sumOfModuleEcts) * 100 : 0;
		BigDecimal tmp = new BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN);
		result = tmp.floatValue();

		return result;
	}

	public static Finder<Integer, Module> find = new Finder<Integer, Module>(Integer.class, Module.class);

	/**
	 * @return A complete {@code List} of all {@code Module}s in database.
	 */
	public static List<Module> all() {
		return find.all();
	}

	/**
	 * @param id
	 * @return A {@code Module} specified by its ID.
	 */
	public static Module getModuleById(Integer id) {
		return find.ref(id);
	}

	/**
	 * Create a {@code Module} in database.
	 * 
	 * @param module
	 *            The {@code Module} to add. Must not be <code>null</code>.
	 */
	public static void create(Module module) {
		if (module != null) {
			module.save();
		} else {
			throw new IllegalArgumentException("Module could not be added to database");
		}
	}

	/**
	 * Delete a specific {@code Module} from database.
	 * 
	 * <p>
	 * Warning: All associated {@code ExamResult}s will be removed also.
	 * 
	 * @param id
	 *            The ID of the {@code Module} to delete from database.
	 */
	public static void delete(Integer id) {
		find.ref(id).delete();
	}
}
