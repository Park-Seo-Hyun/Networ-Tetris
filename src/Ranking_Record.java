import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

public class Ranking_Record extends JFrame {
	 // 랭킹 데이터를 저장할 파일 경로.
    static final String FILE_PATH = "Ranking.txt";
    // 컨텐츠 패널.
    private JPanel contentPane;
    // 이름을 입력할 텍스트 필드
    private JTextField textField;

    /**
     * Launch the application.
     * 프로그램 실행을 위한 main 메소드. Swing 작업은 Event Dispatch Thread에서 실행됩니다.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Ranking_Record 프레임을 생성하여 보이도록 설정함.
                    Ranking_Record frame = new Ranking_Record();
                    // 프레임을 보이도록 설정.
                    frame.setVisible(true);
                    // 크기 조절 불가능하도록 설정.
                    frame.setResizable(false);
                } catch (Exception e) {
                	 // 예외 발생 시 콘솔에 에러 메시지 출력함.
                    e.printStackTrace();
                }
            }
        });
    }

	/**
	 * Create the frame.
	 */
    public Ranking_Record() {
        // 게임 점수를 출력
        System.out.println(MyTetris.tetrisCanvas.score);

        // JFrame 설정
        // 프레임 닫을 때 동작 설정함.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // 프레임 위치와 크기 설정함.
        setBounds(100, 100, 298, 189);

        // 콘텐츠 패널과 관련된 UI 설정
        contentPane = new JPanel();
        // 콘텐츠 패널 테두리 설정함.
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        // 프레임에 콘텐츠 패널 추가함.
        setContentPane(contentPane); 
        // CardLayout으로 레이아웃 설정함.
        contentPane.setLayout(new CardLayout(0, 0));

        // 패널과 관련된 UI 구성
        // 새로운 패널 생성함.
        JPanel panel = new JPanel();
        // 패널에 대한 툴팁 설정함.
        panel.setToolTipText("Score save"); 
        // 콘텐츠 패널에 패널 추가함.
        contentPane.add(panel, "name_624620029655900"); 

        // GridBagLayout 설정
        // GridBagLayout 생성함.
        GridBagLayout gbl_panel = new GridBagLayout();
        // 열 너비 설정함.
        gbl_panel.columnWidths = new int[]{69, 56, 30, 106, 44, 56, 62, 0, 0};
        // 행 높이 설정함.
        gbl_panel.rowHeights = new int[]{0, 40, 0, 0, 0, 0, 0};
        // 열 가중치 설정함.
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        // 행 가중치 설정함.
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        // 패널에 GridBagLayout 적용함.
        panel.setLayout(gbl_panel); 

        // "게임 끝" 라벨 구성
        // "게임 끝"을 표시하는 라벨 생성함.
        JLabel lblNewLabel = new JLabel("게임 끝");
        // 라벨 폰트 설정함.
        lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20)); 
        // GridBag 설정을 위한 인스턴스 생성함.
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        // 여백 설정
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        // X축 그리드 설정함.
        gbc_lblNewLabel.gridx = 3;
        // Y축 그리드 설정함.
        gbc_lblNewLabel.gridy = 1; 
        // 라벨을 패널에 추가함.
        panel.add(lblNewLabel, gbc_lblNewLabel);

        // "점수"와 실제 게임 점수를 보여주는 라벨 설정
        // 점수를 표시하는 라벨 생성함.
        JLabel lblNewLabel_1 = new JLabel("점수 : " + MyTetris.tetrisCanvas.score);
        // 라벨 폰트 설정
        lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14)); 
        // GridBag 설정을 위한 인스턴스 생성
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints(); 
        // 여백 설정함.
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5); 
        // X축 그리드 설정함.
        gbc_lblNewLabel_1.gridx = 3;
        // Y축 그리드 설정함.
        gbc_lblNewLabel_1.gridy = 2;
        // 라벨을 패널에 추가함.
        panel.add(lblNewLabel_1, gbc_lblNewLabel_1); 

        // 이름을 입력하는 텍스트 필드 설정함.
        // 이름을 입력하는 텍스트 필드 생성함.
        textField = new JTextField();
        // 텍스트 필드에 대한 툴팁 설정함.
        textField.setToolTipText("Name"); 
        // GridBag 설정을 위한 인스턴스 생성함.
        GridBagConstraints gbc_textField = new GridBagConstraints(); 
        // 수평으로 채우도록 설정함.
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        // 여백 설정함.
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        // X축 그리드 설정함.
        gbc_textField.gridx = 3; 
        // Y축 그리드 설정함.
        gbc_textField.gridy = 3; 
        // 텍스트 필드를 패널에 추가.
        panel.add(textField, gbc_textField);

        // 기록 저장을 수행하는 버튼 설정함.
        // "기록 저장"을 표시하는 버튼 생성함.
        JButton btnNewButton = new JButton("기록 저장");
        // 액션 처리를 위한 ActionListener 설정함.
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 // 텍스트 필드로부터 이름을 가져옴.
                String name = textField.getText();
                if (name.isEmpty()) {
                	// 이름이 비어있는 경우 기본값으로 "Nickname" 설정함.
                    name = "Nickname"; 
                }
                // 이름과 점수를 저장하는 메소드 호출함.
                saveRanking(name, MyTetris.tetrisCanvas.score); 
                // 저장 완료 메시지 창.
                JOptionPane.showMessageDialog(Ranking_Record.this, "랭킹이 저장되었습니다.");
             // 다이얼로그 창 닫기.
                dispose(); 
            }
        });
		
        // "기록 저장" 버튼의 GridBag 설정
        // GridBagConstraints 인스턴스 생성함.
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        // 수직으로 채우도록 설정함.
        gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
        // 여백 설정함.
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 5); 
        // X축 그리드 설정함.
        gbc_btnNewButton.gridx = 3;
        // Y축 그리드 설정함.
        gbc_btnNewButton.gridy = 4; 
        // 버튼을 패널에 추가함.
        panel.add(btnNewButton, gbc_btnNewButton);
		
	}
	
	// 기존의 saveRanking 함수
	protected void saveRanking(String name, int score) {
		 // 랭킹 데이터 로드.
	    List<String> rankingDataList = loadRanking();
	    // 새로운 랭킹 데이터 추가함.
	    addRanking(rankingDataList, name, score);
	    // 내림차순으로 정렬.
	    sortRanking(rankingDataList); 
	    // 정렬된 랭킹 데이터 저장.
	    saveSortedRanking(rankingDataList); 
	    // 초과 데이터 제거함.
	    removeExcessRanking(rankingDataList);
	}

	// 랭킹 데이터를 파일에서 로드하는 메서드.
	protected List<String> loadRanking() {
		 // 랭킹 데이터를 보관할 리스트 생성.
	    List<String> rankingDataList = new ArrayList<>();

	    // 파일 리더 생성함.
	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) { 
	    	 // 각 줄의 데이터를 저장하기 위한 변수.
	        String line;
	        // 파일의 모든 줄을 읽음.
	        while ((line = reader.readLine()) != null) { 
	        	 // 각 줄의 데이터를 리스트에 추가함.
	            rankingDataList.add(line);
	        }
	      // IOException 발생 시.
	    } catch (IOException e) {
	    	// 에러 메시지 출력함.
	        e.printStackTrace(); 
	    }

	    // 읽어온 랭킹 데이터 목록 반환함.
	    return rankingDataList;
	}

	// 새로운 랭킹 데이터를 추가하는 메서드.
	protected void addRanking(List<String> rankingDataList, String name, int score) {
		// 이름과 점수를 문자열로 결합하여 랭킹 데이터 생성함.
	    String rankingData = name + "," + score; 
	    // 새로운 랭킹 데이터를 리스트에 추가함.
	    rankingDataList.add(rankingData);
	}

	// 내림차순으로 랭킹 데이터를 정렬하는 메서드.
	protected void sortRanking(List<String> rankingDataList) {
		// 리스트를 점수를 기준으로 내림차순 정렬하는 람다식.
	    rankingDataList.sort((s1, s2) -> { 
	    	 // 첫 번째 랭킹 데이터의 점수 가져옴.
	        int score1 = Integer.parseInt(s1.split(",")[1]);
	        // 두 번째 랭킹 데이터의 점수 가져옴.
	        int score2 = Integer.parseInt(s2.split(",")[1]);
	        // 두 점수를 비교하여 정렬 순서 반환함.
	        return Integer.compare(score2, score1);
	    });
	}

	// 정렬된 랭킹 데이터를 파일에 저장하는 메서드.
	protected void saveSortedRanking(List<String> rankingDataList) {
		 // 파일 라이터 생성함.
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
	    	// 랭킹 데이터 리스트를 반복하여 파일에 씀.
	        for (String data : rankingDataList) { 
	        	// 랭킹 데이터를 파일에 씀.
	            writer.write(data);
	            // 새 줄로 이동하여 다음 랭킹 데이터 작성함.
	            writer.newLine();
	        }
	        // IOException 발생 시.
	    } catch (IOException e) {
	    	// 에러 메시지 출력함.
	        e.printStackTrace(); 
	    }
	}

	// 랭킹 인원이 최대 저장 개수를 초과하는 경우 초과 데이터를 제거하는 메서드.
	protected void removeExcessRanking(List<String> rankingDataList) {
		 // 최대 저장 가능한 랭킹 데이터 개수 설정함.
	    int maxEntries = 10;

	    // 현재 랭킹 데이터의 개수가 최대 저장 개수보다 많은 경우
	    if (rankingDataList.size() > maxEntries) { 
	    	 // 초과 데이터 제거함.
	        rankingDataList.subList(maxEntries, rankingDataList.size()).clear();
	     // 수정된 랭킹 데이터를 다시 파일에 저장함.
	        saveSortedRanking(rankingDataList); 
	    }
	}
	
	
	// 랭킹 데이터를 특정 형식으로 출력하는 메서드.
	protected String outputRanking() {
		// 문자열을 동적으로 처리하기 위한 StringBuilder 객체 생성함.
	    StringBuilder sb = new StringBuilder(); 
	    // 파일 리더 생성함.
	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
	    	 // 한 줄씩 읽어올 변수.
	        String line;
	        // 들여쓰기 공백 설정 (4칸 들여쓰기).
	        String indentation = "    ";

	        // 파일에서 한 줄씩 읽음.
	        while ((line = reader.readLine()) != null) {
	        	// 쉼표로 문자열을 분할하여 배열로 저장함.
	            String[] parts = line.split(","); 
	            // 두 부분으로 나눠진 경우에만 처리함.
	            if (parts.length == 2) { 
	            	 // 유저명 가져옴.
	                String username = parts[0].trim();
	                // 숫자 가져옴.
	                String number = parts[1].trim();
	             // 형식에 맞게 문자열 추가함.
	                sb.append(indentation).append(username).append(": ").append(number).append("\n"); 
	            }
	        }
	        // 리더 닫기.
	        reader.close(); 
	        // IOException 발생 시.
	    } catch (IOException e) {
	    	// 에러 메시지 출력함.
	        e.printStackTrace(); 
	    }
	 // 형식에 맞게 정리된 랭킹 데이터 문자열 반환함.
	    return sb.toString(); 
	}

}
