package write_midi;

import exceptions.*;

public class Note {
    private String note_name;
    private int octave;
    private boolean sharp;
    private boolean cont;
    private boolean rest;

    /**
     * 音符对象的构造器
     * @param nn 音名
     * @param oct 所在音阶
     * @param sh 是否是升音
     */
    public Note(String nn, int oct, boolean sh) {
        note_name = nn;
        octave = oct;
        sharp = sh;
        cont = rest = false;
    }

    /**
     * 特殊音的构造器，构造一个延续前一音的占位音或一个休止符
     * @param cont 延续前一音的标志
     * @param rest 休止符的标志
     */
    public Note(boolean c, boolean r) throws NoteConstructException{
        if (c ^ r) {
            cont = c;
            rest = r;
        }
        else {
            throw new NoteConstructException();
        }
    }
    
    /**
     * 占位音符的构造器，构造一个没有音符意义的Note对象
     */
    public Note() {
        cont = true;
        rest = true;
    }

    public String getName() {
        return note_name;
    }

    public int getOctave() {
        return octave;
    }

    public boolean isSharp() {
        return sharp;
    }

    public boolean isNote() {
        if (!cont & !rest) {
            return true;
        }
        else return false;
    }

    public boolean isNull() {
        return cont & rest;
    }

    public boolean isRest() {
        return rest & !cont;
    }

    public boolean isContinue() {
        return cont & !rest;
    }

    public void printInfo() {
        System.out.println("Note Info:");
        if (isNote()) {
            System.out.println("note name:" + note_name + "; octave:" + Integer.toString(octave) + "; sharp:" + Boolean.toString(sharp));
        }
        else if (isContinue()) {
            System.out.println("continue last note");
        }
        else if (isRest()) {
            System.out.println("rest note");
        }
    }
}
