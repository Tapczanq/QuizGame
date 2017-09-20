package quiz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
import javax.swing.ScrollPaneConstants;

public class QuizEditor {
	QuizCard card;
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private JFrame frame;
	
	public void createGui(){
		frame =new JFrame("quiz card editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel=new JPanel();
		Font bigFont=new Font("sanserif",Font.BOLD,24);
		question=new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		
		JScrollPane scrollQuestion = new JScrollPane(question);
		scrollQuestion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollQuestion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		JScrollPane scrollAnswer = new JScrollPane(answer);
		scrollAnswer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollAnswer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton nextButton = new JButton("next card");
		
		cardList = new ArrayList<QuizCard>();
		
		JLabel labelQuestion = new JLabel("Question:");
		JLabel labelAnswer = new JLabel("Answer:");
		
		mainPanel.add(labelQuestion);
		mainPanel.add(scrollQuestion);
		mainPanel.add(labelAnswer);
		mainPanel.add(scrollAnswer);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		JMenuBar menu = new JMenuBar();
		JMenu menuFile= new JMenu("File");
		JMenuItem newOption= new JMenuItem("New");
		JMenuItem saveOption = new JMenuItem("Save");
		newOption.addActionListener(new NewMenuListener());
		saveOption.addActionListener(new SaveMenuListener());
		menuFile.add(newOption);
		menuFile.add(saveOption);
		menu.add(menuFile);
		frame.setJMenuBar(menu);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500, 600);
		frame.setVisible(true);
		}
	
	public class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			QuizCard card = new QuizCard(question.getText(),answer.getText());
			cardList.add(card);
			cleanCard();
		}
		}
	
	public class SaveMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			QuizCard card = new QuizCard(question.getText(),answer.getText());
			cardList.add(card);
			
			JFileChooser dataFile = new JFileChooser();
			dataFile.showSaveDialog(frame);
			saveFile(dataFile.getSelectedFile());		
		}
		}
	
	public class NewMenuListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			cardList.clear();
			cleanCard();
			
		}
	}

	private void cleanCard(){
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}
	private void saveFile(File file){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			for (QuizCard card : cardList){
				writer.write(card.getQuestion()+"/");
				writer.write(card.getAnswer()+"\n");
			}
			writer.close();
		} catch (IOException ex){
			System.out.println("can not save file");
			ex.printStackTrace();
		}
	}
	
}
