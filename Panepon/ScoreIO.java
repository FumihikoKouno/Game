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
	
	public ScoreIO(){}
	
	private void statusPrint(String fileName){
		System.out.println(fileName);
		System.out.println("  status:");
		System.out.println("    eRecord " + eRecord);
		System.out.println("    scRecord " + scRecord);
		System.out.println("    stRecord " + stRecord);
	}
	
	public void makeRanking(Record[] endless, Record[] scoreAttack, Record[] stageClear, boolean[] replayStatus){
		File dir = new File("./Score");
		File[] list;
		String name;
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

    public void setReplayData(int mode, long s, int[] sf, int[] wf, int[] x, int[] y){
	switch(mode){
	case Data.ENDLESS:
	    s = eSeed;
	    sf = eScrollFrame;
	    wf = eSwapFrame;
	    x = eSwapX;
	    y = eSwapY;
	    break;
	case Data.SCORE_ATTACK:
	    s = scSeed;
	    sf = scScrollFrame;
	    wf = scSwapFrame;
	    x = scSwapX;
	    y = scSwapY;
	    break;
	case Data.STAGE_CLEAR:
	    s = stSeed;
	    sf = stScrollFrame;
	    wf = stSwapFrame;
	    x = stSwapX;
	    y = stSwapY;
	    break;
	}
    }

    public void readReplayData(String name){
	try{
	    byte[] tmp = new byte[51];
	    BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File("./Score/"+name+".score")));
	    is.read(tmp,0,51);
	    readReplayData(is);
	    is.close();
	}catch(FileNotFoundException e){
	    e.printStackTrace();
	}catch(IOException e){
	    e.printStackTrace();
	}
    }

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
	    if(tmp[0] == 0x01){
		replayE = true;
	    }else{
		replayE = false;
	    }
	    if(tmp[1] == 0x01){
		replaySC = true;
	    }else{
		replaySC = false;
	    }
	    if(tmp[2] == 0x01){
		replayST = true;
	    }else{
		replayST = false;
	    }
	    if(replayE){
		is.read(tmp,0,8);
		eSeed = 0;
		for(int i = 0; i < 8; i++){
		    eSeed += ( (tmp[i]&0xff) << (8*i) );
		}
		is.read(tmp,0,4);
		int count = 0;
		for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
		eScrollFrame = new int[count];
		for(int i = 0; i < count; i++){
		    eScrollFrame[i] = 0;
		    is.read(tmp,0,4);
		    for(int j = 0; j < 4; j++) eScrollFrame[i] += ((tmp[j] & 0xff) << (8*j));
		}
		count = 0;
		is.read(tmp,0,4);
		for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
		eSwapFrame = new int[count];
		eSwapX = new int[count];
		eSwapY = new int[count];
		for(int i = 0; i < count; i++){
		    is.read(tmp,0,6);
		    eSwapFrame[i] = 0;
		    for(int j = 0; j < 4; j++) eSwapFrame[i] += ((tmp[j]&0xff) << (8*j));
		    eSwapX[i] = tmp[4] & 0xff;
		    eSwapY[i] = tmp[5] & 0xff;
		}
	    }
	    if(replaySC){
		is.read(tmp,0,8);
		scSeed = 0;
		for(int i = 0; i < 8; i++){
		    scSeed += ( (tmp[i]&0xff) << (8*i) );
		}
		is.read(tmp,0,4);
		int count = 0;
		for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
		scScrollFrame = new int[count];
		for(int i = 0; i < count; i++){
		    scScrollFrame[i] = 0;
		    is.read(tmp,0,4);
		    for(int j = 0; j < 4; j++) scScrollFrame[i] += ((tmp[j] & 0xff) << (8*j));
		}
		count = 0;
		is.read(tmp,0,4);
		for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
		scSwapFrame = new int[count];
		scSwapX = new int[count];
		scSwapY = new int[count];
		for(int i = 0; i < count; i++){
		    is.read(tmp,0,6);
		    scSwapFrame[i] = 0;
		    for(int j = 0; j < 4; j++) scSwapFrame[i] += ((tmp[j]&0xff) << (8*j));
		    scSwapX[i] = tmp[4] & 0xff;
		    scSwapY[i] = tmp[5] & 0xff;
		}
	    }
	    if(replayST){
		is.read(tmp,0,8);
		stSeed = 0;
		for(int i = 0; i < 8; i++){
		    stSeed += ( (tmp[i]&0xff) << (8*i) );
		}
		is.read(tmp,0,4);
		int count = 0;
		for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
		stScrollFrame = new int[count];
		for(int i = 0; i < count; i++){
		    stScrollFrame[i] = 0;
		    is.read(tmp,0,4);
		    for(int j = 0; j < 4; j++) stScrollFrame[i] += ((tmp[j] & 0xff) << (8*j));
		}
		count = 0;
		is.read(tmp,0,4);
		for(int i = 0; i < 4; i++) count += ( (tmp[i] & 0xff) << (8*i));
		stSwapFrame = new int[count];
		stSwapX = new int[count];
		stSwapY = new int[count];
		for(int i = 0; i < count; i++){
		    is.read(tmp,0,6);
		    stSwapFrame[i] = 0;
		    for(int j = 0; j < 4; j++) stSwapFrame[i] += ((tmp[j]&0xff) << (8*j));
		    stSwapX[i] = tmp[4] & 0xff;
		    stSwapY[i] = tmp[5] & 0xff;
		}
	    }
	}catch(IOException e){
	    e.printStackTrace();
	}
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
		int prevData;
		byte[] tmp = new byte[51];
		try{
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File("./Score/"+name)));
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

			if(tmp[0] == 0x01){
				eRecord = new Record(name.substring(0,name.length()-6),eScore,eTime,eMaxChain,eMaxDelete,replayE);
			}else{
				eRecord = null;
			}
			if(tmp[17] == 0x01){
				scRecord = new Record(name.substring(0,name.length()-6),scScore,scTime,scMaxChain,scMaxDelete,replaySC);
			}else{
				scRecord = null;
			}
			if(tmp[34] == 0x01){
				stRecord = new Record(name.substring(0,name.length()-6),stScore,stTime,stMaxChain,stMaxDelete,replayST);
			}else{
				stRecord = null;
			}
			//			statusPrint(name);
			
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
	    String name = "";
	    while(name != null && name.equals("")) name = JOptionPane.showInputDialog(frame, "–¼‘O‚ð“ü—Í‚µ‚Ä‚­‚¾‚³‚¢");
	    if(name != null){
		name += ".score";
		if(new File("./Score/"+name).exists()){
		    readFile(name);
		    boolean update = false;
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

				os.write(0x01);
				if(replaySC) os.write(0x01);
				else os.write(0x00);
				if(replayST) os.write(0x01);
				else os.write(0x00);
				for(int i = 0; i < 8; i++){
				    os.write((int)((Data.seed & (0xff << i*8)) >> i*8));
				}
				int len = Data.replayScrollFrame.size();
				for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
				for(int i = 0; i < len; i++){
				    int scrollFrameTmp = Data.replayScrollFrame.get(i);
				    for(int j = 0; j < 4; j++){
					os.write((scrollFrameTmp & (0xff << j*8)) >> j*8);
				    }
				}
				len = Data.replaySwapFrame.size();
				for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                for(int i = 0; i < len; i++){
                                    int swapFrameTmp = Data.replaySwapFrame.get(i);
                                    for(int j = 0; j < 4; j++){
                                        os.write((swapFrameTmp & (0xff << j*8)) >> j*8);
                                    }
				    os.write(Data.replaySwapX.get(i) & 0xff);
				    os.write(Data.replaySwapY.get(i) & 0xff);
                                }
				if(replaySC){
				    for(int i = 0; i < 8; i++){
					os.write((int)((scSeed & (0xff << i*8)) >> i*8));
				    }
				    len = scScrollFrame.length;
				    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
				    for(int i = 0; i < len; i++){
					for(int j = 0; j < 4; j++){
					    os.write((scScrollFrame[i] & (0xff << j*8)) >> j*8);
					}
				    }
				    len = scSwapFrame.length;
				    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
				    for(int i = 0; i < len; i++){
					for(int j = 0; j < 4; j++){
					    os.write((scSwapFrame[i] & (0xff << j*8)) >> j*8);
					}
					os.write(scSwapX[i] & 0xff);
					os.write(scSwapY[i] & 0xff);
				    }
				}
				if(replayST){
                                    for(int i = 0; i < 8; i++){
                                        os.write((int)((stSeed & (0xff << i*8)) >> i*8));
                                    }
                                    len = stScrollFrame.length;
				    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((stScrollFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                    }
                                    len = stSwapFrame.length;
				    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((stSwapFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                        os.write(stSwapX[i] & 0xff);
                                        os.write(stSwapY[i] & 0xff);
                                    }

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

                                if(replayE) os.write(0x01);
				else os.write(0x00);
				os.write(0x01);
                                if(replayST) os.write(0x01);
                                else os.write(0x00);

                                if(replayE){
                                    for(int i = 0; i < 8; i++){
                                        os.write((int)((eSeed & (0xff << i*8)) >> i*8));
                                    }
                                    int len = eScrollFrame.length;
				    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((eScrollFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                    }
                                    len = eSwapFrame.length;
				    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((eSwapFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                        os.write(eSwapX[i] & 0xff);
                                        os.write(eSwapY[i] & 0xff);
                                    }
                                }
                                    
                                for(int i = 0; i < 8; i++){
                                    os.write((int)((Data.seed & (0xff << i*8)) >> i*8));
                                }
                                int len = Data.replayScrollFrame.size();
                                for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                for(int i = 0; i < len; i++){
                                    int scrollFrameTmp = Data.replayScrollFrame.get(i);
                                    for(int j = 0; j < 4; j++){
                                        os.write((scrollFrameTmp & (0xff << j*8)) >> j*8);
                                    }
                                }
                                len = Data.replaySwapFrame.size();
                                for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                for(int i = 0; i < len; i++){
                                    int swapFrameTmp = Data.replaySwapFrame.get(i);
                                    for(int j = 0; j < 4; j++){
                                        os.write((swapFrameTmp & (0xff << j*8)) >> j*8);
                                    }
                                    os.write(Data.replaySwapX.get(i) & 0xff);
                                    os.write(Data.replaySwapY.get(i) & 0xff);
                                }

                                if(replayST){
                                    for(int i = 0; i < 8; i++){
                                        os.write((int)((stSeed & (0xff << i*8)) >> i*8));
                                    }
                                    len = stScrollFrame.length;
                                    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((stScrollFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                    }
                                    len = stSwapFrame.length;
                                    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((stSwapFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                        os.write(stSwapX[i] & 0xff);
                                        os.write(stSwapY[i] & 0xff);
                                    }

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

                                if(replayE) os.write(0x01);
                                else os.write(0x00);
                                if(replaySC) os.write(0x01);
                                else os.write(0x00);
                                os.write(0x01);

                                if(replayE){
                                    for(int i = 0; i < 8; i++){
                                        os.write((int)((eSeed & (0xff << i*8)) >> i*8));
                                    }
                                    int len = eScrollFrame.length;
                                    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((eScrollFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                    }
                                    len = eSwapFrame.length;
                                    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((eSwapFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                        os.write(eSwapX[i] & 0xff);
                                        os.write(eSwapY[i] & 0xff);
                                    }
                                }

                                if(replaySC){
                                    for(int i = 0; i < 8; i++){
                                        os.write((int)((scSeed & (0xff << i*8)) >> i*8));
                                    }
                                    int len = scScrollFrame.length;
                                    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((scScrollFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                    }
                                    len = scSwapFrame.length;
                                    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                    for(int i = 0; i < len; i++){
                                        for(int j = 0; j < 4; j++){
                                            os.write((scSwapFrame[i] & (0xff << j*8)) >> j*8);
                                        }
                                        os.write(scSwapX[i] & 0xff);
                                        os.write(scSwapY[i] & 0xff);
                                    }
                                }

                                for(int i = 0; i < 8; i++){
                                    os.write((int)((Data.seed & (0xff << i*8)) >> i*8));
                                }
                                int len = Data.replayScrollFrame.size();
                                for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
				for(int i = 0; i < len; i++){
                                    int scrollFrameTmp = Data.replayScrollFrame.get(i);
                                    for(int j = 0; j < 4; j++){
                                        os.write((scrollFrameTmp & (0xff << j*8)) >> j*8);
                                    }
                                }
                                len = Data.replaySwapFrame.size();
                                for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
                                for(int i = 0; i < len; i++){
                                    int swapFrameTmp = Data.replaySwapFrame.get(i);
                                    for(int j = 0; j < 4; j++){
                                        os.write((swapFrameTmp & (0xff << j*8)) >> j*8);
                                    }
                                    os.write(Data.replaySwapX.get(i) & 0xff);
                                    os.write(Data.replaySwapY.get(i) & 0xff);
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
			    os.write(0x01);
			    os.write(0x00);
			    os.write(0x00);
			    for(int i = 0; i < 8; i++){
				os.write((int)((Data.seed & (0xff << i*8)) >> i*8));
			    }
			    int len = Data.replayScrollFrame.size();
			    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			    for(int i = 0; i < len; i++){
				int scrollFrameTmp = Data.replayScrollFrame.get(i);
				for(int j = 0; j < 4; j++){
				    os.write((scrollFrameTmp & (0xff << j*8)) >> j*8);
				}
			    }
			    len = Data.replaySwapFrame.size();
			    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			    for(int i = 0; i < len; i++){
				int swapFrameTmp = Data.replaySwapFrame.get(i);
				for(int j = 0; j < 4; j++){
				    os.write((swapFrameTmp & (0xff << j*8)) >> j*8);
				}
				os.write(Data.replaySwapX.get(i) & 0xff);
				os.write(Data.replaySwapY.get(i) & 0xff);
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

			    os.write(0x00);
			    os.write(0x01);
			    os.write(0x00);

			    for(int i = 0; i < 8; i++){
				os.write((int)((Data.seed & (0xff << i*8)) >> i*8));
			    }
			    int len = Data.replayScrollFrame.size();
			    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			    for(int i = 0; i < len; i++){
				int scrollFrameTmp = Data.replayScrollFrame.get(i);
				for(int j = 0; j < 4; j++){
				    os.write((scrollFrameTmp & (0xff << j*8)) >> j*8);
				}
			    }
			    len = Data.replaySwapFrame.size();
			    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			    for(int i = 0; i < len; i++){
				int swapFrameTmp = Data.replaySwapFrame.get(i);
				for(int j = 0; j < 4; j++){
				    os.write((swapFrameTmp & (0xff << j*8)) >> j*8);
				}
				os.write(Data.replaySwapX.get(i) & 0xff);
				os.write(Data.replaySwapY.get(i) & 0xff);
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
			    for(int i = 0; i < 8; i++){
				os.write((int)((Data.seed & (0xff << i*8)) >> i*8));
			    }
			    os.write(0x00);
			    os.write(0x00);
			    os.write(0x01);
			    int len = Data.replayScrollFrame.size();
			    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			    for(int i = 0; i < len; i++){
				int scrollFrameTmp = Data.replayScrollFrame.get(i);
				for(int j = 0; j < 4; j++){
				    os.write((scrollFrameTmp & (0xff << j*8)) >> j*8);
				}
			    }
			    len = Data.replaySwapFrame.size();
			    for(int i = 0; i < 4; i++) os.write((len & (0xff << i*8)) >> i*8);
			    for(int i = 0; i < len; i++){
				int swapFrameTmp = Data.replaySwapFrame.get(i);
				for(int j = 0; j < 4; j++){
				    os.write((swapFrameTmp & (0xff << j*8)) >> j*8);
				}
				os.write(Data.replaySwapX.get(i) & 0xff);
				os.write(Data.replaySwapY.get(i) & 0xff);
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