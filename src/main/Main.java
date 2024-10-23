package main;

import java.util.Iterator;

import controller.Controller;
import logic.Game;
import view.MainWindow;

public class Main {
	
	private static MainWindow _mainWindow;
	private static Controller _controller;
	private static Game _game;
	
	public static void main(String[] args) {
		_game = new Game();
		_controller = new Controller(_game);
		_mainWindow = new MainWindow(_controller);
	}

}
