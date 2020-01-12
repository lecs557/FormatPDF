package controller;

public class FormatController {

    public enum format {title, heading, bolditalic, bold, italic, paragraph};

    public format formatWord(String font, int size) {
        if(font.contains("-It") && font.contains("-Bold"))
            return format.bolditalic;
        else if(font.contains("-It"))
            return format.italic;
        else if(font.contains("-Bold") && size>12)
            return format.heading;
        else if(font.contains("-Bold"))
            return format.bold;
        else
            return format.paragraph;

    }
}
