package application.dao.interfaces;

import java.util.List;
import java.util.Set;

import application.entities.Pipe;
import application.utilclasses.Point;

public interface PipeDao {
	List<Pipe> getAllPipesAsList();

	void create(Pipe pipe);

	Set<Point> getAllPointsAsSet();

	Integer[] getArrayOfUniqueEndPoints();

	Integer[] getArrayOfUniqueStartPoints();

	List<Pipe> getAllConnectedPipesTo(int point);

	List<Point> getAllUniquePointsAsList();
}
