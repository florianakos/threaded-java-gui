package lab.two;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.*;
import java.lang.Runtime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuiCommand extends Frame implements ActionListener  {
	// needed for some reason... (?)
	private static final long serialVersionUID = 1L;

	// private variables if any
	private TextField txtCommandInput;  
	private TextArea txtAreaOutput;
	private Button btnExecute;
	private Button btnLooping;
	private String command = "";
	
	// Constructor to se t up the GUI elements
	public GuiCommand() {
		// set the layout for mat for AWT
		setLayout(new FlowLayout());   

		// Add TXT Input and Output objects to the window
		txtCommandInput = new TextField("Type here your command...", 20); 
		txtCommandInput.setEditable(true);  
		txtCommandInput.setLocation(1600, 1600);
		add(txtCommandInput);                     
		
		txtAreaOutput = new TextArea("Output will come here...", 30, 70); 
		txtAreaOutput.setEditable(false);   
		txtAreaOutput.setLocation(1600, 1600);
		add(txtAreaOutput);  
		
		// add button to execute the given command
		btnExecute = new Button("Execute Command");
		add(btnExecute);
		btnExecute.addActionListener(this);
		
		// add button to execute the looping
		btnLooping = new Button("Do some looping");
		add(btnLooping);
		btnLooping.addActionListener(this);

		// set title, size and visibility
		setTitle("AWT Counter");
		setSize(1200, 500);
		setVisible(true);
	}

	// Listener method that is called to execute the appropriate function for the button that was clicked
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnExecute) {
			// call the function for executing the command
			executeCommand();
		} else if (evt.getSource() == btnLooping) {
			// call the function for doing the looping in a new thread
			doSomeLooping();
		}
	}
	
	// Method that will use the other class for doing some looping in parallel using threading
	public void doSomeLooping() {
		// create the pool of threads for quick spawning
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		
		try {
			
			System.out.println("Start looping like crazy in new thread... (-(-_(-_-)_-)-)");
			// create a new instance of the LoopingProg class
			LoopingProg obj_worker = new LoopingProg(10);
			// execute the instance in a new thread using the pool
			threadPool.execute(obj_worker);
			
		} catch(Exception e) {
			
			threadPool.shutdown();
			System.out.println(e.getMessage());
			return;
			
		}
	}
	
	public void executeCommand() {
		command = txtCommandInput.getText(); // Increase the counter value
		if (command.equals("") | command.equals("Type here your command...")) {
			txtAreaOutput.setText("PLEASE GIVE A VALID COMMAND!!!");
			return;
		}
		
		String[] cmdTemplate = {
				"/bin/sh",
				"-c",
				command
				};
		
		String currentText = txtAreaOutput.getText() + "\n\n== New Command was given: [ " + command +" ] ===\n\n";
		
		StringBuilder sb = new StringBuilder();
		sb.append(currentText);
		String newText;
		try {
			Process process = Runtime.getRuntime().exec(cmdTemplate);
			// Process process = Runtime.getRuntime().exec("cmd /c" + command); FOR WINDOWS
	        BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream()));
	         
	        while ((newText = reader.readLine()) != null){
	            //System.out.println("The inout stream is " + s);
	            //String current = txtFieldOutput.getText();
	        	sb.append(newText + "\n");
	        	//sb.append("\n");
	        	txtAreaOutput.setText(sb.toString());
	        	txtAreaOutput.setCaretPosition(txtAreaOutput.getHeight());
	        } 
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		// instantiate the main frame/window with the constructor
		GuiCommand gcWindow = new GuiCommand();
		
		// Implement the closing handler for the main window .... really annoying
		gcWindow.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {gcWindow.dispose();}
	     	}
		);
	}
}