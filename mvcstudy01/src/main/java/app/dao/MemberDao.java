package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import app.dbconn.DbConn;
import app.domain.MemberVo;

public class MemberDao {

	//������� �����ϰ� �������� Ȱ���Ѵ�
	private Connection conn;  //��������� �����ص� �ڵ��ʱ�ȭ��
	private PreparedStatement pstmt;
	
	public MemberDao() {
		DbConn dbconn = new DbConn();
		this.conn = dbconn.getConnection();
	}
	
	public int memberInsert(
			String memberId,
			String memberPwd,String memberName,
			String memberBirth,String memberGender,
			String memberPhone,String memberEmail,
			String memberAddr,String memberHobby){
		int exec = 0;
		
		String sql = "insert into member0803(memberid,memberpwd,membername,memberbirth,membergender,memberphone,memberemail,memberaddr,memberhobby)"
		        +" values(?,?,?,?,?,?,?,?,?)";
		try{
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberId);
		pstmt.setString(2, memberPwd);
		pstmt.setString(3, memberName);
		pstmt.setString(4, memberBirth);
		pstmt.setString(5, memberGender);
		pstmt.setString(6, memberPhone);
		pstmt.setString(7, memberEmail);
		pstmt.setString(8, memberAddr);
		pstmt.setString(9, memberHobby);	
		exec = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return exec;	
	}

	public ArrayList<MemberVo>  memberSelectAll(){
		//���ѹ迭Ŭ���� ��ü�����ؼ� �����͸� ���� �غ� �Ѵ�
		ArrayList<MemberVo> alist =new ArrayList<MemberVo>();
		ResultSet rs = null;
		String sql="select * from member0803 where delyn='N' order by midx desc";
		try{
			//1.â��(�÷���)�� �����
			//2.������ �����ؼ� �����͸� ���밴ü�� ��ƿ´� 
			//3.���밴ü�� �ִ� �����͸� ȸ����ü(MemberVo)�� �Űܴ�´� 
			//4.ȸ����ü�� â�� ����ִ´�	
			
			//����(����)��ü
			pstmt = conn.prepareStatement(sql);
			//DB�� �ִ� ���� ��ƿ��� ���밴ü
			rs = pstmt.executeQuery();
			//rs.next()�� �������� �ִ��� Ȯ���ϴ� �޼ҵ� ������true
			while(rs.next()){
				MemberVo mv = new MemberVo();
				//rs���� midx�� ������ mv�� �Űܴ�´�
				mv.setMidx( rs.getInt("midx") ); 
				mv.setMemberId( rs.getString("memberid") );
				mv.setMemberName( rs.getString("membername"));
				mv.setWriteday( rs.getString("writeday"));
				alist.add(mv);
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

	public int memberIdCheck(String memberId){
		int value=0;  // ������� 0���� �ƴ���
		
		String sql="select count(*) as cnt from member0803 where memberid=?";
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				value =	rs.getInt("cnt");
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
		return value;
	}


	public int memberLoginCheck(String memberId, String memberPwd){
		int value=0;
		
		String sql="select midx from member0803 where memberid=? and memberpwd=?";
		ResultSet rs = null;
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				value =	rs.getInt("midx");
				//value�� 0�̸� ��ġ�����ʴ´�
				//1 ��ġ�Ѵ�
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
		
		return value;
	}

	
	
	
	
	
}
