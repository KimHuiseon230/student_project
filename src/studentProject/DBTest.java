package studentProject;
//package sec01.exam01;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class DBTest {
//
//	public static void main(String[] args) {
//		// 1.�ܺο��� ������ ���̽��� �����Ҽ� �ֵ��� ����. studentDB�� ����.
//		Connection connection = null;
//		String url = "jdbc:mysql://localhost:3306/personDB?serverTimezone=UTC";
//		String user = "root";
//		String password = "123456";
//		String driverName = "com.mysql.cj.jdbc.Driver";
//
//		// 2.���������� JDBC�� �˾Ƽ� �� ���ֱ� ������ �츮�� JDBC�� DriverManager �� ���ؼ� DB���� ������ ������ �ȴ�.
//		try {
//			// 2-1 JDBC Ŭ���� �ε�. DB���� ������ ������ȴ�.
//			Class.forName(driverName);
//			// 2-2 mysql DB�� ����.
//			connection = DriverManager.getConnection(url, user, password);
//		} catch (ClassNotFoundException e) {
//			// `com.mysql.cj.jdbc.Driver` ���Ŭ���������̺귯�����߰������ʾҴٸ�����߻�
//			System.err.println("�����ͺ��̽� �ε����" + e.getStackTrace());
//		} catch (SQLException e) {
//			// DB ���� ������ Ʋ�ȴٸ� ���� �߻�
//			System.err.println("�����ͺ��̽� �������" + e.getStackTrace());
//		} //
//			// 3. �����͸� �����Ѵ�. insert into ���̺� �� values();
//			// ��ũ��ġ ��
//		PreparedStatement ps = null;
//		int returnValue = 1;
//		DBTest s = new DBTest("������", 28, "����", 77, 88, "�����");
//		String query = "insert into personTBL values(null,?,?,?,?,?,?);";
//
//		try {
//			ps = connection.prepareStatement(query);
//			ps.setString(1, s.getName());
//			ps.setInt(2, s.getAge());
//			ps.setString(3, s.getGender());
//			ps.setInt(4, s.getHeight());
//			ps.setInt(5, s.getWeight());
//			ps.setString(6, s.getCity());
//			// ���� �����ϸ� 1�� �����Ѵ�.
//			returnValue = ps.executeUpdate();
//		} catch (SQLException e) {
//			System.err.println("insert ���� �߻�" + e.getMessage());
//		} finally {
//			try {
//				if (ps != null) {
//					ps.close();
//				}
//			} catch (Exception e) {
//				System.out.println("ps close ����" + e.getMessage());
//				e.printStackTrace();
//			}
//			try {
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (Exception e) {
//				System.out.println("connection close ����" + e.getMessage());
//				e.printStackTrace();
//			}
//		} // end of finally
//		if (returnValue == 1) {
//			System.out.println("���Լ���");
//		} else {
//			System.out.println("���Խ���");
//		}
//	}// end of main
//}
