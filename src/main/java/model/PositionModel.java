package model;

import GameLogic.Position;

import java.io.Serializable;
import java.util.Objects;

public class PositionModel implements Serializable {
    public int x,y;
    public PositionModel(int x, int y){
        this.x = x;
        this.y = y;
    }
    public PositionModel(){
        this.x = 0;
        this.y = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return this.x == position.x &&
                this.y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
