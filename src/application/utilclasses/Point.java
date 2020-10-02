package application.utilclasses;

import java.util.ArrayList;
import java.util.List;

import application.dao.daoimpl.PipeDaoImpl;
import application.dao.interfaces.PipeDao;
import application.entities.Pipe;

public class Point {

	private int pointName;
	private List<Pipe> pipes = new ArrayList<>();
	private boolean visited = false;
	private int distanceFromSource = Integer.MAX_VALUE / 2;

	public Point() {
	}

	public Point(int pointName) {
		this.pointName = pointName;
	}

	public void initializeConnectedPipes() {
		PipeDao pipeDao = new PipeDaoImpl();
		this.pipes = pipeDao.getPipesWhichStartFromPoint(this.pointName);
	}

	public int getPointName() {
		return pointName;
	}

	public void setPointName(int pointName) {
		this.pointName = pointName;
	}

	public List<Pipe> getPipes() {
		return pipes;
	}

	public void setPipes(List<Pipe> pipes) {
		this.pipes = pipes;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getDistanceFromSource() {
		return distanceFromSource;
	}

	public void setDistanceFromSource(int distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
	}
}
