
public class TetrisData {
	public static final int ROW = 20;
	public static final int COL = 10;
	
	private int data[][];  // ROW x COL 의 배열
	private int line;      // 지운 줄 수
	
	public TetrisData() {
		data = new int[ROW][COL];
	}
	
	public int getAt(int x, int y) {
		if(x <0 || x >= ROW || y < 0 || y >= COL)
			return 0;
		return data[x][y];
	}
	
	public void setAt(int x, int y, int v) {
		// Piece가 설치될 경유 hold 기능 활성화함.
		TetrisCanvas.isHold = false; 
		data[x][y] = v;
	}
	
	public int getLine() {
		return line;
	}
	
	
	// 동기화된 메서드로, 줄을 지우는 기능 수행함.
	public synchronized void removeLines() {
		 // 레이블 NEXT로 반복문을 제어.
	    NEXT:
	    for (int i = ROW - 1; i >= 0; i--) {
	    	 // 특정 줄이 완료되었는지 확인하기 위한 플래그.
	        boolean done = true;

	        // 해당 행의 모든 열을 확인하여 줄이 완성되었는지 확인함.
	        for (int k = 0; k < COL; k++) {
	            if (data[i][k] == 0) {
	            	 // 줄이 완성되지 않음.
	                done = false;
	             // 다음 줄을 확인하기 위해 레이블 NEXT로 이동함.
	                continue NEXT; 
	            }
	        }

	        // 해당 행이 완전히 채워졌을 때.
	        if (done) {
	        	TetrisBgm_one.Play_Bgm("success.wav", -10); 
	        	// 완성된 줄 개수 증가함.
	            line++; 

	            // 위의 줄을 한 줄씩 아래로 이동함.
	            for (int x = i; x > 0; x--) {
	                for (int y = 0; y < COL; y++) {
	                    data[x][y] = data[x - 1][y];
	                }
	            }

	            // 맨 위 줄 초기화 (0으로 설정).
	            if (i != 0) {
	                for (int y = 0; y < COL; y++) {
	                    data[0][y] = 0;
	                }
	            }

	            // 줄을 지우고 난 후에 다시 같은 행을 확인하기 위해 i를 증가시킴.
	            i++;
	        }
	    }
	}
	
	 // data 배열 초기화함.
	public void clear() {  
		for(int i=0; i < ROW; i++){
			for(int k = 0; k < COL; k++){
				data[i][k] = 0;
			}
		}
	}
	
	// data 배열 내용 출력함.
	public void dump() {   
		for(int i=0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				System.out.print(data[i][k] + " ");
			}
			System.out.println();
		}
	}
	
	public void loadNetworkData(String input) {
		clear();
		String[] dataArray = input.split(",");
		int count = 0;
		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				setAt(i, k, Integer.parseInt(dataArray[count]));
				count++;
			}
		}
	}
	
	public String saveNetworkData() {
		StringBuilder output = new StringBuilder("");
		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				String data = String.valueOf(getAt(i, k));
				output.append(data+",");
			}
		}
		String result = output.toString();
		return result;
	}
}
