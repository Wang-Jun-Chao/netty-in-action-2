package nia.chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: 王俊超
 * Date: 2017-10-12 23:21
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class BlockingIoExample {
    public void serve(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request;
        String response;
        while ((request = in.readLine()) != null) {
            if ("Done".endsWith(request)) {
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
