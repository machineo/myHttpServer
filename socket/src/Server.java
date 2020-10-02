
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    List<MyChannel> myChannelList = new ArrayList<>();

    public static void main(String[] args) throws Exception{

        new Server().start();
    }

    public void start() throws Exception{

        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            //接收客户端连接，阻塞式
            Socket socket = serverSocket.accept();
            System.out.println("一个新用户建立连接！！");
            MyChannel myChannel = new MyChannel(socket);
            myChannelList.add(myChannel);//将创建的客户端通道集中管理

            new Thread(myChannel).start();//开启通道

        }
    }

    //为每一个客户端开辟一条线程
    class MyChannel implements Runnable{
        private DataInputStream dataInputStream = null;
        private DataOutputStream dataOutputStream =null;

        private boolean isRunning = true;

        private String name;//用户名称

        //初始化信息
        public MyChannel(Socket socket){
            try {
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                this.name = dataInputStream.readUTF();
                send("欢迎进入聊天室");
                sendOthers(name+"加入了聊天室",false);
            } catch (IOException e) {
                try {
                    dataOutputStream.close();
                    dataInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                isRunning = false;
            }
        }

        public String recive(){
            String msg = "";
            try {
                msg = dataInputStream.readUTF();
            } catch (IOException e) {
                try {
                    dataInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                CloseUtils.CloseAll(dataInputStream,dataOutputStream);
                isRunning = false;
                myChannelList.remove(this);
            }
            System.out.println("msg: "+ msg);
            return msg;
        }


        /**
         * 发送信息
         * @param msg
         */
        public void send(String msg){
            try {
                dataOutputStream.writeUTF(msg);
            } catch (IOException e) {
                isRunning = false;
                try {
                    dataOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                myChannelList.remove(this);
            }
        }


        public void sendOthers(String msg,Boolean isUser){

            String name = this.name;//说话的人的名字

            //解析发送的数据，判断是否是私聊
            if (msg.startsWith("@")&&msg.indexOf(":")>-1&&isUser){

                //被@的人的名字
                String username = msg.substring(1, msg.indexOf(":"));

                //发送的话
                String content = msg.substring(msg.indexOf(":") + 1);

                for (MyChannel myChannel : myChannelList){
                    if (myChannel.name.equals(username)){
                        myChannel.send(name+"悄悄的跟你说"+content);
                    }
                }


            }else {
                for (MyChannel myChannel : myChannelList) {
                    if (myChannel == this && isUser) {
                        myChannel.send("我说:" + msg);
                        continue;
                    }
                    if (isUser) {//如果是用户说话，把名字加上
                        myChannel.send(name + "说:" + msg);
                    }
                    if (!isUser) {//如果是系统公告，就直接打印
                        myChannel.send(msg);
                    }

                }
            }
        }
        @Override
        public void run() {
            while (isRunning){
                sendOthers(recive(),true);
            }
        }
    }

}
