package read;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * The type Colors parser.
 */
public class ColorsParser {
    /**
     * Color from string java . awt . color.
     *
     * @param s the s
     * @return the java . awt . color
     */
    public java.awt.Color colorFromString(String s) {
        Pattern pattern1 = compile("color\\(RGB\\(([0-9]+),([0-9]+),([0-9]+)\\)\\)");
        Pattern pattern2 = compile("([0-9]+)");

        Matcher matcher1 = pattern1.matcher(s);

        if (matcher1.find()) {
            String subS = s.substring(matcher1.start(), matcher1.end());
            Matcher matcher2 = pattern2.matcher(subS);
            int num1, num2, num3;
            matcher2.find();
            num1 = Integer.parseInt(subS.substring(matcher2.start(), matcher2.end()));
            matcher2.find();
            num2 = Integer.parseInt(subS.substring(matcher2.start(), matcher2.end()));
            matcher2.find();
            num3 = Integer.parseInt(subS.substring(matcher2.start(), matcher2.end()));

            return new Color(num1, num2, num3);
        }

        Pattern patternNameColor = compile("color\\((([a-z]+)([A-Z]*)([a-z]+))\\)");
        Matcher matcherNameColor = patternNameColor.matcher(s);
        if (matcherNameColor.find()) {
            String color = s.substring(matcherNameColor.start() + 6, matcherNameColor.end() - 1);
            if (color.equals("red")) {
                return Color.RED;
            }
            if (color.equals("black")) {
                return Color.black;
            }
            if (color.equals("blue")) {
                return Color.blue;
            }
            if (color.equals("cyan")) {
                return Color.cyan;
            }
            if (color.equals("gray")) {
                return Color.gray;
            }
            if (color.equals("lightGray")) {
                return Color.lightGray;
            }
            if (color.equals("green")) {
                return Color.green;
            }
            if (color.equals("orange")) {
                return Color.orange;
            }
            if (color.equals("pink")) {
                return Color.pink;
            }
            if (color.equals("white")) {
                return Color.white;
            }
            if (color.equals("yellow")) {
                return Color.yellow;
            }
        }
        return null;
    }
}
