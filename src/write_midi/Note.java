package write_midi;

import exceptions.*;

/**
 * Note类统一表示了四类音符：一般的单音（音名+后缀）、休止符、表示持续上一发声内容（单音或和弦）的占位音、以及没有音符含义的占位音符
 */
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
     * 延续上一发声内容的占位音以及休止符的构造器
     * @param c 一个布尔值，表示构造的音符是否是延续上一发声内容的占位音
     * @param r 一个布尔值，表示构造的音符是否是休止符
     * @throws NoteConstructException 使用此构造器构造Note对象时，两参数不能相同；若相同，抛出Note对象构造异常
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
     * 占位音符的构造器，构造一个没有音符意义的Note对象，可用于标志和弦的存在
     */
    public Note() {
        cont = true;
        rest = true;
    }

    /**
     * 返回音符的音名
     * @return 一个字符串，即音名
     * @throws NoteInfoException 只有音符类型的Note对象有音名，如果试图访问其他类型的Note对象的音名（如休止符的音名），抛出获取Note对象信息的异常
     */
    public String getName() throws NoteInfoException{
        if (this.isNote()) {
            return note_name;
        }
        else {
            throw new NoteInfoException();
        }
    }

    /**
     * 返回音符所在音阶
     * @return 一个整数，即音符所在音阶
     * @throws NoteInfoException 只有音符类型的Note对象有音阶，如果试图访问其他类型的Note对象的音阶（如休止符的音阶），抛出获取Note对象信息的异常
     */
    public int getOctave() throws NoteInfoException{
        if (this.isNote()) {
            return octave;
        }
        else {
            throw new NoteInfoException();
        }
    }

    /**
     * 返回音符是否是升音
     * @return 一个布尔值，表示音符是否为升音
     * @throws NoteInfoException 只有音符类型的Note对象有升音信息，如果试图访问其他类型的Note对象的升音信息（如休止符的升音），抛出获取Note对象信息的异常
     */
    public boolean isSharp() throws NoteInfoException{
        if (this.isNote()) {
            return sharp;
        }
        else {
            throw new NoteInfoException();
        }
    }

    /**
     * 返回此Note对象是否为音符类型
     * @return 一个布尔值，表示此Note对象是否为音符类型
     */
    public boolean isNote() {
        if (!cont & !rest) {
            return true;
        }
        else return false;
    }

    /**
     * 返回此Note对象是否为无意义类型
     * @return 一个布尔值，表示此Note对象是否为无意义类型
     */
    public boolean isNull() {
        return cont & rest;
    }

    /**
     * 返回此Note对象是否为休止符类型
     * @return 一个布尔值，表示此Note对象是否为休止符类型
     */
    public boolean isRest() {
        return rest & !cont;
    }

    /**
     * 返回此Note对象是否为延续上一发声内容的占位符类型
     * @return 一个布尔值，表示此Note对象是否为延续上一发声内容的占位符类型
     */
    public boolean isContinue() {
        return cont & !rest;
    }

    /**
     * 打印此Note对象的信息（用于debug）
     */
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
        else if (isNull()) {
            System.out.println("null note");
        }
    }
}
