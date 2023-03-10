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
			System.out.println("+==================================[ 성적 처리 프로그램 ]==================================+");
			System.out.println("|	        1.입력 | 2.출력 | 3.분석 | 4.검색 | 5.수정 | 6.순위 | 7.삭제 | 8.종료           |");
			System.out
					.println("+=====================================================================================+");
			System.out.print("입력 >> ");
			no = Integer.parseInt(sc.nextLine());
			//
			switch (no) {
			case INPUT:
				// 생성자를 선택해야함. 이름, 나이,국어,영어, 수학
				Student student = inputDataStudent();
				// DB입력
				int rValue = dbCon.insert(student);
				if (rValue == 1) {
					System.out.println("삽입성공");
				} else {
					System.err.println("삽입실패");
				}
				break;
			case PRINT:
				ArrayList<Student> list2 = dbCon.select();
				if (list2 == null) {
					System.out.println("선택 실패!");
				} else {
					printStudent(list2);
				}
				break;
			case ANALYZE:
				ArrayList<Student> list3 = dbCon.analizeSelect();
				if (list3 == null) {
					System.out.println("선택 실패!");
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
					System.err.println("학생이름 검색오류");
				}
				break;
			case PUDATE:
				int updateRetrunValue = 0;
				int id = inputId(); // id를 통해서 값
				Student stu = dbCon.selectId(id);
				if (stu == null) {
					System.out.println("수정오류");
				} else {
					Student updateStudent = updataeStudent(stu);
					updateRetrunValue = dbCon.update(updateStudent);
				}
				if (updateRetrunValue == 1) {
					System.out.println("UPDATE 성공");
				} else {
					System.err.println("UPDATE 실패");
				}
				break;
			case SORT:
				ArrayList<Student> list5 = dbCon.selectSort();
				if (list5 == null) {
					System.err.println("정렬 오류");
				} else {
					printScoreSort(list5);
				}
				break;
			case DELETE:
				int delete = inputId();
				int deleteReurnValue = dbCon.delete(delete);
				if (deleteReurnValue == 1) {
					System.err.println("삭제성공");
				} else {
					System.out.println("삭제 실패");
				}
				break;
			case EXIT:
				run = false;
				break;
			}
		} // end of while
		System.err.println("!system : 프로그램을 종료합니다.");
	}

	private static int inputId() {
		boolean run = true;
		int id = 0;
		while (run) {
			try {
				System.out.print("ID 입력 : ");
				id = Integer.parseInt(sc.nextLine());
				if (id > 0 && id < Integer.MAX_VALUE) {
					run = false;
				}
			} catch (NullPointerException e) {
				System.err.println("id오류");
			}
		}
		return id;
	}

	// 성적순으로 정렬하려여
	private static void printScoreSort(ArrayList<Student> list) {
		Collections.sort(list, Collections.reverseOrder());
		System.out.println("등수" + "\t" + "id" + "\t" + "이름" + "\t" + "성별" + "\t" + "나이" + "\t" + "국어" + "\t" + "영어"
				+ "\t" + "수학" + "\t" + "총점" + "\t" + "평균" + "\t" + "등급");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + 1 + "등" + "\t" + list.get(i).toString());
		}
	}

	private static void deleteStudent(ArrayList<Student> list) {
		String name = nameMatchPattern();
		boolean flag = false;
		try {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals(name)) {
					list.remove(i);
					System.err.println("system : 성공적으로 [" + name + "] 가 삭제되었습니다.");
					flag = true;
					break;
				}

			}
		} catch (Exception e) {
			if (flag == false) {
				System.err.println("해당 학생을 찾을 수 없습니다.");
			}
		}
	}

	private static String searchStudent() {
		String name = null;
		name = nameMatchPattern();
		return name;
	}

	private static Student updataeStudent(Student student) {
		int kor = inputscoreSubject(student.getName(), "국어", student.getKor());
		student.setKor(kor);
		int eng = inputscoreSubject(student.getName(), "영어", student.getEng());
		student.setEng(eng);
		int math = inputscoreSubject(student.getName(), "수학", student.getMath());
		student.setMath(math);
		student.calToal();
		student.calAvg();
		student.calGrade();
		System.out.println("수정완료");
		return student;

	} // end

	private static int inputscoreSubject(String subject, String name, int score) {
		boolean run = true;
		int data = 0;
		while (run) {
			System.out.print(subject + " " + name + " " + score + "점" + " >> ");
			try {
				data = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(data));
				if (matcher.find() && data >= 0 && data <= 100) {
					break;
				} else {
					System.err.println("범위를 벗어났습니다. 다시 시작하겠습니다.");
				}
			} catch (Exception e) {
				System.err.println("점수 입력에 오류가 발생하였습니다.");
				data = 0;
			}
		}
		return data;
	}

	private static String nameMatchPattern() {
		String name = null;
		while (true) {
			try {
				System.out.print("이름 >> ");
				name = sc.nextLine();
				Pattern pattern = Pattern.compile("^[가-힣]{1,3}$");
				Matcher matcher = pattern.matcher(name);
				if (matcher.find()) {
					break;
				} else {
					System.err.println("패턴이 옳바르지 않습니다. 다시 입력해주십시오");
				}
			} catch (Exception e) {
				System.err.println("패턴이 옳바르지 않습니다. 다시 입력해주십시오");
			}
		}
		return name;
	}

	private static void analyzeStudent(ArrayList<Student> list) {
		System.out.println("id" + "\t" + "이름" + "\t" + "총점" + "\t" + "평균" + "\t" + "등급");
		for (Student data : list) {
			System.out.println(data.getId() + "\t" + data.getName() + "\t" + data.getTotal() + "\t"
					+ String.format("%.2f", data.getAvg()) + "\t" + data.getGrade());
		}
	}

	private static void printStudent(ArrayList<Student> list) {
		System.out.println("id" + "\t" + "이름" + "\t" + "성별" + "\t" + "나이" + "\t" + "국어" + "\t" + "영어" + "\t" + "수학"
				+ "\t" + "총점" + "\t" + "평균" + "\t" + "등급");
		for (Student data : list) {
			System.out.println(data);
		} // for
	}

	private static Student inputDataStudent() {
		String name = nameMatchPattern();
		String gender = inputgGender();
		int age = inputAge();
		int kor = inputScore("국어");
		int eng = inputScore("영어");
		int math = inputScore("수학");
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
				System.out.print("성별(남자/여자) >> ");
				gender = sc.nextLine();
				Pattern pattern = Pattern.compile("^[가-힣]{2}$");
				Matcher matcher = pattern.matcher(gender); // (genderNum == 1) ? "남성" : "여성";
				if (matcher.find() && gender.equals("남자")||gender.equals("여자")) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("성별이 맞지 않습니다.");
				}

			} catch (Exception e) {
				System.err.println("성별이 맞지 않습니다.");
			}
		}
		return gender;
	}

	private static int inputScore(String sub) {
		int score = 0;
		boolean flag = false;
		while (true) {
			try {
				System.out.print(sub + " 입력 >> ");
				score = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(score));
				if (matcher.find() && score <= 100) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("system: 해당 점수는 적합하지 않습니다.");
				}

			} catch (Exception e) {
				System.err.println("system: 해당 점수는 적합하지 않습니다.");
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
				System.out.print("나이 >> ");
				age = Integer.parseInt(sc.nextLine());
				Matcher matcher = pattern.matcher(String.valueOf(age));
				if (matcher.find() && age <= 100) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("system: 해당 나이에 적합하지 않습니다.");
				}
			} catch (Exception e) {
				System.err.println("system: 해당 나이에 적합하지 않습니다.");
			}
		}
		return age;
	}

}