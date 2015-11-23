package usee.com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import usee.com.service.CommentService;
import usee.com.utils.LogUtils;

/**
 * Servlet implementation class Praise
 */
@WebServlet("/Praise")
public class Praise extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Praise.class);     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Praise() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String messageid=request.getParameter("messageid");
		String devid=request.getParameter("devid");
		logger.info(LogUtils.logRequest(request));
//		System.out.println("?????");
//		System.out.println(request);
		CommentService cs=new CommentService();
		String result=cs.praise(devid,messageid);
		PrintWriter out = response.getWriter();
		out.print(result);
	}

}
