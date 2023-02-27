import {ComponentObject} from "@/assets/scripts/ComponentObject";
import {SnakeBody} from "@/assets/scripts/SnakeBody";

export class Snake extends ComponentObject{
    constructor(info,gameMap) {
        super();

        this.id = info.id;
        this.color = info.color;
        this.gameMap = gameMap;
        //存放蛇 snakeBody[0] 是蛇头
        this.snakeBody = [new SnakeBody(info.row,info.col)];
        this.next_step = null;  //下一回合的位置

        this.speed = 5; //速度
        this.directives = -1;  // -1:无指令 0:上 1:右 2:下 3:左
        this.status = 0; // 0:静止 1:移动 2:死亡

        //定义偏移量
        this.dr = [-1, 0, 1, 0];
        this.dc = [0, 1, 0, -1];

        this.ans = 0;  //当前回合数

        //初始化眼睛方向
        if (this.id === 0) this.eye_dir = 0;  //左下角朝上
        if (this.id === 1) this.eye_dir = 2;  //右上角朝下

        //蛇眼睛的偏移量 左右两眼
        this.eye_dx = [
            [-1,1],
            [1,1],
            [1,-1],
            [-1,-1],
        ];

        this.eye_dy = [
            [-1,-1],
            [-1,1],
            [1,1],
            [1,-1],
        ];
    }

    start(){

    }

    //当前回合蛇的长度是否 +1
    is_add_length(){
        if(this.ans <= 10) return true;
        if(this.ans % 3 === 1) return true;
        return false;
    }

    //设置蛇移动的方向
    set_direction(d) {
        this.directives = d;
    }

    //更新蛇的状态 下一回合
    update_status(){

        const d = this.directives;
        let r = this.snakeBody[0].row + this.dr[d];
        let c = this.snakeBody[0].col + this.dc[d];
        this.next_step = new SnakeBody(r,c);
        this.eye_dir = d;
        this.directives = -1;  //清空操作
        this.status = 1;
        this.ans ++;

        const len = this.snakeBody.length;
        for(let i = len; i > 0; i --){
            this.snakeBody[i] =  JSON.parse(JSON.stringify(this.snakeBody[i - 1]));
        }

    }

    //蛇的移动
    move(){
        let dx = this.next_step.x - this.snakeBody[0].x;
        let dy = this.next_step.y - this.snakeBody[0].y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        //走到目标点
        if(distance < 1e-2){
            this.snakeBody[0] = this.next_step;   //增加一个新的蛇头
            this.next_step = null;
            this.status = 0;

            if(!this.is_add_length()){
                this.snakeBody.pop();
            }

        }else {
            const space_dis = this.speed * this.timedelta / 1000;  //每一帧走过的距离（间隔距离）
            this.snakeBody[0].x += space_dis * dx / distance;
            this.snakeBody[0].y += space_dis * dy / distance;

            //蛇不变长
            if(!this.is_add_length()){
                const len = this.snakeBody.length;
                const tail = this.snakeBody[len - 1];
                const tail_next = this.snakeBody[len - 2];
                const tail_dx = tail_next.x - tail.x;
                const tail_dy = tail_next.y - tail.y;
                //尾巴移动的距离
                tail.x += space_dis * tail_dx / distance;
                tail.y += space_dis * tail_dy / distance;

            }

        }
    }

    update() {
        if(this.status === 1){
            this.move();
        }
        this.render();
    }

    render(){
        const L = this.gameMap.L;
        const ctx = this.gameMap.ctx;

        ctx.fillStyle = this.color;

        if (this.status === 2){
            ctx.fillStyle = "white";
        }

        for(let body of this.snakeBody){
            ctx.beginPath();
            ctx.arc(body.x * L, body.y * L,L/2 * 0.8,0, Math.PI * 2);
            ctx.fill();
        }

        for(let i = 1; i < this.snakeBody.length ; i ++){
            const a = this.snakeBody[i], b = this.snakeBody[i - 1];
            //重合
            if(Math.abs(a.x - b.x ) < 1e-2 && Math.abs(a.y - b.y) < 1e-2)
                continue;
            //竖直方向
            if (Math.abs(a.x - b.x) < 1e-2){
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y,b.y) * L, L * 0.8,Math.abs(a.y - b.y) * L);
            }else {
                ctx.fillRect(Math.min(a.x,b.x) * L, (a.y - 0.4) * L,Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }

        ctx.fillStyle = "black";
        for(let i = 0; i < 2; i ++){

            const eye_x = (this.snakeBody[0].x + this.eye_dx[this.eye_dir][i] * 0.15) * L;
            const eye_y = (this.snakeBody[0].y + this.eye_dy[this.eye_dir][i] * 0.15) * L ;

            ctx.beginPath();
            ctx.arc(eye_x, eye_y, L * 0.05,0,Math.PI * 2);
            ctx.fill();
        }

    }

}