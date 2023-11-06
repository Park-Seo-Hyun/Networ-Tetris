import java.io.File;
import javax.sound.sampled.*;

public class TetrisBgm {
	 private static Clip clip;
	    
	    // 테트리스 노래 실행
	    public static void Play_Bgm(String MusicPath, float volume) {
	    	try {
	    		// 현재 클립이 null이 아니라면, 즉 이전 노래가 재생 중이라면
	            if (clip != null) {  
	            	 // 이전 노래를 정지
	                clip.stop(); 
	                // 이전 노래 클립을 해제
	                clip.close();  
	            }

	            // 테트리스 노래 지정하기.
	            File file = new File(MusicPath); 
	            // Clip 객체 생성한다.
	            clip = AudioSystem.getClip();  
	            // 오디오 파일 열어준다.
	            clip.open(AudioSystem.getAudioInputStream(file));  

	            // 볼륨을 조절할 믹서 생성하기.
	            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

	            // 볼륨 조정하기 (음수 값: 줄이기, 양수 값: 늘리기)
	            volumeControl.setValue(volume);
	            // 노래 무한 재생시키기.
	            clip.loop(Clip.LOOP_CONTINUOUSLY);  
	            // 노래 재생 시작시키기.
	            clip.start();  
	        } catch (Exception e) {
	        	// 예외 정보 출력.
	            e.printStackTrace(); 
	         // 오류 발생 시 메시지 출력.
	            System.err.println("CAN NOT PLAY THE MUSIC");  
	        }
	    }

	    // 테트리스 노래를 초기화시킨다.
	    public static void stop() {
	    	// clip이 null이 아닌 경우에만 실행한다.
	        if (clip != null) {  
	        	// 노래 재생을 중지시킨다.
	            clip.stop();  
	            // clip 리소스를 해제시킨다.
	            clip.close(); 
	        }
	    }
}