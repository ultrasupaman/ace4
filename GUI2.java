import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


	public class GUI2 extends JPanel implements ActionListener, KeyListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JFrame frame;
		private JTextArea chatBox, chatBox1;
		private ArrayList<String> chats;
		private JScrollPane scroller;
		private JLabel convoLabel;
		private JFileChooser fc;
		int returnVal;
		private ChatClient cc = new ChatClient();
		private ChatServer cs = new ChatServer();
		private GUI g;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						GUI2 window = new GUI2();
						window.frame.setVisible(true);
						window.frame.setResizable(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		/**
		 * Create the application.
		 */
		public GUI2() {
			initialize();
			g = new GUI();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		public void initialize() {
			frame = new JFrame("Starting point");
			frame.setBounds(100, 100, 450, 450);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridLayout(2,1));
			
			JButton btnchat1 = new JButton("Single Chat");
			//btnchat1.setBounds(220, 223, 89, 23);
			btnchat1.addActionListener(this);
			btnchat1.setActionCommand("Chat 1");
			btnchat1.setIcon(new ImageIcon("src/singlechatace4.png"));
			frame.getContentPane().add(btnchat1);
			
			JButton btnchat2 = new JButton("Group Chat");
			btnchat2.setBounds(320, 223, 89, 23);
			btnchat2.setIcon(new ImageIcon("src/multiplechat_ace4.png"));
			btnchat2.addActionListener(this);
			btnchat2.setActionCommand("Chat 2");
		
			
			frame.getContentPane().add(btnchat2);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public JFrame getFrame(){
			return frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton o = (JButton) e.getSource();
			String action = o.getActionCommand();
			
			if(action.equals("Chat 1")){
				System.out.println("Chat pressed");
				frame.dispose();
				//g.initialize();
				this.frame=g.getFrame();
				frame.setVisible(true);
				
			}
			
			if(action.equals("Chat 2")){
				frame.dispose();
				//g.initialize();
				this.frame=g.getFrame();
				frame.setVisible(true);
			}
			
		}
}
