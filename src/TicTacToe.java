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
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TicTacToe {
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
	
	public static Random generator = new Random(System.currentTimeMillis());
	
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
			this.repaint();
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
		
		public int screen = 0;
		
		public int player = 1;
		
		public boolean CPUPlaying = false;
		
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			if(screen == 0) {
				g2.setColor(new Color(0,102,255));
				g2.setFont(new Font("TimesRoman",Font.BOLD,45));
				g2.drawString("TICTACTOE", 20, 60);
				g2.setColor(new Color(0,153,255));
				g2.fill(new RoundRectangle2D.Double(35,200,250,100,15,15));
				g2.setColor(new Color(255,255,255));
				g2.setFont(new Font("TimesRoman",Font.BOLD,70));
				g2.drawString("PLAY", 65, 270);
			}
			if(screen == 1) {
				g2.setColor(new Color(0,153,255));
				g2.fill(new RoundRectangle2D.Double(35,50,250,100,15,15));
				g2.setColor(new Color(255,255,255));
				g2.setFont(new Font("TimesRoman",Font.BOLD,70));
				g2.drawString("1 P", 120, 120);
				g2.setColor(new Color(0,153,255));
				g2.fill(new RoundRectangle2D.Double(35,200,250,100,15,15));
				g2.setColor(new Color(255,255,255));
				g2.setFont(new Font("TimesRoman",Font.BOLD,70));
				g2.drawString("2 P", 120, 270);
			}
			if(screen == 2) {
				g2.setColor(new Color(0,0,0));
				g2.setFont(new Font("TimesRoman",Font.PLAIN,30));
				g2.drawString("You play as... ", 75, 40);
				g2.setColor(new Color(0,153,255));
				g2.fill(new RoundRectangle2D.Double(35,50,250,100,15,15));
				g2.setColor(new Color(255,255,255));
				g2.setFont(new Font("TimesRoman",Font.BOLD,70));
				g2.drawString(" X", 120, 120);
				g2.setColor(new Color(0,153,255));
				g2.fill(new RoundRectangle2D.Double(35,200,250,100,15,15));
				g2.setColor(new Color(255,255,255));
				g2.setFont(new Font("TimesRoman",Font.BOLD,70));
				g2.drawString(" O", 120, 270);
			}
			if(screen == 3) {
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
				if(win(gameBoard) != Players.BLANK) {
					g2.setColor(new Color(102,255,153,200));
					g2.fill(new RoundRectangle2D.Double(50,50,200,200,10,10));
					g2.setFont(new Font("TimesRoman",Font.PLAIN,20));
					g2.setColor(new Color(0,0,0));
					if(win(gameBoard) == Players.TIE) {
						g2.drawString("It's a tie!", 120, 75);
					} else {
						g2.drawString("Player " + win(gameBoard) + " won!",95,75);
					}
					g2.setColor(new Color(153,255,255));
					g2.fill(new RoundRectangle2D.Double(118,90,70,30,10,10));
					g2.setColor(new Color(0,0,0));
					g2.drawString("Restart", 125, 110);
				}
			}
		}
		
		public void mousePressed(int x, int y) {
			if(screen == 0) {
				if(37 < x && x < 287 && y > 225 && y < 326) {
					screen = 1;
					this.repaint();
					return;
				}
				System.err.println("Out of bounds");
				return;
			}
			if(screen == 1) {
				if(37 < x && x < 287 && y > 75 && y < 176) {
					screen = 2;
					this.repaint();
					return;
				}
				if(37 < x && x < 287 && y > 225 && y < 326) {
					screen = 3;
					player = 1;
					CPUPlaying = false;
					this.repaint();
					return;
				}
				System.err.println("Out of bounds");
				return;
			}
			if(screen == 2) {
				if(37 < x && x < 287 && y > 75 && y < 176) {
					screen = 3;
					player = 1;
					CPUPlaying = true;
					this.repaint();
					return;
				}
				if(37 < x && x < 287 && y > 225 && y < 326) {
					screen = 3;
					player = 2;
					playAIX();
					CPUPlaying = true;
					this.repaint();
					return;
				}
				System.err.println("Out of bounds");
				return;
			}
			if(screen == 3 && win(gameBoard) == Players.BLANK) {
				if((x-8)/100 < 3 && (y-30)/100 < 3) {
					if(gameBoard[(x-8)/100][(y-30)/100] == Players.BLANK) {
						if(CPUPlaying == false) {
							if(player == 1) {
								player = 2;
								gameBoard[(x-8)/100][(y-30)/100] = Players.X;
								System.out.println("Place X on (" + x/100 + "," + y/100 + ")");
							} else {
								player = 1;
								gameBoard[(x-8)/100][(y-30)/100] = Players.O;
								System.out.println("Place O on (" + x + "," + y + ")");
							}
						}
						if(player == 1 && CPUPlaying == true) {
							gameBoard[(x-8)/100][(y-30)/100] = Players.X;
							System.out.println("Place X on (" + x/100 + "," + y/100 + ")");
							playAIO();
							this.repaint();
						}
						if(player == 2 && CPUPlaying == true) {
							gameBoard[(x-8)/100][(y-30)/100] = Players.O;
							System.out.println("Place O on (" + x/100 + "," + y/100 + ")");
							playAIX();
							this.repaint();
						}
						if(win(gameBoard) != Players.BLANK) {
							if(win(gameBoard) == Players.TIE) {
								System.out.println("It's a tie!");
							} else {
								System.out.println("Player " + win(gameBoard) + " won!"); 
							}
						}
						component.repaint();
					} else {
						System.err.print("A player already placed here\n");
					}
				} else {
					System.err.print("Clicked on the line/out of bounds\n");
				}
			} else if (screen == 3) {
				if(win(gameBoard) != Players.BLANK) {
					if(x > 121 && x < 191 && y > 118 && y < 146) {
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								gameBoard[i][j] = Players.BLANK;
							}
						}
						screen = 1;
						component.repaint();
					}
				}
			}
		}
		public void playAIX() {
			double chance = -10, currentChance;
			ArrayList<Integer> placesX = new ArrayList<Integer>();
			ArrayList<Integer> placesY = new ArrayList<Integer>();
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(gameBoard[i][j] == Players.BLANK) {
						gameBoard[i][j] = Players.X;
						currentChance = XAIO(gameBoard);
						if(currentChance > chance) {
							chance = currentChance;
							placesX.clear();
							placesY.clear();
							placesX.add(i);
							placesY.add(j);
						}
						if(currentChance == chance) {
							placesX.add(i);
							placesY.add(j);
						}
						gameBoard[i][j] = Players.BLANK;
					}
				}
			}
			int i = generator.nextInt(placesX.size());
			gameBoard[placesX.get(i)][placesY.get(i)] = Players.X;
			System.out.println("Placed X on (" + placesX.get(i) + "," + placesY.get(i) + ")");
			System.out.println(1-Math.pow((1.0-Math.pow((chance+10.0)/11.0, 2.33)), 1.0/2.33));
			this.repaint();
		}
		public double XAIX(Players[][] game) {
			Players[][] game2 = game.clone();
			double x = -10;
			if(win(game2) != Players.BLANK) {
				if(win(game2) == Players.O) {
					return -10;
				} else if(win(game2) == Players.X) {
					return 1;
				}
				return 0;
			}
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(game2[i][j] == Players.BLANK) {
						game2[i][j] = Players.X;
						if(XAIO(game2) > x) {
							x = XAIO(game2);
						}
						game2[i][j] = Players.BLANK;
					}
				}
			}
			return x;
		}
		public double XAIO(Players[][] game) {
			Players[][] game2 = game.clone();
			if(win(game2) != Players.BLANK) {
				if(win(game2) == Players.O) {
					return -10;
				} else if(win(game2) == Players.X) {
					return 1;
				}
				return 0;
			}
			double x = 0;
			int blank = 0;
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(game2[i][j] == Players.BLANK) {
						game2[i][j] = Players.O;
						blank++;
						x += XAIX(game2);
						game2[i][j] = Players.BLANK;
					}
				}
			}
			x /= blank;
			return x;
		}
		public void playAIO() {
			double chance = 0;
			if(win(gameBoard) != Players.BLANK) {
				return;
			}
			double currentChance;
			chance = -11;
			ArrayList<Integer> placesX = new ArrayList<Integer>();
			ArrayList<Integer> placesY = new ArrayList<Integer>();
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(gameBoard[i][j] == Players.BLANK) {
						gameBoard[i][j] = Players.O;
						currentChance = OAIX(gameBoard);
						if(currentChance > chance) {
							chance = currentChance;
							placesX.clear();
							placesY.clear();
							placesX.add(i);
							placesY.add(j);
						}
						if(currentChance == chance) {
							placesX.add(i);
							placesY.add(j);
						}
						gameBoard[i][j] = Players.BLANK;
					}
				}
			}
			int i = generator.nextInt(placesX.size());
			gameBoard[placesX.get(i)][placesY.get(i)] = Players.O;
			System.out.println("Placed O on (" + placesX.get(i) + "," + placesY.get(i) + ")");
			System.out.println(1-Math.pow((1.0-Math.pow((chance+10.0)/11.0, 2.33)), 1.0/2.33));
			this.repaint();
		}
		public double OAIX(Players[][] game) {
			Players[][] game2 = game.clone();
			double x = 0;
			double blank = 0;
			if(win(game2) != Players.BLANK) {
				if(win(game2) == Players.O) {
					return 1;
				} else if(win(game2) == Players.X) {
					return -10;
				}
				return 0;
			}
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(game2[i][j] == Players.BLANK) {
						game2[i][j] = Players.X;
						x += OAIO(game2);
						blank++;
						game2[i][j] = Players.BLANK;
					}
				}
			}
			x /= blank;
			return x;
		}
		public double OAIO(Players[][] game) {
			Players[][] game2 = game.clone();
			if(win(game2) != Players.BLANK) {
				if(win(game2) == Players.O) {
					return 1;
				} else if(win(game2) == Players.X) {
					return -10;
				}
				return 0;
			}
			double x = -10;
			double chance;
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(game2[i][j] == Players.BLANK) {
						game2[i][j] = Players.O; chance = OAIX(game2);
						if(x < chance) {
							x = chance;
						}
						game2[i][j] = Players.BLANK;
					}
				}
			}
			return x;
		}
		public Players win(Players[][] game) {
			for(int i = 0; i < 3; i++) {
				if(game[i][0] == game[i][1] && game[i][2] == game[i][1] && game[i][0] != Players.BLANK) {
					return game[i][0];
				}
				if(game[0][i] == game[1][i] && game[2][i] == game[1][i] && game[0][i] != Players.BLANK) {
					return game[0][i];
				}
			}
			if(game[0][0] == game[1][1] && game[1][1] == game[2][2] && game[0][0] != Players.BLANK) {
				return game[0][0];
			}
			if(game[2][0] == game[1][1] && game[1][1] == game[0][2] && game[1][1] != Players.BLANK) {
				return game[1][1];
			}
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(game[i][j] == Players.BLANK) {
						return Players.BLANK;
					}
				}
			}
			return Players.TIE;
		}
	}
}
