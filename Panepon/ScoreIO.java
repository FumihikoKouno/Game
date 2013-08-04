/**
 * スコアの入出力用クラス
 * いろいろと後付けしたためぐちゃぐちゃで一番長い
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

class ScoreIO{
	// 各データ
	private boolean replayE;
	private boolean replaySC;
	private boolean replayST;
	private Record eRecord;
	private Record scRecord;
	private Record stRecord;
	private long eSeed;
	private long scSeed;
	private long stSeed;
	private int[] eScrollFrame;
	private int[] scScrollFrame;
	private int[] stScrollFrame;
	private int[] eSwapFrame;
	private int[] scSwapFrame;
	private int[] stSwapFrame;
	private int[] eSwapX;
	private int[] scSwapX;
	private int[] stSwapX;
	private int[] eSwapY;
	private int[] scSwapY;
	private int[] stSwapY;
	private int[] eCursorFrame;
	private int[] eCursorX;
	private int[] eCursorY;
	private int[] scCursorFrame;
	private int[] scCursorX;
	private int[] scCursorY;
	private int[] stCursorFrame;
	private int[] stCursorX;
	private int[] stCursorY;
	private int eSumTime;
	private int scSumTime;
	private int stSumTime;
	private int eCount;
	private int scCount;
	private int stCount;
	
	public ScoreIO(){}
	
	private void statusPrint(String fileName){
		System.out.println(fileName);
		System.out.println("*status:");
		System.out.println(" eRecord " + eRecord);
		System.out.println(" scRecord " + scRecord);
		System.out.println(" stRecord " + stRecord);
	}
	
	// 与えられたモードに対応するデータをrに挿入する
	public void makeModeRanking(Record[] r, int mode){
		Record tmp = null;
		switch(mode){
		case Data.ENDLESS:
			tmp = eRecord;
			break;
		case Data.SCORE_ATTACK:
			tmp = scRecord;
			break;
		case Data.STAGE_CLEAR:
			tmp = stRecord;
			break;
		}
		if(tmp != null){
			for(int j = 0; j < r.length; j++){
				if(r[j] == null){
					r[j] = tmp;
					break;
				}else{
					if(mode == Data.STAGE_CLEAR){
						if(tmp.haveShorterTimeThan(r[j])){
							for(int k = r.length-1; k >= j; k--){
								if(r[k] == null) continue;
								if(k == r.length-1){
									r[k] = null;
								}else{
									r[k+1] = r[k];
									r[k] = null;
								}
							}
							r[j] = tmp;
							break;
						}
					}else{
						if(tmp.haveHighScoreThan(r[j])){
							for(int k = r.length-1; k >= j; k--){
								if(r[k] == null) continue;
								if(k == r.length-1){
									r[k] = null;
								}else{
									r[k+1] = r[k];
									r[k] = null;
								}
							}
							r[j] = tmp;
							break;
						}
					}
				}
			}
		}
	}
	
	// ランキングの生成
	public void makeRanking(Record[] endless, Record[] scoreAttack, Record[] stageClear, boolean[] replayStatus){
		File dir;
		// ハードモード用ランキングと普通のランキング
		if(Data.hard==1) dir = new File("./Score");
		else dir = new File("./HardScore");
		File[] list;
		String name;
		// 各配列の初期化
		for(int i = 0; i < 10; i++){
			endless[i] = null;
			scoreAttack[i] = null;
			stageClear[i] = null;
		}
		String fileName;
		if(dir.exists() && dir.isDirectory()){
			list = dir.listFiles();
			// Score内の各ファイルについて
			for(int i = 0; i < list.length; i++){
				fileName = list[i].getName();
				// .scoreで終わるファイルなら
				if(fileName.endsWith(".score")){
					// ファイルの中身を読み、各メンバ変数に代入
					readFile(fileName);
					// 読んだデータをもとにランキングの対応する位置に挿入
					makeModeRanking(endless,Data.ENDLESS);
					makeModeRanking(scoreAttack,Data.SCORE_ATTACK);
					makeModeRanking(stageClear,Data.STAGE_CLEAR);
				}
			}
		}
		return;
	}

	// プレイ回数などを書き出す
	public void writePlayCount(BufferedOutputStream os, int mode, boolean b){
		try{
			Record tmp = null;
			switch(mode){
			case Data.ENDLESS:
				tmp = eRecord;
				break;
			case Data.SCORE_ATTACK:
				tmp = scRecord;
				break;
			case Data.STAGE_CLEAR:
				tmp = stRecord;
				break;
			default:
				tmp = new Record("",Data.score,Data.time,Data.maxChain,Data.maxDelete,false,0,0);
				break;
			}
			if(tmp == null){
				if(b){
					for(int i = 0; i < 4; i++){
						os.write(((Data.time) & (0xff<<i*8)) >> i*8);
						os.write(((1) & (0xff<<i*8)) >> i*8);
					}
				}else{
					for(int i = 0; i < 4; i++){
						os.write(((0) & (0xff<<i*8)) >> i*8);
						os.write(((0) & (0xff<<i*8)) >> i*8);
					}
				}
			}else{
				if(b){
					for(int i = 0; i < 4; i++){
						if(mode == Data.SCORE_ATTACK){
						os.write(((tmp.getSumTime()+Data.TIME_LIMIT) & (0xff<<i*8)) >> i*8);
						}else{
						os.write(((tmp.getSumTime()+Data.time) & (0xff<<i*8)) >> i*8);
						}
						os.write(((tmp.getCount()+1) & (0xff<<i*8)) >> i*8);
					}
				}else{
					for(int i = 0; i < 4; i++){
						os.write(((tmp.getSumTime()) & (0xff<<i*8)) >> i*8);
						os.write(((tmp.getCount()) & (0xff<<i*8)) >> i*8);
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	// 基本記録を書き出す(スコア、時間、最大連鎖数、最大同時消し数)
	public void writeRecord(BufferedOutputStream os, int mode){
		try{
			Record tmp = null;
			switch(mode){
			case Data.ENDLESS:
				tmp = eRecord;
				break;
			case Data.SCORE_ATTACK:
				tmp = scRecord;
				break;
			case Data.STAGE_CLEAR:
				tmp = stRecord;
				break;
			default:
				tmp = new Record("",Data.score,Data.time,Data.maxChain,Data.maxDelete,false,0,0);
				break;
			}
			if(tmp == null){
				for(int i = 0; i < 17; i++) os.write(0x00);
			}else{
				os.write(0x01);
				for(int i = 0; i < 4; i++){
					os.write((tmp.getScore() & (0xff<<i*8)) >> i*8);
					os.write((tmp.getTime() & (0xff<<i*8)) >> i*8);
					os.write((tmp.getMaxChain() & (0xff<<i*8)) >> i*8);
					os.write((tmp.getMaxDelete() & (0xff<<i*8)) >> i*8);
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public long getSeed(int mode){
		switch(mode){
		case Data.ENDLESS:
			return eSeed;
		case Data.SCORE_ATTACK:
			return scSeed;
		case Data.STAGE_CLEAR:
			return stSeed;
		}
		return -1L;
	}

	public int[] getScrollFrame(int mode){
		switch(mode){
		case Data.ENDLESS:
			return eScrollFrame;
		case Data.SCORE_ATTACK:
			return scScrollFrame;
		case Data.STAGE_CLEAR:
			return stScrollFrame;
		}
		return null;
	}
	public int[] getSwapX(int mode){
		switch(mode){
		case Data.ENDLESS:
			return eSwapX;
		case Data.SCORE_ATTACK:
			return scSwapX;
		case Data.STAGE_CLEAR:
			return stSwapX;
		}
		return null;
	}

	public int[] getSwapY(int mode){
		switch(mode){
		case Data.ENDLESS:
			return eSwapY;
		case Data.SCORE_ATTACK:
			return scSwapY;
		case Data.STAGE_CLEAR:
			return stSwapY;
		}
			return null;
	}


	public int[] getSwapFrame(int mode){
		switch(mode){
		case Data.ENDLESS:
			return eSwapFrame;
		case Data.SCORE_ATTACK:
			return scSwapFrame;
		case Data.STAGE_CLEAR:
			return stSwapFrame;
		}
		return null;
	}
	public int[] getCursorX(int mode){
		switch(mode){
		case Data.ENDLESS:
			return eCursorX;
		case Data.SCORE_ATTACK:
			return scCursorX;
		case Data.STAGE_CLEAR:
			return stCursorX;
		}
		return null;
	}

	public int[] getCursorY(int mode){
		switch(mode){
		case Data.ENDLESS:
			return eCursorY;
		case Data.SCORE_ATTACK:
			return scCursorY;
		case Data.STAGE_CLEAR:
			return stCursorY;
		}
		return null;
	}


	public int[] getCursorFrame(int mode){
		switch(mode){
		case Data.ENDLESS:
			return eCursorFrame;
		case Data.SCORE_ATTACK:
			return scCursorFrame;
		case Data.STAGE_CLEAR:
			return stCursorFrame;
		}
		return null;
	}

	// リプレイデータを読む(引数は名前でBufferedInputStreamを引数にとる同じ名前の関数を呼ぶ)
	public void readReplayData(String name){
		try{
			byte[] tmp = new byte[51];
			BufferedInputStream is;
			if(Data.hard == 1) is = new BufferedInputStream(new FileInputStream(new File("./Score/"+name+".score")));
			else is = new BufferedInputStream(new FileInputStream(new File("./HardScore/"+name+".score")));
			// リプレイデータの開始位置まで読み飛ばす
			is.read(tmp,0,51);
			readReplayData(is);
			is.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	// 対応するモードのリプレイデータを読み、それらをメンバ変数に代入
	public void readModeReplayData(BufferedInputStream is, int mode){
		try{
			switch(mode){
			case Data.ENDLESS:
				if(!replayE) return;
				break;
			case Data.SCORE_ATTACK:
				if(!replaySC) return;
				break;
			case Data.STAGE_CLEAR:
				if(!replayST) return;
				break;
			default:
				return;
			}
			byte[] tmp = new byte[8];
			long tmpSeed = 0;
			int[] tmpScrollFrame;
			int[] tmpSwapFrame;
			int[] tmpSwapX;
			int[] tmpSwapY;
			int[] tmpCursorFrame;
			int[] tmpCursorX;
			int[] tmpCursorY;
			is.read(tmp,0,8);
			tmpSeed = 0;
			for(int i = 0; i < 8; i++){
				long bit = tmp[i];
				tmpSeed += ( (bit&0x00000000000000ffL) << (8*i) );
			}
			is.read(tmp,0,4);
			int count = 0;
			for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
			tmpScrollFrame = new int[count];
			for(int i = 0; i < count; i++){
				tmpScrollFrame[i] = 0;
				is.read(tmp,0,4);
				for(int j = 0; j < 4; j++) tmpScrollFrame[i] += ((tmp[j] & 0xff) << (8*j));
			}
			count = 0;
			is.read(tmp,0,4);
			for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
			tmpSwapFrame = new int[count];
			tmpSwapX = new int[count];
			tmpSwapY = new int[count];
			for(int i = 0; i < count; i++){
				is.read(tmp,0,6);
				tmpSwapFrame[i] = 0;
				for(int j = 0; j < 4; j++) tmpSwapFrame[i] += ((tmp[j]&0xff) << (8*j));
				tmpSwapX[i] = tmp[4] & 0xff;
				tmpSwapY[i] = tmp[5] & 0xff;
			}
			count = 0;
			is.read(tmp,0,4);
			for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
			tmpCursorFrame = new int[count];
			tmpCursorX = new int[count];
			tmpCursorY = new int[count];
			for(int i = 0; i < count; i++){
				is.read(tmp,0,6);
				tmpCursorFrame[i] = 0;
				for(int j = 0; j < 4; j++) tmpCursorFrame[i] += ((tmp[j]&0xff) << (8*j));
				tmpCursorX[i] = tmp[4] & 0xff;
				tmpCursorY[i] = tmp[5] & 0xff;
			}
			switch(mode){
			case Data.ENDLESS:
				eSeed = tmpSeed;
				eScrollFrame = tmpScrollFrame;
				eSwapFrame = tmpSwapFrame;
				eSwapX = tmpSwapX;
				eSwapY = tmpSwapY;
				eCursorFrame = tmpCursorFrame;
				eCursorX = tmpCursorX;
				eCursorY = tmpCursorY;
				break;
			case Data.SCORE_ATTACK:
				scSeed = tmpSeed;
				scScrollFrame = tmpScrollFrame;
				scSwapFrame = tmpSwapFrame;
				scSwapX = tmpSwapX;
				scSwapY = tmpSwapY;
				scCursorFrame = tmpCursorFrame;
				scCursorX = tmpCursorX;
				scCursorY = tmpCursorY;
				break;
			case Data.STAGE_CLEAR:
				stSeed = tmpSeed;
				stScrollFrame = tmpScrollFrame;
				stSwapFrame = tmpSwapFrame;
				stSwapX = tmpSwapX;
				stSwapY = tmpSwapY;
				stCursorFrame = tmpCursorFrame;
				stCursorX = tmpCursorX;
				stCursorY = tmpCursorY;
				break;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	// プレイカウントを読む
	public void readPlayCount(BufferedInputStream is){
		try{
			byte[] tmp = new byte[24];
			int flag = is.read(tmp,0,24);
			if(flag == -1){
				eSumTime = 0;
				eCount = 0;
				scSumTime = 0;
				scCount = 0;
				stSumTime = 0;
				stCount = 0;
				return;
			}
			for(int i = 0; i < 8; i+=2){
				eSumTime += ((tmp[i]&0xff) << (8*i));
				eCount += ((tmp[i+1]&0xff) << (8*i));
			}
			for(int i = 0; i < 8; i+=2){
				scSumTime += ((tmp[i+8]&0xff) << (8*i));
				scCount += ((tmp[i+9]&0xff) << (8*i));
			}
			for(int i = 0; i < 8; i+=2){
				stSumTime += ((tmp[i+16]&0xff) << (8*i));
				stCount += ((tmp[i+17]&0xff) << (8*i));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	// リプレイデータを読む(引数はBufferedInputStream)
	public void readReplayData(BufferedInputStream is){
		try{
			byte[] tmp = new byte[8];
			int flag = is.read(tmp,0,3);
			if(flag == -1){
				replayE = false;
				replaySC = false;
				replayST = false;
				return;
			}
			replayE = (tmp[0] == 0x01);
			replaySC = (tmp[1] == 0x01);
			replayST = (tmp[2] == 0x01);
			readModeReplayData(is,Data.ENDLESS);
			readModeReplayData(is,Data.SCORE_ATTACK);
			readModeReplayData(is,Data.STAGE_CLEAR);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	// 対応する名前のファイルを読み、対応するメンバ変数にすべて代入
	public void readFile(String name){
		if(Data.hard==1){
			if(!(new File("./Score/"+name).exists())){
				eRecord = null;
				scRecord = null;
				stRecord = null;
				replayE = false;
				replaySC = false;
				replayST = false;
				return;
			}
		}else{
			if(!(new File("./HardScore/"+name).exists())){
				eRecord = null;
				scRecord = null;
				stRecord = null;
				replayE = false;
				replaySC = false;
				replayST = false;
				return;
			}
		}
		int eScore = 0;
		int eTime = 0;
		int eMaxChain = 0;
		int eMaxDelete = 0;
		int scScore = 0;
		int scTime = 0;
		int scMaxChain = 0;
		int scMaxDelete = 0;
		int stScore = 0;
		int stTime = 0;
		int stMaxChain = 0;
		int stMaxDelete = 0;
		int prevData;
		byte[] tmp = new byte[51];
		try{
			BufferedInputStream is;
			if(Data.hard==1) is = new BufferedInputStream(new FileInputStream(new File("./Score/"+name)));
			else is = new BufferedInputStream(new FileInputStream(new File("./HardScore/"+name)));
			prevData = is.read(tmp,0,51);
			for(int i = 0; i < 4; i++){
				eScore += ((tmp[1+i*4] & 0xff) << (8*i));
				eTime += ((tmp[2+i*4] & 0xff) << (8*i));
				eMaxChain += ((tmp[3+i*4] & 0xff) << (8*i));
				eMaxDelete += ((tmp[4+i*4] & 0xff) << (8*i));
				scScore += ((tmp[18+i*4] & 0xff) << (8*i));
				scTime += ((tmp[19+i*4] & 0xff) << (8*i));
				scMaxChain += ((tmp[20+i*4] & 0xff) << (8*i));
				scMaxDelete += ((tmp[21+i*4] & 0xff) << (8*i));
				stScore += ((tmp[35+i*4] & 0xff) << (8*i));
				stTime += ((tmp[36+i*4] & 0xff) << (8*i));
				stMaxChain += ((tmp[37+i*4] & 0xff) << (8*i));
				stMaxDelete += ((tmp[38+i*4] & 0xff) << (8*i));
			}
			readReplayData(is);
			readPlayCount(is);

			if(tmp[0] == 0x01){
				eRecord = new Record(name.substring(0,name.length()-6),eScore,eTime,eMaxChain,eMaxDelete,replayE,eSumTime,eCount);
			}else{
				eRecord = null;
			}
			if(tmp[17] == 0x01){
				scRecord = new Record(name.substring(0,name.length()-6),scScore,scTime,scMaxChain,scMaxDelete,replaySC,scSumTime,scCount);
			}else{
				scRecord = null;
			}
			if(tmp[34] == 0x01){
				stRecord = new Record(name.substring(0,name.length()-6),stScore,stTime,stMaxChain,stMaxDelete,replayST,stSumTime,stCount);
			}else{
				stRecord = null;
			}
			is.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	// リプレイデータの書き出し
	public void writeReplay(BufferedOutputStream os, int mode){
		try{
			long tmpSeed;
			int[] tmpScrollFrame;
			int[] tmpSwapFrame;
			int[] tmpSwapX;
			int[] tmpSwapY;
			int[] tmpCursorFrame;
			int[] tmpCursorX;
			int[] tmpCursorY;
			int len;
			switch(mode){
			case Data.ENDLESS:
				if(!replayE) return;
				tmpSeed = eSeed;
				tmpScrollFrame = eScrollFrame;
				tmpSwapFrame = eSwapFrame;
				tmpSwapX = eSwapX;
				tmpSwapY = eSwapY;
				tmpCursorFrame = eCursorFrame;
				tmpCursorX = eCursorX;
				tmpCursorY = eCursorY;
				break;
			case Data.SCORE_ATTACK:
				if(!replaySC) return;
				tmpSeed = scSeed;
				tmpScrollFrame = scScrollFrame;
				tmpSwapFrame = scSwapFrame;
				tmpSwapX = scSwapX;
				tmpSwapY = scSwapY;
				tmpCursorFrame = scCursorFrame;
				tmpCursorX = scCursorX;
				tmpCursorY = scCursorY;
				break;
			case Data.STAGE_CLEAR:
				if(!replayST) return;
				tmpSeed = stSeed;
				tmpScrollFrame = stScrollFrame;
				tmpSwapFrame = stSwapFrame;
				tmpSwapX = stSwapX;
				tmpSwapY = stSwapY;
				tmpCursorFrame = stCursorFrame;
				tmpCursorX = stCursorX;
				tmpCursorY = stCursorY;
				break;
			default:
				tmpSeed = Data.seed;
				len = Data.replayScrollFrame.size();
				tmpScrollFrame = new int[len];
				for(int i = 0; i < len; i++) tmpScrollFrame[i] = Data.replayScrollFrame.get(i);
				len = Data.replaySwapFrame.size();
				tmpSwapFrame = new int[len];
				tmpSwapX = new int[len];
				tmpSwapY = new int[len];
				for(int i = 0; i < len; i++){
					tmpSwapFrame[i] = Data.replaySwapFrame.get(i);
					tmpSwapX[i] = Data.replaySwapX.get(i);
					tmpSwapY[i] = Data.replaySwapY.get(i);
				}
				len = Data.replayCursorFrame.size();
				tmpCursorFrame = new int[len];
				tmpCursorX = new int[len];
				tmpCursorY = new int[len];
				for(int i = 0; i < len; i++){
					tmpCursorFrame[i] = Data.replayCursorFrame.get(i);
					tmpCursorX[i] = Data.replayCursorX.get(i);
					tmpCursorY[i] = Data.replayCursorY.get(i);
				}
				break;
			}
			for(int i = 0; i < 8; i++){
				os.write((int)((tmpSeed & (0xffL << i*8)) >> i*8));
			}
			len = tmpScrollFrame.length;
			for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			for(int i = 0; i < len; i++){
				for(int j = 0; j < 4; j++){
				os.write((tmpScrollFrame[i] & (0xff << j*8)) >> j*8);
				}
			}
			len = tmpSwapFrame.length;
			for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			for(int i = 0; i < len; i++){
				for(int j = 0; j < 4; j++){
					os.write((tmpSwapFrame[i] & (0xff << j*8)) >> j*8);
				}
				os.write(tmpSwapX[i] & 0xff);
				os.write(tmpSwapY[i] & 0xff);
			}
			len = tmpCursorFrame.length;
			for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			for(int i = 0; i < len; i++){
				for(int j = 0; j < 4; j++){
					os.write((tmpCursorFrame[i] & (0xff << j*8)) >> j*8);
				}
				os.write(tmpCursorX[i] & 0xff);
				os.write(tmpCursorY[i] & 0xff);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	// 名前を入力し、その名前に対応するスコアデータをファイルに出力
	public void output(){
		boolean updatable = false;
		File dir;
		if(Data.hard==1) dir = new File("./Score");
		else dir = new File("./HardScore");
		if(!dir.exists()){
			dir.mkdir();
		}
		JFrame frame = new JFrame();
		String name = "";
		while(name != null && name.equals("")) name = JOptionPane.showInputDialog(frame, "名前を入力してください");
		if(name != null){
			name += ".score";
			writeFile(name,Data.gameStatus);
		}
	}
	
	// 対応する名前のファイルにデータを書き出し
	public void writeFile(String name, int mode){
		boolean update = false;
		readFile(name);
		try{
			BufferedOutputStream os;
			if(Data.hard==1) os = new BufferedOutputStream(new FileOutputStream(new File("./Score/"+name)));
			else os = new BufferedOutputStream(new FileOutputStream(new File("./HardScore/"+name)));
			switch(mode){
			case Data.ENDLESS:
				update = (eRecord == null || eRecord.getScore() < Data.score);
				if(update) writeRecord(os,-1);
				else writeRecord(os,Data.ENDLESS);
				writeRecord(os,Data.SCORE_ATTACK);
				writeRecord(os,Data.STAGE_CLEAR);
				os.write(0x01);
				if(replaySC) os.write(0x01);
				else os.write(0x00);
				if(replayST) os.write(0x01);
				else os.write(0x00);
				if(update) writeReplay(os,-1);
				else writeReplay(os,Data.ENDLESS);
				writeReplay(os,Data.SCORE_ATTACK);
				writeReplay(os,Data.STAGE_CLEAR);
				writePlayCount(os,Data.ENDLESS,true);
				writePlayCount(os,Data.SCORE_ATTACK,false);
				writePlayCount(os,Data.STAGE_CLEAR,false);
				break;
			case Data.SCORE_ATTACK:
				update = (scRecord == null || scRecord.getScore() < Data.score);
				writeRecord(os,Data.ENDLESS);
				if(update) writeRecord(os,-1);
				else writeRecord(os,Data.SCORE_ATTACK);
				writeRecord(os,Data.STAGE_CLEAR);
				if(replayE) os.write(0x01);
				else os.write(0x00);
				os.write(0x01);
				if(replayST) os.write(0x01);
				else os.write(0x00);
				writeReplay(os,Data.ENDLESS);
				if(update) writeReplay(os,-1);
				else writeReplay(os,Data.SCORE_ATTACK);
				writeReplay(os,Data.STAGE_CLEAR);
				writePlayCount(os,Data.ENDLESS,false);
				writePlayCount(os,Data.SCORE_ATTACK,true);
				writePlayCount(os,Data.STAGE_CLEAR,false);
				break;
			case Data.STAGE_CLEAR:
				update = (stRecord == null || stRecord.getTime() > Data.time);
				writeRecord(os,Data.ENDLESS);
				writeRecord(os,Data.SCORE_ATTACK);
				if(update) writeRecord(os,-1);
				else writeRecord(os,Data.STAGE_CLEAR);
				if(replayE) os.write(0x01);
				else os.write(0x00);
				if(replaySC) os.write(0x01);
				else os.write(0x00);
				os.write(0x01);
				writeReplay(os,Data.ENDLESS);
				writeReplay(os,Data.SCORE_ATTACK);
				if(update) writeReplay(os,-1);
				else writeReplay(os,Data.STAGE_CLEAR);
				writePlayCount(os,Data.ENDLESS,false);
				writePlayCount(os,Data.SCORE_ATTACK,false);
				writePlayCount(os,Data.STAGE_CLEAR,true);
				break;
			default:
				writeRecord(os,Data.ENDLESS);
				writeRecord(os,Data.SCORE_ATTACK);
				writeRecord(os,Data.STAGE_CLEAR);
				if(replayE) os.write(0x01);
				else os.write(0x00);
				if(replaySC) os.write(0x01);
				else os.write(0x00);
				if(replayST) os.write(0x01);
				else os.write(0x00);
				writeReplay(os,Data.ENDLESS);
				writeReplay(os,Data.SCORE_ATTACK);
				writeReplay(os,Data.STAGE_CLEAR);
				writePlayCount(os,Data.ENDLESS,false);
				writePlayCount(os,Data.SCORE_ATTACK,false);
				writePlayCount(os,Data.STAGE_CLEAR,false);
				break;
			}
			os.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}