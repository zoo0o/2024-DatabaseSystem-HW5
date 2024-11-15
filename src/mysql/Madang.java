package mysql;

import java.sql.*;
import java.util.Scanner;

public class Madang {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://192.168.56.101:4567/madang", "kimjiyu", "1234");

			// 삽입, 삭제, 검색 구현
			System.out.println("1.삽입 2.삭제 3.검색 4.목록 조회");
			Scanner sc = new Scanner(System.in);
			int num = sc.nextInt();
			sc.nextLine();

			switch (num) {
			case 1: // 삽입
				System.out.print("삽입할 책의 ID 입력: ");
				int bookId = sc.nextInt();
				sc.nextLine();

				System.out.print("삽입할 책의 이름 입력: ");
				String bookName = sc.nextLine();

				System.out.print("삽입할 책의 출판사 입력: ");
				String publisher = sc.nextLine();

				System.out.print("삽입할 책의 가격 입력: ");
				int price = sc.nextInt();

				String insertSql = "INSERT INTO Book VALUES (?, ?, ?, ?)";
				PreparedStatement pstmt = con.prepareStatement(insertSql);
				pstmt.setInt(1, bookId);
				pstmt.setString(2, bookName);
				pstmt.setString(3, publisher);
				pstmt.setInt(4, price);
				pstmt.executeUpdate();
				pstmt.close();
				break;

			case 2: // 삭제
				System.out.print("삭제할 책의 ID 입력: ");
				int deleteId = sc.nextInt();

				String deleteSql = "DELETE FROM Book WHERE bookid = ?";
				PreparedStatement pstmt2 = con.prepareStatement(deleteSql);
				pstmt2.setInt(1, deleteId);
				int result = pstmt2.executeUpdate();

				if (result < 1) {
					System.out.println("해당 ID의 도서를 찾을 수 없습니다.");
				}
				pstmt2.close();
				break;

			case 3: // 검색
				System.out.print("검색할 책의 이름 입력: ");
				String searchName = sc.nextLine();

				String searchSql = "SELECT * FROM Book WHERE bookname LIKE ?";
				PreparedStatement pstmt3 = con.prepareStatement(searchSql);
				pstmt3.setString(1, "%" + searchName + "%");
				ResultSet rs2 = pstmt3.executeQuery();

				boolean found = false;
				while (rs2.next()) {
					found = true;
					System.out.println(rs2.getInt(1) + " " + rs2.getString(2) + " " + rs2.getString(3));
				}
				if (!found) {
					System.out.println("검색 결과가 없습니다.");
				}
				rs2.close();
				pstmt3.close();
				break;

			case 4: // 목록 조회
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Book");
				while (rs.next()) {
					System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
				}
				rs.close();
				stmt.close();
				break;

			default:
				System.out.println("잘못된 선택입니다.");
				break;
			}

			sc.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}