package cn.mm.servlet;


import cn.mm.server.Request;
import cn.mm.server.Response;
import cn.mm.server.Servlet;

public class LoginServlet extends Servlet {

	@Override
	public void doGet(Request request, Response response) {

		response.print("<html>\r\n" + 
				"<head>\r\n" + 
				"    <META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\r\n" + 
				"    <title>首页</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"登录成功,欢迎你\r\n" + request.getParameter("username")+
				"</body>\r\n" +
				"</html>");
		System.out.println("uuuuuuusername:::   " + request.getParameter("username"));
	}

	@Override
	public void doPost(Request request, Response response) {
		// TODO Auto-generated method stub
//		response.print("<html>\r\n" +
//				"<head>\r\n" +
//				"    <META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\r\n" +
//				"    <title>首页</title>\r\n" +
//				"</head>\r\n" +
//				"<body>\r\n" +
//				"登录成功,欢迎你\r\n" + request.getParameter("username")+
//				"  " + request.getParameter("password") +
//				"</body>\r\n" +
//				"</html>");
//		System.out.println("uuuuuuusername:::   " + request.getParameter("username"));
	}
}
