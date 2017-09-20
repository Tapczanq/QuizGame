package quiz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class QuizGame {

	private JTextArea question;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardindex;
	private JFrame frame;
	private JButton nextButton;
	private boolean showAnswer;
	
	public void go(){
		
		frame= new JFrame("Quiz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel= new JPanel();
		Font bigFont=new Font("sanserafi",Font.BOLD,24);
		
		question = new JTextArea(10,20);
		question.setFont(bigFont);
		question.setLineWrap(true);
		question.setEditable(false);
		
		JScrollPane scroll= new JScrollPane(question);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		nextButton=new JButton("show question");
		mainPanel.add(scroll);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		
		JMenuBar menuBar= new JMenuBar();
		JMenu menuFile= new JMenu("File");
		JMenuItem openOption= new JMenuItem("open a collection of cards");
		openOption.addActionListener(new OpenMenuListener());
		menuFile.add(openOption);
		menuBar.add(menuFile);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640, 500);
		frame.setVisible(true);
	}
	public class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(showAnswer){
				question.setText(currentCard.getAnswer());
				nextButton.setText("next card");
				showAnswer=false;
			}else{
				if(currentCardindex < cardList.size()){
					showNextCard();
				}else{
					question.setText("It was the last card");
					nextButton.setEnabled(false);
				}
			}
			
		}
		
	}
	public class OpenMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser dialogFile = new JFileChooser();
			dialogFile.showOpenDialog(frame);
			readFile(dialogFile.getSelectedFile());
			
		}

	}
	private void readFile(File file){
		cardList = new ArrayList<QuizCard>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line=reader.readLine())!=null){
				createCard(line);
			}
			reader.close();
		}catch(Exception ex){
			System.out.println("can not read card file");
			ex.printStackTrace();
		}
		showNextCard();
	}
	private void createCard(String lineData){
		String [] results= lineData.split("/");
		QuizCard card=new QuizCard(results[0], results[1]);
		cardList.add(card);
		System.out.println("create card!");
	}
	private void showNextCard(){
		currentCard= cardList.get(currentCardindex);
		currentCardindex ++;
		question.setText(currentCard.getQuestion());
		nextButton.setText("show answer");
		showAnswer = true;
	}
}
