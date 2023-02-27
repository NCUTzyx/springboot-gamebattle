export class SnakeBody{
    constructor(row,col) {
        this.row = row;
        this.col = col;
        this.x = col + 0.5;
        this.y = row + 0.5;
    }
}