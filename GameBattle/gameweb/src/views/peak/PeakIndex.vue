<template>

  <ComCard>

    <table class="table table-striped table-hover" >
      <thead>
      <tr>
        <th>玩家</th>
        <th>巅峰分</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id" >
        <td>
          <img :src="user.headshot" alt="" class="review-photo"> &nbsp;
          <span class="review-username">{{ user.username }}</span>
        </td>
        <td>{{ user.rating }}</td>
      </tr>
      </tbody>
    </table>

    <nav aria-label="..." style="float: right">
      <ul class="pagination">
        <li class="page-item" @click="click_page(-2)" >
          <a class="page-link" href="#">前一页</a>
        </li>

        <li :class="'page-item ' + page.active " v-for="page in pages" :key="page.number" @click="click_page(page.number)">
          <a class="page-link" href="#">{{ page.number }}</a>
        </li>
        <li class="page-item" @click="click_page(-1)">
          <a class="page-link" href="#">后一页</a>
        </li>
      </ul>
    </nav>



  </ComCard>

</template>

<script>
import ComCard from "@/components/ComCard";
import {useStore} from "vuex";
import {ref} from "vue";
import $ from "jquery";
export default {
  name: "PeakIndex",
  components:{
    ComCard,
  },

  setup() {
    const store = useStore();
    let users = ref([]);
    let current_page = 1;
    let total_users = 0;
    let pages = ref([]);

    const click_page = (page) => {
      if (page === -2) page = current_page - 1;
      if (page === -1) page = current_page + 1;
      let max_pages = parseInt(Math.ceil(total_users / 10));

      if (page >= 1 && page <= max_pages){
        pull_page(page);
      }
    };

    const update_pages = () => {
      let max_pages = parseInt(Math.ceil(total_users / 10));
      let new_pages = [];
      for(let i = current_page - 2; i <= current_page + 2; i ++){
        if (i >= 1 && i <= max_pages) {
          new_pages.push({
            number: i,
            active: i === current_page ? "active" : "",
          });
        }
      }
      pages.value = new_pages;
    }

    const pull_page = (page) =>{
      current_page = page;
      $.ajax({
        url: "https://app3817.acapp.acwing.com.cn/api/peak/getlist",
        data: {
          page,
        },
        type: "GET",
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp){
          users.value = resp.users;
          total_users = resp.users_count;
          update_pages();
        },
        error(resp){
          console.log(resp);
        }
      });
    };

    pull_page(current_page);

    return{
      users,
      total_users,
      pages,
      click_page
    }
  }
}
</script>

<style scoped>

img.review-photo {
  width: 5vh;
  border-radius: 50%;
}

</style>