package sec02.ex02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection conn; // jdbc의 하나의 방법인 커넥션 풀, 쪼개서 주거니 받거니 하는 객체를 생성한 후 conn에 넣겠다.
	private PreparedStatement pstmt; // 실질적인 작업을 하는 객체, 검색, 추가, 삭제, 갱신 등 
	private DataSource dataFactory; //커넥션 풀의 Connection을 관리하기위한 객체 DataSource, context.xml의 데이터소스를 읽어들이는 객체
	
	public MemberDAO() {
		
		try {
				//현재 환경의 naming context 획득하기
				Context ctx = new InitialContext();
				
				Context envContext = (Context) ctx.lookup("java:/comp/env");
				dataFactory = (DataSource) envContext.lookup("jdbc/servletex"); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public List listMembers()
	{
		List<MemberVO> membersList = new ArrayList();
		try
		{
			conn = dataFactory.getConnection(); //가상객체의 정보를 가져와서 conn에 넣은 후 sql 구문을 실햇시키겠다
			String query = "select * from t_member order by joinDate desc";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) // 커넥션 풀을 이용하기때문에 처음엔 포인터가 1행을 가르킨다.
			{
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO memberVO = new MemberVO(id, pwd, name, email, joinDate); //MemberVo 필드 값을 가지고 있는 객체에 담아서 저장
				membersList.add(memberVO); //memberVo 객체의 주소를 각 인덱스에 넣어서 관리
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return membersList;
	}
	
	public void addMember(MemberVO m) //호출한 Controller로 돌아감
	{
		try
		{
			conn = dataFactory.getConnection();
			String id = m.getId();
			String pwd = m.getPwd();
			String name = m.getName();
			String email = m.getEmail();
			String query = "INSERT INTO t_member(id, pwd, name, email)"+" VALUES(?, ?, ?, ?)";
			System.out.println(query);
			pstmt = conn.prepareStatement(query); //커넥션풀 데이터를 쪼개서 하겠다는 뜻
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public MemberVO findMember(String _id)
	{
		MemberVO memInfo = null;
		try
		{
			conn = dataFactory.getConnection();
			String query = "select * from t_member where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, _id);
			System.out.println(query);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString("id");
			String pwd = rs.getString("pwd");
			String name = rs.getString("name");
			String email = rs.getString("email");
			Date joinDate = rs.getDate("joinDate");
			memInfo = new MemberVO(id, pwd, name, email, joinDate);
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return memInfo;
	}
	
	public void modMember(MemberVO memberVO)
	{
		String id = memberVO.getId();
		String pwd = memberVO.getPwd();
		String name = memberVO.getName();
		String email = memberVO.getEmail();
		try
		{
			conn = dataFactory.getConnection();
			String query = "update t_member set pwd=?,name=?,email=? where id=?";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pwd);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void delMember(String id)
	{
		try
		{
			conn = dataFactory.getConnection();
			String query = "delete from t_member where id=?";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
