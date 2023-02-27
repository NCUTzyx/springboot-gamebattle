const game_objects = [];

export class ComponentObject{
    constructor() {
        game_objects.push(this);
        this.has_start = false;  //是否初始化
        this.timedelta = 0; //时间间隔
    }
    // 初始化 第一帧执行 只执行一次
    start() {


    }
    // 每一帧执行一次，除第一帧外
    update() {

    }
    //删除之前执行
    on_destroy(){

    }

    // 从总的数组中, 删除对象
    destroy() {
        this.on_destroy();
        for(let i in game_objects){   //i 是索引
            let obj = game_objects[i];
            if (obj === this){
                game_objects.splice(i);
                break;
            }
        }
    }

}
let last_timestamp;  // 上一次执行的时刻
let step = (timestamp) => {  //正在执行的时刻
    for (let obj of game_objects){
        if(!obj.has_start){
            obj.has_start = true;
            obj.start();
        }else{
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(step);
}

requestAnimationFrame(step);
