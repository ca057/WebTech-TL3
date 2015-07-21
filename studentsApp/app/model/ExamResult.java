package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class ExamResult extends Model {

	@Id
	@Column(name = "RESULT_ID")
	private Integer id;

	@Required
	private String name;

	@Required
	private int ects;

	@Required
	private float grade;

	@Required
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODULE_ID")
	private Module module;

	public ExamResult(int resultID, Module module, String name, int ects, float grade) {
		this.id = resultID;
		this.module = module;

		setName(name);
		setEcts(ects);
		setGrade(grade);
	}

	public int getResultID() {
		return id;
	}

	public Module getModule() {
		return module;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		if (grade <= 0 || grade > 5.0) {
			throw new IllegalArgumentException("Illegal arguments for grade.");
		}
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Illegal parameters for  name");
		}
		this.name = name;
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
	public Float getTotalAverageGrade() {
		float sumOfGradeTimesEcts = 0;
		float sumOfEcts = 0;

		for (ExamResult result : all()) {
			sumOfGradeTimesEcts += result.getGrade() * result.getEcts();
			sumOfEcts += result.getEcts();
		}

		return (sumOfEcts != 0) ? sumOfGradeTimesEcts / sumOfEcts : 0;
	}

	public static Finder<Integer, ExamResult> find = new Finder<>(Integer.class, ExamResult.class);

	public static List<ExamResult> all() {
		return find.all();
	}

	public static void create(ExamResult result) {
		result.save();
	}

	public static void delete(Integer id) {
		find.ref(id).delete();
	}
}
