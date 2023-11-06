import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TetrisCanvas extends JPanel implements Runnable, KeyListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    // 그래픽스 함수를 사용하기 위한 클래스.
    private Graphics bufferGraphics = null;
    // bufferGraphics로 그림을 그릴 때 실제로 그려지는 가상 버퍼.
    private Image offscreen;
    // 화면의 크기를 저장하는 변수.
    private Dimension dim;
    private TetrisPreview preview;
    private MyTetris myTetris;
    
    protected Thread worker;
    protected TetrisData data;
    protected boolean stop, makeNew;
    protected Piece current;
    protected Piece next;
    protected Piece ghost; 
    protected boolean value;
    protected int line;
    // Tetris hold 기능 여부.
    protected static boolean isHold = false;
    protected int level = 1;
    
    public int score;
    public Piece Hold;

    public TetrisCanvas(MyTetris t) {
        this.myTetris = t;
        data = new TetrisData();
        addKeyListener(this);
        addComponentListener(this);
    }

    public void setTetrisPreview(TetrisPreview preview) {
        this.preview = preview;
    }

    // 버퍼 초기 함수.
    public void initBufferd() {
        dim = getSize();
        // 화면의 크기를 가져옴.
        System.out.println(dim.getSize());
        // 배경 색깔을 흰색으로 변경.
        setBackground(Color.white);
        // 화면 크기와 똑같은 가상 버퍼(이미지)를 생성.
        offscreen = createImage(dim.width, dim.height);
        // 가상 버퍼(이미지)로부터 그래픽스 객체를 얻어옴.
        bufferGraphics = offscreen.getGraphics();
    }

    // 현재 Hold된 블록 정보를 설정하고 TetrisPreview에 전달.
    public void setHoldBlockInfo(Piece holdBlockInfo) {
        this.Hold = holdBlockInfo;
        preview.setHoldBlockInfo(Hold);
    }

    // 게임 시작.
    public void start() {
        if (stop) {
            stop();
        }

        // TetrisData 객체를 초기화하여 score와 line을 리셋.
        data = new TetrisData();

        // TetrisPreview 객체에게 게임 데이터를 다시 설정.
        if (preview != null) {
            preview.setGameData(data);
        }
        // 데이터 초기화.
        data.clear();
        worker = new Thread(this);
        worker.start();
        makeNew = true;
        stop = false;
        // 이전에 울리던 노래를 멈추기.
        TetrisBgm.stop();
        // 새로운 노래 재생.
        TetrisBgm.Play_Bgm("TetrisBGM.wav", -20);
        requestFocus();
        repaint();

        // 게임 시작 시 현재 블록을 초기 위치로 설정.
        current = createBlock();
        createGhostBlock();
        next = createNextBlock();
        preview.setCurrentBlock(current);
        preview.setNextBlock(next);
    }

    public void stop() {
        stop = true;
        // 현재 블록 초기화
        current = null;
        
        // 음악 정지
        TetrisBgm.stop();
    }
    
    public void createGhostBlock() {
        if (current == null) {
        	 return;
        }  
            ghost = new Bar(data);
            
            // 현재 블록의 윙ㅊ이 및 회전 상태 복사함.
             for (int i = 0; i < 4; i++) {
                 ghost.r[i] = current.r[i];
                 ghost.c[i] = current.c[i];
             }

             // 현재 블록의 type을 복사함.
             ghost.type = current.getType();
             ghost.roteType = current.roteType();
             
             // 현재 블록의 위치를 복사함.
             ghost.center.x = current.center.x;
             ghost.center.y = current.center.y;

             while (!ghost.moveDown()) {
                System.out.println("");
               }
     }

    public void paint(Graphics g) {
        // 화면을 지운다. 지우지 않으면 이전 그림이 그대로 남아 잔상이 생김.
        bufferGraphics.clearRect(0, 0, dim.width, dim.height);

        // 쌓인 조각들 그리기.
        for (int i = 0; i < TetrisData.ROW; i++) {
            for (int k = 0; k < TetrisData.COL; k++) {
                if (data.getAt(i, k) == 0) {
                    bufferGraphics.setColor(Constant.getColor(data.getAt(i, k)));
                    bufferGraphics.draw3DRect(Constant.margin / 2 + Constant.w * k,
                            Constant.margin / 2 + Constant.w * i, Constant.w, Constant.w, true);
                } else {
                    bufferGraphics.setColor(Constant.getColor(data.getAt(i, k)));
                    bufferGraphics.fill3DRect(Constant.margin / 2 + Constant.w * k,
                            Constant.margin / 2 + Constant.w * i, Constant.w, Constant.w, true);
                }
            }
        }

        // ghost 블록 그리기
        if (ghost != null) {
            System.out.print("");
             for (int i = 0; i < 4; i++) {
                bufferGraphics.setColor(Color.gray);
                 bufferGraphics.fill3DRect(Constant.margin / 2 + Constant.w * (ghost.getX() + ghost.c[i]),
                         Constant.margin / 2 + Constant.w * (ghost.getY() + ghost.r[i]),
                         Constant.w, Constant.w, true);
             }
         }
        
        if (current != null) {
            // 현재 블록 그리기
            for (int i = 0; i < 4; i++) {
                bufferGraphics.setColor(Constant.getColor(current.getType()));
                bufferGraphics.fill3DRect(Constant.margin / 2 + Constant.w * (current.getX() + current.c[i]),
                        Constant.margin / 2 + Constant.w * (current.getY() + current.r[i]),
                        Constant.w, Constant.w, true);
            }
        } 
        
        // 가상 버퍼(이미지)를 원본 버퍼에 복사.
        g.drawImage(offscreen, 0, 0, this);

        myTetris.refresh();
    }
    


    // 테트리스 판의 크기를 지정하는 메서드.
    public Dimension getPreferredSize() {
        // 테트리스 판의 폭 (너비)를 계산. 각 블록의 너비에 열의 수를 곱하고, 상하좌우 여백을 추가.
        int tw = Constant.w * TetrisData.COL + Constant.margin;

        // 테트리스 판의 높이를 계산. 각 블록의 높이에 행의 수를 곱하고, 상하좌우 여백을 추가.
        int th = Constant.w * TetrisData.ROW + Constant.margin;

        // 계산된 폭과 높이를 사용하여 새로운 Dimension(차원) 객체를 생성하고 반환합니다.
        return new Dimension(tw, th);
    }

    private Piece createBlock() {
        int random = (int) (Math.random() * Integer.MAX_VALUE) % 7;
     // 0부터 6까지의 난수를 생성하여 무작위 블록 선택.
        switch (random) {
           // 블록 유형 0: 바 형태.
            case 0:
                current = new Bar(data);
                break;
            // 블록 유형 1: T자 형태.
            case 1:
                current = new Tee(data);
                break;
            // 블록 유형 2: L 형태.
            case 2:
                current = new El(data);
                break;
            // 블록 유형 3: J 형태.
            case 3:
                current = new J_Piece(data);
                break;
            // 블록 유형 4: Z 형태.
            case 4:
                current = new Z_Piece(data);
                break;
            // 블록 유형 5: S 형태.
            case 5:
                current = new S_Piece(data);
                break;
            // 블록 유형 6: O 형태.
            case 6:
                current = new O_Piece(data);
                break;
            // 그 외의 경우 (랜덤 값이 0에서 6 사이가 아닐 때).
            default:
                if (random % 2 == 0)
                   // 짝수인 경우 T자 형태 블록.
                    current = new Tee(data);
                else
                   // 홀수인 경우 L 형태 블록.
                    current = new El(data);
        }

        return current;
    }

    private Piece createNextBlock() {
        int random2 = (int) (Math.random() * Integer.MAX_VALUE) % 7;
        
        // 0부터 6까지의 난수를 생성하여 무작위 블록 선택.
        switch (random2) {
           // 블록 유형 0: 바 형태.
            case 0:
                next = new Bar(data);
                break;
            // 블록 유형 1: T자 형태.
            case 1:
                next = new Tee(data);
                break;
            // 블록 유형 2: L 형태.
            case 2:
                next = new El(data); 
                break;
            // 블록 유형 3: J 형태.
            case 3:
                next = new J_Piece(data); 
                break;
            // 블록 유형 4: Z 형태.
            case 4:
                next = new Z_Piece(data);
                break;
            // 블록 유형 5: S 형태.
            case 5:
                next = new S_Piece(data); 
                break;
            // 블록 유형 6: O 형태.
            case 6:
                next = new O_Piece(data);
                break;
            default:
                // 그 외의 경우 (랜덤 값이 0에서 6 사이가 아닐 때).
                if (random2 % 2 == 0)
                   // 짝수인 경우 T자 형태 블록.
                    next = new Tee(data); 
                else
                   // 홀수인 경우 L 형태 블록.
                    next = new El(data);
        }
        
        return next;
    }

    public void run() {
        // 다음 블록 생성 및 TetrisPreview에 설정.
        next = createNextBlock();
        preview.setCurrentBlock(current);
        preview.setNextBlock(next);

        while (!stop) {
            try {
                if (makeNew) {
                    // 새 블록 생성 로직.
                    if (value == false) {
                        // 현재 블록 생성.
                        current = createBlock();
                        createGhostBlock();
                        value = true;
                    } else {
                        // 다음 블록을 현재 블록으로 설정.
                        current = next;
                        // 새 다음 블록 생성.
                        next = createNextBlock();
                        // TetrisPreview에 다음 블록 설정.
                        preview.setNextBlock(next);
                        createGhostBlock();
                    }
                    makeNew = false;
                } else {
                    // 현재 블록 이동 로직.
                    // 아래로 한 칸 이동.
                    if (current.moveDown()) {
                        makeNew = true;
                        // 블록을 그리드에 복사하고 게임 종료 확인.
                        if (current.copy()) {
                            stop();
                            // score 계산.
                            score = data.getLine() * 175 * (data.getLine() / 3 + 2);
                            // 게임 종료 및 score 안내.
                            JOptionPane.showMessageDialog(this, "게임 끝\n점수: " + score);
                       Ranking_Record RankingSave = new Ranking_Record();
                       RankingSave.setVisible(true);
                        }
                        // 현재 블록 초기화.
                        current = null;
                        ghost = null;
                        // 게임 루프의 다음 반복으로 진행.
                        continue;
                    }
                    // 가득 찬 라인 삭제.
                    data.removeLines();
                    // 그래픽 업데이트.
                    repaint();
                    // 게임 속도 조절을 위해 게임 루프의 반복 시간 조절.
                    Thread.sleep(Constant.interval / (data.getLine() / 3 + 2));
                }
              // 예외인 경우
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // 키보드를 이용해서 테트리스 조각 제어.
    @Override
    public void keyPressed(KeyEvent e) {
        if (current == null)
            return;
        switch (e.getKeyCode()) {
        // shift 키 Hold.
        case KeyEvent.VK_SHIFT:
            // shift 키를 눌렀을 때 HOLD 기능.
            if (Hold == null) {
                // 현재 piece를 hold.
                Hold = current;
                current = null;
                ghost = null;
                // Hold 블록 정보 전달.
                setHoldBlockInfo(Hold);
                // piece 갱신.
                makeNew = true;
                // hold 기능 비활성화 (TetrisData에서 활성화).
                isHold = true;
                worker.interrupt();
            } else if (Hold != null && isHold != true) {
                Piece temp;
                temp = current;
                current = Hold;
                Hold = temp;
                isHold = true;
                createGhostBlock();
                worker.interrupt();
            }
            Hold.center.x = 5;
            Hold.center.y = 0;
            // Hold 블록 정보 전달.
            setHoldBlockInfo(Hold);
            break;
                
            // 왼쪽 화살표.
            case KeyEvent.VK_LEFT:
               //현재 블록 왼쪽으로 이동.
                current.moveLeft();
                createGhostBlock();
                repaint();
                break;
                
            // 오른쪽 화살표.
            case KeyEvent.VK_RIGHT:
               //현재 블록 오른쪽으로 이동.
                current.moveRight();
                createGhostBlock();
                repaint();
                break;
                
            // 윗쪽 화살표.
            case KeyEvent.VK_UP:
                current.rotate();
                createGhostBlock();
                repaint();
                break;
                
            // 아랫쪽 화살표 키 (아래로 이동).   
            case KeyEvent.VK_DOWN:
                // 현재 조각을 한 칸 아래로 이동하고, 이동 가능 여부를 확인합니다.
                boolean temp = current.moveDown();
                if (temp) {
                    // 이동이 불가능하면 새로운 테트리스 조각을 생성하도록 플래그를 설정합니다.
                    makeNew = true;
                    // 현재 조각을 게임 판에 복사하고, 게임 종료 여부를 확인합니다.
                    if (current.copy()) {
                        // 게임 종료 시, 게임을 멈추고 최종 점수를 계산합니다.
                        stop();
                        int score = data.getLine() * 175 * (data.getLine() / 3 + 2);
                        // 사용자에게 게임 종료 메시지와 점수를 표시합니다.
                        JOptionPane.showMessageDialog(this, "게임 끝\n점수 : " + score);

                    }
                    // 현재 조각을 null로 설정하여 다음 조각 생성을 준비합니다.
                    current = null;
                    ghost = null;
                    data.removeLines();
                    try {
                       worker.interrupt();
                    } catch(Exception e1) {
                       System.out.println("인터럽트 발생!");
                    }
                }
                // 행이 모두 채워져서 제거할 수 있는 경우, 행을 제거하고 화면을 다시 그립니다.
                repaint();
                break;
                
            // 스페이스바 아래 이동.
            case KeyEvent.VK_SPACE:
                // 테트리스 조각을 가장 아래로 이동시키기 위해 while 루프를 사용합니다.
                while (!current.moveDown()) {
                }
                // 아래로 이동 후, 새로운 테트리스 조각을 생성하도록 플래그를 설정합니다.
                makeNew = true;

                // 현재 조각을 게임 판에 복사하고, 게임 종료 여부를 확인합니다.
                if (current.copy()) {
                    // 게임 종료 시, 게임을 멈추고 최종 점수를 계산합니다.
                    stop();
                    int score = data.getLine() * 175 * (data.getLine() / 3 + 2);
                    // 사용자에게 게임 종료 메시지와 점수를 표시합니다.
                    JOptionPane.showMessageDialog(this, "게임 끝\n점수 : " + score);
                }
                // 현재 조각을 null로 설정하여 다음 조각 생성을 준비합니다.
                current = null;
                ghost = null;
                // 행이 모두 채워져서 제거할 수 있는 경우, 행을 제거하고 화면을 다시 그립니다.
                data.removeLines();
                try {
                   worker.interrupt();
                } catch(Exception e1) {
                   System.out.println("인터럽트 발생!");
                }
                repaint();
                break;
        }
    }

    // 데이터를 반환하는 메서드.
    public TetrisData getData() {
        return data;
    }

    // 새로운 블록(Piece)을 반환하는 메서드.
    public Piece getNewBlock() {
        return next;
    }

    // 홀드된 블록(Piece)을 반환하는 메서드.
    public Piece getHold() {
        return Hold;
    }

    // 지워진 줄의 수를 반환하는 메서드.
    public int getLinesCleared() {
        return data.getLine();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("resize");
        initBufferd();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}