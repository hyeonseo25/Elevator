package components;

public class Elevator {
	private int floor;
	private boolean open;
	private int openSecond;
	private int status; // 0-아래로 이동중, 1- 위로 이동중
	private boolean moving;
	private boolean check; // 방향 바꾸기 위한 변수
	private int[][] outButtonChecked = new int[2][15];
	private int[] inButtonChecked = new int[15];
	private int destination;
	
	public Elevator() {
		floor = 1;
		moving=false;
		destination = 0;
		runElevator();
	}
	
	public void runElevator() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						
						if(!open) {
							if(destination==0) {
								moving=false;
								makeDestination();
							}else if(!isMoving()) {
								moveElevator();
							}
						}
						Thread.sleep(40);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public int getOpenSecond() {
		return openSecond;
	}

	public void setOpenSecond(int openSecond) {
		this.openSecond = openSecond;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int[][] getOutButtonChecked() {
		return outButtonChecked;
	}

	public void setOutButtonChecked(int[][] outButtonChecked) {
		this.outButtonChecked = outButtonChecked;
	}

	public int[] getInButtonChecked() {
		return inButtonChecked;
	}

	public void setInButtonChecked(int[] inButtonChecked) {
		this.inButtonChecked = inButtonChecked;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setDestination(int floor) {
		if(status==0) {
			if(destination>floor) {
				destination = floor;
			}
		}else if(status == 1) {
			if(destination<floor) {
				destination = floor;
			}
		}else if(destination==0){
			destination = floor;
		}
	}
	public int getDestination() {
		return destination;
	}
	public int makeDestination() {
		if(!isOpen()) {
			if(status==0) {
				if(checkUpFloor() == 1) { 
					return 0;
				}else {
					if(checkDownFloor() == 1) {
						return 0;
					}
				}
			}else if(status == 1) {
				if(checkDownFloor() == 1) {
					return 0;
				}else {
					if(checkUpFloor() == 1) {
						return 0;
					}
				}
			}
		}
		
		return 0;
	}
	
	// 현재 층 위의 층 체크
	public int checkUpFloor() {
		for(int i=inButtonChecked.length-1; i>floor-1; i--) {
			if(inButtonChecked[i]==1 || outButtonChecked[0][i]==1 || outButtonChecked[1][i]==1) {
				destination=i+1;
				return 1;
			}
			
		}
		return 0;
	}
	
	public int checkDownFloor() {
		for(int i=0; i<floor-1; i++) {
			if(inButtonChecked[i]==1 || outButtonChecked[0][i]==1 || outButtonChecked[1][i]==1) {
				destination=i+1;
				return 1;
			}
		}
		return 0;
	}

	public void moveElevator() {
		if(destination>getFloor()) {
			status=1;
		}else {
			status=0;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(getFloor()!=destination) {
					try {
						moving=true;
						if(!isOpen()) {
							Thread.sleep(1200);
							if(destination>getFloor()) {
								setFloor(getFloor()+1);
							}else {
								setFloor(getFloor()-1);
							}
							stopElevator();
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				destination=0;
				moving=false;
			}
		}).start();
		
	}
	
	public void stopElevator() {
		if(inButtonChecked[floor-1]==1||outButtonChecked[status][floor-1]==1) {
			inButtonChecked[floor-1]=0;
			outButtonChecked[status][floor-1] = 0;
			openDoor();
		}else if(check==true) {
			if(floor==destination) {
				inButtonChecked[floor-1]=0;
				outButtonChecked[(int)Math.pow(status-1, 2)][floor-1] = 0;
				openDoor();
			}
		}
	}
	
	public void openDoor() {
		System.out.println("문이 열립니다.");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					setOpen(true);
					setOpenSecond(3);
					while(openSecond!=0) {
						System.out.println(getOpenSecond()+"초");
						Thread.sleep(1000);
						setOpenSecond(getOpenSecond()-1);
					}
					setOpen(false);
					System.out.println("문이 닫힙니다.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
