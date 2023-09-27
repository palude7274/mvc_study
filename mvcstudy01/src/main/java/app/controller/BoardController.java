package app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import app.dao.BoardDao;
import app.dao.MemberDao;
import app.domain.BoardVo;
import app.domain.Criteria;
import app.domain.MemberVo;
import app.domain.PageMaker;
import app.domain.SearchCriteria;

// HttpServlet�� ��ӹ޾ұ⶧���� Ŭ������ ���ͳ��������� �ȴ�
@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String location; 
	public BoardController(String location){
		this.location = location;
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//���ڿ� ��
		if (location.equals("boardList.do")) {	
			
			String searchType = request.getParameter("searchType");
			if (searchType ==null) searchType="subject";
			String keyword = request.getParameter("keyword");
			if (keyword ==null) keyword="";			
			String page = request.getParameter("page");
			if (page ==null) page ="1";
						
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(Integer.parseInt(page));	
			scri.setSearchType(searchType);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BoardDao bd = new BoardDao();
			ArrayList<BoardVo> alist  = bd.boardSelectAll(scri);
			int cnt = bd.boardTotalCount(scri); //��ü�Խù���
			//System.out.println("cnt?"+cnt);
			pm.setTotalCount(cnt);
						
			request.setAttribute("alist", alist);
			request.setAttribute("pm", pm);
			
			String path ="/board/boardList.jsp";
			 //ȭ��뵵�� �ּҴ� ������� �佺�ؼ� �ش� ���ּҷ� ������
			 RequestDispatcher rd = request.getRequestDispatcher(path);
			 rd.forward(request, response);	
		
		}else if(location.equals("boardWrite.do")) {
						
			 String path ="/board/boardWrite.jsp";
			 //ȭ��뵵�� �ּҴ� ������� �佺�ؼ� �ش� ���ּҷ� ������
			 RequestDispatcher rd = request.getRequestDispatcher(path);
			 rd.forward(request, response);
			
		}else if(location.equals("boardWriteAction.do")) {
			
			String savePath="D:\\PGD\\mvcstudy0803\\src\\main\\webapp\\images";
			int sizeLimit = 15*1024*1024; //15M
			String dataTy ="UTF-8";
			
			//�����̸� �ߺ���å
			DefaultFileRenamePolicy drp = null;
			drp = new DefaultFileRenamePolicy();
			
			//�پ��� ���ϰ� �����͸� �Ѱܹ޴� ��ſ�û��ü
			MultipartRequest multi = null;
			multi = new MultipartRequest(request,savePath,sizeLimit,dataTy,drp);
			
			
			//1.�ѱ䰪�� �޴´�
			String subject = multi.getParameter("subject");
			String contents = multi.getParameter("contents");
			String writer = multi.getParameter("writer");
			String pwd = multi.getParameter("pwd");
			
			//���ϳѰܹޱ�
			//�����ڿ� �Ѿ���� ���� �����̸��� ��´�
			Enumeration files =   multi.getFileNames();
			//���� ��ü�� ������
			String file = (String)files.nextElement();
			//�� ������ �̸��� �����Ѵ�(������ ����Ǵ� �����̸�)
			String fileName = multi.getFilesystemName(file);
			//���� �����̸� ����
			String originFileName = multi.getOriginalFileName(file);
			
			int midx = 0;
			HttpSession session = request.getSession();
			midx = (int)session.getAttribute("midx");
			
			//2. �������� �Է��Ѵ�
			BoardVo bv = new BoardVo();
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setFilename(fileName);
			bv.setPwd(pwd);
			bv.setMidx(midx);
			
			BoardDao bd = new BoardDao();
			int value = bd.boardInsert(bv);
			
			//3. ó���� �������� ���Ӱ� �̵��Ѵ�
			if (value ==0) { //�Է� �ȵǾ����� �Է�������
				String path = request.getContextPath()+"/board/boardWrite.do";
				response.sendRedirect(path);
			}else {	//�ԷµǾ����� ����Ʈ �������� �̵�		
				String path = request.getContextPath()+"/board/boardList.do";
				response.sendRedirect(path);
			}

		}else if(location.equals("boardContents.do")) {
			String bidx = request.getParameter("bidx");
			int bidx_int = Integer.parseInt(bidx);
			
			BoardDao bd = new BoardDao();
			int exec = bd.boardCntUpdate(bidx_int);
			BoardVo bv = bd.boardSelectOne(bidx_int);			
			
			request.setAttribute("bv", bv);
			
			String path ="/board/boardContents.jsp";
			 //ȭ��뵵�� �ּҴ� ������� �佺�ؼ� �ش� ���ּҷ� ������
			 RequestDispatcher rd = request.getRequestDispatcher(path);
			 rd.forward(request, response);
			 
		}else if(location.equals("boardModify.do")) {
			String bidx = request.getParameter("bidx");
			int bidx_int = Integer.parseInt(bidx);
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_int);
			
			request.setAttribute("bv", bv);			
			
			String path ="/board/boardModify.jsp";
			 //ȭ��뵵�� �ּҴ� ������� �佺�ؼ� �ش� ���ּҷ� ������
			 RequestDispatcher rd = request.getRequestDispatcher(path);
			 rd.forward(request, response);
			 
		}else if(location.equals("boardModifyAction.do")) { 
			//1.���������� �Ѱܹް�
			String bidx = request.getParameter("bidx");
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			String pwd = request.getParameter("pwd");
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			//2. �������� �Է��Ѵ�
			BoardVo bv = new BoardVo();
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);			
			bv.setPwd(pwd);
			bv.setBidx(Integer.parseInt(bidx));
			bv.setIp(ip);
			
			BoardDao bd = new BoardDao();
			int value = bd.boardModify(bv);
			
			PrintWriter out = response.getWriter();
			
			//3. ó���� �������� ���Ӱ� �̵��Ѵ�
			if (value ==0) { //���� �ȵǾ����� ����������
				
				 out.println("<script>alert('��й�ȣ�� ��ġ���� �ʽ��ϴ�.');location.href='"+request.getContextPath()+"/board/boardModify.do?bidx="+bidx+"'</script>");	 
				 				
				//String path = request.getContextPath()+"/board/boardModify.do?bidx="+bidx;
				//response.sendRedirect(path);
			}else {	//�����Ǿ����� ���� �������� �̵�		
				String path = request.getContextPath()+"/board/boardContents.do?bidx="+bidx;
				response.sendRedirect(path);
			}		
		
		}else if(location.equals("boardDelete.do")) {
			
			String bidx  = request.getParameter("bidx");
			int bidx_int = Integer.parseInt(bidx);
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_int);
			
			request.setAttribute("bv", bv);			
		
			String path ="/board/boardDelete.jsp";
			 //ȭ��뵵�� �ּҴ� ������� �佺�ؼ� �ش� ���ּҷ� ������.���� �����̹Ƿ�
			 //�����ؼ� ���� ���� �ִ�
			 RequestDispatcher rd = request.getRequestDispatcher(path);
			 rd.forward(request, response);
		
		}else if (location.equals("boardDeleteAction.do")) {
		
			String bidx  = request.getParameter("bidx");
			int bidx_int = Integer.parseInt(bidx);
			String pwd = request.getParameter("pwd");
		
			//ó���ϴ� �޼ҵ带 ������ �Ѵ�
			int value=0;
			
			BoardDao bd = new BoardDao();
			value = bd.boardDelete(bidx_int, pwd);			
			
			//ó���� �Ǹ� 1�� �ƴϰ� �ƴϸ� 0������ ������  value
			if (value !=0) {
				String path = request.getContextPath()+"/board/boardList.do";
				response.sendRedirect(path);				
			}else {
				String path = request.getContextPath()+"/board/boardDelete.do?bidx="+bidx;
				response.sendRedirect(path);				
			}			
			
		
		}else if(location.equals("boardReply.do")) {
			
			String bidx  = request.getParameter("bidx");
			int bidx_int = Integer.parseInt(bidx);
			String originbidx  = request.getParameter("originbidx");
			int originbidx_int = Integer.parseInt(originbidx);
			String depth = request.getParameter("depth");
			int depth_int = Integer.parseInt(depth);
			String level_  = request.getParameter("level_");
			int level_int = Integer.parseInt(level_);
			
			BoardVo bv = new BoardVo();
			bv.setBidx(bidx_int);
			bv.setOriginbidx(originbidx_int);
			bv.setDepth(depth_int);
			bv.setLevel_(level_int);		
			
			request.setAttribute("bv", bv);
		
			String path ="/board/boardReply.jsp";
			 //ȭ��뵵�� �ּҴ� ������� �佺�ؼ� �ش� ���ּҷ� ������ ���������� �־� �佺����
			 RequestDispatcher rd = request.getRequestDispatcher(path);
			 rd.forward(request, response);
		}else if(location.equals("boardReplyAction.do")) {
			
			String bidx  = request.getParameter("bidx");
			int bidx_int = Integer.parseInt(bidx);
			String originbidx  = request.getParameter("originbidx");
			int originbidx_int = Integer.parseInt(originbidx);
			String depth = request.getParameter("depth");
			int depth_int = Integer.parseInt(depth);
			String level_  = request.getParameter("level_");
			int level_int = Integer.parseInt(level_);
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			String pwd = request.getParameter("pwd");
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			int midx = 0;
			HttpSession session = request.getSession();
			midx = (int)session.getAttribute("midx");
			
			BoardVo bv = new BoardVo();
			bv.setBidx(bidx_int);
			bv.setOriginbidx(originbidx_int);
			bv.setDepth(depth_int);
			bv.setLevel_(level_int);		
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);			
			bv.setPwd(pwd);
			bv.setIp(ip);
			bv.setMidx(midx);
			
			int value=0;
			//ó���ϴ� �޼ҵ带 �����
			
			BoardDao bd = new BoardDao();  //��ü����
			value = bd.boardReply(bv);			
			
			if (value !=0) {
				String path = request.getContextPath()+"/board/boardList.do";
				response.sendRedirect(path);				
			}else {
				String path = request.getContextPath()+"/board/boardReply.do?bidx="+bidx+"&originbidx="+originbidx+"&depth="+depth+"&level_="+level_+"";
				response.sendRedirect(path);				
			}		
			
			
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
