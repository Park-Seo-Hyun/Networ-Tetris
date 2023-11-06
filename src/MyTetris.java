import java.awt.GridLayout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem; 

@SuppressWarnings("unused")
public class MyTetris extends JFrame {

    private static final long serialVersionUID = 1L;
    public static TetrisCanvas tetrisCanvas;
    private TetrisClient client = null;
	private Ranking_Leaderboard ranking_Leaderboard;

    public MyTetris() {
    	
        setTitle("테트리스");
        setSize(320 * 4, 600);

        // 1x4 그리드 레이아웃 설정.
        GridLayout layout = new GridLayout(1, 4);
        setLayout(layout);

        // TetrisCanvas 초기화
        tetrisCanvas = new TetrisCanvas(this);
        TetrisNetworkCanvas netCanvas = new TetrisNetworkCanvas();

        // TetrisPreview 초기화.
        TetrisPreview preview = new TetrisPreview(tetrisCanvas.getData());
        TetrisNetworkPreview netPreview = new TetrisNetworkPreview(netCanvas.getData());
        tetrisCanvas.setTetrisPreview(preview);
//        netCanvas.setTetrisPreview(netPreview);

        //메뉴 생성
        createMenu(tetrisCanvas, preview, netCanvas, netPreview);
        
        // 컴포넌트 추가.
        add(tetrisCanvas);
        add(preview);
        add(netCanvas);
        add(netPreview);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    public void createMenu(TetrisCanvas tetrisCanvas, TetrisPreview preview, TetrisNetworkCanvas netCanvas, TetrisNetworkPreview netPreview) {
        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);

        // "게임" 메뉴 생성.
        JMenu gameMenu = new JMenu("게임");
        mb.add(gameMenu);

        // "시작" 메뉴 아이템 생성 및 이벤트 핸들러 등록.
        JMenuItem startItem = new JMenuItem("시작");
        JMenuItem restartItem = new JMenuItem("재시작");
        JMenuItem exitItem = new JMenuItem("종료");
        gameMenu.add(startItem);
        gameMenu.add(restartItem);
        gameMenu.add(exitItem);
        
        // 초기 상태에서는 재시작 버튼 비활성화.
        startItem.setEnabled(true);
        // 초기 상태에서는 시작 버튼 활성화.
        restartItem.setEnabled(false);

        startItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //게임 음악 재생.
               TetrisBgm.Play_Bgm("TetrisBGM.wav", -20); 
               //게임 시작.
                tetrisCanvas.start();
                netCanvas.start();
                // 시작 버튼 비활성화.
                startItem.setEnabled(false);
                // 재시작 버튼 활성화.
                restartItem.setEnabled(true);
            }
        });
        
        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                   tetrisCanvas.stop();
                    // 음악 정지.
                    TetrisBgm.stop();
                    netCanvas.stop();
                    // 단위 ms.
                    Thread.sleep(1000); 

                    // 새로운 게임을 시작하기 전에 데이터 초기화시키기.
                    // TetrisData의 데이터 초기화   .
                    tetrisCanvas.getData().clear(); 
                    // hold 블록 초기화.
                    tetrisCanvas.setHoldBlockInfo(null); 
                    // 버퍼 초기화.
                    tetrisCanvas.initBufferd(); 
                    // 워커 스레드 재생성.
                    tetrisCanvas.worker = new Thread(tetrisCanvas);
                    //게임 음악 재생
                    TetrisBgm.Play_Bgm("TetrisBGM.wav", -20); 
                    //게임 시작.
                    tetrisCanvas.start();
                    netCanvas.start();
                    // 시작 버튼 비활성화.
                    startItem.setEnabled(false);
                    // 재시작 버튼 활성화.
                    restartItem.setEnabled(true);
                  //예외일 경우.
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                tetrisCanvas.stop();
                // 음악 정지.
                TetrisBgm.stop();
                netCanvas.stop();
            }
        });

        // "네트워크" 메뉴 생성.
        JMenu networkMenu = new JMenu("네트워크");
        mb.add(networkMenu);

        // "서버 생성" 메뉴 아이템 생성 및 이벤트 핸들러 등록.
        JMenuItem serverItem = new JMenuItem("서버 생성...");
        networkMenu.add(serverItem);

        // "서버 접속" 메뉴 아이템 생성 및 이벤트 핸들러 등록.
        JMenuItem clientItem = new JMenuItem("서버 접속...");
        networkMenu.add(clientItem);
        
        serverItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 서버 대화상자를 통해 포트 번호를 입력받고 서버를 시작.
                ServerDialog dialog = new ServerDialog();
                dialog.setVisible(true);
                if (dialog.userChoice == ServerDialog.Choice.OK) {
                    TetrisServer server = new TetrisServer(dialog.getPortNumber());
                    server.start();
                    serverItem.setEnabled(false);
                }
            }
        });

        clientItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 클라이언트 대화상자를 통해 호스트와 포트 번호를 입력받고 클라이언트를 시작.
                ClientDialog dialog = new ClientDialog();
                dialog.setVisible(true);
                if (dialog.userChoice == ClientDialog.Choice.OK) {
                    client = new TetrisClient(tetrisCanvas, preview, netCanvas, netPreview, dialog.getHost(), dialog.getPortNumber());
                    client.start();
                    clientItem.setEnabled(false);
                }
            }
        });
        
        // "랭크" 메뉴 생성.
        JMenu RankMenu = new JMenu("랭크");
        mb.add(RankMenu);
        // "랭크 생성" 메뉴 아이템 생성 및 이벤트 핸들러 등록.
        JMenuItem RankItem = new JMenuItem("순위");

        // RankItem에 대한 ActionListener 추가함.
        RankItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	 // Ranking_Leaderboard 인스턴스 생성함.
                    ranking_Leaderboard = new Ranking_Leaderboard();
                 // IOException 예외 발생 시
                } catch (IOException e1) {
                	 // 에러 메시지 출력함.
                    e1.printStackTrace();
                }
                // Ranking_Leaderboard 창을 표시함.
                ranking_Leaderboard.setVisible(true);
            }
        });

        // RankItem을 RankMenu에 추가함.
        RankMenu.add(RankItem);
        
    }

    public static void main(String[] args) {
        new MyTetris();
    }

    // 네트워크 통신을 통해 게임 상태를 업데이트.
    public void refresh() {
        if (client != null)
            client.send();
    }

}