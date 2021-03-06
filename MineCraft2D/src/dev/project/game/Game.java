package dev.project.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.project.game.display.Display;
import dev.project.game.gfx.Assets;
import dev.project.game.gfx.GameCamera;
import dev.project.game.input.KeyManager;
import dev.project.game.input.MouseManager;
import dev.project.game.states.GameState;
import dev.project.game.states.MenuState;
//import dev.project.game.states.MenuState;
//import dev.project.game.states.SettingState;
import dev.project.game.states.States;

public class Game implements Runnable { 					// Runnable is the Interface for Thread 
	
	private Display display;
	private int width,height;
	private boolean running = false; 
	private String title;
	
	private Thread thread; 
	
	private BufferStrategy buffer;						// Creates Buffer for our game
	private Graphics brush;								// All drawing of graphics is associated with this object
	
	//States
	public States gameState;
	private States menuState;
//	private States settingState;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//Camera
	private GameCamera gameCamera;
	
	//Handler
	private Handler handler;
	
	public Game(String title, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init()
	{
		display = new Display(title,width,height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0,0);
		
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
//		settingState = new SettingState(this);
		States.SetState(menuState);
	}
	
	
	private void update(){
			
			keyManager.update();
			
			if(States.getState() != null)
				States.getState().update();;
	}

	private void render()
	{
	
		buffer = display.canvas.getBufferStrategy();
		
			if(buffer == null)
			{
				display.canvas.createBufferStrategy(3);
				return;
			}
		
		brush = buffer.getDrawGraphics(); 		// Initializing Graphics Object
		
		brush.clearRect(0, 0, width, height); 	//Clear Screen
		
		if(States.getState() != null)
			States.getState().render(brush);

		
		
		buffer.show();
		brush.dispose();
	}
	
	int fps = 60;
	double timePerTick = 1000000000 / fps;
	double delta = 0;
	long now ;
	long lastTime = System.nanoTime();
	long timer = 0;
	int ticks = 0;
	
	public void run()
	{
		init();
	
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1)
			{
					update();
					render();
					ticks++;
					delta--;
			}
			
			if(timer >= 1000000000)
			{
				System.out.println("Ticks: "+ticks);
				timer = 0;
				ticks = 0;
			}
			
		}
			stop();
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public MouseManager getMouseManager(){
		return mouseManager;
	}
	
	public GameCamera getGameCamera(){
		return gameCamera;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public synchronized void start()				// Synchronized Allows Execution of single thread 
	{												// while accessing the method. Else, two threads will access
		if(running)									// same method at same time
			return;
		
		running = true;								
		thread = new Thread(this);					
		thread.start();
	}

	public synchronized void stop()
	{
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();							// This may create Exception so surround it with try block 
		} catch (InterruptedException e) {
			System.out.print("Thread Execption Caused: ");
			e.printStackTrace();
		}
	}

}
