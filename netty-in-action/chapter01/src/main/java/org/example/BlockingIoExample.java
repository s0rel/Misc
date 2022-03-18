package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 代码清单 1-1 阻塞 I/O 示例
 */
public class BlockingIoExample {
    public void serve(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        // ServerSocket 上的 accept() 将会一直阻塞到一个连接建立，随后返回一个新的 Socket 用于客户端和服务器之间的通信。
        // 该 ServerSocket 将继续监听传入的连接。
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        while ((request = in.readLine()) != null) {
            if ("Done".equals(request)) {
                break;
            }
            response = processRequest(request);
            out.println(response);
        }
    }

    private String processRequest(String request) {
        return "Processed";
    }
}
