import java.awt.Point;

public abstract class Piece{
	// 방향 지정.
	final int DOWN = 0;  
	final int LEFT = 1;
	final int RIGHT = 2;
	// Y축 좌표 배열.
	protected int r[]; 
	// X축 좌표 배열.
	protected int c[];   
	// 테트리스 내부 데이터.
	protected TetrisData data;  
	// 조각의 중심 좌표.
	protected Point center; 
	protected Piece next;
	protected int type;
	protected int roteType;
	
	public Piece(TetrisData data, int type, int roteType) {
		// 조각의 Y축 좌표 배열 초기화.
	    r = new int[4]; 
	    // 조각의 X축 좌표 배열 초기화.
	    c = new int[4];  
	    // 테트리스 데이터를 받아와서 저장.
	    this.data = data; 
	    // 조각의 초기 중심 좌표를 (5, 0)으로 설정.
	    center = new Point(5, 0);  
	    this.type = type;
	    this.roteType = roteType;
	}
	
	
	public int getType() {
		return type;
	}
	public int roteType() {
		return roteType;
	}
 
	public int getX() { 
		// 조각의 중심 X축 좌표를 반환
	    return center.x;  
	}

	public int getY() { 
		// 조각의 중심 Y축 좌표를 반환
	    return center.y;  
	}
	
	// 조각의 현재 위치 정보를 게임 데이터에 복사하고 게임 종료 상황인지 확인.
	// 만약 조각의 Y축 좌표가 게임 화면 위로 벗어나면 게임 종료 상황으로 판단.
	// 이후, 조각의 위치 정보를 게임 데이터에 복사하여 조각을 화면에 고정.
	public boolean copy() {
		// 게임 종료 여부.
	    boolean isGameOver = false; 
	    // 현재 조각의 중심 X축 좌표를 얻음.
	    int x = getX(); 
	    // 현재 조각의 중심 Y축 좌표를 얻음.
	    int y = getY(); 

	    // 게임 종료 상황인지 확인
	    if (getMinY() + y <= 0) {
	        isGameOver = true; // 조각의 Y축 좌표가 게임 화면 위로 벗어나면 게임 종료.
	    }

	    // 조각의 현재 위치 정보를 게임 데이터에 복사.
	    for (int i = 0; i < 4; i++) {
	        data.setAt(y + r[i], x + c[i], getType());
	    }

	    return isGameOver;
	}
 
	// 현재 조각이 다른 조각과 겹치는지를 판단.
	// 파라미터 dir에 따라 아래, 왼쪽, 오른쪽 방향으로 겹침 여부를 확인.
	public boolean isOverlap(int dir) {
		// 현재 조각의 중심 X축 좌표를 얻음.
	    int x = getX();
	    // 현재 조각의 중심 Y축 좌표를 얻음.
	    int y = getY(); 

	    switch (dir) {
	        // 아래 방향 확인.
	        case 0:
	            for (int i = 0; i < r.length; i++) {
	                if (data.getAt(y + r[i] + 1, x + c[i]) != 0) {
	                	// 아래 방향으로 겹친 경우 true 반환.
	                    return true; 
	                }
	            }
	            break;

	        // 왼쪽 방향 확인.
	        case 1:
	            for (int i = 0; i < r.length; i++) {
	                if (data.getAt(y + r[i], x + c[i] - 1) != 0) {
	                	// 왼쪽 방향으로 겹친 경우 true 반환.
	                    return true; 
	                }
	            }
	            break;

	        // 오른쪽 방향 확인.
	        case 2:
	            for (int i = 0; i < r.length; i++) {
	                if (data.getAt(y + r[i], x + c[i] + 1) != 0) {
	                	// 오른쪽 방향으로 겹친 경우 true 반환.
	                    return true; 
	                }
	            }
	            break;
	    }
	    // 겹치지 않은 경우 false 반환.
	    return false; 
	}
 
	//Y좌표의 최대값을 구해준다.
	public int getMinX() {
		int min = c[0];
		for(int i=1; i < c.length; i++) {
			if(c[i] < min) {
				min = c[i];
			}
		}
		return min;
	}
 
	
	public int getMaxX() {
		int max = c[0];
		for(int i=1; i < c.length; i++) {
			if(c[i] > max) {
				max = c[i];
			}
		}
		return max;
	}
 
	//Y좌표의 최소값을 구해준다.
	public int getMinY() {
		int min = r[0];
		for(int i=1; i < r.length; i++) {
			if(r[i] < min) {
				min = r[i];
			}
		}
		return min;
	}
 
	public int getMaxY() {
		int max = r[0];
		for(int i=1; i < r.length; i++) {
			if(r[i] > max) {
				max = r[i];
			}
		}
		return max;
	}

	// 아래로 이동시켜준다.
	public boolean moveDown() { 
	    if (center.y + getMaxY() + 1 < TetrisData.ROW) {
	        if (isOverlap(DOWN) != true) {
	            center.y++;
	        } else {
	            return true;
	        }
	    } else {
	        return true;
	    }

	    return false;
	}

	 // 왼쪽으로 이동시켜준다.	
	public void moveLeft() { 
		if(center.x + getMinX() -1 >= 0)
			if(isOverlap(LEFT) != true) {center.x--;}
			else return;
	}

	 // 오른쪽으로 이동시켜준다.
	public void moveRight() { 
		if(center.x + getMaxX() + 1 < TetrisData.COL)
			if(isOverlap(RIGHT) != true) {center.x++;}
			else return;
	}
	
	//스페이스바를 누르면 맨 아래로 이동시켜준다.
	public void spaceDown() { 
		for(int i = 0; i < 19; i ++) {
			this.moveDown();
		}
	}

	// 조각을 회전시키는 메서드
	public void rotate() {  
	    // 바닥에 닿지 않았을 때만 회전.
	    if (!isOnGround()) { 
	    	// 회전 유형을 얻어옴.
	        int rc = roteType(); 

	        if (rc <= 1) {
	        	// 회전 유형이 1 이하일 경우 회전하지 않고 메서드 종료.
	            return; 
	        } else if (rc == 2) {
	            // 회전 유형이 2일 경우, 조각을 4번 회전.
	            rotate4();
	            rotate4();
	            rotate4();
	        } else {
	            // 그 외의 경우, 조각을 4번 회전.
	            rotate4();
	        }
	    }
	}
	
	// 현재 조각이 바닥에 닿았는지 확인하는 메서드.
	public boolean isOnGround() {
		// 현재 조각의 중심 Y축 좌표를 얻음.
	    int y = center.y; 

	    for (int i = 0; i < 4; i++) {
	        // 조각의 각 부분(블록)에 대해 아래쪽에 블록이 있는지 확인.
	        if (data.getAt(y + r[i] + 1, center.x + c[i]) != 0) {
	            return true; // 아래에 블록이 있는 경우 (바닥에 닿았을 경우) true 반환.
	        }
	    }
	    // 아직 바닥에 닿지 않았을 경우 false 반환.
	    return false;
	}

	// 조각 회전2.
	public void rotate4() {   
	    // 저장된 좌표 배열을 회전시킵니다.
	    for (int i = 0; i < 4; i++) {
	        int temp = c[i];
	        c[i] = -r[i];
	        r[i] = temp;
	    }
	    
	    // 회전 후 조각이 게임 보드 경계를 벗어나지 않도록 조정합니다.
	    int minX = getMinX();
	    int maxX = getMaxX();
	    int minY = getMinY();
	    int maxY = getMaxY();
	    
	    // 왼쪽 경계를 벗어나면 오른쪽으로 이동시켜준다.
	    if (getX() + minX < 0) {
	        moveRight();
	    } 
	    // 오른쪽 경계를 벗어나면 왼쪽으로 이동시켜준다.
	    else if (getX() + maxX >= TetrisData.COL) {
	        moveLeft();
	    }
	    
	    // 위쪽 경계를 벗어나면 아래로 이동시켜준다.
	    if (getY() + minY < 0) {
	        center.y = -minY;
	    } 
	    // 아래쪽 경계를 벗어나면 위로 이동시켜준다.
	    else if (getY() + maxY >= TetrisData.ROW) {
	        center.y = TetrisData.ROW - 1 - maxY;
	    }
	}
	
	
	public String currentPush() {
		return r[0] + "." +
		r[1] + "." +
		r[2] + "." +
		r[3] + "." +
		c[0] + "." +
		c[1] + "." +
		c[2] + "." +
		c[3] + "." +
		center.x + "." +
		center.y + "." +
		type;
	}
		
	public void currentPull(String str) {
		String[] dataArr = str.split("\\.");
		this.r[0]= Integer.parseInt(dataArr[0]);
		this.r[1]= Integer.parseInt(dataArr[1]);
		this.r[2]= Integer.parseInt(dataArr[2]);
		this.r[3]= Integer.parseInt(dataArr[3]);
		this.c[0]= Integer.parseInt(dataArr[4]);
		this.c[1]= Integer.parseInt(dataArr[5]);
		this.c[2]= Integer.parseInt(dataArr[6]);
		this.c[3]= Integer.parseInt(dataArr[7]);
		this.center.x = Integer.parseInt(dataArr[8]);
		this.center.y = Integer.parseInt(dataArr[9]);
		this.type = Integer.parseInt(dataArr[10]);
		
	}

}
