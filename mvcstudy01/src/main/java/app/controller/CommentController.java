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

import app.dao.CommentDao;
import app.domain.CommentVo;

@WebServlet("/CommentController")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String location;

	public CommentController(String location) {
		this.location = location;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (location.equals("commentList.do")) {

			CommentDao cd = new CommentDao();
			ArrayList<CommentVo> list = cd.commentSelectAll();
			
			int listCnt = list.size();
			int cidx = 0;
			String cwriter = "";
			String ccontents = "";
			String cwriteday = "";
			int midx = 0;
			String str = "";
			
			for(int i=0; i< listCnt; i++){
				cidx = list.get(i).getCidx();
				cwriter = list.get(i).getCwriter();
				ccontents = list.get(i).getCcontents();
				cwriteday = list.get(i).getCwriteday();
				midx = list.get(i).getMidx();
				
				String comma = "";
				if(i == listCnt-1) { //마지막 횟수이면
					comma = "";
					
				}else {
					comma = ",";
				}
				str = str +"{\"cidx\":\""+cidx+"\",\"cwriter\":\""+cwriter+"\",\"ccontents\":\""+ccontents+"\",\"cwriteday\":\""+cwriteday+"\",\"midx\":\""+midx+"\"}"+comma;
			}

			// json 파일형식의 문서를 담는다
			 // str = "[{\"nm\":\"홍길동\"},{\"nm\":\"김길똥\"}]";

			PrintWriter out = response.getWriter();
			out.println("["+str+"]");

		}else if(location.equals("commentWrite.do")) {
			
			String bidx = request.getParameter("bidx");
			String midx = request.getParameter("midx");
			String cwriter = request.getParameter("cwriter");
			String ccontents = request.getParameter("ccontents");
			
			CommentVo cv = new CommentVo();
			cv.setBidx(Integer.parseInt(bidx));
			cv.setMidx(Integer.parseInt(midx));
			cv.setCwriter(cwriter);
			cv.setCcontents(ccontents);
			
			int value=0;
			//댓글입력 메소드 만든다
			CommentDao cd = new CommentDao();
			value = cd.commentInsert(cv);
			
			String str ="{\"value\":\""+value+"\"}";
			
			PrintWriter out = response.getWriter();
			out.println(str);			
			
		}else if(location.equals("commentDelete.do")) {
			
			String cidx = request.getParameter("cidx");
			//System.out.println("cidx?"+cidx);
			int value = 0;
			
			CommentDao cd = new CommentDao();
			value =cd.commentDelete(Integer.parseInt(cidx));
			
			//System.out.println("value?"+value);
			String str ="{\"value\":\""+value+"\"}";
			PrintWriter out = response.getWriter();
			out.println(str);
	
		}
	

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}