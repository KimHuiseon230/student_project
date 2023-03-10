package studentProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentMain {
	public static Scanner sc = new Scanner(System.in);

	public static final int INPUT = 1, PRINT = 2, ANALYZE = 3, EARCH = 4, PUDATE = 5, SORT = 6, DELETE = 7, EXIT = 8;

	public static void main(String[] args) {

		ArrayList<Student> list = new ArrayList<Student>();
		DBConnection dbCon = new DBConnection();
		boolean run = true;
		int no = 0;

		while (run) {
			System.out.println("+==================================[ ���� ó�� ���α׷� ]==================================+");
			System.out.println("|	        1.�Է� | 2.��� | 3.�м� | 4.�˻� | 5.���� | 6.���� | 7.���� | 8.����           |");
			System.out
					.println("+=====================================================================================+");
			System.out.print("�Է� >> ");
			no = Integer.parseInt(sc.nextLine());
			//
			switch (no) {
			case INPUT:
				// �����ڸ� �����ؾ���. �̸�, ����,����,����, ����
				Student student = inputDataStudent();
				// DB�Է�
				int rValue = dbCon.insert(student);
				if (rValue == 1) {
					System.out.println("���Լ���");
				} else {
					System.err.println("���Խ���");
				}
				break;
			case PRINT:
				ArrayList<Student> list2 = dbCon.select();
				if (list2 == null) {
					System.out.println("���� ����!");
				} else {
					printStudent(list2);
				}
				break;
			case ANALYZE:
				ArrayList<Student> list3 = dbCon.analizeSelect();
				if (list3 == null) {
					System.out.println("���� ����!");
				} else {
					analyzeStudent(list3);
				}
				break;
			case EARCH:
				String name = searchStudent();
				ArrayList<Student> list4 = dbCon.nameSearch(name);
				if (list4.size() >= 1) {
					printStudent(list4);
				} else {
					System.err.println("�л��̸� �˻�����");
				}
				break;
			case PUDATE:
				int updateRetrunValue = 0;
				int id = inputId(); // id�� ���ؼ� ��
				Student stu = dbCon.selectId(id);
				if (stu == null) {
					System.out.println("��������");
				} else {
					Student updateStudent = updataeStudent(stu);
					updateRetrunValue = dbCon.update(updateStudent);
				}
				if (updateRetrunValue == 1) {
					System.out.println("UPDATE ����");
				} else {
					System.err.println("UPDATE ����");
				}
				break;
			case SORT:
				ArrayList<Student> list5 = dbCon.selectSort();
				if (list5 == null) {
					System.err.println("���� ����");
				} else {
					printScoreSort(list5);
				}
				break;
			case DELETE:
				int delete = inputId();
				int deleteReurnValue = dbCon.delete(delete);
				if (deleteReurnValue == 1) {
					System.err.println("��������");
				} else {
					System.out.println("���� ����");
				}
				break;
			case EXIT:
				run = false;
				break;
			}
		} // end of while
		System.err.println("!system : ���α׷��� �����մϴ�.");
	}

	private static int inputId() {
		boolean run = true;
		int id = 0;
		while (run) {
			try {
				System.out.print("ID �Է� : ");
				id = Integer.parseInt(sc.nextLine());
				if (id > 0 && id < Integer.MAX_VALUE) {
					run = false;
				}
			} catch (NullPointerException e) {
				System.err.println("id����");
			}
		}
		return id;
	}

	// ���������� �����Ϸ���
	private static void printScoreSort(ArrayList<Student> list) {
		Collections.sort(list, Collections.reverseOrder());
		System.out.println("���" + "\t" + "id" + "\t" + "�̸�" + "\t" + "����" + "\t" + "����" + "\t" + "����" + "\t" + "����"
				+ "\t" + "����" + "\t" + "����" + "\t" + "���" + "\t" + "���");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + 1 + "��" + "\t" + list.get(i).toString());
		}
	}

	private static void deleteStudent(ArrayList<Student> list) {
		String name = nameMatchPattern();
		boolean flag = false;
		try {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals(name)) {
					list.remove(i);
					System.err.println("system : ���������� [" + name + "] �� �����Ǿ����ϴ�.");
					flag = true;
					break;
				}

			}
		} catch (Exception e) {
			if (flag == false) {
				System.err.println("�ش� �л��� ã�� �� �����ϴ�.");
			}
		}
	}

	private static String searchStudent() {
		String name = null;
		name = nameMatchPattern();
		return name;
	}

	private static Student updataeStudent(Student student) {
		int kor = inputscoreSubject(student.getName(), "����", student.getKor());
		student.setKor(kor);
		int eng = inputscoreSubject(student.getName(), "����", student.getEng());
		student.setEng(eng);
		int math = inputscoreSubject(student.getName(), "����", student.getMath());
		student.setMath(math);
		student.calToal();
		student.calAvg();
		student.calGrade();
		System.out.println("�����Ϸ�");
		return student;

	} // end

	private static int inputscoreSubject(String subject, String name, int score) {
		boolean run = true;
		int data = 0;
		while (run) {
			System.out.print(subject + " " + name + " " + score + "��" + " >> ");
			try {
				data = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(data));
				if (matcher.find() && data >= 0 && data <= 100) {
					break;
				} else {
					System.err.println("������ ������ϴ�. �ٽ� �����ϰڽ��ϴ�.");
				}
			} catch (Exception e) {
				System.err.println("���� �Է¿� ������ �߻��Ͽ����ϴ�.");
				data = 0;
			}
		}
		return data;
	}

	private static String nameMatchPattern() {
		String name = null;
		while (true) {
			try {
				System.out.print("�̸� >> ");
				name = sc.nextLine();
				Pattern pattern = Pattern.compile("^[��-�R]{1,3}$");
				Matcher matcher = pattern.matcher(name);
				if (matcher.find()) {
					break;
				} else {
					System.err.println("������ �ǹٸ��� �ʽ��ϴ�. �ٽ� �Է����ֽʽÿ�");
				}
			} catch (Exception e) {
				System.err.println("������ �ǹٸ��� �ʽ��ϴ�. �ٽ� �Է����ֽʽÿ�");
			}
		}
		return name;
	}

	private static void analyzeStudent(ArrayList<Student> list) {
		System.out.println("id" + "\t" + "�̸�" + "\t" + "����" + "\t" + "���" + "\t" + "���");
		for (Student data : list) {
			System.out.println(data.getId() + "\t" + data.getName() + "\t" + data.getTotal() + "\t"
					+ String.format("%.2f", data.getAvg()) + "\t" + data.getGrade());
		}
	}

	private static void printStudent(ArrayList<Student> list) {
		System.out.println("id" + "\t" + "�̸�" + "\t" + "����" + "\t" + "����" + "\t" + "����" + "\t" + "����" + "\t" + "����"
				+ "\t" + "����" + "\t" + "���" + "\t" + "���");
		for (Student data : list) {
			System.out.println(data);
		} // for
	}

	private static Student inputDataStudent() {
		String name = nameMatchPattern();
		String gender = inputgGender();
		int age = inputAge();
		int kor = inputScore("����");
		int eng = inputScore("����");
		int math = inputScore("����");
		Student student = new Student(name, gender, age, kor, eng, math);
		student.calToal();
		student.calAvg();
		student.calGrade();
		return student;
	}

	private static String inputgGender() {

		String gender = null;
		boolean flag = false;
		for (; true;) {
			try {
				System.out.print("����(����/����) >> ");
				gender = sc.nextLine();
				Pattern pattern = Pattern.compile("^[��-�R]{2}$");
				Matcher matcher = pattern.matcher(gender); // (genderNum == 1) ? "����" : "����";
				if (matcher.find() && gender.equals("����")||gender.equals("����")) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("������ ���� �ʽ��ϴ�.");
				}

			} catch (Exception e) {
				System.err.println("������ ���� �ʽ��ϴ�.");
			}
		}
		return gender;
	}

	private static int inputScore(String sub) {
		int score = 0;
		boolean flag = false;
		while (true) {
			try {
				System.out.print(sub + " �Է� >> ");
				score = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(score));
				if (matcher.find() && score <= 100) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("system: �ش� ������ �������� �ʽ��ϴ�.");
				}

			} catch (Exception e) {
				System.err.println("system: �ش� ������ �������� �ʽ��ϴ�.");
			}
		}
		return score;
	}

	private static int inputAge() {
		int age = 0;
		boolean flag = false;

		while (true) {
			try {
				Pattern pattern = Pattern.compile("^[0-9]{2}$");
				System.out.print("���� >> ");
				age = Integer.parseInt(sc.nextLine());
				Matcher matcher = pattern.matcher(String.valueOf(age));
				if (matcher.find() && age <= 100) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("system: �ش� ���̿� �������� �ʽ��ϴ�.");
				}
			} catch (Exception e) {
				System.err.println("system: �ش� ���̿� �������� �ʽ��ϴ�.");
			}
		}
		return age;
	}

}