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
//		// 1.외부에서 데이터 베이스를 접속할수 있도록 설정. studentDB로 수정.
//		Connection connection = null;
//		String url = "jdbc:mysql://localhost:3306/personDB?serverTimezone=UTC";
//		String user = "root";
//		String password = "123456";
//		String driverName = "com.mysql.cj.jdbc.Driver";
//
//		// 2.내부적으로 JDBC가 알아서 다 해주기 때문에 우리는 JDBC의 DriverManager 를 통해서 DB와의 연결을 얻으면 된다.
//		try {
//			// 2-1 JDBC 클래스 로드. DB와의 연결을 얻으면된다.
//			Class.forName(driverName);
//			// 2-2 mysql DB와 연결.
//			connection = DriverManager.getConnection(url, user, password);
//		} catch (ClassNotFoundException e) {
//			// `com.mysql.cj.jdbc.Driver` 라는클래스가라이브러리로추가되지않았다면오류발생
//			System.err.println("데이터베이스 로드오류" + e.getStackTrace());
//		} catch (SQLException e) {
//			// DB 접속 정보가 틀렸다면 오류 발생
//			System.err.println("데이터베이스 연결오류" + e.getStackTrace());
//		} //
//			// 3. 데이터를 삽입한다. insert into 테이블 명 values();
//			// 워크밴치 툴
//		PreparedStatement ps = null;
//		int returnValue = 1;
//		DBTest s = new DBTest("김진선", 28, "여자", 77, 88, "서울시");
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
//			// 삽입 성공하면 1을 리턴한다.
//			returnValue = ps.executeUpdate();
//		} catch (SQLException e) {
//			System.err.println("insert 오류 발생" + e.getMessage());
//		} finally {
//			try {
//				if (ps != null) {
//					ps.close();
//				}
//			} catch (Exception e) {
//				System.out.println("ps close 오류" + e.getMessage());
//				e.printStackTrace();
//			}
//			try {
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (Exception e) {
//				System.out.println("connection close 오류" + e.getMessage());
//				e.printStackTrace();
//			}
//		} // end of finally
//		if (returnValue == 1) {
//			System.out.println("삽입성공");
//		} else {
//			System.out.println("삽입실패");
//		}
//	}// end of main
//}
