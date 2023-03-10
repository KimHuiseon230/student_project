package studentProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection {
	private Connection connection = null;

	public void connect() {
		// 1. �ܺο��� DB�����Ҽ� �ֵ��� ����
		Properties properties = new Properties();
		FileInputStream fis = null;
		// 2. db.Properties ������ �ε�
		try {
			fis = new FileInputStream("G:/Student/src/studentProject/db.properties");
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("FileInputStream error" + e.getStackTrace());
		} catch (IOException e) {
			System.out.println("Properties.load error" + e.getStackTrace());
		}
		try {
			// 2-1 JDBC Ŭ���� �ε�. DB���� ������ ������ȴ�.
			Class.forName(properties.getProperty("driverName"));
			// 2-2 mysql DB�� ����.
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException e) {
			// `com.mysql.cj.jdbc.Driver` ��� Ŭ������ ���̺귯���� �߰� ���� �ʾҴٸ� �����߻�
			System.err.println("[�����ͺ��̽� �ε����]" + e.getStackTrace());
		} catch (SQLException e) {
			// DB ���� ������ Ʋ�ȴٸ� ���� �߻�
			System.err.println("[�����ͺ��̽� �������]" + e.getStackTrace());
		}
	}
	// insert
	public int insert(Student s) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		System.out.println(s);
		String query = "insert into studentTBL values(null,?,?,?,?,?,?,?,?,?)";

		try {
			ps = connection.prepareStatement(query);

			ps.setString(1, s.getName());
			ps.setString(2, s.getGender());
			ps.setInt(3, s.getAge());
			ps.setInt(4, s.getKor());
			ps.setInt(5, s.getEng());
			ps.setInt(6, s.getMath());
			ps.setInt(7, s.getTotal());
			ps.setDouble(8, s.getAvg());
			ps.setString(9, s.getGrade());
			// ���� �����ϸ� 1�� �����Ѵ�.
			returnValue = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("INSERT ERROR" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.err.println("PS CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.err.println("CONNECTION CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
		} // end of finally

		return returnValue;
	}
	// select()
	public ArrayList<Student> select() {
		ArrayList<Student> list = new ArrayList<>();

		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM studentTBL";

		try {
			ps = connection.prepareStatement(query);
			// select �����ϸ� ResultSet, �����ϸ� null
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				Double avg = rs.getDouble("avg");
				String grade = rs.getString("grade");
				list.add(new Student(id, name, gender, age, kor, eng, math, total, avg, grade));
			}

		} catch (SQLException e) {
			System.err.println("SELECT ERROR" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.err.println("PS CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.err.println("CONNECTION CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
		} // end of finally
		return list;
	}
	// analizeSelect()
	public ArrayList<Student> analizeSelect() {
		ArrayList<Student> list = new ArrayList<>();

		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT id,name,gender,age, total,avg,grade FROM studentTBL";

		try {
			ps = connection.prepareStatement(query);

			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				int total = rs.getInt("total");
				Double avg = rs.getDouble("avg");
				String grade = rs.getString("grade");
				list.add(new Student(id, name, null, age, 0, 0, 0, total, avg, grade));
			}

		} catch (SQLException e) {
			System.err.println("SELECT ERROR" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.err.println("PS CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.err.println("CONNECTION CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
		} // end of finally
		return list;
	}
	// nameSearch()
	public ArrayList<Student> nameSearch(String dataName) {
		ArrayList<Student> list = new ArrayList<>();

		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM studentTBL WHERE name like ?";

		try {
			ps = connection.prepareStatement(query);

			ps.setString(1, "%" + dataName + "%");
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				Double avg = rs.getDouble("avg");
				String grade = rs.getString("grade");
				list.add(new Student(id, name, gender, age, kor, eng, math, total, avg, grade));
			}

		} catch (SQLException e) {
			System.err.println("SELECT ERROR" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.err.println("PS CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.err.println("CONNECTION CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
		} // end of finally
		return list;
	}
	// selectId()
	public Student selectId(int dataId) {
		Student student = null;
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM studentTBL WHERE id=?";

		try {
			ps = connection.prepareStatement(query);

			ps.setInt(1, dataId);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				Double avg = rs.getDouble("avg");
				String grade = rs.getString("grade");
				student = new Student(id, name, gender, age, kor, eng, math, total, avg, grade);
			}

		} catch (SQLException e) {
			System.err.println("SELECT ERROR" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.err.println("PS CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.err.println("CONNECTION CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
		} // end of finally
		return student;

	}
	// update()
	public int update(Student s) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		System.out.println(s);
		String query = "UPDATE  studentTBL SET  kor=?,eng=?,math=?,total=?,avg=?, grade=? WHERE id=?";

		try {
			ps = connection.prepareStatement(query);

			ps.setInt(1, s.getKor());
			ps.setInt(2, s.getEng());
			ps.setInt(3, s.getMath());
			ps.setInt(4, s.getTotal());
			ps.setDouble(5, s.getAvg());
			ps.setString(6, s.getGrade());
			ps.setInt(7, s.getId());
			// ���� �����ϸ� 1�� �����Ѵ�.
			returnValue = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("UPDATE ERROR" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.err.println("PS CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.err.println("CONNECTION CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
		} // end of finally
		return returnValue;
	}
	// selectSort()
	public ArrayList<Student> selectSort() {
		ArrayList<Student> list = new ArrayList<>();
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select *from studentTBL order by total desc;";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				Double avg = rs.getDouble("avg");
				String grade = rs.getString("grade");
				list.add(new Student(id, name, gender, age, kor, eng, math, total, avg, grade));
			}

		} catch (SQLException e) {
			System.err.println("SELECT ERROR" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.err.println("PS CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.err.println("CONNECTION CLOSE ERROR" + e.getMessage());
				e.printStackTrace();
			}
		} // end of finally
		return list;
	}
	public int delete(int delete) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "DELETE FROM studentTBL WHERE id=?";
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, delete);
			returnValue = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("insert ���� �߻�" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.err.println("ps close ����" + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.err.println("connection close ����" + e.getMessage());
				e.printStackTrace();
			}
		} // end of finally
		return returnValue;
	}
}
