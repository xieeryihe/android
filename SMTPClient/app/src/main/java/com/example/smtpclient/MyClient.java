package com.example.smtpclient;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;


public class MyClient {
    private final String mailServer = "smtp.qq.com";
    int serverPort = 587;

    private String username = "2640182777@qq.com";// 输入自己的用户名对应的编码
    private String authorizationCode = "fjbzxoivfccxdjbc"; //此处不是自己的密码，而是开启SMTP服务时对应的授权码

    private String date = "";
    private String fromAddress = "2640182777@qq.com";
    private String toAddress = "653670584@qq.com";//我小号
    private String subject = "Test";
    private String content = "Hello";

    private ArrayList<String> toAddressList;

    public MyClient(){
        toAddressList = new ArrayList<>();
        toAddressList.add("653670584@qq.com");
        toAddressList.add("2640182777@qq.com");
    }

    public void setUsername(String username) {
        //用户名，相当于发送方的邮箱
        this.username = username;
    }
    public void setAuthorizationCode(String authorizationCode) {
        //设置授权码
        this.authorizationCode = authorizationCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getToAddressList() {
        return toAddressList;
    }

    public void setToAddressList(ArrayList<String> toAddressList) {
        this.toAddressList = toAddressList;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean runClient() throws IOException {

        //创建客户套接字并连接，状态码220
        Socket clientSocket = new Socket(mailServer,serverPort);//创建套接字并建立连接

        //定义输入输出流
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//从服务器输入读到
        PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream());//输出到服务器
        //查看连接结果
        printBufferIn(socketIn,1);



        //1.建立邮件连接
        socketOut.print("Helo Alice\r\n");//注意是\r\n
        socketOut.flush();//注意这里一定要刷新
        printBufferIn(socketIn,3);
        //再多输出一行就会卡住，因为输出到服务器的内容只返回了三条信息，再readline就会因为没有信息卡住


        //验证身份
        Base64.Encoder encoder = Base64.getEncoder();//编码器
        //构造验证信息
        String verifyInfo = String.format("\0%s\0%s",username,authorizationCode);//
        String str1 = encoder.encodeToString(verifyInfo.getBytes(StandardCharsets.US_ASCII));
        String verifyString = String.format("AUTH PLAIN %s\r\n",str1);
        //byte[] verifyBytes = verifyString.getBytes();//后来发现java不用以byte[]的形式编码print到socket上去
        socketOut.print(verifyString);
        socketOut.flush();
        String passInfo = "Authentication successful";
        boolean pass = printBufferIn(socketIn,1,passInfo);//打印结果并查看验证信息
        if (!pass){
            socketIn.close();
            socketOut.close();
            clientSocket.close();
            return false;
        }


        //2.标识发送方
        socketOut.print(String.format("Mail From:<%s>\r\n", fromAddress));
        socketOut.flush();
        printBufferIn(socketIn,1);

        //3.标识接收方
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< toAddressList.size(); i++){
            System.out.println("-----one mail send------");
            String tempToAddr = toAddressList.get(i);
            socketOut.print(String.format("Rcpt To:<%s>\r\n", tempToAddr));
            sb.append(tempToAddr).append(";");
            socketOut.flush();
            printBufferIn(socketIn,1);
        }


        //4.传输邮件数据
        socketOut.print("Data\r\n");//不能一写
        //socketOut.flush();//注意：这个时候千万不能flush，否则就认为Data段结束了

        //要传输数据的数据
        //设置邮件主题等
        socketOut.print(String.format("date:%s\r\n",date));
        socketOut.print(String.format("subject:%s\r\n",subject));
        socketOut.print(String.format("from:%s\r\n",fromAddress));
        toAddress = sb.substring(0,sb.length()-1);
        socketOut.print(String.format("to:%s\r\n",toAddress));
        //这个好像不用设置，反正群发也会在上面Rcpt里面设置正确收件地址
        //但是为了MIME格式，还是这么写了。
        //设置邮件格式
        socketOut.print("Content-Type:text/plain;charset=\"utf-8\"\r\n");
        socketOut.print("\r\n");//必须要输入一个换行才能正常输入正文
        //邮件正文
        //System.out.println("text begin\n");

        socketOut.print(String.format("%s\r\n", content));

        //System.out.println("write success\n");

        //邮件写完一次flush
        socketOut.flush();
        printBufferIn(socketIn,1);//354 End data with <CR><LF>.<CR><LF>.

        //最后输出到socket一个点. 加换行 ，且只能是一个点，别的字符数量都不行
        socketOut.print(".\r\n");//这也解释了为什么参考代码有一个endMsg = "\r\n.\r\n"
        socketOut.flush();
        printBufferIn(socketIn,1);//会输出  "250 OK: queued as."

        //5.结束邮件连接
        socketOut.print("QUIT\r\n");
        socketOut.flush();
        printBufferIn(socketIn,1);//221 Bye.

        //释放资源
        socketIn.close();
        socketOut.close();
        clientSocket.close();
        return true;
    }

    //从缓冲区读取n行，并检查是否包含验证信息verifyInfo，包含返回1，否则返回0
    public boolean printBufferIn(BufferedReader buf , int n, String verifyInfo) throws IOException {
        String line;
        boolean flag = false;
        if (verifyInfo==null|| verifyInfo.equals("")){//如果没有检索信息
            for (int i = 0;i<n;i++){
                line = buf.readLine();
                System.out.println(line);
            }
        } else { //否则就逐行检查
            for (int i = 0;i<n;i++){
                line = buf.readLine();
                if (line.contains(verifyInfo)){
                    flag = true;
                }
                System.out.println(line);
            }
        }
        System.out.println();
        return flag;
    }
    //重载函数，调用验证信息为null
    public boolean printBufferIn(BufferedReader buf , int n) throws IOException {
        return this.printBufferIn(buf,n,null);
    }

}
