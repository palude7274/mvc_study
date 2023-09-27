package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import app.dbconn.DbConn;
import app.domain.MemberVo;

public class MemberDao {

	//멤버변수 선언하고 전역으로 활용한다
	private Connection conn;  //멤버변수는 선언만해도 자동초기화됨
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
		//무한배열클래스 객체생성해서 데이터를 담을 준비를 한다
		ArrayList<MemberVo> alist =new ArrayList<MemberVo>();
		ResultSet rs = null;
		String sql="select * from member0803 where delyn='N' order by midx desc";
		try{
			//1.창고(컬렉션)를 만든다
			//2.쿼리를 실행해서 데이터를 전용객체에 담아온다 
			//3.전용객체에 있는 데이터를 회원객체(MemberVo)에 옮겨담는다 
			//4.회원객체를 창고에 집어넣는다	
			
			//구문(쿼리)객체
			pstmt = conn.prepareStatement(sql);
			//DB에 있는 값을 담아오는 전용객체
			rs = pstmt.executeQuery();
			//rs.next()는 다음값이 있는지 확인하는 메소드 있으면true
			while(rs.next()){
				MemberVo mv = new MemberVo();
				//rs에서 midx값 꺼내서 mv에 옮겨담는다
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
		int value=0;  // 결과값이 0인지 아닌지
		
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
				//value가 0이면 일치하지않는다
				//1 일치한다
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
