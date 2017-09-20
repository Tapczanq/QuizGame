package quiz;

public class Demo {

	public static void main(String[] args) {
		
		QuizEditor gui = new QuizEditor();
		gui.createGui();
		QuizGame game=new QuizGame();
		game.go();
	}

}
