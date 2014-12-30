package simulation;

import java.awt.Point;

public class AnimationThread extends Thread implements Runnable {

	private WorldFrame worldFrame;
	private boolean stop;
	private Model[][] cloneSpace;

	public AnimationThread(WorldFrame worldFrame) {
		this.setWorldFrame(worldFrame);
		cloneSpace = cloneModel();
	}

	public void stopAnimation() {
		stop = true;
	}

	private Model[][] cloneModel() {
		Model[][] myInt = new Model[worldFrame.space.length][];
		for (int i = 0; i < worldFrame.space.length; i++) {
			Model[] aMatrix = worldFrame.space[i];
			int aLength = aMatrix.length;
			myInt[i] = new Model[aLength];
			for (int j = 0; j < aLength; j++) {
				Model source = worldFrame.space[i][j];
				Model target = new Model(new Point(source.getPoint().x,
						source.getPoint().y), worldFrame);
				target.setClone(true);
				target.setAlive(source.isAlive());
				myInt[i][j] = target;
			}
			// System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
		}

		return myInt;
	}

	public void run() {

		while (!stop) {
			cloneSpace = cloneModel();
			for (int column = 0; column < worldFrame.getColumns(); column++) {
				for (int row = 0; row < worldFrame.getRows(); row++) {
					Model model = worldFrame.space[column][row];
					switch (getNumAliveNeighbor(column, row)) {
					case 0:
					case 1:
					case 5:
					case 6:
					case 7:
					case 8:
					case 4:
						model.setAlive(false);
						break;
					// case 4:
					case 3:
						model.setAlive(true);
						break;

					default:
						break;
					}
				}
			}
			try {
				Thread.sleep(worldFrame.getSpeed());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static int getNumAliveNeighbor(int column, int row,
			WorldFrame worldFrame) {
		return new AnimationThread(worldFrame).getNumAliveNeighbor(column, row);
	}

	private int getNumAliveNeighbor(int column, int row) {
		int count = 0;
		Model northWest = getNorthWest(column, row);
		Model north = getNorth(column, row);
		Model northEast = getNorthEast(column, row);
		Model east = getEast(column, row);
		Model southEast = getSouthEast(column, row);
		Model south = getSouth(column, row);
		Model southWest = getSouthWest(column, row);
		Model west = getWest(column, row);
		if (northWest != null && northWest.isAlive()) {
			count++;
		}
		if (north != null && north.isAlive()) {
			count++;
		}
		if (northEast != null && northEast.isAlive()) {
			count++;
		}
		if (east != null && east.isAlive()) {
			count++;
		}
		if (southEast != null && southEast.isAlive()) {
			count++;
		}
		if (south != null && south.isAlive()) {
			count++;
		}
		if (southWest != null && southWest.isAlive()) {
			count++;
		}
		if (west != null && west.isAlive()) {
			count++;
		}

		return count;
	}

	private Model getNorthWest(int column, int row) {
		if (row > 0 && column > 0) {
			return cloneSpace[column - 1][row - 1];
		}
		return null;
	}

	private Model getNorth(int column, int row) {
		if (row > 0) {
			return cloneSpace[column][row - 1];
		}
		return null;
	}

	private Model getNorthEast(int column, int row) {
		if (row > 0 && column < worldFrame.getColumns() - 1) {
			return cloneSpace[column + 1][row - 1];
		}
		return null;
	}

	private Model getEast(int column, int row) {
		if (column < worldFrame.getColumns() - 1) {
			return cloneSpace[column + 1][row];
		}
		return null;
	}

	private Model getSouthEast(int column, int row) {
		if (row < worldFrame.getRows() - 1
				&& column < worldFrame.getColumns() - 1) {
			return cloneSpace[column + 1][row + 1];
		}
		return null;
	}

	private Model getSouth(int column, int row) {
		if (row < worldFrame.getRows() - 1) {
			return cloneSpace[column][row + 1];
		}
		return null;
	}

	private Model getSouthWest(int column, int row) {
		if (row < worldFrame.getRows() - 1 && column > 0) {
			return cloneSpace[column - 1][row + 1];
		}
		return null;
	}

	private Model getWest(int column, int row) {
		if (column > 0) {
			return cloneSpace[column - 1][row];
		}
		return null;
	}

	public WorldFrame getWorldFrame() {
		return worldFrame;
	}

	public void setWorldFrame(WorldFrame worldFrame) {
		this.worldFrame = worldFrame;
	}

}
