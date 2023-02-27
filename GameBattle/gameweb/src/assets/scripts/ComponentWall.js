import {ComponentObject} from "@/assets/scripts/ComponentObject";

export class ComponentWall extends ComponentObject{

    constructor(row,col,gameMap) {
        super();
        this.row = row;
        this.col = col;

        this.gameMap = gameMap;
        this.color = "#B37226";

    }

    update() {
        this.render();
    }

    render(){
        const L = this.gameMap.L;
        const ctx = this.gameMap.ctx;

        ctx.fillStyle = this.color;
        ctx.fillRect(this.col * L, this.row * L,L,L);
    }

}