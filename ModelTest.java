import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 */
public class ModelTest {
	//Tests the model and classes effected by the model (just player so far)
	Player p = new Player();
	Player mower = new Player();
	Player it = new Player();
	Objects iapple = new Objects(ObjectSprites.APPLE, 300, 530, false, true, 8, "compost");
	Objects isoda = new Objects(ObjectSprites.SODA, 1125, 625, false, true, 8, "recycle");
	Objects ipizza = new Objects(ObjectSprites.PIZZA, 1050, 625, false, true, 6, "trash");
	Objects iplate = new Objects(ObjectSprites.PLATE, 800, 625, false, true, 6, "trash");
	Model m = new Model(1920, 1080, 165, 165, Direction.EAST);
	Model m2 = new Model(1920, 1080, 165, 165, Direction.EAST);
	Objects[][] newitems = new Objects [][] {{iapple, isoda, ipizza, iplate}};
	Door[][] doors = new Door[][] {{new Door(220, 530, 0, -1, 1)}};
		
	Objects [][] items = new Objects [][] {{new Objects(ObjectSprites.GRASS, 0, 530, false, true, 0, null)}};
	Objects [][] itemCollision= new Objects [][] {{new Objects(ObjectSprites.GRASS, 584, 530, false, true, 0, null)}};
	Objects [][] itemCollisionWest= new Objects [][] {{new Objects(ObjectSprites.GRASS, 320, 530, false, true, 0, null)}};
	Objects [][] itemCollisionNonGrass = new Objects [][] {{new Objects(ObjectSprites.APPLE, 384, 530, false, true, 8, "compost")}};
	Door[][] doorMap = new Door[][] {{new Door(480, 607, 0, -1, 1)},
		{new Door(480, 607, 1, -1, 0)}};
		Door[][] interactableDoorMap = new Door[][] {{new Door(352, 607, 0, -1, 1)},
			{new Door(480, 607, 1, -1, 0)}};
			
			
  
	@Test
	/**
	 * this is the test method
	 * 
	 * @param: null
	 * @return: null
	 */
  
	public void test() {
		p.setXloc(352);
		p.setYloc(500);
		mower.setXloc(352);
		mower.setYloc(500);
		mower.setMow(true);
		m.setDirect(Direction.EAST);
		m.updateLocationAndDirection(p, items, false);
		p.setXloc(m.getX());
		assertEquals(530, m.getY());
		assertEquals(584, m.getX());
		assertEquals(584, p.getXloc());
		
		m.calculateHealth(items);
		assertEquals(0, m.getHealth());
		

		m.setDirect(Direction.WEST);
		m.updateLocationAndDirection(p, items, false);
		p.setXloc(m.getX());
		assertEquals(552, p.getXloc());

		m.setDirect(Direction.EAST);
		m.updateLocationAndDirection(p, itemCollision, false);
		p.setXloc(m.getX());
		assertEquals(Direction.EAST, m.getDirect());
		assertEquals(552, p.getXloc());

		m.setDirect(Direction.WEST);
		m.updateLocationAndDirection(p, itemCollisionWest, false);
		p.setXloc(m.getX());
		assertEquals(520, p.getXloc());

		m.setDirect(Direction.EAST);
		iapple.interacted = true;
		ipizza.interacted = true;
		m.updateLocationAndDirection(p, newitems, true);
		p.setXloc(m.getX());
		assertEquals(552, p.getXloc());

		m.setDirect(Direction.WEST);
		m.updateLocationAndDirection(mower, itemCollision, true);
		p.setXloc(m.getX());
		assertEquals(552, p.getXloc());

		m.setDirect(Direction.EAST);
		
		m.updateLocationAndDirection(mower, itemCollisionNonGrass, true);
		p.setXloc(m.getX());
		assertEquals(584, p.getXloc());

		m.checkDoors(p, doorMap, false);
		assertEquals(0, p.getPlayerRoom());

		m.checkDoors(p, interactableDoorMap, true);
		assertEquals(0, p.getPlayerRoom());
		
		p.playerRoom = 5;
		
		for (int i = 0; i < newitems.length; i++) {
			for (int j = 0; j < newitems[i].length; j++) {
				newitems[i][j].setSorted(true);
			}}
		m.calculateHealth(newitems);
		
		
		
		
		
		it.setPlayerRoom(0);
		it.setXloc(300);
		it.setYloc(530);
		it.setDirect(Direction.WEST);
		m2.setXLoc(1400);
		m2.updateLocationAndDirection(it, newitems, true);
		
		m2.setXLoc(-120);
		m2.setDirect(Direction.WEST);
		m2.updateLocationAndDirection(it, newitems, true);
		
		
		m2.setXLoc(300);
		m2.updateLocationAndDirection(it, newitems, true);
		m2.setDirect(Direction.EAST);
		it.setDirect(Direction.EAST);
		m2.updateLocationAndDirection(it, newitems, true);
		
		p.setXloc(0);
		p.setYloc(530);
		p.setDirect(Direction.EAST);
		p.setPlayerRoom(0);
		p.setMow(true);
		m.setXLoc(0);
		m.updateLocationAndDirection(p, items, true);
		
		
		m.setXLoc(220);
		p.setXloc(220);
		p.setPlayerRoom(0);
		p.operateDoor(0);
		assertEquals(0, p.getPlayerRoom());
		
		p.operateDoor(1);
		assertEquals(1, p.getPlayerRoom());
		
		p.setSonny(true);
		p.setMove(true);
		p.setMow(false);
		p.getImage();
		assertEquals(4, p.getFrameCount());
		
		p.setSonny(true);
		p.setMove(false);
		p.setMow(true);
		p.getImage();
		assertEquals(1, p.getFrameCount());
		
		p.setSonny(true);
		p.setMove(false);
		p.setMow(false);
		p.getImage();
		assertEquals(1, p.getFrameCount());
		
		p.setSonny(false);
		p.setMove(true);
		p.setMow(false);
		p.getImage();
		assertEquals(4, p.getFrameCount());
		
		p.setSonny(false);
		p.setMove(false);
		p.setMow(true);
		p.getImage();
		assertEquals(1, p.getFrameCount());
		
		p.setSonny(false);
		p.setMove(false);
		p.setMow(false);
		p.getImage();
		assertEquals(1, p.getFrameCount());
		
		p.changeEquip(1);
		assertEquals(false, p.isMow());
		p.changeEquip(2);
		assertEquals(true, p.isMow());
		p.changeEquip(3);
		assertEquals(true, p.isGrass());
		
		
		//m.checkDoors(p, doors, true);
		//assertTrue(newitems[0][0].checkCollision(p, 308));
	}
}
