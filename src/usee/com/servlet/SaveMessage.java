package usee.com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import usee.com.service.CommentService;
import usee.com.service.MessageService;
import usee.com.utils.LogUtils;

/**
 * Servlet implementation class SaveMessage
 */
@WebServlet("/SaveMessage")
public class SaveMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SaveMessage.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveMessage() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String lon=request.getParameter("lon");
		String lat=request.getParameter("lat");
		String deadline=request.getParameter("deadline");
		String devid=request.getParameter("devid");
		String kind=request.getParameter("kind");
		String content=request.getParameter("content");
		String creattime=request.getParameter("creattime");
		logger.info(LogUtils.logRequest(request));
		MessageService ms=new MessageService();
		boolean flag=ms.saveMessage(content, lon, lat, deadline, devid, kind,creattime);
		JSONObject result=new JSONObject();
		if(flag==true){
			 result.put("error", 0) ;
			 result.put("result", "success");
		}
		else {
			 result.put("error", 1);
			 result.put("result", "error");
		}
		PrintWriter out = response.getWriter();
		out.print(result.toString());
	}

}
