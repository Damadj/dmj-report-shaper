package dmj.report.shaper.util;

public class TextFormatter {
    public String formatTextName(String name) {
        int index = name.indexOf("(");
        if (index > 0) return name.substring(0, index);
        else return name;
    }
}
