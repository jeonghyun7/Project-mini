package sec03.brd01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO 
{
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;
	public BoardDAO()
	{
		try
		{
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/servletex");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public List SelectALLArticles()
	{
		List articlesList = new ArrayList();
		try
		{
			conn = dataFactory.getConnection(); //아래select구문에 level 생략함 oracle사용되는 계층형쿼리구문 생략함
			String query = "SELECT articleNO, parentNO, title, content, id, writeDate"
								+ " from t_board"
								+ " ORDER BY articleNO DESC";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				// int level = rs.getInt("level"); 
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				ArticleVO article = new ArticleVO();
				// article.setLevel(level);
				article.setArticleNO(articleNO);
				article.setParentNO(parentNO);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWriteDate(writeDate);
				articlesList.add(article);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return articlesList;
	}
}