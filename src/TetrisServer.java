import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TetrisServer extends Thread {
	// 클라이언트 핸들러 목록을 저장하는 벡터.
	private Vector<ServerHandler> handlers; 
	private int port; // 서버 포트 번호.

	// 생성자: 포트 번호를 인자로 받아 초기화.
	public TetrisServer(int port) {
		this.port = port;
	}
	
	// 서버 스레드 실행 메서드.
	public void run () {
		ServerSocket server = null;
		try {
			// 서버 소켓 생성하기.
			server = new ServerSocket (port); 
			// 클라이언트 핸들러 벡터 초기화하기.
			handlers = new Vector<ServerHandler>(); 
			System.out.println("서버가 준비되었습니다.");
			while (true) {
				// 클라이언트 연결 대기하기.
				Socket client = server.accept(); 
				System.out.println("접속한 클라이언트: " + client.getInetAddress() + ":" + client.getPort());
				 // 클라이언트 핸들러 생성.
				ServerHandler c = new ServerHandler(this, client);
				c.start(); // 핸들러 스레드 시작
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// 클라이언트 핸들러를 등록하는 메서드.
	public void register(ServerHandler c) {
		handlers.addElement(c);
	}
	
	// 클라이언트 핸들러를 등록 해제하는 메서드.
	public void unregister(Object o) {
		handlers.removeElement(o);
	}

	// 모든 클라이언트에게 메시지를 브로드캐스트하는 메서드.
	public void broadcast(String message) {
		synchronized (handlers) {
			int n = handlers.size();
			for(int i = 0; i < n; i++) {
				ServerHandler c = handlers.elementAt(i);
				try {
					c.println(message);
				  // 예외일 경우.
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}