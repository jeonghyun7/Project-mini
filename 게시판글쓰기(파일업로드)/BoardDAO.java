package sec03.brd02;

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
			String query = "SELECT function_hierarchical() AS articleNO, @LEVEL AS level, title, content, imageFileName, id, writeDate FROM (SELECT @start_with:=0, @articleNO:=@start_with, @LEVEL:=0) tbl JOIN t_board";
								
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{ 
				int articleNO = rs.getInt("articleNO");
				int level = rs.getInt("level");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String imageFileName = rs.getString("imageFileName");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				ArticleVO article = new ArticleVO();
				
				article.setArticleNO(articleNO);
				article.setLevel(level);
				article.setTitle(title);
				article.setContent(content);
				article.setImageFileName(imageFileName);
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
	
	private int getNewArticleNO()
	{
		try
		{
			conn = dataFactory.getConnection();
			String query = "SELECT max(articleNO) from t_board";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				return (rs.getInt(1) +1);
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	public void insertNewArticle(ArticleVO article)
	{
		try
		{
			conn = dataFactory.getConnection();
			int articleNO = getNewArticleNO();
			int parentNO = article.getParentNO();
			String title = article.getTitle();
			String content = article.getContent();
			String id = article.getId();
			String imageFileName = article.getImageFileName();
			String query = "INSERT INTO t_board (articleNO, parentNO, title, content, imageFileName, id)"
							+ " VALUES (?, ?, ?, ?, ?, ?)";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,  articleNO);
			pstmt.setInt(2,  parentNO);
			pstmt.setString(3,  title);
			pstmt.setString(4, content);
			pstmt.setString(5,  imageFileName);
			pstmt.setString(6,  id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}