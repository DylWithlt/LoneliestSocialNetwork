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
 * This class stores information about a news feed post in a 
 * social network. Posts can be stored and displayed. This class
 * serves as a superclass for more specific post types.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 0.2
 */
public class Post 
{
    private String username;  // username of the post's author
    private long timestamp;
    private int likes;
    private ArrayList<String> comments;
    
    private JPanel postContainer;
    protected JPanel postContent;

    /**
     * Constructor for objects of class Post.
     * 
     * @param author    The username of the author of this post.
     */
    public Post(String author)
    {
        username = author;
        timestamp = System.currentTimeMillis();
        likes = 0;
        comments = new ArrayList<>();
    }

    /**
     * Record one more 'Like' indication from a user.
     */
    public void like()
    {
        likes++;
    }

    /**
     * Record that a user has withdrawn his/her 'Like' vote.
     */
    public void unlike()
    {
        if (likes > 0) {
            likes--;
        }
    }

    /**
     * Add a comment to this post.
     * 
     * @param text  The new comment to add.
     */
    public void addComment(String text)
    {
        comments.add(text);
    }

    /**
     * Return the time of creation of this post.
     * 
     * @return The post's creation time, as a system time value.
     */
    public long getTimeStamp()
    {
        return timestamp;
    }
    
    public JPanel getContentPanel() {
        return postContent;
    }

    /**
     * Display the details of this post.
     * 
     * (Currently: Print to the text terminal. This is simulating display 
     * in a web browser for now.)
     */
    public void display(JPanel mainFeed)
    {
        postContainer = new JPanel();
        postContent = new JPanel();
        mainFeed.add(postContainer, BorderLayout.NORTH);
        postContainer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        postContainer.setBorder(new EtchedBorder());
        
        JLabel author = new JLabel("Author: " + username);
        author.setBorder(new EtchedBorder());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        postContainer.add(author, c);
        
        JLabel timeStamp = new JLabel(timeString(timestamp));
        timeStamp.setBorder(new EtchedBorder());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        postContainer.add(timeStamp, c);
        
        postContent.setBorder(new EtchedBorder());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.ipady = 20;
        postContainer.add(postContent, c);
        c.ipady = 0;
        
        /* TODO:
         * Create button that calls like();
         * Create button that calls unlike();
         */
        JLabel likeLabel = new JLabel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        postContainer.add(likeLabel, c);
        if(likes > 0) {
            likeLabel.setText("  -  " + likes + " people like this.");
        }
        else {
            likeLabel.setText("No one has liked this yet.");
        }
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        JPanel cmntContainer = new JPanel();
        cmntContainer.setLayout(new GridLayout(0, 2));
        postContainer.add(cmntContainer, c);
        
        //TODO: MAKE COMMENT SYSTEM ACTUALLY WORK
        //TO FUTURE DYLAN: WE WANT TO DISPLAY BUTTONS SIDE BY SIDE ONE FOR VIEW COMMENTS
        // OTHER FOR POSTING NEW COMMENTS WHICH WILL THEN CREATE A JTextField and Post Button
        // To readers: This was just temporary.
        
        JLabel cmntLabel = new JLabel();
        cmntContainer.add(cmntLabel);
        if(comments.isEmpty()) {
            cmntLabel.setText("   No comments.");
        }
        else {
            cmntLabel.setText("   " + comments.size() + " comment(s). Click here to view.");
        }
    }
    
    /**
     * Create a string describing a time point in the past in terms 
     * relative to current time, such as "30 seconds ago" or "7 minutes ago".
     * Currently, only seconds and minutes are used for the string.
     * 
     * @param time  The time value to convert (in system milliseconds)
     * @return      A relative time string for the given time
     */
    
    private String timeString(long time)
    {
        long current = System.currentTimeMillis();
        long pastMillis = current - time;      // time passed in milliseconds
        long seconds = pastMillis/1000;
        long minutes = seconds/60;
        if(minutes > 0) {
            return minutes + " minutes ago";
        }
        else {
            return seconds + " seconds ago";
        }
    }
}
