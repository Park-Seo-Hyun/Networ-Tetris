public class El extends Piece {
    // El 클래스의 생성자
    public El(TetrisData data) {
        // Piece 클래스의 생성자 호출.
        super(data, 5, 4);
        // El 모양 테트리스 조각의 좌표 설정.
        // 첫 번째 블록의 열 좌표
        c[0] = 0; 
        // 첫 번째 블록의 행 좌표
        r[0] = 0;
        // 두 번째 블록의 열 좌표
        c[1] = -1;
        // 두 번째 블록의 행 좌표
        r[1] = 0; 
        // 세 번째 블록의 열 좌표
        c[2] = -1;
        // 세 번째 블록의 행 좌표
        r[2] = 1;
        // 네 번째 블록의 열 좌표
        c[3] = 1; 
        // 네 번째 블록의 행 좌표
        r[3] = 0;
    }

//    // 조각의 유형을 나타내는 메서드
//    public int getType() {
//    	 // El 모양 테트리스 조각을 나타내는 유형 코드
//        return 5;
//    }
//
//    // 회전 유형을 나타내는 메서드
//    public int roteType() {
//    	 // El 모양 테트리스 조각의 회전 유형 코드
//        return 4;
//    }
}