package GameLogic;

import java.util.Objects;

public class Position {
    public int x,y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Position(){
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
