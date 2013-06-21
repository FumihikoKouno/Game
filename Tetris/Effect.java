class Effect extends Thread implements Common{
	Field field;
	Block current;
	int effectType;
	public Effect(int effectType, Block current, Field field){
		this.effectType = effectType;
		this.current = current;
		this.field = field;
	}
	public void run(){
		field.setEffect(true);
		switch(effectType){
		case BLOCK_SET:
			current.setShowType(BLOCK_SET);
			try{
				Thread.sleep(150);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			current.setShowType(BLOCK_NORMAL);
			break;
		case BLOCK_DELETE:
			for(int i = 0; i < 2; i++){
				field.setTurn(true,false);
				try{
					Thread.sleep(150);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				field.setTurn(false,false);
				try{
					Thread.sleep(150);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			field.setTurn(true,false);
			try{
				Thread.sleep(400);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			field.setTurn(false,true);
			break;
		}
		field.setEffect(false);
	}
}