package GameMenu;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import GameMenu.sprite.Player;
import GameMenu.sprite.Alien;
import GameMenu.sprite.Input;
import GameMenu.sprite.Sprite;
import GameMenu.Settings;
import GameMenu.sprite.Shot;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;


public class StartGame extends Parent{
	  Group root = new Group();


	 Random rnd = new Random();
     Scene scene;
	 Pane playfieldLayer = new Pane();
	 Pane scoreLayer = new Pane();
	 Pane gameOver = new Pane();
	 Image playerImage;
	 Image enemyImage;
	 Image background;
	 Image playerBulletImage;
     Pane layer;
     List<Shot> playerBulletList = new ArrayList<>();
	 List<Player> players = new ArrayList<>();
	 List<Alien> enemies = new ArrayList<>();
	 public int k = 0;
	 public boolean ok = true;
	 public int count = 0;
	 public int count2 = 0;
	 public int score = 0;
	 Text collisionText = new Text();
	 Text Score = new Text();
	 Text GameOver = new Text();
	 boolean collision = false;
     Label label1 = new Label();
     Pane root1;
    
	 public void startgame(Stage SecondaryStage) throws IOException {
		 //Label label1 = new Label();
		 InputStream isbg = Files.newInputStream(Paths.get("resources/space.jpg"));
	        background = new Image(isbg);
	        isbg.close();
	        InputStream isshot = Files.newInputStream(Paths.get("resources/shot.png"));
	        playerBulletImage = new Image(isshot);
	        isshot.close();
	        ImageView imgView = new ImageView(background);
			imgView.setFitWidth(800);
			imgView.setFitHeight(600);
		 root.getChildren().addAll(imgView,playfieldLayer,scoreLayer,label1);
		 scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

	        SecondaryStage.setScene( scene);
	        SecondaryStage.show();
	        InputStream is = Files.newInputStream(Paths.get("resources/spacship.png"));
	        playerImage = new Image(is);
	        is.close();
	        InputStream is1 = Files.newInputStream(Paths.get("resources/alien.png"));
	        enemyImage = new Image(is1);
	        is1.close();
	        createScoreLayer();
	        createPlayers();
	       
		 AnimationTimer gameLoop = new AnimationTimer() {

	         @Override
	         public void handle(long now) {

	             Random random = new Random();
	             
	             players.forEach(sprite -> sprite.processInput());

	             spawnEnemies(true);
	             if(ok && count == 0)
		            	moveSprites(enemies);
		            count++;	
		            count2++;
		            if(count == 100)
		            	{
		            	relocateSprites(enemies);
		            	count = 0;
		            	}
	            
	             players.forEach(sprite -> sprite.move());
	             enemies.forEach(sprite -> sprite.move());
	             players.forEach(sprite -> spawnPrimaryWeaponObjects(sprite));
	             playerBulletList.forEach(sprite -> sprite.move());
	             checkCollisions();

	            
	             players.forEach(sprite -> sprite.updateUI());
	             enemies.forEach(sprite -> sprite.updateUI());
                 playerBulletList.forEach(sprite -> sprite.updateUI());
	             enemies.forEach(sprite -> sprite.checkRemovability());

	             removeSprites( enemies);

	             updateScore();
	             if(ok == false)
	            	 this.stop();
	             gameOver();
	         }
	     };
	     gameLoop.start();
	 }
	 
     public void relocateSprites(List<? extends Sprite> spriteList) {
    	 Iterator<? extends Sprite> iter = spriteList.iterator();
    	    while( iter.hasNext()) {
    	    	Sprite sprite = iter.next();
    	    	if(sprite.getDx() != 0 ) {
    	    		sprite.setDx(-sprite.getDx());
    	    		sprite.setDy(-sprite.getDy());
    	    		
    	    		//sprite.updateUI();
    	    	}
    	    }
     }

     public void gameOver() {
    	 if(collision == true) {
    		 GameOver.setFont(Font.font(null, FontWeight.BOLD, 64));
    		 GameOver.setStroke(Color.BLACK);
    		 GameOver.setFill(Color.RED);
    		 GameOver.setText("GAME OVER");
    		 GameOver.setVisible(true);
    		 double x = (Settings.SCENE_WIDTH - GameOver.getBoundsInLocal().getWidth()) / 2;
    	        double y = (Settings.SCENE_HEIGHT - GameOver.getBoundsInLocal().getHeight()) / 2;
    	        GameOver.relocate(x, y);
    		 players.forEach(sprite -> sprite.removeFromLayer());
    		 enemies.forEach(sprite -> sprite.removeFromLayer());
    		 playerBulletList.forEach(sprite -> sprite.removeFromLayer());
    		 ok = false;
    		 
    	 }
    	 if(enemies.isEmpty()) {
    		 GameOver.setFont(Font.font(null, FontWeight.BOLD, 64));
    		 GameOver.setStroke(Color.BLACK);
    		 GameOver.setFill(Color.RED);
    		 GameOver.setText("YOU WIN!");
    		 GameOver.setVisible(true);
    		 double x = (Settings.SCENE_WIDTH - GameOver.getBoundsInLocal().getWidth()) / 2;
    	        double y = (Settings.SCENE_HEIGHT - GameOver.getBoundsInLocal().getHeight()) / 2;
    	        GameOver.relocate(x, y);
    		 players.forEach(sprite -> sprite.removeFromLayer());
    		 enemies.forEach(sprite -> sprite.removeFromLayer());
    		 playerBulletList.forEach(sprite -> sprite.removeFromLayer());
    		 ok = false;
    	 }
    	 scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
		  () {

		        @Override
		        public void handle(KeyEvent t) {
		          if(t.getCode()==KeyCode.ESCAPE)
		          {
		              System.out.println("click on escape");
		           Stage sb = (Stage)label1.getScene().getWindow();//use any one object
		           sb.close();
		          }
		        }
		    });
     }
	public void createScoreLayer() {


        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);
        
        Score.setFont( Font.font( null, FontWeight.BOLD, 25));
        Score.setStroke(Color.BLACK);
        Score.setFill(Color.RED);
        
        scoreLayer.getChildren().addAll( collisionText,Score,GameOver);

        
        collisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
        collisionText.relocate(x, y);
        Score.relocate(0, 0);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);


    }

public void createPlayers() {

    
    Input input = new Input( scene);

    
    input.addListeners(); 

    Image image = playerImage;

   
    double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
    double y = Settings.SCENE_HEIGHT * 0.7;

    
    Player player = new Player(playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, input, x, y);

    
    players.add( player);

}
public void spawnEnemies( boolean random) {

    if( random && k!=0) {
        return;
    }

  
    Image image = enemyImage;

    
    double speed = rnd.nextDouble() * 1.0 + 2.0;

    
    double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
    double y = -image.getHeight();

    
    //Alien enemy = new Alien( playfieldLayer, image, x, y, 0, 0, speed, 0, 1,1);
    enemies = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {

            var alien = new Alien(playfieldLayer,image,310 + 18 * j,
                    Commons.ALIEN_INIT_Y + 18 * i, 0, 0, 0, 0, 1,1,310 + 18 * j,Commons.ALIEN_INIT_Y + 18 * i);
            alien.stopMovement();
            enemies.add(alien);
        }
    }
    k=1;
    

}

private void spawnPrimaryWeaponObjects( Player player) {
	 
	 player.chargePrimaryWeapon();

	   if( player.isFirePrimaryWeapon()) {
	    
	    Shot playerBullet;

	    Image image = playerBulletImage;
	    
	    
	    double x = player.getPrimaryWeaponX() - image.getWidth() / 2.0; 
	    double y = player.getPrimaryWeaponY();

	    double spread = player.getPrimaryWeaponBulletSpread();
	    double count = player.getPrimaryWeaponBulletCount();
	    double speed = player.getPrimaryWeaponBulletSpeed();
	    
	    
	    playerBullet = new Shot( playfieldLayer, image, x, y, 0, -speed, 0, 0);
	    playerBulletList.add( playerBullet);
	    
	    // left/right: vary x-axis position
	    for( int i=0; i < count / 2.0; i++) {

	        // left
	        playerBullet = new Shot( playfieldLayer, image, x, y, -spread * i, -speed, 0, 0);
	        playerBulletList.add( playerBullet);

	        // right
	        playerBullet = new Shot( playfieldLayer, image, x, y, spread * i, -speed, 0, 0);
	        playerBulletList.add( playerBullet);

	    }
	    
	    player.unchargePrimaryWeapon();
	   }
	 
	}

public void removeSprites(  List<? extends Sprite> spriteList) {
    Iterator<? extends Sprite> iter = spriteList.iterator();
    while( iter.hasNext()) {
        Sprite sprite = iter.next();

        if( sprite.isRemovable()) {

            score+=10;
            sprite.removeFromLayer();

            
            iter.remove();
        }
    }
}

public void moveSprites( List<? extends Sprite> spriteList) {
	Iterator<? extends Sprite> i = spriteList.iterator();
	Random rnd = new Random();
	int mark = rnd.nextInt(100);
	int temp = 0;
	while(i.hasNext()) {
		Sprite sprite = i.next();
		
		temp++;
		if(temp == mark) {
			
			int randomX = 0;
			while(randomX == 0) {
				
				randomX = ThreadLocalRandom.current().nextInt(-2, 2);
			}
			int randomY = 0;
			while(randomY == 0 ) {
				
				randomY = ThreadLocalRandom.current().nextInt(0, 2);
			}
				
			sprite.setDx(randomX);
			sprite.setDy(randomY);
			sprite.startMovement();
			//sprite.updateUI();
			
		}
	}
}
public void checkCollisions() {

    collision = false;

    for( Player player: players) {
        for( Alien enemy: enemies) {
            if( player.collidesWith(enemy)) {
                collision = true;
            }
        }
    }
    for( Shot shot: playerBulletList) {
    	for( Alien enemy: enemies) {
    		if(shot.collidesWith(enemy)) {
    			enemy.remove();
    			
    			shot.stopMovement();
    			shot.removeFromLayer();
    		}
    	}
    }
}
public void updateScore() {
	Score.setText(Integer.toString(score));
    
}
}
