import java.io.IOException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

class BGM{
	private Sequence sequence;
	private Sequencer sequencer;
	private long startTick = 0;
	
	public BGM(){
		try{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(new MyMetaEventListener());
			URL url = BGM.class.getResource("bgm/tetris.mid");
			sequence = MidiSystem.getSequence(url);
			sequencer.setSequence(sequence);
			startTick = sequencer.getMicrosecondPosition()+2000000;
			sequencer.setMicrosecondPosition(startTick);
		}catch(MidiUnavailableException e){
			e.printStackTrace();
		}catch(InvalidMidiDataException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void play(){
		sequencer.start();
	}
	public void stop(){
		sequencer.stop();
	}
	private class MyMetaEventListener implements MetaEventListener{
		public void meta(MetaMessage meta){
			if(meta.getType() == 47){
				if(sequencer != null && sequencer.isOpen()){
					sequencer.setMicrosecondPosition(startTick);
					sequencer.start();
				}
			}
		}
	}
	
}
