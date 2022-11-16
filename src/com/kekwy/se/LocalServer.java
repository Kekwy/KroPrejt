package com.kekwy.se;

import com.kekwy.se.data.DataStruct;
import com.kekwy.se.data.IOPort;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作为本地服务的访问入口，向主控器呈递不同来源的用户请求。
 * <p>
 * 需要注意的是，由于暂不支持文件拷贝，该服务并不可以直接为远程用户提供服务，
 * 可以考虑编写一个 web 服务后端作为 ViewController（详见设计分析与实验报告）
 * 实现该功能。但是由于可以直接指定服务主机上的源码文件进行编译执行，显然存在安全漏洞。
 * 若用户上传一个病毒源码，然后被我们的程序执行，可以很容易的获取一个反弹 shell
 * 甚至进一步完成 root 提权。
 * <p>
 * 故在为远程用户提供服务前，首先需要解决软件安全性问题。
 * <p>
 * 且根据当前的需求分析，远程服务并不是我们目前的主要应用场景。
 * <hr>
 * 将在未来软件更新过程中尽快更换为基于 Netty NIO 框架的实现。
 */
public class LocalServer {

    private static final int DEFAULT_SERVER_PORT = 10086;
    private final int serverPort;
    private final ServerSocket serverSocket;

    public LocalServer(int serverPort) throws IOException {
        this.serverPort = serverPort;
        serverSocket = new ServerSocket(serverPort);
    }

    public LocalServer() throws IOException {
        this.serverPort = DEFAULT_SERVER_PORT;
        serverSocket = new ServerSocket(serverPort);
    }

    private boolean active = true;

    public void stop() {
        active = false;
    }

    private final ExecutorService exec = Executors.newCachedThreadPool();

    public IOPort<DataStruct> ioPort = new IOPort<>();

    private class Handler extends Thread {
        private final Socket clientSocket;
        private final UUID uuid = UUID.randomUUID();

        public UUID getUUID() {
            return uuid;
        }

        private final BufferedWriter bfWr;

        Handler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                bfWr = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int requestNum = 0;

        private final Object mutex = new Object();

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String request;
                while (!Objects.equals(request = in.readLine(), "\r")) {
                    synchronized (mutex) {
                        requestNum++;
                    }
                    DataStruct data = new DataStruct(uuid, null);
                    data.setExtra(request);
                    ioPort.send(data, MainController.getMainController().ioPort);
                    // MainController.getMainController().ioPort.post(data);
                }
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public boolean send(String response) {

            try {
                bfWr.write(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            synchronized (mutex) {
                requestNum -= 1;
                return requestNum == 0;
            }
        }

        public void close() {
            active = false;
            try {
                bfWr.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private final Map<UUID, Handler> handlerMap = new HashMap<>();

    public void start() {
        new Thread(this::accept).start();
        new Thread(this::response).start();
    }

    public void accept() {
        while (active) {
            try {
                Socket clientSocket = serverSocket.accept();
                Handler handler = new Handler(clientSocket);
                handlerMap.put(handler.getUUID(), handler);
                exec.execute(handler);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void response() {
        while (active) {
            DataStruct data = ioPort.get();
            if(data == null) {
                stop();
                return;
            } else {
                UUID uuid = data.getUUID();
                Handler handler = handlerMap.get(uuid);
                if(handler.send(data.getExtra() + "\r")) {
                    handler.close();
                    handlerMap.remove(uuid);
                }
            }
        }
    }

}
