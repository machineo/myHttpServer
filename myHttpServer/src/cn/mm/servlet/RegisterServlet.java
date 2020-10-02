package cn.mm.servlet;


import cn.mm.server.Request;
import cn.mm.server.Response;
import cn.mm.server.Servlet;

public class RegisterServlet extends Servlet {

	@Override
	public void doGet(Request request, Response response) {
		response.print("<html>\r\n" + 
				"<head>\r\n" + 
				"    <META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\r\n" + 
				"    <title>注册成功页面</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"注册成功,欢迎你\r\n" + request.getParameter("username")+
				"</body>\r\n" + 
				"</html>");
	}

	@Override
	public void doPost(Request request, Response response) {
		// TODO Auto-generated method stub
		
	}

}
