package com.csyangchsh.fileserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * @Author csyangchsh
 * Date: 14/7/22
 */
public class MiniFileServer {

    private final File rootDir;
    private final ServerSocket server;

    public MiniFileServer(File rootDir, int port) throws IOException {
        this.rootDir = rootDir;
        this.server = new ServerSocket(port);
    }

    public void process(){
        ConnectionHandler.init(this.rootDir);
        System.out.println("Server is listening on port 12121. Press CTRL-C to quit...");
        while (true) {
            try {
                ConnectionHandler.process(server.accept());
            } catch (IOException e) {
                System.out.println("Error in processing connection...");
            }
        }
    }

    public static void main(String[] args) {
        File root = new File(".");
        try {
            MiniFileServer server = new MiniFileServer(root, 12121);
            server.process();
        } catch (IOException e) {
            System.out.println("Startup server failed...");
        }
    }

}
