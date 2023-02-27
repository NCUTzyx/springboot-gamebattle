import $ from "jquery";

const ModuleUser = {
    state: {
        id: "",
        username: "",
        headshot: "",
        token: "",
        is_login: false,
        is_info: true,  //是否在从后端拉取信息
    },
    getters: {
    },
    mutations: {
        updateUser(state,user){
            state.id = user.id;
            state.username = user.username;
            state.headshot = user.headshot;
            state.is_login = user.is_login;
        },
        updateToken(state,token){
            state.token = token;
        },
        logout(state){
            state.id = "";
            state.username = "";
            state.headshot = "";
            state.token = "";
            state.is_login = false;
        },
        updateIsInfo(state,is_info){
            state.is_info = is_info;
        }
    },
    actions: {
        login(context,data){

            $.ajax({
                url: "https://app3817.acapp.acwing.com.cn/api/user/account/token",
                type: "POST",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp){
                    if (resp.message === "success"){
                        localStorage.setItem("jwt_token",resp.token);
                        context.commit("updateToken",resp.token);
                        data.success(resp);
                    }else {
                        data.error(resp);
                    }
                },
                error(resp){
                    data.error(resp);
                }
            });
        },

        getInfo(context, data){
            $.ajax({
                url: "https://app3817.acapp.acwing.com.cn/api/user/account/info",
                type: "GET",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp){
                    if (resp.message === "success"){
                        context.commit("updateUser",{
                            ...resp,
                            is_login: true,
                        });
                        data.success(resp);
                    }else {
                        data.error(resp);
                    }
                },
                error(resp){
                    data.error(resp);
                }
            });
        },
        logout(context) {
            localStorage.removeItem("jwt_token");
            context.commit("logout");
        }
    },
    modules: {
    }
};
export default ModuleUser;