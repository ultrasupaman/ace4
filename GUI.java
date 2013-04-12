

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class GUI extends JPanel implements ActionListener, KeyListener{
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
	private MessageTriple mt;
	//private Scanner inputReader = new Scanner(System.in);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
		mt = new MessageTriple("", "", "");
	}

	public JFrame getFrame(){
		return frame;
	}



	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Chat Shack");
		frame.setBounds(100, 100, 450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		chats = new ArrayList<String>();
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);

		JMenu menuMode = new JMenu("Mode");
		menuBar.add(menuMode);

		//Used if the user wants to be in chat mode and just send plain text.
		JMenuItem mntmModes = new JMenuItem("Text mode");
		menuMode.add(mntmModes);

		//Used if the user wants to be in image mode and send images.
		JMenuItem mntmImage = new JMenuItem("Image Mode");
		menuMode.add(mntmImage);

		//Used if the user wants to be in audio mode and send music/audio files.
		JMenuItem mntmAudio = new JMenuItem("Audio Mode");
		menuMode.add(mntmAudio);

		JMenuItem mntmLogs = new JMenuItem("Logs...");
		menuFile.add(mntmLogs);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setActionCommand("Exit");
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}

		});
		//mntmExit.addComponentListener(this)
		menuFile.add(mntmExit);

		JMenu menuChatroom = new JMenu("ChatRoom");
		menuBar.add(menuChatroom);

		JMenuItem menuRoom = new JMenuItem("Room 1");
		menuChatroom.add(menuRoom);

		JMenuItem menuRoomTwo = new JMenuItem("Room 2");
		menuChatroom.add(menuRoomTwo);
		frame.getContentPane().setLayout(null);

		//		JPanel convoWindow = new JPanel();
		//		//convoWindow.setBackground(new Color(0, 255, 204));
		//		convoWindow.setBounds(10, 11, 300, 260);
		//		frame.getContentPane().add(convoWindow);

		chatBox = new JTextArea();
		chatBox.setBackground(new Color(0, 255, 204));
		chatBox.setBounds(10, 300, 300, 50);
		chatBox.setVisible(true);
		chatBox.setText("" + cc.inputReader);
		chatBox.setToolTipText("Individual user's chat");
		chatBox.addKeyListener(this);

		frame.getContentPane().add(chatBox);

		chatBox1 = new JTextArea(10, 2);
		chatBox1.setBackground(new Color(235, 255, 204));
		chatBox1.setEditable(false);
		chatBox1.setBounds(20, 40, 200, 200);
		chatBox1.setText("" + cc.inputReader);
		chatBox1.setVisible(true);
		chatBox1.setLineWrap(true);
		chatBox1.setToolTipText("Whole conversation");
		scroller = new JScrollPane(chatBox1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		frame.getContentPane().add(chatBox1);

		JButton btnSend = new JButton("Send");
		btnSend.setBounds(320, 313, 89, 23);
		btnSend.addActionListener(this);
		btnSend.setActionCommand("Send");
		frame.getContentPane().add(btnSend);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(320, 343, 89, 23);
		btnClear.addActionListener(this);
		btnClear.setActionCommand("Clear");
		frame.getContentPane().add(btnClear);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(320, 373, 89, 23);
		btnSave.addActionListener(this);
		btnSave.setActionCommand("Save");
		frame.getContentPane().add(btnSave);

		JButton sendImage = new JButton();
		sendImage.setBounds(220, 273, 89, 23);
		sendImage.addActionListener(this);
		sendImage.setIcon(new ImageIcon("src/picturebuttonforgui.png"));
		sendImage.setActionCommand("SendImage");
		sendImage.setToolTipText("Chose an image to attach.");
		frame.getContentPane().add(sendImage);

		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(320, 283, 89, 23);
		btnLoad.addActionListener(this);
		btnLoad.setActionCommand("Load");
		//btnLoad.setIcon(new ImageIcon("src/picturebuttonforgui.png"));
		frame.getContentPane().add(btnLoad);

		JLabel usersLabel = new JLabel("LOL");
		usersLabel.setText(" " + cc.getUserName());
		usersLabel.setBounds(10, 262, 300, 50);
		frame.getContentPane().add(usersLabel);

		convoLabel = new JLabel("LOL");
		convoLabel.setText("Current Conversation");
		convoLabel.setBounds(21, 5, 300, 50);
		frame.getContentPane().add(convoLabel);

		JPanel avatarSpace = new JPanel();
		avatarSpace.setBackground(new Color(0, 0, 153));
		avatarSpace.setBounds(320, 11, 104, 109);
		frame.getContentPane().add(avatarSpace);
		chats.add(chatBox.getText());
		//chatBox1.add(scroller);
		frame.add(scroller);
	}

	/*This method will get all the text in the textarea and write it to a file. Then
	 * if the user clicks the "History" button, then this file will be displayed either in
	 * the current textarea or in a newly opened window. 
	 * This user will be prompted if they save the chat history to give the
	 * file a name and then save it. Also, if they want to load a file, they
	 * should select the loadFile button to do so.*/
	public void writeToFile(String filename){
		String output = "";
		try{
			// Create file
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(chatBox1.getText());
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}	
	}


	public ArrayList<String> loadFile(String filename) {
		//Setup the arraylist to be returned.
		ArrayList<String> returnFile = new ArrayList<String>();

		//Load the required file into a file object
		File file = new File(filename);

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				//Get the next line of the file, and add it into the returnFile arraylist
				String line = scanner.nextLine();
				returnFile.add(line);
				chatBox1.setText("" + returnFile);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found: "  + e.getLocalizedMessage());
		}

		return returnFile;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton o = (JButton) arg0.getSource();

		String action = o.getActionCommand();
		if(chatBox.getText().equals("")){
			System.out.println("empty");

		}
		if(action.equals("Clear")){
			System.out.println("LOL");
			chatBox.setText("");
			chatBox1.setText("");
			//chats.clear();
		}
		if(action.equals("Send")){

			System.out.println("MT user: " + mt.getUser());
			chats.add(chatBox.getText());
			chatBox1.setText( mt.getUser() + chats + "\n");//mt.getUser() is nothing just now.
			chatBox.setText("");
		}

		if(action.equals("SendImage")){
			fc = new JFileChooser();
			returnVal = fc.showOpenDialog(GUI.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();
				//log.append("Opening: " + file.getName() + "." + newline);
				String filepath = selectedFile.getAbsolutePath();

			}

		}

		if(action.equals("Save")){

			writeToFile("Greg.txt");
			//			 chats.add(chatBox.getText());
			//				chatBox1.setText( chats + "\n");
			//				chatBox.setText("");
		}

		if(action.equals("Load")){

			convoLabel.setText("Previous chats...");
			loadFile("Greg.txt");
			//			 chats.add(chatBox.getText());
			//				chatBox1.setText( chats + "\n");
			//				chatBox.setText("");
		}

		else{
			//chats.add(chatBox.getText());
			//chatBox1.setText(" " + chats + "\n");
			convoLabel.setText("Current Conversation");
			System.out.println("pressed");
		}
	}

	/*This is a method that reads from the dictionary.txt file, chooses one of the 
	 * client's words or phrases and then replaces it with something else, thus
	 * making that particular client's chat hilarious. */
	public void funnyChat(){
		System.out.println(chatBox.getText());
		if(chatBox.getText().equals("null")){

			chatBox.setText("Swally ");
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int keycode = arg0.getKeyChar();
		System.out.println(keycode);
		if(keycode == 10){
			chats.add(chatBox.getText());
			chatBox1.setText( cc.getUserName() + ": " + chats + "\n");
			chatBox.setText("");
			convoLabel.setText("Current Conversation");
			funnyChat();
			System.out.println("LL");
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}