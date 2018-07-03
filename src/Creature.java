import java.io.File;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Creature extends Item {
	
	boolean alive;

	public Creature(String pName, String pType, String pDes, double pPrice, String pSize) {
		super(pName, pType, pDes, pPrice, pSize);
		alive = true;
	}
	
	public boolean getAlive() { return alive; }
	public void setAlive(boolean pAlive) { alive = pAlive; }
	
	public void roar() {
		System.out.println("ROAAARRRRRR");
		JFXPanel fxPanel = new JFXPanel();
		Media hit = new Media(new File("./src/roar.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}

}
