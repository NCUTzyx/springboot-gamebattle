import { ComponentObject } from "@/assets/scripts/ComponentObject";
import {ComponentWall} from "@/assets/scripts/ComponentWall";
import {Snake} from "@/assets/scripts/Snake";

export class GameMap extends ComponentObject{
    constructor(ctx,parent,store) {
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0;

        this.rows = 13;
        this.cols = 14;
        //存储四周障碍物
        this.walls = [];
        //地图刷新的随机障碍物数量
        this.ranWalls_count = 15;

        this.snakes = [
            new Snake({ id:0,color:"#F94848", row:this.rows-2, col:1 },this),
            new Snake({ id:1,color:"#4876EC", row:1, col:this.cols - 2 },this),
        ];
    }

    create_barrier(){

        const temp = this.store.state.battle.gamemap;
        for(let r = 0; r < this.rows; r ++){
            for(let c = 0; c < this.cols; c ++){
                if(temp[r][c]){
                    this.walls.push(new ComponentWall(r,c,this));
                }
            }
        }

        return true;
    }

    //绑定事件监听
    events_listener(){

        if (this.store.state.review.is_review){
            let k = 0;
            const a_steps = this.store.state.review.a_steps;
            const b_steps = this.store.state.review.b_steps;
            const loser = this.store.state.review.review_loser;
            const [snake0,snake1] = this.snakes;
            const interval_id = setInterval(()=>{
                if (k >= a_steps.length - 1 ) {
                    //蛇死亡
                    if (loser === "AB" || loser === "A") {
                        snake0.status = 2;
                    }
                    if (loser === "AB" || loser === "B") {
                        snake1.status = 2;
                    }
                    clearInterval(interval_id);

                }else {
                    snake0.set_direction(parseInt(a_steps[k]));
                    snake1.set_direction(parseInt(b_steps[k]));
                }
                k ++;
            },300);

        }else {
            this.ctx.canvas.focus();  //自动聚焦
            this.ctx.canvas.addEventListener("keydown", e => {

                let d = -1;
                if (e.key === 'w') d = 0;
                else if (e.key === 'd') d = 1;
                else if (e.key === 's') d = 2;
                else if (e.key === 'a') d = 3;

                if (d >= 0) {
                    this.store.state.battle.socket.send(JSON.stringify({
                        event: "move",
                        directives: d,
                    }));
                }
            });
        }
    }

    start() {
        this.create_barrier();
        this.events_listener();
    }

    //判断snake 下回合是否移动
    check_step() {
        for(let snake of this.snakes){
            if(snake.status !== 0) return false;
            if(snake.directives === -1) return false;
        }
        return true;

    }

    //地图和小方格大小
    update_size(){
        this.L = parseInt( Math.min(this.parent.clientHeight / this.rows, this.parent.clientWidth / this.cols) );
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    //蛇进入下一回合
    next_round(){
        for(let snake of this.snakes){
            snake.update_status();
        }
    }

    //目标位置是否合法 => 目标位置是否撞墙/是否是两条蛇的身体
    is_hit_wall(body){
        for(let wall of this.walls){
            if (wall.row === body.row && wall.col === body.col){
                return false;
            }
        }

        for(let snake of this.snakes){
            let len = snake.snakeBody.length;
            if(!snake.is_add_length()){   //长度不加1的时候蛇尾会动，此时上一步的蛇尾不要判断
                len --;
            }
            for(let i = 0 ; i < len; i ++){
                if (snake.snakeBody[i].row === body.row && snake.snakeBody[i].col === body.col){
                    return false;
                }
            }
        }
        return true;
    }

    update() {
        this.update_size();
        //下回合移动
        if (this.check_step()){
            this.next_round();
        }

        this.render();
    }
    // 渲染函数
    render(){
        const color_even = "#AAD751", color_odd = "#A2D149";
        for(let r = 0; r < this.rows; r ++){
            for(let c = 0; c < this.cols; c ++){
                if( (r+c) % 2 == 0 ){
                    this.ctx.fillStyle = color_even;
                }else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }
}