import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.spaeth.appbase.adds.swing.component.customized.layout.OrderedLayoutConstraint;
import com.spaeth.appbase.adds.swing.component.customized.layout.VLayoutFlow;
import com.spaeth.appbase.adds.swing.component.customized.layout.VLayoutManager;

public class HorizontalLayoutTest {

	public static void main(final String[] args) {
		final JFrame f = new JFrame();
		f.setSize(202, 202);

		final LayoutManager2 layout = new VLayoutManager(VLayoutFlow.TOP_DOWN, 10);

		f.setLayout(layout);
		JButton b1 = new JButton("First Button 1");
		final JButton b2 = new JButton("First Button 2");
		JButton b3 = new JButton("First Button 3");

		b1.setMinimumSize(new Dimension(0, 0));
		b2.setMinimumSize(new Dimension(0, 0));
		b3.setMinimumSize(new Dimension(0, 0));

		f.add(b1, new OrderedLayoutConstraint("0", "0", 0));
		f.add(b2, new OrderedLayoutConstraint("0", "0", 1));
		f.add(b3, new OrderedLayoutConstraint("0%", "0", 1));

		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				layout.addLayoutComponent(b2, new OrderedLayoutConstraint("20", "20", 0));
			}
		});

		f.setVisible(true);

		layout.addLayoutComponent(b1, new OrderedLayoutConstraint("20", "20", 0));

	}
}
