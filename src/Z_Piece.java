public class Z_Piece extends Piece {
    public Z_Piece(TetrisData data) {
    	// Piece 클래스의 생성자 호출.
        super(data, 1, 4); 

        // Z 모양 테트리스 조각의 좌표 설정.
        c[0] = 0; r[0] = 0;
        c[1] = -1; r[1] = 0;
        c[2] = 0; r[2] = 1;
        c[3] = 1; r[3] = 1;
    }

//    public int getType() {
//    	// 조각의 유형을 나타내는 정수 반환.
//        return 1; 
//    }
//
//    public int roteType() {
//    	// 회전 유형을 나타내는 정수 반환.
//        return 4; 
//    }
}