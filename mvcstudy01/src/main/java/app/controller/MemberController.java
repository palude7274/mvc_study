package app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.dao.MemberDao;
import app.domain.MemberVo;

// HttpServlet�� ��ӹ޾ұ⶧���� Ŭ������ ���ͳ��������� �ȴ�
@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private String location;
	public MemberController(String location) {
		this.location = location;		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		//���ڿ� ��
		if (location.equals("memberList.do")) {
			
			MemberDao md =new MemberDao();
			ArrayList<MemberVo> list =  md.memberSelectAll();
			
			request.setAttribute("list", list);
			
			 String path ="/member/memberList.jsp";
			 //ȭ��뵵�� �ּҴ� ������� �佺�ؼ� �ش� ���ּҷ� ������
			 RequestDispatcher rd = request.getRequestDispatcher(path);
			 rd.forward(request, response);
			
		}else if(location.equals("memberJoin.do")) {
			
			String path ="/member/memberJoin.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		
		}else if(location.equals("memberJoinAction.do")) {			
			
			//�����͸� �Ѱ��ָ� ��û ��ü�� �� ���� �޾Ƽ� �Ѿ�� �Ű�������
			//��� ���� ������ ���ο� ������ ��´�
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			String memberName= request.getParameter("memberName");
			String memberYear = request.getParameter("memberYear");
			String memberMonth = request.getParameter("memberMonth");
			String memberDay = request.getParameter("memberDay");
			String memberGender = request.getParameter("memberGender");
			String memberPhone = request.getParameter("memberPhone");
			String memberEmail = request.getParameter("memberEmail");
			String memberAddr = request.getParameter("memberAddr");
			String[] memberHobby = request.getParameterValues("memberHobby");
			String str = "";
			for(int i = 0; i<memberHobby.length;i++){
				str = str + memberHobby[i]+",";	
			}
			//substring�޼ҵ�: ù��°���� 0������ �����ϰ� �ι�°���� ��ȣ ��������
			str = str.substring(0, str.length() - 1);
			
			String memberBirth  = memberYear+memberMonth+memberDay;
			//������ ������ ��ü�� �����ؼ� 
			
			//DB�� �Է��Ѵ�
			MemberDao md = new MemberDao();
			int exec = md.memberInsert(memberId,memberPwd,memberName,memberBirth,memberGender,memberPhone,memberEmail,memberAddr,str);
			
			PrintWriter out = response.getWriter();
			
			if (exec == 1) {
				//�ڵ��̵��޼ҵ�
				//response.sendRedirect(request.getContextPath()+"/member/memberList.html");	
				out.println("<script>alert('ȸ������ �Ǿ����ϴ�.');"
				+	"document.location.href='"+request.getContextPath()+"/member/memberList.do'</script>");
			}else{
				out.println("<script>history.back();</script>");	
			}			
		}else if(location.equals("memberLogin.do")) {
			
			String path ="/member/memberLogin.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
			
		}else if(location.equals("memberLoginAction.do")) {
			
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
						
			MemberDao md = new MemberDao();
			int midx = 0; 
			midx = md.memberLoginCheck(memberId,memberPwd);
			
			
			//Actionó���ϴ� �뵵�� send������� ������
			
			if (midx !=0){  //��ġ�ϸ�
				//���ǿ� ȸ�����̵� ��´�
				HttpSession session = request.getSession();
				session.setAttribute("memberId", memberId);
				session.setAttribute("midx", midx);
				
				response.sendRedirect(request.getContextPath()+"/");
			}else{
				response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
			}		
			
		}else if(location.equals("memberLogout.do")) {
			
			HttpSession session = request.getSession();
			session.removeAttribute("memberid");
			session.removeAttribute("midx");
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/");
		}
		
		
		
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
