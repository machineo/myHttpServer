import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable{
    //得到数据
    private DataInputStream dataInputStream = null;
    private boolean isRunning = true;

    public Receive(Socket socket){
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            isRunning =false;

            try {
                dataInputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public String recive(){
        String msg ="";
        try {
            msg = dataInputStream.readUTF();

        } catch (IOException e) {
            isRunning =false;
            try {
                dataInputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return msg;
    }

    @Override
    public void run() {
        while (isRunning){
            System.out.println(recive());
        }
    }
}
