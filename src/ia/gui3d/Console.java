package ia.gui3d;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Console extends JFrame implements Displayable, MessageOutputter {

        private TextArea textArea;

        public Console() {
        	super("Console");
        	setSize(600, 200);

        	textArea = new TextArea();
        	textArea.setEditable(false);
        	textArea.setBackground(Color.WHITE);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

            setLocation(100, 200);

            add (textArea);
        }

        public void addText( String message ) {
        	textArea.setText(">> " + message + textArea.getText());
            textArea.setCaretPosition(textArea.getRows());
        }

        public void refresh() {
        }

        public void display() {
            setVisible(true);
        }

        public void update(Object o) {
        	textArea.setText("");
        }

		public void print(Object msg) {
			addText(msg.toString());			
		}

		public void printf(String fmt, Object... args) {
			addText(String.format(fmt, args));
		}

		public void println(Object msg) {
			addText(msg + "\n");			
		}
}
