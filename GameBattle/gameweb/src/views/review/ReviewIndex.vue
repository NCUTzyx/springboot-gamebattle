<template>
  <ComCard>
    <table class="table table-striped table-hover" >
      <thead>
      <tr>
        <th>A</th>
        <th>B</th>
        <th>对战结果</th>
        <th>对战时间</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="review in reviews" :key="review.review.id" >
        <td>
          <img :src="review.a_headshot" alt="" class="review-photo"> &nbsp;
          <span class="review-username">{{ review.a_username }}</span>
        </td>

        <td>
          <img :src="review.b_headshot" alt="" class="review-photo"> &nbsp;
          <span class="review-username">{{ review.b_username }}</span>
        </td>
        <td>{{ review.result }}</td>
        <td>{{review.review.createtime}}</td>
        <td>
          <button type="button" class="btn btn-primary" @click="open_review_content(review.review.id)">查看录像</button>
        </td>
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
import $ from "jquery";
import {ref} from "vue";
import router from "@/router";

export default {
  name: "ReviewIndex",
  components:{
    ComCard,
  },
  setup() {
    const store = useStore();
    let reviews = ref([]);
    let current_page = 1;
    let total_reviews = 0;
    let pages = ref([]);

    const click_page = (page) => {
      if (page === -2) page = current_page - 1;
      if (page === -1) page = current_page + 1;
      let max_pages = parseInt(Math.ceil(total_reviews / 10));

      if (page >= 1 && page <= max_pages){
        pull_page(page);
      }
    }

    const update_pages = () => {
      let max_pages = parseInt(Math.ceil(total_reviews / 10));
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
          url: "https://app3817.acapp.acwing.com.cn/api/review/getlist",
          data: {
            page,
          },
          type: "GET",
          headers: {
            Authorization: "Bearer " + store.state.user.token,
          },
          success(resp){
            reviews.value = resp.reviews;
            total_reviews = resp.reviews_count;
            update_pages();
          },
          error(resp){
            console.log(resp);
          }
        });
    };

    pull_page(current_page);

    const stringTo2D = map =>{
      let g = [];
      for (let i = 0, k = 0; i < 13; i ++){
        let line = [];
        for(let j = 0; j < 14; j ++, k ++){
            if (map[k] === '0') line.push(0);
            else line.push(1)
        }
        g.push(line);
      }
      return g;
    }

    const open_review_content = (reviewId) => {
      for (const review of reviews.value){
        if (review.review.id === reviewId){
          store.commit("updateIsReview",true);
          console.log(review);
          store.commit("updateGame",{
            map: stringTo2D(review.review.map),
            a_id: review.review.aid,
            a_sx: review.review.asx,
            a_sy: review.review.asy,
            b_id: review.review.bid,
            b_sx: review.review.bsx,
            b_sy: review.review.bsy,
          });
          store.commit("updateSteps",{
            a_steps: review.review.asteps,
            b_steps: review.review.bsteps,
          });
          store.commit("updateReviewLoser",review.review.loser);
          router.push({
            name: "reviewContent",
            params: {
              reviewId: reviewId,
            }
          });
          break;
        }
      }
    }

    return{
      reviews,
      total_reviews,
      open_review_content,
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