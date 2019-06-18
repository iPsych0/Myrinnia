package dev.ipsych0.maprefactoring;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    private File file;
    private String correctMapValues;

    public FileParser(File file) {
        this.file = file;
        this.correctMapValues = loadCorrectMapValues();
        parse();
    }

    private String loadCorrectMapValues() {
        InputStream is = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = new FileInputStream("../correctTilesetElements.txt");
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            if(sb.toString().isEmpty()){
                throw new Exception();
            }

            return sb.toString();

        } catch (Exception e) {
            System.exit(1);
        }
        return null;
    }

    private void parse() {
        InputStream is = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String inputString = sb.toString();

            is.close();
            reader.close();

            Pattern p = Pattern.compile("(\\s<tileset firstgid=\"\\d+\"\\ssource=\"[a-zA-Z_0-9\\-]+.tsx\"/>)\n");
            Matcher m = p.matcher(inputString);

            String incorrectValues = "";
            while (m.find()) {
                incorrectValues += m.group();
            }

            inputString = inputString.replaceFirst(incorrectValues, correctMapValues);

            FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(inputString.getBytes());
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
