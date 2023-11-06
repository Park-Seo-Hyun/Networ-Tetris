public class S_Piece extends Piece {
    // S_Piece 클래스의 생성자
    public S_Piece(TetrisData data) {
        // Piece 클래스의 생성자 호출.
        super(data, 2, 4);
        // S 모양 테트리스 조각의 좌표 설정.
        // 첫 번째 블록의 열 좌표.
        c[0] = 0; 
        // 첫 번째 블록의 행 좌표.
        r[0] = 1; 
        // 두 번째 블록의 열 좌표.
        c[1] = -1;
        // 두 번째 블록의 행 좌표.
        r[1] = 1;
        // 세 번째 블록의 열 좌표.
        c[2] = 0; 
        // 세 번째 블록의 행 좌표.
        r[2] = 0; 
        // 네 번째 블록의 열 좌표.
        c[3] = 1;
        // 네 번째 블록의 행 좌표.
        r[3] = 0;
    }

//    // 조각의 유형을 나타내는 메서드.
//    public int getType() {
//    	 // S 모양 테트리스 조각을 나타내는 유형 코드.
//        return 2;
//    }
//
//    // 회전 유형을 나타내는 메서드.
//    public int roteType() {
//    	 // S 모양 테트리스 조각의 회전 유형 코드.
//        return 4;
//    }
}