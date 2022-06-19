package write_midi;

import javax.sound.midi.*;
import java.io.*;
import java.util.*;
import exceptions.*;

/**
 * MidiWriter为一个利用javax.sound.midi库来写midi文件的类
 */
public class MidiWriter{
    static final int tpb = 24;
    static final byte soft_on = 0x20;
    static final byte soft_off = 0x10;
    static final byte strong_on = 0x60;
    static final byte strong_off = 0x50;
    byte velocity_on;
    byte velocity_off;
    int cur_octave;
    int cur_instrument;
    int bpm;
    int last_tick;

    Sequence seq;
    Track track;

    /**
     * MidiWriter的构造器
     * @param bp bpm（每分钟节拍数），一个整数，全曲速度设置
     * @param co 当前八度（current octave），合法范围为0到9，是一个可以在演奏过程中修改的量
     * @param ci 当前乐器（current instrument），一个整数，对应general midi的音色表编号
     * @param strength 力度，可以取0到1，1代表强力度，0代表弱力度
     * @throws Exception 异常
     */
    public MidiWriter(int bp, int co, int ci, int strength) throws Exception{
        bpm = bp;
        cur_octave = co;
        cur_instrument = ci;
        if (strength == 0) {
            velocity_on = soft_on;
            velocity_off = soft_off;
        }
        else if (strength == 1) {
            velocity_on = strong_on;
            velocity_off = strong_off;
        }
        else {
            throw new InvalidVelocityConfigException();
        }
        last_tick = 1;

        // tempo-based timing
        seq = new Sequence(Sequence.PPQ, tpb); 
        track = seq.createTrack();
            
        // system exclusive message
        byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
        SysexMessage sm = new SysexMessage();
        sm.setMessage(b, 6);
        MidiEvent me = new MidiEvent(sm, (long)0);
        track.add(me);

        //calculate speed
        int speed = 60000000 / bpm;
        int s1 = (speed & Integer.valueOf("FF0000", 16)) >> 16;
        int s2 = speed & Integer.valueOf("00FF00", 16) >> 8;
        int s3 = speed & Integer.valueOf("0000FF", 16);

        //set speed
        MetaMessage mt = new MetaMessage();
        byte[] bt = {(byte)s1, (byte)s2, (byte)s3};
        mt.setMessage(0x51, bt, 3);
        me = new MidiEvent(mt, (long)0);
        track.add(me);

        //set omni on
        ShortMessage mm = new ShortMessage();
        mm.setMessage(0xB0, 0x7D, 0x00);
        me = new MidiEvent(mm, (long)0);
        track.add(me);

        //set poly on
        mm = new ShortMessage();
        mm.setMessage(0xB0, 0x7F, 0x00);
        me = new MidiEvent(mm, (long)0);
        track.add(me);

        //set instrument
        mm = new ShortMessage();
        mm.setMessage(0xC0, (byte)cur_instrument, 0x00);
        me = new MidiEvent(mm, (long)0);
        track.add(me);        
    }

    /**
     * 本方法将
     * @param file_name
     * @throws Exception
     */
    public void writeMidiFile(String file_name) throws Exception{
        File f = new File(file_name);
        MidiSystem.write(seq, 1, f);
    }

    int getNoteID(String noteName, int octave, boolean sharp) {
        int id = 12 * (octave + 1);
        if (sharp) {
            id += 1;
        }
        if (noteName.equals("C")) {
            // do nothing
        }
        else if (noteName.equals("D")) {
            id += 2;
        }
        else if (noteName.equals("E")) {
            id += 4;
        }
        else if (noteName.equals("F")) {
            id += 5;
        }
        else if (noteName.equals("G")) {
            id += 7;
        }
        else if (noteName.equals("A")) {
            id += 9;
        }
        else {
            id += 11;
        }
        
        return id;
    }

    /**
     * 向音轨中添加一个音，具体通过添加一个note-on事件以及一个note-off事件实现
     * @param note 待添加的音
     * @param duration 持续的节拍数
     * @throws Exception 异常
     */
    public void addNote(Note note, float duration) throws Exception{
        int id = getNoteID(note.getName(), note.getOctave(), note.isSharp());
        
        // note on
        ShortMessage mm = new ShortMessage();
        mm.setMessage(0x90, id, velocity_on);
        MidiEvent me = new MidiEvent(mm, last_tick);
        track.add(me);

        last_tick += duration * tpb;

        // note off
        mm = new ShortMessage();
        mm.setMessage(0x80, id, velocity_off);
        me = new MidiEvent(mm, (long)(last_tick));
        track.add(me);

    }

    /**
     * 向音轨中特定位置（时间，即tick）添加一个音，具体通过添加一个note-on事件以及一个note-off事件实现
     * @param note 待添加的音
     * @param duration 持续的节拍数
     * @param on_tick 待添加的音的开始时刻
     * @throws Exception 异常
     */
    public void addNote(Note note, float duration, int on_tick) throws Exception{
        int id = getNoteID(note.getName(), note.getOctave(), note.isSharp());
        
        // note on
        ShortMessage mm = new ShortMessage();
        mm.setMessage(0x90, id, velocity_on);
        MidiEvent me = new MidiEvent(mm, on_tick);
        track.add(me);

        last_tick = on_tick + (int)(duration * tpb);

        // note off
        mm = new ShortMessage();
        mm.setMessage(0x80, id, velocity_off);
        me = new MidiEvent(mm, (long)(last_tick));
        track.add(me);

    }

    /**
     * 向音轨中添加一个休止音，具体通过更新last_tick实现
     * @param duration 休止音持续的节拍数
     */
    public void addRest(float duration) {
        last_tick += duration * tpb;
    }

    /**
     * 向音轨中添加一个和弦，具体通过添加多个同时的note-on以及note-off事件实现
     * @param notes 待添加的和弦中所有音的信息
     * @param duration 持续的节拍数
     * @throws Exception 异常
     */
    public void addChord(ArrayList<Note> notes, float duration) throws Exception{
        for(int i = 0; i < notes.size(); i++) {
            int id = getNoteID(notes.get(i).getName(), notes.get(i).getOctave(), notes.get(i).isSharp());
        
            // note on
            ShortMessage mm = new ShortMessage();
            mm.setMessage(0x90, id, velocity_on);
            MidiEvent me = new MidiEvent(mm, last_tick);
            track.add(me);

            // note off
            mm = new ShortMessage();
            mm.setMessage(0x80, id, velocity_off);
            me = new MidiEvent(mm, (long)(last_tick + duration * tpb));
            track.add(me);
        }
        last_tick += duration * tpb;
    }

    public void addChord(ArrayList<Note> notes, float duration, int on_tick) throws Exception{
        for(int i = 0; i < notes.size(); i++) {
            int id = getNoteID(notes.get(i).getName(), notes.get(i).getOctave(), notes.get(i).isSharp());
        
            // note on
            ShortMessage mm = new ShortMessage();
            mm.setMessage(0x90, id, velocity_on);
            MidiEvent me = new MidiEvent(mm, on_tick);
            track.add(me);

            // note off
            mm = new ShortMessage();
            mm.setMessage(0x80, id, velocity_off);
            me = new MidiEvent(mm, (long)(on_tick + duration * tpb));
            track.add(me);
        }
        last_tick = on_tick + (int)(duration * tpb);
    }

    public void addSequence(ArrayList<Note> notes, ArrayList<ArrayList<Note>> chords, ArrayList<Float> durations) throws Exception{
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).isNote()) {
                addNote(notes.get(i), durations.get(i));
            }
            else if (notes.get(i).isRest()) {
                addRest(durations.get(i));
            }
            else if (notes.get(i).isNull()) {
                addChord(chords.get(0), durations.get(i));
                chords.remove(0);
            }
        }
    }

    public int getLastTick() {
        return last_tick;
    }

}
