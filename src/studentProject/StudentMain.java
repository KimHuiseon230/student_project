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
			System.out.println("+==================================[ ¼ºÀû Ã³¸® ÇÁ·Î±×·¥ ]==================================+");
			System.out.println("|	        1.ÀÔ·Â | 2.Ãâ·Â | 3.ºÐ¼® | 4.°Ë»ö | 5.¼öÁ¤ | 6.¼øÀ§ | 7.»èÁ¦ | 8.Á¾·á           |");
			System.out
					.println("+=====================================================================================+");
			System.out.print("ÀÔ·Â >> ");
			no = Integer.parseInt(sc.nextLine());
			//
			switch (no) {
			case INPUT:
				// »ý¼ºÀÚ¸¦ ¼±ÅÃÇØ¾ßÇÔ. ÀÌ¸§, ³ªÀÌ,±¹¾î,¿µ¾î, ¼öÇÐ
				Student student = inputDataStudent();
				// DBÀÔ·Â
				int rValue = dbCon.insert(student);
				if (rValue == 1) {
					System.out.println("»ðÀÔ¼º°ø");
				} else {
					System.err.println("»ðÀÔ½ÇÆÐ");
				}
				break;
			case PRINT:
				ArrayList<Student> list2 = dbCon.select();
				if (list2 == null) {
					System.out.println("¼±ÅÃ ½ÇÆÐ!");
				} else {
					printStudent(list2);
				}
				break;
			case ANALYZE:
				ArrayList<Student> list3 = dbCon.analizeSelect();
				if (list3 == null) {
					System.out.println("¼±ÅÃ ½ÇÆÐ!");
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
					System.err.println("ÇÐ»ýÀÌ¸§ °Ë»ö¿À·ù");
				}
				break;
			case PUDATE:
				int updateRetrunValue = 0;
				int id = inputId(); // id¸¦ ÅëÇØ¼­ °ª
				Student stu = dbCon.selectId(id);
				if (stu == null) {
					System.out.println("¼öÁ¤¿À·ù");
				} else {
					Student updateStudent = updataeStudent(stu);
					updateRetrunValue = dbCon.update(updateStudent);
				}
				if (updateRetrunValue == 1) {
					System.out.println("UPDATE ¼º°ø");
				} else {
					System.err.println("UPDATE ½ÇÆÐ");
				}
				break;
			case SORT:
				ArrayList<Student> list5 = dbCon.selectSort();
				if (list5 == null) {
					System.err.println("Á¤·Ä ¿À·ù");
				} else {
					printScoreSort(list5);
				}
				break;
			case DELETE:
				int delete = inputId();
				int deleteReurnValue = dbCon.delete(delete);
				if (deleteReurnValue == 1) {
					System.err.println("»èÁ¦¼º°ø");
				} else {
					System.out.println("»èÁ¦ ½ÇÆÐ");
				}
				break;
			case EXIT:
				run = false;
				break;
			}
		} // end of while
		System.err.println("!system : ÇÁ·Î±×·¥À» Á¾·áÇÕ´Ï´Ù.");
	}

	private static int inputId() {
		boolean run = true;
		int id = 0;
		while (run) {
			try {
				System.out.print("ID ÀÔ·Â : ");
				id = Integer.parseInt(sc.nextLine());
				if (id > 0 && id < Integer.MAX_VALUE) {
					run = false;
				}
			} catch (NullPointerException e) {
				System.err.println("id¿À·ù");
			}
		}
		return id;
	}

	// ¼ºÀû¼øÀ¸·Î Á¤·ÄÇÏ·Á¿©
	private static void printScoreSort(ArrayList<Student> list) {
		Collections.sort(list, Collections.reverseOrder());
		System.out.println("µî¼ö" + "\t" + "id" + "\t" + "ÀÌ¸§" + "\t" + "¼ºº°" + "\t" + "³ªÀÌ" + "\t" + "±¹¾î" + "\t" + "¿µ¾î"
				+ "\t" + "¼öÇÐ" + "\t" + "ÃÑÁ¡" + "\t" + "Æò±Õ" + "\t" + "µî±Þ");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + 1 + "µî" + "\t" + list.get(i).toString());
		}
	}

	private static void deleteStudent(ArrayList<Student> list) {
		String name = nameMatchPattern();
		boolean flag = false;
		try {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals(name)) {
					list.remove(i);
					System.err.println("system : ¼º°øÀûÀ¸·Î [" + name + "] °¡ »èÁ¦µÇ¾ú½À´Ï´Ù.");
					flag = true;
					break;
				}

			}
		} catch (Exception e) {
			if (flag == false) {
				System.err.println("ÇØ´ç ÇÐ»ýÀ» Ã£À» ¼ö ¾ø½À´Ï´Ù.");
			}
		}
	}

	private static String searchStudent() {
		String name = null;
		name = nameMatchPattern();
		return name;
	}

	private static Student updataeStudent(Student student) {
		int kor = inputscoreSubject(student.getName(), "±¹¾î", student.getKor());
		student.setKor(kor);
		int eng = inputscoreSubject(student.getName(), "¿µ¾î", student.getEng());
		student.setEng(eng);
		int math = inputscoreSubject(student.getName(), "¼öÇÐ", student.getMath());
		student.setMath(math);
		student.calToal();
		student.calAvg();
		student.calGrade();
		System.out.println("¼öÁ¤¿Ï·á");
		return student;

	} // end

	private static int inputscoreSubject(String subject, String name, int score) {
		boolean run = true;
		int data = 0;
		while (run) {
			System.out.print(subject + " " + name + " " + score + "Á¡" + " >> ");
			try {
				data = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(data));
				if (matcher.find() && data >= 0 && data <= 100) {
					break;
				} else {
					System.err.println("¹üÀ§¸¦ ¹þ¾î³µ½À´Ï´Ù. ´Ù½Ã ½ÃÀÛÇÏ°Ú½À´Ï´Ù.");
				}
			} catch (Exception e) {
				System.err.println("Á¡¼ö ÀÔ·Â¿¡ ¿À·ù°¡ ¹ß»ýÇÏ¿´½À´Ï´Ù.");
				data = 0;
			}
		}
		return data;
	}

	private static String nameMatchPattern() {
		String name = null;
		while (true) {
			try {
				System.out.print("ÀÌ¸§ >> ");
				name = sc.nextLine();
				Pattern pattern = Pattern.compile("^[°¡-ÆR]{1,3}$");
				Matcher matcher = pattern.matcher(name);
				if (matcher.find()) {
					break;
				} else {
					System.err.println("ÆÐÅÏÀÌ ¿Ç¹Ù¸£Áö ¾Ê½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ½Ê½Ã¿À");
				}
			} catch (Exception e) {
				System.err.println("ÆÐÅÏÀÌ ¿Ç¹Ù¸£Áö ¾Ê½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ½Ê½Ã¿À");
			}
		}
		return name;
	}

	private static void analyzeStudent(ArrayList<Student> list) {
		System.out.println("id" + "\t" + "ÀÌ¸§" + "\t" + "ÃÑÁ¡" + "\t" + "Æò±Õ" + "\t" + "µî±Þ");
		for (Student data : list) {
			System.out.println(data.getId() + "\t" + data.getName() + "\t" + data.getTotal() + "\t"
					+ String.format("%.2f", data.getAvg()) + "\t" + data.getGrade());
		}
	}

	private static void printStudent(ArrayList<Student> list) {
		System.out.println("id" + "\t" + "ÀÌ¸§" + "\t" + "¼ºº°" + "\t" + "³ªÀÌ" + "\t" + "±¹¾î" + "\t" + "¿µ¾î" + "\t" + "¼öÇÐ"
				+ "\t" + "ÃÑÁ¡" + "\t" + "Æò±Õ" + "\t" + "µî±Þ");
		for (Student data : list) {
			System.out.println(data);
		} // for
	}

	private static Student inputDataStudent() {
		String name = nameMatchPattern();
		String gender = inputgGender();
		int age = inputAge();
		int kor = inputScore("±¹¾î");
		int eng = inputScore("¿µ¾î");
		int math = inputScore("¼öÇÐ");
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
				System.out.print("¼ºº°(³²ÀÚ/¿©ÀÚ) >> ");
				gender = sc.nextLine();
				Pattern pattern = Pattern.compile("^[°¡-ÆR]{2}$");
				Matcher matcher = pattern.matcher(gender); // (genderNum == 1) ? "³²¼º" : "¿©¼º";
				if (matcher.find() && gender.equals("³²ÀÚ")||gender.equals("¿©ÀÚ")) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("¼ºº°ÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù.");
				}

			} catch (Exception e) {
				System.err.println("¼ºº°ÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù.");
			}
		}
		return gender;
	}

	private static int inputScore(String sub) {
		int score = 0;
		boolean flag = false;
		while (true) {
			try {
				System.out.print(sub + " ÀÔ·Â >> ");
				score = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(score));
				if (matcher.find() && score <= 100) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("system: ÇØ´ç Á¡¼ö´Â ÀûÇÕÇÏÁö ¾Ê½À´Ï´Ù.");
				}

			} catch (Exception e) {
				System.err.println("system: ÇØ´ç Á¡¼ö´Â ÀûÇÕÇÏÁö ¾Ê½À´Ï´Ù.");
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
				System.out.print("³ªÀÌ >> ");
				age = Integer.parseInt(sc.nextLine());
				Matcher matcher = pattern.matcher(String.valueOf(age));
				if (matcher.find() && age <= 100) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("system: ÇØ´ç ³ªÀÌ¿¡ ÀûÇÕÇÏÁö ¾Ê½À´Ï´Ù.");
				}
			} catch (Exception e) {
				System.err.println("system: ÇØ´ç ³ªÀÌ¿¡ ÀûÇÕÇÏÁö ¾Ê½À´Ï´Ù.");
			}
		}
		return age;
	}

}