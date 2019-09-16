import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TicTacToe2P {
	public static void main(String[] args) {
		JFrame frame = new TicTacToeJFrame();
		frame.setSize(320,400);
		frame.setTitle("Tic tac toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public enum Players {BLANK, X, O, TIE}
	
	public static Players[][] gameBoard = new Players[3][3];
	
	static TicTacToeJComponent component = new TicTacToeJComponent();
	
	static class TicTacToeJFrame extends JFrame implements MouseListener {
		
		private static final long serialVersionUID = 1L;

		public TicTacToeJFrame() {
			addMouseListener(this);
			this.add(component);
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					gameBoard[i][j] = Players.BLANK;
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			component.mousePressed(arg0.getX(), arg0.getY());
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
	}
	
	static class TicTacToeJComponent extends JComponent {

		private static final long serialVersionUID = 1L;
		
		public int screen = 1;
		
		public int player = 1;
		
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			if(screen == 1) {
				g2.setColor(new Color(0,0,0));
				g2.setStroke(new BasicStroke(15,1,1));
				for(int i = 1; i <= 2; i++) {
					g2.draw(new Line2D.Double(10,100*i,300,100*i));
					g2.draw(new Line2D.Double(100*i,10,100*i,300));
				}
				g2.setStroke(new BasicStroke(10,1,1));
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						g2.setColor(Color.RED);
						if(gameBoard[i][j] == Players.O) {
							g2.draw(new Ellipse2D.Double(15+100*i,15+100*j,70,70));
						}
						g2.setColor(Color.BLUE);
						if(gameBoard[i][j] == Players.X) {
							g2.draw(new Line2D.Double(15+100*i,15+100*j,85+100*i,85+100*j));
							g2.draw(new Line2D.Double(15+100*i,85+100*j,85+100*i,15+100*j));
						}
					}
				}
				
				g2.setFont(new Font("TimesRoman",Font.PLAIN,30));
				g2.setColor(new Color(0,0,0));
				g2.drawString("Player " + player + "'s turn", 0, 350);
				if(win() != Players.BLANK) {
					g2.setColor(new Color(102,255,153,200));
					g2.fill(new RoundRectangle2D.Double(50,50,200,200,10,10));
					g2.setFont(new Font("TimesRoman",Font.PLAIN,20));
					g2.setColor(new Color(0,0,0));
					if(win() == Players.TIE) {
						g2.drawString("It's a tie!", 120, 75);
					} else {
						g2.drawString("Player " + win() + " won!",95,75);
					}
					g2.setColor(new Color(153,255,255));
					g2.fill(new RoundRectangle2D.Double(118,90,70,30,10,10));
					g2.setColor(new Color(0,0,0));
					g2.drawString("Restart", 125, 110);
				}
			}
		}
		
		public void mousePressed(int x, int y) {
			if(screen == 1 && win() == Players.BLANK) {
				if((x-8)/100 < 3 && (y-30)/100 < 3) {
					if(gameBoard[(x-8)/100][(y-30)/100] == Players.BLANK) {
						if(player == 1) {
							player = 2;
							gameBoard[(x-8)/100][(y-30)/100] = Players.X;
							System.out.println("Place X on (" + x/100 + "," + y/100 + ")");
						} else {
							player = 1;
							gameBoard[(x-8)/100][(y-30)/100] = Players.O;
							System.out.println("Place O on (" + x + "," + y + ")");
						}
						if(win() != Players.BLANK) {
							if(win() == Players.TIE) {
								System.out.println("It's a tie!");
							} else {
								System.out.println("Player " + win() + " won!"); 
							}
						}
						component.repaint();
					} else {
						System.err.print("A player already placed here\n");
					}
				} else {
					System.err.print("Clicked on the line/out of bounds\n");
				}
			} else if (screen == 1) {
				if(win() != Players.BLANK) {
					if(x > 121 && x < 191 && y > 118 && y < 146) {
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								gameBoard[i][j] = Players.BLANK;
							}
						}
						player = 1;
						component.repaint();
					}
				}
			}
		}
		
		public Players win() {
			for(int i = 0; i < 3; i++) {
				if(gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][2] == gameBoard[i][1] && gameBoard[i][0] != Players.BLANK) {
					return gameBoard[i][0];
				}
				if(gameBoard[0][i] == gameBoard[1][i] && gameBoard[2][i] == gameBoard[1][i] && gameBoard[0][i] != Players.BLANK) {
					return gameBoard[0][i];
				}
			}
			if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != Players.BLANK) {
				return gameBoard[0][0];
			}
			if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[0][2] && gameBoard[1][1] != Players.BLANK) {
				return gameBoard[1][1];
			}
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(gameBoard[i][j] == Players.BLANK) {
						return Players.BLANK;
					}
				}
			}
			return Players.TIE;
		}
	}
}
