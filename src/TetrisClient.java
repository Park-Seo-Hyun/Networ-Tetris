import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class TetrisClient extends Thread {
	private String host;
	private int port;
	private  BufferedReader  i;
    private  PrintWriter     o;
    private TetrisNetworkCanvas netCanvas;
    private TetrisCanvas tetrisCanvas;
    private TetrisNetworkPreview netPreview;
    private TetrisPreview preview;
    private String key;
    public TetrisClient(TetrisCanvas tetrisCanvas, TetrisPreview preview, TetrisNetworkCanvas netCanvas, TetrisNetworkPreview netPreview, String host, int port) {
    	this.tetrisCanvas = tetrisCanvas;
    	this.preview = preview;
    	this.netCanvas = netCanvas;
    	this.netPreview = netPreview;
    	this.host = host;
    	this.port = port;
    	
    	UUID uuid = UUID.randomUUID();
		key = uuid.toString()+";";
		System.out.println("My key: "+key);
    }
    
    public void send() {
    	String data = tetrisCanvas.getData().saveNetworkData();
    	String current;
    	int next = tetrisCanvas.getNewBlock().getType();
    	int score = preview.getScore();
    	int line = preview.getLine();
    	int Hold = 0;
    	if(tetrisCanvas.current != null) {
    		current = tetrisCanvas.current.currentPush();
    		if(tetrisCanvas.Hold != null)
    			Hold = tetrisCanvas.getHold().getType();
    		System.out.println("send: "+data);
    		o.println(key + data + ';' + current + ";" + next + ";"
    		+ score + ";" + line + ";" + Hold);
    	}
    }
    
	public void run() {
		System.out.println("client start!");
		Socket s;
		try {
			s = new Socket(host, port);
			InputStream ins = s.getInputStream();
			OutputStream os = s.getOutputStream();
			i = new BufferedReader(new InputStreamReader(ins));
			o = new PrintWriter(new OutputStreamWriter(os), true);
			
			while (true) {
				String line = i.readLine();
				if(line.length() != 0)
				{
					String[] parsedData = line.split(";");
					String checkKey = parsedData[0]+";";
					if(!checkKey.equals(key)) {
						netCanvas.getData().loadNetworkData(parsedData[1]);
						netCanvas.setCurrent(parsedData[2]);
						switch(Integer.parseInt(parsedData[3])){
		                  case 1:
		                     netPreview.setnewBlock(new Z_Piece(new TetrisData()));
		                     break;
		                  case 2:
		                     netPreview.setnewBlock(new S_Piece(new TetrisData()));
		                     break;
		                  case 3:
		                     netPreview.setnewBlock(new Bar(new TetrisData()));
		                     break;
		                  case 4:
		                     netPreview.setnewBlock(new O_Piece(new TetrisData()));
		                     break;
		                  case 5:
		                     netPreview.setnewBlock(new El(new TetrisData()));
		                     break;
		                  case 6:
		                     netPreview.setnewBlock(new Tee(new TetrisData()));
		                     break;
		                  case 7:
		                     netPreview.setnewBlock(new J_Piece(new TetrisData()));
		                     break;
		                  default:
		                     netPreview.setnewBlock(null);
		                     break;
		                  }
						netPreview.setScore(Integer.parseInt(parsedData[4]));
						netPreview.setLine(Integer.parseInt(parsedData[5]));
						switch(Integer.parseInt(parsedData[6])){
		                  case 1:
		                     netPreview.setHold(new Z_Piece(new TetrisData()));
		                     break;
		                  case 2:
		                     netPreview.setHold(new S_Piece(new TetrisData()));
		                     break;
		                  case 3:
		                     netPreview.setHold(new Bar(new TetrisData()));
		                     break;
		                  case 4:
		                     netPreview.setHold(new O_Piece(new TetrisData()));
		                     break;
		                  case 5:
		                     netPreview.setHold(new El(new TetrisData()));
		                     break;
		                  case 6:
		                     netPreview.setHold(new Tee(new TetrisData()));
		                     break;
		                  case 7:
		                     netPreview.setHold(new J_Piece(new TetrisData()));
		                     break;
		                  default:
		                     netPreview.setHold(null);
		                     break;
		                  }
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
