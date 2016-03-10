package usee.com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import usee.com.service.MessageService;
import usee.com.utils.LogUtils;

/**
 * Servlet implementation class GetMessagesList
 */
@WebServlet("/GetMessageList")
public class GetMessageList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(GetMessageList.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMessageList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String lon = request.getParameter("lon");
		String lat = request.getParameter("lat");
		String address = request.getParameter("location");
		if (null != address) {
			address = new String(address.getBytes("iso8859-1"), "utf-8");
		}
		// System.out.println(address);
		String kind = request.getParameter("kind");
//		if(null!=kind){
//			kind=new String(kind.getBytes("iso8859-1"), "utf-8");
//		}
		System.out.println(kind);
		logger.info(LogUtils.logRequest(request));
		MessageService ms = new MessageService();
		String result = ms.getMessages(lon, lat, address, kind);
		PrintWriter out = response.getWriter();
		out.print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String lon = request.getParameter("lon");
		String lat = request.getParameter("lat");
		// String kind = new String(request.getParameter("kind").getBytes(
		// "iso8859-1"), "utf-8");
		String address = request.getParameter("location");
		String kind = request.getParameter("kind");
		// logger.info(LogUtils.logRequest(request));
		MessageService ms = new MessageService();
		String result = ms.getMessages(lon, lat, address, kind);
		PrintWriter out = response.getWriter();
		out.print(result);
	}

}
