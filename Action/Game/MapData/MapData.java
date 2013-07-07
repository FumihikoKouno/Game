package Game.MapData;

import Game.Sprite.*;
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
	public ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	public int[][] pass;
	public int[][] data;
	/**
	 * �R���X�g���N�^
	 * mapID��ID�����}�b�v�f�[�^�̓ǂݍ���
	 */
	public MapData(int mapID){
		load(mapID);
	}
	/**
	 * �f�[�^�ǂݍ��ݗp�֐�
	 * Map"id".dat("id"�͋�̓I�Ȑ���)��ǂݍ���
	 */
	public void load(int id){
		// �V���ȃ}�b�v�Ɉړ�����̂�spriteList�̓N���A
		spriteList.clear();
		// �t�@�C�����̎w��
		String fileName = "./Game/MapData/Map" + id + ".dat";
		String s;
		try{
			// �t�@�C�����J��
			BufferedReader file = new BufferedReader(new FileReader(new File(fileName)));
			// ��s��(�w�i�摜�̓ǂݍ���)
			s = file.readLine();
			Data.image.backgroundImage = new ImageIcon(getClass().getResource("../Image/Picture/" + s)).getImage();
			// ��s�ڂ͓ǂݔ�΂�
			file.readLine();
			// �O�s�ڂ͏d�͂̐ݒ�
			s = file.readLine();
			Data.gravity = Integer.parseInt(s);
			// �l�s�ڂ͓ǂݔ�΂�
			file.readLine();
			{
				// �܍s�ڂŃ}�b�v�̃T�C�Y��ǂݍ���
				s = file.readLine();
				String[] tmp = s.split(" ");
				col = Integer.parseInt(tmp[0]);
				row = Integer.parseInt(tmp[1]);
			}
			// �ǂݍ��񂾃T�C�Y�̔z���ݒ�
			pass = new int[row][col];
			data = new int[row][col];
			// �Z�s�ڂ͓ǂݔ�΂�
			file.readLine();
			// �`�b�v�̎�ނ̓ǂݍ���
			for(int i = 0; i < row; i++){
				s = file.readLine();
				String[] tmp = s.split(" ");
				for(int j = 0; j < col; j++){
					data[i][j] = Integer.parseInt(tmp[j]);
				}
			}
			// ��s�ǂݔ�΂�
			file.readLine();
			// �ʉߐ��̓ǂݍ���
			for(int i = 0; i < row; i++){
				s = file.readLine();
				String[] tmp = s.split(" ");
				for(int j = 0; j < col; j++){
					pass[i][j] = Integer.parseInt(tmp[j]);
				}
			}
			// ��s�ǂݔ�΂�
			file.readLine();
			// �t�@�C�������ׂēǂݏI���܂ŁA�X�v���C�g�̓ǂݍ���
			while((s = file.readLine()) != null){
				String[] tmp = s.split(" ");
				if(tmp[0].equals("Coin")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					spriteList.add(new Coin(x,y));
				}
				if(tmp[0].equals("Enemy1")){
					int x = Integer.parseInt(tmp[1]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					spriteList.add(new Enemy1(x,y));
				}
				if(tmp[0].equals("MapChange")){
					int newMapID = Integer.parseInt(tmp[1]);
					int px = Integer.parseInt(tmp[2]) * Data.CHIP_SIZE;
					int py = Integer.parseInt(tmp[3]) * Data.CHIP_SIZE;
					int x = Integer.parseInt(tmp[4]) * Data.CHIP_SIZE;
					int y = Integer.parseInt(tmp[5]) * Data.CHIP_SIZE;
					spriteList.add(new MapChange(newMapID,px,py,x,y));
				}
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}