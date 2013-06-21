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
	private int mode;
	Record eRecord;
	Record scRecord;
	Record stRecord;
	public ScoreIO(){}
	
	private void statusPrint(String fileName){
		System.out.println(fileName);
		System.out.println("  status:");
		System.out.println("    eRecord " + eRecord);
		System.out.println("    scRecord " + scRecord);
		System.out.println("    stRecord " + stRecord);
	}
	
	public void makeRanking(Record[] endless, Record[] scoreAttack, Record[] stageClear){
		File dir = new File("./Score");
		File[] list;
		String name;
		/*
		endless = new Record[10];
		scoreAttack = new Record[10];
		stageClear = new Record[10];
		*/
		for(int i = 0; i < 10; i++){
			endless[i] = null;
			scoreAttack[i] = null;
			stageClear[i] = null;
		}
		String fileName;
		if(dir.exists() && dir.isDirectory()){
			list = dir.listFiles();
			for(int i = 0; i < list.length; i++){
				fileName = list[i].getName();
				if(fileName.endsWith(".score")){
					readFile(fileName);
					if(eRecord != null){
						for(int j = 0; j < endless.length; j++){
							if(endless[j] == null){
								endless[j] = eRecord;
								break;
							}else{
								if(eRecord.haveHighScoreThan(endless[j])){
									for(int k = endless.length-1; k >= j; k--){
										if(endless[k] == null) continue;
										if(k == endless.length-1){
											endless[k] = null;
										}else{
											endless[k+1] = endless[k];
											endless[k] = null;
										}
									}
									endless[j] = eRecord;
									break;
								}
							}
						}
					}
					if(scRecord != null){
						for(int j = 0; j < scoreAttack.length; j++){
							if(scoreAttack[j] == null){
								scoreAttack[j] = scRecord;
								break;
							}else{
								if(scRecord.haveHighScoreThan(scoreAttack[j])){
									for(int k = scoreAttack.length-1; k >= j; k--){
										if(scoreAttack[k] == null) continue;
										if(k == scoreAttack.length-1){
											scoreAttack[k] = null;
										}else{
											scoreAttack[k+1] = scoreAttack[k];
											scoreAttack[k] = null;
										}
									}
									scoreAttack[j] = scRecord;
									break;
								}
							}
						}
					}
					if(stRecord != null){
						for(int j = 0; j < stageClear.length; j++){
							if(stageClear[j] == null){
								stageClear[j] = stRecord;
								break;
							}else{
								if(stRecord.haveShorterTimeThan(stageClear[j])){
									for(int k = stageClear.length-1; k >= j; k--){
										if(stageClear[k] == null) continue;
										if(k == stageClear.length-1){
											stageClear[k] = null;
										}else{
											stageClear[k+1] = stageClear[k];
											stageClear[k] = null;
										}
									}
									stageClear[j] = stRecord;
									break;
								}
							}
						}
					}
				}
			}
		}
		return;
	}
	
	public void readFile(String name){
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
		byte[] tmp = new byte[51];
		try{
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File("./Score/"+name)));
			is.read(tmp,0,51);
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
			if(tmp[0] == 0x01){
				eRecord = new Record(name.substring(0,name.length()-6),eScore,eTime,eMaxChain,eMaxDelete);
			}else{
				eRecord = null;
			}
			if(tmp[17] == 0x01){
				scRecord = new Record(name.substring(0,name.length()-6),scScore,scTime,scMaxChain,scMaxDelete);
			}else{
				scRecord = null;
			}
			if(tmp[34] == 0x01){
				stRecord = new Record(name.substring(0,name.length()-6),stScore,stTime,stMaxChain,stMaxDelete);
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
	
	public void output(){
		boolean updatable = false;
		File dir = new File("./Score");
		if(!dir.exists()){
			dir.mkdir();
		}
		JFrame frame = new JFrame();
		String name = JOptionPane.showInputDialog(frame, "–¼‘O‚ð“ü—Í‚µ‚Ä‚­‚¾‚³‚¢");
		if(name != null){
			name += ".score";
			if(new File("./Score/"+name).exists()){
				readFile(name);
				int tmpScore;
				int tmpTime;
				int tmpMaxChain;
				int tmpMaxDelete;
				switch(Data.gameStatus){
				case Data.ENDLESS:
					if(eRecord == null || eRecord.getScore() < Data.score){
						try {
							BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File("./Score/"+name)));
							os.write(0x01);
							for(int i = 0; i < 4; i++){
								os.write((Data.score & (0xff<<i*8)) >> i*8);
								os.write((Data.time & (0xff<<i*8)) >> i*8);
								os.write((Data.maxChain & (0xff<<i*8)) >> i*8);
								os.write((Data.maxDelete & (0xff<<i*8)) >> i*8);
							}
							if(scRecord != null){
								os.write(0x01);
								tmpScore = scRecord.getScore();
								tmpTime = scRecord.getTime();
								tmpMaxChain = scRecord.getMaxChain();
								tmpMaxDelete = scRecord.getMaxDelete();
							}else{
								os.write(0x00);
								tmpScore = 0;
								tmpTime = 0;
								tmpMaxChain = 0;
								tmpMaxDelete = 0;
							}
							for(int i = 0; i < 4; i++){
								os.write((tmpScore & (0xff<<i*8)) >> i*8);
								os.write((tmpTime & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxChain & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxDelete & (0xff<<i*8)) >> i*8);
							}							
							if(stRecord != null){
								os.write(0x01);
								tmpScore = stRecord.getScore();
								tmpTime = stRecord.getTime();
								tmpMaxChain = stRecord.getMaxChain();
								tmpMaxDelete = stRecord.getMaxDelete();
							}else{
								os.write(0x00);
								tmpScore = 0;
								tmpTime = 0;
								tmpMaxChain = 0;
								tmpMaxDelete = 0;
							}
							for(int i = 0; i < 4; i++){
								os.write((tmpScore & (0xff<<i*8)) >> i*8);
								os.write((tmpTime & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxChain & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxDelete & (0xff<<i*8)) >> i*8);
							}
							os.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
					break;
				case Data.SCORE_ATTACK:
					if(scRecord == null || scRecord.getScore() < Data.score){
						try {
							BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File("./Score/"+name)));
							if(eRecord != null){
								os.write(0x01);
								tmpScore = eRecord.getScore();
								tmpTime = eRecord.getTime();
								tmpMaxChain = eRecord.getMaxChain();
								tmpMaxDelete = eRecord.getMaxDelete();
							}else{
								os.write(0x00);
								tmpScore = 0;
								tmpTime = 0;
								tmpMaxChain = 0;
								tmpMaxDelete = 0;
							}
							for(int i = 0; i < 4; i++){
								os.write((tmpScore & (0xff<<i*8)) >> i*8);
								os.write((tmpTime & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxChain & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxDelete & (0xff<<i*8)) >> i*8);
							}
							os.write(0x01);
							for(int i = 0; i < 4; i++){
								os.write((Data.score & (0xff<<i*8)) >> i*8);
								os.write((Data.time & (0xff<<i*8)) >> i*8);
								os.write((Data.maxChain & (0xff<<i*8)) >> i*8);
								os.write((Data.maxDelete & (0xff<<i*8)) >> i*8);
							}		
							if(stRecord != null){
								os.write(0x01);
								tmpScore = stRecord.getScore();
								tmpTime = stRecord.getTime();
								tmpMaxChain = stRecord.getMaxChain();
								tmpMaxDelete = stRecord.getMaxDelete();
							}else{
								os.write(0x00);
								tmpScore = 0;
								tmpTime = 0;
								tmpMaxChain = 0;
								tmpMaxDelete = 0;
							}
							for(int i = 0; i < 4; i++){
								os.write((tmpScore & (0xff<<i*8)) >> i*8);
								os.write((tmpTime & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxChain & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxDelete & (0xff<<i*8)) >> i*8);
							}
							os.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
					break;
				case Data.STAGE_CLEAR:
					if(stRecord == null || stRecord.getTime() > Data.time){
						try {
							BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File("./Score/"+name)));
							if(eRecord != null){
								os.write(0x01);
								tmpScore = eRecord.getScore();
								tmpTime = eRecord.getTime();
								tmpMaxChain = eRecord.getMaxChain();
								tmpMaxDelete = eRecord.getMaxDelete();
							}else{
								os.write(0x00);
								tmpScore = 0;
								tmpTime = 0;
								tmpMaxChain = 0;
								tmpMaxDelete = 0;
							}
							for(int i = 0; i < 4; i++){
								os.write((tmpScore & (0xff<<i*8)) >> i*8);
								os.write((tmpTime & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxChain & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxDelete & (0xff<<i*8)) >> i*8);
							}
							if(scRecord != null){
								os.write(0x01);
								tmpScore = scRecord.getScore();
								tmpTime = scRecord.getTime();
								tmpMaxChain = scRecord.getMaxChain();
								tmpMaxDelete = scRecord.getMaxDelete();
							}else{
								os.write(0x00);
								tmpScore = 0;
								tmpTime = 0;
								tmpMaxChain = 0;
								tmpMaxDelete = 0;
							}
							for(int i = 0; i < 4; i++){
								os.write((tmpScore & (0xff<<i*8)) >> i*8);
								os.write((tmpTime & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxChain & (0xff<<i*8)) >> i*8);
								os.write((tmpMaxDelete & (0xff<<i*8)) >> i*8);
							}
							os.write(0x01);
							for(int i = 0; i < 4; i++){
								os.write((Data.score & (0xff<<i*8)) >> i*8);
								os.write((Data.time & (0xff<<i*8)) >> i*8);
								os.write((Data.maxChain & (0xff<<i*8)) >> i*8);
								os.write((Data.maxDelete & (0xff<<i*8)) >> i*8);
							}
							os.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
					break;
				}
			}else{
				switch(Data.gameStatus){
				case Data.ENDLESS:
					try {
						BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File("./Score/"+name)));
						os.write(0x01);
						for(int i = 0; i < 4; i++){
							os.write((Data.score & (0xff<<i*8)) >> i*8);
							os.write((Data.time & (0xff<<i*8)) >> i*8);
							os.write((Data.maxChain & (0xff<<i*8)) >> i*8);
							os.write((Data.maxDelete & (0xff<<i*8)) >> i*8);
						}
						for(int i = 0; i < 34; i++){
							os.write(0x00);
						}
						os.close();
					}catch(IOException e){
						e.printStackTrace();
					}
					break;
				case Data.SCORE_ATTACK:
					try {
						BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File("./Score/"+name)));
						for(int i = 0; i < 17; i++){
							os.write(0x00);
						}
						os.write(0x01);
						for(int i = 0; i < 4; i++){
							os.write((Data.score & (0xff<<i*8)) >> i*8);
							os.write((Data.time & (0xff<<i*8)) >> i*8);
							os.write((Data.maxChain & (0xff<<i*8)) >> i*8);
							os.write((Data.maxDelete & (0xff<<i*8)) >> i*8);
						}
						for(int i = 0; i < 17; i++){
							os.write(0x00);
						}
						os.close();
					}catch(IOException e){
						e.printStackTrace();
					}
					break;
				case Data.STAGE_CLEAR:
					try {
						BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File("./Score/"+name)));
						for(int i = 0; i < 34; i++){
							os.write(0x00);
						}
						os.write(0x01);
						for(int i = 0; i < 4; i++){
							os.write((Data.score & (0xff<<i*8)) >> i*8);
							os.write((Data.time & (0xff<<i*8)) >> i*8);
							os.write((Data.maxChain & (0xff<<i*8)) >> i*8);
							os.write((Data.maxDelete & (0xff<<i*8)) >> i*8);
						}
						os.close();
					}catch(IOException e){
						e.printStackTrace();
					}
					break;
				}
				
			}
		}
	}
	private void input(){
	}
}