// TODO this only displays ONE dataset so far...
//      how do we display all of them?
//      table? tabbed panes + search field?
// TODO remove hard-coding of file locations
// TODO flushing data field instead of overwrite
// TODO dropdown not as a field?

/* varianten zur aufnahme aller datasets
 *
 * A) combobox
 *    + einfachste loesung
 *    - unpraktisch
 * 1. array von datasets (pro session persistent), zugriff auf index des arrays anhand
 *    auswahl in dropdown
 * 2. datasets fortlaufend in textfields (pro session persistent) einlesen, counter i laufen
 *    lassen zur nummerierung in dropdown, (i-1)*rowPerDataset ist startindex zur anzeige in GUI
 *
 * B) table
 * 1. datasets fortlaufend einlesen und in tabelle(n) schreiben
 *    + pro session nichts persistent
 *    - tabellen muessen aufgeteilt werden oder horizontal muss sehr weit gescrollt werden
 *    ? prioritaet der datensaetze koennte anzeige reduzieren
 *
 * C) suchfeld
 *    +- suche gezielt nach bestimmten infos
 *    - aktuell ohne Persistence ist das nur moeglich, wenn ein primary key pro dataset festegelegt
 *      wird, wie z.B. die kunden-id
 *
 * natuerlich koennen A und C auch fuer B als auswahlkomponente benutzt werden
 *
 * in kombination mit obigen ansaetzen:
 * i) card pane (keine aufteilung der infos moeglich)
 * ii)tabbed pane (aufteilung der infos)
 *
 *  */

package sandbox;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class DatasetIO extends JFrame {

	private static final String INPUT_FILE = "C:\\in.txt";
	private static final String OUTPUT_FILE = "C:\\out.txt";
	private static final int[] BLOCKS = { 10, 10, 10, 20, 20, 20, 30, 20, 30,
			100, 100, 50, 100, 100, 60, 80, 20, 80, 30, 30, 30, 30, 30, 30, 80,
			10, 3, 20, 30, 30, 4, 7, 10, 10, 10, 20, 10, 1, 1, 1, 10, 1024, 1,
			1, 1, 1, 1, 50, 10, 1024, 1, 20, 10, 40, 8, 8, 1, 1, 7, 10, 3, 10,
			1, 10, 1, 11, 1, 100, 20, 1, 1, 10, 1, 1, 1, 1, 1, 30, 30, 1, 10,
			1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 10, 10, 1, 10, 1, 1, 1,
			1, 1, 30, 30, 1, 10, 1, 10, 5, 5, 1, 1, 10, 2, 1, 1, 1, 1, 1, 1, 1,
			10, 27, 1, 1, 80, 25, 25, 25, 25 };
	private int rows;
	private int numDatasets = 0;
	private JTextField[] textfields;
	private Vector<String> textfieldInput = new Vector<String>();
	private final JComboBox<Integer> pickDataset = new JComboBox<Integer>();
	private static final int dataSize = 4195;

	public DatasetIO() {
		this.rows = BLOCKS.length;
		this.textfields = new JTextField[rows];
		pickDataset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedDataset = pickDataset.getSelectedIndex();
					displayDataset(selectedDataset);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		init();
	}

	protected void displayDataset(int datasetIndex) throws IOException {
		BufferedReader in = null;
		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		try {
			in = new BufferedReader(new FileReader(INPUT_FILE));
			int startIndex = (datasetIndex - 1) * BLOCKS.length;
			// int endIndex = startIndex + BLOCKS.length;
			// endIndex should be redundant
			// int endIndex = datasetIndex * BLOCKS.length;
			for (int i = 0, j = startIndex; i < BLOCKS.length/* , j < endIndex */; i++, j++) {
				textfields[i].setText(textfieldInput.get(j));
				// textfields[i].setSize(dim);
			}
		} finally {
			if (in != null)
				in.close();
		}

	}

	private void init() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel main = new JPanel(new BorderLayout());

		/* populating the info panel */

		// FIXME final
		final JPanel dataPane = new JPanel(new SpringLayout());

		// should be an array, because we might want to write the labels to file
		// at some point
		JLabel[] labels = new JLabel[rows];
		for (int i = 0; i < rows; i++) {
			// with Spring Form
			labels[i] = new JLabel("(" + (i + 1) + ")");
			dataPane.add(labels[i]);
			textfields[i] = new JTextField();
			labels[i].setLabelFor(textfields[i]);
			dataPane.add(textfields[i]);
		}

		/* lay out the panel with SpringLayout */
		SpringUtilities.makeCompactGrid(dataPane, rows, 2, // rows, cols
				0, 0, // initX, initY
				1, 1); // xPad, yPad

		/* adding the panel to the scroll pane */
		JScrollPane scrollPane = new JScrollPane(dataPane);

		/* creating and setting the buttons */

		JButton readButton = new JButton("Read");
		readButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				read();
				dataPane.repaint();
			}
		});

		JButton writeButton = new JButton("Write");
		writeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				write();
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.add(readButton);
		buttonPane.add(writeButton);
		buttonPane.add(pickDataset);

		main.add(scrollPane, BorderLayout.CENTER);
		main.add(buttonPane, BorderLayout.SOUTH);

		this.getContentPane().add(main);
		this.pack();
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);

	}

	protected void read() {
		try {
			readDataset();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pickDataset.setSelectedIndex(0);
	}

	protected void write() {
		try {
			writeDataset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void readDataset() throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(INPUT_FILE));
			int charIndex = 0;
			String text;
			// int c;
			char[] data = new char[dataSize];
			while (in.read(data) != -1) {
				for (int i = 0; i < BLOCKS.length; i++) {
					text = "";
					for (int j = 0; j < BLOCKS[i]; j++, charIndex++) {
						text += data[charIndex];
					}
					textfieldInput.add(text);
					// System.out.println(textfieldInput.size());
				}
				data = new char[dataSize];
				charIndex = 0;
			}
		} finally {
			if (in != null)
				in.close();
		}
		numDatasets = textfieldInput.size() / BLOCKS.length;
		// numDatasets = data.length / dataSize;
	}

	protected void writeDataset() throws IOException {
		outputFromGUI();
	}

	protected void outputFromGUI() throws IOException {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(OUTPUT_FILE));
			for (int i = 0; i < textfields.length; i++) {
				out.write(textfields[i].getText());
			}
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new DatasetIO();
	}

}
