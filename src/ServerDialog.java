import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ServerDialog extends JDialog {
	// 직렬화 ID.
	private static final long serialVersionUID = 1L; 
	// 라벨.
	private JLabel label = new JLabel("포트:"); 
	// 텍스트 필드.
	private JTextField text = new JTextField(10); 
	// 확인 버튼.
	private JButton okButton = new JButton("확인"); 
	// 취소 버튼.
	private JButton cancelButton = new JButton("취소"); 
	// 포트 번호.
	private int port = 0; 
	public enum Choice {
		// 사용자 선택.
		NONE, OK, CANCEL 
	}
	
	// 초기 사용자 선택값은 '없음'.
	public Choice userChoice = Choice.NONE; 

	public ServerDialog() {
		 // 대화상자가 모달로 설정.
		setModal(true);
		// 대화상자 제목.
		setTitle("서버 생성"); 
		// 레이아웃 설정.
		setLayout(new FlowLayout()); 
		 // 라벨 추가.
		add(label);
		// 텍스트 필드 추가.
		add(text); 
		// 확인 버튼 추가.
		add(okButton); 
		// 취소 버튼 추가.
		add(cancelButton); 
		// 크기 자동 조절.
		pack(); 
		 // 텍스트 필드의 초기값 설정.
		text.setText("5000");

		// 확인 버튼에 대한 이벤트 리스너.
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 // 사용자 선택을 '확인'으로 설정.
				userChoice = Choice.OK;
				 // 텍스트 필드에서 포트 번호 추출.
				port = Integer.parseInt(text.getText());
				dispose(); // 대화상자 닫기.
			}
		});

		// 취소 버튼에 대한 이벤트 리스너.
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 // 사용자 선택을 '취소'로 설정.
				userChoice = Choice.CANCEL;
				// 대화상자 닫기.
				dispose(); 
			}
		});
	}

	public int getPortNumber() {
		// 설정된 포트 번호 반환.
		return this.port; 
	}
}