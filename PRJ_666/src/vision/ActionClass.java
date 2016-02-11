package vision;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ActionClass implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		int action = Integer.parseInt(e.getActionCommand());
		//System.out.println(action);
	}
}