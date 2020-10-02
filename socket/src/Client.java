import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{

        Socket socket = new Socket("localhost", 8888);

        System.out.println("请输入名称：");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String name = bufferedReader.readLine();//得到名称
      /*  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(bufferedReader.readLine());
        dataOutputStream.flush();*/
        new Thread(new Send(socket,name)).start();

        new Thread(new Receive(socket)).start();

    }
}
