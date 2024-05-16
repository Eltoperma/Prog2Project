package DrawLogic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BouncingBallAnimation extends JPanel {
    private int x = 0;
    private int y = 0;
    private final int BALL_SIZE = 30;
    private final int MOVE_DISTANCE = 50; // Specify the distance to move
    private Timer moveTimer;

    public BouncingBallAnimation() {
        setFocusable(true); // Allow the JPanel to receive key events
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    private void handleKeyPress(KeyEvent e) {
        if (moveTimer != null && moveTimer.isRunning()) {
            return; // Don't start a new animation if one is already running
        }

        int keyCode = e.getKeyCode();
        int targetX = x;
        int targetY = y;

        // Calculate the target position based on the pressed key
        if (keyCode == KeyEvent.VK_W) {
            targetY = Math.max(y - MOVE_DISTANCE, 0); // Move up
        } else if (keyCode == KeyEvent.VK_S) {
            targetY = Math.min(y + MOVE_DISTANCE, getHeight() - BALL_SIZE); // Move down
        } else if (keyCode == KeyEvent.VK_A) {
            targetX = Math.max(x - MOVE_DISTANCE, 0); // Move left
        } else if (keyCode == KeyEvent.VK_D) {
            targetX = Math.min(x + MOVE_DISTANCE, getWidth() - BALL_SIZE); // Move right
        }

        // Start a timer to animate the movement
        int finalTargetX = targetX;
        int finalTargetY = targetY;
        moveTimer = new Timer(1, new ActionListener() {
            int startX = x;
            int startY = y;
            int deltaX = finalTargetX - startX;
            int deltaY = finalTargetY - startY;
            int steps = 12;
            int stepCount = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (stepCount >= steps) {
                    moveTimer.stop(); // Stop the timer when animation is complete
                } else {
                    // Calculate the current position based on the easing function
                    double t = (double) stepCount / steps;
                    double easing = easeInOut(t);
                    x = startX + (int) (deltaX * easing);
                    y = startY + (int) (deltaY * easing);
                    stepCount++;
                    repaint(); // Trigger repaint to update ball position
                }
            }
        });

        moveTimer.start(); // Start the timer
    }

    // Bezier curve for easing in and out
    private double easeInOut(double t) {
        return t*t / (t*t + (1 - t)*(1 - t));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(x, y, BALL_SIZE, BALL_SIZE); // Draw a red ball
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Ball Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new BouncingBallAnimation());
        frame.setVisible(true);
    }
}
