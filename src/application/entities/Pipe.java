package application.entities;

import javax.persistence.*;

@Entity(name = "PIPE")
@Table(name = "PIPE")
public class Pipe extends AbstractPipe {

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "startPoint")
    private int startPoint;
    @Column(name = "endPoint")
    private int endPoint;
    @Column(name = "length")
    private int length;

    public Pipe() {
    }

    public Pipe(int startPoint, int endPoint, int length) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
    }

    public Pipe(long id, int startPoint, int endPoint, int length) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
