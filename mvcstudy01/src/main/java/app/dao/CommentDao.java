package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.dbconn.DbConn;
import app.domain.CommentVo;


public class CommentDao {
	
	//멤버변수 선언하고 전역으로 활용한다
	private Connection conn;  //멤버변수는 선언만해도 자동초기화됨
	private PreparedStatement pstmt;
		
		//생성자를 만든다 DB연결
	public CommentDao() {
		DbConn dbconn = new DbConn();
		this.conn = dbconn.getConnection();
	}
	
	public ArrayList<CommentVo>  commentSelectAll(){
		//무한배열클래스 객체생성해서 데이터를 담을 준비를 한다
		ArrayList<CommentVo> alist =new ArrayList<CommentVo>();
		ResultSet rs = null;
		String sql="select * from comment0803 where delyn='N' order by cidx desc";
		try{
			//1.창고(컬렉션)를 만든다
			//2.쿼리를 실행해서 데이터를 전용객체에 담아온다 
			//3.전용객체에 있는 데이터를 회원객체(CommentVo)에 옮겨담는다 
			//4.회원객체를 창고(컬렉션)에 집어넣는다	
			
			//구문(쿼리)객체
			pstmt = conn.prepareStatement(sql);
			//DB에 있는 값을 담아오는 전용객체
			rs = pstmt.executeQuery();
			//rs.next()는 다음값이 있는지 확인하는 메소드 있으면true
			while(rs.next()){
				CommentVo cv = new CommentVo();
				//rs에서 cidx값 꺼내서 cv에 옮겨담는다
				cv.setCidx(rs.getInt("cidx"));
				cv.setMidx(rs.getInt("midx"));
				cv.setBidx(rs.getInt("bidx"));
				cv.setCwriter(rs.getString("cwriter"));
				cv.setCcontents(rs.getString("ccontents"));
				cv.setCwriteday(rs.getString("cwriteday"));
				alist.add(cv);
				//반복문을 돌릴때마다 창고(컬렉션)에 추가해서 담는다
			}		
			
		}catch(Exception e){
			e.printStackTrace();		
		}finally{
			try{
				rs.close();
				pstmt.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return alist;
	}
	
	public int commentInsert(CommentVo cv){
		int exec = 0;
		
		String sql = "insert into comment0803(cwriter,ccontents,bidx,midx)"
		        +" values(?,?,?,?)";
		try{
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, cv.getCwriter());
		pstmt.setString(2, cv.getCcontents());
		pstmt.setInt(3, cv.getBidx());
		pstmt.setInt(4, cv.getMidx());
		exec = pstmt.executeUpdate();		//실행이 되면 1 안되면 0
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return exec;	
	}
	
	public int commentDelete(int cidx){
		int exec = 0;
		
		String sql = "update comment0803 set delyn='Y' where cidx=?";

		try{
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, cidx);
		exec = pstmt.executeUpdate();		//실행이 되면 1 안되면 0
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return exec;	
	}

}
