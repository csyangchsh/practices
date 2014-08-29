package com.csyangchsh.fileserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @Author csyangchsh
 * Date: 14/7/22
 */
public class ConnectionHandler implements Runnable{

    static File ROOT_DIR;

    private Socket client;

    public ConnectionHandler(Socket socket) {
        client = socket;
    }

    @Override
    public void run() {

       try {
           Request request = new Request(client.getInputStream());
           Response response = new Response(request);
           response.write(client.getOutputStream());
       } catch (IOException e) {
           System.out.println("Error in processing request...");
       } finally {
           try {
               client.close();
           } catch (IOException e) {
               System.out.println("Error in closing connection...");
           }
       }

    }

    public static void init(File root) {
        ROOT_DIR = root;
    }

    public static void process(Socket socket) {
        new Thread(new ConnectionHandler(socket)).start();
    }
}

class Request {
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";

    String uri;
    String method;

    public Request(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String query = reader.readLine();
        parseRequestLine(query);
        if(isPostRequest() && uri.equalsIgnoreCase("/upload")) {
            do {


            } while (reader.ready());
        }
    }

    private void parseRequestLine(String query) {
        StringTokenizer tokenizer = new StringTokenizer(query);
        method = tokenizer.nextToken();
        uri = tokenizer.nextToken();
    }

    public boolean isGetRequest() {
        return GET_METHOD.equalsIgnoreCase(method);
    }

    public boolean isPostRequest() {
        return POST_METHOD.equalsIgnoreCase(method);
    }
}

class Response {
    public static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    public static final String HTML_MIME_TYPE = "text/html";

    static final String HTML_START ="<html><title>File List</title><body>";
    static final String HTML_END ="</body></html>";

    String responseString;
    String contentType;
    String status;
    long contentLength;
    FileInputStream file;

    public Response(Request request) {
        if(request.isGetRequest()) {
            String uri = request.uri;
            if (uri.equals("/")) {
                buildFileList();
            } else {
                buildFile(uri.substring(1));
            }

        } else if(request.isPostRequest()) {
            buildFileList();
        }
    }

    private void buildFileList() {
        String[] files = ConnectionHandler.ROOT_DIR.list();
        int len = files.length;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            File curFile = new File(ConnectionHandler.ROOT_DIR, files[i]);
            if (curFile.isDirectory()) {
                sb.append(files[i]);
                sb.append("<br />");
            } else {
                sb.append("<a href=\"" + "/" + files[i] + "\">" + files[i] + "</a>");
                sb.append("<br />");
            }

        }
        responseString = HTML_START + sb.toString() + HTML_END;
        contentLength = (long) responseString.length();
        contentType = Response.HTML_MIME_TYPE;
        status = "HTTP/1.1 200 OK";
    }

    private void buildFile(String fileName) {
        try {
            File f = new File(fileName);
            file = new FileInputStream(f);
            contentLength = f.length();
            contentType = Response.DEFAULT_MIME_TYPE;
            status = "HTTP/1.1 200 OK";

        } catch (FileNotFoundException e) {
            System.out.println("File not found...");
            responseString = HTML_START + "File not found" + HTML_END;
            contentLength = (long) responseString.length();
            contentType = Response.HTML_MIME_TYPE;
            status = "HTTP/1.1 404 Not Found";
        }
    }

    public void write(OutputStream out) throws IOException {
        DataOutputStream output = new DataOutputStream(out);
        output.writeBytes(status + "\r\n");
        output.writeBytes("Content-Type: " + contentType + "\r\n");
        output.writeBytes("Content-Length: " + contentLength + "\r\n");
        output.writeBytes("Connection: close\r\n");
        output.writeBytes("Server: MiniFileServer\r\n");
        output.writeBytes("\r\n");
        if(file != null) {
            byte[] buffer = new byte[1024] ;
            int bytesRead;
            while ((bytesRead = file.read(buffer)) != -1 ) {
                output.write(buffer, 0, bytesRead);
            }
            file.close();
        }else {
            output.writeBytes(responseString);
        }

        output.flush();
        output.close();
    }
}


