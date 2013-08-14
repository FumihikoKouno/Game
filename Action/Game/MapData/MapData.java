package Game.MapData;

import Game.Sprite.*;
import Game.Sprite.Item.*;
import Game.Sprite.Enemy.*;
import Game.Common.Data;

import java.awt.Image;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;

public class MapData{
	public int col;
	public int row;
	public ArrayList<Sprite> passSpriteList = new ArrayList<Sprite>();
	public ArrayList<Sprite> unpassSpriteList = new ArrayList<Sprite>();
	public int[][] pass;
	public int[][] data;
	public int id;
	/**
	 * コンストラクタ
	 * mapIDのIDを持つマップデータの読み込み
	 */
	public MapData(int mapID){
		id = mapID;
		load(id);
	}
	/**
	 * データ読み込み用関数
	 * Map"id".dat("id"は具体的な数字)を読み込む
	 */
	public void load(int id){
		this.id = id;
		// 新たなマップに移動するのでspriteListはクリア
		passSpriteList.clear();
		unpassSpriteList.clear();
		// ファイル名の指定
		String fileName = "./Game/MapData/Map" + id + ".dat";
		String s;
		try{
			// ファイルを開く
			BufferedReader file = new BufferedReader(new FileReader(new File(fileName)));
			// 一行目(背景画像の読み込み)
			s = file.readLine();
			Data.image.backgroundImage = new ImageIcon(getClass().getResource("../Image/Picture/" + s)).getImage();
			// 二行目は読み飛ばす
			file.readLine();
			// 三行目は重力の設定
			s = file.readLine();
			Data.gravity = Integer.parseInt(s);
			// 四行目は読み飛ばす
			file.readLine();
			{
				// 五行目でマップのサイズを読み込む
				s = file.readLine();
				String[] tmp = s.split(" ");
				col = Integer.parseInt(tmp[0]);
				row = Integer.parseInt(tmp[1]);
			}
			// 読み込んだサイズの配列を設定
			pass = new int[row][col];
			data = new int[row][col];
			// 六行目は読み飛ばす
			file.readLine();
			// チップの種類の読み込み
			for(int i = 0; i < row; i++){
				s = file.readLine();
				String[] tmp = s.split(" ");
				for(int j = 0; j < col; j++){
					data[i][j] = Integer.parseInt(tmp[j]);
				}
			}
			// 一行読み飛ばす
			file.readLine();
			// 通過性の読み込み
			for(int i = 0; i < row; i++){
				s = file.readLine();
				String[] tmp = s.split(" ");
				for(int j = 0; j < col; j++){
					pass[i][j] = Integer.parseInt(tmp[j]);
				}
			}
			// 一行読み飛ばす
			file.readLine();
			// ファイルをすべて読み終わるまで、スプライトの読み込み
			while((s = file.readLine()) != null){
				String[] tmp = s.split(" ");
				if(tmp[0].equals("BrokenChip")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					int weapon = Integer.parseInt(tmp[3]);
					int element = Integer.parseInt(tmp[4]);
					unpassSpriteList.add(new BrokenChip(x,y,weapon,element));
				}
				if(tmp[0].equals("Coin")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					passSpriteList.add(new Coin(x,y));
				}
				if(tmp[0].equals("RightUpSlope")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					unpassSpriteList.add(new RightUpSlope(x,y));
				}
				if(tmp[0].equals("LeftUpSlope")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					unpassSpriteList.add(new LeftUpSlope(x,y));
				}
				if(tmp[0].equals("Enemy0")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					passSpriteList.add(new Enemy0(x,y));
				}
				if(tmp[0].equals("Enemy1")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					passSpriteList.add(new Enemy1(x,y));
				}
				if(tmp[0].equals("AppearingChip")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					int start = Integer.parseInt(tmp[3]);
					int end = Integer.parseInt(tmp[4]);
					int limit = Integer.parseInt(tmp[5]);
					unpassSpriteList.add(new AppearingChip(x,y,start,end,limit));
				}
				if(tmp[0].equals("FroatingStage")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					int kind = Integer.parseInt(tmp[3]);
					int scale = Integer.parseInt(tmp[4]);
					int dir = Integer.parseInt(tmp[5]);
					unpassSpriteList.add(new FroatingStage(x,y,kind,scale,dir));
				}
				if(tmp[0].equals("MapChange")){
					int newMapID = Integer.parseInt(tmp[1]);
					int px = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					int py = Integer.parseInt(tmp[3]) * Data.CHIP_SIZE;
					int x = Integer.parseInt(tmp[4]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[5]) * Data.CHIP_SIZE;
					passSpriteList.add(new MapChange(newMapID,px,py,x,y));
				}
				if(tmp[0].equals("Needle")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					int d = Integer.parseInt(tmp[3]);
					unpassSpriteList.add(new Needle(x,y,d));
				}
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}