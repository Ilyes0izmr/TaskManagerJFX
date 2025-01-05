package com.example.todolist.ui;

/**
 * @brief Provides utility methods for generating random solid colors.
 *
 * @author Izemmouren Ilyes
 */
public class RandomColor {

    /**
     * @brief Returns a random solid color code.
     * This method selects a random solid color from a predefined list of hex color codes.
     *
     * @return String A random hex color code.
     */
    public static String getRandomColor() {
        // List of dark solid color codes
        String[] colors = {
                "#07002D", // Dark Blue
                "#0D1B2A", // Deep Blue
                "#1B263B", // Navy Blue
                "#2A2438", // Dark Purple
                "#4B4453", // Muted Purple
                "#6A5ACD", // Slate Blue
                "#483D8B", // Dark Indigo
                "#8B008B", // Dark Magenta
                "#C71585", // Medium Violet Red
                "#0A9396", // Dark Cyan
                "#005F73", // Deep Teal
                "#0A9396", // Dark Teal
                "#15F5BA", // Dark Turquoise
                "#0A9396", // Dark Green
                "#2E8B57"  // Sea Green
        };

        // Pick a random color from the list
        return colors[(int) (Math.random() * colors.length)];
    }
}