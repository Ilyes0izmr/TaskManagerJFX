package com.example.todolist.ui;

public class RandomColor {

    // Function to return a random color code from the list
    public static String getRandomColor() {
        // List of hex color codes
        String[] colors = {
                "#07002D", "#15F5BA", "#836FFF", "#EB3678",
                "#FF8F00", "#B6FFFA", "#687EFF", "#3DC2EC",
                "#2A2438"
        };

        // Pick a random color from the list
        return colors[(int) (Math.random() * colors.length)];
    }
}