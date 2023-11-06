import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler extends Thread {
	// 클라이언트와의 소켓 연결.
    private Socket s; 
 // 입력 스트림을 읽기 위한 BufferedReader.
    private BufferedReader i; 
 // 출력 스트림을 사용하여 데이터 전송.
    private PrintWriter o; 
 // Tetris 게임 서버의 레퍼런스.
    private TetrisServer server; 

    public ServerHandler(TetrisServer server, Socket s) throws IOException {
    	 // 소켓 설정
        this.s = s;
     // 서버 설정
        this.server = server; 

        // 클라이언트와의 통신을 위한 입력 및 출력 스트림 설정.
        InputStream ins = s.getInputStream();
        OutputStream os = s.getOutputStream();
        i = new BufferedReader(new InputStreamReader(ins));
        o = new PrintWriter(new OutputStreamWriter(os), true);
    }

    public void run() {
        try {
        	// 서버에 현재 핸들러 등록.
            while (true) {
            server.register(this); 
            	// 클라이언트로부터 메시지 읽기.
                String msg = i.readLine(); 
                // 읽은 메시지를 모든 클라이언트에게 브로드캐스트.
                broadcast(msg); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

     // 핸들러 등록 해제.
        server.unregister(this); 
        try {
        	// 입력 스트림 닫기.
            i.close(); 
            // 출력 스트림 닫기.
            o.close();
         // 소켓 닫기
            s.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void println(String message) {
    	 // 메시지를 클라이언트로 출력.
        o.println(message);
    }

    protected void broadcast(String message) {
    	 // 메시지를 모든 클라이언트에게 브로드캐스트.
        server.broadcast(message);
    }
}