import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The NewsFeed class stores news posts for the news feed in a
 * social network application.
 * 
 * Display of the posts is currently simulated by printing the
 * details to the terminal. (Later, this should display in a browser.)
 * 
 * This version does not save the data to disk, and it does not
 * provide any search or ordering functions.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 0.2
 */
public class NewsFeed
{
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    
    private ArrayList<Post> posts;
    private JFrame frame;
    private JPanel mainFeed;
    
    
    /**
     * Construct an empty news feed.
     */
    public NewsFeed()
    {
        posts = new ArrayList<>();
        makeFeed();
    }

    /**
     * Show the news feed. Currently: print the news feed details
     * to the terminal. (To do: replace this later with display
     * in web browser.)
     */
    public void refresh()
    {
        // display all posts
        for (Component x : mainFeed.getComponents()) {
            if (!(x instanceof JScrollPane)) {
                mainFeed.remove(x);
            }   
        }
        mainFeed.revalidate();
        mainFeed.repaint();
        for(Post post : posts) {
            post.display(mainFeed);
        }
        
    }
    
    private void promptTextPost() {
        JFrame textPromptFrm = new JFrame("Text Post");
        JPanel contentPane = (JPanel)textPromptFrm.getContentPane();
        contentPane.setBorder(new EmptyBorder(12,12,12,12));
        contentPane.setLayout(new BorderLayout(6,6));
        
        JPanel forum = new JPanel();
        forum.setLayout(new GridLayout(0, 1));
        
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setSize(authorLabel.getPreferredSize());
        forum.add(authorLabel);
        
        JTextField authorField = new JTextField(16);
        forum.add(authorField);
        
        JLabel msgLabel = new JLabel("Message");
        forum.add(msgLabel);
        
        JTextArea msgField = new JTextArea();
        forum.add(msgField);
        
        JButton post = new JButton("Post");
        post.addActionListener(e -> createMsgPost(textPromptFrm, authorField.getText(), msgField.getText()));
        forum.add(post);
        
        contentPane.add(forum);
        
        textPromptFrm.setSize(300, 200);
        textPromptFrm.setVisible(true);
    }
    
    
    
    private void createMsgPost(JFrame textPromptFrame, String author, String msg) {
        textPromptFrame.dispose();
        posts.add(new MessagePost(author, msg));
    }
    
    private void openFile(JFrame windowFrame, ImagePanel imgPanel)
    {
        int returnVal = fileChooser.showOpenDialog(frame);

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled
        }
        File selectedFile = fileChooser.getSelectedFile();
        OFImage currentImage = ImageFileManager.loadImage(selectedFile);
        
        if(currentImage == null) {   // image file was not a valid image
            JOptionPane.showMessageDialog(windowFrame,
                    "The file was not in a recognized image file format.",
                    "Image Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        imgPanel.setImage(currentImage);
        windowFrame.pack();
    }
    
    private void promptPhotoPost() {
        JFrame photoPromptFrm = new JFrame("Text Post");
        JPanel contentPane = (JPanel)photoPromptFrm.getContentPane();
        contentPane.setBorder(new EmptyBorder(12,12,12,12));
        contentPane.setLayout(new BorderLayout(6,6));
        
        JPanel forum = new JPanel();
        forum.setLayout(new GridLayout(0,1));
        
        JLabel authorLabel = new JLabel("Author");
        forum.add(authorLabel);
        
        JTextField authorField = new JTextField(16);
        forum.add(authorField);
        
        JLabel msgLabel = new JLabel("Caption");
        forum.add(msgLabel);
        
        JTextField msgField = new JTextField(16);
        forum.add(msgField);
        
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setBorder(new EtchedBorder());
        forum.add(imagePanel);
        
        JButton browse = new JButton("Browse");
        browse.addActionListener(e -> openFile(photoPromptFrm, imagePanel));
        forum.add(browse);
        
        JButton post = new JButton("Post");
        post.addActionListener(e -> createPhotoPost(photoPromptFrm, authorField.getText(), msgLabel.getText(), fileChooser.getSelectedFile().getPath()));
        forum.add(post);
        
        contentPane.add(forum);
        
        photoPromptFrm.setSize(300, 200);
        photoPromptFrm.setVisible(true);
    }
    
    private void createPhotoPost(JFrame photoPromptFrame, String author, String caption, String filePath) {
        photoPromptFrame.dispose();
        try {
            posts.add(new PhotoPost(author, caption, filePath));
        } catch (Exception e) {}
    }
    
    private void makeFeed() {
        frame = new JFrame("News Feed");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(12,12,12,12));
        contentPane.setLayout(new BorderLayout(6,6));
        
        mainFeed = new JPanel();
        mainFeed.setLayout(new BoxLayout(mainFeed, BoxLayout.PAGE_AXIS));
        
        JScrollPane scrPane = new JScrollPane(mainFeed);
        scrPane.setLayout(new ScrollPaneLayout());
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrPane);
        
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));
        
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> refresh());
        toolbar.add(refresh);
        
        JButton textPostBut = new JButton("Text Post");
        textPostBut.addActionListener(e -> promptTextPost());
        toolbar.add(textPostBut);
        
        JButton photoPostBut = new JButton("Photo Post");
        photoPostBut.addActionListener(e -> promptPhotoPost());
        toolbar.add(photoPostBut);
        
        JPanel flow = new JPanel();
        flow.add(toolbar);
        
        contentPane.add(flow, BorderLayout.WEST);
        
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
