
package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * This class represents an exam result.
 * 
 * @author Simon
 *
 */
@SuppressWarnings("serial")
@Entity
public class ExamResult extends Model {
	/**
	 * The ID of the exam result. The column's name in the database is
	 * accordingly set as "RESULT_ID".
	 */
	@Id
	@Column(name = "RESULT_ID")
	public Integer id;
	/**
	 * The name attribute of the exam result. Must not be <code>null</code>.
	 */
	@Required
	public String name;
	/**
	 * The number of ECTS associated with this module group. Must not be
	 * <code>null</code> or less than zero.
	 */
	@Required
	public int ects;
	/**
	 * The grade granted to this exam. Must not be smaller or equal to zero or
	 * greater than five.
	 */
	@Required
	public float grade;
	/**
	 * The single module group associated with this exam result. Must not be
	 * <code>null</code>. If exam result is removed, module group will be
	 * updated.
	 */
	@Required
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "MODULE_ID")
	public Module module;

	/**
	 * Constructor of {@code ExamResult}.
	 * 
	 * @param module
	 *            Associated module. Must not be <code>null</code>.
	 * @param name
	 *            Name of the exam. Must not be <code>null</code>.
	 * @param ects
	 *            Number of ECTS associated with this module. Must not be
	 *            <code>null</code> or smaller than zero.
	 * @param grade
	 *            Grade granted for this exam. Must not be <code>null</code>,
	 *            smaller equal to zero or greater than five.
	 */
	public ExamResult(Module module, String name, int ects, float grade) {
		Integer id = 0;
		for (ExamResult result : all()) {
			id = Math.max(id, result.getID() + 1);
		}
		this.id = id;
		if (module != null) {
			this.module = module;
		} else {
			throw new IllegalArgumentException("ExamResult could not be initiated, Module not set");
		}

		setName(name);
		setEcts(ects);
		setGrade(grade);
	}

	public int getID() {
		return id;
	}

	public Module getModule() {
		return module;
	}

	public String getName() {
		return name;
	}

	public int getEcts() {
		return ects;
	}

	public float getGrade() {
		return grade;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	/**
	 * @param name
	 *            Name of the exam result. Must not be <code>null</code>.
	 */
	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Illegal parameters for  name");
		}
		this.name = name;

	}

	/**
	 * @param ects
	 *            Number of ECTS associated with this exam result. Must not be
	 *            <code>null</code> or smaller than zero.
	 */
	public void setEcts(int ects) {
		if (ects < 0) {
			throw new IllegalArgumentException("Illegal parameters for ects");
		}
		this.ects = ects;

	}

	/**
	 * @param grade
	 *            Grade granted for this exam. Must not be <code>null</code>,
	 *            smaller equal to zero or greater than five.
	 */
	public void setGrade(float grade) {
		if (grade <= 0 || grade > 5.0) {
			throw new IllegalArgumentException("Illegal arguments for grade.");
		}
		this.grade = grade;

	}

	/**
	 * Get the average grade determined by all results.
	 * 
	 * <p>
	 * The total average grade is determined as follows:
	 * <p>
	 * The sum of all grades multiplied by their respective number of ECTS is
	 * divided by the overall number of ECTS.
	 * 
	 * @return The total average grade as {@code Float}, or <code>0</code> if no
	 *         ECTS were achieved yet.
	 */
	public static Float getTotalAverageGrade() {
		float sumOfGradeTimesEcts = 0;
		float sumOfEcts = 0;

		for (ExamResult result : all()) {
			sumOfGradeTimesEcts += result.getGrade() * result.getEcts();
			sumOfEcts += result.getEcts();
		}

		return (sumOfEcts != 0) ? sumOfGradeTimesEcts / sumOfEcts : 0;
	}

	public static Finder<Integer, ExamResult> find = new Finder<>(Integer.class, ExamResult.class);

	/**
	 * @return A complete {@code List} of all {@code ExamResult}s in database.
	 */
	public static List<ExamResult> all() {
		return find.all();
	}

	/**
	 * @param id
	 * @return A {@code ExamResult} specified by its ID.
	 */
	public static ExamResult getExamResultById(Integer id) {
		return find.ref(id);
	}

	/**
	 * Create an {@code ExamResult} in database. The ExamResult will also be
	 * associated with its {@code Module} automatically.
	 * 
	 * @param result
	 *            The {@code ExamResult} to add. Must not be <code>null</code>.
	 */
	public static void create(ExamResult result) {
		System.out.println(result);
		if (result != null) {
			result.getModule().addExamResult(result);
			result.save();
		} else {
			throw new IllegalArgumentException("Result could not be added.");
		}
	}

	/**
	 * Delete a specific {@code ExamResult} from database. Reference on this
	 * ExamResult by {@code Module} will also be removed.
	 * 
	 * @param id
	 *            The ID of the {@code ExamResult} to delete from database.
	 */
	public static void delete(Integer id) {
		find.ref(id).getModule().removeExamResult(find.ref(id));
		find.ref(id).delete();
	}

	@Override
	public String toString() {
		return "ExamResult [id=" + id + ", name=" + name + ", ects=" + ects + ", grade=" + grade + ", module=" + module
				+ "]";
	}

}
