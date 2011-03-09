package treeviewer;

import javax.swing.*;
import java.awt.*;
import java.lang.Exception;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Tree extends JPanel {
    private JComponent root;
    private Tree left, right;
    int midline; // TODO: dynamic midline calculation
 
    public Tree(JComponent root, Tree left, Tree right) {
        this.root = root;
        this.left = left;
        this.right = right;

	setLayout(null);
        Insets insets = getInsets();
        Dimension root_size = root.getPreferredSize();
        midline = insets.left + root_size.width / 2;
        int highline = insets.top + root_size.height + 10;
        int width = insets.left + insets.right + root_size.width; 
        int height = 0;
 
        if (left != null) {
	    Dimension left_size = left.getPreferredSize();
	    midline = Math.max(midline, insets.left + left_size.width + 5);
	    left.setBounds(insets.left, highline, 
			   left_size.width, left_size.height);
	    width = midline + root_size.width/2; 
	    height = Math.max(height,
			      left.getY() + left.getHeight() + insets.bottom);
	    add(left);
        }

        root.setBounds(midline - root_size.width/2, insets.top,
		       root_size.width, root_size.height);
        height = Math.max(height,
			  root.getY() + root.getHeight() + insets.bottom);
        add(root);

        if (right != null) {
            Dimension right_size = right.getPreferredSize();
            right.setBounds(midline + 5, highline,
			    right_size.width, right_size.height);
            width = midline + Math.max(root_size.width/2,
				       right_size.width+5) + insets.right;
            height = Math.max(height,
			      right.getY() + right.getHeight() + insets.bottom);
            add(right);
	}
	setPreferredSize(new Dimension(width, height));
    }
      
    public static Tree parseTree(BufferedReader in, boolean isLegalTree){   
	String line;
	try {
	    line = in.readLine();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return null;
	}
	
	try {
	    if(line.isEmpty()){
		if (!isLegalTree){
		    throw  new  InvalidTreeException("null tree exception from input");
		} else {
		    return null;
		}
	    } else {
		Tree left = parseTree(in, true);
		Tree right = parseTree(in, true);
		return new Tree(new JLabel(line), left, right);
	    }
	} catch (InvalidTreeException e) {
	    JOptionPane.showMessageDialog(null,
					  "Invalid Tree Exception",
					  "Illegal read",
					  JOptionPane.ERROR_MESSAGE);
	    e.printStackTrace();
	}
	return null;
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (left != null) {
	    g.drawLine(left.getX() + left.midline, left.getY(),
		       left.getX() + left.getWidth(),
		       root.getY() + root.getHeight());
	}
	if (right != null) {
	    g.drawLine(right.getX() + right.midline, right.getY(),
		       right.getX(), root.getY() + root.getHeight());
	}
    }
    
    public static void main(String[] args) {
	BufferedReader in =
	    new BufferedReader(new InputStreamReader(System.in));
	Tree tree = parseTree(in, false);
	JFrame frame = new JFrame("Tree View");
	JPanel panel = new JPanel();
	
	panel.add(tree);
	JScrollPane scrollPane = new JScrollPane(panel);
	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	frame.getContentPane().add(scrollPane);
	frame.setSize(tree.getSize());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
    }
}
