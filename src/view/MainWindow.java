/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import controller.Controller;
import logic.Observer;
import logic.WeightedMatrix;
import logic.WeightedMatrix.MatrixElement;
import objects.Card;
import objects.Pair;
import objects.Statics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 *
 * @author Pablo Blanco
 */
public class MainWindow extends JFrame implements Observer {

	private String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
	private String[] ranksS = { "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s", "Ts", "Js", "Qs", "Ks", "As" };
	private String[] ranksO = { "2o", "3o", "4o", "5o", "6o", "7o", "8o", "9o", "To", "Jo", "Qo", "Ko", "Ao" };
	private String[] suits = { "d", "c", "s", "h" };
	private ArrayList<String> range;
	private JTextField text;
	private WeightedMatrix matrix;
	private Map<JButton, MatrixElement> buttonMap;
	private Map<String, JButton> buttonMap2;
	private Map<String, JButton> buttonMap3;
	private Map<String, List<Pair>> queue;
	private Controller _controller;
	private List<Pair> ranking;
	private List<List<Pair>> comb = new ArrayList<>();
	private double numberOfPairs;
	private double numberOfPairsO;
	private double numberOfPairsS;
	private JTextField percentText;
	private List<JTextField> _onBoard;
	private int numOnBoard = 0;
	private JSlider progressBar;
	private List<Card> _board;
	private Map<String, List<String>> staticsMap;
	private Object message;
	private JPanel solPanel;
	private Color rouge = new Color(255, 102, 102);
	private Color bleu = new Color(153, 204, 255);
	private Color vert = new Color(153, 255, 153);
	private Color jaune = new Color(255, 255, 102);
	private Color gris = new Color(204, 204, 204);
	private Color pink = new Color(255, 182, 193);

	/**
	 * Creates new form MainWindow
	 */
	public MainWindow(Controller controller) {
		this._controller = controller;
		buttonMap = new HashMap<>();
		buttonMap2 = new HashMap<>();
		buttonMap3 = new HashMap<>();
		this.range = new ArrayList<>();
		_onBoard = new ArrayList<>();
		matrix = new WeightedMatrix(13, 13);
		text = new JTextField();
		ranking = new ArrayList<>();
		_board = new ArrayList<>();
		staticsMap = new LinkedHashMap<>();
		initRanking();
		initMatrix();
		range = new ArrayList<>();

		queue = new HashMap<>();

		initComponents();
	}

	private void initComponents() {

		queue = new HashMap<>();

		queue.put("pair", new ArrayList<>());

		for (String s : ranksS) {

			List<Pair> a = new ArrayList<>();

			queue.put(s, a);

		}

		for (String s : ranksO) {

			List<Pair> a = new ArrayList<>();

			queue.put(s, a);

		}

		setTitle("Hold`em distribution");

		JPanel mainPanel = new JPanel();

		setSize(new Dimension(500, 500));

		mainPanel.setLayout(new BorderLayout());

		JPanel panelito = new JPanel();

		JPanel panel = new JPanel(new GridLayout(13, 13));

		JPanel desk = new JPanel();

		desk.setLayout(new BoxLayout(desk, BoxLayout.Y_AXIS));

		JPanel board = new JPanel(new GridLayout(13, 4));

		int col = 12, row = 12;

		while (buttonMap.size() != 169) {

			JButton aux = new JButton();

			aux.setFont(new Font("Times New Roman", Font.BOLD, 14));

			if (row >= 0) {

				if (col >= 0) {

					aux.setText(matrix.getElement(col, row).getName());

					if (matrix.getElement(col, row).get_suited().equals("s")) {

						aux.setBackground(rouge);

					} else if (matrix.getElement(col, row).get_suited().equals("o")) {

						aux.setBackground(bleu);

					} else if (matrix.getElement(col, row).get_suited().equals(""))

						aux.setBackground(vert);

					aux.setPreferredSize(new Dimension(70, 40));

					aux.setMargin(new java.awt.Insets(1, 8, 1, 8));

					aux.addActionListener((e) -> {

						updateButton(aux);

						text.setText("");

						String r = updateText();

						String new_string = "";

						if (!r.equals(""))

							new_string = r.substring(0, r.length() - 1);

						text.setText(new_string);

						setPercent();

					});

					buttonMap2.put(matrix.getElement(col, row).getName(), aux);

					buttonMap.put(aux, matrix.getElement(col, row));

					panel.add(aux);

					col--;

				} else {

					row--;

					col = 12;

				}

			}

		}

		row = 12;

		col = 3;

		while (row >= 0) {

			while (col >= 0) {

				JButton aux = new JButton();

				aux.setFont(new Font("Times New Roman", Font.BOLD, 14));

				if (col == 0) {

					aux.setText(ranks[row] + "s");

					aux.setBackground(gris);

					buttonMap3.put(ranks[row] + "s", aux);

				} else if (col == 1) {

					aux.setText(ranks[row] + "d");

					aux.setBackground(bleu);

					buttonMap3.put(ranks[row] + "d", aux);

				} else if (col == 2) {

					aux.setText(ranks[row] + "c");

					aux.setBackground(vert);

					buttonMap3.put(ranks[row] + "c", aux);

				} else {

					aux.setText(ranks[row] + "h");

					aux.setBackground(rouge);

					buttonMap3.put(ranks[row] + "h", aux);

				}

				aux.addActionListener((e) -> {

					if (aux.getBackground() == pink) {

						if (aux.getText().charAt(1) == 'h') {

							aux.setBackground(rouge);

						} else if (aux.getText().charAt(1) == 'c') {

							aux.setBackground(vert);

						} else if (aux.getText().charAt(1) == 'd') {

							aux.setBackground(bleu);

						} else {

							aux.setBackground(gris);

						}

						for (JTextField t : _onBoard) {

							if (t.getText().equals(aux.getText())) {

								t.setText("");

							}

						}

						_board.remove(new Card("" + aux.getText().charAt(0), "" + aux.getText().charAt(1),

								"" + aux.getText().charAt(0) + aux.getText().charAt(1)));

						numOnBoard--;

					} else {
						if (numOnBoard < 5) {
							aux.setBackground(pink);
							boolean notFound = false;
							int i = 0;
							while (!notFound) {
								if (_onBoard.get(i).getText().equals("")) {
									_onBoard.get(i).setText(aux.getText());
									notFound = true;
									_board.add(new Card("" + aux.getText().charAt(0), "" + aux.getText().charAt(1),

											"" + aux.getText().charAt(0) + aux.getText().charAt(1)));
								}
								i++;
							}
							numOnBoard++;
						}
					}
				});

				board.add(aux);

				col--;

			}

			row--;

			col = 3;

		}

		JPanel textFieldsPanel = new JPanel(new GridLayout(1, 5));

		for (int i = 0; i < 5; i++) {

			JTextField textField = new JTextField(2);

			textField.setEnabled(true);

			textField.addActionListener((e) -> {
				updateOnBoard(textField);
			});

			textFieldsPanel.add(textField);

			_onBoard.add(textField);

		}

		desk.add(textFieldsPanel);

		desk.add(board);

		panelito.add(panel);

		panelito.add(desk);

		JPanel eastPanel = new JPanel();

		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

		JPanel preSelectedButtonsPanel = new JPanel();

		preSelectedButtonsPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx = 1.0;

		JButton allButton = new JButton("All");

		preSelectedButtonsPanel.add(allButton, c);

		JButton anySuitedButton = new JButton("Any suited");

		preSelectedButtonsPanel.add(anySuitedButton, c);

		JButton anyBroadwayButton = new JButton("Any broadway");

		preSelectedButtonsPanel.add(anyBroadwayButton, c);

		JButton anyPair = new JButton("Any pair");

		preSelectedButtonsPanel.add(anyPair, c);

		JButton clear = new JButton("Clear");

		preSelectedButtonsPanel.add(clear, c);

		c.weighty = 1.0;

		c.gridx = 0;

		c.gridy = 5;

		c.gridwidth = 5;

		text.addActionListener((e) -> {
			
			
			for(List<Pair> l : queue.values()) {
				l.clear();
			}

			if (text.getText().equals("")) {
				range.clear();
				for (JButton b : buttonMap.keySet()) {
					if (buttonMap.get(b).get_suited().equals("s")) {
						b.setBackground(rouge);
					} else if (buttonMap.get(b).get_suited().equals("o")) {
						b.setBackground(bleu);
					} else {
						b.setBackground(vert);
					}
				}
				
				
			} else {
				range.clear();
				String aux[];
				String name = "";
				String a = "";
				boolean finish = false;
				//aux = text.getText().split(",");
				aux = text.getText().split("\\s*,\\s*");
				for (int i = 0; i < aux.length; i++) {
					finish = false;
					int p = 0, q = 0;
					for (int k = 0; k < aux[i].length(); k++) {
						if (aux[i].charAt(k) != '+' && aux[i].charAt(k) != '-')
							name += aux[i].charAt(k);
					}
					if (aux[i].contains("+")) {
						String r = "";
						if (aux[i].length() > 0) {
						    r = aux[i].substring(0, aux[i].length() - 1);
						}
						if(checkPair(r)) {
						while (p < 13 && !finish) {
							while (q < 13 && !finish) {
								if (matrix.getElement(p, q).getName().equals(new String(name))) {
									a = matrix.getElement(p, q).getName();
									finish = true;
								} else {
									q++;
								}
							}
							if (!finish) {
								p++;
								q = 0;
							}
						}
						finish = false;
							if (aux[i].contains("s")) {
								while (p != q) {
									queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
											.add(new Pair(matrix.getElement(p, q).get_firstCard(),
													matrix.getElement(p, q).get_secondCard(),
													matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
									p++;
								}
							} else if (aux[i].contains("o")) {
								while (p != q) {
									queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
											.add(new Pair(matrix.getElement(p, q).get_firstCard(),
													matrix.getElement(p, q).get_secondCard(),
													matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
									q++;
								}
							} else {
								while (p < 13 && q < 13) {
									queue.get("pair")
											.add(new Pair(matrix.getElement(p, q).get_firstCard(),
													matrix.getElement(p, q).get_secondCard(),
													matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
									p++;
									q++;
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, message, "No existe el rango " + aux[i],
									JOptionPane.WARNING_MESSAGE);
						}
						

					} else if (aux[i].contains("-")) {
						String[] r = aux[i].split("-");
						
						if(checkPair(r[1]) && checkPair(r[0]) && r[0].charAt(0) == r[1].charAt(0)) {
							if(r.length == 2) {
								while (p < 13 && !finish) {
									while (q < 13 && !finish) {
										if (matrix.getElement(p, q).getName().equals(new String(r[0]))) {
											finish = true;
										} else {
											q++;
										}
									}
									if (!finish) {
										p++;
										q = 0;
									}
								}
								if (r[0].contains("s")) {
									Card c1 = new Card(r[0].charAt(1) + "", "", "");
									Card c2 = new Card(r[1].charAt(1) + "", "", "");

									if (c1.get_num() > c2.get_num()) {
										while (!matrix.getElement(p, q).getName().equals(r[1])) {
											queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
													.add(new Pair(matrix.getElement(p, q).get_firstCard(),
															matrix.getElement(p, q).get_secondCard(),
															matrix.getElement(p, q).get_suited()));
											range.add(matrix.getElement(p, q).getName());
											buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
											p--;
										}
									} else {
										while (!matrix.getElement(p, q).getName().equals(r[1])) {
											queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
													.add(new Pair(matrix.getElement(p, q).get_firstCard(),
															matrix.getElement(p, q).get_secondCard(),
															matrix.getElement(p, q).get_suited()));
											range.add(matrix.getElement(p, q).getName());
											buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
											p++;
										}
									}

									queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
											.add(new Pair(matrix.getElement(p, q).get_firstCard(),
													matrix.getElement(p, q).get_secondCard(),
													matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
								} else if (r[0].contains("o")) {
									Card c1 = new Card(r[0].charAt(1) + "", "", "");
									Card c2 = new Card(r[1].charAt(1) + "", "", "");

									if (c1.get_num() > c2.get_num()) {
										while (!matrix.getElement(p, q).getName().equals(r[1])) {
											queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
													.add(new Pair(matrix.getElement(p, q).get_firstCard(),
															matrix.getElement(p, q).get_secondCard(),
															matrix.getElement(p, q).get_suited()));
											range.add(matrix.getElement(p, q).getName());
											buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
											q--;
										}
									} else {
										while (!matrix.getElement(p, q).getName().equals(r[1])) {
											queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
													.add(new Pair(matrix.getElement(p, q).get_firstCard(),
															matrix.getElement(p, q).get_secondCard(),
															matrix.getElement(p, q).get_suited()));
											range.add(matrix.getElement(p, q).getName());
											buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
											q++;
										}
									}
									queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
											.add(new Pair(matrix.getElement(p, q).get_firstCard(),
													matrix.getElement(p, q).get_secondCard(),
													matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
								} else {
									Card c1 = new Card(r[0].charAt(1) + "", "", "");
									Card c2 = new Card(r[1].charAt(1) + "", "", "");

									if (c1.get_num() > c2.get_num()) {
										while (!matrix.getElement(p, q).getName().equals(r[1])) {
											queue.get("pair")
													.add(new Pair(matrix.getElement(p, q).get_firstCard(),
															matrix.getElement(p, q).get_secondCard(),
															matrix.getElement(p, q).get_suited()));
											range.add(matrix.getElement(p, q).getName());
											buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
											p--;
											q--;
										}
									} else {
										while (!matrix.getElement(p, q).getName().equals(r[1])) {
											queue.get("pair")
													.add(new Pair(matrix.getElement(p, q).get_firstCard(),
															matrix.getElement(p, q).get_secondCard(),
															matrix.getElement(p, q).get_suited()));
											range.add(matrix.getElement(p, q).getName());
											buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
											p++;
											q++;
										}
									}
									queue.get("pair").add(new Pair(matrix.getElement(p, q).get_firstCard(),
											matrix.getElement(p, q).get_secondCard(), matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(jaune);
								}
								
							}
							else {
								///////////when using - to complete
								while (p < 13 && !finish) {
									while (q < 13 && !finish) {
										if (matrix.getElement(p, q).getName().equals(new String(r[0]))) {
											finish = true;
										} else {
											q++;
										}
									}
									if (!finish) {
										p++;
										q = 0;
									}
								}
								if (r[0].contains("s")) {
									while (p != 0) {
										queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
												.add(new Pair(matrix.getElement(p, q).get_firstCard(),
														matrix.getElement(p, q).get_secondCard(),
														matrix.getElement(p, q).get_suited()));
										range.add(matrix.getElement(p, q).getName());
										buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(Color.yellow);
										p--;
									}
									queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
											.add(new Pair(matrix.getElement(p, q).get_firstCard(),
													matrix.getElement(p, q).get_secondCard(),
													matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(Color.yellow);
								} else if (r[0].contains("o")) {
									while (q != 0) {
										queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
												.add(new Pair(matrix.getElement(p, q).get_firstCard(),
														matrix.getElement(p, q).get_secondCard(),
														matrix.getElement(p, q).get_suited()));
										range.add(matrix.getElement(p, q).getName());
										buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(Color.yellow);
										q--;
									}
									queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
											.add(new Pair(matrix.getElement(p, q).get_firstCard(),
													matrix.getElement(p, q).get_secondCard(),
													matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(Color.yellow);
								} else {
									while (p != 0) {
										queue.get("pair")
												.add(new Pair(matrix.getElement(p, q).get_firstCard(),
														matrix.getElement(p, q).get_secondCard(),
														matrix.getElement(p, q).get_suited()));
										range.add(matrix.getElement(p, q).getName());
										buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(Color.yellow);
										p--;
										q--;
									}
									queue.get("pair").add(new Pair(matrix.getElement(p, q).get_firstCard(),
											matrix.getElement(p, q).get_secondCard(), matrix.getElement(p, q).get_suited()));
									range.add(matrix.getElement(p, q).getName());
									buttonMap2.get(matrix.getElement(p, q).getName()).setBackground(Color.yellow);
								}
							}
						}
						else {
							JOptionPane.showMessageDialog(null, message, "No existe el rango " + aux[i],
									JOptionPane.WARNING_MESSAGE);
						}
						
						
					} else {
						
						if(checkPair(aux[i])) {
							buttonMap2.get(aux[i]).setBackground(jaune);

							if (aux[i].length() == 2) {
								queue.get("pair").add(new Pair(aux[i].charAt(0) + "", aux[i].charAt(1) + "", ""));
								range.add(aux[i]);
							} else {
								queue.get("" + aux[i].charAt(0) + aux[i].charAt(2))
										.add(new Pair(aux[i].charAt(0) + "", aux[i].charAt(1) + "", aux[i].charAt(2) + ""));
								range.add(aux[i]);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, message, "No existe el par " + aux[i],
									JOptionPane.WARNING_MESSAGE);
						}
						
						

					}
					name = "";
				}
				setPercent();
			}
		});

		preSelectedButtonsPanel.add(text, c);

		eastPanel.add(preSelectedButtonsPanel);

//		JPanel publi = new JPanel();
//		publi.setLayout(new FlowLayout());
//
//		ImageIcon imagen1 = escalarImagen("jiji.jpg", 300, 300);
//
//		JLabel label1 = new JLabel(imagen1);
//
//		publi.add(label1);
//
//		eastPanel.add(publi);

		allButton.addActionListener((e) -> {

			for (List<Pair> l : queue.values()) {

				l.clear();

			}

			for (JButton b : buttonMap.keySet()) {

				b.setBackground(jaune);

				b.setForeground(Color.black);

				if (buttonMap.get(b).get_suited().equals("s")) {

					queue.get(b.getText().charAt(0) + "s")

							.add(new Pair("" + b.getText().charAt(0), "" + b.getText().charAt(1), "s"));

				} else if (buttonMap.get(b).get_suited().equals("o")) {

					queue.get(b.getText().charAt(0) + "o")

							.add(new Pair("" + b.getText().charAt(0), "" + b.getText().charAt(1), "o"));

				} else {

					queue.get("pair").add(new Pair("" + b.getText().charAt(0), "" + b.getText().charAt(1), ""));

				}

				range.add(b.getText());

				b.setEnabled(false);

			}

			setPercent();

			text.setText("All");

			text.setEnabled(false);

		});

		anyPair.addActionListener((e) -> {

			for (JButton b : buttonMap.keySet()) {

				if (buttonMap.get(b).get_suited().equals("")) {

					b.setBackground(new Color(203, 153, 255));

					queue.get("pair").add(new Pair(b.getText().charAt(0) + "", b.getText().charAt(1) + "", ""));

				}

				b.setEnabled(false);

			}

			setPercent();

			text.setText("Any pair");

			text.setEnabled(false);

		});

		/// CLEAR

		clear.addActionListener((e) -> {

			for (JButton b : buttonMap.keySet()) {

				if (buttonMap.get(b).get_suited().equals("s")) {

					b.setBackground(rouge);

				} else if (buttonMap.get(b).get_suited().equals("o")) {

					b.setBackground(bleu);

				} else {

					b.setBackground(vert);

				}

				b.setEnabled(true);

			}

			for (List<Pair> l : queue.values()) {

				l.clear();

			}
			range.clear();

			setPercent();

			text.setText(updateText());

			text.setEnabled(true);

		});

		anyBroadwayButton.addActionListener((e) -> {

			for (JButton b : buttonMap.keySet()) {

				Pair aux = new Pair("" + b.getText().charAt(0), "" + b.getText().charAt(1), "");

				if (aux.get_num2() >= 10) {

					b.setBackground(new Color(204, 153, 255));

					if (buttonMap.get(b).get_suited().equals("s")) {

						queue.get(b.getText().charAt(0) + "s")

								.add(new Pair("" + b.getText().charAt(0), "" + b.getText().charAt(1), "s"));

					} else if (buttonMap.get(b).get_suited().equals("o")) {

						queue.get(b.getText().charAt(0) + "o")

								.add(new Pair("" + b.getText().charAt(0), "" + b.getText().charAt(1), "o"));

					} else {

						queue.get("pair").add(new Pair("" + b.getText().charAt(0), "" + b.getText().charAt(1), ""));

					}

				}

				b.setEnabled(false);

			}

			setPercent();

			text.setText("Any broadway");

			text.setEnabled(false);

		});

		anySuitedButton.addActionListener((e) -> {

			for (JButton b : buttonMap.keySet()) {

				if (b.getText().contains("s")) {

					b.setBackground(new Color(204, 153, 255));

					queue.get(b.getText().charAt(0) + "s")

							.add(new Pair("" + b.getText().charAt(0), "" + b.getText().charAt(1), "s"));

					range.add(b.getText());

				}

				b.setEnabled(false);

			}

			setPercent();

			text.setText("Any suited");

			text.setEnabled(false);

		});

		JPanel buttonsPanel = new JPanel();

		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

		JButton okButton = new JButton();
		okButton.setText("Accept");
		numberOfPairs = 0;
		solPanel = new JPanel(new GridLayout(staticsMap.size(), 2));
		eastPanel.add(solPanel);
		okButton.addActionListener((e) -> {
			eastPanel.remove(solPanel);

			if (range.isEmpty())
				JOptionPane.showMessageDialog(null, message, "Introduce un rango", JOptionPane.WARNING_MESSAGE);
			else {
				if (numOnBoard >= 3) {
					staticsMap = new LinkedHashMap<>();
					solPanel = new JPanel(new GridLayout(staticsMap.size(), 2));
					staticsMap = _controller.checkAll(range, _board);
					generateSol();
					eastPanel.add(solPanel);
					solPanel.revalidate();
					// revalidate();
					solPanel.repaint();
				} else
					JOptionPane.showMessageDialog(null, message, "Debe haber al menos 3 cartas on board",
							JOptionPane.WARNING_MESSAGE);
			}
		});

		JButton cancelButton = new JButton();

		cancelButton.setText("Cancel");

		Box horizontalBox = Box.createHorizontalBox();

		progressBar = new JSlider();

		progressBar.setPreferredSize(new Dimension(200, 40));

		horizontalBox.add(progressBar, c);

		percentText = new JTextField(5);

		percentText.setMaximumSize(new Dimension(80, 20));

		horizontalBox.add(percentText);

		progressBar.addChangeListener((e) -> {

			percentText.setText(progressBar.getValue() + "%");

			updateButtonsByPercent();

		});

		horizontalBox.add(Box.createHorizontalGlue());

		horizontalBox.add(okButton);

		horizontalBox.add(cancelButton);

		mainPanel.add(eastPanel, BorderLayout.EAST);

		mainPanel.add(panelito, BorderLayout.CENTER);

		int padding = 10;

		// buttonsPanel.setBorder(new EmptyBorder(padding, 0, 0, 0));

		buttonsPanel.add(horizontalBox);

		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		add(mainPanel);

		pack();

		setVisible(true);

	}

	public void initMatrix() {
		String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
		for (int row = matrix.getRows() - 1; row >= 0; row--) {
			for (int col = matrix.getColumns() - 1; col >= 0; col--) {
				if (row < col) {
					matrix.setElement(row, col, ranks[col] + ranks[row] + "s", 0, ranks[col], ranks[row], "s");
				} else if (row > col) {
					matrix.setElement(row, col, ranks[row] + ranks[col] + "o", 0, ranks[row], ranks[col], "o");
				} else {
					matrix.setElement(row, col, ranks[col] + ranks[row], 0, ranks[col], ranks[row], "");
				}
			}
		}
	}

	public String updateText() {
		String resul = "";
		for (List<Pair> l : queue.values()) {

			if (!l.isEmpty()) {
				int cont = 1;
				Pair initPair = l.get(0);
				Pair antPair = l.get(0);
				Pair lastPair = l.get(l.size() - 1);
				String individulPairs = initPair.get_name() + ",";
				String groupPairs = "";

				for (int i = 1; i < l.size(); i++) {
					if (antPair.get_num2() + 1 == l.get(i).get_num2()) {
						cont++;

						if (cont > 1 && l.get(i).get_num2() + 1 == l.get(i).get_num1()) {
							groupPairs = initPair.get_name() + "+,";
							individulPairs = "";
						} else if (cont > 2) {
							if (lastPair.get_name().equals("AA") && initPair.get_num1() + cont == 15) groupPairs = initPair.get_name() + "+" + ",";
							else if (initPair.get_name().charAt(1) == '2') groupPairs = l.get(i).get_name() + "-" + ",";
							else groupPairs = l.get(i).get_name() + "-" + initPair.get_name() + ",";
							individulPairs = "";

						} else {
							individulPairs += l.get(i).get_name() + ",";
						}
					} else {
						cont = 1;
						resul += groupPairs;
						resul += individulPairs;
						groupPairs = "";
						initPair = l.get(i);
						individulPairs = l.get(i).get_name() + ",";
					}
					antPair = l.get(i);
				}
				resul += individulPairs;
				resul += groupPairs;
			}

		}

		return resul;
	}

	private void updateButton(JButton aux) {
		Pair p = null;

		for (List<Pair> a : queue.values()) {
			if (!a.isEmpty()) {
				for (Pair b : a) {
					if (b.get_name().equals(aux.getText())) {
						p = b;
					}
				}
			}
		}

		if (aux.getBackground() == jaune) {

			if (buttonMap.get(aux).get_suited().equals("s")) {
				aux.setBackground(rouge);
				queue.get("" + aux.getText().charAt(0) + "s").remove(p);
			} else if (buttonMap.get(aux).get_suited().equals("o")) {
				aux.setBackground(bleu);
				queue.get("" + aux.getText().charAt(0) + "o").remove(p);
			} else if (buttonMap.get(aux).get_suited().equals("")) {
				aux.setBackground(vert);
				queue.get("pair").remove(p);
			}
			range.remove(aux.getText());

		} else {
			aux.setBackground(jaune);
			if (buttonMap.get(aux).get_suited().equals("s")) {
				queue.get("" + aux.getText().charAt(0) + "s")
						.add(new Pair("" + aux.getText().charAt(0), "" + aux.getText().charAt(1), "s"));
				sortList(queue.get("" + aux.getText().charAt(0) + "s"));
			} else if (buttonMap.get(aux).get_suited().equals("o")) {
				queue.get("" + aux.getText().charAt(0) + "o")
						.add(new Pair("" + aux.getText().charAt(0), "" + aux.getText().charAt(1), "o"));
				sortList(queue.get("" + aux.getText().charAt(0) + "o"));
			} else if (buttonMap.get(aux).get_suited().equals("")) {
				queue.get("pair").add(new Pair("" + aux.getText().charAt(0), "" + aux.getText().charAt(1), ""));
				sortList(queue.get("pair"));
			}
			range.add(aux.getText());

		}
	}

	private void sortList(List<Pair> list) {
		list.sort(new Comparator<Pair>() {
			@Override
			public int compare(Pair o1, Pair o2) {
				int index1 = -1;
				int index2 = -1;
				for (int i = 0; i < ranks.length; i++) {
					if (ranks[i].equals("" + o1.get_secondCard())) {
						index1 = i;
					}
					if (ranks[i].equals("" + o2.get_secondCard())) {
						index2 = i;
					}
				}
				return Integer.compare(index1, index2);
			}
		});
	}

	private void setPercent() {
		numberOfPairsS = 0;
		numberOfPairsO = 0;
		numberOfPairs = 0;

		for (List<Pair> l : queue.values()) {
			if (!l.isEmpty()) {
				if (l.get(0).get_suited().equals("s"))
					numberOfPairsS += l.size();
				else if (l.get(0).get_suited().equals("o"))
					numberOfPairsO += l.size();
				else if (l.get(0).get_suited().equals(""))
					numberOfPairs += l.size();
			}
		}

		double p = (numberOfPairsS * 4 + numberOfPairsO * 12 + numberOfPairs * 6) / 1326;
		String formattedValue = String.format("%.2f", p * 100);

		percentText.setText(formattedValue + "%");
	}

	private void initRanking() {

		ranking.add(new Pair("A", "A", ""));

		ranking.add(new Pair("K", "K", ""));

		ranking.add(new Pair("A", "K", "s"));

		ranking.add(new Pair("Q", "Q", ""));

		ranking.add(new Pair("A", "K", "o"));

		ranking.add(new Pair("J", "J", ""));

		ranking.add(new Pair("A", "Q", "s"));

		ranking.add(new Pair("T", "T", ""));

		ranking.add(new Pair("A", "Q", "o"));

		ranking.add(new Pair("9", "9", ""));

		ranking.add(new Pair("A", "J", "s"));

		ranking.add(new Pair("8", "8", ""));

		ranking.add(new Pair("A", "T", "s"));

		ranking.add(new Pair("A", "J", "o"));

		ranking.add(new Pair("7", "7", ""));

		ranking.add(new Pair("6", "6", ""));

		ranking.add(new Pair("A", "T", "o"));

		ranking.add(new Pair("A", "9", "s"));

		ranking.add(new Pair("5", "5", ""));

		ranking.add(new Pair("A", "8", "s"));

		ranking.add(new Pair("K", "Q", "s"));

		ranking.add(new Pair("4", "4", ""));

		ranking.add(new Pair("A", "9", "o"));

		ranking.add(new Pair("A", "7", "s"));

		ranking.add(new Pair("K", "J", "s"));

		ranking.add(new Pair("A", "5", "s"));

		ranking.add(new Pair("A", "8", "o"));

		ranking.add(new Pair("A", "6", "s"));

		ranking.add(new Pair("A", "4", "s"));

		ranking.add(new Pair("3", "3", ""));

		ranking.add(new Pair("K", "T", "s"));

		ranking.add(new Pair("A", "7", "o"));

		ranking.add(new Pair("A", "3", "s"));

		ranking.add(new Pair("K", "Q", "o"));

		ranking.add(new Pair("A", "2", "s"));

		ranking.add(new Pair("A", "5", "o"));

		ranking.add(new Pair("A", "6", "o"));

		ranking.add(new Pair("A", "4", "o"));

		ranking.add(new Pair("K", "J", "o"));

		ranking.add(new Pair("Q", "J", "s"));

		ranking.add(new Pair("A", "3", "o"));

		ranking.add(new Pair("2", "2", ""));

		ranking.add(new Pair("K", "9", "s"));

		ranking.add(new Pair("A", "2", "o"));

		ranking.add(new Pair("K", "T", "o"));

		ranking.add(new Pair("Q", "T", "s"));

		ranking.add(new Pair("K", "8", "s"));

		ranking.add(new Pair("K", "7", "s"));

		ranking.add(new Pair("J", "T", "s"));

		ranking.add(new Pair("K", "9", "o"));

		ranking.add(new Pair("K", "6", "s"));

		ranking.add(new Pair("Q", "J", "o"));

		ranking.add(new Pair("Q", "9", "s"));

		ranking.add(new Pair("K", "5", "s"));

		ranking.add(new Pair("K", "8", "o"));

		ranking.add(new Pair("K", "4", "s"));

		ranking.add(new Pair("Q", "T", "o"));

		ranking.add(new Pair("K", "7", "o"));

		ranking.add(new Pair("K", "3", "s"));

		ranking.add(new Pair("K", "2", "s"));

		ranking.add(new Pair("Q", "8", "s"));

		ranking.add(new Pair("K", "6", "o"));

		ranking.add(new Pair("J", "9", "s"));

		ranking.add(new Pair("K", "5", "o"));

		ranking.add(new Pair("Q", "9", "o"));

		ranking.add(new Pair("J", "T", "o"));

		ranking.add(new Pair("K", "4", "o"));

		ranking.add(new Pair("Q", "7", "s"));

		ranking.add(new Pair("T", "9", "s"));

		ranking.add(new Pair("Q", "6", "s"));

		ranking.add(new Pair("K", "3", "o"));

		ranking.add(new Pair("J", "8", "s"));

		ranking.add(new Pair("Q", "5", "s"));

		ranking.add(new Pair("K", "2", "o"));

		ranking.add(new Pair("Q", "8", "o"));

		ranking.add(new Pair("Q", "4", "s"));

		ranking.add(new Pair("Q", "3", "s"));

		ranking.add(new Pair("J", "9", "o"));

		ranking.add(new Pair("T", "8", "s"));

		ranking.add(new Pair("J", "7", "s"));

		ranking.add(new Pair("Q", "7", "o"));

		ranking.add(new Pair("Q", "2", "s"));

		ranking.add(new Pair("Q", "6", "o"));

		ranking.add(new Pair("9", "8", "s"));

		ranking.add(new Pair("Q", "5", "o"));

		ranking.add(new Pair("T", "9", "o"));

		ranking.add(new Pair("J", "8", "o"));

		ranking.add(new Pair("J", "6", "s"));

		ranking.add(new Pair("J", "5", "s"));

		ranking.add(new Pair("T", "7", "s"));

		ranking.add(new Pair("Q", "4", "o"));

		ranking.add(new Pair("J", "4", "s"));

		ranking.add(new Pair("J", "7", "o"));

		ranking.add(new Pair("Q", "3", "o"));

		ranking.add(new Pair("9", "7", "s"));

		ranking.add(new Pair("J", "3", "s"));

		ranking.add(new Pair("T", "8", "o"));

		ranking.add(new Pair("T", "6", "s"));

		ranking.add(new Pair("Q", "2", "o"));

		ranking.add(new Pair("8", "7", "s"));

		ranking.add(new Pair("J", "2", "s"));

		ranking.add(new Pair("J", "6", "o"));

		ranking.add(new Pair("9", "8", "o"));

		ranking.add(new Pair("T", "7", "o"));

		ranking.add(new Pair("9", "6", "s"));

		ranking.add(new Pair("J", "5", "o"));

		ranking.add(new Pair("T", "5", "s"));

		ranking.add(new Pair("T", "4", "s"));

		ranking.add(new Pair("8", "6", "s"));

		ranking.add(new Pair("J", "4", "o"));

		ranking.add(new Pair("T", "3", "s"));

		ranking.add(new Pair("T", "6", "o"));

		ranking.add(new Pair("9", "7", "o"));

		ranking.add(new Pair("9", "5", "s"));

		ranking.add(new Pair("7", "6", "s"));

		ranking.add(new Pair("J", "3", "o"));

		ranking.add(new Pair("T", "2", "s"));

		ranking.add(new Pair("8", "7", "o"));

		ranking.add(new Pair("8", "5", "s"));

		ranking.add(new Pair("9", "6", "o"));

		ranking.add(new Pair("J", "2", "o"));

		ranking.add(new Pair("T", "5", "o"));

		ranking.add(new Pair("9", "4", "s"));

		ranking.add(new Pair("7", "5", "s"));

		ranking.add(new Pair("6", "5", "s"));

		ranking.add(new Pair("T", "4", "o"));

		ranking.add(new Pair("9", "3", "s"));

		ranking.add(new Pair("8", "6", "o"));

		ranking.add(new Pair("8", "4", "s"));

		ranking.add(new Pair("9", "5", "o"));

		ranking.add(new Pair("7", "6", "o"));

		ranking.add(new Pair("T", "3", "o"));

		ranking.add(new Pair("9", "2", "s"));

		ranking.add(new Pair("7", "4", "s"));

		ranking.add(new Pair("5", "4", "s"));

		ranking.add(new Pair("T", "2", "o"));

		ranking.add(new Pair("8", "5", "o"));

		ranking.add(new Pair("6", "4", "s"));

		ranking.add(new Pair("8", "3", "s"));

		ranking.add(new Pair("7", "5", "o"));

		ranking.add(new Pair("9", "4", "o"));

		ranking.add(new Pair("7", "3", "s"));

		ranking.add(new Pair("8", "2", "s"));

		ranking.add(new Pair("9", "3", "o"));

		ranking.add(new Pair("5", "3", "s"));

		ranking.add(new Pair("6", "5", "o"));

		ranking.add(new Pair("6", "3", "s"));

		ranking.add(new Pair("8", "4", "o"));

		ranking.add(new Pair("9", "2", "o"));

		ranking.add(new Pair("4", "3", "s"));

		ranking.add(new Pair("7", "2", "s"));

		ranking.add(new Pair("7", "4", "o"));

		ranking.add(new Pair("5", "4", "o"));

		ranking.add(new Pair("6", "2", "s"));

		ranking.add(new Pair("5", "2", "s"));

		ranking.add(new Pair("6", "4", "o"));

		ranking.add(new Pair("8", "3", "o"));

		ranking.add(new Pair("4", "2", "s"));

		ranking.add(new Pair("8", "2", "o"));

		ranking.add(new Pair("7", "3", "o"));

		ranking.add(new Pair("6", "3", "o"));

		ranking.add(new Pair("5", "3", "o"));

		ranking.add(new Pair("3", "2", "s"));

		ranking.add(new Pair("4", "3", "o"));

		ranking.add(new Pair("7", "2", "o"));

		ranking.add(new Pair("6", "2", "o"));

		ranking.add(new Pair("5", "2", "o"));

		ranking.add(new Pair("4", "2", "o"));

		ranking.add(new Pair("3", "2", "o"));

	}

	private void updateButtonsByPercent() {

		int n = (progressBar.getValue() * 169) / 100;

		for (List<Pair> l : queue.values()) {

			l.clear();

		}

		for (JButton b : buttonMap.keySet()) {

			if (buttonMap.get(b).get_suited().equals("s")) {

				b.setBackground(rouge);

			} else if (buttonMap.get(b).get_suited().equals("o")) {

				b.setBackground(bleu);

			} else if (buttonMap.get(b).get_suited().equals("")) {

				b.setBackground(vert);

			}

			range.remove(b.getText());

		}

		for (int i = 0; i < n; i++) {

			buttonMap2.get(ranking.get(i).get_name()).setBackground(new Color(204, 153, 255));

			if (ranking.get(i).get_suited().equals("")) {

				queue.get("pair").add(ranking.get(i));

				sortList(queue.get("pair"));

			} else {

				queue.get(ranking.get(i).get_firstCard() + ranking.get(i).get_suited()).add(ranking.get(i));

				sortList(queue.get(ranking.get(i).get_firstCard() + ranking.get(i).get_suited()));

			}

			range.add(ranking.get(i).get_name());

		}

	}

	private void generateSol() {

		GridBagLayout layout = new GridBagLayout();
		solPanel.setLayout(layout);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;

		int cont = 1;
		String ant = "";

		int gridY = 0;
		for (String s : staticsMap.keySet()) {
			if (!s.equals("total")) {
				JLabel games = new JLabel(s + ": ");
				games.setPreferredSize(new Dimension(100, 20));

				c.gridx = 0;
				c.gridy = gridY;
				c.anchor = GridBagConstraints.WEST;
				layout.setConstraints(games, c);
				solPanel.add(games);

				JLabel pairs = new JLabel();

				if (!staticsMap.get(s).isEmpty()) {
					if (s.equals("Ace high")) {
						pairs.setText(staticsMap.get(s).size() + "");
					}

					else {
						ant = staticsMap.get(s).get(0);
						if (staticsMap.get(s).size() == 1) {
							pairs.setText(ant);
							int x = staticsMap.get(s).size();
							int y = Integer.parseInt(staticsMap.get("total").get(0));
							double tot = (double) x / y;

							pairs.setText(pairs.getText() + "(" + staticsMap.get(s).size() + "/"
									+ (staticsMap.get("total").get(0) + ")" + (int) (tot * 100) + "%"));
							cont = 1;

						} else {
							for (int i = 1; i < staticsMap.get(s).size(); i++) {
								if (ant.equals(staticsMap.get(s).get(i))) {
									cont++;
									if (i == staticsMap.get(s).size() - 1) {
										if (ant.equals(staticsMap.get(s).get(i))) {
											pairs.setText(pairs.getText() + ant + "(" + cont + ")");
										}
									}
								} else {

									if (cont > 1) {
										pairs.setText(pairs.getText() + ant + "(" + cont + "), ");
									} else {
										pairs.setText(pairs.getText() + ant + ", ");
									}
									if (i == staticsMap.get(s).size() - 1) {
										pairs.setText(pairs.getText() + staticsMap.get(s).get(i));
									}
									cont = 1;
									ant = staticsMap.get(s).get(i);
								}

							}
							int x = staticsMap.get(s).size();
							int y = Integer.parseInt(staticsMap.get("total").get(0));
							double tot = (double) x / y;

							pairs.setText(pairs.getText() + "(" + staticsMap.get(s).size() + "/"
									+ (staticsMap.get("total").get(0) + ")" + (int) (tot * 100) + "%"));
							cont = 1;
						}
					}

				} else {
					pairs.setText("0");
				}
				c.gridx = 1;
				c.gridy = gridY;
				c.anchor = GridBagConstraints.WEST;
				layout.setConstraints(pairs, c);
				solPanel.add(pairs);
			}

			gridY++;
		}
	}

	private void updateOnBoard(JTextField textField) {
		boolean found = false;
		int p = 0;
		if (textField.getText().length() == 2) {
			while (p < ranks.length && !found) {
				if (ranks[p].equals(textField.getText().charAt(0) + "")) {
					found = true;
				}
				p++;
			}
			found = false;
			p = 0;
			while (p < suits.length && !found) {
				if (suits[p].equals(textField.getText().charAt(1) + "")) {
					found = true;
				}
				p++;
			}

			if (!found) {
				JOptionPane.showMessageDialog(null, message, "Carta invalida", JOptionPane.WARNING_MESSAGE);
			} else {
				if (!_board.contains(new Card(textField.getText().charAt(0) + "", textField.getText().charAt(1) + "",
						textField.getText()))) {

					_board.add(new Card(textField.getText().charAt(0) + "", textField.getText().charAt(1) + "",
							textField.getText()));
					buttonMap3.get(textField.getText()).setBackground(pink);
					numOnBoard++;
				} else {
					JOptionPane.showMessageDialog(null, message, "Carta ya seleccionada", JOptionPane.WARNING_MESSAGE);
				}
			}

		} else if (textField.getText().equals("")) {

			_board.clear();
			numOnBoard = 0;

			for (JButton b : buttonMap3.values()) {
				if (b.getText().contains("h"))
					b.setBackground(rouge);
				else if (b.getText().contains("c"))
					b.setBackground(vert);
				else if (b.getText().contains("d"))
					b.setBackground(bleu);
				else if (b.getText().contains("s"))
					b.setBackground(gris);
			}

			for (JTextField t : _onBoard) {
				if (!t.getText().equals("")) {

					_board.add(new Card(t.getText().charAt(0) + "", t.getText().charAt(1) + "", t.getText()));
					buttonMap3.get(t.getText()).setBackground(pink);
					numOnBoard++;

				}
			}
		} else {
			JOptionPane.showMessageDialog(null, message, "Carta invalida", JOptionPane.WARNING_MESSAGE);
		}

	}

	private ImageIcon escalarImagen(String ruta, int ancho, int alto) {
		try {
			BufferedImage img = ImageIO.read(new File(ruta));
			Image imagenEscalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
			return new ImageIcon(imagenEscalada);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean checkPair(String pair) {
		boolean ok = false;
	    String[] pokerHandRankings = {"AA", "KK", "AKs", "QQ", "AKo", "JJ", "AQs", "TT", "AQo", "99", "AJs", "88", "ATs", "AJo", "77", "66", "ATo", "A9s", "55", "A8s", "KQs", "44", "A9o", "A7s", "KJs", "A5s", "A8o", "A6s", "A4s", "33", "KTs", "A7o", "A3s", "KQo", "A2s", "A5o", "A6o", "A4o", "KJo", "QJs", "A3o", "22", "K9s", "A2o", "KTo", "QTs", "K8s", "K7s", "K9o", "JTs", "QJo", "K6s", "Q9s", "K5s", "K8o", "K4s", "QTo", "K7o", "K3s", "Q8s", "K2s", "K6o", "J9s", "K5o", "Q9o", "JTo", "K4o", "Q7s", "T9s", "Q6s", "K3o", "Q5s", "K2o", "Q8o", "Q4s", "J9o", "T8s", "J7s", "Q3s", "Q7o", "Q2s", "Q6o", "98s", "Q5o", "T9o", "J8o", "J6s", "J5s", "T7s", "Q4o", "J7o", "J4s", "J3s", "J2s", "T6s", "T8o", "J7o", "J6o", "Q4o", "Q2o", "J5o", "T7o", "98o", "97o", "T5s", "T4s", "T3s", "T2s", "97s", "96s", "87s", "86s", "95s", "76s", "J4o", "J3o", "J2o", "T6o", "T5o", "T4o", "T3o", "T2o", "94s", "93s", "92s", "85s", "84s", "83s", "82s", "76s", "75s", "74s", "73s", "72s", "87o", "96o", "95o", "94o", "93o", "92o", "87o", "86o", "85o", "84o", "83o", "82o", "65s", "64s", "63s", "62s", "76o", "75o", "74o", "73o", "72o", "54s", "53s", "52s", "65o", "64o", "63o", "62o", "43s", "42s", "32s", "54o", "53o", "52o", "43o", "42o", "32o"};
	    int i = 0;
		
		while(!ok && i < pokerHandRankings.length) {
			if(pair.equals(pokerHandRankings[i])) {
				ok = true;
			}
			i++;
		}
		
		
		return ok;
	}

}