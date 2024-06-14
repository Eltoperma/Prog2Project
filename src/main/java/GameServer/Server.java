package GameServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private boolean host;
    private ServerSocket serverSocket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public void setPort(int port) throws Exception {
        if(port >1023 && port < 49151) this.port = port;
        else throw new Exception("Out of Port range");
    }
    Server(boolean host){
        this.host = host;

    }
    public void startServer(){
        try{
            serverSocket = new ServerSocket(port);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        


    }

}
