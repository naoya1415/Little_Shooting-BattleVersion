package sample.PC_Battle_Implement;


import gameLogic.GameContainer;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



/**
 *  PC(AWT)向けサンプル実装の、エントリーポイント
 * @author n-dolphin
 * @version 1.00 2014/01/17
 */
public class PC_Battle_EntryPoint  extends JFrame {

	
	/**
	 * 画面の幅
	 */
	final int windowWidth = 500;
	/**
	 * 画面の高さ
	 */
	final int windowHeight = 800;
	
	
	public static void main(String[] args) {
		new PC_Battle_EntryPoint();
	}
	/**
	 * エントリーポイント
	 * @param args
	 */
	public PC_Battle_EntryPoint() {
		Dimension dimOfScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dimOfScreen.width / 2 - windowWidth / 2, dimOfScreen.height
				/ 2 - windowHeight / 2, windowWidth, windowHeight);
		
		
		setResizable(false);
		setTitle("Little Invader Battle Version - Ichikawa Naoya");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MyJPanel panel = new MyJPanel();
		Container c = getContentPane();
		c.add(panel);
		
		panel.setFocusable(true);
		setVisible(true);
	}

	/**
	 * 描画クラス
	 * @author n-dolphin
	 *
	 */
	public class MyJPanel extends JPanel implements ActionListener,
			MouseListener, MouseMotionListener ,KeyListener{
	
		GameContainer gc ;
		
		/* 全体の設定に関する変数 */
		Dimension dimOfPanel;
		Timer timer;
		
		
		/**
		 * コンストラクタ（ゲーム開始時の初期化） 
		 */
		public MyJPanel() {
			// 全体の設定
			dimOfPanel =null;
			
			getSize();
		
			this.requestFocus();
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
			

			timer = new Timer(50, this);
			timer.start();
		}

		/* 一定時間ごとの処理（ActionListener に対する処理） */
		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
		
		/* パネル上の描画 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if(dimOfPanel ==null){
				dimOfPanel = getSize();
				gc = new PC_Battle_GameContainer(dimOfPanel.width,dimOfPanel.height);
			}
			
			gc.update(g);
		}
		
		/* MouseMotionListener に対する処理 */
		// マウスを動かす
		@Override
		public void mouseMoved(MouseEvent e) {
			gc.move(e.getX(),0);
		}

		// マウスをドラッグする
		@Override
		public void mouseDragged(MouseEvent e) {
			gc.move(e.getX(),0);
		}
		// マウスボタンを押下する
		@Override
		public void mousePressed(MouseEvent e) {
			gc.pointerDown(e.getX(),e.getY());
		}
		
		// マウスボタンをクリックする
		@Override
		public void mouseClicked(MouseEvent e) {	
			gc.touch(e.getX(),e.getY());
		}
		// マウスボタンを離す
		@Override
		public void mouseReleased(MouseEvent e) {
			gc.pointerUp(e.getX(),e.getY());
		}
		
		// マウスが領域内に入る
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		
		// マウスが領域外へ出る
		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
//			gc.keyPressed(e.getKeyChar());	
		}

		@Override
		public void keyPressed(KeyEvent e) {
			gc.keyPressed(e.getKeyChar());	
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
	}
}
