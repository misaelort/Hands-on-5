package examples.SLR;


import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Interfaz extends JFrame {

	public int numero;         //Entero utilizado para cachar valor de interfaz
	private HandsOn5 myAgent;  //Objeto agente

	Interfaz(HandsOn5 a) {
		super("Regresion Lineal");
		myAgent = a;

		add("North",new JLabel("Ingrese un Numero"));
		
		JTextField titleField = new JTextField(15);
		add("Center",titleField);
		
		JButton button = new JButton("Calcular");
		add("South",button);
		button.addActionListener( new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent ev) {
		        numero = Integer.parseInt(titleField.getText());
		        myAgent.CalculaRegresion();
	        }
	        
		}
		);

		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();	
			}
		});
		
		setSize(400,100);

	}



	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}

}
