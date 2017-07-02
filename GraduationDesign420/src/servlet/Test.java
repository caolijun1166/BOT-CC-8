package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//EE00 CC00 0200 1B00 0000 1E00 0000 FF00
		String humiTemp = (String)getServletContext().getAttribute("humiTemp");
		//必须要有非空判断，否侧启动服务器时会报空指针异常。
		if(humiTemp != null)
		{
			int humi = Integer.parseInt(humiTemp.split(" ")[3].split("00")[0], 16);
			int temp = Integer.parseInt(humiTemp.split(" ")[5].split("00")[0], 16);
			response.getWriter().append(temp+";"+humi);
		}
		else
			response.getWriter().append("0;0");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
