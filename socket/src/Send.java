import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable {
    private BufferedReader bufferedReader = null;

    private DataOutputStream dataOutputStream = null;

    private boolean isRunning = true;
    private String name; //用户名称

    /**
     * 构造方法
     * @param socket
     * @param name
     */
    public Send(Socket socket, String name){
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in)); //输入
            dataOutputStream = new DataOutputStream(socket.getOutputStream()); //输出
            this.name = name;
            //当建立链接之后，发送姓名给服务端
            send(name);

        } catch (IOException e) {
            //e.printStackTrace();
            isRunning = false;
            try {
                dataOutputStream.close();
                bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    public String getConsoleData(){
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            isRunning = false;
            try {
                bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 发送信息的方法
     */
    public void send(String msg){
        try {
            if (msg!=null && !msg.equals("")) {
                dataOutputStream.writeUTF(msg);
                dataOutputStream.flush();
            }

        } catch (IOException e) {
            isRunning = false;
            try {
                dataOutputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        while (isRunning){
            send(getConsoleData());
        }

    }
}
