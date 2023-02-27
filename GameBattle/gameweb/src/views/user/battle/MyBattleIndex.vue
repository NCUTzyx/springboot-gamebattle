<template>

  <div class="container">
    <div class="row">
      <div class="col-3">
        <div class="card" style="margin-top: 20px">
          <div class="card-body">
            <img :src="$store.state.user.headshot" alt="头像" style="width: 100%;">
          </div>
        </div>
      </div>

      <div class="col-9">
        <div class="card" style="margin-top: 20px">
          <div class="card-header">
               <span class="title">MyRobot</span>
            <button type="button" class="btn btn-outline-primary float-end" data-bs-toggle="modal" data-bs-target="#addRobot-btn">Create</button>

            <!-- Create Modal -->
            <div class="modal fade" id="addRobot-btn" tabindex="-1" >
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Create Robot</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div class="mb-3">
                      <label for="addRobot-name" class="form-label">名称</label>
                      <input v-model="addRobot.name" type="text" class="form-control" id="addRobot-name" placeholder="请输入Robot名称">
                    </div>
                    <div class="mb-3">
                      <label for="addRobot-description" class="form-label">简介</label>
                      <textarea v-model="addRobot.description" class="form-control" id="addRobot-description" rows="3" placeholder="请输入Robot简介"></textarea>
                    </div>
                    <div class="mb-3">
                      <label for="addRobot-code" class="form-label">代码</label>
                      <VAceEditor
                          v-model:value="addRobot.content"
                          @init="editorInit"
                          lang="c_cpp"
                          theme="textmate"
                          style="height: 300px" />
                    </div>
                  </div>
                  <div class="modal-footer">
                    <div class="message">{{ addRobot.message }}</div>
                    <button type="button" class="btn btn-primary" @click="add_Robot">创建</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="card-body">
            <table class="table table-striped table-hover">
              <thead>
                <tr>
                  <th>名称</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="robot in robots" :key="robot.id">
                  <td>{{ robot.name }}</td>
                  <td>{{ robot.createtime }}</td>
                  <td>
                    <button type="button" class="btn btn-outline-secondary" style="margin-right: 10px;" data-bs-toggle="modal" :data-bs-target="'#updateRobot-' + robot.id">修改</button>
                    <button type="button" class="btn btn-outline-danger" @click="delete_robot(robot)">删除</button>

                    <!-- Update Modal -->
                    <div class="modal fade" :id="'updateRobot-' + robot.id " tabindex="-1" >
                      <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Create Robot</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            <div class="mb-3">
                              <label for="addRobot-name" class="form-label">名称</label>
                              <input v-model="robot.name" type="text" class="form-control" id="addRobot-name" placeholder="请输入Robot名称">
                            </div>
                            <div class="mb-3">
                              <label for="addRobot-description" class="form-label">简介</label>
                              <textarea v-model="robot.description" class="form-control" id="addRobot-description" rows="3" placeholder="请输入Robot简介"></textarea>
                            </div>
                            <div class="mb-3">
                              <label for="addRobot-code" class="form-label">代码</label>
                              <VAceEditor
                                  v-model:value="robot.content"
                                  @init="editorInit"
                                  lang="c_cpp"
                                  theme="textmate"
                                  style="height: 300px" />
                            </div>
                          </div>
                          <div class="modal-footer">
                            <div class="message">{{ robot.message }}</div>
                            <button type="button" class="btn btn-primary" @click="update_robot(robot)">保存修改</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { ref, reactive } from "vue";
import $ from "jquery";
import {useStore} from "vuex";
import {Modal} from "bootstrap/dist/js/bootstrap";
import {VAceEditor} from 'vue3-ace-editor';
import ace from 'ace-builds';

export default {
  name: "RobotIndex",
  components:{
    VAceEditor
  },
  setup(){
    ace.config.set(
        "basePath",
        "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")

    const store = useStore();
    let robots = ref([]);

    const addRobot = reactive({
      name: "",
      description: "",
      content: "",
      message: "",
    })

    //查询 robot
    const refresh_list =()=>{
      $.ajax({
        url: "https://app3817.acapp.acwing.com.cn/api/user/robot/getlist",
        type: "GET",
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp){
          robots.value = resp;
        }
      })
    };
    refresh_list();

    //增加robot
    const add_Robot =()=>{
      addRobot.message = "";
      $.ajax({
        url: "https://app3817.acapp.acwing.com.cn/api/user/robot/add",
        type: "POST",
        data: {
          name: addRobot.name,
          description: addRobot.description,
          content: addRobot.content
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        success(resp){
          if (resp.message === "success"){
            addRobot.name = "";
            addRobot.description = "";
            addRobot.content = "";
            //关闭Modal框
            Modal.getInstance("#addRobot-btn").hide();

            refresh_list();

          }else {
            addRobot.message = resp.message;
          }
        }
      });
    };

    //删除robot
    const delete_robot =(robot)=>{
      $.ajax({
        url: "https://app3817.acapp.acwing.com.cn/api/user/robot/delete",
        type: "POST",
        data: {
          robot_id: robot.id,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp){
          if (resp.message === "success"){
            refresh_list();
          }
        }
      });
    };

    //修改robot
    const update_robot =(robot)=> {
      $.ajax({
        url: "https://app3817.acapp.acwing.com.cn/api/user/robot/update",
        type: "POST",
        data: {
          robot_id: robot.id,
          name: robot.name,
          description: robot.description,
          content: robot.content,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        success(resp){
          if (resp.message === "success"){
            Modal.getInstance('#updateRobot-' + robot.id).hide();
            refresh_list();
          }
        }
      });
    }

    return{
      robots,
      addRobot,
      add_Robot,
      delete_robot,
      update_robot,
    }
  }
}
</script>

<style scoped>

.title{
  font-size: 150%;
  font-weight: bolder;
  color: grey;
  font-family:  'Satisfy',cursive;
}

div.message{
  color: red;
  margin-right: 10px;;
}


</style>