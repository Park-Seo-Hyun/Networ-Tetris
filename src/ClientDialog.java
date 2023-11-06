import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientDialog extends JDialog {
    // 직렬화 버전 UID.
    private static final long serialVersionUID = 1L; 

    // "Host:" 라벨.
    private JLabel label1 = new JLabel("Host:");  
    // Host 입력란.
    private JTextField text1 = new JTextField(10);  
    // "Port:" 라벨.
    private JLabel label2 = new JLabel("Port:");
    // Port 입력란.
    private JTextField text2 = new JTextField(10);
    // "Ok" 버튼.
    private JButton okButton = new JButton("Ok");
    // "Cancel" 버튼.
    private JButton cancelButton = new JButton("Cancel");
    // 포트 번호.
    private int port = 0; 
    // 호스트 주소.
    private String host;
    
    // 사용자 선택을 나타내는 열거형.
    public enum Choice {
        NONE, OK, CANCEL
    }
    
    // 사용자 선택.
    public Choice userChoice = Choice.NONE;
    
    public ClientDialog() {
        // 모달 다이얼로그 설정.
        setModal(true);
        // 다이얼로그 제목 설정.
        setTitle("서버 접속");
        // 3x1 그리드 레이아웃 설정.
        setLayout(new GridLayout(3, 1));
        
        // 호스트 패널 설정.
        JPanel hostPanel = new JPanel();
        add(hostPanel);
        // 호스트 패널 레이아웃 설정.
        hostPanel.setLayout(new FlowLayout());
        // "Host:" 라벨 추가.
        hostPanel.add(label1);
        // Host 입력란 추가.
        hostPanel.add(text1);

        // 포트 패널 설정.
        JPanel portPanel = new JPanel();
        add(portPanel);
        portPanel.setLayout(new FlowLayout()); 
        // "Port:" 라벨 추가.
        portPanel.add(label2); 
        // Port 입력란 추가.
        portPanel.add(text2); 

        // 버튼 패널 설정.
        JPanel buttonPanel = new JPanel();
        add(buttonPanel);
        // 버튼 패널 레이아웃 설정.
        buttonPanel.setLayout(new FlowLayout()); 
        // "Ok" 버튼 추가.
        buttonPanel.add(okButton); 
        // "Cancel" 버튼 추가.
        buttonPanel.add(cancelButton); 
        
        // 다이얼로그 크기 자동 조절.
        pack();
        // Host 입력란 기본 값 설정.
        text1.setText("127.0.0.1"); 
        // Port 입력란 기본 값 설정.
        text2.setText("5000");
        
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자 선택을 "OK"로 설정.
                userChoice = Choice.OK; 
                // Host 값을 입력된 값으로 업데이트.
                host = text1.getText(); 
                // Port 값을 입력된 값으로 업데이트.
                port = Integer.parseInt(text2.getText()); 
                // 다이얼로그 닫기.
                dispose(); 
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자 선택을 "CANCEL"로 설정.
                userChoice = Choice.CANCEL;  
                // 다이얼로그 닫기.
                dispose();  
            }
        });
    }

    public int getPortNumber() {
        // 현재 포트 번호 반환.
        return this.port;
    }

    public String getHost() {
        // 현재 호스트 주소 반환.
        return this.host; 
    }
}