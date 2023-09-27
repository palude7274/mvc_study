package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.dbconn.DbConn;
import app.domain.CommentVo;


public class CommentDao {
	
	//������� �����ϰ� �������� Ȱ���Ѵ�
	private Connection conn;  //��������� �����ص� �ڵ��ʱ�ȭ��
	private PreparedStatement pstmt;
		
		//�����ڸ� ����� DB����
	public CommentDao() {
		DbConn dbconn = new DbConn();
		this.conn = dbconn.getConnection();
	}
	
	public ArrayList<CommentVo>  commentSelectAll(){
		//���ѹ迭Ŭ���� ��ü�����ؼ� �����͸� ���� �غ� �Ѵ�
		ArrayList<CommentVo> alist =new ArrayList<CommentVo>();
		ResultSet rs = null;
		String sql="select * from comment0803 where delyn='N' order by cidx desc";
		try{
			//1.â��(�÷���)�� �����
			//2.������ �����ؼ� �����͸� ���밴ü�� ��ƿ´� 
			//3.���밴ü�� �ִ� �����͸� ȸ����ü(CommentVo)�� �Űܴ�´� 
			//4.ȸ����ü�� â��(�÷���)�� ����ִ´�	
			
			//����(����)��ü
			pstmt = conn.prepareStatement(sql);
			//DB�� �ִ� ���� ��ƿ��� ���밴ü
			rs = pstmt.executeQuery();
			//rs.next()�� �������� �ִ��� Ȯ���ϴ� �޼ҵ� ������true
			while(rs.next()){
				CommentVo cv = new CommentVo();
				//rs���� cidx�� ������ cv�� �Űܴ�´�
				cv.setCidx(rs.getInt("cidx"));
				cv.setMidx(rs.getInt("midx"));
				cv.setBidx(rs.getInt("bidx"));
				cv.setCwriter(rs.getString("cwriter"));
				cv.setCcontents(rs.getString("ccontents"));
				cv.setCwriteday(rs.getString("cwriteday"));
				alist.add(cv);
				//�ݺ����� ���������� â��(�÷���)�� �߰��ؼ� ��´�
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
		exec = pstmt.executeUpdate();		//������ �Ǹ� 1 �ȵǸ� 0
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
		exec = pstmt.executeUpdate();		//������ �Ǹ� 1 �ȵǸ� 0
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
