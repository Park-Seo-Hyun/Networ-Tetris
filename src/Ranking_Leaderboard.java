
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ranking_Leaderboard extends JDialog {

	// 콘텐츠 패널을 생성하여 참조하는 변수.
	private final JPanel contentPanel = new JPanel(); 
	 // 랭킹 기록을 나타내는 Ranking_Record 인스턴스를 생성하여 참조하는 변수.
	private final Ranking_Record ranking_record = new Ranking_Record();

	/**
	 * Launch the application.
	 * 프로그램을 시작하는 main 메서드입니다.
	 */
	public static void main(String[] args) {
	    try {
	    	 // 랭킹 보드를 나타내는 대화 상자 생성함.
	        Ranking_Leaderboard dialog = new Ranking_Leaderboard();
	     // 대화 상자가 닫힐 때 종료 설정함.
	        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
	     // 대화 상자를 화면에 표시함.
	        dialog.setVisible(true); 
	        // 예외 발생 시.
	    } catch (Exception e) {
	    	// 예외 내용을 출력함.
	        e.printStackTrace(); 
	    }
	}

	/**
	 * Create the dialog.
	 */
	public Ranking_Leaderboard() throws IOException {
	    // 대화 상자의 위치와 크기 설정, 조절 불가능하도록 설정함.
	    setBounds(100, 100, 300, 500);
	    setResizable(false);

	    // 레이아웃 및 패널 설정함.
	    // BorderLayout으로 설정된 콘텐츠 패널 레이아웃 지정함.
	    getContentPane().setLayout(new BorderLayout());
	    // 콘텐츠 패널의 여백 설정함.
	    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	    // 콘텐츠 패널을 프레임의 서쪽에 추가함.
	    getContentPane().add(contentPanel, BorderLayout.WEST);

	    // GridBagLayout 설정함.
	    // GridBagLayout 인스턴스 생성함.
	    GridBagLayout gbl_contentPanel = new GridBagLayout();
	    // 열의 너비 설정함.
	    gbl_contentPanel.columnWidths = new int[]{28, 51, 77, 79, 29, 0}; 
	    // 행의 높이 설정함.
	    gbl_contentPanel.rowHeights = new int[]{0, 10, -6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	    // 열의 가중치 설정함.
	    gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
	    // 행의 가중치 설정함.
	    gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
	    // 콘텐츠 패널에 GridBagLayout 적용함.
	    contentPanel.setLayout(gbl_contentPanel); 
		{
			{
				// "순위표"를 표시하는 레이블 및 설정함.
				// "순위표"를 나타내는 레이블 생성함.
				JLabel lblNewLabel_title = new JLabel("순위표"); 
				 // 레이블의 폰트 설정함.
				lblNewLabel_title.setFont(new Font("한컴 고딕", Font.BOLD, 30));

				// GridBagConstraints 설정.
				 // GridBagConstraints 인스턴스 생성함.
				GridBagConstraints gbc_lblNewLabel_title = new GridBagConstraints();
				// 격자 너비 설정 (3칸 차지)함.
				gbc_lblNewLabel_title.gridwidth = 3; 
				 // 여백 설정함.
				gbc_lblNewLabel_title.insets = new Insets(0, 0, 5, 5);
				 // X축 위치 설정함.
				gbc_lblNewLabel_title.gridx = 1;
				 // Y축 위치 설정함.
				gbc_lblNewLabel_title.gridy = 1;
				 // 레이블을 contentPanel에 추가함.
				contentPanel.add(lblNewLabel_title, gbc_lblNewLabel_title);
			}
			{
				// "New label"을 표시하는 레이블과 설정함.
				 // "New label"을 나타내는 레이블 생성함.
				JLabel lblNewLabel_Score = new JLabel("New label");
				// 수정: 레이블의 텍스트를 상단에 정렬함.
				lblNewLabel_Score.setVerticalAlignment(SwingConstants.TOP); 

				try {
					 // 랭킹 기록 파일 경로 가져옴.
				    String filePath = Ranking_Record.FILE_PATH;
				    // Ranking_Score 메서드로부터 데이터 가져옴.
				    String data = Ranking_Score(filePath);
				    // HTML 형식으로 데이터를 레이블에 설정함.
				    lblNewLabel_Score.setText("<html>" + data + "</html>");
				 // IOException 발생 시.
				} catch (IOException e) { 
					 // 에러 메시지 출력.
				    e.printStackTrace();
				}

				// GridBagConstraints 설정
				 // GridBagConstraints 인스턴스 생성함.
				GridBagConstraints gbc_lblNewLabel_Score = new GridBagConstraints();
				 // 여백 설정함.
				gbc_lblNewLabel_Score.insets = new Insets(0, 0, 0, 5);
				// 격자 높이 설정함.
				gbc_lblNewLabel_Score.gridheight = 1; 
				 // X축 위치 설정함.
				gbc_lblNewLabel_Score.gridx = 3;
				 // Y축 위치 설정함.
				gbc_lblNewLabel_Score.gridy = 2;
				// 텍스트를 상단에 정렬 설정함.
				gbc_lblNewLabel_Score.anchor = GridBagConstraints.NORTH; 
				// 레이블을 contentPanel에 추가함.
				contentPanel.add(lblNewLabel_Score, gbc_lblNewLabel_Score); 
			}
			{
				// "Ranking"을 나타내는 레이블과 설정함.
				// "Ranking"을 나타내는 레이블 생성함.
				JLabel lblNewLabel_Ranking = new JLabel(); 
				 // 수정: 레이블의 텍스트를 상단에 정렬함.
				lblNewLabel_Ranking.setVerticalAlignment(SwingConstants.TOP);
				 // Ranking_Ranking 메서드로부터 가져온 데이터 HTML 형식으로 레이블에 설정함.
				lblNewLabel_Ranking.setText("<html>" + Ranking_Ranking());

				// GridBagConstraints 설정함.
				 // GridBagConstraints 인스턴스 생성함.
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				 // 격자 높이 설정함.
				gbc_lblNewLabel.gridheight = 1;
				// GridBagConstraints 인스턴스 생성함.
				gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
				 // X축 위치 설정함.
				gbc_lblNewLabel.gridx = 1;
				// Y축 위치 설정함.
				gbc_lblNewLabel.gridy = 2; 
				 // 레이블을 contentPanel에 추가함.
				contentPanel.add(lblNewLabel_Ranking, gbc_lblNewLabel);
				// "User"를 나타내는 레이블과 설정함.
				 // "User"를 나타내는 레이블 생성함.
				JLabel lblNewLabel_User = new JLabel();
				//레이블의 텍스트를 상단에 정렬함.
				lblNewLabel_User.setVerticalAlignment(SwingConstants.TOP); 
				try {
					 // 랭킹 기록 파일 경로 가져옴.
				    String filePath = Ranking_Record.FILE_PATH;
				    // Ranking_User 메서드로부터 데이터 가져옴.
				    String data = Ranking_User(filePath);
				    // HTML 형식으로 데이터를 레이블에 설정함.
				    lblNewLabel_User.setText("<html>" + data + "</html>");
				    // IOException 발생 시.
				} catch (IOException e) {
					// 에러 메시지 출력함.
				    e.printStackTrace(); 
				}

				// GridBagConstraints 설정
				 // GridBagConstraints 인스턴스 생성.
				GridBagConstraints gbc_lblNewLabel_User = new GridBagConstraints();
				 // 격자 높이 설정함.
				gbc_lblNewLabel_User.gridheight = 1;
				 // 여백 설정함.
				gbc_lblNewLabel_User.insets = new Insets(0, 0, 0, 5);
				 // X축 위치 설정함.
				gbc_lblNewLabel_User.gridx = 2;
				 // Y축 위치 설정함.
				gbc_lblNewLabel_User.gridy = 2;
				 // 텍스트를 상단에 정렬 설정함.
				gbc_lblNewLabel_User.anchor = GridBagConstraints.NORTH;
				 // 레이블을 contentPanel에 추가함.
				contentPanel.add(lblNewLabel_User, gbc_lblNewLabel_User);

				// 버튼 패널 설정함.
				 // 버튼 패널 생성함.
				JPanel buttonPane = new JPanel();
				 // 버튼 패널 레이아웃 설정함.
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

				// 버튼 추가함.
				 // 버튼 패널을 프레임의 아래쪽에 추가함.
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					 // "OK"를 나타내는 버튼 생성함.
				    JButton okButton = new JButton("OK");
				    okButton.addActionListener(new ActionListener() {
				        public void actionPerformed(ActionEvent e) {
				        	// 대화 상자 닫기.
				            dispose(); 
				        }
				    });
				    // 액션 명령 설정함.
				    okButton.setActionCommand("OK");
				    // 버튼 패널에 버튼 추가함.
				    buttonPane.add(okButton);
				    // 버튼을 기본 버튼으로 설정함.
				    getRootPane().setDefaultButton(okButton);
				}
			}
		}
	}
	
	// 점수를 파일에서 불러오는 메서드.
	private static String Ranking_Score(String filePath) throws IOException {
		// 파일을 읽기 위한 BufferedReader 생성함.
	    BufferedReader reader = new BufferedReader(new FileReader(filePath)); 
	    String line;
	    // 문자열을 담기 위한 StringBuilder 생성함.
	    StringBuilder sb = new StringBuilder();
	    while ((line = reader.readLine()) != null) {
	    	 // 쉼표로 문자열 분할함.
	        String[] parts = line.split(",");
	        if (parts.length == 2) {
	        	 // 숫자.
	            String number = parts[1].trim();
	            // 숫자를 StringBuilder에 추가함.
	            sb.append(number).append("<br><br>");
	        }
	    }
	 // BufferedReader 닫음.
	    reader.close(); 
	    // 결과를 HTML 형식으로 반환.
	    return sb.toString();
	}

	// 유저를 파일에서 불러오는 메서드
	private static String Ranking_User(String filePath) throws IOException {
		 // 파일을 읽기 위한 BufferedReader 생성함.
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    String line;
	    // 문자열을 담기 위한 StringBuilder 생성함.
	    StringBuilder sb = new StringBuilder();
	    while ((line = reader.readLine()) != null) {
	    	 // 쉼표로 문자열 분할함.
	        String[] parts = line.split(",");
	        if (parts.length == 2) {
	        	 // 유저명.
	            String username = parts[0].trim();
	            // 유저명을 StringBuilder에 추가함.
	            sb.append(username).append("<br><br>");
	        }
	    }
	    // BufferedReader 닫음.
	    reader.close();
	    // 결과를 HTML 형식으로 반환함.
	    return sb.toString();
	}

	// 등수를 나타내는 메서드
	private static String Ranking_Ranking() throws IOException {
		 // 문자열을 담기 위한 StringBuilder 생성함.
	    StringBuilder ranking = new StringBuilder();
	    for (int i = 1; i < 11; i++) {
	    	// 등수를 StringBuilder에 추가함.
	        ranking.append(i).append("등").append("<br><br>"); 
	    }
	    // 결과를 HTML 형식으로 반환함.
	    return ranking.toString();
	}
}
