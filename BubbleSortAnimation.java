import javax.swing.*;
import java.awt.*;

/**
 * A class that provides a visual representation of the Bubble Sort algorithm using animation.
 * It extends JPanel to use its graphics capabilities for custom rendering.
 */
public class BubbleSortAnimation extends JPanel {
    private int[] array; // Array to be sorted
    private final int DELAY = 100; // Animation speed in milliseconds
    private int currentSwapA = -1, currentSwapB = -1; // Indices of elements being swapped
    private boolean sorting = false; // Flag to prevent multiple sort executions

    /**
     * Constructor that initializes the array to be sorted and starts the sorting process after a delay.
     *
     * @param inputArray An array of integers that will be sorted and displayed
     */
    public BubbleSortAnimation(int[] inputArray) {
        this.array = inputArray.clone(); // Clone to avoid modifying the original array
        // Start sorting after a delay of 2 seconds
        new Timer(2000, e -> startSorting()).start();
    }

    /**
     * Starts the sorting process in a separate thread to prevent blocking the UI thread.
     */
    private void startSorting() {
        if (!sorting) {
            sorting = true; // Set sorting flag to true
            new Thread(this::bubbleSort).start(); // Start the bubbleSort method in a new thread
        }
    }

    /**
     * The bubble sort algorithm. It iteratively compares and swaps adjacent elements to sort the array.
     */
    private void bubbleSort() {
        // Outer loop for each pass through the array
        for (int i = 0; i < array.length - 1; i++) {
            // Inner loop for comparing adjacent elements
            for (int j = 0; j < array.length - i - 1; j++) {
                currentSwapA = j; // Set the index of the first element to be swapped
                currentSwapB = j + 1; // Set the index of the second element to be swapped
                repaint(); // Request a repaint to visualize the current state
                sleep(); // Pause to create animation effect

                // If the first element is greater, swap the elements
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
        currentSwapA = currentSwapB = -1; // Reset swap tracking after sorting is done
        repaint(); // Final repaint after sorting is complete
    }

    /**
     * Swaps the elements at the specified indices and repaints the array to show the change.
     *
     * @param i Index of the first element
     * @param j Index of the second element
     */
    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        repaint(); // Repaint the panel to visualize the swap
        sleep(); // Pause to create animation effect
    }

    /**
     * Pauses the current thread for the specified delay (animation speed).
     */
    private void sleep() {
        try {
            Thread.sleep(DELAY); // Pause execution for a given delay time
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle interruption exception
        }
    }

    /**
     * Paints the components of the BubbleSortAnimation panel, including the bubbles and numbers.
     * This method is called whenever the UI needs to be redrawn.
     *
     * @param g The Graphics object used to paint the components
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to handle basic rendering

        int panelWidth = getWidth(); // Get the current width of the panel
        int bubbleDiameter = panelWidth / array.length - 10; // Calculate the diameter of each bubble
        Font font = new Font("Arial", Font.BOLD, 18); // Set font for the numbers inside the bubbles

        // Loop through each element in the array to draw the corresponding bubble
        for (int i = 0; i < array.length; i++) {
            int x = i * (bubbleDiameter + 10); // Calculate the x-position of the bubble
            int y = getHeight() / 2 - array[i] / 2; // Calculate the y-position based on the value

            // Set the color for the bubble (red for the current swap, blue otherwise)
            if (i == currentSwapA || i == currentSwapB) {
                g.setColor(Color.RED); // Highlight swapped elements in red
            } else {
                g.setColor(Color.BLUE); // Default color for non-swapped elements
            }

            g.fillOval(x, y, bubbleDiameter, bubbleDiameter); // Draw the bubble
            g.setColor(Color.BLACK); // Set color for the bubble's border
            g.drawOval(x, y, bubbleDiameter, bubbleDiameter); // Draw the border of the bubble

            // Draw the number inside the bubble
            g.setColor(Color.WHITE); // Set color for the number text
            g.setFont(font); // Set the font for the number
            String number = String.valueOf(array[i]); // Convert the number to a string
            int textWidth = g.getFontMetrics().stringWidth(number); // Calculate text width for centering
            int textHeight = g.getFontMetrics().getAscent(); // Calculate text height for centering
            g.drawString(number, x + bubbleDiameter / 2 - textWidth / 2, y + bubbleDiameter / 2 + textHeight / 4); // Draw the number inside the bubble
        }
    }

    /**
     * Main method to run the animation with a custom array of integers.
     *
     * @param args Command line arguments (not used in this program)
     */
    public static void main(String[] args) {
        // Example of a custom array that will be sorted
        int[] customArray = {42, 15, 78, 30, 5, 95, 60, 20, 10, 55, 100, 1, 2, 1}; // Change this array

        // Create a new JFrame to hold the animation panel
        JFrame frame = new JFrame("Bubble Sort Animation with Custom Array");
        BubbleSortAnimation panel = new BubbleSortAnimation(customArray); // Create an instance of the animation panel

        // Setup the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400); // Set the size of the frame
        frame.add(panel); // Add the animation panel to the frame
        frame.setVisible(true); // Make the frame visible
    }
}
