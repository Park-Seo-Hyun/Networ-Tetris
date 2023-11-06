import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TetrisNetworkPreview extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TetrisData data;
	private Piece next;
	private Piece Hold;
	
	private int score;
	private int line;
	
	public TetrisNetworkPreview(TetrisData data) {
		this.data = data;
		repaint();
	}
	
	public void setnewBlock(Piece next) {
		this.next = next;
		repaint();
	}
	
	public void setHold(Piece Hold) {
		this.Hold = Hold;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//쌓인 조각들 그리기
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(Constant.getColor(0));
					g.draw3DRect(Constant.margin/2 + Constant.w * k,
							Constant.margin/2 + Constant.w * i, 
							Constant.w, Constant.w, true);
				}
			}
		}
		//System.out.println(current);
		// 현재 내려오고 있는 테트리스 조각 그리기
		// 다음 블록 그리기.
        if (next != null) {
            for (int i = 0; i < 4; i++) {
                g.setColor(Constant.getColor(next.getType()));
                g.fill3DRect(Constant.margin / 2 + Constant.w * (1 + next.c[i]),
                        Constant.margin / 2 + Constant.w * (0 + next.r[i]),
                        Constant.w, Constant.w, true);
            }
        }
        
        // 보관 칸 그리기.
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                if (data.getAt(i, k) == 0) {
                    g.setColor(Constant.getColor(0));
                    g.draw3DRect(Constant.margin / 2 + Constant.w * k,
                            Constant.margin / 2 + Constant.w * (i + 6), Constant.w, Constant.w, true);
                } else {
                    g.setColor(Constant.getColor(0));
                    g.fill3DRect(Constant.margin / 2 + Constant.w * k,
                            Constant.margin / 2 + Constant.w * (i + 6), Constant.w, Constant.w, true);
                }
            }
        }
        
        // 보관된 블록 그리기.
        if (Hold != null) {
            for (int i = 0; i < 4; i++) {
                g.setColor(Constant.getColor(Hold.getType()));
                int x = Constant.margin / 2 + Constant.w * (Hold.c[i] + 1);
                int y = Constant.margin / 2 + Constant.w * (Hold.r[i] + 8);

                // 위치 조정 - 화면을 넘어가지 않도록, 보관 칸 내부에서 나타나도록 수정하기.
                if (x >= Constant.margin / 2 && x + Constant.w <= getWidth() - Constant.margin / 2
                    && y >= Constant.margin / 2 && y + Constant.w <= getHeight() - Constant.margin / 2) {
                    g.fill3DRect(x, y, Constant.w, Constant.w, true);
                } else {
                    // 만약 위치가 화면을 벗어나면, 보관 칸 내부로 위치 조정하기.
                    if (x < Constant.margin / 2) {
                        x = Constant.margin / 2;
                    } else if (x + Constant.w > getWidth() - Constant.margin / 2) {
                        x = getWidth() - Constant.margin / 2 - Constant.w;
                    }
                    if (y < Constant.margin / 2) {
                        y = Constant.margin / 2;
                    } else if (y + Constant.w > getHeight() - Constant.margin / 2) {
                        y = getHeight() - Constant.margin / 2 - Constant.w;
                    }
                    g.fill3DRect(x, y, Constant.w, Constant.w, true);
                }
            }
        }
        
        // Score 표시.
//        score = data.getLine() * 175 * (data.getLine() / 3 + 1);
        // Score 폰트 설정.
        g.setFont(new Font(null, Font.BOLD, 18)); 
        // Score 텍스트 색상 설정.
        g.setColor(Color.BLACK); 
        // Score 위치 설정.
        g.drawString("Score : " + score, 15, 4 * Constant.w + 15 * Constant.margin); 

        // Line 표시.
//        line = data.getLine();
        // Line 폰트 설정.
        g.setFont(new Font(null, Font.BOLD, 18)); 
        // Line 텍스트 색상 설정.
        g.setColor(Color.BLACK); 
        // Line 위치 설정.
        g.drawString("Line : " + line, 15, 4 * Constant.w + 17 * Constant.margin); 

        // Level 폰트 설정.
        g.setFont(new Font(null, Font.BOLD, 18)); 
        // Level 텍스트 색상 설정.
        g.setColor(Color.BLACK); 
        // Level 위치 설정.
        g.drawString("Level : " + (line / 3 + 1), 15, 4 * Constant.w + 19 * Constant.margin); 
	}
}
