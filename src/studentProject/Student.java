package studentProject;

import java.util.Objects;

public class Student implements Comparable<Student> {
	public static final int TOTAL_COUNT = 3;
	private int id;
	private String name;
	private String gender;
	private int age;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private double avg;
	private String grade;

	public Student() {
	}

	public Student(String name, String gender, int age, int total, double avg, String grade) {
		this(0, name, gender, age, 0, 0, 0, total, avg, grade);
	}

	public Student(String name, String gender, int age, int kor, int eng, int math) {
		this(0, name, gender, age, kor, eng, math, 0, 0.0, null);
	}

	public Student(int id, String name, String gender, int age, int kor, int eng, int math, int total, double avg,
			String grade) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.total = total;
		this.avg = avg;
		this.grade = grade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.age, this.name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Student) {
			Student stu = (Student) obj;
			return (this.age == stu.age) && (this.name.equals(stu.name));
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getTotal() {
		return total;
	}

	public double getAvg() {
		String data = String.format("%.2f", this.avg);
		return Double.parseDouble(data);
	}

	public String getGrade() {
		return grade;
	}

	public void calToal() {
		this.total = this.kor + this.eng + this.math;
	}

	public void calAvg() {
		this.avg = total / (double) Student.TOTAL_COUNT;
	}

	public void calGrade() {
		switch ((int) (this.avg / 10)) {
		case 10:
			
		case 9:
			this.grade = "A";
			break;
		case 8:
			this.grade = "B";
			break;
		case 7:
			this.grade = "C";
			break;
		case 6:
			this.grade = "D";
			break;
		default:
			this.grade = "F";
			break;
		}
	}

	@Override
	public String toString() {
		return id + "\t" + name + "\t" + gender + "\t" + age + "\t" + kor + "\t" + eng + "\t" + math + "\t" + total
				+ "\t" + String.format("%.2f", avg) + "\t" + grade;

	}

	@Override
	public int compareTo(Student o) {
		if ((this.total - o.total) == 0) {
			return 0;
		} else if ((this.total - o.total) > 0) {
			return 1;
		} else {
			return -1;
		}
	}
}
