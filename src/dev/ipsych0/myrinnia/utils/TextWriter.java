package dev.ipsych0.myrinnia.utils;

import dev.ipsych0.myrinnia.Handler;

import java.io.Serializable;

public class TextWriter implements Serializable {

    private static final long serialVersionUID = 1004544037335045018L;
    private String[] text;
    private StringBuilder sb;
    private static final int CHARS_PER_SECOND = 20;
    private static final int CHAR_PER_TICK = 60 / CHARS_PER_SECOND;
    private int timer;
    private int charIndex;
    private int commaTimer, fullStopTimer;
    private boolean stoppingComma, stoppingFullStop;
    private int currentText;
    private boolean skipRequested;

    public TextWriter(String[] text) {
        this.text = text;
        this.currentText = 0;
        sb = new StringBuilder();
    }

    public void tick() {
        if (stoppingComma) {
            if (10 > commaTimer) {
                commaTimer++;
                return;
            } else {
                stoppingComma = false;
                commaTimer = 0;
            }
        }
        if (stoppingFullStop) {
            if (20 > fullStopTimer) {
                fullStopTimer++;
                return;
            } else {
                stoppingFullStop = false;
                fullStopTimer = 0;
            }
        }

        timer++;

        // Every 3 ticks, append a character if we're not at the end yet
        if (text != null) {
            boolean outOfBounds = !(charIndex < text[currentText].length());
            if (!stoppingComma && !stoppingFullStop && timer >= CHAR_PER_TICK && !outOfBounds) {
                sb.append(text[currentText].charAt(charIndex));

                if (text[currentText].charAt(charIndex) == ',') {
                    stoppingComma = true;
                } else if (text[currentText].charAt(charIndex) == '.') {
                    stoppingFullStop = true;
                    Handler.get().playEffect("ui/textbox_type.ogg");
                }

                charIndex++;
                timer = 0;
                if (charIndex % Handler.get().getRandomNumber(2, 3) == 0 && !stoppingFullStop) {
                    Handler.get().playEffect("ui/textbox_type.ogg");
                }
            }
        }
    }

    public void nextDialogue() {
        // If we press continue while still appending, render the whole string immediately
        if (sb.length() != text[currentText].length()) {
            sb.delete(0, text[currentText].length());
            sb.append(text[currentText]);
            charIndex = text[currentText].length();
            skipRequested = true;
        } else {
            // If we press continue when whole string is ready, we proceed to next monologue
            sb.delete(0, text[currentText].length());
            currentText++;
            charIndex = 0;
        }
    }

    public StringBuilder getSb() {
        return sb;
    }

    public int getCurrentText() {
        return currentText;
    }

    public void setCurrentText(int currentText) {
        this.currentText = currentText;
    }

    public int getMonologueLength() {
        return text.length;
    }

    public boolean isSkipRequested() {
        return skipRequested;
    }
}
