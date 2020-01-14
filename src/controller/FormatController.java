package controller;

public class FormatController {

    public enum format {title, heading, bolditalic, bold, italic, paragraph};

    public format formatWord(String font, int size, boolean sizeb2) {
        if(font.contains("-It") && font.contains("-Bold"))
            return format.bolditalic;
        else if(font.contains("-It"))
            return format.italic;
        else if(font.contains("-Bold") && size>12 && sizeb2)
            return format.heading;
        else if(font.contains("-Bold"))
            return format.bold;
        else if (size>14)
            return format.title;
        else
            return format.paragraph;

    }
}
